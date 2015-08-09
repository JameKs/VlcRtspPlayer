/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.org.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


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
public class FbrpSecOrgMember extends ValueObject implements Serializable {
	
	private static final long serialVersionUID = 9077247915976968398L;
	
	/** 常量。 */
	public static final String REF = "FbrpSecOrgMemberVO";
	/** 常量。 */
	public static final String PROP_STATUS = "Status";
	/** 常量。 */
	public static final String PROP_ORGID = "Orgid";
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

	private Date  createdTime;
	private String  creatorId;
	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String orgId;
	private java.lang.String staffId;
	private java.lang.String status;
	private String relationType;
	private String delFlag;
	
	/**
	 * 获取 delFlag。
	 * 
	 * @return String
	 */
	@Column(name="DEL_FLAG")
	public String getDelFlag() {
		return delFlag;
	}
	
	/**
	 * 设置 delFlag。
	 * 
	 * @param delFlag String
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	 * Return the value associated with the column: orgid。
	 * 
	 * @return String
	 */
	public java.lang.String getOrgId() {
		return orgId;
	}

	/**
	 * Set the value related to the column: orgid。
	 * 
	 * @param orgId
	 *            the orgid value
	 */
	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
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
	 * @return status
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
	 * 返回 relationType。
	 * 
	 * @return 返回 relationType。
	 */
	public String getRelationType() {
		return relationType;
	}

	/**
	 * 设置 relationType。
	 * 
	 * @param relationType 设置 relationType。
	 */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	/**
	 * 获取 createdTime。
	 * 
	 * @return Date
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