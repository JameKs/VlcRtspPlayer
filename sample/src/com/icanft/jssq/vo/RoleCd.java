/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.jssq.vo;

import java.io.Serializable;

import com.icanft.common.DefaultVo;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月23日
 */
public class RoleCd extends DefaultVo implements Serializable {

	private static final long serialVersionUID = 7326940746136887768L;
	
	private String cdId;
	
	private String roleId;

	/**
	 * @return 返回 cdId。
	 */
	public String getCdId() {
		return cdId;
	}

	/**
	 * @param cdId 设置 cdId。
	 */
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}

	/**
	 * @return 返回 roleId。
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId 设置 roleId。
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
}
