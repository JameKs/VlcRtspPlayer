/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月28日
 */
public interface IServletProxyHandler {

	public abstract void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

	public abstract void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

}