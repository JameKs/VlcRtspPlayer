/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * 提供针对Informix数据库的IDialect实现。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class InformixDialect extends AbstractDialect {

	/**
	 * 构造方法。
	 */
	public InformixDialect() {
	}

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
		if (offset > 0) {
			throw new UnsupportedOperationException("informix has no offset");
		} else {
			return (new StringBuffer(sql.length() + 8))
					.append(sql)
					.insert(InternationalizationUtil.toLowerCase(sql).indexOf("select") + 6,
							(new StringBuilder()).append(" first ")
									.append(limit).toString()).toString();
		}
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect# supportsPaged()
	 */
	@Override
	public boolean supportsPaged() {
		return false;
	}

}