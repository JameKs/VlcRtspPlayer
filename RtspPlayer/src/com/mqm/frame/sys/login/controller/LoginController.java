package com.mqm.frame.sys.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.sys.login.service.ILoginService;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.constants.BaseConstants;

@Controller
@RequestMapping("/login")
@SessionAttributes("user")
public class LoginController {

	private static final Logger log = Logger.getLogger(LoginController.class);

	@Resource
	private ILoginService loginService;

	@RequestMapping(value = "longin.do")
	public String login(ModelMap map, User user, HttpServletRequest request)
			throws Exception {
		User currentUser = loginService.login(user);
		//request.getSession().setAttribute("user", currentUser);
		map.addAttribute("user", currentUser);
		if (currentUser == null) {
			map.put("msg", "用户帐号或密码错误！");
			return "forward:/";
		} else {
			request.getSession().setAttribute("user", currentUser);
		}
		return "public/frame";
	}
	
	@RequestMapping(value = "main.do")
	public String main(ModelMap map, User user, HttpServletRequest request)
			throws Exception {
		return "public/frame";
	}
	
	@RequestMapping(value = "logout")
	public String logout(ModelMap map, User user, HttpServletRequest request)
			throws Exception {
		String page = (String) ContextUtil.get(BaseConstants.BEFORE_SEL_APP, ContextUtil.SCOPE_SESSION);
		String url = "j_spring_security_logout";
		if (StringUtils.hasText(page)) {
			return "redirect" + url + ">spring-security-redirect" + page ;
		} 
		return "redirect" + url;
	}

}
