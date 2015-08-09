/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityGenerator;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.security.acl.extendpoint.IPrincipalType;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * 实现Spring Security内置的PermissionEvaluator接口。
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
public class AclPermissionEvaluator implements PermissionEvaluator {

	private static final Log log = LogFactory
			.getLog(AclPermissionEvaluator.class);

	private MutableAclService aclService;
	private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
	private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();
	private PermissionFactory permissionFactory;

	private IGrantService grantService;

	/**
	 * 构建器。
	 * 
	 * @param aclService
	 *            MutableAclService
	 */
	public AclPermissionEvaluator(MutableAclService aclService) {
		this.aclService = aclService;
	}

	/**
	 * Determines whether the user has the given permission(s) on the domain
	 * object using the ACL configuration. If the domain object is null, returns
	 * false (this can always be overridden using a null check in the expression
	 * itself).
	 * 
	 * @param authentication
	 *            Authentication
	 * @param domainObject
	 *            Object
	 * @param permission
	 *            Object
	 * 
	 * @return boolean
	 */
	public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
		if (domainObject == null) {
			return false;
		}
		ObjectIdentity objectIdentity = this.objectIdentityRetrievalStrategy.getObjectIdentity(domainObject);

		boolean isgrant = checkPermission(authentication, objectIdentity, permission);
		return isgrant;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		ObjectIdentity objectIdentity = this.objectIdentityGenerator
				.createObjectIdentity(targetId, targetType);
		return checkPermission(authentication, objectIdentity, permission);
	}

	private boolean checkPermission(Authentication authentication,
			ObjectIdentity oid, Object permission) {
		UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		if (this.grantService.isAdmin(user.getLoginId())) {
			return true;
		}

		// 若当前登录用户是应用的管理员,如果资源对象没有在Acl中注册，则注册，并默认给管理员授权。
		/*
		 * if (user.isAppAdmin()) { Acl acl = this.aclService.readAclById(oid);
		 * if (acl == null) { String principal =
		 * grantService.getAppAdminRole().getId();
		 * this.grantService.insertGrant((String)
		 * oid.getIdentifier(),oid.getType(), 16, principal); } }
		 */
		// 获取当前用户所拥有的SID
		Map<String, IPrincipalType> map = ContextUtil.getApplicationContext()
				.getBeansOfType(IPrincipalType.class);
		List<ValueObject> list = new ArrayList();
		for (IPrincipalType principalType : map.values()) {
			list.addAll(principalType.getCurrentPrincipals());
		}
		List sids = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Sid sid = new PrincipalSid(list.get(i).getId());
			sids.add(sid);
		}
		List<Permission> requiredPermission = this.resolvePermission(permission);
		try {
			// 通过数据资源查找SIDS对应的权限
			Acl acl = this.aclService.readAclById(oid, sids);
			// 验证是否授权
			if (acl != null && acl.isGranted(requiredPermission, sids, false)) {
				if (log.isDebugEnabled()) {
					log.debug("Access is granted");
				}
				return true;
			}
			if (log.isDebugEnabled()) {
				log.debug("Returning false - ACLs returned, but insufficient permissions for this principal");
			}
		} catch (NotFoundException nfe) {
			if (log.isDebugEnabled()) {
				log.debug("Returning false - no ACLs apply for this principal");
			}
		}
		return false;
	}

	private List<Permission> resolvePermission(Object permission) {
		if (permission instanceof Integer) {
			return Arrays.asList(this.permissionFactory
					.buildFromMask(((Integer) permission).intValue()));
		}

		if (permission instanceof Permission) {
			return Arrays.asList((Permission) permission);
		}

		if (permission instanceof Permission[]) {
			return Arrays.asList((Permission[]) permission);
		}

		if (permission instanceof String) {
			String permString = (String) permission;
			Permission p = null;

			try {
				p = this.permissionFactory.buildFromName(permString);
			} catch (IllegalArgumentException notfound) {
				p = this.permissionFactory
						.buildFromName(InternationalizationUtil
								.toUpperCase(permString));
			}

			if (p != null) {
				return Arrays.asList(p);
			}

		}
		throw new IllegalArgumentException("Unsupported permission: "
				+ permission);
	}

	/**
	 * 设置ObjectIdentityRetrievalStrategy。
	 * 
	 * @param objectIdentityRetrievalStrategy
	 *            ObjectIdentityRetrievalStrategy
	 */
	public void setObjectIdentityRetrievalStrategy(
			ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
		this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
	}

	/**
	 * 设置ObjectIdentityGenerator。
	 * 
	 * @param objectIdentityGenerator
	 *            ObjectIdentityGenerator
	 */
	public void setObjectIdentityGenerator(
			ObjectIdentityGenerator objectIdentityGenerator) {
		this.objectIdentityGenerator = objectIdentityGenerator;
	}

	/**
	 * 设置PermissionFactory。
	 * 
	 * @param permissionFactory
	 *            PermissionFactory
	 */
	public void setPermissionFactory(PermissionFactory permissionFactory) {
		this.permissionFactory = permissionFactory;
	}

	/**
	 * 设置IGrantService。
	 * 
	 * @param grantService
	 *            IGrantService
	 */
	public void setGrantService(IGrantService grantService) {
		this.grantService = grantService;
	}

}
