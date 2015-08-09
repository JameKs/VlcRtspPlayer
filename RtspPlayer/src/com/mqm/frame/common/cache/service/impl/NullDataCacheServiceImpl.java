/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.cache.service.impl;

import java.io.Serializable;

import com.mqm.frame.common.cache.service.IDataCacheService;

/**
 * <pre>
 * 不缓存任何数据。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class NullDataCacheServiceImpl implements IDataCacheService {

	@Override
	public void putDataToCache(String keyId, Serializable data) {
	}

	@Override
	public Serializable getDataFromCache(String keyId) {
		return null;
	}

	@Override
	public void removeDataFromCache(String keyId) {
	}

}
