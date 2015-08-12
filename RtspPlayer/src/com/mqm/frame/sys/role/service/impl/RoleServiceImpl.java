/**
 * JsglServiceImpl.java
 * 2015
 * 2015年5月18日
 */
package com.mqm.frame.sys.role.service.impl;

import java.util.List;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.role.dao.IRoleDao;
import com.mqm.frame.sys.role.service.IRoleService;
import com.mqm.frame.sys.role.vo.Role;

/**
 * @author Administrator
 * 
 */
public class RoleServiceImpl extends DefaultServiceImpl<Role> implements
		IRoleService<Role> {
	
	IRoleDao roleDao;
	
	@Override
	public List findByUserLoginId(String loginId) {
		return roleDao.findByUserLoginId(loginId);
	}

	/**
	 * @param jsglDao
	 *            the jsglDao to set
	 */
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
