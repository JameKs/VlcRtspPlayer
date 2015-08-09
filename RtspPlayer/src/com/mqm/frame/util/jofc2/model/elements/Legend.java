/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

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
public class Legend implements Serializable{

	private static final long serialVersionUID = -1103000336598511514L;

	private String position;
	private boolean visible;
	private String bg_colour;
	private String border_color;
	private boolean shadow;
	private Integer margin;
	private Integer alpha;
	private Integer padding;
	private boolean border;
	private Integer stroke;

	/**
	 * Default constructor for Legend. Automatically sets visibility to true and
	 * position to right cause they are the only correct values at that time.
	 */
	public Legend() {
		super();
		setVisible(true);
		setPosition("right");
	}

	/**
	 * 获取position。
	 * 
	 * @return String
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets the position of the legend. Actually there is only the right side
	 * supported.
	 * 
	 * @param position String
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * 获取visible。
	 * 
	 * @return boolean
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Determines if the legend should be rendered or not (atm you should set it
	 * to true if you pass a legend to chart).
	 * 
	 * @param visible boolean
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * 获取bg_colour。
	 * 
	 * @return String
	 */
	public String getBg_colour() {
		return bg_colour;
	}

	/**
	 * Sets the backgroud color of the legend. E.g. "#FFFFFF" for white.
	 * 
	 * @param bg_colour  String
	 */
	public void setBg_colour(String bg_colour) {
		this.bg_colour = bg_colour;
	}

	/**
	 * 获取border_color。
	 * 
	 * @return String
	 */
	public String getBorder_color() {
		return border_color;
	}

	/**
	 * Sets the border color of the legend. E.g. "#FFFFFF" for white.
	 * 
	 * @param border_color String
	 */
	public void setBorder_color(String border_color) {
		this.border_color = border_color;
	}

	/**
	 * 获取shadow。
	 * 
	 * @return boolean
	 */
	public boolean isShadow() {
		return shadow;
	}

	/**
	 * Switch if shadows should be used or not.
	 * 
	 * 设置shadow
	 * 
	 * @param shadow boolean
	 */
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	/**
	 * 获取margin。
	 * 
	 * @return Integer
	 */
	public Integer getMargin() {
		return margin;
	}

	/**
	 * "margin" defines the number of pixel spacing around the outside of the
	 * right side legend box. This can be used to adjust how close the legend
	 * appears to the edge of the window and to the right side Y legend or right
	 * edge of the chart if there is no right side legend.
	 * 
	 * @param margin Integer
	 */
	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	/**
	 * 获取alpha。
	 * 
	 * @return Integer
	 */
	public Integer getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha (transparency value) of the legend.
	 * 
	 * @param  alpha int
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * 获取padding。
	 * 
	 * @return Integer
	 */
	public Integer getPadding() {
		return padding;
	}

	/**
	 * "padding" defines a number of pixels to place between the labels and the
	 * border of the right side legend.
	 * 
	 * @param padding int
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}

	/**
	 * 获取border。
	 * 
	 * @return boolean
	 */
	public boolean isBorder() {
		return border;
	}

	/**
	 * "border" defines whether or not to draw a border line around the right
	 * side legend box.
	 * 
	 * @param border boolean
	 */
	public void setBorder(boolean border) {
		this.border = border;
	}

	/**
	 * 获取stroke。
	 * 
	 * @return Integer
	 */
	public Integer getStroke() {
		return stroke;
	}

	/**
	 * "stroke" defines the size of the border line around the right side legend
	 * box.
	 * 
	 * @param stroke int
	 */
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}
}
