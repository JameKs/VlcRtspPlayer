package com.icanft.jssq.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.icanft.cdgl.vo.Cdxx;

/**
 * @author Administrator
 *
 */
public class CdxxRole extends Cdxx implements Serializable {

	private static final long serialVersionUID = -433577178466098132L;

	private String gxId;
	
	private String roleId;

	/**
	 * @return the cdId
	 */
	public String getGxId() {
		return gxId;
	}

	/**
	 * @param cdId the cdId to set
	 */
	public void setGxId(String gxId) {
		this.gxId = gxId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
}
