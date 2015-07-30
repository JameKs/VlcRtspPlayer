/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.vo;

import java.util.Date;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author lijiawei  lijiawei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class StaffOAVO extends ValueObject {

	private static final long serialVersionUID = 3254115945958832097L;
	
	private String logID;
	private String logTime;
	private String actionType;
	// userInfo
	private String userID; // userId
	private String accountName; // 用户登录账号
	private String username; // 用户名
	private String fullName;
	private String userCode;
	private String email;
	private String orgID;
	private String orgCode;
	private String dutyID;
	private String dutyCode;
	private String leaderID;
	private String idNO;
	private Date birthday;
	private String mailSize;
	private String serviceLevel;
	private String order;
	private String status;
	private Date create;
	private String createrID;
	private String createrCode;
	private Date freezetime;
	private Date pwdUpdate;
	private Date lastLogin;
	private Date lastModify;
	private String description;
	private String officephone;
	private String mobile;
	private String userLevel;
	private String sex;
	private String internalCode;
	
	private String fromOrgId;
	private String fromOrgCode;
	
	private String orgName;
	private String fromOrgName;
	
	private String flag;

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDutyID() {
		return dutyID;
	}

	public void setDutyID(String dutyID) {
		this.dutyID = dutyID;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getLeaderID() {
		return leaderID;
	}

	public void setLeaderID(String leaderID) {
		this.leaderID = leaderID;
	}

	public String getIdNO() {
		return idNO;
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMailSize() {
		return mailSize;
	}

	public void setMailSize(String mailSize) {
		this.mailSize = mailSize;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public String getCreaterID() {
		return createrID;
	}

	public void setCreaterID(String createrID) {
		this.createrID = createrID;
	}

	public String getCreaterCode() {
		return createrCode;
	}

	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}

	public Date getFreezetime() {
		return freezetime;
	}

	public void setFreezetime(Date freezetime) {
		this.freezetime = freezetime;
	}

	public Date getPwdUpdate() {
		return pwdUpdate;
	}

	public void setPwdUpdate(Date pwdUpdate) {
		this.pwdUpdate = pwdUpdate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOfficephone() {
		return officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getSex() {
		if((sex == null) || ("null".equals(sex))) {
			sex = "";
		}
		return sex;
	}

	public void setSex(String sex) {
		if((sex == null) || ("null".equals(sex))) {
			sex = "";
		}
		this.sex = sex;
	}

	public String getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}

	public String getFromOrgId() {
		return fromOrgId;
	}

	public void setFromOrgId(String fromOrgId) {
		this.fromOrgId = fromOrgId;
	}

	public String getFromOrgCode() {
		return fromOrgCode;
	}

	public void setFromOrgCode(String fromOrgCode) {
		this.fromOrgCode = fromOrgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFromOrgName() {
		return fromOrgName;
	}

	public void setFromOrgName(String fromOrgName) {
		this.fromOrgName = fromOrgName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
