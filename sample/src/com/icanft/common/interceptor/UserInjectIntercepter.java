/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.icanft.xtgl.yhgl.vo.User;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author meihu meihu@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class UserInjectIntercepter extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof UserAware) {
			User user = (User) request.getSession().getAttribute("user");
			UserAware userAware = (UserAware)handler;
			userAware.setUser(user);
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
