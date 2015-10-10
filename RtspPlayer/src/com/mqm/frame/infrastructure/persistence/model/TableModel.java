/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * <pre>
 * 表模型。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class TableModel {
	private String tableName;
	private Class<?> cls;
	private List<ColumnModel> cols = new ArrayList<ColumnModel>();
	private List<ColumnModel> pks = new ArrayList<ColumnModel>();

	
	/**
	 * 自定义的构造方法。
	 * 
	 * @param tableName String
	 * @param cls Class<?>
	 */
	public TableModel(String tableName, Class<?> cls) {
		super();
		this.tableName = tableName;
		this.cls = cls;
	}
	
	/**
	 * 获取tableName。
	 * 
	 * @return String
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 设置tableName。
	 * 
	 * @param tableName String
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * 获取cls。
	 * 
	 * @return Class<?>
	 */
	public Class<?> getCls() {
		return cls;
	}
	
	/**
	 * 设置cls。
	 * 
	 * @param cls Class<?>
	 */
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	
	/**
	 * 获取cols。
	 * 
	 * @return List<ColumnModel>
	 */
	public List<ColumnModel> getCols() {
		return cols;
	}
	/**
	 * 增加cols。
	 * 
	 * @param col ColumnModel
	 */
	public void addColumn(ColumnModel col){
		this.cols.add(col);
	}
	/**
	 * 增加pks。
	 * 
	 * @param col ColumnModel
	 */
	public void addPk(ColumnModel col){
		this.pks.add(col);
	}
	/**
	 * 获取pks。
	 * 
	 * @return List<ColumnModel>
	 */
	public List<ColumnModel> getPks() {
		return pks;
	}
	
	/**
	 * 获取AllCols。
	 * 
	 * @return  List<ColumnModel>
	 */
	public List<ColumnModel> getAllCols() {
		ArrayList<ColumnModel> ret = new ArrayList<ColumnModel>();
		ret.addAll(this.pks);
		ret.addAll(this.cols);
		return Collections.unmodifiableList(ret);
	}

}
