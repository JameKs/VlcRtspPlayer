/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import java.util.Arrays;
import java.util.List;

import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.HorizontalBarChartBarConverter;

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
public class HorizontalBarChart extends Element {

	private static final long serialVersionUID = 3320580794787784639L;

	private String colour;

	/**
	 * 构建器。
	 */
	public HorizontalBarChart() {
		super("hbar");
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
	 * @return HorizontalBarChart
	 */
	public HorizontalBarChart setColour(String colour) {
		this.colour = colour;
		return this;
	}

	/**
	 * 新增Bar。
	 * 
	 * @param bars
	 *            Bar...
	 * @return HorizontalBarChart
	 */
	public HorizontalBarChart addBars(Bar... bars) {
		getValues().addAll(Arrays.asList(bars));
		return this;
	}

	/**
	 * 新增List<Bar>。
	 * 
	 * @param bars
	 *            List<Bar>
	 * 
	 * @return HorizontalBarChart
	 */
	public HorizontalBarChart addBars(List<Bar> bars) {
		getValues().addAll(bars);
		return this;
	}

	/**
	 * 新增Bar。
	 * 
	 * @param rightValues
	 *            Number...
	 * 
	 * @return HorizontalBarChart
	 */
	public HorizontalBarChart addValues(Number... rightValues) {
		Bar[] values = new Bar[rightValues.length];
		for (int i = 0; i < rightValues.length; ++i) {
			values[i] = new Bar(rightValues[i]);
		}
		return addBars(values);
	}

	/**
	 * 新增Bar。
	 * 
	 * @param rightValues List<Number>
	 * @return HorizontalBarChart
	 */
	public HorizontalBarChart addValues(List<Number> rightValues) {
		for (Number number : rightValues) {
			if (number != null) {
				this.addBars(new Bar(number));
			}
		}
		return this;
	}

	/**
	 * 新增Bar。
	 * 
	 * @param left Number
	 * @param right Number
	 * 
	 * @return HorizontalBarChart
	 */
	public HorizontalBarChart addBar(Number left, Number right) {
		return addBars(new Bar(left, right));
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
	@Converter(HorizontalBarChartBarConverter.class)
	public static class Bar {
		private final Number right;
		private Number left;

		/**
		 * 构建器。
		 * 
		 * @param right Number
		 */
		public Bar(Number right) {
			this(null, right);
		}

		/**
		 * 构建器。
		 * 
		 * @param left Number
		 * @param right Number
		 */
		public Bar(Number left, Number right) {
			if (right == null) {
				throw new NullPointerException("Field is mandatory.");
			}
			this.right = right;
			setLeft(left);
		}

		/**
		 * 返回right。
		 * 
		 * @return right Number。
		 */
		public Number getRight() {
			return right;
		}

		/**
		 * 返回left。
		 * 
		 * @return left。
		 */
		public Number getLeft() {
			return left;
		}

		/**
		 * 设置left。
		 * 
		 * @param left Number
		 * 
		 * @return Bar
		 */
		public Bar setLeft(Number left) {
			this.left = left;
			return this;
		}
	}

}
