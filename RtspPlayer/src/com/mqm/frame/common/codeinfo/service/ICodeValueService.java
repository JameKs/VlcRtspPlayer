/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.codeinfo.service;

import java.util.List;
import java.util.Map;

import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;
import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.util.PagedResult;

/**
 * 
 * <pre>
 * 代码值服务接口。
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
public interface ICodeValueService extends IGenericService<FbrpCommonCodeValue>{

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_common_codeValueService";

	/**
	 * 使用代码类型取得代码值列表 该方法总是使用缓存。
	 * 
	 * @param key String
	 * 
	 * @return Map
	 */
	Map<String, String> getCodeValueMap(String key);

	/**
	 * 使用代码类型取得代码值列表 该方法的第二个参数forceReload可以指定总是从数据库查找。
	 * 
	 * @param key String
	 * 
	 * @param forceReload
	 *            为true时，总时从数据库取最新的值，并更新到缓存中
	 * 
	 * @return Map 键值对，其中key对就代码值的"值"，value对应代码值的"名称"
	 */
	Map<String, String> getCodeValueMap(String key, boolean forceReload);

	/**
	 * 根据码表类型名称和本地语言，获取指定应用代码值信息列表。
	 * 
	 * @param codeTypeName
	 *            代码类型名称
	 * 
	 * @return List 代码值信息列表
	 */
	public List<FbrpCommonCodeValue> getCodeInfoList(String codeTypeName);

	/**
	 * 根据代码类型ID获取代码值信息(不使用cache，有分页)。
	 * 
	 * @param codeTypeId
	 *            代码类型ID
	 *            
	 * @param pageIndex 
	 *                 int
	 *                 
	 * @param pageSize
	 *                int
	 *            
	 * @return PagedResult<FbrpInfraCodeValueVO>
	 */
	public PagedResult<FbrpCommonCodeValue> findByCodeTypeId(String codeTypeId, int pageIndex, int pageSize);
	
	/**
	 * 根据代码类型ID获取代码值信息(不使用cache)。
	 * 
	 * @param codeTypeId
	 *            代码类型ID
	 *            
	 * @return List<FbrpInfraCodeValueVO>
	 */
	public List<FbrpCommonCodeValue> findByCodeTypeId(String codeTypeId);

	/**
	 * 创建或者更新代码值。
	 * 
	 * @param codeValue FbrpCommonCodeValue
	 */
	public void createOrUpdateCodeValue(FbrpCommonCodeValue codeValue);
	
	/**
	 * 删除代码值。
	 * 
	 * @param id String
	 */
	public void deleteCodeValue(String id);
	
	/**
	 * 批量删除代码值。
	 * 
	 * @param ids List<String>
	 */
	public void deleteCodeValue(List<String> ids);

}
