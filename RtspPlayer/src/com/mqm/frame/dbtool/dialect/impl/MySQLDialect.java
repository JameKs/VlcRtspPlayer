/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTable;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTableCol;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * 
 * <pre>
 * 提供针对MySQL数据库的IDialect实现。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class MySQLDialect extends AbstractDialect {

	private static final Log log = LogFactory.getLog(MySQLDialect.class);

	protected static final String SQL_END_DELIMITER = ";";

	/**
	 * 构造方法。
	 */
	public MySQLDialect() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.dbtool.dialect.impl.AbstractDialect#getTableColInfo(
	 * FbrpDbtoolDs ds, String tableName)
	 */
	@Override
	public Map<String, FbrpDbtoolTableCol> getTableColInfo(FbrpDbtoolDs ds,
			final String tableName) {
		// jdbc
		SimpleJdbcTemplate simpleJdbcTemplate = this.getDsService()
				.getSimpleJdbcTemplate(ds.getId());
		if (simpleJdbcTemplate == null) {
			return null;
		}
		JdbcOperations jdbcOperations = simpleJdbcTemplate.getJdbcOperations();

		// 查询表的字段信息
		final Map<String, FbrpDbtoolTableCol> tableColInfo = jdbcOperations
				.execute(new ConnectionCallback<Map<String, FbrpDbtoolTableCol>>() {
					public Map<String, FbrpDbtoolTableCol> doInConnection(
							Connection conn) throws SQLException,
							DataAccessException {
						Map<String, FbrpDbtoolTableCol> dbtableColInfo = new HashMap<String, FbrpDbtoolTableCol>();
						ResultSet rs = null;
						try {
							DatabaseMetaData dmd = conn.getMetaData();
							rs = dmd.getPrimaryKeys("", dmd.getUserName(), tableName); // 主键字段
							List<String> keyColumns = new ArrayList<String>();
							while (rs.next()) {
								keyColumns.add(rs.getObject("COLUMN_NAME").toString());
							}
							// 所有字段
							rs = dmd.getColumns(conn.getCatalog(), dmd.getUserName(), tableName, "%");
							String columnName = null;
							// 排序号
							int sortNo = 0;
							while (rs.next()) {
								FbrpDbtoolTableCol dntableColinfo = new FbrpDbtoolTableCol();
								// 字段名
								columnName = rs.getObject("COLUMN_NAME").toString();
								dntableColinfo.setName(columnName);
								// 是否是主键
								if (keyColumns.contains(columnName)) {
									dntableColinfo.setPrimaryKeyFlag(Long
											.valueOf(BaseConstants.YESORNO_TYPE_YES));
								} else {
									dntableColinfo.setPrimaryKeyFlag(Long
											.valueOf(BaseConstants.YESORNO_TYPE_NO));
								}
								// 允许为空
								dntableColinfo.setNullable(Long.valueOf(rs.getObject("NULLABLE").toString()));
								// 长度
								String length = String.valueOf(rs.getObject("COLUMN_SIZE").toString());
								if (rs.getObject("DECIMAL_DIGITS") != null
										&& ((Integer) rs.getObject("DECIMAL_DIGITS")) != 0) {
									length += ","
											+ rs.getObject("DECIMAL_DIGITS").toString();
								}
								dntableColinfo.setLength(length);
								// 备注
								dntableColinfo.setRemark((String) rs.getObject("REMARKS"));
								// 类型
								dntableColinfo.setColType(rs.getObject("TYPE_NAME").toString());
								// 默认值
								dntableColinfo.setDefaultValue((String) rs.getObject("COLUMN_DEF"));
								// 排序号
								dntableColinfo.setSortNo(Long.valueOf(sortNo++));
								dbtableColInfo.put(dntableColinfo.getName(),
										dntableColinfo);
							}
						} catch (Exception exe) {
							log.error("", exe);
						} finally {
							JdbcUtils.closeResultSet(rs);
						}
						return dbtableColInfo;
					}
				});
		// 增加comments
		this.setTableColComments(simpleJdbcTemplate, tableName, tableColInfo);
		return tableColInfo;
	}

	/**
	 * 给表字段信息增加comments。
	 * 
	 * @param simpleJdbcTemplate
	 *            SimpleJdbcTemplate
	 * @param tableName
	 *            String
	 * @param tableColInfo
	 *            Map<String, FbrpDbtoolTableCol>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setTableColComments(SimpleJdbcTemplate simpleJdbcTemplate,
			final String tableName,
			final Map<String, FbrpDbtoolTableCol> tableColInfo) {
		simpleJdbcTemplate.getJdbcOperations().execute(new StatementCallback() {
			public Object doInStatement(Statement statement)
					throws SQLException, DataAccessException {
				ResultSet rs = null;
				try {
					rs = statement
							.executeQuery("select column_name,column_comment from information_schema.columns where table_name='"
									+ tableName + "'");
					while (rs.next()) {
						tableColInfo.get(rs.getString("column_name"))
								.setRemark(rs.getString("column_comment"));
					}
				} catch (SQLException exe) {
					log.error("", exe);
				} finally {
					JdbcUtils.closeResultSet(rs);
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.dbtool.dialect.impl.AbstractDialect#getTables(String
	 * dsId)
	 */
	@Override
	public Map<String, FbrpDbtoolTable> getTables(String dsId) {
		// JDBC
		SimpleJdbcTemplate simpleJdbcTemplate = this.getDsService()
				.getSimpleJdbcTemplate(dsId);
		if (simpleJdbcTemplate == null) {
			return null;
		}
		// 表信息列表
		final Map<String, FbrpDbtoolTable> tablesMap = simpleJdbcTemplate
				.getJdbcOperations().execute(
						new ConnectionCallback<Map<String, FbrpDbtoolTable>>() {
							public Map<String, FbrpDbtoolTable> doInConnection(
									Connection conn) throws SQLException,
									DataAccessException {
								Map<String, FbrpDbtoolTable> map = new HashMap<String, FbrpDbtoolTable>();
								String[] types = { "TABLE" };
								ResultSet rs = null;
								try {
									DatabaseMetaData dmd = conn.getMetaData();
									rs = dmd.getTables("", dmd.getUserName(),
											"%", types);
									while (rs.next()) {
										FbrpDbtoolTable dbtable = new FbrpDbtoolTable();
										// 表名
										dbtable.setName(rs.getObject(
												"TABLE_NAME").toString());
										if (!dbtable.getName().startsWith(
												"BIN$")) {
											map.put(dbtable.getName(), dbtable);
										}
									}
								} catch (Exception exe) {
									log.error("", exe);
								} finally {
									JdbcUtils.closeResultSet(rs);
								}
								return map;
							}
						});
		// 增加comments
		this.setTableComments(tablesMap, simpleJdbcTemplate);
		return tablesMap;
	}

	/**
	 * 给表增加Comments。
	 * 
	 * @param tablesMap
	 *            Map<String, FbrpDbtoolTable>
	 * @param simpleJdbcTemplate
	 *            SimpleJdbcTemplate
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setTableComments(final Map<String, FbrpDbtoolTable> tablesMap,
			SimpleJdbcTemplate simpleJdbcTemplate) {
		simpleJdbcTemplate.getJdbcOperations().execute(new StatementCallback() {
			public Object doInStatement(Statement statement)
					throws SQLException, DataAccessException {
				ResultSet rs = null;
				try {
					rs = statement
							.executeQuery("SELECT table_name,table_comment FROM information_schema.tables");
					while (rs.next()) {
						String tableName = rs.getString("table_name");
						FbrpDbtoolTable tempVO = tablesMap.get(tableName);
						if (tempVO != null) {
							String tableComment = rs.getString("table_comment");
							int index = tableComment.indexOf(";");
							if (index != -1) {
								tableComment = tableComment.substring(0, index);
							}
							// TODO xiaocheng_lu 如果是其存储引擎呢？
							String regex = "(?i)InnoDB free:[\\d\\s]+kB.*";
							tableComment = tableComment.replaceAll(regex, "");
							tempVO.setRemark(tableComment);
						}
					}
				} catch (SQLException exe) {
					log.error("", exe);
				} finally {
					JdbcUtils.closeResultSet(rs);
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getPagedString(String sql,
	 * boolean flag)
	 */
	@Override
	public String getPagedString(String sql, boolean hasOffset) {
		return (new StringBuffer(sql.length() + 20)).append(trim(sql))
				.append(hasOffset ? " limit ?,?" : " limit ?").append(";")
				.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getPagedString(String sql,
	 * int offset, int limit)
	 */
	@Override
	public String getPagedString(String sql, int offset, int limit) {
		sql = trim(sql);
		StringBuffer sb = new StringBuffer(sql.length() + 20);
		sb.append(sql);
		if (offset > 0) {
			sb.append(" limit ").append(offset).append(',').append(limit)
					.append(";");
		} else {
			sb.append(" limit ").append(limit).append(";");
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mqm.frame.dbtool.dialect.IDialect# supportsPaged()
	 */
	@Override
	public boolean supportsPaged() {
		return true;
	}

	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1 - ";".length());
		}
		return sql;
	}

}