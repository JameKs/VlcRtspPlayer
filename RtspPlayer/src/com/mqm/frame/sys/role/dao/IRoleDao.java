/**
 * IJsglDao.java
 * 2015
 * 2015年5月18日
 */
package com.mqm.frame.sys.role.dao;

import java.util.List;

import com.mqm.frame.common.IDefaultDao;

/**
 * @author Administrator
 * @param <T>
 *
 */
public interface IRoleDao<T> extends IDefaultDao<T>{
	
	public List<T> findByUserLoginId(String loginId);
}
