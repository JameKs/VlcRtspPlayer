/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.vo;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * <pre>
 * 用户信息装载类。
 * </pre>
 * 
 * @author lijiawei lijiawei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class StaffUumVO extends ValueObject {

	private static final long serialVersionUID = -4278445697277862742L;

	private String userId;	//用户ID这个是系统自己产生的
	@NotBlank(message = "用户登录账号不能为空")
	private String accountName; //用户登录账号
	@NotBlank(message = "用户名不能为空")
	private String userName; //用户名
	private String fullName; //用户全称
	private Date birthday; //生日
	private String sex; //性别
	@Length(max = 18, message = "长度不能超过18位")
	@Pattern(regexp="^((([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|(3[0-1]))\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|(3[0-1]))\\d{3}(\\d|X|x)))*)$", message="身份证号码格式不正确")
	private String idNo; //身份证号码
	@Length(max=128, message="长度不能超过128位")
	@Pattern(regexp = "^(((\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*))*)$", message = "邮件地址格式不正确")
	private String email; //邮件地址
	@NotBlank(message = "税务部门代码不能为空")
	private String orgCode; //管理机构代码
	private String officephone; //办公电话
	@Length(max=12,message="长度不能超过12位")
	@Pattern(regexp = "^(\\d*)$", message = "只能输入数字")
	private String mobile; //移动电话
	private String description; //描述

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
