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
	
	protected String getStatement(String key) {
		String statement = null;
		if (key != null && key.indexOf(".") > -1) {
			statement = key;
		} else {
			statement = this.getClass().getName() + "." + key;
		}
		return statement;
	}
	
	//公用的方法
	@Override
	public void insert(String key , T t) {
		String statement = this.getStatement(key);
		sqlSessionTemplate.insert(statement, t);
	}
	
	@Override
	public void deleteById(String key , String id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(String key , T t) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public T findById(String key , String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List findList(String key , T t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List findPageList(String key, Object parameter,
			int pageIndex, int pageSize) {
		RowBounds rb = this.getRowBounds(pageIndex, pageSize);
		String statement = this.getStatement(key);
		List list = this.sqlSessionTemplate.selectList(statement, parameter, rb);
		return list;
	}
	
	protected RowBounds getRowBounds(int pageIndex, int pageSize) {
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		int offset = (pageIndex - 1) * pageSize;
		RowBounds rb = new RowBounds(offset, pageSize);
		return rb;
	}
	
	@Override
	public int findListCount(String key , T t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
