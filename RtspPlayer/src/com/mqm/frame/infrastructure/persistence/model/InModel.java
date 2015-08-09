/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.model;

/**
 * 
 * <pre>
 * 模型类，对应SQL语句中的in语句,包括属性名及值集合。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class InModel {
	private String property;
	private Object values ;
	private boolean notIn;
	
	/**
	 * 自定义的构造方法。
	 * 
	 * @param property String
	 * @param values Object
	 */
	public InModel(String property, Object values) {
		this(property, values, false);
	}
	
	/**
	 * 自定义的构造方法。
	 * 
	 * @param property String
	 * @param values Object
	 * @param notIn boolean
	 */
	public InModel(String property, Object values, boolean notIn) {
		this.property = property;
		this.values = values;
		this.notIn = notIn;
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
	 * 获取values。
	 * 
	 * @return Object
	 */
	public Object getValues() {
		return values;
	}
	
	/**
	 * 设置values。
	 * 
	 * @param values Object
	 */
	public void setValues(Object values) {
		this.values = values;
	}
	
	/**
	 * 获取InFlag。
	 * 
	 * @return String
	 */
	public String getInFlag(){
		if(notIn){
			return "not in";
		}else{
			return "in";
		}
	}
}
