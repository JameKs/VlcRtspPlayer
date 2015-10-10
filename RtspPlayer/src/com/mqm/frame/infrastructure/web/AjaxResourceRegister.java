/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.web;

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
public class AjaxResourceRegister {
	
	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "ajaxResourceRegister";
	
	private Map<String,String> map = new HashMap<String,String>();
	
	/**
	 * 设置map。
	 * 
	 * @param map 设置map。
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	/**
	 * 返回map中的取值。
	 * 
	 * @param path String
	 * 
	 * @return String
	 */
	public String get(String path){
		return map.get(path);
	}
	 
}
