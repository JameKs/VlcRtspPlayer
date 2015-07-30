/**
 * JsglController.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jssq.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icanft.common.util.StringUtil;
import com.icanft.jsgl.vo.Role;
import com.icanft.jssq.service.IJssqService;
import com.icanft.jssq.vo.CdxxRole;
import com.icanft.xtgl.yhgl.vo.User;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/jsxx")
public class JssqController {

	@Resource
	private IJssqService jssqService;

	/**
	 * 进入角色授权的主界面
	 * 
	 * @param map
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jssq.do", params = "main")
	public String main(ModelMap map, Role role, HttpServletRequest request) {
		return "/admin/jssq/jssq";
	}

	/**
	 * 按条件查询角色列表
	 * 
	 * @param map
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jssq.do", params = "findList")
	@ResponseBody
	public String findList(ModelMap map, Role role, HttpServletRequest request) {
		List list = jssqService.findList(role);
		int count = jssqService.findListCount(role);
		String jsonString = StringUtil.pageListToJson(list, count);
		return jsonString;
	}

	/**
	 * 根据角色查询出选择菜单树
	 * 
	 * @param map
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jssq.do", params = "findRoleMenuTree")
	@ResponseBody
	public List<Map<String,Object>> findRoleMenuTree(ModelMap map, CdxxRole cdxxRole,
			HttpServletRequest req) {
		String roleId = req.getParameter("roleId");
		List<Map<String,Object>> list = (ArrayList<Map<String,Object>>)jssqService.findRoleMenuTree(roleId);
		return list;
	}

	/**
	 * 更新角色菜单树权限
	 * 
	 * @param map
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jssq.do", params = "save")
	@ResponseBody
	public String save(ModelMap map, String ids , String roleId, 
			HttpServletRequest req, 
			@ModelAttribute User user) {
		jssqService.save(ids,roleId,user.getLoginId());
		return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
	}
	
	/**
	 * 根据角色查询出选择菜单树
	 * 
	 * @param map
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "jssq.do", params = "findRoleMenuZtree")
	@ResponseBody
	public List findRoleMenuZtree(ModelMap map, CdxxRole cdxxRole,
			HttpServletRequest req) {
		String roleId = req.getParameter("roleId");
		List list = jssqService.findRoleMenuZtree(roleId);
		return list;
	}

}
