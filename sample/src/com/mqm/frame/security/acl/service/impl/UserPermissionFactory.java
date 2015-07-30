/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.model.Permission;

/**
 * <pre>
 * 自定义PermissionFactory实现类。
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
public class UserPermissionFactory extends ApplicationObjectSupport implements PermissionFactory {

	private static final Log log = LogFactory.getLog(UserPermissionFactory.class);

	private final Map<Integer, Permission> registeredPermissionsByInteger = new HashMap<Integer, Permission>();
	private final Map<String, Permission> registeredPermissionsByName = new HashMap<String, Permission>();

	/**
	 * Registers the <tt>Permission</tt> fields from the <tt>BasePermission</tt>
	 * class.
	 */
	public UserPermissionFactory() {
		registerPublicPermissions(BasePermission.class);
	}

	/**
	 * Registers the <tt>Permission</tt> fields from the supplied class.
	 * 
	 * @param permissionClass
	 *            Permission实现类
	 */
	public UserPermissionFactory(Class<? extends Permission> permissionClass) {
		registerPublicPermissions(permissionClass);
	}

	/**
	 * Registers a map of named <tt>Permission</tt> instances.
	 * 
	 * @param namedPermissions
	 *            the map of <tt>Permission</tt>s, keyed by name.
	 */
	public UserPermissionFactory(Map<String, ? extends Permission> namedPermissions) {
		for (Map.Entry<String, ? extends Permission> entry : namedPermissions.entrySet()) {
			String key = entry.getKey();
			Permission value = entry.getValue();
			registerPermission(value, key);
		}

		// for (String name : namedPermissions.keySet()) {
		// registerPermission(namedPermissions.get(name), name);
		// }
	}

	/**
	 * Registers the public static fields of type {@link Permission} for a give
	 * class.
	 * <p>
	 * These permissions will be registered under the name of the field. See
	 * {@link BasePermission} for an example.
	 * 
	 * @param clazz
	 *            a {@link Permission} class with public static fields to
	 *            register
	 */
	protected void registerPublicPermissions(Class<? extends Permission> clazz) {

		Field[] fields = clazz.getFields();

		for (int i = 0; i < fields.length; i++) {
			try {
				Object fieldValue = fields[i].get(null);

				if (Permission.class.isAssignableFrom(fieldValue.getClass())) {
					// Found a Permission static field
					Permission perm = (Permission) fieldValue;
					String permissionName = fields[i].getName();

					registerPermission(perm, permissionName);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	protected void registerPermission(Permission perm, String permissionName) {

		Integer mask = Integer.valueOf(perm.getMask());

		// Register the new Permission
		registeredPermissionsByInteger.put(mask, perm);
		registeredPermissionsByName.put(permissionName, perm);
	}

	/**
	 * 构建Permission。
	 * 
	 * @param mask
	 *            int
	 * 
	 * @return Permission
	 * 
	 */
	public Permission buildFromMask(int mask) {
		if (registeredPermissionsByInteger.containsKey(Integer.valueOf(mask))) {
			// The requested mask has an exact match against a
			// statically-defined Permission, so return it
			return registeredPermissionsByInteger.get(Integer.valueOf(mask));
		}

		// To get this far, we have to use a CumulativePermission
		CumulativePermission permission = new CumulativePermission();

		for (int i = 0; i < 32; i++) {
			int permissionToCheck = 1 << i;

			if ((mask & permissionToCheck) == permissionToCheck) {
				Permission p = registeredPermissionsByInteger.get(Integer.valueOf(permissionToCheck));

				if (p == null) {
					throw new IllegalStateException("Mask '" + permissionToCheck
							+ "' does not have a corresponding static Permission");
				}
				permission.set(p);
			}
		}

		return permission;
	}

	/**
	 * 构建Permission。
	 * 
	 * @param name
	 *            String
	 * 
	 * @return Permission
	 * 
	 */
	public Permission buildFromName(String name) {
		Permission p = registeredPermissionsByName.get(name);

		if (p == null) {
			throw new IllegalArgumentException("Unknown permission '" + name + "'");
		}

		return p;
	}

	/**
	 * 构建Permission。
	 * 
	 * @param names
	 *            List<String>
	 * 
	 * @return List<Permission>
	 * 
	 */
	public List<Permission> buildFromNames(List<String> names) {
		if ((names == null) || (names.size() == 0)) {
			return Collections.emptyList();
		}

		List<Permission> permissions = new ArrayList<Permission>(names.size());

		for (String name : names) {
			permissions.add(buildFromName(name));
		}

		return permissions;
	}

	/**
	 * 初始化。
	 */
	public void init() {
		Map<String, Permission> map = this.getApplicationContext().getBeansOfType(Permission.class);
		if (map != null && !map.isEmpty()) {
			for (Permission permission : map.values()) {
				if (log.isDebugEnabled()) {
					log.debug("加载系统中自定义的权限类型...." + permission);
				}
				registerPublicPermissions(permission.getClass());
			}
		}
	}

}
