/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * <pre>
 * 动态调用各种AuthenticationEntryPoint提供者。
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
public class FbrpAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private AuthenticationEntryPoint loginUrlAuthenticationEntryPoint;

	@Override
	public void commence(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			AuthenticationException authenticationException)
			throws IOException, ServletException {
		this.loginUrlAuthenticationEntryPoint.commence(httpServletRequest,
				httpServletResponse, authenticationException);
	}

	/**
	 * 设置AuthenticationEntryPoint。
	 * 
	 * @param loginUrlAuthenticationEntryPoint
	 *            设置 loginUrlAuthenticationEntryPoint。
	 */
	public void setLoginUrlAuthenticationEntryPoint(
			AuthenticationEntryPoint loginUrlAuthenticationEntryPoint) {
		this.loginUrlAuthenticationEntryPoint = loginUrlAuthenticationEntryPoint;
	}

}
