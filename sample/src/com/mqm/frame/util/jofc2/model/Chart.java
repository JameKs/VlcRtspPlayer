/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.mqm.frame.util.jofc2.OFC;
import com.mqm.frame.util.jofc2.OFCException;
import com.mqm.frame.util.jofc2.axis.XAxis;
import com.mqm.frame.util.jofc2.axis.YAxis;
import com.mqm.frame.util.jofc2.model.elements.Element;
import com.mqm.frame.util.jofc2.model.elements.Legend;
import com.mqm.frame.util.jofc2.model.elements.Tooltip;

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
public class Chart implements Serializable {

	private static final long serialVersionUID = -1868082240169089976L;
	
	private Text title;
	private XAxis x_axis;
	private YAxis y_axis;
	private YAxis y_axis_right;
	private Text y_legend;
	private Text x_legend;
	private String bg_colour;
	private int is_decimal_separator_comma = 0;
	private int is_fixed_num_decimals_forced = 0;
	private int is_thousand_separator_disabled = 0;
	private int num_decimals = 2;
	private Collection<Element> elements = new ArrayList<Element>();
	private Legend legend;
    private Tooltip tooltip;

    /**
     * 获取x_axis。
     * 
     * @return XAxis
     */
    public XAxis getXAxis() {
		return x_axis;
	}

    /**
     * 默认的构造方法。
     */
	public Chart() {
	//nothing...
	}

	/**
	 * 自定义的构造方法1。
	 * 
	 * @param titleText String
	 */
	public Chart(String titleText) {
		this(titleText, null);
	}

	/**
	 * 自定义的构造方法2。
	 * 
	 * @param titleText String
	 * @param style String
	 */
	public Chart(String titleText, String style) {
		this.setTitle(new Text(titleText, style));
	}

	/**
	 * 获取toolTip。
	 * 
	 * @return Tooltip
	 */
    public Tooltip getTooltip()
    {
        return tooltip;
    }

    /**
     * 设置toolTip。
     * 
     * @param tooltip Tooltip
     */
    public void setTooltip(Tooltip tooltip)
    {
        this.tooltip = tooltip;
    }

   /**
    * 设置x_axis。
    * 
    * @param x_axis XAxis
    * 
    * @return Chart
    */
    public Chart setXAxis(XAxis x_axis) {
		this.x_axis = x_axis;
		return this;
	}

    /**
     * 获取y_axis。
     * 
     * @return YAxis
     */
	public YAxis getYAxis() {
		return y_axis;
	}

	/**
	 * 设置y_axis。
	 * 
	 * @param y_axis YAxis
	 * 
	 * @return Chart
	 */
	public Chart setYAxis(YAxis y_axis) {
		this.y_axis = y_axis;
		return this;
	}

	/**
	 * 设置y_axis_right。
	 * 
	 * @param y_axis_right YAxis
	 * 
	 * @return  Chart
	 */
	public Chart setYAxisRight(YAxis y_axis_right) {
		this.y_axis_right = y_axis_right;
		return this;
	}

	/**
	 * 获取y_axis_right。
	 * 
	 * @return y_axis_right
	 */
	public YAxis getYAxisRight() {
		return y_axis_right;
	}

	/**
	 * 获取title。
	 * 
	 * @return Text
	 */
	public Text getTitle() {
		return title;
	}

	/**
	 * 设置title。
	 * 
	 * @param title Text
	 * 
	 * @return Chart
	 */
	public Chart setTitle(Text title) {
		this.title = title;
		return this;
	}

	/**
	 * 获取x_legend。
	 * 
	 * @return Text
	 */
	public Text getXLegend() {
		return x_legend;
	}

	/**
	 * 设置x_legend。
	 * 
	 * @param x_legend Text
	 * 
	 * @return Chart
	 */
	public Chart setXLegend(Text x_legend) {
		this.x_legend = x_legend;
		return this;
	}

	/**
	 * 获取y_legend。
	 * 
	 * @return Text
	 */
	public Text getYLegend() {
		return y_legend;
	}

	/**
	 * 设置y_legend。
	 * 
	 * @param y_legend Text
	 * 
	 * @return Chart
	 */
	public Chart setYLegend(Text y_legend) {
		this.y_legend = y_legend;
		return this;
	}

	/**
	 * 获取bg_colour。
	 * 
	 * @return String
	 */
	public String getBackgroundColour() {
		return bg_colour;
	}

	/**
	 * 设置bg_colour。
	 * 
	 * @param bg_colour String
	 * 
	 * @return  Chart
	 */
	public Chart setBackgroundColour(String bg_colour) {
		this.bg_colour = bg_colour;
		return this;
	}

	/**
	 * 获取elements。
	 * 
	 * @return Collection<Element> 
	 */
	public Collection<Element> getElements() {
		return elements;
	}

	/**
	 * 设置elements。
	 * 
	 * @param elements Collection<Element>
	 * 
	 * @return Chart
	 */
	public Chart setElements(Collection<Element> elements) {
		this.elements.clear();
		this.elements.addAll(elements);
		return this;
	}

	/**
	 * 增加一个element。
	 * 
	 * @param e Element
	 * 
	 * @return Chart
	 */
	public Chart addElements(Element... e) {
		elements.addAll(Arrays.asList(e));
		return this;
	}

	/**
	 * 增加elements集合。
	 * 
	 * @param coll Collection<Element>
	 * 
	 * @return Chart
	 */
	public Chart addElements(Collection<Element> coll) {
		elements.addAll(coll);
		return this;
	}

	/**
	 * 删除指定的element。
	 * 
	 * @param e  Element
	 * 
	 * @return boolean
	 */
	public boolean removeElement(Element e) {
		return elements.remove(e);
	}

	/**
	 * 根据text信息寻找element。
	 * 
	 * @param text String
	 * 
	 * @return Element
	 */
	public Element getElementByText(String text) {
		for (Element e : getElements()) {
			if (text.equals(e.getText())) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 输出字符串格式。
	 * 
	 * @return String
	 * 
	 * @throws OFCException can throw an OFCException if there is a problem
	 * rendering this Chart object. This exception would indicate an issue with
	 * the JOFC2 library itself.
	 */
    @Override
    public String toString() throws OFCException {
		return OFC.getInstance().render(this);
	}

	/**
	 * 面向调试的字符串格式输出。
	 * 
	 * @return a well formatted JSON File which is much more easy for debugging
     *         (toString() returns only one line)
	 */
	public String toDebugString() {
		return OFC.getInstance().prettyPrint(this, 3);
	}

	/**
	 * 判断is_decimal_separator_comma取值。
	 * 
	 * @return <code>true</code> if a comma is used as decimal separator and
	 *         <code>false</code> if a dot is used as decimal separator.
	 */
	public boolean isDecimalSeparatorComma() {
		return is_decimal_separator_comma == 1;
	}

	/**
	 * Configures the symbols used to format decimal numbers. If the given value
	 * is <code>false</code> the American format (e.g. 1,234.45) is used. If the
	 * given value is <code>true</code> the German format (1.234,45) is used.
	 * Other formats like the French one are not yet supported by OFC.
	 * 
	 * @param is_decimal_separator_comma
	 *           <code>true</code> sets the decimal format to German,
	 *           <code>false</code> to American.
	 */
	public void setDecimalSeparatorIsComma(boolean is_decimal_separator_comma) {
		this.is_decimal_separator_comma = is_decimal_separator_comma ? 1 : 0;
	}

	/**
	 * 判断is_fixed_num_decimals_forced取值。
	 * 
	 * @return <code>true</code> if decimals are fixed to num_decimals and
	 *         <code>false</code> if not.
	 */
	public boolean isFixedNumDecimalsForced() {
		return is_fixed_num_decimals_forced == 1;
	}

	/**
	 * Configures OFC to use fixed decimals (with num_decimals length). E.g.
	 * num_decimals=2 for <code>true</code> 1.1 will be 1.10 or 1 will be 1.00,
	 * for <code>false</code> 1.1 remains 1.1 and 1 remains 1
	 * 
	 * @param is_fixed_num_decimals_forced
	 *           <code>true</code> sets OFC to use fixed decimal length
	 *           <code>false</code> switches off fixed decimal length
	 */
	public void setFixedNumDecimalsForced(boolean is_fixed_num_decimals_forced) {
		this.is_fixed_num_decimals_forced = is_fixed_num_decimals_forced ? 1 : 0;
	}

	/**
	 * 判断is_thousand_separator_disabled取值。
	 * 
	 * @return <code>true</code> if thousand separators are used (e.g. 1.000 or
	 *         1,000... depending on is_decimal_separator_comma),
     *         <code>false</code> otherwise.
	 */
	public boolean isThousandSeparatorDisabled() {
		return is_thousand_separator_disabled == 1;
	}

	/**
	 * 设置is_thousand_separator_disabled取值。
	 * 
	 * @param is_thousand_separator_disabled
     *         <code>true</code> turns on the thousand separator (e.g. 1.000 or 1,000...
	 *         depending on is_decimal_separator_comma)
     *         <code>false</code> turns of the thousand separator (e.g. 1000)
	 */
	public void setThousandSeparatorDisabled(boolean is_thousand_separator_disabled) {
		this.is_thousand_separator_disabled = is_thousand_separator_disabled ? 1 : 0;
	}

	/**
	 * 获取num_decimals。
	 * 
	 * @return int
	 */
	public int getNumDecimals() {
		return num_decimals;
	}

	/**
	 * 设置num_decimals。
	 * 
	 * @param num_decimals int
	 */
	public void setNumDecimals(int num_decimals) {
		this.num_decimals = num_decimals;
	}

	/**
	 * 计算YAxis范围。
	 * 
	 * @param steps int
	 */
	public void computeYAxisRange(int steps) {
		Double min = null;
		double max = 0;
		double stepWidth = 1;
		if (getElements() != null) {
			if (getYAxis() == null) {
				YAxis ya = new YAxis();
				this.setYAxis(ya);
			}
			for (Element e : getElements()) {
				max = Math.max(max, e.getMaxValue());
				min = nullSafeMin(min, e.getMinValue());
			}
            if (min == null) {
                min = 0.0;
            }
            stepWidth = getStepWidth(Math.abs(max - min), steps);
			min = Math.floor(min / stepWidth) * stepWidth;
			max = Math.ceil(max / stepWidth) * stepWidth;
			getYAxis().setRange(min, max, stepWidth);

		}
	}
    
    private Double nullSafeMin(Double min, double doubleValue) {
        if (null == min) {
            min = doubleValue;
        }
        min = Math.min(min, doubleValue);
        return min;
    }

    private double getStepWidth(double distance, int steps) {
		double result = distance / steps;
		double exponent = Math.floor(Math.log10(result))+1;
		result = result / Math.pow(10, exponent);
		if (result > 0.5) {
			result = 1;
		} else if (result > 0.25) {
			result = 0.5;
		} else {
			result = 0.25;
		}
		return result * Math.pow(10, exponent);
	}

    /**
     * 获取legend。
     * 
     * @return Legend
     */
	public Legend getLegend() {
		return legend;
	}
	
	/**
	 * 设置legend。
	 * 
	 * @param legend Legend
	 */
	public void setLegend(Legend legend) {
		this.legend = legend;
	}

}
