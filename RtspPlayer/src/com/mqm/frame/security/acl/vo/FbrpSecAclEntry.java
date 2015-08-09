/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * 
 * <pre>
 * 关联表FBRP_SEC_ACL_ENTRY的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpSecAclEntry extends ValueObject {

	private static final long serialVersionUID = 5315517992490547151L;

	@NotEmpty(message = "必填")
	@Length(max = 32, message = "字符长度不能超过32")
	private java.lang.String aclObjectIdentity;

	@NotBlank(message = "必填")
	private java.lang.Long aceOrder;

	@NotEmpty(message = "必填")
	@Length(max = 32, message = "字符长度不能超过32")
	private java.lang.String sid;

	@NotBlank(message = "必填")
	private java.lang.Long mask;

	@NotBlank(message = "必填")
	private java.lang.Long granting;

	@NotBlank(message = "必填")
	private java.lang.Long auditSuccess;

	@NotBlank(message = "必填")
	private java.lang.Long auditFailure;
	
	private FbrpSecAclSid sidVO;
	
	/**
	 * 返回sidVO。
	 * 
	 * @return sidVO
	 */
	public FbrpSecAclSid getSidVO() {
		return sidVO;
	}

	/**
	 * 设置sidVO。
	 *  
	 * @param sidVO FbrpSecAclSid
	 */
	public void setSidVO(FbrpSecAclSid sidVO) {
		this.sidVO = sidVO;
	}

	/**
	 * Return the value associated with the column: ACL_OBJECT_IDENTITY。
	 * 
	 * @return String
	 */
	public java.lang.String getAclObjectIdentity() {
		return aclObjectIdentity;
	}

	/**
	 * Set the value related to the column: ACL_OBJECT_IDENTITY。
	 * 
	 * @param aclObjectIdentity
	 *            the ACL_OBJECT_IDENTITY value
	 */
	public void setAclObjectIdentity(java.lang.String aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	/**
	 * Return the value associated with the column: ACE_ORDER。
	 * 
	 * @return Long
	 */
	public java.lang.Long getAceOrder() {
		return aceOrder;
	}

	/**
	 * Set the value related to the column: ACE_ORDER。
	 * 
	 * @param aceOrder
	 *            the ACE_ORDER value
	 */
	public void setAceOrder(java.lang.Long aceOrder) {
		this.aceOrder = aceOrder;
	}

	/**
	 * Return the value associated with the column: SID。
	 * 
	 * @return String
	 */
	public java.lang.String getSid() {
		return sid;
	}

	/**
	 * Set the value related to the column: SID。
	 * 
	 * @param sid
	 *            the SID value
	 */
	public void setSid(java.lang.String sid) {
		this.sid = sid;
	}

	/**
	 * Return the value associated with the column: MASK。
	 * 
	 * @return Long
	 */
	public java.lang.Long getMask() {
		return mask;
	}

	/**
	 * Set the value related to the column: MASK。
	 * 
	 * @param mask
	 *            the MASK value
	 */
	public void setMask(java.lang.Long mask) {
		this.mask = mask;
	}

	/**
	 * Return the value associated with the column: GRANTING。
	 * 
	 * @return Long
	 */
	public java.lang.Long getGranting() {
		return granting;
	}

	/**
	 * Set the value related to the column: GRANTING。
	 * 
	 * @param granting
	 *            the GRANTING value
	 */
	public void setGranting(java.lang.Long granting) {
		this.granting = granting;
	}

	/**
	 * Return the value associated with the column: AUDIT_SUCCESS。
	 * 
	 * @return Long
	 */
	public java.lang.Long getAuditSuccess() {
		return auditSuccess;
	}

	/**
	 * Set the value related to the column: AUDIT_SUCCESS。
	 * 
	 * @param auditSuccess
	 *            the AUDIT_SUCCESS value
	 */
	public void setAuditSuccess(java.lang.Long auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	/**
	 * Return the value associated with the column: AUDIT_FAILURE。
	 * 
	 * @return Long
	 */
	public java.lang.Long getAuditFailure() {
		return auditFailure;
	}

	/**
	 * Set the value related to the column: AUDIT_FAILURE。
	 * 
	 * @param auditFailure
	 *            the AUDIT_FAILURE value
	 */
	public void setAuditFailure(java.lang.Long auditFailure) {
		this.auditFailure = auditFailure;
	}

}