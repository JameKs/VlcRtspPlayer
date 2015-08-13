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

	public abstract List findMenuByUserId(String userId);

}