/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqm.frame.util.jofc2.axis.Axis;
import com.mqm.frame.util.jofc2.axis.Label;
import com.mqm.frame.util.jofc2.axis.XAxis;
import com.mqm.frame.util.jofc2.axis.XAxisLabels;
import com.mqm.frame.util.jofc2.axis.YAxis;
import com.mqm.frame.util.jofc2.model.Chart;
import com.mqm.frame.util.jofc2.model.Text;
import com.mqm.frame.util.jofc2.model.elements.AreaHollowChart;
import com.mqm.frame.util.jofc2.model.elements.BarChart;
import com.mqm.frame.util.jofc2.model.elements.Element;
import com.mqm.frame.util.jofc2.model.elements.FilledBarChart;
import com.mqm.frame.util.jofc2.model.elements.HorizontalBarChart;
import com.mqm.frame.util.jofc2.model.elements.LineChart;
import com.mqm.frame.util.jofc2.model.elements.NullElement;
import com.mqm.frame.util.jofc2.model.elements.PieChart;
import com.mqm.frame.util.jofc2.model.elements.ScatterChart;
import com.mqm.frame.util.jofc2.model.elements.ShapeChart;
import com.mqm.frame.util.jofc2.model.elements.SketchBarChart;
import com.mqm.frame.util.jofc2.model.elements.StackedBarChart;
import com.mqm.frame.util.jofc2.model.elements.Tooltip;
import com.mqm.frame.util.jofc2.model.metadata.Alias;
import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.SingleValueConverter;

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
public class OFC {

	private static final Log log = LogFactory.getLog(OFC.class);
	
	private static final Class<?>[] models = new Class<?>[] {
			Axis.class,
			Text.class,
			XAxis.class,
			YAxis.class,
			XAxisLabels.class,
			Label.class,
			Element.class,
			Axis.class,
			BarChart.class,
			PieChart.class,
			HorizontalBarChart.class,
			LineChart.class,
			ScatterChart.class,
			AreaHollowChart.class,
			PieChart.Slice.class,
			HorizontalBarChart.Bar.class,
			Label.Rotation.class,
			ScatterChart.Point.class,
			FilledBarChart.class,
			SketchBarChart.class,
            StackedBarChart.class,
			StackedBarChart.StackValue.class,
			StackedBarChart.Stack.class,
            StackedBarChart.Key.class,
            BarChart.Bar.class,
			FilledBarChart.Bar.class,
			SketchBarChart.Bar.class,
			LineChart.Dot.class,
			NullElement.class,
			Chart.class,
            ShapeChart.class,
            ShapeChart.Point.class,
            Tooltip.class,
            Tooltip.Type.class
    };
	private final XStream converter = new XStream(new OFCJSONDriver());

	/**
	 * Sole constructor.
	 */
	public OFC() {
		for (Class<?> c : models) {
			doAlias(c);
			doRegisterConverter(c);
		}
	}

	/**
	 * 返回OFC。
	 * 
	 * @return OFC。
	 */
	public static OFC getInstance() {
		return LazyInstance.instance;
	}

	private void doAlias(Class<?> c) {
		if (c.isAnnotationPresent(Alias.class)) {
			converter.alias(c.getAnnotation(Alias.class).value(), c);
		}
		for (Field f : c.getDeclaredFields()) {
			if (f.isAnnotationPresent(Alias.class)) {
				converter.aliasField(f.getAnnotation(Alias.class).value(), c, f.getName());
			}
		}
	}

	private void doRegisterConverter(Class<?> c) {
		if (c.isAnnotationPresent(Converter.class)) {
			Class<? extends ConverterMatcher> clazz = c.getAnnotation(Converter.class).value();
			try {
				if (SingleValueConverter.class.isAssignableFrom(clazz)) {
					converter.registerConverter((SingleValueConverter) clazz.newInstance());
				} else {
					converter.registerConverter((com.thoughtworks.xstream.converters.Converter) clazz.newInstance());
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	/**
	 * Use this method in your applications to send data back to the chart
	 * widget.
	 * 
	 * @param c
	 *           the chart to render
	 * @throws OFCException
	 *            when rendering fails
	 * @return the JSONified chart data
	 */
	public String render(Chart c) throws OFCException {
		String json = converter.toXML(c);
		try {
			return new JSONObject(json).getString(Chart.class.getName());
		} catch (JSONException je) {
			throw new OFCException(json, je);
		}
	}

	/**
	 * Use this method for debugging purposes.
	 * 
	 * @param c
	 *           the chart to render
	 * @param indentationLevel
	 *           number of spaces to use for indentation
	 * @throws OFCException
	 *            when rendering fails
	 * @return pretty-printed JSONified chart data
	 */
	public String prettyPrint(Chart c, int indentationLevel) throws OFCException {
		String json = converter.toXML(c);
		try {
			return new JSONObject(json).getJSONObject(Chart.class.getName())
					.toString(indentationLevel);
		} catch (JSONException je) {
			throw new OFCException(json, je);
		}
	}

	/**
	 * Convenience method for converting Collections to Arrays. You can use this
	 * where the API has limited support for collections:
	 * getLabels().addLabels(OFC.toArray(stringList, String.class));
	 * 
	 * @param collection
	 *           The collection to use
	 * @param type
	 *           The supertype for the collection. This will commonly be Integer,
	 *           Number, etc.
	 * @param <T> ValueObject
	 * 
	 * @return the array of the collection
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> collection, Class<? extends T> type) {
		return collection.toArray((T[]) Array.newInstance(type, collection.size()));
	}

	/**
	 * Convenience method to generate labels from a collection of Objects, if so
	 * desired.
	 * 
	 * @param source
	 *           the source collection holding Objects.
	 * @return a collection of all the objects toString() method invoked
	 */
	public static List<String> stringify(List<? extends Object> source) {
		List<String> strings = new ArrayList<String>(source.size());
		for (Object o : source) {
			strings.add(o.toString());
		}
		return strings;
	}

	/**
	 * Convenience method to generate labels from an array of disparate Objects.
	 * 
	 * @param objects
	 *           the array of objects to convert
	 * @return a collection of all the objects toString() result
	 */
	public static String[] stringify(Object... objects) {
		return stringify(Arrays.asList(objects)).toArray(new String[objects.length]);
	}
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
class LazyInstance {
	protected static final OFC instance = new OFC();
}
