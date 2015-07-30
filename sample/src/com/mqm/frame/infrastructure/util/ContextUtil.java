/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

/**
 * 
 * <pre>
 * Spring DI容器辅助类。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ContextUtil {

	private static final Log log = LogFactory.getLog(ContextUtil.class);

	/** HttpSession范围。 */
	public static final String SCOPE_SESSION = "fbrp_infrastructure_sessionContextHolder";
	/** 请求范围。 */
	public static final String SCOPE_REQUEST = "fbrp_infrastructure_requestContextHolder";
	/** DI容器范围。 */
	public static final String SCOPE_APPLICATION = "fbrp_infrastructure_applicationContextHolder";

	//缓存的DI容器
	private static ApplicationContext applicationContext;
	
	//FBRP应用的部署位置
	private static String applicationPath;

	/**
	 * 返回DI容器。
	 * 
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 设置DI容器。
	 * 
	 * @param applicationContext ApplicationContext
	 */
	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		ContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 设置 applicationPath。
	 * 
	 * @param applicationPath 设置 applicationPath。
	 */
	public static void setApplicationPath(String applicationPath) {
		ContextUtil.applicationPath = applicationPath;
	}
	
	/**
	 * 返回 applicationPath。
	 * 
	 * @return 返回 applicationPath。
	 */
	public static String getApplicationPath() {
		return applicationPath;
	}
	
	/**
	 * 查询Spring受管Bean。
	 * 
	 * @param beanId String
	 * @return Object
	 */
	public static Object getBean(String beanId) {
		if (beanId == null) {
			return null;
		}
		if (beanId.equalsIgnoreCase("null")) {
			return null;
		} else {
			return getBeanByBeanIdOrClass(beanId, null);
		}
	}

	/**
	 * 查询Spring受管Bean。
	 * 
	 * @param targetClass Class
	 * 
	 * @return List
	 */
	public static List getBeansByClass(Class targetClass) {
		List ret = new ArrayList();
		if (applicationContext == null || targetClass == null) {
			return ret;
		}
		Map beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
				applicationContext, targetClass, true, true);
		if (beans.size() == 0) {
			log.info((new StringBuilder()).append("No bean of type ").append(
					targetClass.toString()).append(" found.").toString());
		}
		return new ArrayList(beans.values());
	}

	/**
	 * 查询受管Bean。
	 * 
	 * @param beanId String
	 * @param clazz Class
	 * 
	 * @return Object
	 */
	public static Object getBeanByBeanIdOrClass(String beanId, Class clazz) {
		if (applicationContext == null) {
			return null;
		}
		if ("null".equalsIgnoreCase(beanId)) {
			return null;
		}
		if (beanId != null && applicationContext.containsBean(beanId)) {
			return applicationContext.getBean(beanId);
		}
		List l = getBeansByClass(clazz);
		if (l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询消息资源。
	 * 
	 * @param property String
	 * 
	 * @return String
	 */
	public static String getMessage(String property){
		ApplicationContext applicationContext = ContextUtil.getApplicationContext();
		try{
			String message = applicationContext.getMessage(property, null, null);
			return message;
		} catch(Exception ex){
			log.error("", ex);
			return null;
		}
	}
	
	/**
	 * 往ContextHolder存储内容。
	 * 
	 * @param key String
	 * @param value Object
	 * @param scopeBeanId String
	 */
	public static void put(String key, Object value, String scopeBeanId) {
		ContextHolder contextHolder = (ContextHolder) getBean(scopeBeanId);
		if (contextHolder != null) {
			contextHolder.put(key, value);
		}
	}

	/**
	 * 从ContextHolder查询内容。
	 * 
	 * @param key String
	 * @param scopeBeanId String
	 * @return Object
	 */
	public static Object get(String key, String scopeBeanId) {
		ContextHolder contextHolder = (ContextHolder) getBean(scopeBeanId);
		if (contextHolder != null) {
			return contextHolder.get(key);
		} else {
			return null;
		}
	}

	/**
	 * 销毁ContextHolder中的所有内容。
	 * 
	 * @param scopeBeanId String
	 */
	public static void clear(String scopeBeanId) {
		ContextHolder contextHolder = (ContextHolder) getBean(scopeBeanId);
		if (contextHolder != null) {
			contextHolder.clear();
		}
	}

	/**
	 * 删除ContextHolder中的某个内容。
	 * 
	 * @param key String
	 * @param scopeBeanId scopeBeanId
	 */
	public static void remove(String key, String scopeBeanId) {
		ContextHolder contextHolder = (ContextHolder) getBean(scopeBeanId);
		if (contextHolder != null) {
			contextHolder.remove(key);
		}
	}
	
}