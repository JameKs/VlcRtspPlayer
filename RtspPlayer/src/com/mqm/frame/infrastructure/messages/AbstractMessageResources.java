/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.messages;

import java.util.Locale;

/**
 * <pre>
 * 程序的中文名称。
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
public abstract class AbstractMessageResources {
	
	/**
	 * 获取信息。
	 * 
	 * @param key String
	 * @return String
	 */
	public String getMessage(String key) {
		return getMessage(key, Locale.getDefault());
	}

	/**
	 * 获取信息。
	 * 
	 * @param key String
	 * @param parameter Object
	 * 
	 * @return String
	 */
	public String getMessage(String key, Object parameter) {
		return getMessage(key, new Object[] { parameter }, null,Locale.getDefault());
	}

	/**
	 * 获取信息。
	 * 
	 * @param key String
	 * @param parameters Object[]
	 * 
	 * @return String
	 */
	public String getMessage(String key, Object[] parameters) {
		return getMessage(key, parameters, Locale.getDefault());
	}

	/**
	 * 获取信息。
	 * 
	 * @param key String
	 * @param parameters  Object[]
	 * @param locale Locale
	 * 
	 * @return String
	 */
	public String getMessage(String key, Object[] parameters, Locale locale) {
		return getMessage(key, parameters, null, locale);
	}

	/**
	 * 获取信息。
	 * 
	 * @param key String
	 * @param locale Locale
	 * @return String
	 */
	public String getMessage(String key, Locale locale) {
		return getMessage(key, null, null, locale);
	}

	/**
	 * 获取信息。
	 * 
	 * @param key  String
	 * @param parameter Object
	 * @param locale Locale
	 * @return String
	 */
	public String getMessage(String key, Object parameter, Locale locale) {
		return getMessage(key, new Object[] { parameter }, null, locale);
	}

	/**
	 * 获取信息。
	 * 
	 * @param key String
	 * @param parameters  Object[]
	 * @param defaultMessage String
	 * @param locale Locale
	 * @return String
	 */
	public abstract String getMessage(String key, Object[] parameters,String defaultMessage, Locale locale);
}
