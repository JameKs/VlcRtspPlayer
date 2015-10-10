/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public  class Order {
	
	private String property;
	private String column;
	private boolean ascending;
	
	private Order(String property,boolean ascending){
		this.property = property;
		this.ascending = ascending;
	}
	/**
	 * 静态方法升序排序。
	 * 
	 * @param property String
	 * @return Order
	 */
	public static Order ASC(String property){
		return new Order(property,true);
	}
	
	/**
	 * 静态方法降序排序。
	 * 
	 * @param property String
	 * @return Order
	 */
	public static Order DESC(String property){
		return new Order(property,false);
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
	 * 设置Column。
	 * 
	 * @param column String
	 */
	public void setColumn(String column) {
		this.column = column;
	}
	
	@Override
	public String toString() {
		return this.getProperty() + ":"+this.getColumn() + " " +this.getOrder();
	}
	
	/**
	 * 获取Order。
	 * 
	 * @return String
	 */
	public String getOrder(){
		return ascending?"asc":"desc";
	}

	
}
