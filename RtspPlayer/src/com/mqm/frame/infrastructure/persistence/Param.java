/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence;

import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * 程序的中文名称。
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
public class Param {
	private Operator operator;
	private String alias = Constants.DEFAULT_ALIAS;
	private String property;
	private String column;
	private Object value;
	private String logic = "and";
	private boolean ig;

	/**
	 * 默认的构造方法。
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            Object
	 */
	public Param(String property, Object value) {
		this(property, Operator.EQ, value, false);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param property
	 *            String
	 * @param value
	 *            Object
	 * @param ig
	 *            boolean
	 */
	public Param(String property, Object value, boolean ig) {
		this(property, Operator.EQ, value, ig);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param property
	 *            String
	 * @param operator
	 *            Operator
	 */
	public Param(String property, Operator operator) {
		this(property, operator, null, false);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param property
	 *            String
	 * @param operator
	 *            Operator
	 * @param value
	 *            Object
	 */
	public Param(String property, Operator operator, Object value) {
		this(property, operator, value, false);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param operator
	 *            Operator
	 */
	public Param(Operator operator) {
		this(null, operator, null);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param property
	 *            String
	 * @param operator
	 *            Operator
	 * @param value
	 *            Object
	 * @param ig
	 *            boolean
	 */
	public Param(String property, Operator operator, Object value, boolean ig) {
		if (property != null) {
			int offset = property.indexOf(".");
			if (offset > -1) {
				this.alias = property.substring(0, offset);
				this.property = property.substring(offset + 1);
			} else {
				this.property = property;
			}
		}
		this.value = value;
		this.ig = ig;
		this.operator = operator;
	}

	/**
	 * 获取value。
	 * 
	 * @return Object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 获取operator。
	 * 
	 * @return String
	 */
	public String getOperatorSign() {
		return operator.toString();
	}

	/**
	 * 获取operator。
	 * 
	 * @return Operator
	 */
	public Operator getOperator() {
		return this.operator;
	}

	/**
	 * 设置alias。
	 * 
	 * @param alias
	 *            String
	 */
	public void setAlias(String alias) {
		this.alias = alias;
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
	 * @param column
	 *            String
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 获取columnx。
	 * 
	 * @return String
	 */
	public String getColumnx() {
		if (this.isLogicalCalculus()) {
			return "";
		}
		String columnx = alias + "." + this.column;
		if (ig) {
			return "lower(" + columnx + ")";
		} else {
			return columnx;
		}
	}

	/**
	 * 获取value。
	 * 
	 * @return Object
	 */
	public Object getValuex() {
		if (this.isLogicalCalculus()) {
			return null;
		}
		if (ig && this.value instanceof String) {
			return InternationalizationUtil.toLowerCase(value.toString());
		} else {
			return value;
		}
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
	 * @param property
	 *            String
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取alias。
	 * 
	 * @return String
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * 获取logic。
	 * 
	 * @return String
	 */
	public String getLogic() {
		return logic;
	}

	/**
	 * 设置logic。
	 * 
	 * @param logic
	 *            String
	 */
	public void setLogic(String logic) {
		this.logic = logic;
	}

	@Override
	public String toString() {
		return this.getValue().toString();
	}

	/**
	 * 获取isLogicalCalculus。
	 * 
	 * @return boolean
	 */
	public boolean isLogicalCalculus() {
		return (Operator.L_BRACKET == this.operator
				|| Operator.R_BRACKET == this.operator || Operator.OR == this.operator);
	}
}
