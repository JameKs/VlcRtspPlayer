/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.mqm.frame.sys.menu.dao.impl;

import java.util.List;
import java.util.Map;

import com.mqm.frame.common.DefaultDaoImpl;
import com.mqm.frame.sys.menu.dao.IMenuDao;
import com.mqm.frame.sys.menu.vo.Cdxx;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年8月12日
 */
public class MenuDaoImpl extends DefaultDaoImpl<Cdxx> implements IMenuDao<Cdxx> {





	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#findByParentId(java.lang.String)
	 */
	@Override
	public List findByParentId(String pId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#findAll(java.lang.String)
	 */
	@Override
	public List findAll(String hasRoot) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#findAllUserMenu(java.lang.String)
	 */
	@Override
	public List findAllUserMenu(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#getNodesByIdAndUserId(java.util.Map)
	 */
	@Override
	public List getNodesByIdAndUserId(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#getChildrenNodes(java.lang.String)
	 */
	@Override
	public List getChildrenNodes(String pId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#getChildrenNodesCount(java.lang.String)
	 */
	@Override
	public long getChildrenNodesCount(String pId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#getWdscjByUserId(java.util.Map)
	 */
	@Override
	public List getWdscjByUserId(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#getAllDept()
	 */
	@Override
	public List getAllDept() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#getAllGw()
	 */
	@Override
	public List getAllGw() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.menu.dao.ICdxxDao#getAllUser()
	 */
	@Override
	public List getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
