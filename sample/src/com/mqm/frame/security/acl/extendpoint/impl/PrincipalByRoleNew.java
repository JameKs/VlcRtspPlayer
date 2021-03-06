/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint.impl;

import java.util.ArrayList;
import java.util.List;

import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.acl.extendpoint.IPrincipalTypeNew;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecRole;

/**
 * <pre>
 * 系统中默认的授权主体（按角色授权）。
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

public class PrincipalByRoleNew extends DefaultServiceImpl implements IPrincipalTypeNew {
	
	
	private IRoleService roleService;
	
	@Override
	public String getPrincipalName() {
		return "角色";
	}

	@Override
	public List getPrincipalsByStaffId(String staffId) {
		return this.roleService.getRolesByStaffId(staffId);
	}

	@Override
	public List getCurrentPrincipals() {
		List<FbrpSecRole> list = new ArrayList<FbrpSecRole>();
		list.addAll(this.getUser().getRoleList());
		/*for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getAppId().equals(this.getAppId())) {
				list.remove(i);
				i--;
			}
		}*/
		return list;
	}


	/**
	 * 设置roleService。
	 * 
	 * @param roleService IRoleService
	 */
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public FbrpBaseController getPageController() {
		FbrpBaseController pageController = new PrincipalPageController() {
			
			public void init() {
				super.setPrincipalObject(new FbrpSecRole());
				//query();
			}
			
//			public List query() {
//				Object[] o = roleService.queryByParamFilterAdminRole(
//						(FbrpSecRole) super.getPrincipalObject(),
//						(this.currentPage - 1) * 10, pageSize);
//				
//				super.setPrincipals((List) o[0]);
//				Integer totalSize = (Integer) o[1];
//				
//				return (List<FbrpSecRole>) o[0];
//			}
			
			

			
//			public void reset() {
//				super.refreshPage();
//				init();
//			}
			
		};
		
		return pageController;
	}
}
