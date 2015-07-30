/**
 * Role.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jsgl.vo;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

import com.icanft.common.DefaultVo;

/**
 * @author Administrator
 *
 */
public class Role extends DefaultVo implements GrantedAuthority, Serializable {

	/**
	 * $
	 */
	private static final long serialVersionUID = 7786360563457077309L;

	private String code;
	
	private String name;
	
	private String bz;
	
	private String status;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bz
	 */
	public String getBz() {
		return bz;
	}

	/**
	 * @param bz the bz to set
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.name;
	}

	
}
