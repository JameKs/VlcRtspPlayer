/**
 * JsglController.java
 * 2015
 * 2015年5月18日
 */
package com.mqm.frame.sys.role.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mqm.frame.sys.role.service.IRoleService;
import com.mqm.frame.sys.role.vo.Role;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.StringUtil;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/jsxx")
@SessionAttributes("user")
public class RoleController {
	
	@Resource
	private IRoleService roleService;
	
	@RequestMapping(value="jsgl.do" , params="main")
	public String main(ModelMap map , HttpServletRequest req) {
		return "/admin/jsgl/jsgl";
	}
	
	@RequestMapping(value="jsgl.do" , params="insert")
	@ResponseBody
	public String insert(ModelMap map, Role role, HttpServletRequest req, @ModelAttribute("user") User user) {
		role.setCjr(user.getLoginId());
		roleService.insert(role);
		return "{success:true,msg:'保存成功！'}";
	}
	
	@RequestMapping(value="jsgl.do" , params="delete")
	@ResponseBody
	public String delete(ModelMap map, String id, HttpServletRequest req) {
		roleService.deleteById(id);
		return "{success:true,msg:'删除成功！'}";
	}
	
	@RequestMapping(value="jsgl.do" , params="update")
	@ResponseBody
	public String update(ModelMap map, Role role, HttpServletRequest req, @ModelAttribute("user") User user) {
		role.setXgr(user.getLoginId());
		roleService.update(role);
		return "{success:true,msg:'更新成功！'}";
	}
	
	@RequestMapping(value="jsgl.do" , params="findById")
	@ResponseBody
	public Role findById(ModelMap map, String id, HttpServletRequest req) {
		Role role = (Role)roleService.findById(id);
		return role;
	}
	
	@RequestMapping(value="jsgl.do" , params="findList")
	@ResponseBody
	public String findList(ModelMap map, Role role, HttpServletRequest req) {
		List list = roleService.findList(role);
		long count = roleService.findListCount(role);
		String jsonString = StringUtil.pageListToJson(list,count);
		return jsonString;
	}

}
