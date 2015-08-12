package com.mqm.frame.sys.menu.controller;

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

import com.mqm.frame.sys.menu.service.IMenuService;
import com.mqm.frame.sys.menu.vo.Cdxx;
import com.mqm.frame.sys.menu.vo.JsonTree;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.constants.BaseConstants;

@Controller
@RequestMapping("/cdxx")
public class MenuController {

	private static final Logger logger = Logger.getLogger(MenuController.class);

	@Resource(name = "cdxxService")
	private IMenuService cdxxService;
	
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
		cdxxService.deleteById(id);
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
	
	//获取整个菜单树
	@RequestMapping(value = "cdgl.do", params = "getTree" )
	@ResponseBody
	public List getMenuJsonTree(ModelMap map, HttpServletRequest req) {
		List<Map<String, Object>> jsonTree = cdxxService.findAll(BaseConstants.TREE_HAS_NO_ROOT);
		return jsonTree;
	}

	/**
	 * @param cdxxService
	 *            the cdxxService to set
	 */
	public void setCdxxService(IMenuService cdxxService) {
		this.cdxxService = cdxxService;
	}

}
