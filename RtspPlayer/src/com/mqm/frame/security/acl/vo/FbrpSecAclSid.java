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
 * 关联表FBRP_SEC_ACL_SID的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpSecAclSid extends ValueObject {

	private static final long serialVersionUID = 2952249525031118671L;

	@NotBlank(message = "必填")
	private java.lang.Long principal;

	@NotEmpty(message = "必填")
	@Length(max = 100, message = "字符长度不能超过100")
	private java.lang.String sid;
	
	private String ext1;
	
	private String ext2;
	
	private String ext3;
	
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
	 * Return the value associated with the column: PRINCIPAL。
	 * 
	 * @return Long
	 */
	public java.lang.Long getPrincipal() {
		return principal;
	}

	/**
	 * Set the value related to the column: PRINCIPAL。
	 * 
	 * @param principal the PRINCIPAL value
	 */
	public void setPrincipal(java.lang.Long principal) {
		this.principal = principal;
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
	 * @param sid the SID value
	 */
	public void setSid(java.lang.String sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}