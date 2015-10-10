/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

/**
 * <pre>
 * 用于BeanUtils日期赋值。
 * </pre>
 * @author lijiawei  lijiawei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DateConverter implements Converter {
	
	private String pattern;
	
	public DateConverter() {
	}
	
	public DateConverter(String pattern) {
		this.pattern = pattern;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	@Override
	public Object convert(Class clz, Object value) {
		if(value instanceof String) {
			String val_str = (String) value;
			if(null == val_str || val_str.trim().length() == 0) {
				return null;
			}
			if(null == pattern || pattern.trim().length() == 0) {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				return sdf.parse(val_str.trim());
			} catch (ParseException e) {
				return null;
			}
		} else if(value instanceof Date) {
			return value;
		} else {
			return null;
		}
	}

}
