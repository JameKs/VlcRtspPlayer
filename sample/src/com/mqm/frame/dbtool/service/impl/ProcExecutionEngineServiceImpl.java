/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

import com.mqm.frame.util.exception.FbrpException;

/**
 * <pre>
 * 存储过程获取数据统一入口。
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
public class ProcExecutionEngineServiceImpl {

	private static final Log log = LogFactory
			.getLog(ProcExecutionEngineServiceImpl.class);
	
	/**
	 * 执行存储过程，不支持有返回参数的存储过程,考虑到存储过程返回结果集时<br>
	 * 必须用到游标做为输出参数,在程序内部封装了个游标类型的变量,调用时不用 另外再传输出参数了。
	 * 
	 * @param jdbcTemplate
	 *            数据源<br >
	 * @param procName
	 *            存储过程名称<br >
	 * @param params
	 *            输入参数参数列表<br >
	 *            
	 * @param types String[]
	 * @return 用List包装的查询结果.List里的元素是HashMap封装的查询结果集的每一行记录<br >
	 * 
	 * @throws FbrpException 异常
	 */
	public static List queryProc(SimpleJdbcTemplate jdbcTemplate, final String procName,
			final String[] params, final String[] types)
			throws FbrpException {
		
		// 数据库产品的名字
		final String databaseName = (String) jdbcTemplate.getJdbcOperations()
				.execute(new ConnectionCallback() {
					public Object doInConnection(Connection connection)
							throws SQLException, DataAccessException {
						return connection.getMetaData()
								.getDatabaseProductName();
					}
				});

		@SuppressWarnings("unchecked")
		List list = (List) jdbcTemplate.getJdbcOperations().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) throws SQLException,
					DataAccessException {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < params.length; i++) {
					sb.append("?,");
				}
				String strSql = "";
				ResultSet set = null;
				CallableStatement cste = null;
				try {
					if ("Oracle".equalsIgnoreCase(databaseName)) {
						if (params.length != 0) {
							strSql = sb.append("?").toString();// 游标参数
							StringBuffer sb2 = new StringBuffer();
							sb2.append("{call ");
							sb2.append(procName);
							sb2.append("(");
							sb2.append(strSql);
							sb2.append(")}");
							strSql = sb2.toString();
							try {
								cste = conn.prepareCall(strSql);
							} catch (SQLException e) {
								log.error("", e);
								throw new FbrpException(e.getMessage());
							}
						} else {
							sb.append("{call ");
							sb.append(procName);
							sb.append("(?) }");
							strSql = sb.toString();
							try {
								cste = conn.prepareCall(strSql);
							} catch (SQLException e) {
								log.error("", e);
								throw new FbrpException(e.getMessage());
							}
						}
						int paramFalg = 1;
						try {
							int[] its = getTypeIndex(types);
							for (int i = 0; i < params.length; i++) {
								setParameter(cste, params[i], its[i], i + 1);
								paramFalg++;
							}
							cste.registerOutParameter(paramFalg, -10);//oracle.jdbc.OracleTypes.CURSOR
							cste.executeQuery();
							set = (ResultSet) cste.getObject(paramFalg);
						} catch (SQLException e) {
							log.error("", e);
							throw new FbrpException(e.getMessage());
						}
					} else {
						if (params.length != 0) {
							strSql = sb.toString().substring(0, sb.toString().length() - 1);
							strSql = "{ call " + procName + "( " + strSql + ")}";
							try {
								cste = conn.prepareCall(strSql);
							} catch (SQLException e) {
								log.error("", e);
								throw new FbrpException(e.getMessage());
							}
						} else {
							try {
								cste = conn.prepareCall("{ call " + procName + "}");
							} catch (SQLException e) {
								log.error("", e);
								throw new FbrpException(e.getMessage());
							}
						}
						int paramFalg = 1;
						try {
							for (int i = 0; i < params.length; i++) {
								cste.setString(i + 1, params[i]);
								paramFalg++;
							}
							cste.registerOutParameter(paramFalg, -10);//oracle.jdbc.OracleTypes.CURSOR
							cste.executeQuery();
							set = (ResultSet) cste.getObject(paramFalg);
						} catch (SQLException e) {
							log.error("", e);
							throw new FbrpException(e.getMessage());
						}
					}

					List resultList = resultSetToList(set);
					return resultList;
				} catch (FbrpException exeception) {
					log.error("", exeception);
					throw exeception;
				} finally {
					JdbcUtils.closeResultSet(set);
					JdbcUtils.closeStatement(cste);
				}
			}
		});
		return list;
	}
	
	private static String[] TYPES_ = { "String", "Byte", "Short", "Integer",
		"Long", "Boolean", "Date", "Time", "Object" };

	/**
	 * 返回字符串类型所对应的index,以方便switch处理。
	 * 
	 * @param types 类型
	 * 
	 * @return int[]
	 */
	private static int[] getTypeIndex(String[] types) {
		int[] rt = new int[types.length];
		for (int i = 0; i < types.length; i++) {
			for (int j = 0; j < TYPES_.length; j++) {
				if (TYPES_[j].equalsIgnoreCase(types[i])) {
					rt[i] = j;
					break;
				}
			}
		}
		return rt;
	}	

	/**
	 * 设置存储过程所需要的参数。
	 * 
	 * @param stm callable statement
	 * @param param 参数
	 * @param type  类型
	 * @param i 设置的参数的位置
	 * 
	 * @throws SQLException sql异常
	 */
	private static void setParameter(CallableStatement stm, Object param, int type,
			int i) throws SQLException {
		String sparam = (String) param;// 传进的参数都是String类型
		switch (type) {
		case 0:
			stm.setString(i, sparam);
			break;
		case 1:
			stm.setByte(i, Byte.parseByte(sparam));
			break;
		case 2:
			stm.setShort(i, Short.parseShort(sparam));
			break;
		case 3:
			stm.setInt(i, Integer.parseInt(sparam));
			break;
		case 4:
			stm.setLong(i, Long.parseLong(sparam));
			break;
		case 5:
			stm.setBoolean(i, Boolean.valueOf(sparam).booleanValue());
			break;
		case 6:
			stm.setDate(i, java.sql.Date.valueOf(sparam));
			break;
		case 7:
			stm.setTime(i, Time.valueOf(sparam));
			break;
		case 8:
			stm.setObject(i, param);
			break;
		default:
			stm.setString(i, sparam);
		}
	}
	/**
	 * 将一个结果集转化为一个List集合
	 * 处理流程:传入一个结果集,通过while循环将结果集里一行数据以列名和字段值为key,value组成的HashMap元素
	 * 然后将每个HashMap加入到list集合。
	 * 
	 * @param rs 要处理的记录集
	 * 
	 * @return 被出来后转为list的result
	 */
	public static List resultSetToList(ResultSet rs) {
		List list = new ArrayList();
		ResultSetMetaData rsmd;
		int cCount = -1;
		try {
			if (rs == null) {
				return null;
			}
			rsmd = rs.getMetaData();
			if (rsmd == null) {
				return null;
			} else {
				cCount = rsmd.getColumnCount();
			}
			while (rs.next()) {
				Map map = new LinkedHashMap();
				for (int j = 1; j <= cCount; j++) {
					// 将字段的原值存储在list中，以便进行求和计算
					Object fieldValue = getField(rs, rsmd, j, false);
					map.put(rsmd.getColumnName(j), fieldValue == null ? "" : fieldValue);
				}
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			log.error("", e);
			return null;
		} finally{
			JdbcUtils.closeResultSet(rs);
		}
	}	

	/**
	 * 取得某个记录集的某列的对象。
	 * 
	 * @param rs
	 *            要处理的记录集
	 * @param rsmd
	 *            要处理的记录集元数据集
	 * @param column
	 *            要处理哪个列
	 * @param convertToString
	 *            是否将结果转化为String
	 * @return 得到的列值
	 * 
	 * @throws SQLException SQLException
	 */
	public static Object getField(ResultSet rs, ResultSetMetaData rsmd,
			int column, boolean convertToString) throws SQLException {

		if (rs == null || rsmd == null) {
			throw new SQLException("ResultSet is null.");
		}
		switch (rsmd.getColumnType(column)) {
		case Types.BIGINT:
			if (convertToString) {
				return String.valueOf(rs.getLong(column));
			} else {
				return Long.valueOf(rs.getLong(column));
			}
		case Types.BINARY:
			if (convertToString) {
				return Byte.toString(rs.getByte(column));
			} else {
				return Byte.valueOf(rs.getByte(column));
			}
		case Types.BIT:
			if (convertToString) {
				return String.valueOf(rs.getBoolean(column));
			} else {
				return Boolean.valueOf(rs.getBoolean(column));
			}
		case Types.CHAR:
			return rs.getString(column);
		case Types.DATE:
			if (convertToString) {
				// return (rs.getDate(column)).toString(); //xiadong 20071213
				return new Date(((Timestamp) rs.getTimestamp(column)).getTime())
						.toString();
			} else {
				// return rs.getDate(column); //xiadong 20071213
				return new Date(((Timestamp) rs.getTimestamp(column)).getTime());
			}
		case Types.DECIMAL:
			if (convertToString) {
				return (rs.getBigDecimal(column));
			} else {
				return rs.getBigDecimal(column);
			}
		case Types.DOUBLE:
			if (convertToString) {
				return String.valueOf(rs.getDouble(column));
			} else {
				return Double.valueOf(rs.getDouble(column));
			}
		case Types.FLOAT:
			if (convertToString) {
				return String.valueOf(rs.getFloat(column));
			} else {
				return Float.valueOf(rs.getFloat(column));
			}
		case Types.INTEGER:
			if (convertToString) {
				return String.valueOf(rs.getInt(column));
			} else {
				return Integer.valueOf(rs.getInt(column));
			}
		case Types.LONGVARBINARY:
			if (convertToString) {
				return (rs.getBinaryStream(column)).toString();
			} else {
				return rs.getBinaryStream(column);
			}
		case Types.NULL:
			if (convertToString) {
				return "NULL";
			} else {
				return null;
			}

		case Types.NUMERIC:
			if (convertToString) {
				return (rs.getBigDecimal(column));
			} else {
				return rs.getBigDecimal(column);
			}
		case Types.REAL:
			if (convertToString) {
				return String.valueOf(rs.getFloat(column));
			} else {
				return Float.valueOf(rs.getFloat(column));
			}
		case Types.SMALLINT:
			if (convertToString) {
				return String.valueOf(rs.getShort(column));
			} else {
				return Short.valueOf(rs.getShort(column));
			}
		case Types.TIME:
			if (convertToString) {
				return (rs.getTime(column)).toString();
			} else {
				return rs.getTime(column);
			}
		case Types.TIMESTAMP:
			if (convertToString) {
				return (rs.getTimestamp(column)).toString();
			} else {
				return rs.getTimestamp(column);
			}
		case Types.TINYINT:
			if (convertToString) {
				return String.valueOf(rs.getByte(column));
			} else {
				return new Byte(rs.getByte(column));
			}
		case Types.VARBINARY:
			if (convertToString) {
				return (rs.getBytes(column)).toString();
			} else {
				return rs.getBytes(column);
			}
		case Types.VARCHAR:
			return rs.getString(column);
		default:
			if (convertToString) {
				return (rs.getObject(column)).toString();
			} else {
				return rs.getObject(column);
			}
		}
	}	

}
