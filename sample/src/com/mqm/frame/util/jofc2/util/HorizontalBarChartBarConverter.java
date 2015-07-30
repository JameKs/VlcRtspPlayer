/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;



import com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart;
import com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart.Bar;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

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
public class HorizontalBarChartBarConverter extends ConverterBase<Bar> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class c) {
		return HorizontalBarChart.Bar.class.isAssignableFrom(c);
	}

	@Override
	public void convert(Bar o, PathTrackingWriter writer, MarshallingContext mc) {
		writeNode(writer, "right", o.getRight(), false);
		writeNode(writer, "left", o.getLeft(), false);
	}
	
}
