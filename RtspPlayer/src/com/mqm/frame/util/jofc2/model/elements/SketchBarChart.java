/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import com.mqm.frame.util.jofc2.model.metadata.Alias;

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
public class SketchBarChart extends FilledBarChart {

	private static final long serialVersionUID = 7562070898232847510L;

	private static final transient String TYPE = "bar_sketch";
	
	@Alias("offset")
	private Integer funFactor;

	/**
	 * 默认的构造方法。
	 */
	public SketchBarChart() {
		super(TYPE);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param colour
	 *            String
	 * @param outlineColour
	 *            String
	 * @param funFactor
	 *            Integer
	 */
	public SketchBarChart(String colour, String outlineColour, Integer funFactor) {
		super(TYPE);
		setColour(colour);
		setOutlineColour(outlineColour);
		setFunFactor(funFactor);
	}

	/**
	 * 获取funFactor。
	 * 
	 * @return Integer
	 */
	public Integer getFunFactor() {
		return funFactor;
	}

	/**
	 * 设置funFactor。
	 * 
	 * @param funFactor
	 *            Integer
	 * @return BarChart
	 */
	public BarChart setFunFactor(Integer funFactor) {
		this.funFactor = funFactor;
		return this;
	}

	/**
	 * 
	 * <pre>
	 * 程序的中文名称。
	 * </pre>
	 * 
	 * @author mmr mmr@foresee.cn
	 * @version 1.00.00
	 * 
	 *          <pre>
	 * 修改记录
	 *    修改后版本:     修改人：  修改日期:     修改内容:
	 * </pre>
	 */
	public static class Bar extends FilledBarChart.Bar {
		@Alias("offset")
		private Integer funFactor;

		/**
		 * 默认的构造方法。
		 * 
		 * @param top Number
		 */
		public Bar(Number top) {
			super(top);
		}

		/**
		 * 自定义的构造方法。
		 * 
		 * @param top
		 *            Number
		 * @param funFactor
		 *            Integer
		 */
		public Bar(Number top, Integer funFactor) {
			super(top);
			setFunFactor(funFactor);
		}

		/**
		 * 自定义的构造方法。
		 * 
		 * @param top
		 *            Number
		 * @param bottom
		 *            Number
		 * @param funFactor
		 *            Integer
		 */
		public Bar(Number top, Number bottom, Integer funFactor) {
			super(top, bottom);
			setFunFactor(funFactor);
		}

		/**
		 * 设置funFactor。
		 * 
		 * @param funFactor
		 *            Integer
		 * @return Bar
		 */
		public Bar setFunFactor(Integer funFactor) {
			this.funFactor = funFactor;
			return this;
		}

		/**
		 * 获取funFactor。
		 * 
		 * @return Integer
		 */
		public Integer getFunFactor() {
			return funFactor;
		}
	}
}
