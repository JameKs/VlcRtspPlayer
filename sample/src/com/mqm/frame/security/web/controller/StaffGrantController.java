/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.infrastructure.util.VOUtils;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.acl.extendpoint.IGrantExtendPoint;
import com.mqm.frame.security.acl.extendpoint.IGrantShowShape;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.util.SecurityUtil;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author lijiawei  lijiawei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("public-access/security/authorization")
public class StaffGrantController extends FbrpBaseController {
	
	@Resource
	private IStaffService staffService;

	@ResponseBody
	@RequestMapping(value = "staffGrant.do", params = "checkStaffGrant")
	public Map<String, Object> checkStaffGrant(String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		if(url.contains("taskYsz")) {
			flag = true;
		} else {
			String accountName = this.getUser().getLoginId();
			if (accountName != null && !"".equals(accountName.trim())) {
				flag = this.staffService.checkStaffGrant(accountName, url);
			}
		}
		map.put("success", flag);
		return map;
	}
	
}
