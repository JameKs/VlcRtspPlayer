/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.mqm.frame.common;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.mqm.frame.common.Converter.DateConverter;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.sys.role.vo.Role;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * <pre>
 * Controller的基类，用于公用方法的封装
 * <pre>
 * @author meihu2007@sina.com
 * 2015年8月16日
 */
public class DefaultController {
	
	
	@InitBinder  
    protected void initBinder(HttpServletRequest request,  
                                  ServletRequestDataBinder binder) throws Exception {  
        //对于需要转换为Date类型的属性，使用DateEditor进行处理  
        binder.registerCustomEditor(Date.class, new DateConverter());  
    } 
	
	/**
	 * 获得登录的用户
	 * @return
	 */
	public User getUser(){
		return (User)ContextUtil.get(BaseConstants.USER_PROFILE,ContextUtil.SCOPE_SESSION);
	}
	
	/**
	 * 判断登录的用户是不是管理员
	 * @return
	 */
	public boolean isAdmin(){
		User user = (User)ContextUtil.get(BaseConstants.USER_PROFILE,ContextUtil.SCOPE_SESSION);
		List<Role> roles = (List<Role>)user.getRoles();
		for(Role role: roles){
			if("SUPADMIN".equals(role.getCode())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得当前页
	 * @param req
	 * @return
	 */
	protected int getPageIndex(HttpServletRequest req){
		String pageIndex = req.getParameter("page");
		if(StringUtils.isNotEmpty(pageIndex)){
			return Integer.parseInt(pageIndex);
		}
		return 0;
	}
	
	/**
	 * 获得每页记录数
	 * @param req
	 * @return
	 */
	protected int getPageSize(HttpServletRequest req){
		String limit = req.getParameter("limit");
		if(StringUtils.isNotEmpty(limit)){
			return Integer.parseInt(limit);
		}
		return 0;
	}
}
