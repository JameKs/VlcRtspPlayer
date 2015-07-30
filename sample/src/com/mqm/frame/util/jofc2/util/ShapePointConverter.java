/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;

import com.mqm.frame.util.jofc2.model.elements.ShapeChart;
import com.mqm.frame.util.jofc2.model.elements.ShapeChart.Point;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

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
public class ShapePointConverter extends ConverterBase<Point> {

	@Override
	public void convert(ShapeChart.Point o, PathTrackingWriter writer,
			MarshallingContext mc) {
		writeNode(writer, "x", o.getX(), false);
		writeNode(writer, "y", o.getY(), false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class arg0) {
		return Point.class.isAssignableFrom(arg0);
	}

}
