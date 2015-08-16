/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.mqm.frame.common;

import java.util.List;

import com.mqm.frame.sys.role.vo.Role;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年8月9日
 */
public interface IDefaultService<T> {
	
	public void insert(T t);

	public void deleteById(String id);
	
	public void deleteByIds(String[] ids);

	public void update(T t);
	
	public T findById(String id);
	
	public List findList(T t);
	
	public List findPageList(T t , int pageIndex, int pageSize);
	
	public int findListCount(T t);
	
	public List findAll();
}
