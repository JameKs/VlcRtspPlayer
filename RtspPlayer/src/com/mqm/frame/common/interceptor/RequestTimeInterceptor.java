/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.mqm.frame.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <pre>
 * 计算每次请求耗时
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月24日
 */
public class RequestTimeInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		Long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		return super.preHandle( request, response, handler);
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		Long startTime = (Long)request.getAttribute("startTime");
		Long requestTime = System.currentTimeMillis() - startTime;
		request.setAttribute("requestTime", requestTime);
		super.postHandle(request,  response,  handler,  modelAndView);
	}

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}



}
