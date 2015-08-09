package com.mqm.frame.sys.user.controller;

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

@Controller
@RequestMapping("/yhgl")
public class UserController extends DefaultController {

	private static final long serialVersionUID = -1097881755395101376L;
	
	@Resource
	private IUserService userService;
	
	@RequestMapping(value="yhgl.do" , params="main")
	public String ryxxMain(User user , ModelMap map , HttpServletRequest req){
		return "/admin/xtgl/yhgl/yhxx";
	};
	
	@RequestMapping(value="yhgl.do" , params="insert")
	public String add(User user , ModelMap map , HttpServletRequest req){
		User sessionUser = (User)req.getSession().getAttribute("user");
		user.setCjr(sessionUser.getLoginId());
		userService.insert(user);
		return "{success:true,msg:'添加成功！'}";
	};
	
	@RequestMapping(value="yhgl.do" , params="delete")
	public String delete(String id , ModelMap map , HttpServletRequest req){
		userService.delete(id);
		return "{success:true,msg:'添加成功！'}";
	}
	
	@RequestMapping(value="yhgl.do" , params="update")
	public String update(User user , ModelMap map , HttpServletRequest req){
		User sessionUser = (User)req.getSession().getAttribute("user");
		user.setXgr(sessionUser.getLoginId());
		userService.update(user);
		return "{success:true,msg:'添加成功！'}";
	}
	
	@RequestMapping(value="yhgl.do" , params="findById")
	public String findById(String id , ModelMap map , HttpServletRequest req){
		User user = userService.findById(id);
		map.put("user", user );
		return "{success:true,msg:'添加成功！'}";
	}
	
	@RequestMapping(value="yhgl.do" , params="findList")
	@ResponseBody
	public String find(User user , ModelMap map , HttpServletRequest req){
		int pageIndex = super.getPageIndex(req);
		int pageSize = super.getPageSize(req);
        List list = userService.findList(user,pageIndex,pageSize);
        long count = userService.findListCount(user);
        String jsonStr = StringUtil.pageListToJson(list, count);
		return jsonStr;
	};

}
