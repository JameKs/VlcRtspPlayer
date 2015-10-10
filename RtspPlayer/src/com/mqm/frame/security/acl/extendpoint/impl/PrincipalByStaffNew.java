/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint.impl;

import java.util.Arrays;
import java.util.List;

import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.acl.extendpoint.IPrincipalTypeNew;
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
public class PrincipalByStaffNew extends DefaultServiceImpl implements
		IPrincipalTypeNew {

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

   @Override
	public FbrpBaseController getPageController() {
	   FbrpBaseController pageController = new PrincipalPageController() {
		
			public String init() {
				super.setPrincipalObject(new FbrpSecStaff());
				//query();
				return "";
			}

			
//			public List query() {
//				Object[] o = staffService.queryByParamfilter(
//						(FbrpSecStaff) super.getPrincipalObject(),
//						(this.currentPage - 1) * this.pageSize, pageSize);
//				super.setPrincipals((List) o[0]);
//				this.totalSize = (Integer) o[1];
//				return (List<FbrpSecStaff>) o[0];
//			}

			
//			public int getPageSize() {
//				super.pageSize = 10;
//				return super.getPageSize();
//			}

			
//			public void reset() {
//				super.refreshPage();
//				init();
//			}
		};
	//	pageController.init();
		return pageController;
	}

}
