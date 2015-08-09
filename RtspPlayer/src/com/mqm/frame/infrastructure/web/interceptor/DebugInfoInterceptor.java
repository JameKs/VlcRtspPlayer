/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DebugInfoInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Long s = System.currentTimeMillis();
		request.setAttribute("fbrpRequestStartTimeMillis", s);
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Long s = (Long)request.getAttribute("fbrpRequestStartTimeMillis");
		Long sub = System.currentTimeMillis() - s;
		request.setAttribute("fbrpRequestTimeMillis", sub);
		super.postHandle(request, response, handler, modelAndView);
	}

}
