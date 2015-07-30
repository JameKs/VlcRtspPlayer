/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.model;

import org.apache.ibatis.type.JdbcType;

/**
 * 
 * <pre>
 * 列模型。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ColumnModel {
	private boolean pk;
	private String property;
	private String column;
	private Class<?> javaType;
	private JdbcType jdbcType;
	private int sort;
	
   /**
    * 自定义的构造方法。
    * 
    * @param property String
    * @param column String
    * @param javaType  Class<?>
    * @param sort int
    */
	public ColumnModel(String property, String column, Class<?> javaType,int sort) {
		this.property = property;
		this.column = column;
		this.javaType = javaType;
		this.sort = sort;
	}
	
	/**
	 * 获取property。
	 * 
	 * @return String
	 */
	public String getProperty() {
		return property;
	}
	
	/**
	 * 设置property。
	 * 
	 * @param property String
	 */
	public void setProperty(String property) {
		this.property = property;
	}
	
	/**
	 * 获取column。
	 * 
	 * @return String
	 */
	public String getColumn() {
		return column;
	}
	
	/**
	 * 设置column。
	 * 
	 * @param column String
	 */
	public void setColumn(String column) {
		this.column = column;
	}
	
	/**
	 * 获取javaType。
	 * 
	 * @return  Class<?> 
	 */
	public Class<?> getJavaType() {
		return javaType;
	}
	
	/**
	 * 设置 javaType。
	 * 
	 * @param javaType Class<?>
	 */
	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}
	
	/**
	 * 获取jdbcType。
	 * 
	 * @return JdbcType
	 */
	public JdbcType getJdbcType() {
		return jdbcType;
	}
	
	/**
	 * 设置JdbcType。
	 * 
	 * @param jdbcType JdbcType
	 */
	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}
	
	/**
	 * 获取sort。
	 * 
	 * @return int
	 */
	public int getSort() {
		return sort;
	}
	
	/**
	 * 设置sort。
	 * 
	 * @param sort int
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	/**
	 * 获取pk。
	 * 
	 * @return boolean
	 */
	public boolean isPk() {
		return pk;
	}
	
	/**
	 * 设置pk。
	 * 
	 * @param pk boolean
	 */
	public void setPk(boolean pk) {
		this.pk = pk;
	}
}
