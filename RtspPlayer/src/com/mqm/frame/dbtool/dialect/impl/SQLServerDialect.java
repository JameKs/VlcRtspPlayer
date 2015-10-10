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
import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * 
 * <pre>
 * 提供针对SQL Server数据库的IDialect实现。
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
public class SQLServerDialect extends AbstractDialect {

	private static final Log log = LogFactory.getLog(SQLServerDialect.class);

	/**
	 * 构造方法。
	 */
	public SQLServerDialect() {
	}

	@Override
	public String getPagedString(String sql, boolean hasOffset) {
		return null;
	}

	@Override
	public String getPagedString(String sql, int offset, int limit) {
		if (offset > 0) {
			throw new UnsupportedOperationException("sql server has no offset");
		} else {
			return (new StringBuffer(sql.length() + 8))
					.append(sql)
					.insert(getAfterSelectInsertPoint(sql),
							(new StringBuilder()).append(" top ").append(limit)
									.toString()).toString();
		}
	}

	private int getAfterSelectInsertPoint(String sql) {
		int selectIndex = InternationalizationUtil.toLowerCase(sql).indexOf("select");
		int selectDistinctIndex = InternationalizationUtil.toLowerCase(sql).indexOf("select distinct");
		return selectIndex + (selectDistinctIndex != selectIndex ? 6 : 15);
	}

	@Override
	public boolean supportsPaged() {
		return false;
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
							// 主键字段
							rs = dmd.getPrimaryKeys(null, null, tableName);
							List<String> keyColumns = new ArrayList<String>();
							while (rs.next()) {
								keyColumns.add(rs.getObject("COLUMN_NAME").toString());
							}
							// 所有字段
							rs = dmd.getColumns(conn.getCatalog(), null, tableName, "%");
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
										&& !rs.getObject("DECIMAL_DIGITS").toString().equals("0")) {
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
								dbtableColInfo.put(dntableColinfo.getName(), dntableColinfo);
							}
						} catch (SQLException exe) {
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
									rs = dmd.getTables(null, null, null, types);
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
							.executeQuery("select cast(isnull(obj.name,'')as varchar) name,cast(isnull(prop.value,'')as varchar)value from sysobjects obj left join sys.extended_properties prop on prop.major_id=obj.id where prop.minor_id=0 and obj.xtype='U'");
					while (rs.next()) {
						String tableName = (String) rs.getObject("name");
						FbrpDbtoolTable tempVO = tablesMap.get(tableName);
						if (tempVO != null) {
							String tableComment = rs.getString("value");
							// int index = tableComment.indexOf(";");
							// if(index!=-1){
							// tableComment = tableComment.substring(0,index);
							// }
							// TODO xiaocheng_lu 如果是其存储引擎呢？
							// String regex = "(?i)InnoDB free:[\\d\\s]+kB.*";
							// tableComment = tableComment.replaceAll(regex,
							// "");
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
				StringBuffer sql = new StringBuffer();
				sql.append("select cast(isnull(col.name,'')as varchar) name,cast(isnull(prop.value,'')as varchar)value from sys.extended_properties prop left join syscolumns col on prop.minor_id=col.colid ");
				sql.append("left join sysobjects obj on obj.id = col.id ");
				sql.append("where prop.minor_id<>0 and obj.name='" + tableName
						+ "'");
				try {
					rs = statement.executeQuery(sql.toString());
					while (rs.next()) {
						tableColInfo.get(rs.getString("name")).setRemark(
								rs.getString("value"));
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

}