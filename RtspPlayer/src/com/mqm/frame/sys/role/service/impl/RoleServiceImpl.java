/**
 * JsglServiceImpl.java
 * 2015
 * 2015年5月18日
 */
package com.mqm.frame.sys.role.service.impl;

import java.util.List;

import com.mqm.frame.sys.role.dao.IRoleDao;
import com.mqm.frame.sys.role.service.IRoleService;
import com.mqm.frame.sys.role.vo.Role;

/**
 * @author Administrator
 * 
 */
public class RoleServiceImpl implements
		IRoleService<Role> {
	
	IRoleDao roleDao;
	
	@Override
	public List findByUserLoginId(String loginId) {
		return roleDao.findByUserLoginId(loginId);
	}
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(Role t) {
		roleDao.insert("insert", t);
	}

	@Override
	public void deleteById(String id) {
		roleDao.deleteById("deleteById", id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		for(String id : ids){
			this.deleteById(id);
		}
	}
	
	@Override
	public void update(Role t) {
		roleDao.update("update", t);
	}

	@Override
	public Role findById(String id) {
		return (Role)roleDao.findById("findById", id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(Role t) {
		return roleDao.findList("findList", t);
	}

	@Override
	public List findPageList(Role t, int pageIndex, int pageSize) {
		return roleDao.findPageList("findList", t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(Role t) {
		return roleDao.findListCount("findListCount", t);
	}
	
	@Override
	public List findAll() {
		return roleDao.findAll("findAll");
	}

	/**
	 * @param jsglDao
	 *            the jsglDao to set
	 */
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
