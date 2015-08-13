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
public interface IDefaultDao<T> {
	
	public void insert(String key , T t);

	public void deleteById(String key , String id);

	public void update(String key , T t);
	
	public T findById(String key , String id);

	public List findList(String key , T t);
	
	public List findPageList(String key, T t , int pageIndex, int pageSize);
	
	public int findListCount(String key , T t);
	
	public List findAll(String key);
}
