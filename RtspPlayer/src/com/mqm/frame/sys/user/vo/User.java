package com.mqm.frame.sys.user.vo;

import java.io.Serializable;
import java.util.Date;

import com.mqm.frame.common.DefaultVO;

/**
 * @author Administrator
 *
 */
public class User extends DefaultVO implements Serializable {

	private static final long serialVersionUID = -433577178466098132L;
	
	/** 常量。*/
	public static final String STATUS_INVALIDATE = "0";
	/** 常量。*/
	public static final String STATUS_VALIDATE = "1";
	/** 常量。*/
	public static final String STATUS_DEFAULT = STATUS_VALIDATE;
	
	/** 常量。*/
	public static final int EXPIRE_DAYS_NEVER_EXPIRE = -1;

	private String loginId;
	
	private String password;

	private String userName;

	private String bmDm;

	private String phone;
	
	private String email;
	
	private String status;

	private String active;
	
	private int sessionOutTime ;
	
	private String gwId ;
	
	private String deptId ;
	
	private Integer failedLoginCount;
	
	private Integer maxFailedLoginCount;
	
	private Date passwdExpireTime;
	
	private Integer passwdExpireDays;
	
	private Date accountExpireTime;
	
	private String loginIp;
	
	private Date loginTime;
	
	private String lastLoginIp;
	
	private Date lastLoginTime;
	
	private Integer onlineCount;

	private String locked;
	
	private String delFlag;

	/**
	 * @return 返回 loginId。
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId 设置 loginId。
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return 返回 password。
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password 设置 password。
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 返回 userName。
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName 设置 userName。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return 返回 bmDm。
	 */
	public String getBmDm() {
		return bmDm;
	}

	/**
	 * @param bmDm 设置 bmDm。
	 */
	public void setBmDm(String bmDm) {
		this.bmDm = bmDm;
	}

	/**
	 * @return 返回 phone。
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone 设置 phone。
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return 返回 email。
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email 设置 email。
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 返回 status。
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status 设置 status。
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 返回 active。
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active 设置 active。
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return 返回 sessionOutTime。
	 */
	public int getSessionOutTime() {
		return sessionOutTime;
	}

	/**
	 * @param sessionOutTime 设置 sessionOutTime。
	 */
	public void setSessionOutTime(int sessionOutTime) {
		this.sessionOutTime = sessionOutTime;
	}

	/**
	 * @return 返回 gwId。
	 */
	public String getGwId() {
		return gwId;
	}

	/**
	 * @param gwId 设置 gwId。
	 */
	public void setGwId(String gwId) {
		this.gwId = gwId;
	}

	/**
	 * @return 返回 deptId。
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId 设置 deptId。
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the failedLoginCount
	 */
	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	/**
	 * @param failedLoginCount the failedLoginCount to set
	 */
	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	/**
	 * @return the maxFailedLoginCount
	 */
	public Integer getMaxFailedLoginCount() {
		return maxFailedLoginCount;
	}

	/**
	 * @param maxFailedLoginCount the maxFailedLoginCount to set
	 */
	public void setMaxFailedLoginCount(Integer maxFailedLoginCount) {
		this.maxFailedLoginCount = maxFailedLoginCount;
	}

	/**
	 * @return the passwdExpireTime
	 */
	public Date getPasswdExpireTime() {
		return passwdExpireTime;
	}

	/**
	 * @param passwdExpireTime the passwdExpireTime to set
	 */
	public void setPasswdExpireTime(Date passwdExpireTime) {
		this.passwdExpireTime = passwdExpireTime;
	}

	/**
	 * @return the passwdExpireDays
	 */
	public Integer getPasswdExpireDays() {
		return passwdExpireDays;
	}

	/**
	 * @param passwdExpireDays the passwdExpireDays to set
	 */
	public void setPasswdExpireDays(Integer passwdExpireDays) {
		this.passwdExpireDays = passwdExpireDays;
	}

	/**
	 * @return the accountExpireTime
	 */
	public Date getAccountExpireTime() {
		return accountExpireTime;
	}

	/**
	 * @param accountExpireTime the accountExpireTime to set
	 */
	public void setAccountExpireTime(Date accountExpireTime) {
		this.accountExpireTime = accountExpireTime;
	}

	/**
	 * @return the loginIp
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * @param loginIp the loginIp to set
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the onlineCount
	 */
	public Integer getOnlineCount() {
		return onlineCount;
	}

	/**
	 * @param onlineCount the onlineCount to set
	 */
	public void setOnlineCount(Integer onlineCount) {
		this.onlineCount = onlineCount;
	}

	/**
	 * @return the locked
	 */
	public String getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(String locked) {
		this.locked = locked;
	}

	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
