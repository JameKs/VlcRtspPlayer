/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.voter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import com.mqm.frame.common.base.service.IMenuService;
import com.mqm.frame.common.base.vo.FbrpInfraMenu;
import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.security.FbrpGrantedAuthority;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.security.service.IUrlVoterExtender;
import com.mqm.frame.security.util.SecurityUtil;

/**
 * 
 * <pre>
 * URL投票器。
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
public class UrlVoter extends DefaultServiceImpl implements AccessDecisionVoter<Object> {

	private static final Log log = LogFactory.getLog(UrlVoter.class);

	// 是否启用当前投票器
	private boolean enabled = true;

	private List<String> uncheckUrlList;

	private List<String> urlListExceptSupAdmin;

	private IGrantService grantService;

	private IMenuService menuService;

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class clazz) {
		return clazz.isAssignableFrom(FilterInvocation.class);
	}

	@Override
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
		if (!enabled) {
			return ACCESS_GRANTED;
		}
		boolean flag = this.grantService.isAdmin(this.getUser().getLoginId());
		if(flag){
			//TODO luxiaocheng@foresee.cn 回头再想办法控制“系统管理员”的业务权限
			return ACCESS_GRANTED;
		}
		for (ConfigAttribute config : attributes) {
			String url = config.getAttribute();
			for (String uncheckUrl : uncheckUrlList) {
				if (url.indexOf(uncheckUrl) != -1) {
					return ACCESS_GRANTED;
				}
			}
		}
		List<GrantedAuthority> grantedAuthorities = SecurityUtil.getGrantedAuthorities(this.grantService);
		for (ConfigAttribute config : attributes) {
			String url = config.getAttribute();
			for (String excepetUrl : urlListExceptSupAdmin) {
				if (url.indexOf(excepetUrl) != -1 && !flag) {
					return ACCESS_ABSTAIN;
				}
			}
			if (voteUrl(url, grantedAuthorities)) {
				return ACCESS_GRANTED;
			}else {
				return ACCESS_ABSTAIN;
			}
		}
		List extenders = ContextUtil.getBeansByClass(IUrlVoterExtender.class);
		if (extenders.size() > 0) {
			for (Iterator it = extenders.iterator(); it.hasNext();) {
				if (((IUrlVoterExtender) it.next()).isAccessable(attributes
						.iterator())) {
					return ACCESS_GRANTED;
				}
			}
		}

		return ACCESS_ABSTAIN;
	}

	private boolean voteUrl(String url, List<GrantedAuthority> grantList) {
		if(log.isDebugEnabled()){
			log.debug("正被投票的URL：" + url);
		}
		if(grantList==null){
			return false;
		}
		for (GrantedAuthority grantedAuthority : grantList) {
			FbrpGrantedAuthority fbrpGrantedAuthority = (FbrpGrantedAuthority) grantedAuthority;
			if (FbrpGrantedAuthority.MENU_GRANT_TYPE.equals(fbrpGrantedAuthority.getGrantType())) {
				FbrpInfraMenu grantedMenu = this.menuService.findWithCache(fbrpGrantedAuthority.getId());
				if (grantedMenu != null && grantedMenu.getUrl() != null&& (url.startsWith(grantedMenu.getUrl()))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 针对超级管理员进行URL例外控制。
	 * 
	 * @param urlListExceptSupAdmin List<String>
	 */
	public void setUrlListExceptSupAdmin(List<String> urlListExceptSupAdmin) {
		this.urlListExceptSupAdmin = urlListExceptSupAdmin;
	}

	/**
	 * 设置IMenuService服务。
	 * 
	 * @param menuService IMenuService
	 */
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	/**
	 * 设置IGrantService服务。
	 * 
	 * @param grantService IGrantService
	 */
	public void setGrantService(IGrantService grantService) {
		this.grantService = grantService;
	}

	/**
	 * 设置例外URL。
	 * 
	 * @param uncheckUrlList List<String>
	 */
	public void setUncheckUrlList(List<String> uncheckUrlList) {
		this.uncheckUrlList = uncheckUrlList;
	}

	/**
	 * 是否启用本投票器。
	 * 
	 * @return boolean
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 设置是否启用。
	 * 
	 * @param enabled boolean
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
