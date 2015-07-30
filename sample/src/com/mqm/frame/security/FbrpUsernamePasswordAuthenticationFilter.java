/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.security.exception.CaptchaAuthenticationException;
import com.mqm.frame.security.exception.LicenseException;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * 
 * <pre>
 * Spring Security的用户登陆验证处理的继承类,用于定义用户登陆成功或者失败后操作相应。
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
public class FbrpUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private AuthenticationProvider fbrpDaoAuthenticationProvider;
	
	private IStaffService staffService;
	
	private LdapContext ctx = null;

    @SuppressWarnings("rawtypes")
	private Hashtable env;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		// 登录前做license验证,如通过则继续进行用户名密码验证,否则抛出异常。
		boolean licenseIsValid = (Boolean) ContextUtil.get("licenseIsValid",
				ContextUtil.SCOPE_APPLICATION);

		if (!licenseIsValid) {
			throw new LicenseException("License过期！");
		}
		// 调用Captcha校验
		this.onPreAuthentication(request, response);

		// 供FbrpDaoAuthenticationProvider使用
		String appId = this.obtainAppId(request);
		if (!StringUtil.isEmpty(appId)) {
			ContextUtil.put(BaseConstants.APPID, appId,
					ContextUtil.SCOPE_SESSION);
		}
		
		String username = this.obtainUsername(request);
		String password = this.obtainPassword(request);
		
		if(this.toValid(username, password)) {
			password = this.staffService.selectPassword(username);
		}
		this.CloseCtx();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		return this.fbrpDaoAuthenticationProvider.authenticate(authRequest);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean toValid(String uid,String password){
		env = new Hashtable();
   	 	Properties prop = new Properties();
   	 	try {
			prop.load(this.getClass().getResourceAsStream("ldapconfig.properties"));
		} catch (IOException e) {
			System.out.println("Properties load faild: " + e.toString());
			return false;
		}
        env.put(Context.INITIAL_CONTEXT_FACTORY, prop.getProperty("gdlt.ldap.INITIAL_CONTEXT_FACTORY"));
        env.put(Context.PROVIDER_URL, prop.getProperty("gdlt.ldap.PROVIDER_URL"));
        env.put(Context.SECURITY_AUTHENTICATION, prop.getProperty("gdlt.ldap.SECURITY_AUTHENTICATION"));
        env.put(Context.SECURITY_PRINCIPAL, "uid="+uid+",ou=People,dc=gdlt");
        env.put(Context.SECURITY_CREDENTIALS, password);
        //如果此处不指定用户名和密码，则自动转为匿名登录
        try {
            ctx = new InitialLdapContext(env, null);
        } catch (javax.naming.AuthenticationException e) {
        	System.out.println("Authentication faild: " + e.toString());
        	return false;
        } catch (Exception e) {
            System.out.println("Something wrong while authenticating: " + e.toString());
            return false;
        }
        return true;
   }
	
	public void CloseCtx() {
        if (ctx != null) {
            try {
                ctx.close();
                System.out.println("closed ctx now");
            } catch (NamingException e) {
                System.out.println("Exception" + e.toString());
            }
        }
    }

	/**
	 * 处理Captcha校验。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	private void onPreAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		String image = request.getParameter("j_captcha_response");
		String rand = (String) request.getSession().getAttribute("Rand");
		if (rand != null && !rand.equals(image)) {
			throw new CaptchaAuthenticationException(image);
		}
	}

	protected String obtainAppId(HttpServletRequest request) {
		return request.getParameter("appId");
	}

	/**
	 * 设置FbrpDaoAuthenticationProvider。
	 * 
	 * @param fbrpDaoAuthenticationProvider AuthenticationProvider
	 */
	public void setFbrpDaoAuthenticationProvider(
			AuthenticationProvider fbrpDaoAuthenticationProvider) {
		this.fbrpDaoAuthenticationProvider = fbrpDaoAuthenticationProvider;
	}

	public void setStaffService(IStaffService staffService) {
		this.staffService = staffService;
	}

}
