package com.mqm.frame.sys.menu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.common.DefaultController;
import com.mqm.frame.sys.menu.service.IMenuService;
import com.mqm.frame.sys.menu.vo.JsonTree;
import com.mqm.frame.sys.menu.vo.MenuVO;
import com.mqm.frame.sys.user.vo.User;

@Controller
@RequestMapping("/menu")
public class MenuController extends DefaultController {

	private static final Logger logger = Logger.getLogger(MenuController.class);

	@Resource(name = "menuService")
	private IMenuService menuService;
	
	@RequestMapping(value="menu.do")
	public String main(ModelMap map, HttpServletRequest req) {

		List<JsonTree> jsonTrees = menuService.getTree();
		map.put("menus", JSONArray.fromObject(jsonTrees).toString());
		
		List<Map<String, Object>> pJsonTree = menuService.getPTree();
		map.put("pMenus", JSONArray.fromObject(pJsonTree).toString());
		
		return "/sys/menu/menu";
	}
	
	@RequestMapping(value="menu.do",params="reloadTree")
	@ResponseBody
	public List reloadTree(ModelMap map, HttpServletRequest req) {
		List<JsonTree> jsonTrees = menuService.getTree();
		return jsonTrees;
	}
	
	@RequestMapping(value="menu.do",params="reloadPTree")
	@ResponseBody
	public List<Map<String, Object>> reloadPTree(ModelMap map, HttpServletRequest req) {
		List<Map<String, Object>> pJsonTree = menuService.getPTree();
		return pJsonTree;
	}
	
	@RequestMapping(value = "menu.do", params = "new")
	@ResponseBody
	public String add(ModelMap map, HttpServletRequest req, MenuVO vo) {
		User user = this.getUser();
		vo.setCjr(user.getLoginId());
		vo.setpId(vo.getpId());
		vo.setLeaf("1".equals(vo.getLeaf())?"1":"0");
		menuService.insert(vo);
		return "{success:true,msg:'新增成功'}";
	};
	
	@RequestMapping(value = "menu.do", params = "delete")
	@ResponseBody
	public String deleteById(ModelMap map, HttpServletRequest req, String id) {
		menuService.deleteById(id);
		return "{success:true,msg:'删除成功'}";
	}
	
	@RequestMapping(value = "menu.do", params = "update")
	@ResponseBody
	public String update(ModelMap map, HttpServletRequest req, MenuVO vo) {
		User user = this.getUser();
		vo.setXgr(user.getLoginId());
		menuService.update(vo);
		return "{success:true,msg:'更新成功！'}";
	}
	
	//获取整个菜单树
	@RequestMapping(value = "menu.do", params = "getTree" )
	@ResponseBody
	public List getMenuJsonTree(ModelMap map, HttpServletRequest req) {
		List<Map<String, Object>> jsonTree = menuService.findAll();
		return jsonTree;
	}

	/**
	 * @param menuService
	 *            the menuService to set
	 */
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

}
