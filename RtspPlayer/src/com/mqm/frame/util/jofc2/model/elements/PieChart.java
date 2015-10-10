/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.mqm.frame.util.jofc2.model.metadata.Alias;
import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.PieChartSliceConverter;

/**
 * 
 * <pre>
 * 饼图。
 * </pre>
 * 
 * @author linjunxiong linjunxiong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class PieChart extends Element {

	private static final long serialVersionUID = 8853434988212173862L;

	@Alias("start-angle")
	private Integer startAngle;
	private Collection<String> colours;
	private Boolean animate;
	private Integer border;
	private Integer radius;
	@Alias("no-labels")
	private Boolean noLabels;

	/**
	 * 默认的构造方法。
	 */
	public PieChart() {
		super("pie");
	}

	/**
	 * 设置animate。
	 * 
	 * @param animate
	 *            boolean
	 * @return PieChart
	 */
	public PieChart setAnimate(boolean animate) {
		this.animate = animate;
		return this;
	}

	/**
	 * 获取animate。
	 * 
	 * @return Boolean
	 */
	public Boolean getAnimate() {
		return animate;
	}

	/**
	 * 获取startAngle。
	 * 
	 * @return Integer
	 */
	public Integer getStartAngle() {
		return startAngle;
	}

	/**
	 * 设置.
	 * 
	 * @param startAngle
	 *            Integer
	 * 
	 * @return PieChart
	 */
	public PieChart setStartAngle(Integer startAngle) {
		this.startAngle = startAngle;
		return this;
	}

	/**
	 * 获得颜色.
	 * 
	 * @return Collection
	 */
	public Collection<String> getColours() {
		return colours;
	}

	/**
	 * 设置颜色。
	 * 
	 * @param colours
	 *            Collection<String>
	 * 
	 * @return PieChart
	 */
	public PieChart setColours(Collection<String> colours) {
		checkColours();
		this.colours = colours;
		return this;
	}

	/**
	 * 设置颜色。
	 * 
	 * @param colours
	 *            String...
	 * 
	 * @return PieChart
	 */
	public PieChart setColours(String... colours) {
		checkColours();
		this.colours.clear();
		this.colours.addAll(Arrays.asList(colours));
		return this;
	}

	/**
	 * 设置颜色。
	 * 
	 * @param colours
	 *            List<String>
	 * 
	 * @return PieChart
	 */
	public PieChart setColours(List<String> colours) {
		checkColours();
		this.colours.clear();
		this.colours.addAll(colours);
		return this;
	}

	/**
	 * 返回border。
	 * 
	 * @return Integer
	 */
	public Integer getBorder() {
		return border;
	}

	/**
	 * 设置border。
	 * 
	 * @param border
	 *            Integer
	 * @return PieChart
	 */
	public PieChart setBorder(Integer border) {
		this.border = border;
		return this;
	}

	/**
	 * 新增Number。
	 * 
	 * @param values Number...
	 * @return PieChart
	 */
	public PieChart addValues(Number... values) {
		getValues().addAll(Arrays.asList(values));
		return this;
	}

	/**
	 * 新增List<Number>。
	 * 
	 * @param values List<Number>
	 * 
	 * @return PieChart
	 */
	public PieChart addValues(List<Number> values) {
		for (Number number : values) {
			// Ignore null values cause they dont make sense in pie Charts
			if (number != null) {
				getValues().add(number);
			}
		}
		return this;
	}

	/**
	 * 新增Slice。
	 * 
	 * @param value Number
	 * @param text String
	 * 
	 * @return PieChart
	 */
	public PieChart addSlice(Number value, String text) {
		return addSlices(new Slice(value, text));
	}

	/**
	 * 新增Slice。
	 * 
	 * @param s Slice...
	 * @return PieChart
	 */
	public PieChart addSlices(Slice... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}

	/**
	 * 新增List<Slice>。
	 * 
	 * @param values List<Slice>
	 * @return PieChart
	 */
	public PieChart addSlices(List<Slice> values) {
		getValues().addAll(values);
		return this;
	}

	/**
	 * 
	 * <pre>
	 * 程序的中文名称。
	 * </pre>
	 * @author luoshifei  luoshifei@foresee.cn
	 * @version 1.00.00
	 * <pre>
	 * 修改记录
	 *    修改后版本:     修改人：  修改日期:     修改内容: 
	 * </pre>
	 */
	@Converter(PieChartSliceConverter.class)
	public static class Slice implements Serializable {

		private static final long serialVersionUID = 6961394996186973937L;
		private final String label;
		private String tip;
		private String highlight = "alpha";
		private String text;
		private final Number value;

		/**
		 * 构建器。
		 * 
		 * @param value Number
		 * @param label String
		 */
		public Slice(Number value, String label) {
			this.label = label;
			this.value = value;
		}

		/**
		 * The slice changes alpha on mouse over instead of breaking out the
		 * pie.
		 */
		public void setOnMouseOverAlpha() {
			this.highlight = "alpha";
		}

		/**
		 * The slice breaks out of the pie on mouse over instead of changing
		 * its. alpha。
		 */
		public void setOnMouseOverBreakout() {
			this.highlight = null;
		}

		/**
		 * 返回value。
		 * 
		 * @return Number value
		 */
		public Number getValue() {
			return value;
		}

		/**
		 * 返回label。
		 * 
		 * @return String
		 */
		public String getLabel() {
			return label;
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
		 * 返回tip。
		 * 
		 * @return the tip Text for the slice. If Tip isset it overrides the
		 *         label
		 */
		public String getTip() {
			return tip;
		}

		/**
		 * Sets the tip Text for the slice. If Tip isset it overrides the label。
		 * 
		 * @param tip
		 *            the Text to set
		 */
		public void setTip(String tip) {
			this.tip = tip;
		}

		/**
		 * 返回highlight。
		 * 
		 * @return String
		 */
		public String getHighlight() {
			return highlight;
		}

		/**
		 * This value is the Representation of the Slice in the legend (if it is
		 * rendered).
		 * 
		 * @param text String
		 */
		public void setText(String text) {
			this.text = text;
		}
	}

	private synchronized void checkColours() {
		if (colours == null) {
			colours = new ArrayList<String>();
		}
	}

	/**
	 * 返回noLabels。
	 * 
	 * @return Boolean
	 */
	public Boolean getNoLabels() {
		return noLabels;
	}

	/**
	 * 设置noLabels.
	 * 
	 * @param noLabels
	 *            设置noLabels.
	 */
	public void setNoLabels(Boolean noLabels) {
		this.noLabels = noLabels;
	}

	/**
	 * 返回radius.
	 * 
	 * @return Integer
	 */
	public Integer getRadius() {
		return radius;
	}

	/**
	 * "radius" allows you to force the radius of the pis to a certain size. If
	 * this is left out of the JSON then the pie will resize itself so that it
	 * and all of its labels fit in the display area (as best as it can).
	 * 
	 * @param radius
	 *            Ineger
	 * 
	 */
	public void setRadius(Integer radius) {
		this.radius = radius;
	}

}
