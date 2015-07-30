/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luoshifei  luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ContextHolder {
	
	private Map contexts;

	/**
	 * 默认的构造方法。
	 */
	public ContextHolder() {
		contexts = new HashMap();
	}

	/**
	 * 根据key获取value。
	 * 
	 * @param key String
	 * 
	 * @return Object
	 */
	public Object get(String key) {
		return contexts.get(key);
	}

	/**
	 * 设置。
	 * 
	 * @param key String
	 * @param value Object
	 */
	public void put(String key, Object value) {
		contexts.put(key, value);
	}

	/**
	 * 清空Map。
	 */
	public void clear() {
		contexts.clear();
	}

	/**
	 * 移除某个键值对。
	 * 
	 * @param key String
	 */
	public void remove(String key) {
		contexts.remove(key);
	}
	
/*	public String getInfo(){
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, Object>> ents = contexts.entrySet();
		for(Map.Entry ent : ents){
			sb.append(ent.getKey()).append(":").append(ent.getValue()).append("<br/>\n");
		}
		return sb.toString();
	}*/
}