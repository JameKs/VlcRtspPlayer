/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.exception.FbrpException;

/**
 * <pre>
 * sql类型与java类型之间的对应关系。
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
public class SqlAssistantOperate {

	private static final Log log = LogFactory.getLog(SqlAssistantOperate.class);

	/**
	 * 拼接sql的字符串。
	 * 
	 * @param type String
	 * @param serverType String
	 * @param value String
	 * 
	 * @return 拼接后的字符串
	 */
	public static String getSqlValueExpression(String type, String serverType,
			String value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		if (type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("Time")) {
			if ("db2".equals(serverType)) {
				if (value != null && !"".equals(value)) {
					value = "date('" + value + "')";
				} else {
					value = null;
				}
			} else if ("sqlserver".equals(serverType)) {
				value = "'" + value + "'";
			} else if ("mysql".equals(serverType)) {
				value = "'" + value + "'";
			} else {
				if (value != null && !"".equals(value)) {
					value = "TO_DATE('" + value + "','YYYY-MM-DD')";
				} else {
					value = null;
				}
			}
		} else if (type.equalsIgnoreCase("Timestamp")) {
			if ("db2".equals(serverType)) {
				value = "TIMESTAMP('" + value + "')";
			} else if ("sqlserver".equals(serverType)) {
				value = "'" + value + "'";
			} else if ("mysql".equals(serverType)) {
				value = "'" + value + "'";
			} else {
				if (value != null && !"".equals(value)) {
					value = "TO_DATE('" + value + "','YYYY-MM-DD HH24:MI:SS')";
				} else {
					value = null;
				}
			}
		} else if (type.indexOf("CHAR") != -1 || type.indexOf("TEXT") != -1) {
			value = "'" + value + "'";
		} else if ("FLOAT".equalsIgnoreCase(type)
				|| "REAL".equalsIgnoreCase(type)
				|| "MONEY".equalsIgnoreCase(type)
				|| "NUMBER".equalsIgnoreCase(type)
				|| "DECIMAL".equalsIgnoreCase(type)
				|| "INT".equalsIgnoreCase(type)
				|| "INTEGER".equalsIgnoreCase(type)) {
			if ("".equals(value)) {
				value = null;
			}
		} else {
			value = "null";
		}
		return value;
	}

	/**
	 * 将传入的SQL类型值和其对应的值进行匹配，当属于某一类型时，返回该SQL类型的对应的JAVA类型的值。
	 * 
	 * @param sqlType String
	 * @param value  传入的值
	 *            
	 * @return 匹配后的Java类型的值。
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static Object matchType(String sqlType, String value)
			throws FbrpException {
		Object javaValue = null;
		if (sqlType.equalsIgnoreCase("BYTE")
				|| sqlType.equalsIgnoreCase("LONG RAW")) {
			javaValue = Byte.parseByte(value);
		} else if (sqlType.equalsIgnoreCase("VARCHAR")
				|| sqlType.equalsIgnoreCase("VARCHAR2")
				|| sqlType.equalsIgnoreCase("CHAR")
				|| sqlType.equalsIgnoreCase("NVARCHAR")) {
			javaValue = value;
		} else if (sqlType.equalsIgnoreCase("SMALLINT")) {
			javaValue = Short.parseShort(value);
		} else if (sqlType.equalsIgnoreCase("INT")
				|| sqlType.equalsIgnoreCase("INTEGER")) {
			javaValue = Integer.parseInt(value);
		} else if (sqlType.equalsIgnoreCase("FLOAT")
				|| sqlType.equalsIgnoreCase("REAL")
				|| sqlType.equalsIgnoreCase("MONEY")
				|| sqlType.equalsIgnoreCase("NUMBER")
				|| sqlType.equalsIgnoreCase("DECIMAL")) {
			javaValue = Float.parseFloat(value);
		} else if (sqlType.equalsIgnoreCase("BIGINT")) {
			javaValue = Long.parseLong(value);
		} else if (sqlType.equalsIgnoreCase("DATE")) {
			try {
				javaValue = new SimpleDateFormat("yyyy-MM-dd").parse(value);
			} catch (ParseException e) {
				log.error("", e);
			}
		} else if (sqlType.equalsIgnoreCase("TIME")) {
			try {
				javaValue = new Time(new SimpleDateFormat("HH:mm:ss").parse(
						value).getTime());
			} catch (ParseException e) {
				log.error("", e);
			}
		} else if (sqlType.equalsIgnoreCase("DATETIME")
				|| sqlType.equalsIgnoreCase("TIMESTAMP")) {
			try {
				javaValue = new Timestamp(new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").parse(value).getTime());
			} catch (ParseException e) {
				log.error("", e);
			}
		} else if (sqlType.equalsIgnoreCase("BOOLEAN")) {
			if (value.equalsIgnoreCase("true")
					|| value.equalsIgnoreCase("false")) {
				javaValue = Boolean.parseBoolean(value);
			} else if (value.equalsIgnoreCase("0")) {
				javaValue = false;
			} else {
				javaValue = true;
			}
		} else if (sqlType.equalsIgnoreCase("ARRAY")) {
			javaValue = value.split(",");
		} else {
			javaValue = value;
		}
		return javaValue;
	}
	
	/**
	 * 对sql的语句的简单验证。
	 * 
	 * @param filterSql String
	 * 
	 * @return boolean
	 */
	public static boolean checkSqlStatement(String filterSql){
		String sql = filterSql;
		if(sql == null || "".equals(sql)) {
			return false;
		}
		sql = InternationalizationUtil.toLowerCase(sql);
		if (!sql.startsWith("select")) {
			return false;
		}
		if (sql.indexOf("drop") != -1) {
			return false;
		}
		if (sql.indexOf("insert") != -1) {
			return false;
		}
		if (sql.indexOf("update") != -1) {
			return false;
		}
		if (sql.indexOf("delete") != -1) {
			return false;
		}
		if (sql.indexOf("alter") != -1) {
			return false;
		}
		return true;
	}

}
