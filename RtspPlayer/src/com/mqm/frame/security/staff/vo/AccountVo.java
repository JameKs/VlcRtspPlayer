/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.vo;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author liuyaping liuyaping@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class AccountVo extends ValueObject{
	private String name;
	private String loginid;
	private String password;
	private String passwdExpireDays;
	private Integer failedLoginCount;
	private String locked;
	private String passwdExpireTime;
	
	/**
	 * 获取name。
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 name。
	 *
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取loginid。
	 *
	 * @return String
	 */
	public String getLoginid() {
		return loginid;
	}
	/**
	 * 设置 loginid。
	 *
	 * @param loginid String
	 */
	public void setLoginid(String loginid) {
		this.loginid = loginid;
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
	 * 设置 password。
	 *
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取passwdExpireDays。
	 *
	 * @return String
	 */
	public String getPasswdExpireDays() {
		return passwdExpireDays;
	}
	/**
	 * 设置 passwdExpireDays。
	 *
	 * @param passwdExpireDays String
	 */
	public void setPasswdExpireDays(String passwdExpireDays) {
		this.passwdExpireDays = passwdExpireDays;
	}
	/**
	 * 获取failedLoginCount。
	 *
	 * @return Integer
	 */
	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}
	/**
	 * 设置 failedLoginCount。
	 *
	 * @param failedLoginCount Integer
	 */
	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}
	/**
	 * 获取locked。
	 *
	 * @return String
	 */
	public String getLocked() {
		return locked;
	}
	/**
	 * 设置 locked。
	 *
	 * @param locked String
	 */
	public void setLocked(String locked) {
		this.locked = locked;
	}
	/**
	 * 获取passwdExpireTime。
	 *
	 * @return String
	 */
	public String getPasswdExpireTime() {
		return passwdExpireTime;
	}
	/**
	 * 设置 passwdExpireTime。
	 *
	 * @param passwdExpireTime String
	 */
	public void setPasswdExpireTime(String passwdExpireTime) {
		this.passwdExpireTime = passwdExpireTime;
	}
}
