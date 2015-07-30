/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.util.Arrays;
import java.util.Collection;

import com.mqm.frame.util.jofc2.model.metadata.Alias;
import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.ScatterChartPointConverter;

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
public class ScatterChart extends Element {

	private static final String TYPE = "scatter";
	private static final long serialVersionUID = 3029567780918048503L;
	private String colour;
	@Alias("dot-size")
	private Integer dotSize;

	/**
	 * 默认的构造方法。
	 */
	public ScatterChart() {
		super(TYPE);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param style Style
	 */
    public ScatterChart(Style style){
        super(style.getStyle());
    }

    /**
     * 增加points。
     * 
     * @param points Point
     * 
     * @return ScatterChart
     */
	public ScatterChart addPoints(Point... points) {
		getValues().addAll(Arrays.asList(points));
		return this;
	}

	/**
	 * 增加points。
	 * 
	 * @param x Number
	 * @param y Number
	 * 
	 * @return ScatterChart
	 */
	public ScatterChart addPoint(Number x, Number y) {
		return addPoints(new Point(x, y));
	}

	/**
	 * 增加points。
	 * 
	 * @param points Collection<Point>
	 * 
	 * @return ScatterChart
	 */
	public ScatterChart addPoints(Collection<Point> points) {
		getValues().addAll(points);
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
	 * @param colour String
	 */
	public void setColour(String colour) {
		this.colour = colour;
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
	 * 设置dotSize。
	 * 
	 * @param dotSize Integer
	 */
	public void setDotSize(Integer dotSize) {
		this.dotSize = dotSize;
	}

	/**
	 * 
	 * <pre>
	 * 程序的中文名称。
	 * </pre>
	 * @author mmr  mmr@foresee.cn
	 * @version 1.00.00
	 * <pre>
	 * 修改记录
	 *    修改后版本:     修改人：  修改日期:     修改内容: 
	 * </pre>
	 */
	@Converter(ScatterChartPointConverter.class)
	public static class Point {

		private Number x;
		private Number y;

		/**
		 * 自定义的构造方法。
		 * 
		 * @param x Number
		 * @param y Number
		 */
		public Point(Number x, Number y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * 获取x。
		 * 
		 * @return Number
		 */
		public Number getX() {
			return x;
		}

		/**
		 * 设置x。
		 * 
		 * @param x Number
		 */
		public void setX(Number x) {
			this.x = x;
		}

		/**
		 * 获取y。
		 * 
		 * @return Number
		 */
		public Number getY() {
			return y;
		}

		/**
		 * 设置y。
		 * 
		 * @param y Number
		 */
		public void setY(Number y) {
			this.y = y;
		}
	}

	/**
	 * 
	 * <pre>
	 * 程序的中文名称。
	 * </pre>
	 * @author mmr  mmr@foresee.cn
	 * @version 1.00.00
	 * <pre>
	 * 修改记录
	 *    修改后版本:     修改人：  修改日期:     修改内容: 
	 * </pre>
	 */
    public static enum Style {
    	
    	/** 风格。*/
		NORMAL("scatter"), 
    	/** 风格。*/
		LINE("scatter_line");

		private String style;

		/**
		 * 默认的构造方法。
		 * 
		 * @param style String
		 */
		Style(String style) {
			this.style = style;
		}

		/**
		 * 获取style。
		 * 
		 * @return String
		 */
		public String getStyle() {
			return style;
		}
	}
}
