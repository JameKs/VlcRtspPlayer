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
public class FilledBarChart extends BarChart {

	private static final long serialVersionUID = 3471991868191065273L;

	private static final transient String TYPE = "bar_filled";

	@Alias("outline-colour")
	private String outlineColour;

	/**
	 * 默认的构造方法。
	 */
	public FilledBarChart() {
		super(TYPE);
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param colour
	 *            String
	 * @param outlineColour
	 *            String
	 */
	public FilledBarChart(String colour, String outlineColour) {
		super(TYPE);
		setColour(colour);
		setOutlineColour(outlineColour);
	}

	protected FilledBarChart(String style) {
		super(style);
	}

	/**
	 * 获取outlineColour。
	 * 
	 * @return String
	 */
	public String getOutlineColour() {
		return outlineColour;
	}

	/**
	 * 设置outlineColour。
	 * 
	 * @param outlineColour
	 *            String
	 * @return BarChart
	 */
	public BarChart setOutlineColour(String outlineColour) {
		this.outlineColour = outlineColour;
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
	public static class Bar extends BarChart.Bar {
		@Alias("outline-colour")
		private String outlineColour;

		/**
		 * 自定义的构造方法。
		 * 
		 * @param top
		 *            Number
		 * @param bottom
		 *            Number
		 */
		public Bar(Number top, Number bottom) {
			super(top, bottom);
		}

		/**
		 * 自定义的构造方法。
		 * 
		 * @param top
		 *            Number
		 * @param bottom
		 *            Number
		 * @param colour
		 *            String
		 * @param outlineColour
		 *            String
		 */
		public Bar(Number top, Number bottom, String colour,
				String outlineColour) {
			super(top, bottom);
			setColour(colour);
			setOutlineColour(outlineColour);
		}

		/**
		 * 自定义的构造方法。
		 * 
		 * @param top
		 *            Number
		 */
		public Bar(Number top) {
			super(top);
		}

		/**
		 * 设置outlineColour。
		 * 
		 * @param outlineColour
		 *            String
		 * @return Bar
		 */
		public Bar setOutlineColour(String outlineColour) {
			this.outlineColour = outlineColour;
			return this;
		}

		/**
		 * 设置outlineColour。
		 * 
		 * @return String
		 */
		public String getOutlineColour() {
			return outlineColour;
		}
	}
}
