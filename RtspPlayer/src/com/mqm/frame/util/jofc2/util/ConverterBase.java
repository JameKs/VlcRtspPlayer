/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
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
 * @param <T>
 */
public abstract class ConverterBase<T> implements Converter {

	@Override
	public final Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void marshal(Object o, HierarchicalStreamWriter hsw, MarshallingContext mc) {
		convert((T) o, (PathTrackingWriter) hsw, mc);
	}

	/**
	 * 构建节点。
	 * 
	 * @param writer PathTrackingWriter
	 * @param name String
	 * @param o Object
	 * @param ignoreNull boolean
	 */
	public final void writeNode(PathTrackingWriter writer, String name, Object o, boolean ignoreNull) {
		if (ignoreNull && o == null) {
			return;
		}
		//Tell xstream to treat null values as integers so that quotes are omitted.
		writer.startNode(name, o == null ? Integer.class : o.getClass());
		writer.setValue(o == null ? "null" : o.toString());
		writer.endNode();
	}

	/**
	 * 转换。
	 * 
	 * @param o T
	 * @param writer PathTrackingWriter
	 * @param mc MarshallingContext
	 */
	public abstract void convert(T o, PathTrackingWriter writer, MarshallingContext mc);

}
