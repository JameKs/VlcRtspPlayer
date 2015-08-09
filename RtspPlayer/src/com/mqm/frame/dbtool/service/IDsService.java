/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDsVO;
import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;

/**
 * <pre>
 * 数据源操作服务接口.
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface IDsService extends IGenericService<FbrpDbtoolDs>{

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_dbtool_dsService";

	/**
	 * 新增或保存这个数据源的连接信息。
	 * 
	 * @param fbrpDbtoolDsVO   数据源的连接信息       
	 */
	public void saveOrUpdateFbrpDbtoolDsVO(FbrpDbtoolDs fbrpDbtoolDsVO);

	/**
	 * 返回一个当前应用下的数据源连接查询结果的Object[],object[0]为数据源的列表,oject[1]为符合条件的数据源的总数。
	 * 
	 * @param fbrpDbtoolDsVO  用来进行查询操作的对象
	 *            
	 * @param offSet 偏移index
	 *            
	 * @param pageSize 查询记录的数量
	 *            
	 * @return Object[]中result[0]是一个FbrpDbtoolConnVO的List,result[1]总数Integer
	 */
	public Object[] getListByParams(FbrpDbtoolDs fbrpDbtoolDsVO, int offSet,
			int pageSize);

	/**
	 * 根据所提供的FbrpDbtoolDsVO的Id来获取数据源连接对象。
	 * 
	 * @param id 数据源编号
	 *            
	 * @return 查询到的结果
	 */
	public FbrpDbtoolDs getFbrpDbtoolDsVObyId(String id);

	/**
	 * 批量删除定义的数据源。
	 * 
	 * @param idList   ID集合
	 *        
	 * @return 被删除的行数
	 */
	public int deleteAllByIdList(List<String> idList);

	/**
	 * 得到当前应用下所有的连接对象。
	 * 
	 * @return 所有的链接对象
	 */
	public List<FbrpDbtoolDs> getAllFbrpDbtoolDsVO();

	/**
	 * 查询指定应用下的所有数据源连接信息,未指定应用则查询所有数据源。
	 * 
	 * @param appId  应用编号
	 *           
	 * @return 数据源连接信息列表
	 */
	public List<FbrpDbtoolDs> getListByAppId(String appId);

	/**
	 * 获取指定数据源的SimpleJdbcTemplate。
	 * 
	 * @param dsId
	 *            引用的数据源ID
	 * @return SimpleJdbcTemplate
	 */
	public abstract SimpleJdbcTemplate getSimpleJdbcTemplate(String dsId);

	/**
	 * 测试数据库连接信息的是否能连接上远程数据库。
	 * 
	 * @param ds  测试链接的属性
	 *           
	 * @return 是否测试成功
	 */
	public abstract boolean testConnection(FbrpDbtoolDs ds);

	/**
	 * 获取所有连接类型默认设置。
	 * 
	 * @param dbTypeList List
	 * 
	 * @return 连接默认设置列表 FbrpDbtoolDsVO
	 */
	public Map<String, FbrpDbtoolDs> getDefaultDsVOMap(List<FbrpCommonCodeValue> dbTypeList);

	/**
	 * 根据条件分页查找数据源。
	 * 
	 * @param fbrpDbtoolDs FbrpDbtoolDs
	 * @param pageIndex int
	 * @param pageSize int
	 * 
	 * @return PagedResult<FbrpDbtoolDsVO>
	 */
	public PagedResult<FbrpDbtoolDsVO> pagedQuery(FbrpDbtoolDsVO fbrpDbtoolDs, int pageIndex, int pageSize);

	/**
	 * 保存或修改数据源。
	 * 
	 * @param fbrpDbtoolDs FbrpDbtoolDs
	 * @return String
	 */
	public String createOrUpdateDs(FbrpDbtoolDs fbrpDbtoolDs);
	
}