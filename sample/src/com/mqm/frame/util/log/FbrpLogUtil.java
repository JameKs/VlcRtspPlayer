/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 日志实用类。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FbrpLogUtil {

	private static final Log log = LogFactory.getLog(FbrpLogUtil.class);

	/**
	 * 获取log对象。
	 * 
	 * @return Log
	 */
	public static Log getLog(){
		return log;
	}
}
