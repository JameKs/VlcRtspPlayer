/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.dao;

import javax.sql.DataSource;

/**
 * 
 * <pre>
 * FBRP自身使用的数据源。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpDataSource {
	
	//数据源对象
	private DataSource dataSource;
	
	/**
	 * 取数据源。
	 * 
	 * @return DataSource 数据源
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 通过Spring注入设置数据源。
	 * 
	 * @param dataSource 数据源
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}
