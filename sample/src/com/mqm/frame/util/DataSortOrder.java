/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

/**
 * <pre>
 * 用于排序查询时，指定升序或降序。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public enum DataSortOrder {
	/**
	 * 升序，如：0,1,2,3 ...。
	 */
	ASC,
	
	/**
	 * 降序，如：9,8,7,6 ... 。
	 */
	DESC;
	
	/**
	 * 转换成字符串。
	 * 
	 * @return String
	 */
	public String toString() {
		if (this.equals(ASC)) {
			return " asc";
		} else {
			return " desc";
		}
	};

}
