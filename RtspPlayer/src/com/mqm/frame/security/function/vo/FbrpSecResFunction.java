/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.function.vo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 关联FBRP_SEC_RES_FUCNTION的VO类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpSecResFunction extends ValueObject implements
		GrantedAuthority {

	private static final long serialVersionUID = 3410197904193783020L;

	@NotEmpty(message = "不能为空")
	@Length(min = 1, max = 128, message = "长度不能超过128")
	private String code;
	@NotEmpty(message = "不能为空")
	@Length(min = 1, max = 64, message = "长度不能超过64")
	private String name;
	@Length(min = 0, max = 200, message = "长度不能超过200")
	private String remark;

	private boolean checked;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Transient
	@Override
	public String getAuthority() {
		return this.getId();
	}

	/**
	 * 返回 code。
	 * 
	 * @return 返回 code。
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 code。
	 * 
	 * @param code
	 *            设置 code。
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取 name。
	 * 
	 * @return String
	 */
	@Column(name="NAME")
	public String getName() {
		return name;
	}

	/**
	 *  设置 name。
	 * 
	 * @param name
	 *            设置 name。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 remark。
	 * 
	 * @return 返回 remark。
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 *  设置 remark。
	 * 
	 * @param remark
	 *            设置 remark。
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取是否选择。
	 * 
	 * @return boolean
	 */
	@Transient
	public boolean isChecked() {
		return checked;
	}
	
	/**
	 * 设置是否选择。
	 * 
	 * @param checked boolean
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
