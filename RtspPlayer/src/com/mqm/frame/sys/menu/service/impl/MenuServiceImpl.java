package com.mqm.frame.sys.menu.service.impl;

import java.util.List;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.menu.dao.IMenuDao;
import com.mqm.frame.sys.menu.service.IMenuService;
import com.mqm.frame.sys.menu.vo.MenuVO;

public class MenuServiceImpl extends DefaultServiceImpl<MenuVO> implements IMenuService<MenuVO> {

	private IMenuDao menuDao;

	/**
	 * 查询用户菜单
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List findMenuByUserId(String userId) {
		List allNodes = menuDao.findMenuByUserId(userId);
		return allNodes;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
}
