/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.startup;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.icanft.common.ContextUtil;

/**
 * 
 * <pre>
 * 启动Spring DI容器的实现类，其扩展了ContextLoaderListener。
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
public class ContextInit extends ContextLoaderListener {

	private static final Log log = LogFactory.getLog(ContextInit.class);

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

	/**
	 * 初始化DI容器。
	 * 
	 * @param context ServletContextEvent
	 */
	public void contextInitialized(ServletContextEvent context) {

		final ServletContext servletContext = context.getServletContext();

		this.servletContext = servletContext;

		super.contextInitialized(context);

		// FBRP新增内容
		setContext(WebApplicationContextUtils
				.getRequiredWebApplicationContext(context.getServletContext()));
		ContextUtil.setApplicationContext(ContextInit.context);

		// 保存FBRP应用的部署位置
		ContextUtil.setApplicationPath(context.getServletContext().getRealPath(
				"/"));


		LicenseColl licenseList = null;
		try {
			licenseList = (LicenseColl) ContextInit.getContext().getBean(
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

	/**
	 * 销毁DI容器。
	 * 
	 * @param event ServletContextEvent
	 */
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		log.info("准备销毁Portlet容器");

		log.info("成功销毁Portlet容器");
		super.contextDestroyed(event);
	}

}