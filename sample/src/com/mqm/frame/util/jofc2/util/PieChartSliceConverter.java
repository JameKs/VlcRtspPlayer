/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;



import com.mqm.frame.util.jofc2.model.elements.PieChart;
import com.mqm.frame.util.jofc2.model.elements.PieChart.Slice;
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
public class PieChartSliceConverter extends ConverterBase<Slice> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class c) {
		return PieChart.Slice.class.isAssignableFrom(c);
	}

	@Override
	public void convert(Slice o, PathTrackingWriter writer, MarshallingContext mc) {
		writeNode(writer, "value", o.getValue(), false);
		writeNode(writer, "label", o.getLabel(), true);
		writeNode(writer, "tip", o.getTip(), true);
		writeNode(writer, "highlight", o.getHighlight(), false);
		writeNode(writer, "text", o.getText(), true);
	}
	
}
