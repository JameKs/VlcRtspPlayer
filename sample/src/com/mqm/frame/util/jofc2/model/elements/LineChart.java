/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.util.Arrays;
import java.util.List;

import com.mqm.frame.util.jofc2.model.metadata.Alias;
import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.DotConverter;

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
public class LineChart extends Element {

	private static final long serialVersionUID = 8807130855547088579L;
	private static transient final Integer DEFAULT_FONTSIZE = 10;
	private Integer width;
	@Alias("dot-size")
	private Integer dotSize;
	@Alias("halo-size")
	private Integer haloSize;
	private String colour;
	private String axis;

	/**
	 * 获取axis。
	 * 
	 * @return String
	 */
	public String getYaxis() {
		return axis;
	}

	/**
	 * 设置axis。
	 * 
	 * @param yAxis
	 *            String
	 */
	public void setYAxis(String yAxis) {
		this.axis = yAxis;
	}

	/**
	 * Tells the LineChart to Use the right YAxis.
	 * 
	 */
	public void setRightYAxis() {
		setYAxis("right");
	}

	/**
	 * 覆写默认的构造方法。
	 */
	public LineChart() {
		this(Style.NORMAL);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param style
	 *            Style
	 */
	public LineChart(Style style) {
		this(style.getStyle());
	}

	protected LineChart(String type) {
		super(type);
		setFontSize(DEFAULT_FONTSIZE);
	}

	/**
	 * 获取宽度。
	 * 
	 * @return Integer
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * 设置宽度。
	 * 
	 * @param width
	 *            Integer
	 * 
	 * @return LineChart
	 */
	public LineChart setWidth(Integer width) {
		this.width = width;
		return this;
	}

	/**
	 * 获取dotSize。
	 * 
	 * @return Integer
	 */
	public Integer getDotSize() {
		return dotSize;
	}

	/**
	 * 设置 dotSize。
	 * 
	 * @param dotSize
	 *            Integer
	 * 
	 * @return LineChart
	 */
	public LineChart setDotSize(Integer dotSize) {
		this.dotSize = dotSize;
		return this;
	}

	/**
	 * 获取colour。
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
	 * 
	 * @return LineChart
	 */
	public LineChart setColour(String colour) {
		this.colour = colour;
		return this;
	}

	/**
	 * 增加value。
	 * 
	 * @param values
	 *            Number
	 * 
	 * @return LineChart
	 */
	public LineChart addValues(Number... values) {
		return addValues(Arrays.asList(values));
	}

	/**
	 * 增加values。
	 * 
	 * @param values
	 *            List<Number>
	 * 
	 * @return LineChart
	 */
	public LineChart addValues(List<Number> values) {
		// We convert all Numbers to Dots except Null Values they are Converted
		// to a Null Element
		// as Dots with the value null are interpreted as Zero from OFC and null
		// Values themself are interpolated
		for (Number number : values) {
			if (number == null) {
				getValues().add(new NullElement());
			} else {
				getValues().add(number);
			}
		}
		return this;
	}

	/**
	 * 新增Dot。
	 * 
	 * @param dots
	 *            Dot...
	 * 
	 * @return LineChart
	 */
	public LineChart addDots(Dot... dots) {
		return addDots(Arrays.asList(dots));
	}

	/**
	 * 新增List<Dot>。
	 * 
	 * @param dots
	 *            List<Dot>
	 * 
	 * @return LineChart
	 */
	public LineChart addDots(List<Dot> dots) {
		getValues().addAll(dots);
		return this;
	}

	/**
	 * 返回haloSize。
	 * 
	 * @return Integer
	 */
	public Integer getHaloSize() {
		return haloSize;
	}

	/**
	 * 设置haloSize。
	 * 
	 * @param haloSize
	 *            Integer
	 * 
	 * @return LineChart
	 */
	public LineChart setHaloSize(Integer haloSize) {
		this.haloSize = haloSize;
		return this;
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
	@Converter(DotConverter.class)
	public static class Dot {

		@Alias("halo-size")
		private Integer haloSize;
		@Alias("dot-size")
		private Integer dotSize;
		private Number value;
		private String colour;

		/**
		 * 构建器。
		 * 
		 * @param value
		 *            Number
		 */
		public Dot(Number value) {
			this(value, null, null, null);
		}

		/**
		 * 构建器。
		 * 
		 * @param value
		 *            Number
		 * @param colour
		 *            String
		 */
		public Dot(Number value, String colour) {
			this(value, colour, null, null);
		}

		/**
		 * 构建器。
		 * 
		 * @param value
		 *            Number
		 * @param colour
		 *            String
		 * @param dotSize
		 *            Integer
		 * @param haloSize
		 *            Integer
		 */
		public Dot(Number value, String colour, Integer dotSize,
				Integer haloSize) {
			setValue(value);
			setColour(colour);
			setDotSize(dotSize);
			setHaloSize(haloSize);
		}

		/**
		 * 返回haloSize。
		 * 
		 * @return Integer
		 */
		public Integer getHaloSize() {
			return haloSize;
		}

		/**
		 * 设置haloSize。
		 * 
		 * @param haloSize
		 *            Integer
		 * 
		 * @return Dot
		 */
		public Dot setHaloSize(Integer haloSize) {
			this.haloSize = haloSize;
			return this;
		}

		/**
		 * 返回dotSize。
		 * 
		 * @return Integer
		 */
		public Integer getDotSize() {
			return dotSize;
		}

		/**
		 * 设置dotSize。
		 * 
		 * @param dotSize
		 *            Integer
		 * @return Dot
		 */
		public Dot setDotSize(Integer dotSize) {
			this.dotSize = dotSize;
			return this;
		}

		/**
		 * 返回value。
		 * 
		 * @return Number
		 */
		public Number getValue() {
			return value;
		}

		/**
		 * 设置value。
		 * 
		 * @param value
		 *            Number
		 * @return Dot
		 */
		public Dot setValue(Number value) {
			this.value = value;
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
		 * 设置colur。
		 * 
		 * @param colour
		 *            String
		 * @return Dot
		 */
		public Dot setColour(String colour) {
			this.colour = colour;
			return this;
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
	public static enum Style {
		/** 线条风格。*/
		NORMAL("line"),
		/** 线条风格。*/
		DOT("line_dot"), 
		/** 线条风格。*/
		HOLLOW("line_hollow");

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
}
