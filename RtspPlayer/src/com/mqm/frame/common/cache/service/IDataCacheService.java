/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.cache.service;

import java.io.Serializable;

/**
 * <pre>
 * 提供数据缓存服务。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IDataCacheService {
	
	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_common_dataCacheService";

    /**
     * 将数据存储到缓存中。
     *  
     * @param keyId 标识数据
     * @param data Serializable
     */
    public void putDataToCache(String keyId, Serializable data);

    /**
     * 从缓存获得数据。
     *  
     * @param keyId 标识数据
     * 
     * @return Serializable
     */
    public Serializable getDataFromCache(String keyId);

    /**
     * 删除缓存的数据。
     * 
     * @param keyId String
     */
    public void removeDataFromCache(String keyId);

}
