/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.strategy;

/**
 * 
 * <pre>
 * FBRP命名策略接口,如果需要自定义命名策略,可实现该接口。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface NamingStrategy {
	
	/**
	 * VO同表间命名策略。
	 * 
	 * @param className String
	 * 
	 * @return String
	 */
	public String classToTableName(String className);
	
	/**
	 * 属性同列间命名策略。
	 * 
	 * @param propertyName String
	 * 
	 * @return String
	 */
	public String propertyToColumnName(String propertyName);

	/**
	 * 外键同属性间命名策略。
	 * 
	 * @param propertyName String
	 * 
	 * @return String
	 */
	public String foreignKeyColumnName(String propertyName);

}
