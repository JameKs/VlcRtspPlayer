/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint.impl;

import java.util.Arrays;
import java.util.List;

import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.security.acl.extendpoint.IPrincipalType;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;

/**
 * <pre>
 * 按人员授权的实现。
 * </pre>
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class PrincipalByStaff extends DefaultServiceImpl implements
		IPrincipalType {

	private IStaffService staffService;

	@Override
	public String getPrincipalName() {
		return "人员";
	}

	@Override
	public List getPrincipalsByStaffId(String staffId) {

		FbrpSecStaff vo = this.staffService.find(staffId);

		return Arrays.asList(vo);
	}

	@Override
	public List getCurrentPrincipals() {
		String staffId = this.getUser().getStaffId();
		FbrpSecStaff staffVO = new FbrpSecStaff();
		staffVO.setId(staffId);
		return Arrays.asList(staffVO);
	}

	/**
	 * 设置staffService。
	 * 
	 * @param staffService IStaffService
	 */
	public void setStaffService(IStaffService staffService) {
		this.staffService = staffService;
	}


}
