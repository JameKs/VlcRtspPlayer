package com.icanft.cdgl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icanft.cdgl.service.ICdxxService;
import com.icanft.cdgl.vo.Cdxx;
import com.icanft.cdgl.vo.JsonTree;
import com.icanft.common.constant.BaseConstants;
import com.icanft.xtgl.yhgl.vo.User;

@Controller
@RequestMapping("/cdxx")
public class CdxxController {

	private static final Logger logger = Logger.getLogger(CdxxController.class);

	@Resource(name = "cdxxService")
	private ICdxxService cdxxService;
	
	@RequestMapping(value="cdgl",params="main")
	public String main(ModelMap map, HttpServletRequest req) {
		List<Cdxx> menus = cdxxService.findAll(BaseConstants.TREE_HAS_ROOT);
		List<JsonTree> jsonTrees = new ArrayList<JsonTree>();

		for (Cdxx vo : menus) {
			JsonTree jsonTree = new JsonTree();
			jsonTree.setId(vo.getId());
			jsonTree.setpId(vo.getpId());
			jsonTree.setName(vo.getCnName());

			if ("0".equals(jsonTree.getpId())) {
				jsonTree.setOpen(true);
			}
			jsonTree.getExts().put("url", vo.getUrl());
			jsonTree.getExts().put("icon", vo.getImageUrl());
			jsonTree.getExts().put("cnName", vo.getCnName());
			jsonTree.getExts().put("enName", vo.getEnName());
			jsonTree.getExts().put("cdDm", vo.getCdDm());
			jsonTree.getExts().put("ccsd", vo.getCcsd().toString());
			jsonTree.getExts().put("ccsx", vo.getCcsx().toString());
			jsonTree.getExts().put("leaf", vo.getLeaf());
			jsonTrees.add(jsonTree);
		}
		map.put("menus", JSONArray.fromObject(jsonTrees).toString());
		return "/admin/cdgl/cdxx";
	}
	
	@RequestMapping(value = "cdgl.do", params = "new")
	@ResponseBody
	public String add(ModelMap map, HttpServletRequest req, Cdxx cdxx, HttpSession session) {
		User user = (User)session.getAttribute("user");
		cdxx.setCjr(user.getLoginId());
		cdxx.setpId(cdxx.getpId());
		cdxx.setLeaf("1".equals(cdxx.getLeaf())?"1":"0");
		cdxxService.insert(cdxx);
		return "{success:true,msg:'新增成功'}";
	};
	
	@RequestMapping(value = "cdgl.do", params = "delete")
	@ResponseBody
	public String deleteById(ModelMap map, HttpServletRequest req, String id) {
		cdxxService.delete(id);
		return "{success:true,msg:'删除成功'}";
	}
	
	@RequestMapping(value = "cdgl.do", params = "update")
	@ResponseBody
	public String update(ModelMap map, HttpServletRequest req, Cdxx cdxx) {
		User user = (User)req.getSession().getAttribute("user");
		cdxx.setXgr(user.getLoginId());
		cdxx.setLeaf("1".equals(cdxx.getLeaf())?"1":"0");
		cdxxService.update(cdxx);
		return "{success:true,msg:'更新成功！'}";
	}
	
	//获取用户菜单导航节点
//	@RequestMapping(value = "cdgl.do", params = "getMenuPanel")
//	@ResponseBody
//	public List getUserMenuPanel(ModelMap map, HttpServletRequest req) {
//		User user = (User)req.getSession().getAttribute("user");
//		HashMap<String, Object> hashMap = new HashMap();
//		hashMap.put("userId", user.getId());
//		hashMap.put("pId", "E1801EBE06D211E588790023540C477B");
//		List<Map<String, Object>> userMenu = cdxxService.getUserMenuPanel(hashMap);
//		return userMenu;
//	}
//	
//	//获取用户菜单导航节点的子树
//	@RequestMapping(value = "cdgl.do", params = "userMenuPanelTree")
//	@ResponseBody
//	public List getUserMenuPanelTree(ModelMap map, HttpServletRequest req) {
//		User user = (User)req.getSession().getAttribute("user");
//		HashMap<String, Object> hashMap = new HashMap();
//		hashMap.put("userId", user.getId());
//		hashMap.put("pId", req.getParameter("id"));
//		List<Map<String, Object>> userMenu = cdxxService.getUserMenuPanelTree(hashMap);
//		return userMenu;
//	}
	
	//获取用户收藏夹
//	@RequestMapping(value = "cdgl.do", params = "wdscj")
//	@ResponseBody
//	public List getUserScj(ModelMap map, HttpServletRequest req) {
//		User user = (User)req.getSession().getAttribute("user");
//
//		HashMap<String, Object> hashMap = new HashMap();
//		hashMap.put("userId", user.getId());
//		List<Map<String, Object>> tree = cdxxService.getWdscjByUserId(hashMap);
//		return tree;
//	}
	
	//获取整个菜单树
	@RequestMapping(value = "cdgl.do", params = "getTree" )
	@ResponseBody
	public List getMenuJsonTree(ModelMap map, HttpServletRequest req) {
		List<Map<String, Object>> jsonTree = cdxxService.getMenuJsonTree();
		//map.put("menu", jsonTree);
		return jsonTree;
	}
	
	//获取部门各位人员树
	@RequestMapping(value = "cdgl.do", params = "getDeptGwUserTree" )
	@ResponseBody
	public List getDeptGwUserTree(ModelMap map, HttpServletRequest req) {
		List<Map<String, Object>> jsonTree = cdxxService.getDeptGwUserTree();
		//map.put("menu", jsonTree);
		return jsonTree;
	}

	/**
	 * @param cdxxService
	 *            the cdxxService to set
	 */
	public void setCdxxService(ICdxxService cdxxService) {
		this.cdxxService = cdxxService;
	}

}
