/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.mqm.frame.infrastructure.FbrpConfigInfo;

/**
 * 
 * <pre>
 * 用于对FbrpConfigInfo提供配置值。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ConfigItem {

	/**
	 * 构建器。
	 * 
	 * @param config
	 *            FbrpConfigInfo
	 * @param key
	 *            String
	 * @param value
	 *            String
	 */
	public ConfigItem(FbrpConfigInfo config, String key, String value) {
		config.add(key, value);
	}

	/**
	 * 构建器。
	 * 
	 * @param config
	 *            FbrpConfigInfo
	 * @param key
	 *            String
	 * @param value
	 *            String
	 * @param defaultValue
	 *            String
	 */
	public ConfigItem(FbrpConfigInfo config, String key, String value,
			String defaultValue) {
		String tValue = StringUtils.hasText(value) ? value : defaultValue;
		config.add(key, tValue);
	}

	/**
	 * 构建器。
	 * 
	 * @param config
	 *            FbrpConfigInfo
	 * @param map
	 *            Map
	 */
	public ConfigItem(FbrpConfigInfo config, Map<String, String> map) {
		config.add(map);
	}

}
