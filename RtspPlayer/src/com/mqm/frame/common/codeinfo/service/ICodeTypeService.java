/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.codeinfo.service;

import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeType;
import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.util.PagedResult;

/**
 * 
 * <pre>
 * 代码类型服务接口。
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
public interface ICodeTypeService extends IGenericService<FbrpCommonCodeType> {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_infrastructure_codeTypeService";

	/**
	 * 批量删除多条代码类型的记录 并且一并删除该类型下代码值信息。
	 * 
	 * @param id
	 *            待删除记录的代码类型主键集合
	 */
	public void deleteByCascade(String id);

	/**
	 * 根据查询条件去获取查询记录详情和记录总数。
	 * 
	 * @param vo
	 *            查询条件
	 * @param start
	 *            开始位置
	 * @param pageSize
	 *            每页显示记录数
	 * 
	 * @return PagedResult
	 *         
	 */
	public PagedResult<FbrpCommonCodeType> query(FbrpCommonCodeType vo,
			int start, int pageSize);

	/**
	 * 判断该代码类型是否可以被代码值引用了。
	 * 
	 * @param vo
	 *            代码类型VO
	 * 
	 * @return boolean
	 */
	public boolean checkTypeIsReferenced(FbrpCommonCodeType vo);

	/**
	 * 根据代码名称获取对应应用的代码类型VO。
	 * 
	 * @param codeTypeName
	 *            代码类型英文名
	 * 
	 * @return FbrpCommonCodeType
	 */
	public FbrpCommonCodeType findFirstByName(String codeTypeName);

}
