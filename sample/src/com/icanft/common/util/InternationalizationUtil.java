/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

/**
 * <pre>
 * 处理同国际化相关的细节。
 * </pre>
 * @author mmr  mmr@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class InternationalizationUtil {

	/**
	 * 处理String.getBytes()。
	 * 
	 * @param str String
	 * @return byte[]
	 */
	public static byte[] getBytes(String str){
		if(str == null){
			return null;
		}
		return str.getBytes();
	}

	/**
	 * 处理String.toUpperCase()。
	 * 
	 * @param str String
	 * @return String
	 */
	public static String toUpperCase(String str){
		if(str == null){
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * 处理String.toLowerCase()。
	 * 
	 * @param str String
	 * @return String
	 */
	public static String toLowerCase(String str){
		if(str == null){
			return null;
		}
		return str.toLowerCase();
	}

}
