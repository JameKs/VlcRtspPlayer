/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author meihu meihu@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class DefaultServiceImpl<T> implements IDefaultService<T> {

	private static final Log log = LogFactory.getLog(DefaultServiceImpl.class);
	
	public IDefaultDao defaultDao;
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(T t) {
		defaultDao.insert("insert", t);
	}

	@Override
	public void deleteById(String id) {
		defaultDao.deleteById("delete", id);
	}

	@Override
	public void update(T t) {
		defaultDao.update("update", t);
	}

	@Override
	public T findById(String id) {
		return (T)defaultDao.findById("findById", id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(T t) {
		return defaultDao.findList("findList", t);
	}

	@Override
	public List findPageList(T t, int pageIndex, int pageSize) {
		return defaultDao.findPageList("findList", t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(T t) {
		return defaultDao.findListCount("findList", t);
	}
	
	@Override
	public List findAll() {
		return defaultDao.findAll("findAll");
	}

	/**
	 * @param defaultDao
	 */
	public void setDefaultDao(IDefaultDao defaultDao) {
		this.defaultDao = defaultDao;
	}

	
	
}
