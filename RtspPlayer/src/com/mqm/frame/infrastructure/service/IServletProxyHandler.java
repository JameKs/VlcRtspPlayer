/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 处理HttpServlet请求。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IServletProxyHandler {

	/**
	 * 处理GET请求。
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

	/**
	 * 处理POST请求。
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

}
