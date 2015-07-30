/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import java.util.LinkedHashMap;

/**
 * <pre>
 * 自定义LinkedHashMap子类。
 * </pre>
 * 
 * @param <K> ValueObject
 * @param <V> ValueObject
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class CodeMapUtil<K,V> extends LinkedHashMap<K, V>{
	
	private static final long serialVersionUID = -6633457078372593613L;

	@Override
	public V get(Object key) {
		String k = key + "";
		return super.get(k);
	}

}
