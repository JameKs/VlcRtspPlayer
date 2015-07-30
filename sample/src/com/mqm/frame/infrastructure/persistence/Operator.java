/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence;

/**
 * 
 * <pre>
 * 对应到SQL运算符表达式。
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
public enum Operator {
	/** “=”。 */
	EQ,
	/** “<”。 */
	LT,
	/** “<=”。 */
	LQ,
	/** “>”。 */
	GT,
	/** “>=”。 */
	GQ,
	/** “<>”。 */
	NOT,
	/** “like”。 */
	LIKE,
	/** “in”。 */
	IN,
	/** “not in”。 */
	NOT_IN,
	/** “is null”。 */
	IS_NULL,
	/** “is not null”。 */
	IS_NOT_NULL,
	/** “(”。 */
	L_BRACKET,
	/** “)”。 */
	R_BRACKET,
	/** “or”。 */
	OR;

	@Override
	public String toString() {
		switch (this) {
		case EQ:
			return "=";
		case LT:
			return "<";
		case LQ:
			return "<=";
		case GT:
			return ">";
		case GQ:
			return ">=";
		case NOT:
			return "<>";
		case LIKE:
			return "like";
		case IN:
			return "in";
		case NOT_IN:
			return "not in";
		case IS_NULL:
			return "is null";
		case IS_NOT_NULL:
			return "is not null";
		case L_BRACKET:
			return "(";
		case R_BRACKET:
			return ")";
		case OR:
			return "or";
		default:
			return "";
		}
	};

}
