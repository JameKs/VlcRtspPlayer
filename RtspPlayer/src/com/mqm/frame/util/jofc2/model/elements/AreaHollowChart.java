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
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class AreaHollowChart extends LineChart {

	private static final long serialVersionUID = 6023248458467056064L;

	private static transient final Float DEFAULT_ALPHA = 0.35f;
    
    @Alias("fill-alpha") private Float fillAlpha;
    @Alias("fill") private String fillColor;
    
    /**
     * 默认的构造方法。
     */
    public AreaHollowChart() {
        super("area_hollow");
        setFillAlpha(DEFAULT_ALPHA);
    }
    
    /**
     * 获取fillAlpha。
     * 
     * @return Float
     */
    public Float getFillAlpha() {
        return fillAlpha;
    }

    /**
     * 设置fillAlpha。
     * 
     * @param fillAlpha Float
     * 
     * @return AreaHollowChart
     */
    public AreaHollowChart setFillAlpha(Float fillAlpha) {
        this.fillAlpha = fillAlpha;
        return this;
    }

    /**
     * 获取fillColor。
     * 
     * @return String
     */
    public String getFillColor()
    {
        return fillColor;
    }

    /**
     * 设置fillColor。
     * 
     * @param fillColor String
     * 
     * @return AreaHollowChart
     */
    public AreaHollowChart setFillColor(String fillColor)
    {
        this.fillColor = fillColor;
        return this;
    }
}