/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.infrastructure.service.IKeyGen;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.util.StringUtil;

/**
 * 
 * <pre>
 * 角色维护模块表现层处理类。
 * </pre>
 * 
 * @author zouyongqiao zouyongqiao@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:   1.1  修改人：zengziwen  修改日期:  2012-10-20   修改内容: 修改queryByParamFilterAdminRole()方法，添加数据权限控制
 * </pre>
 */
@Controller
@RequestMapping("security/role")
public class RoleManageController extends FbrpBaseController {

	private static final Log log = LogFactory
			.getLog(RoleManageController.class);

	@Resource
	private IRoleService roleService;

	@Resource
	private IStaffService staffService;
	
	@Resource
	private IKeyGen keyGen;

	/**
	 * 根据条件分页查询角色列表。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param fbrpSecRole FbrpSecRole
	 * 
	 * @param map ModelMap
	 * 
	 * @param staff FbrpSecStaff
	 * 
	 * @return String
	 */
	@RequestMapping("roleManage.do")
	public String query(HttpServletRequest request,
			@ModelAttribute("paramVo") FbrpSecRole fbrpSecRole, ModelMap map,
			FbrpSecStaff staff) {
		try {
			int pageIndex = this.getPageIndex(request);
			int pageSize = this.getPageSize(request);
			PagedResult<FbrpSecRole> pr = this.roleService.queryByParamFilterAdminRole(fbrpSecRole, pageIndex, pageSize,this.getUser().getOrgId());
			String orgId = this.getUser().getOrgId();
			if("244000000".equals(orgId)) {
				orgId = "24400000000";
			}
			map.put("pr", pr);
			map.put("pr1", new ArrayList<FbrpSecStaff>());
			map.put("rolePr", new FbrpSecRole());
			map.put("editData", new FbrpSecRole());
			map.put("paramVO", new FbrpSecRole());
			FbrpSecStaff paramStaff = new FbrpSecStaff();
			paramStaff.setOrgName(this.getUser().getOrgName());
			map.put("fbrpSecStaff", paramStaff);
			map.put("loginUserId", this.getUser().getLoginId());//登录人帐号
			map.put("orgId", orgId);
			this.auditLog(FbrpCommonAuditLog.BIZTYPE_ROLE,
					FbrpCommonAuditLog.LOGTYPE_SELECT, "获取角色列表");
		} catch (Exception e) {
			log.error("query error!", e);
			this.exceptionMsg.setMainMsg("查询时异常！");
		}
		return "security/role/roleManage";
	}

	/**
	 * 角色查询。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param paramVo FbrpSecRole
	 * 
	 * @return PagedResult<FbrpSecRole>
	 */
	@RequestMapping(value = "roleManage.do", params = "query")
	@ResponseBody
	public PagedResult<FbrpSecRole> loadPageResult(HttpServletRequest request,
			FbrpSecRole paramVo) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpSecRole> pr= this.roleService.queryByParamFilterAdminRole(paramVo, 	pageIndex, pageSize,this.getUser().getOrgId());
		return pr;
	}
	
	/**
	 * 查询角色编码是否已经存在。
	 * 
	 * @param code String
	 * 
	 * @return PagedResult<FbrpSecRole>
	 */
	@RequestMapping(value = "roleManage.do", params = "queryCode")
	@ResponseBody
	public int queryCode(String code) {
	     boolean result = this.roleService.ifExistCode(code);
	     if(result){
	    	 return 1;
	     }else{
	    	 return 0;
	     }
	
	}

	/**
	 * 添加角色。
	 * 
	 * @return FbrpSecRole
	 */
	public FbrpSecRole toAddRole() {
		return new FbrpSecRole();
	}

	/**
	 * 保存一条记录。
	 * 
	 * @param editData FbrpSecRole
	 * 
	 * @param result BindingResult
	 * 
	 * @return String
	 */
	@RequestMapping(value = "roleManage.do", params = "save")
	public String ok(
			@ModelAttribute("editData") @Valid FbrpSecRole editData,
			BindingResult result) {
		if (result.hasErrors()) {
			return "security/role/roleManage";
		}
		//用于判断省局人员创建角色的
		String orgCode = this.getUser().getOrgId();
		if("244000000".equals(orgCode)) {
			orgCode = "24400000000";
		}

		editData.setStatus("y");
		editData.setDelFlag("n");
		editData.setSjssjgDm(orgCode);

		this.roleService.saveOrUpdate(editData);
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_ROLE,
				FbrpCommonAuditLog.LOGTYPE_INSERT, "新增角色");

		return "redirect:roleManage.do";
	}

	/**
	 * 删除角色。
	 * 
	 * @param id String
	 * 
	 * @return String
	 */
	@RequestMapping(value = "roleManage.do", params = "deleteRow")
	public String deleteRole(@RequestParam String id) {
		// this.exceptionMsg.setMainMsg("");

		FbrpSecRole del = this.roleService.find(id);
		// String name = del.getName();
		try {
			// String roleId = this.getRequestParameter(name);
			// boolean isAppAdminRole =
			// this.grantService.isAppAdminRole(roleId);
			// if (isAppAdminRole) {
			// this.exceptionMsg.setMainMsg("业务管理员角色不能删除!");
			// return;
			// } else {
			this.roleService.delete(del);
			this.auditLog(FbrpCommonAuditLog.BIZTYPE_ROLE,
					FbrpCommonAuditLog.LOGTYPE_DELETE, "删除角色[" + del.getId()
							+ "]");
			// }
		} catch (Exception e) {
			log.error("", e);
			this.exceptionMsg.setMainMsg("", e);
		}
		return "redirect:roleManage.do";
	}

	/**
	 * 删除多条角色记录。
	 * 
	 * @param id String[]
	 * 
	 * @return String
	 */
	@RequestMapping(value = "roleManage.do", params = "deleteAll")
	public String deleteAll(String[] id) {
		if ("".equals(id) || id == null) {
			return "redirect:roleManage.do";
		}
		List<FbrpSecRole> roleList = new ArrayList<FbrpSecRole>();
		for (int i = 0; i < id.length; i++) {
			roleList.add(new FbrpSecRole(id[i]));
		}
		this.roleService.deleteAll(roleList);
		return "redirect:roleManage.do";
	}


	/**
	 * 成员查询分页功能。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * 
	 * @param fbrpSecStaff
	 *            FbrpSecStaff
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	@RequestMapping(value = "roleManage.do", params = "queryMember")
	@ResponseBody
	public PagedResult<FbrpSecStaff> queryMember(HttpServletRequest request,
			FbrpSecStaff fbrpSecStaff) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
 
		String parameter = request.getParameter("filterFlag");
		String roleid = request.getParameter("roleid");
		fbrpSecStaff.setRoleid(roleid);
		
		if(StringUtil.isStrEmpty(fbrpSecStaff.getOrgCode())){
			fbrpSecStaff.setOrgCode(this.getUser().getOrgId());
		}
		
		PagedResult<FbrpSecStaff> result = null;
	    if ("-1".equals(parameter)) {
			result = this.staffService.queryMemberById(pageIndex, pageSize,fbrpSecStaff);
		} else if ("1".equals(parameter)) {
			result = this.staffService.queryExistedMemberById(pageIndex,pageSize, fbrpSecStaff);
		}
		return result;
	}

	/**
	 * 批量添加人员。
	 * 
	 * @param staffId
	 *            String
	 * 
	 * @param roleId
	 *            String
	 * 
	 * @return String
	 */
	@RequestMapping(value = "roleManage.do", params = "addMember")
	@ResponseBody
	public String addMember(String staffId, String roleId) {
		List<FbrpSecRoleMember> list = new ArrayList<FbrpSecRoleMember>();
		String[] staffid = staffId.split("&");
		for (int i = 0; i < staffid.length; i++) {
			FbrpSecRoleMember vo = new FbrpSecRoleMember();
			vo.setId(this.keyGen.getUUIDKey());
			vo.setRoleId(roleId);
			vo.setStaffId(staffid[i]);
			list.add(vo);
		}
		this.staffService.batchAddMembers(list);
		return "ok";
	}

	/**
	 * 批量删除人员。
	 * 
	 * @param staffId
	 *            String
	 * 
	 * @param roleId
	 *            String
	 * 
	 * @return String
	 */
	@RequestMapping(value = "roleManage.do", params = "deleteMember")
	@ResponseBody
	public String deleteMember(String staffId, String roleId) {
		List<FbrpSecRoleMember> list = new ArrayList<FbrpSecRoleMember>();
		String[] staffid = staffId.split("&");
		for (int i = 0; i < staffid.length; i++) {
			FbrpSecRoleMember vo = new FbrpSecRoleMember();
			vo.setRoleId(roleId);
			vo.setStaffId(staffid[i]);
			list.add(vo);
		}
		this.staffService.batchUpdateMembers(list);
		return "ok";
	}

	/*
	 * @RequestMapping(value = "roleManage.do", params = "roleMember") public
	 * String queryRoleMember(HttpServletRequest request,
	 * 
	 * @ModelAttribute("paramVo") FbrpSecRole fbrpSecRole, ModelMap map) { int
	 * pageIndex = this.getPageIndex(request); int pageSize =
	 * this.getPageSize(request); Object[] obj =
	 * this.roleService.queryByParamFilterAdminRole( fbrpSecRole, pageIndex,
	 * pageSize); PagedResult<FbrpSecRole> pr = new PagedResult<FbrpSecRole>(
	 * (List<FbrpSecRole>) obj[0], (Integer) obj[1], pageIndex, pageSize); if
	 * (pr != null) { for (FbrpSecRole fsr : pr.getData()) { if
	 * ("sysAdmin".equals(fsr.getCode())) { pr.getData().remove(fsr); break; } }
	 * }
	 * 
	 * map.put("pr", pr); map.put("rolePr", new FbrpSecRole());
	 * map.put("editData", new FbrpSecRole()); map.put("paramVO", new
	 * FbrpSecRole()); this.auditLog(FbrpCommonAuditLog.BIZTYPE_ROLE,
	 * FbrpCommonAuditLog.LOGTYPE_SELECT, "获取角色列表"); map.put("roleVo1", new
	 * FbrpSecRole()); map.put("roleVo2", new FbrpSecRole()); return
	 * "security/role/roleMember"; }
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "roleManage.do", params = "queryRoleMember")
	 * public PagedResult<FbrpSecRole>
	 * loadRoleMemberPageResult(HttpServletRequest request, FbrpSecRole paramVo)
	 * { int pageIndex = this.getPageIndex(request); int pageSize =
	 * this.getPageSize(request); Object[] obj =
	 * this.roleService.queryByParamFilterAdminRole(paramVo, pageIndex,
	 * pageSize); PagedResult<FbrpSecRole> pr = new PagedResult<FbrpSecRole>(
	 * (List<FbrpSecRole>) obj[0], (Integer) obj[1], pageIndex, pageSize); if
	 * (pr != null) { for (FbrpSecRole fsr : pr.getData()) { if
	 * ("sysAdmin".equals(fsr.getCode())) { pr.getData().remove(fsr); break; } }
	 * } return pr; }
	 *//**
	 * 确定更新。
	 * 
	 * @param request
	 */
	/*
	 * @RequestMapping(value = "roleManage.do", params = "okMgrRole") public
	 * @ResponseBody void okMgrRole(String staffId, HttpServletRequest request)
	 * { String[] ids = request.getParameterValues("listId"); List<FbrpSecRole>
	 * list = new ArrayList<FbrpSecRole>(); if (ids != null) { for (int i = 0; i
	 * < ids.length; i++) { FbrpSecRole vo = new FbrpSecRole();
	 * vo.setId(ids[i]); list.add(vo); } }
	 * this.roleMemberService.updateToStaff(staffId, list);
	 * this.auditLog(FbrpCommonAuditLog.BIZTYPE_ROLE,
	 * FbrpCommonAuditLog.LOGTYPE_UPDATE, "更新人员[" + staffId + "]的人员角色"); }
	 *//**
	 * 查询。
	 */
	/*
	 * @RequestMapping(value = "roleManage.do", params = "queryRoles") public
	 * @ResponseBody List[] queryVo(String staffId, HttpServletRequest request)
	 * { int pageIndex = this.getPageIndex(request); int pageSize =
	 * this.getPageSize(request); Object[] result =
	 * this.roleService.queryByParam(new FbrpSecRole(), pageIndex, pageSize);
	 * List<FbrpSecRole> list = (List<FbrpSecRole>) result[0];
	 * 
	 * List<RoleDtoVO> allRoleDtos = new ArrayList<RoleDtoVO>(); for (int i = 0;
	 * i < list.size(); i++) { allRoleDtos.add(new RoleDtoVO(list.get(i))); }
	 * long long1 = Long.parseLong(result[1].toString()); int totalSize = (int)
	 * long1;
	 * 
	 * List<RoleDtoVO> roleDtoList = showFromRole(staffId); for (int i = 0; i <
	 * allRoleDtos.size(); i++) { if (isExit(allRoleDtos.get(i), roleDtoList)) {
	 * allRoleDtos.remove(i); i--; } } List[] results = { allRoleDtos,
	 * roleDtoList }; return results; }
	 *//**
	 * 显示信息。
	 */
	/*
	 * private List<RoleDtoVO> showFromRole(String staffId) { List<RoleDtoVO>
	 * roleDtoList = new ArrayList<RoleDtoVO>();
	 * 
	 * if (!StringUtil.isEmpty(staffId)) { Object[] res =
	 * this.roleService.queryByStaffId(staffId, 0, Integer.MAX_VALUE);
	 * List<FbrpSecRole> listtarg = (List) res[0]; for (int i = 0; i <
	 * listtarg.size(); i++) { roleDtoList.add(new RoleDtoVO(listtarg.get(i)));
	 * } } this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
	 * FbrpCommonAuditLog.LOGTYPE_SELECT, "通过人员[" + staffId + "]获取人员成员"); return
	 * roleDtoList; }
	 *//**
	 * 角色是否已经选择。
	 * 
	 * @param role
	 *            RoleDtoVO
	 * @param list
	 *            List<RoleDtoVO>
	 * @return boolean
	 */
	/*
	 * private boolean isExit(RoleDtoVO role, List<RoleDtoVO> list) { for (int i
	 * = 0; i < list.size(); i++) { if
	 * (list.get(i).getId().equals(role.getId())) { return true; } } return
	 * false; }
	 */

}
