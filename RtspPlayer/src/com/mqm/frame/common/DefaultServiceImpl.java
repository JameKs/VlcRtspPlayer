/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mqm.frame.infrastructure.util.ContextUtil;

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
public class DefaultServiceImpl {

	private static final Log log = LogFactory.getLog(DefaultServiceImpl.class);

	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 设置 sqlSessionTemplate。
	 * 
	 * @param sqlSessionTemplate
	 *            设置 sqlSessionTemplate。
	 */
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	/**
	 * 返回 sqlSessionTemplate。
	 * 
	 * @return 返回 sqlSessionTemplate。
	 */
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	/**
	 * 分页查找。
	 * 
	 */
	public List pagedQuery(String key, Object parameter,
			int pageIndex, int pageSize) {
		RowBounds rb = this.getRowBounds(pageIndex, pageSize);
		String statement = this.getStatement(key);
		List list = this.sqlSessionTemplate.selectList(statement, parameter, rb);
		String statementCount = statement + "_count";
		// 使用缓存机制读取Total。
		Long total = this.queryTotalByUseCache(statementCount, pageIndex,parameter);
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
	
	protected String getStatement(String key) {
		String statement = null;
		if (key != null && key.indexOf(".") > -1) {
			statement = key;
		} else {
			statement = this.getClass().getName() + "." + key;
		}
		return statement;
	}
	
	/**
	 * 使用缓存机制读取Total。
	 */
	public Long queryTotalByUseCache(String statementCount, int pageIndex,
			Object parameter) {
		Long total = 0L;
		// 1、缓存total数 但pageIndex=1时，不进行缓存。 2、 对SQL进行哈希。
		String userRunSql = this.createCacheCountSQLKey(statementCount,
				parameter);
		Long cacheTotal = null;
		String cacheKey = null;
		if (userRunSql != null && this.existsHttpSession()) {
			cacheKey = String.valueOf(userRunSql.hashCode());
			cacheTotal = (Long) ContextUtil.get("SQL_COUNT_KEY" + cacheKey,ContextUtil.SCOPE_SESSION);
		}
		if (cacheTotal != null && pageIndex != 1) {
			total = cacheTotal;
			log.debug(statementCount + "获取缓存total:" + total);
		} else {
			total = (Long) this.getSqlSessionTemplate().selectOne(
					statementCount, parameter);
			if (cacheKey != null&&this.existsHttpSession()) {
				ContextUtil.put("SQL_COUNT_KEY" + cacheKey, total,
						ContextUtil.SCOPE_SESSION);
			}
		}
		return total;
	}
	
	/**
	 * 获取运行时sql。
	 * 
	 * @param statementId
	 *            String
	 * @param parameter
	 *            Object
	 * @return String
	 */
	public String revolveRuntimeSQL(String statementId, Object parameter) {
		try {
			Configuration configuration = this.getSqlSessionTemplate()
					.getConfiguration();
			MappedStatement mappedStatement = configuration
					.getMappedStatement(statementId);
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			return boundSql.getSql();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO luxiaocheng 需要处理的问题
		return "(读到SQL过程中发生错误.)";
	}
	
	private boolean existsHttpSession(){
		ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		Object obj = requestAttributes.getRequest().getSession(false);
		return obj == null ? false : true;
	}
	
	/**
	 * 构建运count SQL与参数组成Key。
	 * 
	 * @param statementId
	 *            String
	 * @param parameter
	 *            Object
	 * @return String
	 */
	private String createCacheCountSQLKey(String statementId, Object parameter) {
		try {
			Configuration configuration = this.getSqlSessionTemplate()
					.getConfiguration();
			MappedStatement mappedStatement = configuration
					.getMappedStatement(statementId);
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			List<ParameterMapping> list = boundSql.getParameterMappings();
			StringBuffer sbf = new StringBuffer();
			if (list != null) {
				for (ParameterMapping p : list) {
					String name = p.getProperty();
					Object object = boundSql.getAdditionalParameter(name);
					String value = object == null ? "" : object.toString();// TODO
																			// 可能存嵌套其它对象的情况，暂不考虑。
																			// add
																			// by
																			// zengzw
					sbf.append(name).append("=").append(value).append(";");
				}
			}
			return boundSql.getSql() + ";" + sbf.toString();
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
}
