/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.codeinfo.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mqm.frame.common.cache.service.IDataCacheService;
import com.mqm.frame.common.codeinfo.service.ICodeTypeService;
import com.mqm.frame.common.codeinfo.service.ICodeValueService;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeType;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;
import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.util.VOUtils;

/**
 * 
 * <pre>
 * 代码管理服务之代码值实现类。
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
public class CodeValueServiceImpl extends MyBatisServiceImpl<FbrpCommonCodeValue> implements ICodeValueService {

	private IDataCacheService dataCacheService;
	private ICodeTypeService codeTypeService;

	/**
	 * 应用启动时从数据库装载出所有的代码值信息，并缓存起来。
	 * 
	 * 代码值存储方式：{codeTypeName,[codeValueVO,codeValueVO,...]}
	 */
	protected void init() {
		// 获取所有的代码值信息
		List<FbrpCommonCodeValue> codeValueVOList = this.list(asc("sortNo"));
		// 获取所有代码类型

		Map<String, FbrpCommonCodeType> codeTypeMap = VOUtils.convert2Map(this.codeTypeService.list());
		Map<String, List<FbrpCommonCodeValue>> map = new HashMap<String, List<FbrpCommonCodeValue>>();
		List<FbrpCommonCodeValue> codeValueList = null;
		String keyId = null;
		for (FbrpCommonCodeValue fbrpInfraCodeValueVO : codeValueVOList) {
			if (codeTypeMap.get(fbrpInfraCodeValueVO.getCodeTypeId()) == null) {
				continue;
			}
			keyId = codeTypeMap.get(fbrpInfraCodeValueVO.getCodeTypeId()).getName();
			if (!map.containsKey(keyId)) {
				codeValueList = new ArrayList<FbrpCommonCodeValue>();
				map.put(keyId, codeValueList);
			}
			map.get(keyId).add(fbrpInfraCodeValueVO);
		}
		// 置入cache中
		for(Map.Entry<String, List<FbrpCommonCodeValue>> entry : map.entrySet()){
			String key = entry.getKey();
			List<FbrpCommonCodeValue> value = entry.getValue();
			this.dataCacheService.putDataToCache(key, (Serializable)value);
		}
				
//		for (String key : map.keySet()) {
//			this.dataCacheService.putDataToCache(key, (Serializable) map.get(key));
//		}
		// 演示MyBatis分页的使用
		// String sId = FbrpInfraCodeValueVO.class.getName()+".selectByMap";
		// this.getSqlSessionTemplate().selectList(sId, null, new RowBounds(3,
		// 7));
	}

	/**
	 * 使用代码类型取得代码值列表 该方法总是使用缓存。
	 * 
	 * 
	 * @param codeTypeName
	 *            代码类型名称
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getCodeValueMap(String codeTypeName) {
		return getCodeValueMap(codeTypeName, false);
	}

	/**
	 * 使用代码类型取得代码值列表 该方法的第二个参数forceReload可以指定总是从数据库查找。
	 * 
	 * @param codeTypeName
	 *            代码类型名称
	 * @param forceReload
	 *            为true时，总时从数据库取最新的值，并更新到缓存中
	 * 
	 * @return Map<String, String> 键值对，其中key对就代码值的"值"，value对应代码值的"名称"
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getCodeValueMap(String codeTypeName, boolean forceReload) {
		Map<String, String> retMap = null;
		List<FbrpCommonCodeValue> codeValueList = null;
		// 使用缓存
		if (!forceReload) {
			codeValueList = (List<FbrpCommonCodeValue>) this.dataCacheService.getDataFromCache(codeTypeName);
			if ((codeValueList == null || codeValueList.size() < 1)) {
				codeValueList = (List<FbrpCommonCodeValue>) this.dataCacheService.getDataFromCache(codeTypeName);
			}
			retMap = listConvertMap(codeValueList);
			return retMap;
		}

		codeValueList = loadByTypeName(codeTypeName);
		this.dataCacheService.putDataToCache(codeTypeName, (Serializable) codeValueList);
		retMap = listConvertMap(codeValueList);
		return retMap;
	}

	/**
	 * 根据码表类型名称和本地语言，获取指定应用代码值信息列表。
	 * 
	 * @param codeTypeName
	 *            代码类型名称
	 * @return List 代码值信息列表
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<FbrpCommonCodeValue> getCodeInfoList(String codeTypeName) {
		List<FbrpCommonCodeValue> codeValueList = (List<FbrpCommonCodeValue>) this.dataCacheService
				.getDataFromCache(codeTypeName);
		if (codeValueList == null || codeValueList.size() < 1) {
			codeValueList = this.loadByTypeName(codeTypeName);
			if (codeValueList != null && codeValueList.size() > 0) {
				this.dataCacheService.putDataToCache(codeTypeName, (Serializable) codeValueList);
			}
		}

		return codeValueList == null ? new ArrayList<FbrpCommonCodeValue>() : codeValueList;
	}

	/**
	 * 根据代码类型ID获取代码值信息(不使用cache，有分页)。
	 * 
	 * @param codeTypeId 代码类型ID
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @return PagedResult<FbrpCommonCodeValue>
	 */
	public PagedResult<FbrpCommonCodeValue> findByCodeTypeId(String codeTypeId, int pageIndex, int pageSize) {
		PMap pm = this.newMapInstance();
		pm.eq("codeTypeId", codeTypeId);
		return this.pagedQuery(pm, pageIndex, pageSize);
	}
	
	/**
	 * 根据代码类型ID获取代码值信息(不使用cache)。
	 * 
	 * @param codeTypeId 代码类型ID
	 * @return List&lt;FbrpInfraCodeValueVO&gt;
	 */
	public List<FbrpCommonCodeValue> findByCodeTypeId(String codeTypeId) {
		return this.findBy("codeTypeId", codeTypeId);
	}

	@Override
	public void createOrUpdateCodeValue(FbrpCommonCodeValue codeValue){
		this.createOrUpdate(codeValue);
		String codeTypeId = codeValue.getCodeTypeId();
		FbrpCommonCodeType codeType = this.codeTypeService.find(codeTypeId);
		// 代码值维护完刷新cache
		List<FbrpCommonCodeValue> codeValueList = this.findByCodeTypeId(codeTypeId);
		this.dataCacheService.putDataToCache(codeType.getName(), (Serializable) codeValueList);
	}
	
	@Override
	public void deleteCodeValue(String id){
		FbrpCommonCodeValue codeValue = this.find(id);
		this.delete(codeValue);
		String codeTypeId = codeValue.getCodeTypeId();
		FbrpCommonCodeType codeType = this.codeTypeService.find(codeTypeId);
		// 代码值维护完刷新cache
		List<FbrpCommonCodeValue> codeValueList = this.findByCodeTypeId(codeTypeId);
		this.dataCacheService.putDataToCache(codeType.getName(), (Serializable) codeValueList);
	}
	
	@Override
	public void deleteCodeValue(List<String> ids){
			FbrpCommonCodeValue codeValue = this.find(ids.get(0));
			this.deleteByIds(ids);
			String codeTypeId = codeValue.getCodeTypeId();
			FbrpCommonCodeType codeType = this.codeTypeService.find(codeTypeId);
			// 代码值维护完刷新cache
			List<FbrpCommonCodeValue> codeValueList = this.findByCodeTypeId(codeTypeId);
			this.dataCacheService.putDataToCache(codeType.getName(), (Serializable) codeValueList);	
	}
	
	

	/**
	 * 将list<fbrpInfraCodeValueVO>转换成Map<String,String> {value1,name}。
	 * 
	 * @param codeValueList
	 * 
	 * @return Map<String, String>
	 */
	private Map<String, String> listConvertMap(List<FbrpCommonCodeValue> codeValueList) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (codeValueList != null && codeValueList.size() > 0) {
			for (FbrpCommonCodeValue vo : codeValueList) {
				map.put(vo.getValue1(), vo.getName());
			}
		}
		return map;
	}

	/**
	 * 通过代码类型名称 ，加载代码值列表 直接从数据库获取。
	 * 
	 * @param codeTypeName
	 * 
	 * @return List<fbrpInfraCodeValueVO>
	 */
	private List<FbrpCommonCodeValue> loadByTypeName(String codeTypeName) {
		FbrpCommonCodeType codeType = (FbrpCommonCodeType) this.codeTypeService.findFirstByName(codeTypeName);
		if (codeType == null) {
			return new ArrayList<FbrpCommonCodeValue>();
		}
		return this.findByCodeTypeId(codeType.getId());
	}

	/**
	 * 设置 codeTypeService。
	 * 
	 * @param dataCacheService
	 *            设置 dataCacheService。
	 */
	public void setDataCacheService(IDataCacheService dataCacheService) {
		this.dataCacheService = dataCacheService;
	}

	/**
	 * 设置 codeTypeService。
	 * 
	 * @param codeTypeService
	 *            设置 codeTypeService。
	 */
	public void setCodeTypeService(ICodeTypeService codeTypeService) {
		this.codeTypeService = codeTypeService;
	}

}
