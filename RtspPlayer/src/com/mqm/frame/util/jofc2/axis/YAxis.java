/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.axis;

import java.util.List;

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
public class YAxis extends Axis {

	private static final long serialVersionUID = 7471159737831995334L;
	
	private Integer tick_length;
	private YAxisLabels labels;

	/**
	 * 设置tick_length。
	 * 
	 * @param tick_length Integer
	 * 
	 * @return YAxis
	 */
	public YAxis setTickLength(Integer tick_length) {
		this.tick_length = tick_length;
		return this;
	}

	/**
	 * 返回tick_length。
	 * 
	 * @return Integer
	 */
	public Integer getTickLength() {
		return tick_length;
	}

	/**
	 * 设置labels。
	 * 
	 * @param labels String...
	 * 
	 * @return YAxis
	 */
	public YAxis setLabels(String... labels) {
		this.labels = new YAxisLabels(labels);
		return this;
	}

	/**
	 * 设置labels。
	 * 
	 * @param labels List<String>
	 * 
	 * @return YAxis
	 */
	public YAxis setLabels(List<String> labels) {
		this.labels = new YAxisLabels(labels);
		return this;
	}

	/**
	 * 维护labels。
	 * 
	 * @param labels String...
	 * 
	 * @return YAxis
	 */
	public YAxis addLabels(String... labels) {
		if (this.labels == null) {
			this.labels = new YAxisLabels();
		}
		this.labels.addLabels(labels);
		return this;
	}

	/**
	 * 维护labels。
	 * 
	 * @param labels List<Label>
	 * 
	 * @return YAxis
	 */
	public YAxis addLabels(List<Label> labels) {
		if (this.labels == null) {
			this.labels = new YAxisLabels();
		}
		this.labels.addLabels(labels);
		return this;
	}

	/**
	 * 返回labels。
	 * 
	 * @return YAxisLabels
	 */
	public YAxisLabels getLabels() {
		if (this.labels == null) {
			this.labels = new YAxisLabels();
		}
		return this.labels;
	}
	
}
