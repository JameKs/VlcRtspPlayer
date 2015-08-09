/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect;

import java.util.Map;

import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTable;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTableCol;

/**
 * 
 * <pre>
 * 屏蔽不同数据库的差异化，包括分页解决方案（适用于MyBatis、JDBC）、获取数据库元数据等。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IDialect {

	/**
	 * 获取数据库中表的信息。
	 * 
	 * @param ds 数据源
	 * 
	 * @return Map<String, FbrpDbtoolTableVO> 返回获取到的数据库表信息
	 */
	public Map<String, FbrpDbtoolTable> getTables(FbrpDbtoolDs ds);
	
	/**
	 * 获取数据库中表的信息。
	 * 
	 * @param dsId 数据源ID
	 * 
	 * @return Map<String, FbrpDbtoolTableVO> 返回获取到的数据库表信息
	 */
	//TODO 在TD数据库下还需要有数据库名信息，直接使用该方法会出错，需要处理。
	public Map<String, FbrpDbtoolTable> getTables(String dsId);
	
	/**
	 * 获取数据库中指定表中的字段信息。
	 * 
	 * @param ds 数据源
	 * @param tableName 要获得表信息的表名
	 *            
	 * @return Map<String, FbrpDbtoolTableColVO>
	 */
	public Map<String, FbrpDbtoolTableCol> getTableColInfo(FbrpDbtoolDs ds, String tableName);
	
	/**
	 * 是否支持分页。
	 * 
	 * @return boolean 是否支持分页
	 */
	public abstract boolean supportsPaged();

	/**
	 * 返回分页SQL。
	 * 
	 * @param sql String
	 * @param flag boolean
	 * @return String
	 */
	public abstract String getPagedString(String sql, boolean flag);

	/**
	 * 返回分页语句。
	 * 
	 * @param sql SQL语句
	 * @param offset 起始行
	 * @param limit 页大小
	 * 
	 * @return String 分页语句
	 */
	public abstract String getPagedString(String sql, int offset, int limit);

}