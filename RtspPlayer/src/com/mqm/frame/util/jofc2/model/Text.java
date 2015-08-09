/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model;

import java.io.Serializable;

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
public class Text implements Serializable {

	private static final long serialVersionUID = -2390229886841547192L;

	/** 常量。 */
	public static final String TEXT_ALIGN_CENTER = "center";
	/** 常量。 */
	public static final String TEXT_ALIGN_LEFT = "left";
	/** 常量。 */
	public static final String TEXT_ALIGN_RIGHT = "right";
	private String text;
	private String style;

	/**
	 * 构建器。
	 */
	public Text() {
		this(null, null);
	}

	/**
	 * 构建器。
	 * 
	 * @param text String
	 */
	public Text(String text) {
		this(text, null);
	}

	/**
	 * 构建器。
	 * 
	 * @param text String
	 * @param style String
	 */
	public Text(String text, String style) {
		setText(text);
		setStyle(style);
	}

	/**
	 * 返回text。
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置text。
	 * 
	 * @param text String
	 * 
	 * @return Text
	 */
	public Text setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * 返回style。
	 * 
	 * @return 返回style。
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置style。
	 * 
	 * @param style String
	 * @return Text
	 */
	public Text setStyle(String style) {
		this.style = style;
		return this;
	}

	/**
	 * 创建风格。
	 * 
	 * @param fontsize int
	 * @param color String
	 * @param textalign String
	 * 
	 * @return String
	 */
	public static String createStyle(int fontsize, String color, String textalign) {
		StringBuilder sb = new StringBuilder();
		if (fontsize != 0) {
			sb.append("font-size: ");
			sb.append(fontsize);
			sb.append("px;");
		}
		if (color != null) {
			sb.append("color: ");
			sb.append(color);
			sb.append(";");
		}
		if (textalign != null) {
			sb.append("text-align: ");
			sb.append(textalign);
			sb.append(";");
		}
		return sb.toString();
	}
	
	/**
	 * 创建风格。
	 * 
	 * @param fontsize 字体大小。
	 * 
	 * @return String
	 */
	public static String createStyle(int fontsize){
		return createStyle(fontsize, null, null);
	}
	
}
