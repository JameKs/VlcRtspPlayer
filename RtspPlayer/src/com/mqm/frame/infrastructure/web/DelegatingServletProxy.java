/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.infrastructure.service.IServletProxyHandler;
import com.mqm.frame.infrastructure.startup.ContextInit;

/**
 * <pre>
 * Title:FBRP引入的Servlet代理。
 * Description: 用来模块化Servlet。
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
@SuppressWarnings("unchecked")
public class DelegatingServletProxy extends HttpServlet {

	private static final long serialVersionUID = -3645799492896744769L;

	private static final Log log = LogFactory
			.getLog(DelegatingServletProxy.class);

	private Map<String, IServletProxyHandler> servletProxyHandlerMap = new HashMap<String, IServletProxyHandler>();

	@Override
	public void init() throws ServletException {
		// 查找IServletProxyHandler集合
		this.servletProxyHandlerMap = (Map<String, IServletProxyHandler>) ContextInit
				.getContext().getBean(
						"fbrp_infrastructure_servletProxyHandlerDefinitionsInfo");
		log.info("被委派的IServletProxyHandler集合:" + this.servletProxyHandlerMap);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		IServletProxyHandler servletProxyHandler = this
				.queryRightHttpServlet(req);
		servletProxyHandler.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		IServletProxyHandler servletProxyHandler = this
				.queryRightHttpServlet(req);
		servletProxyHandler.doPost(req, resp);
	}

	private IServletProxyHandler queryRightHttpServlet(
			HttpServletRequest httpServletRequest) {
		//为了支持这种情况：/ajaxengine/portal|getPage 需要首先将问号后面的内容去掉。需要在前端jsp中在写servlet路径时候注意遵守该规则
		String servletKey = httpServletRequest.getPathInfo();
		if(servletKey.indexOf("|")!=-1){
			servletKey = servletKey.substring(0,servletKey.indexOf("|"));
		}
		Object obj = this.servletProxyHandlerMap.get(servletKey);
		//为了支持在配置扩展点的servletProxyHandlerDefinitionsInfo时候，配置entry key=如果加了/servletproxy，那么需要添加如下代码以支持
		if(obj==null){
			servletKey = httpServletRequest.getServletPath()  +  servletKey;
			obj = this.servletProxyHandlerMap.get(servletKey);
		}
		return (IServletProxyHandler)obj;		
	}

}
