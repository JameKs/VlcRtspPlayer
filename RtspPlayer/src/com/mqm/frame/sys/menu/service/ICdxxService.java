/**
 * ICdxxService.java
 * 2015
 * 2015年5月14日
 */
package com.mqm.frame.sys.menu.service;

import java.util.List;
import java.util.Map;

import com.mqm.frame.sys.menu.dao.ICdxxDao;
import com.mqm.frame.sys.menu.vo.Cdxx;

/**
 * @author Administrator
 *
 */
public interface ICdxxService {

	public abstract void insert(Cdxx cdxx);

	public abstract void delete(String id);

	public abstract void update(Cdxx cdxx);

	public abstract Cdxx findById(String id);

	public abstract List findAll(String hasRoot);
	
	public abstract List findAllUserMenu(String userId);

	
	/**
	 * @param cdxxDao
	 *            the cdxxDao to set
	 */
	public abstract void setCdxxDao(ICdxxDao cdxxDao);


}