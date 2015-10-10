/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.codeinfo.vo;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 关联表FBRP_INFRA_CODE_TYPE的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpCommonCodeType extends ValueObject {

	private static final long serialVersionUID = 4746686315872640785L;

	// fields
	private java.lang.String cname;
	private java.lang.String remark;

	private java.lang.String name;

	
	
	/**
	 * Return the value associated with the column: CNAME。
	 * 
	 * @return String cname
	 */
	public java.lang.String getCname() {
		return cname;
	}

	/**
	 * Set the value related to the column: CNAME。
	 * 
	 * @param cname
	 *            the CNAME value
	 */
	public void setCname(java.lang.String cname) {
		this.cname = cname;
	}

	/**
	 * Return the value associated with the column: REMARK。
	 * 
	 * @return String
	 */
	public java.lang.String getRemark() {
		return remark;
	}

	/**
	 * Set the value related to the column: REMARK。
	 * 
	 * @param remark
	 *            the REMARK value
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/**
	 * 返回 name。
	 * 
	 * @return 返回 name。
	 */
	@Column(name="name")
	public java.lang.String getName() {
		return name;
	}

	/**
	 * 设置 name。
	 * 
	 * @param name
	 *            设置 name。
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

}