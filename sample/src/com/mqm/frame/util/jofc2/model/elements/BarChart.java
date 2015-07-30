/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.util.Arrays;
import java.util.List;

import com.mqm.frame.util.jofc2.model.metadata.Alias;
import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.BarConverter;

/**
 * 
 * <pre>
 * 程序的中文名称。
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
public class BarChart extends Element {

	private static final long serialVersionUID = 6695611795831460343L;

	private String colour;

	/**
	 * 构建器。
	 */
	public BarChart() {
		this(Style.NORMAL);
	}

	/**
	 * 构建器。
	 * 
	 * @param style Style
	 */
	public BarChart(Style style) {
		super(style.getStyle());
	}

	protected BarChart(String style) {
		super(style);
	}

	/**
	 * 新增Number。
	 * 
	 * @param values Number
	 * 
	 * @return BarChart
	 */
	public BarChart addValues(Number... values) {
		return addValues(Arrays.asList(values));
	}

	/**
	 * 新增Number。
	 * 
	 * @param values List<Number>
	 * @return BarChart
	 */
	public BarChart addValues(List<Number> values) {
		for (Number number : values) {
			if (number != null) {
				this.addBars(new Bar(number));
			}
		}
		return this;
	}

	/**
	 * 新增Bar.
	 * 
	 * @param bars Bar...
	 * @return BarChart
	 */
	public BarChart addBars(Bar... bars) {
		return addBars(Arrays.asList(bars));
	}

	/**
	 * 新增Bar。
	 * 
	 * @param bars List<Bar>
	 * @return BarChart
	 */
	public BarChart addBars(List<Bar> bars) {
		getValues().addAll(bars);
		return this;
	}

	/**
	 * 返回colour。
	 * 
	 * @return String
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * 设置colour。
	 * 
	 * @param colour String
	 * @return BarChart
	 */
	public BarChart setColour(String colour) {
		this.colour = colour;
		return this;
	}

	/**
	 * 定义枚举。
	 * 
	 * @return enum
	 */
	public static enum Style {
		/** 风格。 */
		NORMAL("bar"), 
		/** 风格。 */
		THREED("bar_3d"), 
		/** 风格。 */
		GLASS("bar_glass");

		private String style;

		Style(String style) {
			this.style = style;
		}

		/**
		 * 返回style。
		 * 
		 * @return String
		 */
		public String getStyle() {
			return style;
		}
	}

	/**
	 * 
	 * <pre>
	 * 程序的中文名称。
	 * </pre>
	 * 
	 * @author luoshifei luoshifei@foresee.cn
	 * @version 1.00.00
	 * 
	 *          <pre>
	 * 修改记录
	 *    修改后版本:     修改人：  修改日期:     修改内容:
	 * </pre>
	 */
	@Converter(BarConverter.class)
	public static class Bar {

		private Number top;
		private Number bottom;
		private String colour;
		@Alias("tip")
		private String tooltip;

		/**
		 * 构建器。
		 * 
		 * @param top
		 *            Number
		 * @param bottom
		 *            Number
		 * @param colour
		 *            String
		 */
		public Bar(Number top, Number bottom, String colour) {
			setTop(top);
			setBottom(bottom);
			setColour(colour);
		}

		/**
		 * 构建器。
		 * 
		 * @param top
		 *            Number
		 * @param bottom
		 *            Numer
		 */
		public Bar(Number top, Number bottom) {
			this(top, bottom, null);
		}

		/**
		 * 构建器。
		 * 
		 * @param top
		 *            Number
		 * @param colour
		 *            String
		 */
		public Bar(Number top, String colour) {
			this(top, null, colour);
		}

		/**
		 * 构建器。
		 * 
		 * @param top
		 *            Number
		 */
		public Bar(Number top) {
			this(top, null, null);
		}

		/**
		 * 返回top。
		 * 
		 * @return Number
		 */
		public Number getTop() {
			return top;
		}

		/**
		 * 设置top。
		 * 
		 * @param top
		 *            Number
		 * @return Bar
		 */
		public Bar setTop(Number top) {
			this.top = top;
			return this;
		}

		/**
		 * 返回bottom。
		 * 
		 * @return Numer
		 */
		public Number getBottom() {
			return bottom;
		}

		/**
		 * 设置bottom。
		 * 
		 * @param bottom
		 *            Number
		 * @return Bar
		 */
		public Bar setBottom(Number bottom) {
			this.bottom = bottom;
			return this;
		}

		/**
		 * 返回colour。
		 * 
		 * @return String
		 */
		public String getColour() {
			return colour;
		}

		/**
		 * 设置colour。
		 * 
		 * @param colour
		 *            String
		 * @return Bar
		 */
		public Bar setColour(String colour) {
			this.colour = colour;
			return this;
		}

		/**
		 * 返回tooltip。
		 * 
		 * @return String
		 */
		public String getTooltip() {
			return tooltip;
		}

		/**
		 * 设置tooltip。
		 * 
		 * @param tooltip
		 *            String
		 * 
		 * @return Bar
		 */
		public Bar setTooltip(String tooltip) {
			this.tooltip = tooltip;
			return this;
		}
	}

}
