/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mqm.frame.util.jofc2.model.elements.BarChart.Bar;
import com.mqm.frame.util.jofc2.model.elements.LineChart.Dot;
import com.mqm.frame.util.jofc2.model.elements.PieChart.Slice;
import com.mqm.frame.util.jofc2.model.metadata.Alias;

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
public abstract class Element implements Serializable {

	private static final long serialVersionUID = 3975314200083173622L;

	/**
	 * 常量。
	 */
	public static final String ON_CLICK_TOGGLE_VISIBILITY="toggle-visibility";
	
	private final String type;
	private Float alpha;
	private String text;
	@Alias("font-size")
	private Integer fontSize;
	@Alias("tip")
	private String tooltip;
	@Alias("gradient-fill")
	private Boolean gradientFill;
	@Alias("key-on-click")
	private String key_on_click;
	private List<Object> values = new ArrayList<Object>();

	protected Element(String type) {
		this.type = type;
	}

	/**
	 * 获取type。
	 * 
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获取alpha。
	 * 
	 * @return Float
	 */
	public Float getAlpha() {
		return alpha;
	}

	/**
	 * 设置alpha。
	 * 
	 * @param alpha Float
	 * 
	 * @return Element
	 */
	public Element setAlpha(Float alpha) {
		this.alpha = alpha;
		return this;
	}

	/**
	 * 获取text。
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}
	
   /**
    * The Text is used to represent the Element in the legend. If text is null the element will not appear in the legend.
    * 
    * @param text String
    * 
    * @return Element
    */
	public Element setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * 获取fontSize。
	 * 
	 * @return Integer
	 */
	public Integer getFontSize() {
		return fontSize;
	}

	/**
	 * 设置fontSize。
	 * 
	 * @param fontSize Integer
	 * 
	 * @return Element
	 */
	public Element setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
		return this;
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
	 * 设置tooltip。
	 * 
	 * @param tooltip String
	 * @return Element
	 */
	public Element setTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	/**
	 * 获取tooltip。
	 * 
	 * @return  String
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * 获取gradientFill。
	 * 
	 * @return Boolean
	 */
	public Boolean getGradientFill() {
		return gradientFill;
	}

	/**
	 * 设置gradientFill。
	 * 
	 * @param gradientFill Boolean
	 */
	public void setGradientFill(Boolean gradientFill) {
		this.gradientFill = gradientFill;
	}

	/**
	 * Returns the maximum value (double) of the given Element Supports only the
	 * Elements Dot, Bar, Slice and Horizontal Bar.
	 * 
	 * @return double
	 */
	public double getMaxValue() {
		double max = 0.0;
		for (Object obj : getValues()) {
			if (obj != null) {
				if (obj instanceof Number) {
					max = Math.max(max, ((Number) obj).doubleValue());
				} else if (obj instanceof Dot) {
					max = Math.max(max, ((Dot) obj).getValue() != null ? ((Dot) obj).getValue()
							.doubleValue() : 0);
				} else if (obj instanceof Bar) {
					max = Math.max(max, ((Bar) obj).getTop() != null ? ((Bar) obj).getTop()
							.doubleValue() : 0);
					max = Math.max(max, ((Bar) obj).getBottom() != null ? ((Bar) obj).getBottom()
							.doubleValue() : 0);
				} else if (obj instanceof Slice) {
					max = Math.max(max, ((Slice) obj).getValue() != null ? ((Slice) obj).getValue()
							.doubleValue() : 0);
				} else if (obj instanceof com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) {
					max = Math.max(max,
							((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getLeft() != null ? ((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getLeft()
									.doubleValue()
									: 0);
					max = Math.max(max,
							((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getRight() != null ? ((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getRight()
									.doubleValue()
									: 0);
				} else if (obj instanceof NullElement) {
					/* No action */
				} else {
					throw new IllegalArgumentException("Cannot process Objects of Class: " + String.valueOf(obj.getClass()));
				}
			}
		}
		return max;
	}

    /**
     * Returns the minimum value (double) of the given Element Supports only the
     * Elements Dot, Bar, Slice and Horizontal Bar.
     * 
     * @return  double
     */
    public double getMinValue() {
        Double min = null;
        for (Object obj : getValues()) {
            if (obj != null) {
                if (obj instanceof Number) {
                    min = nullSafeMin(min, ((Number) obj).doubleValue());
                } else if (obj instanceof Dot) {
                    min = nullSafeMin(min, ((Dot) obj).getValue() != null ? ((Dot) obj).getValue()
                            .doubleValue() : 0);
                } else if (obj instanceof Bar) {
                    min = nullSafeMin(min, ((Bar) obj).getTop() != null ? ((Bar) obj).getTop()
                            .doubleValue() : 0);
                    min = nullSafeMin(min, ((Bar) obj).getBottom() != null ? ((Bar) obj).getBottom()
                            .doubleValue() : 0);
                } else if (obj instanceof Slice) {
                    min = nullSafeMin(min, ((Slice) obj).getValue() != null ? ((Slice) obj).getValue()
                            .doubleValue() : 0);
                } else if (obj instanceof com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) {
                    min = nullSafeMin(min,
                            ((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getLeft() != null ? ((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getLeft()
                                    .doubleValue()
                                    : 0);
                    min = nullSafeMin(min,
                            ((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getRight() != null ? ((com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar) obj).getRight()
                                    .doubleValue()
                                    : 0);
                } else if (obj instanceof NullElement) {
                    /* No action */
                } else {
                    throw new IllegalArgumentException("Cannot process Objects of Class: " + String.valueOf(obj.getClass()));
                }
            }
        }
        if (null == min) {
            min = 0.0;
        }
        return min;
    }

    private Double nullSafeMin(Double min, double doubleValue) {
        if (null == min) {
            min = doubleValue;
        }
        min = Math.min(min, doubleValue);
        return min;
    }
	
    /**
     * 获取key_on_click。
     * 
     * @return String
     */
	public String getKey_on_click() {
		return key_on_click;
	}

	/**
	 * Set the Key on Click Funktion. e.g. "toggle-visibility". For a Link just pass the URL
	 * toggle-visibility will enable you to click on your legend an switch on and off individual elements.
	 * 
	 * @param key_on_click String
	 */
	public void setKey_on_click(String key_on_click) {
		this.key_on_click = key_on_click;
	}
	
	/**
	 * Shortcut for setKey_on_click(ON_CLICK_TOGGLE_VISIBILITY).
	 */
	public void setToggleVisibility(){
		this.key_on_click = ON_CLICK_TOGGLE_VISIBILITY;
	}
}
