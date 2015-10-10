/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * 
 * <pre>
 * 基于DataSource自动分析出数据库类型。
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
public class DbTypeUtils {

	/**
	 * 获取业务库类型。
	 * 
	 * @param simpleJdbcTemplate
	 *            业务库的SimpleJdbcTemplate实例
	 * 
	 * @return String 数据库类型(系统自定义的,见BaseConstants.DATASOURSE_TYPE_*)
	 */
	public static String analyseDbType(SimpleJdbcTemplate simpleJdbcTemplate) {
		String dbType = null;

		Object[] objectArray = (Object[]) simpleJdbcTemplate
				.getJdbcOperations().execute(new ConnectionCallback() {
					public Object doInConnection(Connection conn)
							throws SQLException, DataAccessException {
						java.sql.DatabaseMetaData dbma = conn.getMetaData();
						String dbName = 
								InternationalizationUtil.toUpperCase(dbma.getDatabaseProductName());
						String driverName = dbma.getDriverName();
						return new Object[] { dbName, driverName };
					}
				});

		dbType = determineDBType(objectArray[0].toString(),
				objectArray[1].toString());
		return dbType;
	}

	/**
	 * 获取数据库类型(系统自定义的,见BaseConstants.DATASOURSE_TYPE_*)。
	 * 
	 * @param dbName
	 *            数据库产品描述
	 * @param driverName
	 *            JDBC驱动名
	 * @return String 数据库类型 
	 */
	private static String determineDBType(String dbName, String driverName) {
		String dbServerType = null;
		if (dbName.indexOf("ORACLE") != -1) {
			dbServerType = BaseConstants.DATASOURSE_TYPE_ORACLE;
		} else if (dbName.indexOf("INFORMIX") != -1) {
			dbServerType = BaseConstants.DATASOURSE_TYPE_INFORMIX;
		} else if (dbName.indexOf("MICROSOFT SQL SERVER") != -1) {
			if (dbName.indexOf("2005") != -1) {
				dbServerType = BaseConstants.DATASOURSE_TYPE_MSSQLSERVER2005;
			} else {
				dbServerType = BaseConstants.DATASOURSE_TYPE_MSSQLSERVER;
			}
		} else if (dbName.indexOf("MYSQL") != -1) {
			dbServerType = BaseConstants.DATASOURSE_TYPE_MYSQL;
		} else if (dbName.indexOf("SYBASE SQL SERVER") != -1
				|| dbName.indexOf("ADAPTIVE SERVER ANYWHERE") != -1
				|| dbName.indexOf("SQL SERVER") != -1
				|| dbName.indexOf("SYBASE SQL ANYWHERE") != -1
				|| dbName.indexOf("ADAPTIVE SERVER ENTERPRISE") != -1) {
			dbServerType = BaseConstants.DATASOURSE_TYPE_SYBASESQL;
		} else if (dbName.indexOf("DB2") != -1
				|| driverName.indexOf("DB2") != -1) {
			if (dbName.indexOf("400") != -1 || driverName.indexOf("400") != -1) {
				dbServerType = BaseConstants.DATASOURSE_TYPE_NONSUPPORT;
			} else {
				dbServerType = BaseConstants.DATASOURSE_TYPE_DB2;
			}
		} else if (dbName.indexOf("TERADATA") != -1) {
			dbServerType = BaseConstants.DATASOURSE_TYPE_TERADATA;
		} else {
			dbServerType = BaseConstants.DATASOURSE_TYPE_NONSUPPORT;
		}
		return dbServerType;
	}

}
