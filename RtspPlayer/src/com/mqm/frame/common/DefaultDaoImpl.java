/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author meihu  meihu@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DefaultDaoImpl<T> implements IDefaultDao<T>{
	
	protected static final Logger logger = Logger.getLogger(DefaultDaoImpl.class);
	
	protected SqlSessionTemplate sqlSessionTemplate;

	//公用的方法
	@Override
	public void insert(String key , T t) {
		String statement = this.getStatement(key);
		sqlSessionTemplate.insert(statement, t);
	}
	
	@Override
	public void deleteById(String key , String id) {
		String statement = this.getStatement(key);
		sqlSessionTemplate.delete(statement, id);
	}
	
	@Override
	public void update(String key , T t) {
		String statement = this.getStatement(key);
		sqlSessionTemplate.update(statement, t);
	}
	
	@Override
	public T findById(String key , String id) {
		String statement = this.getStatement(key);
		T t = (T)sqlSessionTemplate.selectOne(statement, id);
		return t;
	}
	
	@Override
	public List findList(String key , T t) {
		String statement = this.getStatement(key);
		List list = this.sqlSessionTemplate.selectList(statement, t);
		return list;
	}
	
	@Override
	public List findPageList(String key, T t,
			int pageIndex, int pageSize) {
		RowBounds rb = this.getRowBounds(pageIndex, pageSize);
		String statement = this.getStatement(key);
		List list = this.sqlSessionTemplate.selectList(statement, t, rb);
		return list;
	}
	
	@Override
	public int findListCount(String key , T t) {
		String statement = this.getStatement(key);
		int count = this.sqlSessionTemplate.selectOne(statement, t);
		return count;
	}
	
	@Override
	public List findAll(String key) {
		String statement = this.getStatement(key);
		return this.sqlSessionTemplate.selectList(statement);
	}
	
	
	protected String getStatement(String key) {
		String statement = null;
		if (key != null && key.indexOf(".") > -1) {
			statement = key;
		} else {
			statement = this.getClass().getName() + "." + key;
		}
		return statement;
	}
	
	protected RowBounds getRowBounds(int pageIndex, int pageSize) {
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		int offset = (pageIndex - 1) * pageSize;
		RowBounds rb = new RowBounds(offset, pageSize);
		return rb;
	}
	
	/**
	 * @return 返回 sqlSessionTemplate。
	 */
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	/**
	 * @param sqlSessionTemplate 设置 sqlSessionTemplate。
	 */
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
