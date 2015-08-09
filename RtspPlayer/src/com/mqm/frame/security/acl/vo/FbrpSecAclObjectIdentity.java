/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.vo;

import javax.persistence.Column;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * 
 * <pre>
 * 关联表FBRP_SEC_ACL_OBJECT_IDENTITY的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpSecAclObjectIdentity extends ValueObject {

	private static final long serialVersionUID = 7963411532856843907L;

	@NotEmpty(message = "必填")
	@Length(max = 32, message = "字符长度不能超过32")
	private java.lang.String objectIdClass;

	@NotEmpty(message = "必填")
	@Length(max = 32, message = "字符长度不能超过32")
	private java.lang.String objectIdIdentity;

	@Length(max = 32, message = "字符长度不能超过32")
	private java.lang.String parentObject;

	@Length(max = 32, message = "字符长度不能超过32")
	private java.lang.String ownerSid;

	@NotBlank(message = "必填")
	private java.lang.Long entriesInheriting;
	
	private FbrpSecAclSid fbrpSecAclSidVO;
	
	private FbrpSecAclClass fbrpSecAclClassVO;
	
	private String ext1;
	
	private String ext2;
	
	private String ext3;
	
	
	/**
	 * 获取Ext1。
	 * 
	 * @return String
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * 设置Ext1。
	 * 
	 * @param ext1 String
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * 获取Ext2。
	 * 
	 * @return String
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * 设置Ext2。
	 * 
	 * @param ext2 String
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * 获取Ext3。
	 * 
	 * @return String
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * 设置Ext3。
	 * 
	 * @param ext3 String
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * 获取fbrpSecAclClassVO。
	 * 
	 * @return FbrpSecAclClass
	 */
	public FbrpSecAclClass getFbrpSecAclClassVO() {
		return fbrpSecAclClassVO;
	}

	/**
	 * 设置fbrpSecAclClassVO。
	 * 
	 * @param fbrpSecAclClassVO FbrpSecAclClass
	 */
	public void setFbrpSecAclClassVO(FbrpSecAclClass fbrpSecAclClassVO) {
		this.fbrpSecAclClassVO = fbrpSecAclClassVO;
	}

	/**
	 * 获取fbrpSecAclSidVO。
	 * 
	 * @return FbrpSecAclSid
	 */
	public FbrpSecAclSid getFbrpSecAclSidVO() {
		return fbrpSecAclSidVO;
	}

	/**
	 * 设置fbrpSecAclSidVO。
	 * 
	 * @param fbrpSecAclSidVO FbrpSecAclSid
	 */
	public void setFbrpSecAclSidVO(FbrpSecAclSid fbrpSecAclSidVO) {
		this.fbrpSecAclSidVO = fbrpSecAclSidVO;
	}

	/**
	 * 返回 objectIdClass。
	 * 
	 * @return 返回 objectIdClass。
	 */
	public String getObjectIdClass() {
		return objectIdClass;
	}

	/**
	 *  设置 objectIdClass。
	 * 
	 * @param objectIdClass
	 *            设置 objectIdClass。
	 */
	public void setObjectIdClass(String objectIdClass) {
		this.objectIdClass = objectIdClass;
	}

	/**
	 * Return the value associated with the column: OBJECT_ID_IDENTITY。
	 * 
	 * @return String
	 */
	public java.lang.String getObjectIdIdentity() {
		return objectIdIdentity;
	}

	/**
	 * Set the value related to the column: OBJECT_ID_IDENTITY。
	 * 
	 * @param objectIdIdentity
	 *            the OBJECT_ID_IDENTITY value
	 */
	public void setObjectIdIdentity(java.lang.String objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	/**
	 * Return the value associated with the column: PARENT_OBJECT。
	 * 
	 * @return parentObject
	 */
	public java.lang.String getParentObject() {
		return parentObject;
	}

	/**
	 * Set the value related to the column: PARENT_OBJECT。
	 * 
	 * @param parentObject
	 *            the PARENT_OBJECT value
	 */
	public void setParentObject(java.lang.String parentObject) {
		this.parentObject = parentObject;
	}

	/**
	 * Return the value associated with the column: OWNER_SID。
	 * 
	 * @return String
	 */
	@Column(name="OWNER_SID")
	public java.lang.String getOwnerSid() {
		return ownerSid;
	}

	/**
	 * Set the value related to the column: OWNER_SID。
	 * 
	 * @param ownerSid
	 *            the OWNER_SID value
	 */
	public void setOwnerSid(java.lang.String ownerSid) {
		this.ownerSid = ownerSid;
	}

	/**
	 * Return the value associated with the column: ENTRIES_INHERITING。
	 * 
	 * @return Long
	 */
	public java.lang.Long getEntriesInheriting() {
		return entriesInheriting;
	}

	/**
	 * Set the value related to the column: ENTRIES_INHERITING。
	 * 
	 * @param entriesInheriting
	 *            the ENTRIES_INHERITING value
	 */
	public void setEntriesInheriting(java.lang.Long entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}