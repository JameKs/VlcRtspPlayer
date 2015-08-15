/**
 * ICdxxService.java
 * 2015
 * 2015年5月14日
 */
package com.mqm.frame.sys.menu.service;

import java.util.List;
import java.util.Map;

import com.mqm.frame.common.IDefaultService;

/**
 * @author Administrator
 *
 */
public interface IMenuService<T> extends IDefaultService<T> {

	public abstract List findMenuByUserId(String userId);
	
	public List getTree();
	
	public List getPTree();
	
	public Map<String ,Object> getMenuTree(boolean isAdmin,String userId);

}