/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.vo;

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
 * 关联FBRP_SEC_ROLE表。
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
public class FbrpSecRole extends ValueObject implements GrantedAuthority {

	private static final long serialVersionUID = 9077247915976808598L;

	/**
	 * 常量。
	 */
	public static final String DEFAULT_STATUS = "1";
	
	/**
	 * 常量。
	 */
	public static final String DEFAULT_DELFLAG = "n";

	/**
	 * 常量。 
	 */
	public static final String DEFAULT_BIZROLENAME = "业务管理员";
	
	/**
	 * 常量。 
	 */
	public static final String DEFAULT_TECHROLENAME = "技术管理员";
	
	/**
	 * 常量。 
	 */
	public static final String GRANT_PAGE = "/pages/fbrp/security/authorization/grant.jsf";

	@Length(max = 32, message = "角色编码长度不能超过32位")
	private java.lang.String code;

	// fields
	@NotEmpty(message = "角色名称不能为空")
	@Length(max = 64, message = "角色名称长度不能超过64位")
	private java.lang.String name;
	@Length(max = 200, message = "备注长度不能超过200位")
	private java.lang.String remark;
	private java.lang.String status;
	private java.lang.String appId;
	private String delFlag;
	private String sjssjgDm;//数据所属机构代码
	
	/**
	 * 无参构造器。
	 */
	public FbrpSecRole() {
		
	}
	
	/**
	 * 自定义构造器。
	 * 
	 * @param id String
	 */
	public FbrpSecRole(String id) {
		super.setId(id);
	}
	
	@Override
	@Transient
	public String getAuthority() {
		return this.getName();
	}

	/**
	 * 获取delFlag。
	 * 
	 * @return String
	 */
	@Column(name = "DEL_FLAG")
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
	 * 获取code。
	 * 
	 * @return String
	 */
	public java.lang.String getCode() {
		return code;
	}

	/**
	 * 设置code。
	 * 
	 * @param code
	 *            String
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
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
	 * 
	 * @param remark
	 *            the REMARK value
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
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
	 * 获取name。
	 * 
	 * @return String
	 */
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	// @Override
	/**
	 * 设置名称。
	 * 
	 * @param str
	 *            String
	 */
	public void setName(String str) {
		name = str;
	}

	/**
	 * 获取appId。
	 * 
	 * @return String
	 */
	@Transient
	public java.lang.String getAppId() {
		return appId;
	}

	/**
	 * 设置appId。
	 * 
	 * @param appId String
	 */
	public void setAppId(java.lang.String appId) {
		this.appId = appId;
	}


	/**
	 * 返回 sjssjgDm。
	 * 
	 * @return String
	 */
	public String getSjssjgDm() {
		return sjssjgDm;
	}

	/**
	 * 设置 sjssjgDm。
	 * 
	 * @param sjssjgDm String
	 */
	public void setSjssjgDm(String sjssjgDm) {
		this.sjssjgDm = sjssjgDm;
	}
	
}