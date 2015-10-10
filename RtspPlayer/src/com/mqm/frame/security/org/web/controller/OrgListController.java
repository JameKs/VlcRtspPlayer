/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.org.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.org.service.IOrgService;
import com.mqm.frame.security.org.vo.FbrpSecOrg;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.staff.vo.StaffDtoVO;
import com.mqm.frame.util.exception.FbrpException;
import com.mqm.frame.util.web.JsonOrgTreeVo;

/**
 * 
 * <pre>
 * 机构管理控制器类。
 * </pre>
 * 
 * @author zouyongqiao zouyongqiao@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("security/org")
public class OrgListController extends FbrpBaseController {

	private static final Log log = LogFactory.getLog(OrgListController.class);

	@Resource
	private IStaffService staffService;

	@Resource
	private IOrgService orgService;

	/**
	 * 初始化。
	 * 
	 * @param map
	 *            ModelMap
	 * @param request
	 * 				HttpServletRequest
	 * @return String
	 */
	@RequestMapping("orgManage.do")
	public String init(HttpServletRequest request, ModelMap map) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpSecStaff> pr = this.staffService.pagedQuery(new PMap(),
				pageIndex, pageSize);
		PMap pm = new PMap();
		pm.eq("delFlag", FbrpSecOrg.DEL_FLAG_NORMAL);
		pm.isNull("parentId");
		List<FbrpSecOrg> orgManage = orgService.findBy(pm);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (FbrpSecOrg org : orgManage) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("code", org.getCode());
			m.put("name", org.getName());
			m.put("orgType", org.getOrgType());
			m.put("orgLevel", org.getOrgLevel());
			m.put("id", org.getId());
			m.put("pId", org.getParentId());
			m.put("isParent", true);
			list.add(m);
		}
		map.put("treeList", list);
		map.put("pr", pr);
		return "security/org/orgManage";
	}

	/**
	 * 异步加载机构树。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "orgManage.do", params = "loadOrgNode")
	public List<Map<String, Object>> loadOrgNode(HttpServletRequest request) {
		PMap pm = new PMap();
		String pId = request.getParameter("id");
		pm.eq("delFlag", FbrpSecOrg.DEL_FLAG_NORMAL);
		if (pId == null) {
			pm.isNull("parentId");
		} else {
			pm.eq("parentId", pId);
		}
		List<FbrpSecOrg> orgManage = this.orgService.findBy(pm);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (FbrpSecOrg org : orgManage) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("code", org.getCode());
			m.put("name", org.getName());
			m.put("orgType", org.getOrgType());
			m.put("orgLevel", org.getOrgLevel());
			m.put("id", org.getId());
			m.put("pId", org.getParentId());
			m.put("isParent", true);
			list.add(m);
		}
		return list;
	}

	/**
	 * 根据条件查询机构人员。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "orgManage.do", params = "query")
	public PagedResult<FbrpSecStaff> query(HttpServletRequest request) {
		FbrpSecStaff searchStaffVO = new FbrpSecStaff();
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String id = request.getParameter("id");
		searchStaffVO.setOrgid(id);
		searchStaffVO.setCode(code);
		searchStaffVO.setName(name);
		searchStaffVO.setOrgCode(this.getUser().getOrgId());
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpSecStaff> pr = this.staffService.pagedQuery(searchStaffVO, pageIndex, pageSize, Order.ASC("code"),
				Order.ASC("name"));
		return pr;
	}

	/**
	 * 点击选择机构时，获取机构详细信息。
	 * 
	 * @param id
	 *            String
	 * 
	 * @return FbrpSecOrg
	 */
	@RequestMapping(value = "orgManage.do", params = "getOrgDetile", produces = "application/json")
	public @ResponseBody
	FbrpSecOrg getOrgDetile(@RequestParam String id) {
		if (!"0".equals(id)) {
			FbrpSecOrg org = this.orgService.find(id);
			if (!"0".equals(org.getParentId()) && org.getParentId() != null) {
				FbrpSecOrg parent = this.orgService.find(org.getParentId());
				org.setExt3(parent.getName());
			} else {
				org.setExt3("机构树");
			}
			return org;
		}
		return null;
	}

	/**
	 * 机构树。
	 * 
	 * @return TreeNodeEx
	 */
	@RequestMapping(value = "orgManage.do", params = "getOrgTree")
	public @ResponseBody
	List<JsonOrgTreeVo> getOrgTree() {
		List<FbrpSecOrg> orgManage = orgService.getChildOrgById(null);
		List<JsonOrgTreeVo> jsonTrees = new ArrayList<JsonOrgTreeVo>();
		JsonOrgTreeVo rootNode = new JsonOrgTreeVo("0", "0", "机构树", false,
				null, "0", "0", "0", "0");
		jsonTrees.add(rootNode);
		for (FbrpSecOrg secOrg : orgManage) {
			if (secOrg.getParentId() == null) {
				secOrg.setParentId("0");
			}
			JsonOrgTreeVo jsonTree = new JsonOrgTreeVo();
			jsonTree.setId(secOrg.getId());
			jsonTree.setpId(secOrg.getParentId());
			jsonTree.setName(secOrg.getName());
			jsonTree.setCode(secOrg.getCode());
			jsonTree.setOrgType(secOrg.getOrgType());
			jsonTree.setOrgLevel(secOrg.getOrgLevel());

			if (!"0".equals(secOrg.getParentId())
					&& secOrg.getParentId() != null) {
				FbrpSecOrg parent = this.orgService.find(secOrg.getParentId());
				jsonTree.setParentName(parent.getName());
			}
			jsonTree.setClick("javascript:getOrgDetile('" + secOrg.getId()
					+ "');");
			jsonTrees.add(jsonTree);
		}
		return jsonTrees;
	}

	/**
	 * 获取完整机构树。
	 * 
	 * @return List<JsonOrgTreeVo>
	 */
	@RequestMapping(value = "orgManage.do", params = "getOrgTreeAll")
	public @ResponseBody
	List<JsonOrgTreeVo> getOrgTreeAll() {
		List<FbrpSecOrg> orgManage = orgService.findBy("delFlag",
				FbrpSecOrg.DEL_FLAG_NORMAL);
		List<JsonOrgTreeVo> jsonTrees = new ArrayList<JsonOrgTreeVo>();
		JsonOrgTreeVo rootNode = new JsonOrgTreeVo("0", "0", "机构树", false,
				null, "0", "0", "0", "0");
		jsonTrees.add(rootNode);
		for (FbrpSecOrg secOrg : orgManage) {
			if (secOrg.getParentId() == null) {
				secOrg.setParentId("0");
			}
			JsonOrgTreeVo jsonTree = new JsonOrgTreeVo();
			jsonTree.setId(secOrg.getId());
			jsonTree.setpId(secOrg.getParentId());
			jsonTree.setName(secOrg.getName());
			jsonTree.setCode(secOrg.getCode());
			jsonTree.setOrgType(secOrg.getOrgType());
			jsonTree.setOrgLevel(secOrg.getOrgLevel());

			if (!"0".equals(secOrg.getParentId())
					&& secOrg.getParentId() != null) {
				FbrpSecOrg parent = this.orgService.find(secOrg.getParentId());
				jsonTree.setParentName(parent.getName());
			}
			jsonTree.setClick("javascript:showSelPanel('" + secOrg.getId()
					+ "');");
			jsonTrees.add(jsonTree);
		}
		return jsonTrees;
	}

	/**
	 * 保存机构。
	 * 
	 * @param updateFbrpSecOrgVO FbrpSecOrg
	 * 
	 * @return String
	 */
	@RequestMapping(value = "orgManage.do", params = "save")
	public String saveFbrpSecOrg(FbrpSecOrg updateFbrpSecOrgVO) {
		UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		String orgId = updateFbrpSecOrgVO.getId();
		String parentId = updateFbrpSecOrgVO.getParentId();
		try {
			if (orgId == null || "".equals(orgId)) {
				this.orgService.save(updateFbrpSecOrgVO, user);
			} else if (!(orgId).equals(parentId)) {
				this.orgService.update(updateFbrpSecOrgVO, user);
			}
			this.auditLog(FbrpCommonAuditLog.BIZTYPE_ORG,
					FbrpCommonAuditLog.LOGTYPE_INSERT, "新增机构");
		} catch (FbrpException e) {
			log.error("机构编号已存在!");
			this.exceptionMsg.setMainMsg("机构编码已存在，请使用其它编码!");
		}
		return "redirect:orgManage.do";
	}

	/**
	 * 展示机构人员面板。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return PagedResult<FbrpSecStaff>
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "orgManage.do", params = "showSelPanel")
	public PagedResult<FbrpSecStaff> showSelPanel(HttpServletRequest request) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		String orgId = request.getParameter("orgId");
		Object[] ret = this.staffService.queryStaffByOrg(new FbrpSecStaff(),
				pageIndex, pageSize, orgId);
		PagedResult<FbrpSecStaff> pr = new PagedResult<FbrpSecStaff>(
				(List<FbrpSecStaff>) ret[0], (Integer) ret[1], pageIndex,
				pageSize);
		return pr;
	}

	/**
	 * 删除机构。
	 * 
	 * @param id
	 *            String
	 * @return String
	 */
	@RequestMapping(value = "orgManage.do", params = "deleteOrg")
	public String deleteOrg(String id) {
		UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		FbrpSecOrg fbrpSecOrg = this.orgService.find(id);
		fbrpSecOrg.setDelFlag(ValueObject.DEL_FLAG_DELETED);
		orgService.deleteOrgFlag(fbrpSecOrg, user);
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_ORG,
				FbrpCommonAuditLog.LOGTYPE_DELETE, "删除机构");
		return "redirect:orgManage.do";
	}

	/**
	 * 删除具体某机构里的人员。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return PagedResult<FbrpSecStaff>
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "orgManage.do", params = "deleteRow")
	public PagedResult<FbrpSecStaff> deleteRow(HttpServletRequest request) {
		String orgId = request.getParameter("orgId");
		String staffId = request.getParameter("staffId");
		this.orgService.deleteOrgMemberByOrgidAndStaffid(orgId, staffId);
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		Object[] ret = this.staffService.queryStaffByOrg(new FbrpSecStaff(),
				pageIndex, pageSize, orgId);
		PagedResult<FbrpSecStaff> pr = new PagedResult<FbrpSecStaff>(
				(List<FbrpSecStaff>) ret[0], (Integer) ret[1], pageIndex,
				pageSize);
		return pr;
	}

	/**
	 * 将FbrpSecStaffVO集合转换为StaffDto集合。
	 * 
	 * @param list
	 *            List<FbrpSecStaff>
	 * 
	 * @return List<StaffDto>
	 */
	private List<StaffDtoVO> convert2DtoList(List<FbrpSecStaff> list) {
		List<StaffDtoVO> ret = new ArrayList<StaffDtoVO>();
		if (list == null) {
			return ret;
		}

		for (FbrpSecStaff vo : list) {
			ret.add(new StaffDtoVO(vo));
		}
		return ret;
	}

}
