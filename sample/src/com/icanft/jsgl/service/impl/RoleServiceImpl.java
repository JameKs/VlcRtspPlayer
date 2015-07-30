/**
 * JsglServiceImpl.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jsgl.service.impl;

import java.util.List;

import com.icanft.jsgl.dao.IRoleDao;
import com.icanft.jsgl.service.IRoleService;
import com.icanft.jsgl.vo.Role;

/**
 * @author Administrator
 * 
 */
public class RoleServiceImpl implements IRoleService {
	
	private IRoleDao roleDao;
	
	/* (non-Javadoc)
	 * @see com.icanft.jsgl.service.impl.IJsglService#insert(com.icanft.jsgl.vo.Role)
	 */
	@Override
	public void insert(Role role) {
		roleDao.insert(role);
	}

	/* (non-Javadoc)
	 * @see com.icanft.jsgl.service.impl.IJsglService#delete(com.icanft.jsgl.vo.Role)
	 */
	@Override
	public void delete(String id) {
		roleDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.icanft.jsgl.service.impl.IJsglService#update(com.icanft.jsgl.vo.Role)
	 */
	@Override
	public void update(Role role) {
		roleDao.update(role);
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.jsgl.service.impl.IJsglService#find(com.icanft.jsgl.vo.Role)
	 */
	@Override
	public Role findById(String id) {
		return roleDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.icanft.jsgl.service.impl.IJsglService#find(com.icanft.jsgl.vo.Role)
	 */
	@Override
	public List findList(Role role) {
		return roleDao.findList(role);
	}

	/* (non-Javadoc)
	 * @see com.icanft.jsgl.service.impl.IJsglService#findCount(com.icanft.jsgl.vo.Role)
	 */
	@Override
	public long findListCount(Role role) {
		return roleDao.findListCount(role);
	}

	/**
	 * @param jsglDao the jsglDao to set
	 */
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.jsgl.service.impl.IJsglService#find(com.icanft.jsgl.vo.Role)
	 */
	@Override
	public List findByUserLoginId(String loginId) {
		return roleDao.findByUserLoginId(loginId);
	}
	
	
}
