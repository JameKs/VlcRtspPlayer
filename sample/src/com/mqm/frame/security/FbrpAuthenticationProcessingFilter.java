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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * 
 * <pre>
 * FBRP认证处理过滤器。
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
public class FbrpAuthenticationProcessingFilter extends
		AbstractAuthenticationProcessingFilter {

	private AbstractAuthenticationProcessingFilter fbrpUsernamePasswordAuthenticationFilter;

	/**
	 * 构建器。
	 */
	public FbrpAuthenticationProcessingFilter() {
		super("/fbrpauthenticationprocessingfilter");
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {
		return this.fbrpUsernamePasswordAuthenticationFilter
				.attemptAuthentication(httpServletRequest, httpServletResponse);
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		ContextUtil.remove(BaseConstants.APPID, ContextUtil.SCOPE_SESSION);
		ContextUtil.remove(BaseConstants.USER_PROFILE,
				ContextUtil.SCOPE_SESSION);
		ContextUtil.remove(BaseConstants.MENU_TYPE, ContextUtil.SCOPE_SESSION);

		this.fbrpUsernamePasswordAuthenticationFilter.doFilter(servletRequest,
				servletResponse, filterChain);
	}

	/**
	 * 设置 fbrpUsernamePasswordAuthenticationFilter。
	 * 
	 * @param fbrpUsernamePasswordAuthenticationFilter
	 *            设置 fbrpUsernamePasswordAuthenticationFilter。
	 */
	public void setFbrpUsernamePasswordAuthenticationFilter(
			AbstractAuthenticationProcessingFilter fbrpUsernamePasswordAuthenticationFilter) {
		this.fbrpUsernamePasswordAuthenticationFilter = fbrpUsernamePasswordAuthenticationFilter;
	}

	/**
	 * 返回 fbrpUsernamePasswordAuthenticationFilter。
	 * 
	 * @return 返回 fbrpUsernamePasswordAuthenticationFilter。
	 */
	public AbstractAuthenticationProcessingFilter getFbrpUsernamePasswordAuthenticationFilter() {
		return fbrpUsernamePasswordAuthenticationFilter;
	}

}
