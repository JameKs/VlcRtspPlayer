/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.vo;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * <pre>
 * 实体类。
 * </pre>
 * 
 * @author lijiawei lijiawei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class GyUuvOrgVO extends ValueObject {

	private static final long serialVersionUID = -4416675165034991549L;

	private String orgCode;
	private String orgName;
	private String shortName;
	private String parentCode;
	private String swbmBj;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getSwbmBj() {
		return swbmBj;
	}

	public void setSwbmBj(String swbmBj) {
		this.swbmBj = swbmBj;
	}

}
