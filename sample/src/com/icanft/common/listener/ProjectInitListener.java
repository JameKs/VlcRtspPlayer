/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.icanft.common.startup.ContextUtil;
import com.icanft.common.startup.LicenseColl;
import com.icanft.common.startup.ValidateLicense;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月24日
 */
public class ProjectInitListener extends ContextLoaderListener {

	HttpServletRequest request;
	
	private static final Logger log = Logger.getLogger(ProjectInitListener.class);

	private static ApplicationContext context;

	private static ServletContext servletContext;

	/**
	 * 返回ServletContext。
	 * 
	 * @return ServletContext
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * 返回Spring DI容器。
	 * 
	 * @return ApplicationContext
	 */
	public static ApplicationContext getContext() {
		return context;
	}
	
	/**
	 * 设置Spring DI容器。
	 * 
	 * @param ctx ApplicationContext
	 */
	public static void setContext(ApplicationContext ctx) {
		context = ctx;
	}


	public void contextInitialized(ServletContextEvent event) {
		System.out.println("==========系统初始化==========");
		final ServletContext servletContext = event.getServletContext();
		
		ProjectInitListener.servletContext = servletContext;
		
		super.contextInitialized(event);
		
		setContext(WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext()));
		
		ContextUtil.setApplicationContext(ProjectInitListener.context);

		// 保存FBRP应用的部署位置
		ContextUtil.setApplicationPath(event.getServletContext().getRealPath("/"));
		
		LicenseColl licenseList = null;
		try {
			licenseList = (LicenseColl) ProjectInitListener.getContext().getBean(
					"fbrp_licenseColl");
		} catch (Exception exe) {
			log.warn("查找License有误！");
		}
		if (licenseList != null) {
			boolean licenseIsValid = ValidateLicense.validateLicense(
					licenseList, ContextUtil.getApplicationPath());
			ContextUtil.put("licenseIsValid", licenseIsValid,
					ContextUtil.SCOPE_APPLICATION);
		} else {
			ContextUtil.put("licenseIsValid", true,
					ContextUtil.SCOPE_APPLICATION);
		}
	}
	

	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		System.out.println("==========初始化信息进行销毁==========");
	}
}
