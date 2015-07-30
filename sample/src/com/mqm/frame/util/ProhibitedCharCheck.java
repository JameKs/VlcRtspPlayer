/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 禁止文字检查器。
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
public class ProhibitedCharCheck {

	/**
	 * 禁止若干特殊字符。
	 */
	public static char[] prohibitedChar = { 0x2460, 0x2461, 0x2462, 0x2463,
			0x2464, 0x2465, 0x2466, 0x2467, 0x2468, 0x2469, 0x246a, 0x246b,
			0x246c, 0x246d, 0x246e, 0x246f, 0x2470, 0x2471, 0x2472, 0x2473,
			0x3349, 0x3314, 0x3322, 0x334d, 0x3318, 0x3327, 0x3303, 0x3336,
			0x3351, 0x3357, 0x330d, 0x3326, 0x3323, 0x332b, 0x334a, 0x333b,
			0x339c, 0x339d, 0x339e, 0x338e, 0x338f, 0x33c4, 0x33a1, 0x337b,
			0x301d, 0x301f, 0x33cd, 0x32a4, 0x32a5, 0x32a6, 0x32a7, 0x32a8,
			0x3232, 0x3239, 0x337e, 0x337d, 0x337c, 0x222e, 0x2211, 0x221f,
			0x22bf, 0x2160, 0x2161, 0x2162, 0x2163, 0x2164, 0x2165, 0x2166,
			0x2167, 0x2168, 0x2169, 0x3231, 0x2116, 0x2121, 0x007e, 0x203e,
			0x329e, 0x00A2, 0x00A3, 0x00AC, 0x005C };

	/** 追加文字。 */
	private char[] addChars = null;

	/** 省略文字。 */
	private char[] subChars = null;

	/** 包含的禁止文字。 */
	private char errorChars;

	/**
	 * 构建器。
	 */
	public ProhibitedCharCheck() {
		super();
	}

	/**
	 * 构建器。
	 * 
	 * @param addChars
	 *            在禁止文字中追加禁止文字
	 * @param subChars
	 *            从禁止文字中指定省略的禁止文字
	 */
	public ProhibitedCharCheck(char[] addChars, char[] subChars) {
		this.addChars = addChars;
		this.subChars = subChars;
	}

	/**
	 * 检查文字列中是否存在禁止文字。
	 * 
	 * @param value
	 *            需检查的文字列
	 *            
	 * @return 存在禁止文字true 不存在禁止文字false
	 */
	public boolean isProhibited(String value) {

		// 初始化
		List<String> charList = new ArrayList<String>();

		// 把禁止文字放入列表中
		for (int i = 0; i < prohibitedChar.length; i++) {
			charList.add(String.valueOf(prohibitedChar[i]));
		}

		if (value == null) {
			return false;
		}

		// 在禁止文字列中追加禁止文字
		if (addChars != null && addChars.length != 0) {
			for (int i = 0; i < addChars.length; i++) {
				String tempAddChar = String.valueOf(addChars[i]);

				// 判断如果不存在就追加
				if (!charList.contains(tempAddChar)) {
					charList.add(tempAddChar);
				}
			}
		}

		// 从禁止文字列中去除subChars
		if (subChars != null && subChars.length != 0) {
			for (int i = 0; i < subChars.length; i++) {
				String tempSubChar = String.valueOf(subChars[i]);

				if (charList.contains(tempSubChar)) {
					charList.remove(tempSubChar);
				}
			}
		}

		// 如果包含任何一个禁止文字就返回true
		for (int j = 0; j < value.length(); j++) {
			String tempChar = value.substring(j, j + 1);
			if (charList.contains(tempChar)) {
				errorChars = tempChar.charAt(0);
				return true;
			}
		}

		return false;
	}

	/**
	 * 取得禁止文字列。
	 * 
	 * @return errorChars
	 */
	public char getFoundChar() {
		return errorChars;
	}

}
