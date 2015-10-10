/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

import com.mqm.frame.infrastructure.messages.AbstractMessageResources;

/**
 * 
 * <pre>
 * 消息辅助类。
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
public abstract class FbrpMessages {
	
	private static AbstractMessageResources msgResources = null;

	/**
	 * 获取默认Locale下的消息(不带参数)。
	 * 
	 * @param key String
	 * 
	 * @return String
	 */
	public static String get(String key) {
		return get(key, null, null);
	}

	/**
	 * 获取指定Locale下的消息(不带参数)。
	 * 
	 * @param key String
	 * @param locale Locale
	 * @return String
	 */
	public static String get(String key, Locale locale) {
		return get(key, null, locale);
	}

	/**
	 * 获取默认Locale下的消息(带参数)。
	 * 
	 * @param key String
	 * @param args Object[]
	 * @return String
	 */
	public static String get(String key, Object[] args) {
		return get(key,args,null);
	}

	/**
	 * 获取指定Locale下的消息(带参数)。
	 * 
	 * @param key String
	 * @param args Object[]
	 * @param locale Locale
	 * 
	 * @return String
	 */
	public static String get(String key, Object[] args, Locale locale) {
		if (msgResources == null) {
			ApplicationContext cxt = ContextUtil.getApplicationContext();
			Object bean = cxt.getBean("messageSourceProxy");
			/*if(bean==null){
				throw new FbrpException("无法找到命名为\"messageSourceProxy\"的Spring受管Bean!");
			}*/
			msgResources = (AbstractMessageResources) bean;
		}
		return msgResources.getMessage(key, args, locale);
	}

}
