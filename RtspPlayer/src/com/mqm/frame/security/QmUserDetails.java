/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * <pre>
 * Spring Security UserDetails实现类。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class QmUserDetails implements UserDetails {

	private static final long serialVersionUID = -2490337027645328924L;

	private String password;
	private String username;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	private List<GrantedAuthority> authorities;


	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		if (authorities != null) {
			return Collections.unmodifiableList(authorities);
		}
		return AuthorityUtils.NO_AUTHORITIES;
	}

	/**
	 * 设置password。
	 * 
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 设置username。
	 * 
	 * @param username String
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 设置accountNonExpired。
	 * 
	 * @param accountNonExpired boolean
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * 设置accountNonLocked。
	 * 
	 * @param accountNonLocked boolean
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * 设置credentialsNonExpired。
	 * 
	 * @param credentialsNonExpired boolean
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * 设置enabled。
	 * 
	 * @param enabled boolean
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 设置authorities。
	 * 
	 * @param authorities List
	 */
	public void setAuthorities(List authorities) {
		this.authorities = authorities;
	}

	/**
	 * 获取password。
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 获取username。
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 获取accountNonExpired。
	 * 
	 * @return boolean
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * 获取accountNonLocked。
	 * 
	 * @return boolean
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * 获取credentialsNonExpired。
	 * 
	 * @return boolean
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * 获取enabled。
	 * 
	 * @return boolean
	 */
	public boolean isEnabled() {
		return enabled;
	}

}
