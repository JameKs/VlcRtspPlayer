/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.util.HashMap;
import java.util.Map;

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
public class MapUtil {

	/**
	 * 默认的构造方法。
	 */
	public MapUtil() {
	}

	/**
	 * 获取Map。
	 * 
	 * @return Map
	 */
	public static Map makeMap() {
		return new HashMap();
	}

	/**
	 * 根据k1,v1生成一个Map。
	 * 
	 * @param k1  key
	 * @param o1  value
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1) {
		Map result = new HashMap();
		result.put(k1, o1);
		return result;
	}

	/**
	 * 根据k1,v1,k2,v2生成一个Map。
	 * 
	 * @param k1 key1
	 * @param o1 value1
	 * @param k2 key2
	 * @param o2 value2
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1, Object k2, Object o2) {
		Map result = new HashMap();
		result.put(k1, o1);
		result.put(k2, o2);
		return result;
	}

	/**
	 * 根据k1,v1,k2,v2,k3,v3生成一个Map。
	 * 
	 * @param k1 key1
	 * @param o1 value1
	 * @param k2 key2
	 * @param o2 value2
     * @param k3 key3
	 * @param o3 value3
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1, Object k2, Object o2,
			Object k3, Object o3) {
		Map result = new HashMap();
		result.put(k1, o1);
		result.put(k2, o2);
		result.put(k3, o3);
		return result;
	}

	/**
	 * 根据k1,v1,k2,v2,k3,v3,k4,v4生成一个Map。
	 * 
	 * @param k1 key1
	 * @param o1 value1
	 * @param k2 key2
	 * @param o2 value2
     * @param k3 key3
	 * @param o3 value3
	 * @param k4 key4
	 * @param o4 value4
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1, Object k2, Object o2,
			Object k3, Object o3, Object k4, Object o4) {
		Map result = new HashMap();
		result.put(k1, o1);
		result.put(k2, o2);
		result.put(k3, o3);
		result.put(k4, o4);
		return result;
	}

	/**
	 * 根据k1,v1,k2,v2,k3,v3,k4,v4,k5,v5生成一个Map。
	 * 
	 * @param k1 key1
	 * @param o1 value1
	 * @param k2 key2
	 * @param o2 value2
     * @param k3 key3
	 * @param o3 value3
	 * @param k4 key4
	 * @param o4 value4
	 * @param k5 key5
	 * @param o5 value5
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1, Object k2, Object o2,
			Object k3, Object o3, Object k4, Object o4, Object k5, Object o5) {
		Map result = new HashMap();
		result.put(k1, o1);
		result.put(k2, o2);
		result.put(k3, o3);
		result.put(k4, o4);
		result.put(k5, o5);
		return result;
	}

	/**
	 * 根据k1,v1,k2,v2,k3,v3,k4,v4,k5,v5,k6,v6生成一个Map。
	 * 
	 * @param k1 key1
	 * @param o1 value1
	 * @param k2 key2
	 * @param o2 value2
	 * @param k3 key3
	 * @param o3 value3
	 * @param k4 key4
	 * @param o4 value4
	 * @param k5 key5
	 * @param o5 value5
	 * @param k6 key6
	 * @param o6 value6
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1, Object k2, Object o2,
			Object k3, Object o3, Object k4, Object o4, Object k5, Object o5,
			Object k6, Object o6) {
		Map result = new HashMap();
		result.put(k1, o1);
		result.put(k2, o2);
		result.put(k3, o3);
		result.put(k4, o4);
		result.put(k5, o5);
		result.put(k6, o6);
		return result;
	}

	/**
	 * 根据k1,v1,k2,v2,k3,v3,k4,v4,k5,v5,k6,v6,k7,v7生成一个Map。
	 * 
	 * @param k1 key1
	 * @param o1 value1
	 * @param k2 key2
	 * @param o2 value2
	 * @param k3 key3
	 * @param o3 value3
	 * @param k4 key4
	 * @param o4 value4
	 * @param k5 key5
	 * @param o5 value5
	 * @param k6 key6
	 * @param o6 value6
	 * @param k7 key7
	 * @param o7 value7
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1, Object k2, Object o2,
			Object k3, Object o3, Object k4, Object o4, Object k5, Object o5,
			Object k6, Object o6, Object k7, Object o7) {
		Map result = new HashMap();
		result.put(k1, o1);
		result.put(k2, o2);
		result.put(k3, o3);
		result.put(k4, o4);
		result.put(k5, o5);
		result.put(k6, o6);
		result.put(k7, o7);
		return result;
	}


	/**
	 * 根据k1,v1,k2,v2,k3,v3,k4,v4,k5,v5,k6,v6,k7,v7,k8,v8生成一个Map。
	 * 
     * @param k1 key1
	 * @param o1 value1
	 * @param k2 key2
	 * @param o2 value2
	 * @param k3 key3
	 * @param o3 value3
	 * @param k4 key4
	 * @param o4 value4
	 * @param k5 key5
	 * @param o5 value5
	 * @param k6 key6
	 * @param o6 value6
	 * @param k7 key7
	 * @param o7 value7
	 * @param k8 key8
	 * @param o8 value8
	 * 
	 * @return Map
	 */
	public static Map makeMap(Object k1, Object o1, Object k2, Object o2,
			Object k3, Object o3, Object k4, Object o4, Object k5, Object o5,
			Object k6, Object o6, Object k7, Object o7, Object k8, Object o8) {
		Map result = new HashMap();
		result.put(k1, o1);
		result.put(k2, o2);
		result.put(k3, o3);
		result.put(k4, o4);
		result.put(k5, o5);
		result.put(k6, o6);
		result.put(k7, o7);
		result.put(k8, o8);
		return result;
	}
}