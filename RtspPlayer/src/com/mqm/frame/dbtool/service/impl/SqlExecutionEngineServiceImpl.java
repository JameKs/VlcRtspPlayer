/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

import com.mqm.frame.dbtool.dialect.impl.DbDialect;
import com.mqm.frame.dbtool.service.IDsService;
import com.mqm.frame.dbtool.service.ISqlExecutionEngineService;
import com.mqm.frame.dbtool.util.DbTypeUtils;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.exception.FbrpException;

/**
 * <pre>
 * （数据访问引擎的接口）各种数据源提供统一的访问入口。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class SqlExecutionEngineServiceImpl extends DefaultServiceImpl implements
		ISqlExecutionEngineService {

	private static final Log log = LogFactory
			.getLog(SqlExecutionEngineServiceImpl.class);

	// 获取数据源
	private IDsService dsService;

	private DbDialect dbDialect;

	/**
	 * 删除数据库表的记录。
	 *  如果conn为null，则认为是在系统的数据库里进行操作。
	 *  
	 * @param tableName
	 *            数据库的表明
	 * @param dsId
	 *            数据源ID。
	 *            
	 * @throws FbrpException FBRP异常            
	 */
	public void deleteTableRecord(String tableName, String dsId)
			throws FbrpException {
		String sql = "delete from " + tableName;
		int count = executeSql(dsId, sql);
		log.info(count);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#doHandleTable(String tableName, String dsId,
			List<Map<String, Object>> savedatas,
			Map<Map<String, Object>, Map<String, Object>> updatedatas,
			Map<String, Object> deleteDatas)
	 */
	@Override
	public void doHandleTable(String tableName, String dsId,
			List<Map<String, Object>> savedatas,
			Map<Map<String, Object>, Map<String, Object>> updatedatas,
			Map<String, Object> deleteDatas) {
		// try {
		if (savedatas != null && savedatas.size() != 0) {
			saveDatas(dsId, tableName, savedatas);
		}
		if (updatedatas != null && !updatedatas.isEmpty()) {
			updateDatas(dsId, tableName, updatedatas);
		}
		if (deleteDatas != null && !deleteDatas.isEmpty()) {
			deldatas(dsId, tableName, deleteDatas);
		}
		// } catch (Exception e) {
		// log.info("", e);
		// }
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#executeSql(String dsId, final String sql)
			throws FbrpException
	 */
	@Override
	public Integer executeSql(String dsId, final String sql)
			throws FbrpException {
		if (sql == null) {
			return null;
		}
		final String newSql = InternationalizationUtil.toLowerCase(sql).trim();
		if (newSql.startsWith("insert ") || newSql.startsWith("update ")
				|| newSql.startsWith("delete ")) {
			int sqlSucessCount = this.getSimpleJdbcTemplate(dsId).update(sql);
			return Integer.valueOf(sqlSucessCount);
		} else {
			log.info("输入的sql语句不符合规范：" + sql);
			return null;
		}
	}
   
	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#executeSqlBatch(String dsId, final String[] sqls,
			Boolean transactionSuppoort) throws FbrpException 
	 */
	@Override
	public Boolean executeSqlBatch(String dsId, final String[] sqls,
			Boolean transactionSuppoort) throws FbrpException {
		this.getSimpleJdbcTemplate(dsId).getJdbcOperations().batchUpdate(sqls);
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#getALLResult(String tableName,
			String dsId, Integer start, Integer maxRowCount)
			throws FbrpException
	 */
	@Override
	public List<Map<String, Object>> getALLResult(String tableName,
			String dsId, Integer start, Integer maxRowCount)
			throws FbrpException {
		String sql = "select * from " + tableName;
		return querySql(dsId, sql, start, maxRowCount);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#getResultCount(String dsId, String sql)
			throws FbrpException
	 */
	@Override
	public Integer getResultCount(String dsId, String sql)
			throws FbrpException {
		if (!InternationalizationUtil.toLowerCase(sql.trim()).startsWith("select ")) {
			log.info("输入的sql语句不符合规范：" + sql);
			return null;
		}
		int index = InternationalizationUtil.toLowerCase(sql).indexOf(" from ");
		final String newsql = "select count(*) "
				+ sql.substring(index, sql.length());
		int count = this.getSimpleJdbcTemplate(dsId).queryForInt(newsql);
		return Integer.valueOf(count);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#querySql(final String dsId,
			final String sql, final int start, final int maxRowCounts)
			throws FbrpException 
	 */
	@Override
	public List<Map<String, Object>> querySql(final String dsId,
			final String sql, final int start, final int maxRowCounts)
			throws FbrpException {
		if (!InternationalizationUtil.toLowerCase(sql.trim()).startsWith("select ")) {
			log.info("输入的sql语句不符合规范：" + sql);
			return null;
		}
		return this.getSimpleJdbcTemplate(dsId).getJdbcOperations()
				.execute(new ConnectionCallback<List<Map<String, Object>>>() {

					public List<Map<String, Object>> doInConnection(
							Connection conn) throws SQLException,
							DataAccessException {
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						String newsql = null;
						Statement stmt = null;
						ResultSet rs = null;
						try {
							if (start != -1) {
								FbrpDbtoolDs ds = new FbrpDbtoolDs();
								ds.setId(dsId);
								newsql = dbDialect.getDialectByDsId(ds)
										.getPagedString(sql, start,
												maxRowCounts);
							} else {
								newsql = sql;
							}
							log.info("JDBC：" + newsql);
							List<String> columns = new ArrayList<String>();
							Map<String, Object> map = null;

							stmt = conn.createStatement();
							rs = stmt.executeQuery(newsql);
							ResultSetMetaData rsmd = rs.getMetaData();
							int columncount = rsmd.getColumnCount();
							for (int i = 1; i <= columncount; i++) {
								String colLabel = rsmd.getColumnLabel(i);
								//如果有 select a b,c d from 这种情况会有问题，应该优先获取Label，否则和RS对不上
								if(colLabel==null || "".equals(colLabel)){
									columns.add(rsmd.getColumnName(i));
								}else{
									columns.add(colLabel);
								}
							}
							while (rs.next()) {
								map = new HashMap<String, Object>();
								for (int i = 0; i < columns.size(); i++) {
									map.put(columns.get(i),
											rs.getObject(columns.get(i)));
								}
								list.add(map);
							}
						} catch (Exception ex) {
							log.error("", ex);
							throw new SQLException(ex.getMessage());
						} finally {
							JdbcUtils.closeResultSet(rs);
							JdbcUtils.closeStatement(stmt);
						}
						return list;
					}

				});
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#querySqlSimple(final String dsId, final String sql,
			final int start, final int maxRowCounts) throws FbrpException
			throws FbrpException 
	 */
	@Override
	public List<String[]> querySqlSimple(final String dsId, final String sql,
			final int start, final int maxRowCounts) throws FbrpException {
		if (!InternationalizationUtil.toLowerCase(sql.trim()).startsWith("select ")) {
			log.info("输入的sql语句不符合规范：" + sql);
			return null;
		}
		return this.getSimpleJdbcTemplate(dsId).getJdbcOperations()
				.execute(new ConnectionCallback<List<String[]>>() {

					public List<String[]> doInConnection(Connection conn)
							throws SQLException, DataAccessException {
						String newsql = null;
						List<String[]> list = new ArrayList<String[]>();
						Statement stmt = null;
						ResultSet rs = null;
						try {

							if (start != -1) {
								FbrpDbtoolDs ds = new FbrpDbtoolDs();
								ds.setId(dsId);
								newsql = dbDialect.getDialectByDsId(ds)
										.getPagedString(sql, start,
												maxRowCounts);
							} else {
								newsql = sql;
							}
							log.info("JDBC：" + newsql);

							stmt = conn.createStatement();
							rs = stmt.executeQuery(newsql);
							ResultSetMetaData rsmd = rs.getMetaData();
							int columncount = rsmd.getColumnCount();

							while (rs.next()) {
								String vals[] = new String[columncount];
								for (int loop = 0; loop < columncount; loop++) {
									vals[loop] = rs.getString(loop + 1);
								}
								list.add(vals);
							}
						} catch (Exception ex) {
							log.error("", ex);
							throw new SQLException(ex.getMessage());
						} finally {
							JdbcUtils.closeResultSet(rs);
							JdbcUtils.closeStatement(stmt);
						}
						return list;
					}

				});
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.service.ISqlExecutionEngineService#querySql(final String dsId,
			final String sql, final Map<String, Object> map,
			final int startIndex, final int maxRowCounts)
			throws FbrpException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> querySql(final String dsId,
			final String sql, final Map<String, Object> map,
			final int startIndex, final int maxRowCounts)
			throws FbrpException {
		if (!InternationalizationUtil.toLowerCase(sql.trim()).startsWith("select ")) {
			log.info("输入的sql语句不符合规范：" + sql);
			return null;
		}
		return this.getSimpleJdbcTemplate(dsId).getJdbcOperations()
				.execute(new ConnectionCallback<List<Map<String, Object>>>() {
					public List<Map<String, Object>> doInConnection(Connection conn) throws SQLException,
							DataAccessException {
						String querySql = sql;
						List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
						PreparedStatement ps = null;
						ResultSet rs = null;
						int start = 0;
						int end = 0;
						try {
							List<Object> list = new ArrayList<Object>();
							while (querySql.indexOf("<%") != -1) {
								start = querySql.indexOf("<%");
								end = querySql.indexOf("%>");
								String key = querySql.substring(start + 2, end);
								if (map.get(key) instanceof Collection) {
									String databaseProductName = null;
									databaseProductName = conn.getMetaData().getDatabaseProductName();
									StringBuffer s = new StringBuffer(" in (");
									for (Object o : (Collection<Object>) map.get(key)) {
										s.append(databaseToString(databaseProductName, o)).append(",");
									}
									s.deleteCharAt(s.length() - 1).append(")");
									querySql = querySql.replaceAll(querySql.substring(start - 1, end + 2), s.toString());
								} else {
									querySql = querySql.replaceAll(querySql.substring(start, end + 2), "\\?");
									list.add(map.get(key));
								}
							}
							log.info("bauerQuerysql=" + querySql);
							List<String> columns = new ArrayList<String>();
							Map<String, Object> mapColumn = null;
							FbrpDbtoolDs ds = new FbrpDbtoolDs();
							ds.setId(dsId);
							String newSql = dbDialect.getDialectByDsId(ds).getPagedString(querySql, startIndex, maxRowCounts);
							ps = conn.prepareStatement(newSql);
							for (int i = 0; i < list.size(); i++) {
								ps.setObject(i + 1, list.get(i));
							}
							rs = ps.executeQuery();
							ResultSetMetaData rsmd = rs.getMetaData();
							int columncount = rsmd.getColumnCount();
							for (int i = 1; i <= columncount; i++) {
								columns.add(rsmd.getColumnName(i));
							}
							while (rs.next()) {
								mapColumn = new HashMap<String, Object>();
								for (int i = 0; i < columns.size(); i++) {
									mapColumn.put(columns.get(i), rs.getObject(columns.get(i)));
								}
								listResult.add(mapColumn);
							}
						} catch (Exception ex) {
							log.error("", ex);
							throw new SQLException(ex.getMessage());
						} finally {
							JdbcUtils.closeResultSet(rs);
							JdbcUtils.closeStatement(ps);
						}
						return listResult;
					}

				});
	}

	/**
	 * 
	 * 批量保存数据 如果conn为null，则认为是在系统的数据库里进行操作。
	 * 
	 * @param dsId
	 *            数据源ID
	 * @param tableName
	 *            数据库的表名
	 * @param savedatas
	 *            要保存的数据的集合。
	 */
	private void saveDatas(String dsId, String tableName,
			final List<Map<String, Object>> savedatas) {
		StringBuffer insertsql = new StringBuffer("insert into " + tableName
				+ "(");
		StringBuffer ss = new StringBuffer("values (");
		Object[] cols = savedatas.get(0).keySet().toArray();
		for (Object colname : cols) {
			insertsql.append(colname.toString() + ",");
			ss.append("? ,");
		}
		insertsql = new StringBuffer(insertsql.substring(0,
				insertsql.length() - 1)
				+ ")"
				+ ss.subSequence(0, ss.length() - 1) + ")");
		final StringBuffer executeSql = insertsql;
		final String dbType = DbTypeUtils.analyseDbType(this.getSimpleJdbcTemplate(dsId));
		this.getSimpleJdbcTemplate(dsId).getJdbcOperations()
				.execute(new ConnectionCallback() {
					public Object doInConnection(Connection conn)
							throws SQLException, DataAccessException {
						PreparedStatement ps = null;
						try {
							Object[] cols = savedatas.get(0).keySet().toArray();
							ps = conn.prepareStatement(executeSql.toString());
							for (int i = 0; i < savedatas.size(); i++) {
								Map<String, Object> map = savedatas.get(i);
								for (int j = 0; j < cols.length; j++) {
									if (map.get(cols[j]) instanceof Date) {
										Timestamp timestamp = new Timestamp(
												((Date) map.get(cols[j]))
														.getTime());
										ps.setObject(j + 1, timestamp);
									} else {
										if (map.get(cols[j]) == null && dbType.equalsIgnoreCase(BaseConstants.DATASOURSE_TYPE_DB2)) {
											ps.setNull(j + 1, ps.getParameterMetaData().getParameterType(j + 1));
										}else if (map.get(cols[j]) == null && dbType.equalsIgnoreCase(BaseConstants.DATASOURSE_TYPE_TERADATA)) {
											ps.setNull(j + 1, ps.getParameterMetaData().getParameterType(j + 1));
										} else {
											ps.setObject(j + 1, map.get(cols[j]));
										}
									}
								}
								log.info(executeSql.toString());
								ps.addBatch();
							}
							ps.executeBatch();
							ps.clearBatch();
						} catch (Exception ex) {
							log.error("", ex);
							throw new SQLException(ex.getMessage());
						} finally {
							JdbcUtils.closeStatement(ps);
						}
						return null;
					}
				});

	}

	/**
	 * 批量更新数据 如果conn为null，则认为是在系统的数据库里进行操作。
	 * 
	 * @param dsId
	 *            数据源ID
	 * @param tableName
	 *            数据库表名
	 * @param updatedatas
	 *            要更新的数据
	 */
	private void updateDatas(String dsId, final String tableName,
			final Map<Map<String, Object>, Map<String, Object>> updatedatas) {
		this.getSimpleJdbcTemplate(dsId).getJdbcOperations()
				.execute(new ConnectionCallback() {
					public Object doInConnection(Connection conn)
							throws SQLException, DataAccessException {
						String databaseProductName = null;
						Statement smt = null;
						try {
							databaseProductName = conn.getMetaData()
									.getDatabaseProductName();
							List<String> list = new ArrayList<String>();
							for (Map<String, Object> keymap : updatedatas
									.keySet()) {
								StringBuffer updatesql = new StringBuffer(
										"update " + tableName + " set ");
								for (String s : updatedatas.get(keymap)
										.keySet()) {
									updatesql
											.append(s)
											.append("=")
											.append(databaseToString(
													databaseProductName,
													updatedatas.get(keymap)
															.get(s)))
											.append(",");
								}
								updatesql.deleteCharAt(updatesql.length() - 1)
										.append(" where ");
								
								for(Map.Entry<String, Object> entry : keymap.entrySet()){
									String key = entry.getKey();
									Object value = entry.getValue();
									if (key.indexOf("oper") == -1) {
										updatesql
												.append(key)
												.append("=")
												.append(databaseToString(
														databaseProductName,
														value));
									} else {
										updatesql.append(value);
									}
								}
								
//								for (String v : keymap.keySet()) {
//									if (v.indexOf("oper") == -1) {
//										updatesql
//												.append(v)
//												.append("=")
//												.append(databaseToString(
//														databaseProductName,
//														keymap.get(v)));
//									} else {
//										updatesql.append(keymap.get(v));
//									}
//								}
								log.info(updatesql.toString());
								list.add(updatesql.toString());
							}
							smt = conn.createStatement();
							for (int i = 0; i < list.size(); i++) {
								smt.addBatch(list.get(i));
							}
							smt.executeBatch();
						} catch (Exception ex) {
							log.error("", ex);
							throw new SQLException(ex.getMessage());
						} finally {
							JdbcUtils.closeStatement(smt);
						}
						return null;
					}

				});
	}

	/**
	 * 批量删除对象 如果conn为null，则认为是在系统的数据库里进行操作。
	 * 
	 * @param dsId
	 *            数据源ID
	 * @param tableName
	 *            数据库的表名
	 * @param deleteDatas
	 *            要删除的数据 delete 语句中where后的部分，key为列名，value为列对应的值。
	 */
	private void deldatas(String dsId, String tableName,
			final Map<String, Object> deleteDatas) {

		StringBuffer deletesql = new StringBuffer("delete from " + tableName
				+ " where " + deleteDatas.keySet().iterator().next() + "=?");
		final String deleteSqlStr = deletesql.toString();
		this.getSimpleJdbcTemplate(dsId).getJdbcOperations()
				.execute(new ConnectionCallback() {
					public Object doInConnection(Connection conn)
							throws SQLException, DataAccessException {
						PreparedStatement ps = null;
						try {
							ps = conn.prepareStatement(deleteSqlStr);
							Collection<Object> coll = deleteDatas.values();
							for (Object o : coll) {
								ps.setObject(1, o);
								ps.addBatch();
								log.info(deleteSqlStr);
							}
							ps.executeBatch();
						} catch (Exception ex) {
							log.error("", ex);
							throw new SQLException(ex.getMessage());
						} finally {
							JdbcUtils.closeStatement(ps);
						}
						return null;
					}
				});
	}

	/**
	 * 根据传入的o的类型，拼接sql。
	 * 
	 * @param databaseProductName
	 *            数据库的类型名字
	 * @param o
	 *            要拼接的值。
	 * 
	 * @return 拼接后的字符串。
	 */
	private String databaseToString(String databaseProductName, Object o) {
		if (o instanceof String) {
			return "'" + o + "'";
		} else if (o instanceof Date) {
			Date date = (Date) o;
			String value = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
					.format(date);
			if (InternationalizationUtil.toLowerCase(databaseProductName).trim().indexOf("oracle") != -1) {
				StringBuffer buf = new StringBuffer();
				return buf.append("TO_DATE(").append("'").append(value)
						.append("'").append(",").append("'")
						.append("YYYY-MM-DD HH24:MI:SS").append("')")
						.toString();
			} else {
				return "'" + value + "'";
			}
		} else {
			return o.toString();
		}
	}

	private SimpleJdbcTemplate getSimpleJdbcTemplate(String dsId) {
		return this.dsService.getSimpleJdbcTemplate(dsId);
	}

	/**
	 * 设置 dsService。
	 * 
	 * @param dsService IDsService
	 *            
	 */
	public void setDsService(IDsService dsService) {
		this.dsService = dsService;
	}

	/**
	 * 返回 dsService。
	 * 
	 * @return  IDsService
	 */
	public IDsService getDsService() {
		return dsService;
	}

	/**
	 *  设置 dbDialect。
	 * 
	 * @param dbDialect DbDialect    
	 */
	public void setDbDialect(DbDialect dbDialect) {
		this.dbDialect = dbDialect;
	}

	/**
	 * 返回 dbDialect。
	 * 
	 * @return  DbDialect
	 */
	public DbDialect getDbDialect() {
		return dbDialect;
	}

	/**
	 * 执行存储过程，不支持有返回参数的存储过程,考虑到存储过程返回结果集时。<br>
	 * 必须用到游标做为输出参数,在程序内部封装了个游标类型的变量,调用时不用 另外再传输出参数了。
	 * 
	 * @param dsId
	 *            数据源<br >
	 * @param procName
	 *            存储过程名称<br >
	 * @param params
	 *            输入参数参数列表<br >
	 * @param types 参数类型
	 *            
	 * @return 用List包装的查询结果.List里的元素是HashMap封装的查询结果集的每一行记录<br >
	 * 
	 * @throws FbrpException 异常
	 */
	public List queryProc(String dsId, final String procName,
			final String[] params, final String[] types)
			throws FbrpException {
		if("".equals(procName)){
			throw new FbrpException("没有指定存储过程名称！");
		}
		SimpleJdbcTemplate jdbcTemplate = this.getSimpleJdbcTemplate(dsId);
		if(jdbcTemplate==null){
			throw new FbrpException("没有指定正确数据库连接！");
		}
		return ProcExecutionEngineServiceImpl.queryProc(jdbcTemplate, procName, params, types);
	}
	
}
