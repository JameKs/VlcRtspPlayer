/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * <pre>
 * 标识超级管理员权限。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpAdminGrantedAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = 1099647228883807202L;
	
	/**
	 * 标识超级管理员权限。
	 */
	public static final String ADMIN = "ADMIN";

	/**
	 * 返回超级管理员权限。
	 * 
	 * @return String
	 */
	public String getAuthority() {
		return ADMIN;
	}

}
