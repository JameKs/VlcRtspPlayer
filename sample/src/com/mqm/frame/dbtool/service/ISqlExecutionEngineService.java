/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.service;

import java.util.List;
import java.util.Map;

import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * SQL执行引擎。
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
public interface ISqlExecutionEngineService {
	
	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_dbtool_sqlExecutionEngineService";
	
	/**
	 * 直接执行一条select查询语句，所有对数据源查询的基类。
	 * 如果conn为null，则认为是在系统的数据库里进行操作。
	 * 
	 * @param dsId
	 *            数据源ID
	 * @param sql
	 *            一条标准的select语句
	 * @param start
	 *            记录的开始位置
	 * @param maxRowCounts
	 *            返回的记录的条 （-1表示返回满足条件的所有的记录数）。
	 *            
	 * @return 返回的记录值，value为列所对应的值,已查询先后顺序排序
	 * 
	 * @throws FbrpException FBRP异常。
	 */
	public List<String[]> querySqlSimple(String dsId,
			String sql, int start, int maxRowCounts) throws FbrpException;

	/**
	 * 直接执行一条select查询语句,所有对数据源查询的基类，
	 * 如果conn为null，则认为是在系统的数据库里进行操作。
	 * 
	 * @param dsId
	 *            数据源ID
	 * @param sql
	 *            一条标准的select语句
	 * @param start
	 *            记录的开始位置
	 * @param maxRowCounts
	 *            返回的记录的条 （-1表示返回满足条件的所有的记录数）。
	 *            
	 * @return 返回的记录值，key为列名，value为列所对应的值
	 * 
	 * @throws FbrpException FBRP异常。
	 */
	public List<Map<String, Object>> querySql(String dsId,
			String sql, int start, int maxRowCounts) throws FbrpException;

	/**
	 * 执行带有参数的sql语句。
	 *  如果conn为null，则认为是在系统的数据库里进行操作。
	 *  
	 * @param dsId
	 *            数据源ID。
	 * @param sql
	 *            带有参数的sql
	 * @param map
	 *            参数的集合，key为参数名，value为参数的值
	 * @param startIndex
	 *            记录的开始位置，-1表示返回满足条件的所有记录，不进行分页。
	 * @param maxRowCounts 每页数量
	 * 
	 * @return List 
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public List<Map<String, Object>> querySql(String dsId,
			String sql, Map<String, Object> map, int startIndex,
			int maxRowCounts) throws FbrpException;

	/**
	 * 直接执行一条'update','insert','delete'语句的操作。
	 *  如果conn为null，则认为是在系统的数据库里进行操作。
	 *  
	 * @param dsId
	 *            数据源ID
	 * @param sql
	 *            'update','insert','delete'类型的sql语句。
	 *            
	 * @return 返回所影响的记录的条数。
	 * 
	 * @throws FbrpException FBRP异常。
	 */
	public Integer executeSql(String dsId, String sql)
			throws FbrpException;

	/**
	 * 
	 * 执行一组是否需要事务支持的sql语句（即：'update','insert','delete'）。
	 *  如果conn为null，则认为是在系统的数据库里进行操作。
	 *  
	 * @param dsId
	 *            数据源ID。
	 * @param sqls
	 *            一组'update','insert','delete'类型的sql语句
	 * @param transactionSuppoort
	 *            是否需要事务的支持
	 *            
	 * @return 是否执行成功，若启动事务，执行时出现异常，则事务回滚 返回false，否则返回true;若不启动事务
	 *         执行时出现异常，则返回false，反之返回true;
	 * 
	 * @throws FbrpException FBRP异常。
	 */
	public Boolean executeSqlBatch(String dsId, String[] sqls,
			Boolean transactionSuppoort) throws FbrpException;

	/**
	 * 查询满足条件的记录条数。
	 * 如果conn为null，则认为是在系统的数据库里进行操作。
	 * 
	 * @param dsId
	 *            数据源ID。
	 * @param sql SQL语句。
	 * 
	 * @return 记录的条数，分页查询的时候用到
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public Integer getResultCount(String dsId, String sql)
			throws FbrpException;

	/**
	 * 根据数据库表的名称和数据库的连接查询表的所有记录。
	 *  如果conn为null，则认为是在系统的数据库里进行操作。
	 *  
	 * @param tableName
	 *            数据库的表名称
	 * @param dsId
	 *            数据源ID。
	 * @param start
	 *            记录的开始位置
	 * @param maxRowCount
	 *            要查询的记录的最大条数 若为-1，则查询所有记录;否则,返回实际的记录条数
	 *            
	 * @return 返回的一组记录值，key为列名，value为列所对应的值
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public List<Map<String, Object>> getALLResult(String tableName,
			String dsId, Integer start, Integer maxRowCount)
			throws FbrpException;

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
			throws FbrpException;

	/**
	 * 对数据库进行增、删、改的操作。
	 *  如果conn为null，则认为是在系统的数据库里进行操作。
	 *  
	 * @param tableName
	 *            数据库的表名
	 * @param dsId
	 *            数据源ID。
	 * @param savedatas
	 *            要保存的数据,map的key为tableName对应表的colName,value为值
	 * @param updatedatas
	 *            要更新的数据数据, key为对应表的主键。value为要更新的列名和值。
	 * @param deleteDatas
	 *            要删除的数据,key为对应表的主键，value为一组要删除的记录的主键值。
	 *            
	 * @throws FbrpException FBRP异常
	 */
	public void doHandleTable(String tableName, String dsId,
			List<Map<String, Object>> savedatas,
			Map<Map<String, Object>, Map<String, Object>> updatedatas,
			Map<String, Object> deleteDatas) throws FbrpException;
	
	/**
	 * 执行存储过程，不支持有返回参数的存储过程,考虑到存储过程返回结果集时。<br>
	 * 必须用到游标做为输出参数,在程序内部封装了个游标类型的变量,调用时不用 另外再传输出参数了。
	 * 
	 * @param dsId
	 *            数据源<br >
	 * @param procName
	 *            存储过程名称<br >
	 * @param params
	 *            输入参数参数列表
	 * @param types 参数类型
	 * 
	 * @return 用List包装的查询结果.List里的元素是HashMap封装的查询结果集的每一行记录<br >
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public List queryProc(String dsId, final String procName,
			final String[] params, final String[] types)
			throws FbrpException ;	

}
