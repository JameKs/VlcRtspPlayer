/**
 * IJsglDao.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jssq.dao;

import java.util.List;

import com.icanft.jsgl.vo.Role;
import com.icanft.jssq.vo.CdxxRole;
import com.icanft.jssq.vo.RoleCd;

/**
 * @author Administrator
 *
 */
public interface IJssqDao {
	
	public void insert(RoleCd gx);

	public void delete(RoleCd gx);

	public void update(Role role);

	public List findList(Role role);
	
	public int findListCount(Role role);
	
	//public List<CdxxRole> getChildrenNodes(CdxxRole cdxxRole);
	
	public List<CdxxRole> getAllNodes(String roleId);

	public List findRoleMenuZtree(String roleId);
}
