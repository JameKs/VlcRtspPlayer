/**
 * JsglServiceImpl.java
 * 2015
 * 2015年5月18日
 */
package com.mqm.frame.sys.role.service;

import java.util.List;

import com.mqm.frame.common.IDefaultService;

/**
 * @author Administrator
 *
 */
public interface IRoleService<T> extends IDefaultService<T> {

	public abstract List findByUserLoginId(String loginId);

}
