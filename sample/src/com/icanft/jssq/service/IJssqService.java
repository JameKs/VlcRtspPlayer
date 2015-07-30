/**
 * JsglServiceImpl.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jssq.service;

import java.util.List;

import com.icanft.jsgl.vo.Role;
import com.icanft.jssq.vo.CdxxRole;

/**
 * @author Administrator
 * 
 */
public interface IJssqService {

	public abstract List findList(Role role);

	public abstract int findListCount(Role role);

	public abstract List findRoleMenuTree(String roleId);
	
	public abstract List findRoleMenuZtree(String roleId);

	public abstract void save(String ids , String roleId, String loginId);

}
