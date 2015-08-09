/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTable;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTableCol;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * <pre>
 * 提供针对Teradata数据库的IDialect实现。
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
public class TeradataDialect extends AbstractDialect {

	private static final Log log = LogFactory.getLog(TeradataDialect.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.dbtool.dialect.impl.AbstractDialect#getTableColInfo
	 * (com.mqm.frame.dbtool.vo.FbrpDbtoolDsVO, java.lang.String)
	 */
	@Override
	public Map<String, FbrpDbtoolTableCol> getTableColInfo(final FbrpDbtoolDs ds,
			final String tableName) {
		if (tableName == null) {
			return null;
		}
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
							public Map<String, FbrpDbtoolTableCol> doInConnection(Connection conn) throws SQLException,
									DataAccessException {
								Map<String, FbrpDbtoolTableCol> dbtableColInfoVOs = new HashMap<String, FbrpDbtoolTableCol>();
								ResultSet rs = null;
								try {
									DatabaseMetaData dmd = conn.getMetaData();
									String url = (String)ds.getUrl();
									url = url.substring(url.indexOf("=")+1, url.indexOf(","));
									// 主键字段
									rs = dmd.getPrimaryKeys("", url, tableName);
									List<String> keyColumns = new ArrayList<String>();
									while (rs.next()) {
										keyColumns.add(rs.getObject("COLUMN_NAME").toString());
									}
									// 所有字段
									rs = dmd.getColumns(conn.getCatalog(), url, tableName, "%");
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
										dbtableColInfoVOs.put(dntableColinfo.getName(), dntableColinfo);
									}
								} catch (Exception exe) {
									// TODO
									// com.teradata.jdbc.jdbc_4.util.JDBCException:
									// throw new ServiceException("没有对DBC.UDTInfo的SELECT权限");
									// [Teradata Database][TeraJDBC 13.10.00.10][Error 3523][SQLState 42000]
									// The user does not have SELECT access to
									// DBC.UDTInfo.
									log.error("", exe);
								} finally {
									JdbcUtils.closeResultSet(rs);
								}
								return dbtableColInfoVOs;
							}
						});
		return tableColInfo;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getTables(FbrpDbtoolDs ds)
	 */
	@Override
	public Map<String, FbrpDbtoolTable> getTables(final FbrpDbtoolDs ds) {
		// JDBC
		SimpleJdbcTemplate simpleJdbcTemplate = this.getDsService()
				.getSimpleJdbcTemplate(ds.getId());
		if (simpleJdbcTemplate == null) {
			return null;
		}
		
		// 表信息列表
		Map<String, FbrpDbtoolTable> tableInfo = simpleJdbcTemplate
				.getJdbcOperations()
				.execute(
						new ConnectionCallback<Map<String, FbrpDbtoolTable>>() {
							public Map<String, FbrpDbtoolTable> doInConnection(
									Connection conn) throws SQLException,
									DataAccessException {
								Map<String, FbrpDbtoolTable> map = new HashMap<String, FbrpDbtoolTable>();
								String[] types = { "TABLE" };
								ResultSet rs = null;
								try {
								
									DatabaseMetaData dmd = conn.getMetaData();
									
									String url = (String)ds.getUrl();
									url = url.substring(url.indexOf("=")+1, url.indexOf(","));
									
									rs = dmd.getTables("", url, "%", types);
									while (rs.next()) {
										FbrpDbtoolTable dbtable = new FbrpDbtoolTable();
										
										// 表名
										dbtable.setName(rs.getObject(
												"TABLE_NAME").toString());
										Object remark = rs.getObject("REMARKS");
										dbtable.setRemark(remark == null ? ""
												: rs.getObject("REMARKS")
														.toString());
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
		return tableInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.infrastructure.dao.dbdialect.IDialect#supportsPaged()
	 */
	@Override
	public boolean supportsPaged() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.infrastructure.dao.dbdialect.IDialect#getPagedString
	 * (java.lang.String, boolean)
	 */
	@Override
	public String getPagedString(String sql, boolean flag) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.infrastructure.dao.dbdialect.IDialect#getPagedString
	 * (java.lang.String, int, int)
	 */
	@Override
	public String getPagedString(String sql, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
