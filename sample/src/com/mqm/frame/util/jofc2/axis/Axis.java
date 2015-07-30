/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.axis;

import java.io.Serializable;

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
public abstract class Axis implements Serializable {

	private static final long serialVersionUID = 4823643361437691998L;

	private Integer stroke;
	private String colour;
	@Alias("grid-colour")
	private String grid_colour;
	private Double steps;
	private Integer offset;
	@Alias("3d")
	private Integer threed;
	private Double min;
	private Double max;

	/**
	 * 返回stroke。
	 * 
	 * @return Integer
	 */
	public Integer getStroke() {
		return stroke;
	}

	/**
	 * 设置stroke。
	 * 
	 * @param stroke Integer
	 * 
	 * @return Axis
	 */
	public Axis setStroke(Integer stroke) {
		this.stroke = stroke;
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
	 * @return Axis
	 */
	public Axis setColour(String colour) {
		this.colour = colour;
		return this;
	}

	/**
	 * 返回grid_colour。
	 * 
	 * @return String
	 */
	public String getGridColour() {
		return grid_colour;
	}

	/**
	 * 设置grid_colour。
	 * 
	 * @param grid_colour
	 *            String
	 * 
	 * @return Axis
	 */
	public Axis setGridColour(String grid_colour) {
		this.grid_colour = grid_colour;
		return this;
	}

	/**
	 * 返回steps。
	 * 
	 * @return Double
	 */
	public Double getSteps() {
		return steps;
	}

	/**
	 * 设置steps。
	 * 
	 * @param steps Double
	 * 
	 * @return Axis
	 */
	public Axis setSteps(Double steps) {
		this.steps = steps;
		return this;
	}

	/**
	 * 设置steps。
	 * 
	 * @param steps Integer
	 * 
	 * @return Axis
	 */
	public Axis setSteps(Integer steps) {
		this.steps = steps.doubleValue();
		return this;
	}

	/**
	 * 返回offset。
	 * 
	 * @return Integer
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * 设置offset。
	 * 
	 * @param offset Boolean
	 * 
	 * @return Axis
	 */
	public Axis setOffset(Boolean offset) {
		if (offset == null) {
			this.offset = null;
		} else {
			this.offset = offset ? 1 : 0;
		}
		return this;
	}

	/**
	 * 返回threed。
	 * 
	 * @return Integer
	 */
	public Integer get3D() {
		return threed;
	}

	/**
	 * 设置threed。
	 * 
	 * @param threed Integer
	 * 
	 * @return Axis
	 */
	public Axis set3D(Integer threed) {
		this.threed = threed;
		return this;
	}

	/**
	 * 返回min。
	 * 
	 * @return Double
	 */
	public Double getMin() {
		return min;
	}

	/**
	 * 设置min。
	 * 
	 * @param min Double
	 * 
	 * @return Axis
	 */
	public Axis setMin(Double min) {
		this.min = min;
		return this;
	}

	/**
	 * 设置min。
	 * 
	 * @param min Integer
	 * @return Axis
	 */
	public Axis setMin(Integer min) {
		this.min = min.doubleValue();
		return this;
	}

	/**
	 * 返回max。
	 * 
	 * @return Double
	 */
	public Double getMax() {
		return max;
	}

	/**
	 * 设置max。
	 * 
	 * @param max Double
	 * 
	 * @return Axis
	 */
	public Axis setMax(Double max) {
		this.max = max;
		return this;
	}

	/**
	 * 设置max。
	 * 
	 * @param max Integer
	 * @return Axis
	 */
	public Axis setMax(Integer max) {
		this.max = max.doubleValue();
		return this;
	}

	/**
	 * 设置Range。
	 * 
	 * @param min Number
	 * @param max Number
	 * @param step Number
	 * @return Axis
	 */
	public Axis setRange(Number min, Number max, Number step) {
		setMin(min.doubleValue());
		setMax(max.doubleValue());
		setSteps(step.doubleValue());
		return this;
	}

	/**
	 * 设置Range
	 * 。
	 * @param min Number
	 * @param max Number
	 * 
	 * @return Axis
	 */
	public Axis setRange(Number min, Number max) {
		return setRange(min, max, getSteps());
	}

}
