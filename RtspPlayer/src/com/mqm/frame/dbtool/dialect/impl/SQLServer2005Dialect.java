/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * 提供针对SQL Server 2005数据库的IDialect实现。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SQLServer2005Dialect extends AbstractDialect {
	
	/**
	 * 构造方法。
	 */
	public SQLServer2005Dialect() {
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
		if (offset == 0) {
			return (new StringBuffer(sql.length() + 8))
					.append(sql)
					.insert(getSqlAfterSelectInsertPoint(sql),
							(new StringBuilder()).append(" top ").append(limit)
									.toString()).toString();
		}
		int orderByIndex = InternationalizationUtil.toLowerCase(sql).lastIndexOf("order by");
		if (orderByIndex <= 0) {
			throw new UnsupportedOperationException(
					"must specify 'order by' statement to support limit operation with offset in sql server 2005");
		} else {
			String sqlOrderBy = sql.substring(orderByIndex + 8);
			String sqlRemoveOrderBy = sql.substring(0, orderByIndex);
			int insertPoint = getSqlAfterSelectInsertPoint(sql);
			return (new StringBuffer(sql.length() + 100))
					.append("with tempPagination as(")
					.append(sqlRemoveOrderBy)
					.insert(insertPoint + 23,
							(new StringBuilder())
									.append(" ROW_NUMBER() OVER(ORDER BY ")
									.append(sqlOrderBy)
									.append(") as RowNumber,").toString())
					.append((new StringBuilder())
							.append(") select * from tempPagination where RowNumber between ")
							.append(offset + 1).append(" and ")
							.append(offset + limit).toString()).toString();
		}
	}

	protected static int getSqlAfterSelectInsertPoint(String sql) {
		int selectIndex = InternationalizationUtil.toLowerCase(sql).indexOf("select");
		int selectDistinctIndex = InternationalizationUtil.toLowerCase(sql).indexOf("select distinct");
		return selectIndex + (selectDistinctIndex != selectIndex ? 6 : 15);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect# supportsPaged()
	 */
	@Override
	public boolean supportsPaged() {
		return true;
	}

}
