/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2.util;



import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.jofc2.model.elements.Tooltip.Type;
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
public class TooltipTypeConverter implements SingleValueConverter
{
	/**
	 * 将字符串转换成对象。
	 * 
	 *   @param s String
	 *   
	 *   @return Object
	 */
    public Object fromString(String s) {
		return Type.valueOf(InternationalizationUtil.toUpperCase(s));
	}

    /**
     * 将对象转换成字符串。
     * 
     * @param o Object
     * 
     * @return String
     */
	public String toString(Object o) {
		return o.toString();
	}

	/**
	 * 判断目标类是否能够转换。
	 * 
	 * @param arg0 Class
	 * 
	 * @return boolean
	 */
    public boolean canConvert(Class arg0)
    {
        return Type.class.isAssignableFrom(arg0);
    }
    
}
