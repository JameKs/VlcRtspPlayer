/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.hibernate.hql;

import java.util.ArrayList;
import java.util.List;

import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * 动态拼装HQL语句的各种从句。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HqlWrapper extends AbstractWrapper {

	/**
	 * “=”。
	 */
	public static final String EQUAL = "=";
	/**
	 * “<>”。
	 */
	public static final String NOT_EQUAL = "<>";
	/**
	 * “>”。
	 */
	public static final String GREATER_THAN = ">";
	/**
	 * “>=”。
	 */
	public static final String GREATER_EQUAL = ">=";
	/**
	 * “<”。
	 */
	public static final String LESS_THAN = "<";
	/**
	 * “<=”。
	 */
	public static final String LESS_EQUAL = "<=";
	/**
	 * “%value”。
	 */
	public static final String RIGHT_INCLUDE = "%value";
	/**
	 * “value%”。
	 */
	public static final String LEFT_INCLUDE = "value%";
	/**
	 * “%value%”。
	 */
	public static final String INCLUDE = "%value%";
	/**
	 * “%value%”。
	 */
	public static final String INCLUDE_IGNORECASE = "%value%";
	/**
	 * “isnull”。
	 */
	public static final String ISNULL = "isnull";
	/**
	 * “isnotnull”。
	 */
	public static final String ISNOTNULL = "isnotnull";
	/**
	 * “isempty”。
	 */
	public static final String ISEMPTY = "isempty";
	/**
	 * “isnotempty”。
	 */
	public static final String ISNOTEMPTY = "isnotempty";
	/**
	 * “in”。
	 */
	public static final String IN = "in";
	/**
	 * “notin”。
	 */
	public static final String NOTIN = "notin";

	private String hql;
	private String orderBy;
	private String groupBy;
	private String otherHql;
	private List<String> propertyNames;
	private List<String> operators;
	private List<Object> values;

	/**
	 * 默认的构造方法。
	 */
	public HqlWrapper() {
		hql = null;
		orderBy = null;
		groupBy = null;
		propertyNames = new ArrayList<String>();
		operators = new ArrayList<String>();
		values = new ArrayList<Object>();
		otherHql = null;
	}
 
	/**
	 * 设置条件。
	 * 
	 * @param propertyName String
	 * @param operator String
	 * @param value Object
	 */
	public void setCondition(String propertyName, String operator, Object value) {
		if (propertyName == null || propertyName.trim().equals("")) {
			return;
		}
		if (operator == null || operator.trim().equals("")) {
			operator = "=";
		}
		if (operator.equals("isnull") || operator.equals("isnotnull")
				|| operator.equals("isempty") || operator.equals("isnotempty")) {
			value = operator;
		}
		if (value == null) {
			return;
		}
		if ((value instanceof String) && value.toString().trim().equals("")) {
			return;
		}
		if (value instanceof String) {
			value = value.toString().trim();
		}

		propertyNames.add(propertyName);
		if (operator.equals(INCLUDE)) {
			operators.add("like");
			values.add((new StringBuilder()).append("%").append(value)
					.append("%").toString());
		} else if (operator.equals(INCLUDE_IGNORECASE)) {
			operators.add("like");
			values.add((new StringBuilder()).append("%")
					.append(InternationalizationUtil.toLowerCase(value.toString())).append("%")
					.toString());
		} else if (operator.equals(LEFT_INCLUDE)) {
			operators.add("like");
			values.add((new StringBuilder()).append(value).append("%")
					.toString());
		} else if (operator.equals(RIGHT_INCLUDE)) {
			operators.add("like");
			values.add((new StringBuilder()).append("%").append(value)
					.toString());
		} else {
			operators.add(operator);
			values.add(value);
		}
	}

	/**
	 * 等于。
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public void setConditionEqual(String propertyName, Object value) {
		setCondition(propertyName, EQUAL, value);
	}

	/**
	 * 设置查询条件NotEqual。
	 * 
	 * @param propertyName String
	 * @param value  Object
	 */
	public void setConditionNotEqual(String propertyName, Object value) {
		setCondition(propertyName, NOT_EQUAL, value);
	}

	/**
	 * 设置查询条件大于。
	 * 
	 * @param propertyName String
	 * @param value Object
	 */
	public void setConditionGreaterThan(String propertyName, Object value) {
		setCondition(propertyName, GREATER_THAN, value);
	}

	/**
	 * 设置查询条件大于等于。
	 * 
	 * @param propertyName String
	 * @param value Object
	 */
	public void setConditionGreaterEqual(String propertyName, Object value) {
		setCondition(propertyName, GREATER_EQUAL, value);
	}

	/**
	 * 设置查询条件小于。
	 * 
	 * @param propertyName  String
	 * @param value  Object
	 */
	public void setConditionLessThan(String propertyName, Object value) {
		setCondition(propertyName, LESS_THAN, value);
	}

	/**
	 * 设置查询条件小于等于。
	 * 
	 * @param propertyName String
	 * @param value  Object
	 */
	public void setConditionLessEqual(String propertyName, Object value) {
		setCondition(propertyName, LESS_EQUAL, value);
	}

	/**
	 * 设置查询条件RIGHT_INCLUDE。
	 * 
	 * @param propertyName String
	 * @param value Object
	 */
	public void setConditionRightInclude(String propertyName, Object value) {
		setCondition(propertyName, RIGHT_INCLUDE, value);
	}

	/**
	 * 设置查询LEFT_INCLUDE。
	 * 
	 * @param propertyName String
	 * @param value  Object
	 */
	public void setConditionLeftInclude(String propertyName, Object value) {
		setCondition(propertyName, LEFT_INCLUDE, value);
	}

	/**
	 * 设置查询条件INCLUDE。
	 * 
	 * @param propertyName String
	 * @param value Object
	 */
	public void setConditionInclude(String propertyName, Object value) {
		setCondition(propertyName, INCLUDE, value);
	}

	/**
	 * 设置查询条件IncludeIgnoreCase。
	 * 
	 * @param propertyName String
	 * @param value Object
	 */
	public void setConditionIncludeIgnoreCase(String propertyName, Object value) {
		if (value != null && value instanceof String) {
			value = InternationalizationUtil.toLowerCase(((String) value).trim());
		}
		setCondition("lower(" + propertyName + ")", INCLUDE_IGNORECASE, value);
	}

	/**
	 * 设置查询条件为空。
	 * 
	 * @param propertyName String
	 */
	public void setConditionIsNull(String propertyName) {
		setCondition(propertyName, ISNULL, "isnull");
	}

	/**
	 * 设置查询条件为非空。
	 * 
	 * @param propertyName String
	 */
	public void setConditionIsNotNull(String propertyName) {
		setCondition(propertyName, ISNOTNULL, "isnotnull");
	}

	/**
	 * 设置查询条件isEmpty。
	 * 
	 * @param propertyName String
	 */
	public void setConditionIsEmpty(String propertyName) {
		setCondition(propertyName, ISEMPTY, "isempty");
	}

	/**
	 * 设置查询条件IsNotEmpty。
	 * 
	 * @param propertyName String
	 */
	public void setConditionIsNotEmpty(String propertyName) {
		setCondition(propertyName, ISNOTEMPTY, "isnotempty");
	}

	/**
	 * 设置查询条件In。
	 * 
	 * @param propertyName String
	 * @param value  List<?>
	 */ 
	public void setConditionIn(String propertyName, List<?> value) {
		setCondition(propertyName, IN, value);
	}

	/**
	 * 设置查询条件NotIn。
	 * 
	 * @param propertyName String
	 * @param value  List<?>
	 */
	public void setConditionNotIn(String propertyName, List<?> value) {
		setCondition(propertyName, NOTIN, value);
	}

	/**
	 * 获取hql。
	 * 
	 * @return  String
	 */
	public String getHql() {
		return hql;
	}

	/**
	 * 设置hql。
	 * 
	 * @param hql String
	 */
	public void setHql(String hql) {
		this.hql = hql;
	}

	/**
	 * 获取orderBy。
	 * 
	 * @return String
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置orderBy。
	 * 
	 * @param orderBy String
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获取groupBy。
	 * 
	 * @return String
	 */
	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * 设置groupBy。
	 * 
	 * @param groupBy String
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * 获取otherHql。
	 * 
	 * @return String
	 */
	public String getOtherHql() {
		return otherHql;
	}

	/**
	 * 设置otherHql。
	 * 
	 * @param otherHql String
	 */
	public void setOtherHql(String otherHql) {
		this.otherHql = otherHql;
	}

	/**
	 * 获取propertyName。
	 * 
	 * @return  List<String>
	 */
	public List<String> getPropertyNames() {
		return propertyNames;
	}

	/**
	 *  设置propertyNames。
	 *  
	 * @param propertyNames List<String>
	 */
	public void setPropertyNames(List<String> propertyNames) {
		this.propertyNames = propertyNames;
	}

	/**
	 * 获取operators。
	 * 
	 * @return  List<String>
	 */
	public List<String> getOperators() {
		return operators;
	}

	/**
	 * 设置operators。
	 * 
	 * @param operators List<String>
	 */
	public void setOperators(List<String> operators) {
		this.operators = operators;
	}

	/**
	 * 获取values。
	 * 
	 * @return List<Object>
	 */
	public List<Object> getValues() {
		return values;
	}

	/**
	 * 设置values。
	 * 
	 * @param values List<Object>
	 */
	public void setValues(List<Object> values) {
		this.values = values;
	}

}