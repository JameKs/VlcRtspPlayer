/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.security.org.vo.FbrpSecOrg;

/**
 * 
 * <pre>
 * 关联表FBRP_SEC_STAFF的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpSecStaff extends ValueObject { 

	private static final long serialVersionUID = -3762064006054242950L;

	/** 常量。*/
	public static final String STATUS_INVALIDATE = "0";
	/** 常量。*/
	public static final String STATUS_VALIDATE = "1";
	/** 常量。*/
	public static final String STATUS_DEFAULT = STATUS_VALIDATE;
	
	/** 常量。*/
	public static final int EXPIRE_DAYS_NEVER_EXPIRE = -1;
	
	
	// fields
	@Length(max=32,message="长度不能超过32位")
	private java.lang.String code;
	@NotEmpty(message="必填")
	@Length(max=64,message="长度不能超过64位")
	private java.lang.String name;
	private java.lang.String status;
	@Length(max=18,message="长度不能超过18位")
	@Pattern(regexp="^()|([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4})$", message="格式不正确")
	private java.lang.String idNumber;
	private java.lang.String birthday;
	private java.lang.String sex;
	@Length(max=50,message="长度不能超过50位")
	@Pattern(regexp = "^()|([0-9-]*)$", message = "只能输入——和数字")
	private java.lang.String tel;
	@Length(max=12,message="长度不能超过12位")
	@Pattern(regexp = "^()|([0-9-]*)$", message = "只能输入数字")
	private java.lang.String mobileTel;
	@Length(max=128,message="长度不能超过128位")
	@Pattern(regexp = "^()|(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)$", message = "格式不正确")
	private java.lang.String email;
	@Length(max=128,message="长度不能超过128位")
	private java.lang.String address;

	private String loginid;
	private String passwd;
	private Integer failedLoginCount;
	private Integer maxFailedLoginCount;
	private Date passwdExpireTime;
	@Max(value=99999,message="数值超出范围，最多可设置99999天,相当于永不过期")
	private Integer passwdExpireDays;
	private Date accountExpireTime;
	private String loginIp;
	private Date loginTime;
	private String lastLoginIp;
	private Date lastLoginTime;
	private Integer onlineCount;

	private String locked;
	
	private java.lang.String ext1;
	private java.lang.String ext2;
	private java.lang.String ext3;
	private java.lang.String ext4;
	private java.lang.String ext5;
	private java.lang.String ext6;
	private java.lang.String ext7;
	private java.lang.String ext8;
	private java.lang.String ext9;

	private java.lang.String delFlag;
	
	//非数据库表字段
	private String orgid;
	private String orgName;
	private String roleid;
	private String roleName;
	private String orgCode;
	
	//TODO xiaocheng_lu 机构信息，稍后看是否可以将上面的orgid等字段去掉
	private FbrpSecOrg org;

	//用户姓名
	private String staffName;
	
	private String relationType;
	private String creatorId;
	private  Date createdTime;
	private String  lastModifierId;
	private  Date  lastModifiedTime;
	private  boolean  checked;

	/**
	 * 无参构造器。
	 */
	public FbrpSecStaff() {
		
	}
	
	/**
	 * 自定义构造器。
	 * 
	 * @param id String
	 */
	public FbrpSecStaff(String id) {
	    super.setId(id);
	}
	/**
	 *  返回 staffName。
	 * 
	 * @return String
	 */
	@Transient
	public String getStaffName() {
		return staffName;
	}

	/**
	 * 设置 staffName。
	 * 
	 * @param staffName  String
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}


	/**
	 * Return the value associated with the column: CODE.
	 * 
	 * @return String
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: CODE.
	 * 
	 * @param code the CODE value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
	}



	/**
	 * Return the value associated with the column: NAME.
	 * 
	 * @return String
	 */
	@Column(name="NAME")
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: NAME.
	 * 
	 * 
	 * @param name the NAME value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: STATUS.
	 * 
	 * @return String
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: STATUS.
	 * 
	 * @param status the STATUS value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: IDNUMBER.
	 * 
	 * @return String
	 */
	@Column(name="ID_NUMBER")
	public java.lang.String getIdNumber () {
		return idNumber;
	}

	/**
	 * Set the value related to the column: IDNUMBER.
	 * 
	 * @param idNumber the IDNUMBER value
	 */
	public void setIdNumber (java.lang.String idNumber) {
		this.idNumber = idNumber;
	}



	/**
	 * Return the value associated with the column: BIRTHDAY.
	 * 
	 *@return String
	 */
	public java.lang.String getBirthday () {
		return birthday;
	}

	/**
	 * Set the value related to the column: BIRTHDAY.
	 * 
	 * @param birthday the BIRTHDAY value
	 */
	public void setBirthday (java.lang.String birthday) {
		this.birthday = birthday;
	}



	/**
	 * Return the value associated with the column: SEX.
	 * 
	 * @return String
	 */
	public java.lang.String getSex () {
		return sex;
	}

	/**
	 * Set the value related to the column: SEX.
	 * 
	 * @param sex the SEX value
	 */
	public void setSex (java.lang.String sex) {
		this.sex = sex;
	}



	/**
	 * Return the value associated with the column: TEL.
	 * 
	 * @return String
	 */
	public java.lang.String getTel () {
		return tel;
	}

	/**
	 * Set the value related to the column: TEL.
	 * 
	 * @param tel the TEL value
	 */
	public void setTel (java.lang.String tel) {
		this.tel = tel;
	}



	/**
	 * Return the value associated with the column: EMAIL.
	 * 
	 * @return String
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: EMAIL.
	 * 
	 * @param email the EMAIL value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: ADDRESS.
	 * 
	 * @return String
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: ADDRESS.
	 * 
	 * @param address the ADDRESS value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}



	/**
	 * Return the value associated with the column: EXT1.
	 * 
	 * @return String
	 */
	public java.lang.String getExt1 () {
		return ext1;
	}

	/**
	 * Set the value related to the column: EXT1.
	 * 
	 * @param ext1 the EXT1 value
	 */
	public void setExt1 (java.lang.String ext1) {
		this.ext1 = ext1;
	}



	/**
	 * Return the value associated with the column: EXT2.
	 * 
	 * @return String
	 */
	public java.lang.String getExt2 () {
		return ext2;
	}

	/**
	 * Set the value related to the column: EXT2.
	 * 
	 * @param ext2 the EXT2 value
	 */
	public void setExt2 (java.lang.String ext2) {
		this.ext2 = ext2;
	}



	/**
	 * Return the value associated with the column: EXT3.
	 * 
	 * @return String
	 */
	public java.lang.String getExt3 () {
		return ext3;
	}

	/**
	 * Set the value related to the column: EXT3.
	 * 
	 * @param ext3 the EXT3 value
	 */
	public void setExt3 (java.lang.String ext3) {
		this.ext3 = ext3;
	}



	/**
	 * Return the value associated with the column: EXT4.
	 * 
	 * @return String
	 */
	public java.lang.String getExt4 () {
		return ext4;
	}

	/**
	 * Set the value related to the column: EXT4.
	 * 
	 * @param ext4 the EXT4 value
	 */
	public void setExt4 (java.lang.String ext4) {
		this.ext4 = ext4;
	}



	/**
	 * Return the value associated with the column: EXT5.
	 * 
	 * @return String
	 */
	public java.lang.String getExt5 () {
		return ext5;
	}

	/**
	 * Set the value related to the column: EXT5.
	 * 
	 * @param ext5 the EXT5 value
	 */
	public void setExt5 (java.lang.String ext5) {
		this.ext5 = ext5;
	}



	/**
	 * Return the value associated with the column: EXT6.
	 * 
	 * @return String
	 */
	public java.lang.String getExt6 () {
		return ext6;
	}

	/**
	 * Set the value related to the column: EXT6.
	 * 
	 * @param ext6 the EXT6 value
	 */
	public void setExt6 (java.lang.String ext6) {
		this.ext6 = ext6;
	}



	/**
	 * Return the value associated with the column: EXT7.
	 * 
	 * @return String
	 */
	public java.lang.String getExt7 () {
		return ext7;
	}

	/**
	 * Set the value related to the column: EXT7.
	 * 
	 * @param ext7 the EXT7 value
	 */
	public void setExt7 (java.lang.String ext7) {
		this.ext7 = ext7;
	}



	/**
	 * Return the value associated with the column: EXT8.
	 * 
	 * @return String
	 */
	public java.lang.String getExt8 () {
		return ext8;
	}

	/**
	 * Set the value related to the column: EXT8.
	 * 
	 * @param ext8 the EXT8 value
	 */
	public void setExt8 (java.lang.String ext8) {
		this.ext8 = ext8;
	}



	/**
	 * Return the value associated with the column: EXT9.
	 * 
	 * @return String
	 */
	public java.lang.String getExt9 () {
		return ext9;
	}

	/**
	 * Set the value related to the column: EXT9.
	 * 
	 * @param ext9 the EXT9 value
	 */
	public void setExt9 (java.lang.String ext9) {
		this.ext9 = ext9;
	}

	
	
	/**
	 * 返回 loginid。
	 * 
	 * @return  String
	 */
	@Column(name="LOGIN_ID")
	public String getLoginid() {
		return loginid;
	}

	/**
	 * 设置 loginid。
	 * 
	 * @param loginid  String
	 */
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	/**
	 * 返回 passwd。
	 * 
	 * @return  String
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * 设置 passwd。
	 * 
	 * @param passwd  String
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 返回 failedLoginCount。
	 * 
	 * @return  Integer
	 */
	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	/**
	 * 设置 failedLoginCount。
	 * 
	 * @param failedLoginCount  Integer
	 */
	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	/**
	 * 返回 maxFailedLoginCount。
	 * 
	 * @return  Integer
	 */
	public Integer getMaxFailedLoginCount() {
		return maxFailedLoginCount;
	}

	/**
	 * 设置 maxFailedLoginCount。
	 * 
	 * @param maxFailedLoginCount Integer
	 */
	public void setMaxFailedLoginCount(Integer maxFailedLoginCount) {
		this.maxFailedLoginCount = maxFailedLoginCount;
	}

	/**
	 * 返回 passwdExpireTime。
	 * 
	 * @return  Date
	 */
	public Date getPasswdExpireTime() {
		return passwdExpireTime;
	}

	/**
	 * 设置 passwdExpireTime。
	 * 
	 * @param passwdExpireTime  Date
	 */
	public void setPasswdExpireTime(Date passwdExpireTime) {
		this.passwdExpireTime = passwdExpireTime;
	}

	/**
	 * 返回 passwdExpireDays。
	 * 
	 * @return  Integer
	 */
	public Integer getPasswdExpireDays() {
		return passwdExpireDays;
	}

	/**
	 * 设置 passwdExpireDays。
	 * 
	 * @param passwdExpireDays Integer
	 */
	public void setPasswdExpireDays(Integer passwdExpireDays) {
		this.passwdExpireDays = passwdExpireDays;
	}

	/**
	 * 返回 accountExpireTime。
	 * 
	 * @return  Date
	 */
	public Date getAccountExpireTime() {
		return accountExpireTime;
	}

	/**
	 * 设置 accountExpireTime。
	 * 
	 * @param accountExpireTime  Date
	 */
	public void setAccountExpireTime(Date accountExpireTime) {
		this.accountExpireTime = accountExpireTime;
	}

	/**
	 * 返回 loginIp。
	 * 
	 * @return String
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设置 loginIp。
	 * 
	 * @param loginIp String
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 返回 loginTime。
	 * 
	 * @return  Date
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * 设置 loginTime。
	 * 
	 * @param loginTime Date
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * 返回 lastLoginIp。
	 * 
	 * @return String
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * 设置 lastLoginIp。
	 * 
	 * @param lastLoginIp String
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * 返回 lastLoginTime。
	 * 
	 * @return Date
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * 设置 lastLoginTime。
	 * 
	 * @param lastLoginTime Date
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * 返回 onlineCount。
	 * 
	 * @return  Integer
	 */
	public Integer getOnlineCount() {
		if(onlineCount==null){
			return 0;
		}
		return onlineCount;
	}

	/**
	 * 设置 onlineCount。
	 * 
	 * @param onlineCount Integer
	 */
	public void setOnlineCount(Integer onlineCount) {
		this.onlineCount = onlineCount;
	}

	/**
	 * 返回 locked。
	 * 
	 * @return  String
	 */
	@Transient
	public String getLocked() {
		return locked;
	}

	/**
	 * 设置 locked。
	 * 
	 * @param locked  String
	 */
	public void setLocked(String locked) {
		this.locked = locked;
	}

	/**
	 * Return the value associated with the column: DELFLAG.
	 * 
	 * @return String
	 */
	@Column(name="DEL_FLAG")
	public java.lang.String getDelFlag () {
		return delFlag;
	}

	/**
	 * Set the value related to the column: DELFLAG.
	 * 
	 * @param delFlag the DELFLAG value
	 */
	public void setDelFlag (java.lang.String delFlag) {
		this.delFlag = delFlag;
	}


	/**
	 * 返回 orgid。
	 * 
	 * @return String
	 */
	@Transient
	public String getOrgid() {
		return orgid;
	}

	/**
	 * 设置 orgid。
	 * 
	 * @param orgid  String
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	/**
	 * 返回 orgName。
	 * 
	 * @return  String
	 */
	@Transient
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置 orgName。
	 * 
	 * @param orgName String
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	/**
	 * 返回 org。
	 * 
	 * @return FbrpSecOrgVO 
	 */
	public FbrpSecOrg getOrg() {
		return org;
	}

	/**
	 * 设置 org。
	 * 
	 * @param org  FbrpSecOrg
	 */
	public void setOrg(FbrpSecOrg org) {
		this.org = org;
	}


	/**
	 * 返回 roleid。
	 * 
	 * @return String
	 */
	@Transient
	public String getRoleid() {
		return roleid;
	}

	/**
	 * 设置 roleid。
	 * 
	 * @param roleid  String
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	/**
	 * 返回 roleName。
	 * 
	 * @return String
	 */
	@Transient
	public String getRoleName() {
		return roleName;
	}

	/**
	 *  设置 roleName。
	 * 
	 * @param roleName String
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 返回 relationType。
	 * 
	 * @return String
	 */
	@Transient
	public String getRelationType() {
		return relationType;
	}

	/**
	 * 设置 relationType。
	 * 
	 * @param relationType String
	 */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	/**
	 * 返回 mobileTel。
	 * 
	 * @return String
	 */
	public java.lang.String getMobileTel() {
		return mobileTel;
	}

	/**
	 * 设置 mobileTel。
	 * 
	 * @param mobileTel String
	 */
	public void setMobileTel(java.lang.String mobileTel) {
		this.mobileTel = mobileTel;
	}

	/**
	 * 返回 createdTime。
	 * 
	 * @return Date
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置 createdTime。
	 * 
	 * @param createdTime Date
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 返回 creatorId。
	 * 
	 * @return String
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * 设置 creatorId。
	 * 
	 * @param creatorId String
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 返回 lastModifierId。
	 * 
	 * @return String
	 */
	public String getLastModifierId() {
		return lastModifierId;
	}

	/**
	 * 设置 lastModifierId。
	 * 
	 * @param lastModifierId String
	 */
	public void setLastModifierId(String lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	/**
	 * 返回 lastModifiedTime。
	 * 
	 * @return Date
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * 设置 lastModifiedTime。
	 * 
	 * @param lastModifiedTime Date
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	/**
	 * 判断是否选中。
	 * 
	 * @return boolean
	 */
	@Transient
	public boolean isChecked() {
		return checked;
	}

	/**
	 * 设置是否选中。
	 * 
	 * @param checked boolean
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * 返回 orgCode。
	 * 
	 * @return String
	 */
	@Transient
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 orgCode。
	 * 
	 * @param orgCode String
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
}