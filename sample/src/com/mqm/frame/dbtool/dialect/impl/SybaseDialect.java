/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

/**
 * 
 * <pre>
 * 提供针对SybaseDialect数据库的IDialect实现。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SybaseDialect extends AbstractDialect {
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getPagedString(String sql, boolean flag)
	 */
	@Override
	public String getPagedString(String sql, boolean hasOffset) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getPagedString(String sql, int offset, int limit)
	 */
	@Override
	public String getPagedString(String sql, int offset, int limit) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect# supportsPaged()
	 */
	@Override
	public boolean supportsPaged() {
		return false;
	}
	
}