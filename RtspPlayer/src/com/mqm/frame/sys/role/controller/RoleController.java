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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.common.DefaultController;
import com.mqm.frame.sys.role.service.IRoleService;
import com.mqm.frame.sys.role.vo.Role;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/role")
public class RoleController extends DefaultController {
	
	@Resource
	private IRoleService<Role> roleService; 
	
	@RequestMapping(value="role.do")
	public String main(ModelMap map , HttpServletRequest req) {
		return "/sys/role/role";
	}
	
	@RequestMapping(value="role.do" , params="insert")
	@ResponseBody
	public String insert(ModelMap map, Role role, HttpServletRequest req) {
		User user = this.getUser();
		role.setCjr(user.getLoginId());
		roleService.insert(role);
		return BaseConstants.INSERT_SUCC;
	}
	
	@RequestMapping(value="role.do" , params="deleteById")
	@ResponseBody
	public String deleteById(ModelMap map, String id, HttpServletRequest req) {
		roleService.deleteById(id);
		return BaseConstants.DELETE_SUCC;
	}
	
	@RequestMapping(value="role.do" , params="deleteByIds")
	@ResponseBody
	public String deleteByIds(String ids , ModelMap map , HttpServletRequest req){
		String[] temIds = ids.split(",");
		roleService.deleteByIds(temIds);
		return BaseConstants.DELETE_SUCC;
	}
	
	@RequestMapping(value="role.do" , params="update")
	@ResponseBody
	public String update(ModelMap map, Role role, HttpServletRequest req) {
		User user = this.getUser();
		role.setXgr(user.getLoginId());
		roleService.update(role);
		return BaseConstants.UPDATE_SUCC;
	}
	
	@RequestMapping(value="role.do" , params="findById")
	@ResponseBody
	public Role findById(ModelMap map, String id, HttpServletRequest req) {
		Role role = (Role)roleService.findById(id);
		return role;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="role.do" , params="findList")
	@ResponseBody
	public String findList(ModelMap map, Role role, HttpServletRequest req) {
		int pageIndex = this.getPageIndex(req);
		int pageSize = this.getPageSize(req);
		List list = roleService.findListPage(role, pageIndex, pageSize);
		long count = roleService.findListCount(role);
		String jsonString = StringUtil.pageListToJson(list,count);
		return jsonString;
	}

}
