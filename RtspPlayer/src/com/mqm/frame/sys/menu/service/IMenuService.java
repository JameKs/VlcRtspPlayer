/**
 * ICdxxService.java
 * 2015
 * 2015年5月14日
 */
package com.mqm.frame.sys.menu.service;

import java.util.List;

import com.mqm.frame.common.IDefaultService;
import com.mqm.frame.sys.menu.dao.IMenuDao;

/**
 * @author Administrator
 *
 */
public interface IMenuService<T> extends IDefaultService<T> {


	public abstract List findAll(String hasRoot);
	
	public abstract List findAllUserMenu(String userId);

	
	/**
	 * @param cdxxDao
	 *            the cdxxDao to set
	 */
	public abstract void setCdxxDao(IMenuDao cdxxDao);


}