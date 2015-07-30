/**
 * IJsglDao.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jsgl.dao;

import java.util.List;

import com.icanft.jsgl.vo.Role;

/**
 * @author Administrator
 *
 */
public interface IRoleDao {
	
	public void insert(Role role);

	public void delete(String id);

	public void update(Role role);
	
	public Role findById(String id);

	public List findList(Role role);
	
	public long findListCount(Role role);
	
	public List findByUserLoginId(String loginId);
}
