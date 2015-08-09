/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import com.mqm.frame.util.ReflectUtil;
import com.mqm.frame.util.dialect.OracleDialect;

/**
 * 
 * <pre>
 * Interceptor实现类。
 * </pre>
 * @author luoshifei  luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DialectStatementHandlerInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler statement = (RoutingStatementHandler) invocation
				.getTarget();
		StatementHandler handler = (StatementHandler) ReflectUtil
				.getFieldValue(statement, "delegate");
		RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(handler,
				"rowBounds");
        
		if (rowBounds.getLimit() > 0
				&& rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
			BoundSql boundSql = statement.getBoundSql();
			String sql = boundSql.getSql();

			OracleDialect dialect = new OracleDialect();
			sql = dialect.getPagedString(sql, rowBounds.getOffset(),
					rowBounds.getLimit());

			ReflectUtil.setFieldValue(boundSql, "sql", sql);
		}

		return invocation.proceed();
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties properties) {
	}
	
}