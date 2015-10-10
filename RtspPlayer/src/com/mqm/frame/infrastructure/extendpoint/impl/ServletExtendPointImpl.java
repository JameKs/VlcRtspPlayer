/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.extendpoint.impl;

import java.util.Map;

import com.mqm.frame.infrastructure.extendpoint.IServletExtendPoint;
import com.mqm.frame.infrastructure.service.IServletProxyHandler;

/**
 * <pre>
 * Title: Servlet扩展点实现。
 * Description: 程序功能的描述。
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
public class ServletExtendPointImpl implements IServletExtendPoint {

	// Servlet列表
	private Map<String, IServletProxyHandler> servletConfigInfoList;

	/**
	 * 设置Servlet列表。
	 * 
	 * @param servletConfigInfoList Map<String, IServletProxyHandler>
	 */
	public void setServletProxyHandlerDefinitionsInfo(
			Map<String, IServletProxyHandler> servletConfigInfoList) {
		this.servletConfigInfoList = servletConfigInfoList;
	}

	/**
	 * 获得IServletProxyHandler定义集合。
	 * 
	 * @return 返回IServletProxyHandler定义集合
	 */
	public Map<String, IServletProxyHandler> getServletProxyHandlerDefinitionsInfo() {
		return servletConfigInfoList;
	}

}
