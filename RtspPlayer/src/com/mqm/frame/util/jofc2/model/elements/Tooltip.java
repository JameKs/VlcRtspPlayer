/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;


import java.io.Serializable;

import com.mqm.frame.util.jofc2.model.metadata.Alias;
import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.TooltipTypeConverter;

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
public class Tooltip implements Serializable
{
	private static final long serialVersionUID = -7980542927928240127L;

	@Alias(value = "mouse")
    private Type type = null;
    private Boolean shadow;
    private Integer stroke;
    private String colour;
    @Alias(value = "background")
    private String backgroundColour;
    @Alias(value = "title")
    private String titleStyle;
    @Alias(value = "body")
    private String bodyStyle;

    /**
     * 构建器。
     */
    public Tooltip() { }

    /**
     * 返回type。
     * 
     * @return Type
     */
    public Type getType()
    {
        return type;
    }

    /**
     * 设置type。
     * 
     * @return Tooltip
     */
    public Tooltip setHover()
    {
        this.type = Type.HOVER;
        return this;
    }

    /**
     * 设置type。
     * 
     * @return Tooltip
     */
    public Tooltip setProximity()
    {
        this.type = Type.PROXIMITY;
        return this;
    }

    /**
     * 返回shadow。
     * 
     * @return Boolean
     */
    public Boolean getShadow()
    {
        return shadow;
    }

    /**
     * 设置shadow。
     * 
     * @param shadow Boolean
     * @return Tooltip
     */
    public Tooltip setShadow(Boolean shadow)
    {
        this.shadow = shadow;
        return this;
    }

    /**
     * 返回stroke。
     * 
     * @return Integer
     */
    public Integer getStroke()
    {
        return stroke;
    }

    /**
     * 设置stroke。
     * 
     * @param stroke Integer
     * @return Tooltip
     */
    public Tooltip setStroke(Integer stroke)
    {
        this.stroke = stroke;
        return this;
    }

    /**
     * 返回colour。
     * 
     * @return String
     */
    public String getColour()
    {
        return colour;
    }

    /**
     * 设置colour。
     * 
     * @param colour String
     * @return Tooltip
     */
    public Tooltip setColour(String colour)
    {
        this.colour = colour;
        return this;
    }

    /**
     * 返回backgroundColour。
     * 
     * @return String
     */
    public String getBackgroundColour()
    {
        return backgroundColour;
    }

    /**
     * 设置backgroundColour。
     * 
     * @param backgroundColour String
     * 
     * @return Tooltip
     */
    public Tooltip setBackgroundColour(String backgroundColour)
    {
        this.backgroundColour = backgroundColour;
        return this;
    }

    /**
     * 返回titleStyle。
     * 
     * @return String
     */
    public String getTitleStyle()
    {
        return titleStyle;
    }

    /**
     * 设置titleStyle。
     * 
     * @param titleStyle String
     * @return Tooltip
     */
    public Tooltip setTitleStyle(String titleStyle)
    {
        this.titleStyle = titleStyle;
        return this;
    }

    /**
     * 返回bodyStyle。
     * 
     * @return String
     */
    public String getBodyStyle()
    {
        return bodyStyle;
    }

    /**
     * 设置bodyStyle。
     * 
     * @param bodyStyle String
     * @return Tooltip
     */
    public Tooltip setBodyStyle(String bodyStyle)
    {
        this.bodyStyle = bodyStyle;
        return this;
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
    @Converter(TooltipTypeConverter.class)
    public enum Type
    {
    	/** 风格。 */
        PROXIMITY(1),
    	/** 风格。 */
        HOVER(2);

        private final int value;
        private Type(Integer value)
        {
            this.value = value;
        }

        /**
         * 返回value。
         * 
         * @return int
         */
        public int getValue()
        {
            return value;
        }

        /**
         * 返回字符串描述。
         * 
         * @return String
         */
        public String toString()
        {
            return String.valueOf(value);
        }
    }
}
