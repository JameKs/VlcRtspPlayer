/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.axis;

import java.io.Serializable;

import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.RotationConverter;

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
public class Label implements Serializable {

	private static final long serialVersionUID = -6976582830606939527L;

	private String text;
	private String colour;
	private Integer size;
	private Rotation rotate;
	private Boolean visible;

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
	@Converter(RotationConverter.class)
	public static enum Rotation {
		
		/** 风格。 */
		VERTICAL(-90), 
		/** 风格。 */
		HALF_DIAGONAL(-24), 
		/** 风格。 */
		DIAGONAL(-45), 
		/** 风格。 */
		HORIZONTAL(0);

		private final int degrees;

		Rotation(int degrees) {
			this.degrees = degrees;
		}
		
		@Override
		public String toString() {
			return String.valueOf(degrees);
		}
	}

	/**
	 * 构建器。
	 */
	public Label() {
		this(null);
	}

	/**
	 * 构建器。
	 * 
	 * @param text String
	 */
	public Label(String text) {
		setText(text);
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
	 * 设置text。
	 * 
	 * @param text String
	 * 
	 * @return Label
	 */
	public Label setText(String text) {
		this.text = text;
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
	 * @return Label
	 */
	public Label setColour(String colour) {
		this.colour = colour;
		return this;
	}

	/**
	 * 返回size。
	 * 
	 * @return Integer
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * 设置size。
	 * 
	 * @param size Integer
	 * @return Label
	 */
	public Label setSize(Integer size) {
		this.size = size;
		return this;
	}

	/**
	 * 返回rotate。
	 * 
	 * @return rotate
	 */
	public Rotation getRotation() {
		return rotate;
	}

	/**
	 * 设置rotate。
	 * 
	 * @param rotate Rotation
	 * @return Label
	 */
	public Label setRotation(Rotation rotate) {
		this.rotate = rotate;
		return this;
	}

	/**
	 * 返回visible。
	 * 
	 * @return Boolean
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * 设置visible。
	 * 
	 * @param visible Boolean
	 * 
	 * @return Label
	 */
	public Label setVisible(Boolean visible) {
		this.visible = visible;
		return this;
	}

}
