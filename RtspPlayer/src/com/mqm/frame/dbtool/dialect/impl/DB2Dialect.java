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
import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * 
 * <pre>
 * 提供针对DB2数据库的IDialect实现。
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
public class DB2Dialect extends AbstractDialect {

	private static final Log log = LogFactory.getLog(DB2Dialect.class);

	/**
	 * 构造方法。
	 */
	public DB2Dialect() {
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getPagedString(String sql, boolean flag)
	 */
	@Override
	public String getPagedString(String sql, boolean hasOffset) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.dbtool.dialect.impl.AbstractDialect#getTableColInfo
	 * (com.mqm.frame.dbtool.vo.FbrpDbtoolDsVO, java.lang.String)
	 */
	@Override
	public Map<String, FbrpDbtoolTableCol> getTableColInfo(FbrpDbtoolDs ds,
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
							public Map<String, FbrpDbtoolTableCol> doInConnection(
									Connection conn) throws SQLException, DataAccessException {
								Map<String, FbrpDbtoolTableCol> dbtableColInfoVOs = new HashMap<String, FbrpDbtoolTableCol>();
								ResultSet rs = null;
								try {
									DatabaseMetaData dmd = conn.getMetaData();
									// 1.登录验证增加的表
									// 主键字段
									rs = dmd.getPrimaryKeys("", InternationalizationUtil.toUpperCase(dmd.getUserName()), tableName);
									List<String> keyColumns = new ArrayList<String>();
									while (rs.next()) {
										keyColumns.add(rs.getObject("COLUMN_NAME").toString());
									}
									// 所有字段
									rs = dmd.getColumns(conn.getCatalog(), InternationalizationUtil.toUpperCase(dmd.getUserName()),
											tableName, "%");
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
									// 2.本地验证增加的表
								} catch (Exception exe) {
									log.error("", exe);
								} finally {
									JdbcUtils.closeResultSet(rs);
								}
								return dbtableColInfoVOs;
							}
						});
		return tableColInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.dbtool.dialect.impl.AbstractDialect#getTables(com.mqm.frame.dbtool.vo.FbrpDbtoolDsVO)
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
									// 1.登录验证增加的表
									rs = dmd.getTables("", InternationalizationUtil.toUpperCase(dmd
											.getUserName()), "%", types);
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
									// 2.本地验证增加的表, win(MS), linux(?).

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

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getPagedString(String sql, int offset, int limit)
	 */
	@Override
	public String getPagedString(String sql, int offset, int limit) {
		int startOfSelect = InternationalizationUtil.toLowerCase(sql).indexOf("select");
		StringBuffer pagingSelect = (new StringBuffer(sql.length() + 100))
				.append(sql.substring(0, startOfSelect))
				.append("select * from ( select ").append(getRowNumber(sql));
		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ")
					.append(sql.substring(startOfSelect)).append(" ) as row_");
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6));
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		if (offset > 0) {
			pagingSelect.append((new StringBuilder()).append("between ")
					.append(offset + 1).append(" and ").append(offset + limit)
					.toString());
		} else {
			pagingSelect.append((new StringBuilder()).append("<= ")
					.append(limit).toString());
		}
		return pagingSelect.toString();
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect# supportsPaged()
	 */
	@Override
	public boolean supportsPaged() {
		return false;
	}

	private String getRowNumber(String sql) {
		StringBuffer rownumber = (new StringBuffer(50))
				.append("rownumber() over(");
		int orderByIndex = InternationalizationUtil.toLowerCase(sql).indexOf("order by");
		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	private static boolean hasDistinct(String sql) {
		return InternationalizationUtil.toLowerCase(sql).indexOf("select distinct") >= 0;
	}

}