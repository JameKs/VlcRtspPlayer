/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.vo;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * <pre>
 * 角色人员VO类。
 * </pre>
 * @author zhangminchao  zhangminchao@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpSecRoleVO extends ValueObject {

	private static final long serialVersionUID = -3336970265198711558L;
	
	private String code;
	private String name;
	private String remark;
	private String status;
	private String delFlag;
	private String staffid;
	private String roleid;
	private String orgCode;
	private String orgName;
	
	/**
	 * 获取code。
	 *
	 * @return String
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置 code。
	 *
	 * @param code String
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取name。
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 name。
	 *
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 设置 remark。
	 *
	 * @param remark String
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 设置 status。
	 *
	 * @param status String
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取delFlag。
	 *
	 * @return String
	 */
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
	/**
	 * 获取staffid。
	 *
	 * @return String
	 */
	public String getStaffid() {
		return staffid;
	}
	/**
	 * 设置 staffid。
	 *
	 * @param staffid String
	 */
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	/**
	 * 获取roleid。
	 *
	 * @return String
	 */
	public String getRoleid() {
		return roleid;
	}
	/**
	 * 设置 roleid。
	 *
	 * @param roleid String
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	/**
	 * 获取orgCode。
	 * 
	 * @return String
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置orgCode。
	 * 
	 * @param orgCode String
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取orgName。
	 * 
	 * @return String
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * 设置orgName.
	 * 
	 * @param orgName String
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	
}
