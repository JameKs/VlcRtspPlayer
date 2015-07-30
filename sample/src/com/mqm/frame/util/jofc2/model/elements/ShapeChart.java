/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.util.Arrays;
import java.util.List;

import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.ShapePointConverter;

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
public class ShapeChart extends Element {

	private static final long serialVersionUID = -5491121778724754279L;

	private String colour;

	/**
	 * 默认的构造方法。
	 */
	public ShapeChart() {
		this(null);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param colour String
	 */
	public ShapeChart(String colour) {
		super("shape");
		setColour(colour);
	}

	/**
	 * 增加Points。
	 *  
	 * @param points Point
	 * @return ShapeChart
	 */
	public ShapeChart addPoints(Point... points) {
		getValues().addAll(Arrays.asList(points));
		return this;
	}

	/**
	 * 增加Points。
	 * 
	 * @param points List<Point>
	 * @return ShapeChart
	 */
	public ShapeChart addPoints(List<Point> points) {
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
	 * @return ShapeChart
	 */
	public ShapeChart setColour(String colour) {
		this.colour = colour;
		return this;
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
	@Converter(ShapePointConverter.class)
	public static class Point {

		private final Number x;
		private final Number y;

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
		 * 获取y。
		 * 
		 * @return Number
		 */
		public Number getY() {
			return y;
		}
	}
}