/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.model.elements;

import com.mqm.frame.util.jofc2.model.metadata.Converter;
import com.mqm.frame.util.jofc2.util.NullConverter;

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
@Converter(NullConverter.class)
public class NullElement {

	@Override
	public String toString() {
		return "";
	}
}
