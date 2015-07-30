/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import com.mqm.frame.security.service.IUserDetailsService;
import com.mqm.frame.util.exception.FbrpException;

/**
 * <pre>
 * 自定义SimpleUrlAuthenticationFailureHandler实现类。
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
public class FbrpAuthenticationFailureHandler extends
		ExceptionMappingAuthenticationFailureHandler {

	private static final Log log = LogFactory
			.getLog(FbrpAuthenticationFailureHandler.class);

	private IUserDetailsService userDetailsService;

	private Map<String, String> failureUrlMap;
	
	/**
	 * 构建FbrpAuthenticationFailureHandler。
	 */
	public FbrpAuthenticationFailureHandler(){
		this.failureUrlMap = new HashMap();
	}
	
	/**
	 * 初始化异常注册信息。
	 * 
	 * @param failureUrlMap 异常与URL映射信息。
	 */
	@Override
	public void setExceptionMappings(Map<?, ?> failureUrlMap) {
	    this.failureUrlMap.clear();
	    for (Map.Entry entry : failureUrlMap.entrySet()) {
	      Object exception = entry.getKey();
	      Object url = entry.getValue();
	      this.failureUrlMap.put((String)exception, (String)url);
	    }
	}

	/**
	 * 错误处理。
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param exception AuthenticationException
	 * 
	 * @throws IOException IOException
	 * @throws ServletException ServletException 
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		this.onUnsuccessfulAuthentication(request, response, exception);
	    String url = (String)this.failureUrlMap.get(exception.getClass().getName());
	    if (url != null){
			//设置APPID，否则该信息丢失了
			String appId = request.getParameter("appId");
			if(appId!=null && !"".equals(appId)){
				url += "&appId="+appId;
			}
	        getRedirectStrategy().sendRedirect(request, response, url);
	    }else{
	    	super.onAuthenticationFailure(request, response, exception);
	    }
	}

	/**
	 * 登陆失败后,调用IUserDetailsService类的onUnsuccessfulAuthentication方法,登记用户失败次数等信息。
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param exception AuthenticationException
	 */
	protected void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception) {
		try {
			//if (userDetailsService != null && exception.getAuthentication() != null) {
			if (userDetailsService != null) {
				//String loginUser = exception.getAuthentication().getPrincipal().toString();
				String loginUser = exception.getLocalizedMessage();
				userDetailsService.onUnsuccessfulAuthentication(request,
						response, loginUser);
			}
		} catch (FbrpException e) {
			log.error("", e);
		}
	}

	/**
	 * 返回IUserDetailsService。
	 * 
	 * @return IUserDetailsService IUserDetailsService
	 */
	public IUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * 设置IUserDetailsService。
	 * 
	 * @param userDetailsService IUserDetailsService
	 */
	public void setUserDetailsService(IUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
