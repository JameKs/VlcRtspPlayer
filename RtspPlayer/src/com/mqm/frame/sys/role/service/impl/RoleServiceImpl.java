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
	
	IRoleDao<Role> roleDao;
	
	@Override
	public List<Role> findByUserLoginId(String loginId) {
		List<Role> findByUserLoginId = roleDao.findByUserLoginId(loginId);
		return findByUserLoginId;
	}
	
	@Override
	public void insert(Role t) {
		roleDao.insert(t);
	}

	@Override
	public void deleteById(String id) {
		roleDao.deleteById(id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		for(String id : ids){
			this.deleteById(id);
		}
	}
	
	@Override
	public void update(Role t) {
		roleDao.update(t);
	}

	@Override
	public Role findById(String id) {
		return (Role)roleDao.findById(id);
	}

	@Override
	public List<Role> findList(Role t) {
		return roleDao.findList(t);
	}

	@Override
	public List<Role> findListPage(Role t, int pageIndex, int pageSize) {
		return roleDao.findListPage(t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(Role t) {
		return roleDao.findListCount(t);
	}
	
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	/**
	 * @param jsglDao
	 *            the jsglDao to set
	 */
	public void setRoleDao(IRoleDao<Role> roleDao) {
		this.roleDao = roleDao;
	}

}
