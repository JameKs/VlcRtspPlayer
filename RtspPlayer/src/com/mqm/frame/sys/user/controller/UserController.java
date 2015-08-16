package com.mqm.frame.sys.user.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.common.DefaultController;
import com.mqm.frame.sys.user.service.IUserService;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;

@Controller
@RequestMapping("/user")
public class UserController extends DefaultController {

	private static final long serialVersionUID = -1097881755395101376L;
	
	@Resource
	private IUserService userService;
	
	@RequestMapping(value="user.do")
	public String ryxxMain(User user , ModelMap map , HttpServletRequest req){
		return "/sys/user/user";
	};
	
	@RequestMapping(value="user.do" , params="insert")
	@ResponseBody
	public String insert(User user , ModelMap map , HttpServletRequest req){
		User sessionUser = this.getUser();
		user.setCjr(sessionUser.getLoginId());
		user.setPassword("1");
		if(user.getPhone() == null || "".equals(user.getPhone())){
			user.setPhone(" ");
		}
		if(user.getEmail() == null || "".equals(user.getEmail())){
			user.setEmail(" ");
		}
		
		userService.insert(user);
		return BaseConstants.INSERT_SUCC;
	};
	
	@RequestMapping(value="user.do" , params="deleteById")
	@ResponseBody
	public String deleteById(String id , ModelMap map , HttpServletRequest req){
		userService.deleteById(id);
		return BaseConstants.DELETE_SUCC;
	}
	
	@RequestMapping(value="user.do" , params="deleteByIds")
	@ResponseBody
	public String deleteByIds(String ids , ModelMap map , HttpServletRequest req){
		String[] temIds = ids.split(",");
		userService.deleteByIds(temIds);
		return BaseConstants.DELETE_SUCC;
	}
	
	@RequestMapping(value="user.do" , params="update")
	@ResponseBody
	public String update(User user , ModelMap map , HttpServletRequest req){
		User sessionUser = this.getUser();
		user.setXgr(sessionUser.getLoginId());
		user.setXgSj(new Date());
		userService.update(user);
		return BaseConstants.UPDATE_SUCC;
	}
	
	@RequestMapping(value="user.do" , params="findById")
	@ResponseBody
	public User findById(String id , ModelMap map , HttpServletRequest req){
		User user = (User)userService.findById(id);
		return user;
	}
	
	@RequestMapping(value="user.do" , params="findList")
	@ResponseBody
	public String find(User user , ModelMap map , HttpServletRequest req){
		int pageIndex = super.getPageIndex(req);
		int pageSize = super.getPageSize(req);
        List list = userService.findPageList(user,pageIndex,pageSize);
        long count = userService.findListCount(user);
        String jsonStr = StringUtil.pageListToJson(list, count);
		return jsonStr;
	};

}
