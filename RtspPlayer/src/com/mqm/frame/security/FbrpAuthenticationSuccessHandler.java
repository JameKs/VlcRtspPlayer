/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.mqm.frame.common.base.service.IMenuService;
import com.mqm.frame.common.base.vo.FbrpInfraMenu;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.security.service.IUserDetailsService;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.exception.FbrpException;

/**
 * <pre>
 * 自定义SimpleUrlAuthenticationSuccessHandler实现类。
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
public class FbrpAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private static final Log log = LogFactory
			.getLog(FbrpAuthenticationSuccessHandler.class);

	private IUserDetailsService userDetailsService;

	private IMenuService menuService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		this.onSuccessfulAuthentication(request, response, authentication);
		String psw = authentication.getCredentials().toString();
		ContextUtil.put(BaseConstants.PASSWD, psw, ContextUtil.SCOPE_SESSION);

		String defaultURL = this.getDefaultTargetUrl();
		if (!"".equals(request.getParameter("appId"))) {
			if ("/".equals(defaultURL)) {
				defaultURL = "/frame/frame.jsp";
			} else if (defaultURL != null
					&& defaultURL.startsWith("/frame/frame.jsp")) {
				defaultURL = "/frame/frame.jsp";
			}
			this.setDefaultTargetUrl(defaultURL);
		} else {
			if (defaultURL != null && defaultURL.startsWith("/frame/frame.jsp")) {
				defaultURL = "/";
			}
			this.setDefaultTargetUrl(defaultURL);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	/**
	 * 用户登陆成功后,调用IUserDetailsService类的onSuccessfulAuthentication方法,
	 * 做一些成功后的操作,比如把一些登陆信息保存数据库。
	 */
	protected void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult) {
		try {
			if (userDetailsService != null) {
				userDetailsService.onSuccessfulAuthentication(request,
						response, authResult.getPrincipal().toString());
			}
		} catch (FbrpException e) {
			log.error("", e);
		}
	}

	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		if (user != null) {
			String appId = request.getParameter("appId");
			if (!StringUtil.isEmpty(appId)) {
				user.setCurrentAppId(appId);
				String url = extractTargetUrl(request);
				if (!StringUtil.isEmpty(url)) {
					// 该逻辑为通过FBRP对外接口访问FBRP指定资源的页面才会触发：
					// 认证过程中，必须是带有appId，并且是非本地数据库认证
					// 并带有targetType和targetId两个参数才有可能做自定义的跳转
					getRedirectStrategy().sendRedirect(request, response, url);
					return;
				}
			}
		}
		super.handle(request, response, authentication);
	}

	/**
	 * 从请求对象中查找目标url,供后续跳转使用。
	 * 
	 * @param request
	 * 
	 * @return String 目标url
	 */
	private String extractTargetUrl(HttpServletRequest request) {
		String targetType = request.getParameter("targetType");
		String targetId = request.getParameter("targetId");
		String url = null;
		String uri = (String) request.getAttribute("fromTargetUri");
		if ("gateway".equals(uri) && StringUtil.isEmpty(targetType)) {
			targetType = "menu";
		}
		if ("menu".equals(targetType)) {
			FbrpInfraMenu menu = menuService.find(targetId);
			if (menu != null && !StringUtil.isEmpty(menu.getUrl())) {
				url = menu.getUrl();
			}
		} else if ("report".equals(targetType)) {
			// TODO xiaocheng_lu 完成查看报表时的链接
		} else if ("main".equals(targetType)) {
			// TODO xiaocheng_lu 完成查看完整页面的链接
		}
		return url;
	}

	/**
	 * 获取userDetailsService。
	 * 
	 * @return IUserDetailsService
	 */
	public IUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * 设置userDetailsService。
	 * 
	 * @param userDetailsService
	 *            IUserDetailsService
	 */
	public void setUserDetailsService(IUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * 设置menuService。
	 * 
	 * @param menuService
	 *            IMenuService
	 */
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

}
