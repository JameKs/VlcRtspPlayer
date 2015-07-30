/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * <pre>
 * 首页frame.jsp中session失效后，重新跳到login.jsp中，避免页面出现错误信息。
 * </pre>
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FbrpSessionTimeoutFilter extends GenericFilterBean {

	private static final Log log = LogFactory
			.getLog(FbrpSessionTimeoutFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI();

		if (url.contains("/frame/frame.jsp")) {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (authentication == null) {
				HttpServletResponse response = (HttpServletResponse) rep;
				response.sendRedirect("login.jsp");
				return;
			}
		}

		try {
			chain.doFilter(req, rep);
		} catch (Exception e) {
			if (url.contains("login.jsp")) {
				HttpServletResponse response = (HttpServletResponse) rep;
				response.sendRedirect("login.jsp");
				return;
			}
			log.error("", e);
		}
	}

}
