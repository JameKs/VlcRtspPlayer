/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpSecRoleMember extends ValueObject implements Serializable {
	
	private static final long serialVersionUID = 9077247915976968598L;
	
	/** 常量。 */
	public static final String REF = "FbrpSecRoleMemberVO";
	/** 常量。 */
	public static final String PROP_STATUS = "Status";
	/** 常量。 */
	public static final String PROP_ROLEID = "Roleid";
	/** 常量。 */
	public static final String PROP_DELFLAG = "Delflag";
	/** 常量。 */
	public static final String PROP_LASTMODIFIERID = "Lastmodifierid";
	/** 常量。 */
	public static final String PROP_ID = "Id";
	/** 常量。 */
	public static final String PROP_CREATORID = "Creatorid";
	/** 常量。 */
	public static final String PROP_STAFFID = "Staffid";
	/** 常量。 */
	public static final String PROP_LASTMODIFIEDTIME = "Lastmodifiedtime";
	/** 常量。 */
	public static final String PROP_CREATEDTIME = "Createdtime";

	/** 常量。 */
	public static final String DEFAULT_STATUS = "1";
	/** 常量。 */
	public static final String DEFAULT_DELFLAG = "n";

	// primary key
	private java.lang.String id;
	private String ext1;
	private Date createdTime;
	private String  creatorId;

	// fields
	private java.lang.String roleId;
	private java.lang.String staffId;
	private java.lang.String status;
    private String delFlag;
	
	private FbrpSecStaff staff;
	
	/**
	 * 获取delFlag。
	 * 
	 * @return String
	 */
    @Column(name="DEL_FLAG")
	public String getDelFlag() {
		return delFlag;
	}

    /**
     * 设置delFlag。
     * 
     * @param delFlag String
     */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

    /**
     * 获取staff。
     * 
     * @return FbrpSecStaff
     */
	@Transient
	public FbrpSecStaff getStaff() {
		return staff;
	}

	/**
	 * 设置staff。
	 * 
	 * @param staff FbrpSecStaff
	 */
	public void setStaff(FbrpSecStaff staff) {
		this.staff = staff;
	}

	@Override
	public java.lang.String getId() {
		return id;
	}

	@Override
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * Return the value associated with the column: ROLEID。
	 * 
	 * @return String
	 */
	public java.lang.String getRoleId() {
		return roleId;
	}

	/**
	 * Set the value related to the column: ROLEID。
	 * 
	 * @param roleId
	 *            the ROLEID value
	 */
	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}

	/**
	 * Return the value associated with the column: STAFFID。
	 * 
	 * @return String
	 */
	public java.lang.String getStaffId() {
		return staffId;
	}

	/**
	 * Set the value related to the column: STAFFID。
	 * 
	 * @param staffId
	 *            the STAFFID value
	 */
	public void setStaffId(java.lang.String staffId) {
		this.staffId = staffId;
	}

	/**
	 * Return the value associated with the column: STATUS。
	 * 
	 * @return String
	 */
	public java.lang.String getStatus() {
		return status;
	}

	/**
	 * Set the value related to the column: STATUS。
	 * 
	 * @param status
	 *            the STATUS value
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 获取ext1。
	 * 
	 * @return ext1
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * 设置ext1。
	 * 
	 * @param ext1 String
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * 获取 createdTime。
	 * 
	 * @return Date。
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置 createdTime。
	 * 
	 * @param createdTime Data。
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 获取 creatorId。
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

}