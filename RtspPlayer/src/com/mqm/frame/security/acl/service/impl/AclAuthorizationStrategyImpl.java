/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.security.acl.extendpoint.IPrincipalType;

/**
 * 
 * <pre>
 * 实现了Spring Security的AclAuthorizationStrategy接口。
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
public class AclAuthorizationStrategyImpl implements AclAuthorizationStrategy {

	@Override
	public void securityCheck(Acl acl, int changeType) {
		if ((SecurityContextHolder.getContext() == null)
				|| (SecurityContextHolder.getContext().getAuthentication() == null)
				|| !SecurityContextHolder.getContext().getAuthentication()
						.isAuthenticated()) {
			throw new AccessDeniedException(
					"Authenticated principal required to operate with ACLs");
		}

		Map<String, IPrincipalType> map = ContextUtil.getApplicationContext()
				.getBeansOfType(IPrincipalType.class);

		List list = new ArrayList();

		for (IPrincipalType principalType : map.values()) {
			list.addAll(principalType.getCurrentPrincipals());
		}
		List sids = new ArrayList();
		for (Object object : list) {
			Sid currentprincipal = new PrincipalSid(
					(String) new ObjectIdentityImpl(object).getIdentifier());
			sids.add(currentprincipal);
			if (currentprincipal.equals(acl.getOwner())
					&& ((changeType == CHANGE_GENERAL) || (changeType == CHANGE_OWNERSHIP))) {
				return;
			}
		}
		if (acl.isGranted(Arrays.asList(BasePermission.ADMINISTRATION), sids,
				false)) {
			return;
		}
		throw new AccessDeniedException(
				"Principal does not have required ACL permissions to perform requested operation");
	}

}
