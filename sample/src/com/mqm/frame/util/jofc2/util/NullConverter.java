/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;



import com.mqm.frame.util.jofc2.model.elements.NullElement;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.json.JsonWriter;
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
public class NullConverter extends ConverterBase<NullElement> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class type) {
		return NullElement.class.isAssignableFrom(type);
	}

	@Override
	public void convert(NullElement o, PathTrackingWriter writer, MarshallingContext mc) {
		((JsonWriter) writer.underlyingWriter()).setValue(null);
	}
	
}
