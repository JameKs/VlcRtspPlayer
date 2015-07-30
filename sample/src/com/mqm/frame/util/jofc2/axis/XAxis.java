/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.axis;

import java.util.List;

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
public class XAxis extends Axis {

	private static final long serialVersionUID = -7007897621631089309L;
	
	@Alias("tick-height") 
	private Integer tick_height;
    
	private XAxisLabels labels = new XAxisLabels();
    
	/**
	 * 设置tick_height。
	 * 
	 * @param tick_height Integer
	 * 
	 * @return XAxis
	 */
    public XAxis setTickHeight(Integer tick_height) {
        this.tick_height = tick_height;
        return this;
    }
    
    /**
     * 返回Integer。
     * 
     * @return Integer
     */
    public Integer getTickHeight() {
        return tick_height;
    }
    
    /**
     * 返回labels。
     * 
     * @return  XAxisLabels
     */
    public XAxisLabels getLabels() {
        return labels;
    }
    
    /**
     * 设置labels。
     * 
     * @param labels XAxisLabels
     * 
     * @return XAxis
     */
    public XAxis setXAxisLabels(XAxisLabels labels) {
        this.labels = labels;
        return this;
    }
    
    /**
     * 设置labels。
     * 
     * @param labels String...
     * 
     * @return XAxis
     */
    public XAxis setLabels(String... labels) {
        this.labels = new XAxisLabels(labels);
        return this;
    }

    /**
     * 设置labels。
     * 
     * @param labels List
     * 
     * @return XAxis
     */
    public XAxis setLabels(List<String> labels) {
        this.labels = new XAxisLabels(labels);
        return this;
    }
    
    /**
     * 维护labels。
     * 
     * @param labels String...
     * 
     * @return XAxis
     */
    public XAxis addLabels(String... labels) {
        this.labels.addLabels(labels);
        return this;
    }
    
    /**
     * 维护labels。
     * 
     * @param labels Label...
     * 
     * @return XAxis
     */
    public XAxis addLabels(Label... labels) {
        this.labels.addLabels(labels);
        return this;
    }
    
    /**
     * 维护labels。
     * 
     * @param labels List
     * @return XAxis
     */
    public XAxis addLabels(List<Label> labels) {
        this.labels.addLabels(labels);
        return this;
    }

}
