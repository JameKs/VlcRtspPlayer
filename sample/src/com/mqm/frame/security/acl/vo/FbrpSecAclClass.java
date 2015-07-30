/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.vo;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 关联表FBRP_SEC_ACL_CLASS的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpSecAclClass extends ValueObject {

	private static final long serialVersionUID = -7549955177615346278L;

	@NotEmpty(message = "必填")
	@Length(max = 100, message = "字符长度不能超过100")
	private java.lang.String className;

	/**
	 * 返回 className。
	 * 
	 * @return 返回 className。
	 */
	@Column(name="CLASS")
	public java.lang.String getClassName() {
		return className;
	}

	/**
	 * 设置 className。
	 * 
	 * @param className
	 *            设置 className。
	 */
	public void setClassName(java.lang.String className) {
		this.className = className;
	}

}