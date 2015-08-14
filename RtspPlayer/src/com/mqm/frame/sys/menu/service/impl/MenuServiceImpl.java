package com.mqm.frame.sys.menu.service.impl;

import java.util.List;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.menu.dao.IMenuDao;
import com.mqm.frame.sys.menu.service.IMenuService;
import com.mqm.frame.sys.menu.vo.MenuVO;

public class MenuServiceImpl implements IMenuService<MenuVO> {

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
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(MenuVO t) {
		menuDao.insert("insert", t);
	}

	@Override
	public void deleteById(String id) {
		menuDao.deleteById("delete", id);
	}

	@Override
	public void update(MenuVO t) {
		menuDao.update("update", t);
	}

	@Override
	public MenuVO findById(String id) {
		return (MenuVO)menuDao.findById("findById", id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(MenuVO t) {
		return menuDao.findList("findList", t);
	}

	@Override
	public List findPageList(MenuVO t, int pageIndex, int pageSize) {
		return menuDao.findPageList("findList", t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(MenuVO t) {
		return menuDao.findListCount("findList", t);
	}
	
	@Override
	public List findAll() {
		return menuDao.findAll("findAll");
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
}
