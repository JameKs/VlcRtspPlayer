/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;



import com.mqm.frame.util.jofc2.model.elements.StackedBarChart.StackValue;
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
public class StackValueConverter extends ConverterBase<StackValue> {

	@Override
	public void convert(StackValue o, PathTrackingWriter writer, MarshallingContext mc) {
		writeNode(writer, "val", o.getValue(), false);
		writeNode(writer, "colour", o.getColour(), true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class clazz) {
		return StackValue.class.isAssignableFrom(clazz);
	}
	
}
