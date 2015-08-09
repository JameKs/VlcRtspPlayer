/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.infrastructure.service.IServletProxyHandler;
import com.mqm.frame.infrastructure.util.ContextUtil;

/**
 * 
 * <pre>
 * 分发/servlet/ajaxengine开头的请求路径。
 * 如：/servlet/ajaxengine/portalLayoutAction/load?pid=-1。
 * 将使用Spring受管bean id 为portalLayoutAction的类，并调用其load方法。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class AjaxEngineServlet implements IServletProxyHandler {
	
	private static final long serialVersionUID = 4781466826634895060L;
	
	private static final Log log = LogFactory.getLog(AjaxEngineServlet.class);
	
	private static final String servletKey = "/ajaxengine/portal|";
	/**
	 * 区配请求路径，并且可用于找出actionName和methodName。
	 */
	//private static final Pattern URI_PATTERN = Pattern.compile(".*?/servlet/ajaxengine/([^/]+?)/([^/]+)");
	private static final Pattern URI_PATTERN = Pattern.compile("^([^/]+?)/([^/]+)$");

	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String uri = request.getPathInfo();
		//uri = uri.substring(request.getContextPath().length()+"/servlet/ajaxengine".length());
		//更改了前端jsp中的servlet处理方式，故需要作出特别的处理
		int pos = uri.indexOf(servletKey);
		if(pos!=-1){
			uri = "/portal/"+ uri.substring(pos + servletKey.length());
		}

		AjaxResourceRegister reg = (AjaxResourceRegister)ContextUtil.getBean(AjaxResourceRegister.BEAN_ID);
		String value = reg.get(uri);
		if(value==null){
			//TODO 返回错误信息:404
			return;
		}
		Matcher m = URI_PATTERN.matcher(value);

		if(m.find()){
			String actionName = m.group(1);
			String methodName = m.group(2);
			Object bean = ContextUtil.getBean(actionName);
			if (bean instanceof AjaxBaseAction) {
				AjaxBaseAction base = (AjaxBaseAction) bean;
				base.init(request, response);
				try {
					Method method = base.getClass().getMethod(methodName);
					method.invoke(base);
				} catch (Exception e) {
					log.error("", e);
				} 
			}
			
		}else{
			//TODO 返回错误信息：配置不正确
			return;
		}
		
		
		
		/*if (m.find()) {
			String actionName = m.group(1);
			String methodName = m.group(2);
			try {
				Object obj = ContextUtil.getBean(actionName);
				if (obj != null) {
					Class<?> actionClass = obj.getClass();
					if (obj instanceof AjaxBaseAction) {
						AjaxBaseAction base = (AjaxBaseAction) obj;
						base.init(request, response);
						Method method = actionClass.getMethod(methodName);
						method.invoke(obj);
					}
					
					Method initMethod = actionClass.getMethod("init", HttpServletRequest.class,
							HttpServletResponse.class);
					initMethod.invoke(obj, request, response);
					
				}

			} catch (Exception e) {
				log.error("", e);
			}
		}*/

	}

}
