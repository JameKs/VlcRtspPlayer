/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
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
 * 提供针对Oracle数据库的IDialect实现。
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
public class OracleDialect extends AbstractDialect {

	private static final Log log = LogFactory.getLog(OracleDialect.class);

	private String ORACLE_PAGEDSQL_FORMATTER;

	/**
	 * 默认的构造方法。
	 */
	public OracleDialect() {
		ORACLE_PAGEDSQL_FORMATTER = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (@_z_#) A WHERE ROWNUM <= @_x_#) WHERE RN >= @_y_#";
	}

	
	@Override
	public Map<String, FbrpDbtoolTableCol> getTableColInfo(FbrpDbtoolDs ds,
			final String tableName) {
		// jdbc
		SimpleJdbcTemplate simpleJdbcTemplate = this.getDsService()
				.getSimpleJdbcTemplate(ds.getId());
		if (simpleJdbcTemplate == null) {
			return null;
		}
		// 查询表的字段信息
		Map<String, FbrpDbtoolTableCol> tableColInfo = simpleJdbcTemplate
				.getJdbcOperations()
				.execute(
						new ConnectionCallback<Map<String, FbrpDbtoolTableCol>>() {
							public Map<String, FbrpDbtoolTableCol> doInConnection(
									Connection conn) throws SQLException,
									DataAccessException {
								Map<String, FbrpDbtoolTableCol> dbtableColInfoVOs = new HashMap<String, FbrpDbtoolTableCol>();
								ResultSet rs = null;
								try {
									DatabaseMetaData dmd = conn.getMetaData();
									// 主键字段
									rs = dmd.getPrimaryKeys("", dmd.getUserName(), tableName);
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
											dntableColinfo.setPrimaryKeyFlag(Long.valueOf(BaseConstants.YESORNO_TYPE_YES));
										} else {
											dntableColinfo.setPrimaryKeyFlag(Long.valueOf(BaseConstants.YESORNO_TYPE_NO));
										}
										// 允许为空
										dntableColinfo.setNullable(Long.valueOf(rs.getObject("NULLABLE").toString()));
										// 长度
										String length = String.valueOf(rs.getObject("COLUMN_SIZE").toString());
										if (rs.getObject("DECIMAL_DIGITS") != null
												&& ((BigDecimal) rs.getObject("DECIMAL_DIGITS")).doubleValue() != 0) {
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
										dbtableColInfoVOs.put(dntableColinfo.getName(), dntableColinfo);
									}
								} catch (SQLException exe) {
									log.error("", exe);
								} finally {
									JdbcUtils.closeResultSet(rs);
								}
								return dbtableColInfoVOs;
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
			public Object doInStatement(Statement smt) throws SQLException,
					DataAccessException {
				ResultSet rs = null;
				try {
					rs = smt.executeQuery("select * from user_col_comments where comments is not null and table_name='"
							+ tableName + "'");
					while (rs.next()) {
						tableColInfo.get(rs.getString("COLUMN_NAME"))
								.setRemark(rs.getString("COMMENTS"));
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

	/**
	 * 获取数据库中表的信息。
	 * 
	 * @param dsId
	 *            数据源
	 * 
	 * @return Map<String, FbrpDbtoolTableVO>
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
		Map<String, FbrpDbtoolTable> tableInfo = simpleJdbcTemplate
				.getJdbcOperations().execute(
						new ConnectionCallback<Map<String, FbrpDbtoolTable>>() {
							public Map<String, FbrpDbtoolTable> doInConnection(
									Connection conn) throws SQLException,
									DataAccessException {
								Map<String, FbrpDbtoolTable> map = new LinkedHashMap<String, FbrpDbtoolTable>();
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
								} catch (SQLException exe) {
									log.error("", exe);
								} finally {
									JdbcUtils.closeResultSet(rs);
								}
								return map;
							}
						});
		// 增加Comments
		this.setTableComments(tableInfo, simpleJdbcTemplate);
		return tableInfo;
	}

	/**
	 * 给表增加Comments。
	 * 
	 * @param tableInfo   Map<String, FbrpDbtoolTable>
	 * @param simpleJdbcTemplate  SimpleJdbcTemplate
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setTableComments(final Map<String, FbrpDbtoolTable> tableInfo,
			SimpleJdbcTemplate simpleJdbcTemplate) {
		simpleJdbcTemplate.getJdbcOperations().execute(new StatementCallback() {
			public Object doInStatement(Statement smt) throws SQLException,
					DataAccessException {
				ResultSet rs = null;
				try {
					rs = smt.executeQuery("select * from user_tab_comments where comments is not null");
					while (rs.next()) {
						String str = rs.getString("TABLE_NAME");
						FbrpDbtoolTable tempVO = tableInfo.get(str);
						if (tempVO != null) {
							tempVO.setRemark(rs.getString("COMMENTS"));
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


	@Override
	public String getPagedString(String sql, boolean hasOffset) {
		return null;
	}

	@Override
	public String getPagedString(String sql, int offset, int limit) {
		offset++;
		int endset = (offset + limit) - 1;
		String rs = ORACLE_PAGEDSQL_FORMATTER
				.replaceFirst(
						"@_y_#",
						(new StringBuilder()).append(offset).append("")
								.toString())
				.replaceFirst(
						"@_x_#",
						(new StringBuilder()).append(endset).append("")
								.toString()).replaceFirst("@_z_#", sql);
		return rs;
	}

	@Override
	public boolean supportsPaged() {
		return true;
	}

}