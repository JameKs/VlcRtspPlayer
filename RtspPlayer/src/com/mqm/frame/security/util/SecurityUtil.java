/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mqm.frame.common.base.vo.FbrpInfraMenu;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.security.FbrpAdminGrantedAuthority;
import com.mqm.frame.security.FbrpGrantedAuthority;
import com.mqm.frame.security.acl.extendpoint.IGrantExtendPoint;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.security.function.vo.FbrpSecResFunction;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.util.IFbrpTree;

/**
 * 
 * <pre>
 * 认证及授权实用类。
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
public class SecurityUtil {

	/**
	 * 判断登录用户是否是超级管理员。
	 * 
	 * @return boolean 是否为超级管理员
	 */
	public static boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
		for (GrantedAuthority ga : grantedAuthorities) {
			if (ga instanceof FbrpAdminGrantedAuthority) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得授权角色。
	 * 
	 * @param grantService IGrantService
	 * 
	 * @return List
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(IGrantService grantService) {
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();

		if (grantedAuthorities == null) {
			return null;
		} else {
			UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",ContextUtil.SCOPE_SESSION);
			if (user != null && user.getGrantedAuthorties() == null) {
				List roleList = new ArrayList();
				Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
				while (iterator.hasNext()) {
					GrantedAuthority grantedAuthority = iterator.next();
					if (grantedAuthority instanceof FbrpSecRole) {
						FbrpSecRole fbrpSecRoleVO = (FbrpSecRole) grantedAuthority;
							roleList.add(fbrpSecRoleVO);
					}
				}
				grantList = getMenuandUrlandResource(grantService);
				List<GrantedAuthority> fbrpGrantedAuthorities = cacheGrantedAuthorityToUser(user,grantList);
				return fbrpGrantedAuthorities;
			} else if(user==null){
				return null;
			}else{
				return user.getGrantedAuthorties();
			}

		}
	}

	/**
	 * 隐藏授权给用户。
	 * 
	 * @param user UserProfileVO
	 * @param grantList List<? extends GrantedAuthority>
	 * 
	 * @return List<GrantedAuthority>
	 */
	public static List<GrantedAuthority> cacheGrantedAuthorityToUser(UserProfileVO user,
			List<? extends GrantedAuthority> grantList) {
		List<GrantedAuthority> fbrpGrantedAuthorities = new ArrayList<GrantedAuthority>();
		for (GrantedAuthority authority : grantList) {
			FbrpGrantedAuthority fbrpAuthority = new FbrpGrantedAuthority();
			if (authority instanceof FbrpInfraMenu) {
				fbrpAuthority.setId(((FbrpInfraMenu) authority).getId());
				fbrpAuthority.setGrantType(FbrpGrantedAuthority.MENU_GRANT_TYPE);
			} else if (authority instanceof FbrpSecResFunction) {
				fbrpAuthority.setId(((FbrpSecResFunction) authority).getCode());
				fbrpAuthority.setGrantType(FbrpGrantedAuthority.FUNCTION_GRANT_TYPE);
			} else if (authority instanceof IFbrpTree) {
				fbrpAuthority.setId(authority.getAuthority());
				fbrpAuthority.setGrantType(FbrpGrantedAuthority.RESOURCE_GRANT_TYPE);
			} else {
				// TODO:
			}
			fbrpGrantedAuthorities.add(fbrpAuthority);
		}
		if (user != null) {
			user.setGrantedAuthorties(fbrpGrantedAuthorities);
		}
		return fbrpGrantedAuthorities;
	}

	private static List getMenuandUrlandResource(IGrantService grantService) {

		List result = new ArrayList();

		//IMenuService menuService = (IMenuService) ContextUtil.getBean(IMenuService.BEAN_ID);
		
		IGrantExtendPoint grantExtendPoint=(IGrantExtendPoint)ContextUtil.getBean("fbrp_security_menuGrantedExtendPoint");
		
		List list = grantExtendPoint.getAll();

		if(!list.isEmpty()){
			result.addAll(list);
			//List relationURLs = menuService.findRelationURLByMenus(list);
			//result.addAll(relationURLs);
		}

		return result;
	}

	/**
	 * 获得所有权限扩展点。
	 * 
	 * @return Map
	 */
	public static Map<String, IGrantExtendPoint> getAllGrantExtendPoint() {
		Map<String, IGrantExtendPoint> map = new HashMap<String, IGrantExtendPoint>();
		List<IGrantExtendPoint> list = ContextUtil.getBeansByClass(IGrantExtendPoint.class);
		
		for (IGrantExtendPoint grantExtendPoint : list) {
			map.put(grantExtendPoint.getGrantName(),grantExtendPoint);
		}
		
//		IGrantConfigService configService = (IGrantConfigService)ContextUtil.getBean(IGrantConfigService.BEAN_ID);
//		List<IGrantExtendPoint> listConfigExtend=configService.getAllGrantedExtendPoints();
//		for (IGrantExtendPoint extendPoint : listConfigExtend) {
//			map.put(extendPoint.getGrantName(), extendPoint);
//		}

		return map;
	}

}
