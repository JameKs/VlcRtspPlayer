/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luxiaocheng  luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class VOUtils {
	
	/**
	 * 将实体集合以ID为key存放于排序的Map集中。
	 * 
	 * @param collections Collection<T>
	 * @param <T> ValueObject
	 * 
	 * @return Map
	 */
	public static <T extends ValueObject> Map<String,T> convert2Map(Collection<T> collections){
		Map<String,T> map = new LinkedHashMap<String, T>();
		for (T vo : collections) {
			map.put(vo.getId(), vo);
		}
		return map;
	}
	
	/**
	 * 将实体集合以ID为key存放于List集中。
	 * 
	 * @param collections Collections
	 * @param <T> ValueObject
	 * 
	 * @return List
	 */
	public static <T extends ValueObject> List<String> extractIds(Collection<T> collections){
		List<String> ids = new ArrayList<String>();
		for (T vo : collections) {
			ids.add(vo.getId());
		}
		return ids;
	}
	
}
