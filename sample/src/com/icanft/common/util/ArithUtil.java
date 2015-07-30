/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ArithUtil {

	private static final int DEF_DIV_SCALE = 10;

	private ArithUtil() {
	}
	
   /**
    * 比小。
    * 
    * @param d1 double
    * @param d2 double
    * 
    * @return  boolean
    */
	public static boolean isDoubleNotGreater(double d1, double d2) {
		boolean bn = false;
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		if (b1.compareTo(b2) <= 0) {
			bn = true;
		} else {
			bn = false;
		}
		return bn;
	}

	/**
	 * 比大。
	 * 
	 * @param d1 double
	 * @param d2 double
	 * 
	 * @return boolean
	 */
	public static boolean isDoubleNotLess(double d1, double d2) {
		boolean bn = false;
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		if (b1.compareTo(b2) >= 0) {
			bn = true;
		} else {
			bn = false;
		}
		return bn;
	}

	/**
	 * 比等于。
	 * 
	 * @param d1 double
	 * @param d2 double
	 * 
	 * @return boolean
	 */
	public static boolean isDoubleEqual(double d1, double d2) {
		boolean bn = false;
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		if (b1.compareTo(b2) == 0) {
			bn = true;
		} else {
			bn = false;
		}
		return bn;
	}

	/**
	 * 加法。
	 * 
	 * @param d1 double
	 * @param d2 double
	 * 
	 * @return double
	 */ 
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法。
	 * 
	 * @param d1 double
	 * @param d2 double
	 * 
	 * @return double
	 */
	public static double sub(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 乘法。
	 * 
	 * @param d1 double
	 * @param d2 double
	 * 
	 * @return double
	 */
	public static double mul(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 除法。
	 * 
	 * @param d1 double
	 * @param d2 double
	 * @param scale int
	 * 
	 * @return double
	 */
	public static double div(double d1, double d2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else {
			BigDecimal b1 = new BigDecimal(Double.toString(d1));
			BigDecimal b2 = new BigDecimal(Double.toString(d2));
			return b1.divide(b2, scale, 4).doubleValue();
		}
	}

	/**
	 * 除法。
	 * 
	 * @param d1 double
	 * @param d2 double
	 * 
	 * @return double
	 */
	public static double div(double d1, double d2) {
		return div(d1, d2, 10);
	}

	/**
	 * 圆整。
	 * 
	 * @param d double
	 * @param scale int
	 * 
	 * @return double
	 */
	public static double round(double d, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else {
			BigDecimal b = new BigDecimal(Double.toString(d));
			BigDecimal one = BigDecimal.ONE;
			return b.divide(one, scale, 4).doubleValue();
		}
	}

	/**
	 * 取字符串部分内容。
	 * 
	 * @param subStr String
	 * @param n int
	 * 
	 * @return String
	 */
	public static String genPattern(String subStr, int n) {
		StringBuffer temp = new StringBuffer();
		for (; n > 0; n--) {
			if (subStr.trim().equals("#") && n == 1) {
				temp.append("0");
			} else {
				temp.append(subStr);
			}
		}
		return temp.toString();
	}

	/**
	 * 将double格式化成字符串。
	 * 
	 * @param number double
	 * @param intPart int
	 * @param decPart int
	 * 
	 * @return String
	 */
	public static String formatDouble(double number, int intPart, int decPart) {
		return (new DecimalFormat((new StringBuilder())
				.append(genPattern("#", intPart)).append(".")
				.append(genPattern("0", decPart)).toString())).format(number);
	}

	/**
	 * 将BigDecimal格式化成字符串。
	 * 
	 * @param number BigDecimal
	 * @param intPart int
	 * @param decPart int
	 * 
	 * @return String
	 */
	public static String formatDecimal(BigDecimal number, int intPart,
			int decPart) {
		return (new DecimalFormat((new StringBuilder())
				.append(genPattern("#", intPart)).append(".")
				.append(genPattern("0", decPart)).toString())).format(number);
	}

	/**
	 * 将BigDecimal格式化成字符串。
	 * 
	 * @param number BigDecimal
	 * 
	 * @return String
	 */
	public static String formatDecimal(BigDecimal number) {
		String tmp = "";
		tmp = (new DecimalFormat((new StringBuilder())
				.append(genPattern("#", 12)).append(".")
				.append(genPattern("0", 12)).toString())).format(number).trim();
		int i;
		for (i = tmp.length() - 1; i > 0 && tmp.charAt(i) == '0'; i--) {
			;
		}
		return tmp.substring(0, i + 1);
	}

}