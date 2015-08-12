package com.mqm.frame.sys.menu.service.impl;

import java.util.List;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.menu.dao.IMenuDao;
import com.mqm.frame.sys.menu.service.IMenuService;
import com.mqm.frame.sys.menu.vo.Cdxx;

public class MenuServiceImpl extends DefaultServiceImpl<Cdxx> implements IMenuService<Cdxx> {

	private IMenuDao cdxxDao;

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#findAll()
	 */
	@Override
	public List findAll(String hasRoot) {
		return cdxxDao.findAll(hasRoot);
	}
	
	/**
	 * 查询用户菜单
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List findAllUserMenu(String userId) {
		List allNodes = cdxxDao.findAllUserMenu(userId);
		return allNodes;
	}

	@Override
	public void setCdxxDao(IMenuDao cdxxDao) {
		this.cdxxDao = cdxxDao;
	}
	
}
