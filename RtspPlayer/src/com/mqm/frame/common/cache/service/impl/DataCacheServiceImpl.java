/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.cache.service.impl;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.mqm.frame.common.cache.service.IDataCacheService;
import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;

/**
 * <pre>
 * 借助EhCache实现数据缓存服务。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DataCacheServiceImpl extends DefaultServiceImpl implements IDataCacheService, InitializingBean{

    private static final Log log = LogFactory.getLog(DataCacheServiceImpl.class);

    private Cache cache;
        
    /**
     * 将数据存储到缓存中。
     *  
     * @param keyId 标识数据
     * @param data 将被缓存的数据
     */
    public void putDataToCache(String keyId, Serializable data){
        if (log.isTraceEnabled()) {
            log.trace("Cache put: " + keyId);
        }
        Element element = new Element(keyId, data);

        this.cache.put(element);
    }

    /**
     * 从缓存获得数据。
     *  
     * @param keyId 标识数据
     * 
     * @return Serializable
     */
    public Serializable getDataFromCache(String keyId){
        if (log.isTraceEnabled()) {
            log.trace("Cache get: " + keyId);
        }

        Element element = this.cache.get(keyId);

        if (element == null) {
            return null;
        } else {
            return element.getValue();
        }
    }
    
    /**
     * 删除缓存里的内容。
     * 
     * @param keyId String
     */
    public void removeDataFromCache(String keyId){
        if (log.isTraceEnabled()) {
            log.trace("Cache remove: " + keyId);
        }
    	
        Element element = this.cache.get(keyId);
        if(element != null){
            this.cache.remove(keyId);
        }
    }
     
    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.cache == null){
            throw new IllegalArgumentException("未配置缓存");  
        }                    
    }

    /**
     * 设置cache。
     * 
     * @param cache Cache
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    
    /**
     * 返回cache。
     * 
     * @return Cache
     */
    public Cache getCache() {
		return cache;
	}
    
}
