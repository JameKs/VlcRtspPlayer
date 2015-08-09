/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class WebUtil {

	/**
	 * 默认的构造方法。
	 */
	public WebUtil() {
	}

	/**
	 * 设置属性。
	 * 
	 * @param request HttpServletRequest
	 * @param values Map
	 */
	public static void setAttributes(HttpServletRequest request, Map values) {
		if (request == null || values == null) {
			return;
		}
		java.util.Map.Entry entry;
		for (Iterator entrys = values.entrySet().iterator(); entrys.hasNext(); request
				.setAttribute(entry.getKey().toString(), entry.getValue())) {
			entry = (java.util.Map.Entry) entrys.next();
		}

	}
}