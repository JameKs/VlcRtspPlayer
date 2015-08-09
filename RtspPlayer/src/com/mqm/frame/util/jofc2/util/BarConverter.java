/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;



import com.mqm.frame.util.jofc2.model.elements.BarChart;
import com.mqm.frame.util.jofc2.model.elements.FilledBarChart;
import com.mqm.frame.util.jofc2.model.elements.SketchBarChart;
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
public class BarConverter extends ConverterBase<BarChart.Bar> {

	@Override
	public void convert(BarChart.Bar b, PathTrackingWriter writer, MarshallingContext mc) {
		writeNode(writer, "top", b.getTop(), false);
		writeNode(writer, "bottom", b.getBottom(), true);
		writeNode(writer, "colour", b.getColour(), true);
		writeNode(writer, "tip", b.getTooltip(), true);
		if (b instanceof FilledBarChart.Bar) {
			writeNode(writer, "outline-colour", ((FilledBarChart.Bar) b).getOutlineColour(), true);
		}
		if (b instanceof SketchBarChart.Bar) {
			writeNode(writer, "offset", ((SketchBarChart.Bar) b).getFunFactor(), true);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class clazz) {
		return BarChart.Bar.class.isAssignableFrom(clazz);
	}
	
}
