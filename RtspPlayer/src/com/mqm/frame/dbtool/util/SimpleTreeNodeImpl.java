/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.util;

import com.mqm.frame.util.web.TreeNodeImpl;

/**
 * <pre>
 * 简单树,增加leafIcon, icon, type属性。
 * </pre>
 * 
 * @param <T> ValueObject
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class SimpleTreeNodeImpl<T> extends TreeNodeImpl<T> {

	private static final long serialVersionUID = 7934685815606467807L;

	private String icon;
	private String leafIcon;

	private String type;

	/**
	 * 返回type。
	 * 
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置type。
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取icon。
	 * 
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置icon。
	 * 
	 * @param icon String
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取leafIcon。
	 * 
	 * @return String
	 */
	public String getLeafIcon() {
		return leafIcon;
	}

	/**
	 * 设置leafIcon。
	 * 
	 * @param leafIcon String
	 */
	public void setLeafIcon(String leafIcon) {
		this.leafIcon = leafIcon;
	}

}
