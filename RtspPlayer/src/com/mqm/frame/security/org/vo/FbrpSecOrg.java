/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.org.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author linjunxiong linjunxiong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
public class FbrpSecOrg extends ValueObject {

	private static final long serialVersionUID = -2329944501596017572L;
	/**
	 * 常量。
	 */
	public static final String KIND_ORG = "ORG";
	/**
	 * 常量。
	 */
	public static final String KIND_DEPT = "DEPT";
	/**
	 * 常量。
	 */
	public static final String KIND_DEFAULT = KIND_ORG;
	/**
	 * 常量。
	 */
	public static final String STATUS_INVALIDATE = "0";
	/**
	 * 常量。
	 */
	public static final String STATUS_VALIDATE = "1";
	/**
	 * 常量。
	 */
	public static final String STATUS_DEFAULT = STATUS_VALIDATE;

	private String id;
	@NotEmpty(message = "必填")
	@Length(max = 32, message = "长度不能超过32")
	private String code;
	@NotEmpty(message = "必填")
	@Length(max = 64, message = "长度不能超过64")
	private String name;
	private String parentId;
	private String orgLevel;
	private String orgType;
	// private IPUnitbiztypeVO bizType;
	private String status;
	@Length(max = 200, message = "长度不能超过200")
	private String remark;

	private String superOrgName;
	private String ownerOrgName;

	private boolean haveChildOrg;
	private String iconUrl;
	private String delFlag;
	private  String  ext1;
	private  String  ext2;
	private  String  ext3;
	private String  lastModifierId;
	private Date  createdTime;
	private String creatorId;
	private  Date  lastModifiedTime;
	
	/**
	 * 返回 DelFlag。
	 * 
	 * @return String
	 */
	@Column(name = "DEL_FLAG")
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * 设置 DelFlag。
	 * 
	 * @param delFlag String
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 返回 superOrgName。
	 * 
	 * @return String
	 */
	@Transient
	public String getSuperOrgName() {
		return superOrgName;
	}

	/**
	 * 设置 superOrgName。
	 * 
	 * @param superOrgName
	 *            String
	 */
	public void setSuperOrgName(String superOrgName) {
		this.superOrgName = superOrgName;
	}

	/**
	 * 返回 ownerOrgName。
	 * 
	 * @return String
	 */
	@Transient
	public String getOwnerOrgName() {
		return ownerOrgName;
	}

	/**
	 * 设置 ownerOrgName。
	 * 
	 * @param ownerOrgName
	 *            String
	 */
	public void setOwnerOrgName(String ownerOrgName) {
		this.ownerOrgName = ownerOrgName;
	}

	/**
	 * 返回 id。
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id。
	 * 
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取orgLevel。
	 * 
	 * @return String
	 */
	public String getOrgLevel() {
		return orgLevel;
	}

	/**
	 * 设置orgLevel。
	 * 
	 * @param orgLevel
	 *            String
	 */
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * 获取status。
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置status。
	 * 
	 * @param status
	 *            String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取remark。
	 * 
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置remark。
	 * 
	 * @param remark
	 *            String
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回 code。
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 code。
	 * 
	 * @param code
	 *            String
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 返回 name。
	 * 
	 * @return String
	 */
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	/**
	 * 设置 name。
	 * 
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 parentId。
	 * 
	 * @return String
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置 parentId。
	 * 
	 * @param parentId
	 *            String
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 返回 orgType。
	 * 
	 * @return String
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * 设置 orgType。
	 * 
	 * @param orgType
	 *            String
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 返回 haveChildOrg。
	 * 
	 * @return boolean
	 */
	public boolean isHaveChildOrg() {
		return haveChildOrg;
	}

	/**
	 * 设置 haveChildOrg。
	 * 
	 * @param haveChildOrg
	 *            boolean
	 */
	public void setHaveChildOrg(boolean haveChildOrg) {
		this.haveChildOrg = haveChildOrg;
	}

	/**
	 * 设置 haveChildOrg。
	 * 
	 * @param haveChildOrg
	 *            int
	 */
	public void setHaveChildOrg(int haveChildOrg) {
		this.haveChildOrg = (haveChildOrg > 0);
	}

	/**
	 * 返回 iconUrl。
	 * 
	 * @return String
	 */
	@Transient
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * 设置 iconUrl。
	 * 
	 * @param iconUrl
	 *            String
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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
	 * 返回ext2。
	 * 
	 * @return String
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * 设置ext2。
	 * 
	 * @param ext2 String
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * 获取ext3。
	 * 
	 * @return ext3
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * 设置 ext3。
	 * 
	 * @param ext3 String
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * 获取 lastModifierId。
	 * 
	 * @return lastModifierId
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

	/**
	 * 获取 lastModifiedTime。
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
}
