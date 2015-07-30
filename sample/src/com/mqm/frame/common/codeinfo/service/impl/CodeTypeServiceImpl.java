/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.codeinfo.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.common.cache.service.IDataCacheService;
import com.mqm.frame.common.codeinfo.service.ICodeTypeService;
import com.mqm.frame.common.codeinfo.service.ICodeValueService;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeType;
import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.util.PagedResult;

/**
 * 
 * <pre>
 * 代码管理服务之代码类型实现类。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CodeTypeServiceImpl extends MyBatisServiceImpl<FbrpCommonCodeType> implements
		ICodeTypeService {

	private static final Log log = LogFactory.getLog(CodeTypeServiceImpl.class);
	
	private IDataCacheService dataCacheService;
	
	/**
	 * 批量删除多条代码类型的记录 并且一并删除该类型下代码值信息。
	 * 
	 * @param id
	 *            待删除记录的代码类型主键
	 */

	public void deleteByCascade(String id) {
		FbrpCommonCodeType codeType = this.find(id);
		if(codeType==null){
			//TODO luxiaocheng deal bad datas
			return;
		}
		this.delete(id);
		//this.deleteBy(this.newMapInstance("codeTypeId", id));
		ICodeValueService codeValueService = (ICodeValueService)getApplicationContext().getBean("fbrp_common_codeValueService");
		codeValueService.deleteBy("codeTypeId", id);
		this.dataCacheService.removeDataFromCache(codeType.getName());
		
	}

	/**
	 * 根据查询条件去获取查询记录详情和记录总数。
	 * 
	 * @param queryVO
	 *            查询条件
	 * @param pageIndex
	 *            开始位置
	 * @param pageSize
	 *            每页显示记录数
	 * 
	 * @return Object[]
	 *         Object[]中result[0]是一个FbrpCommonCodeTypeVO的List,result[1]总数Integer
	 */
	public PagedResult<FbrpCommonCodeType> query(FbrpCommonCodeType queryVO, int pageIndex,
			int pageSize) {
		PMap pm = this.newMapInstance();
		pm.includeIgnoreCase("name", queryVO.getName());
		pm.includeIgnoreCase("cname", queryVO.getCname());
		pm.includeIgnoreCase("remark", queryVO.getRemark());
		return this.pagedQuery(pm, pageIndex, pageSize);
	}

	/**
	 * 判断该代码类型是否可以被代码值引用了。
	 * 
	 * @param vo
	 *            代码类型VO
	 * 
	 * @return boolean
	 */
	
	public boolean checkTypeIsReferenced(FbrpCommonCodeType vo) {
		PMap pm = this.newMapInstance("codeTypeId", vo.getId());
		ICodeValueService codeValueService = (ICodeValueService)getApplicationContext().getBean("fbrp_common_codeValueService");
		return codeValueService.count(pm)>0;
		
		
	}

	/**
	 * 根据代码名称获取对应当前应用的代码类型VO。
	 * 
	 * @param codeTypeName
	 *            代码类型英文名称
	 * 
	 * @return FbrpCommonCodeTypeVO
	 */
	
	public FbrpCommonCodeType findFirstByName(String codeTypeName) {
		return this.findFirstBy(this.newMapInstance("name", codeTypeName));
	}

	/**
	 * 设置 dataCacheService。
	 * 
	 * @param dataCacheService
	 *            设置 dataCacheService。
	 */
	public void setDataCacheService(IDataCacheService dataCacheService) {
		this.dataCacheService = dataCacheService;
	}

}
