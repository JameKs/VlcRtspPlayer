/**
 * JsglServiceImpl.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jsgl.service;

import java.util.List;

import com.icanft.jsgl.vo.Role;

/**
 * @author Administrator
 *
 */
public interface IRoleService {

	public abstract void insert(Role role);

	public abstract void delete(String id);

	public abstract void update(Role role);
	
	public abstract Role findById(String id);

	public abstract List findList(Role role);

	public abstract long findListCount(Role role);
	
	public abstract List findByUserLoginId(String loginId);

}
