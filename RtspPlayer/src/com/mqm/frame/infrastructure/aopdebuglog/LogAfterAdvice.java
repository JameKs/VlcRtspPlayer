/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.aopdebuglog;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

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
public class LogAfterAdvice implements AfterReturningAdvice, Serializable {

	private static final long serialVersionUID = 0x2fe67a367ccb0583L;
	private static final Log log = LogFactory.getLog(LogAfterAdvice.class);

	/**
	 * 默认的构造方法。
	 */
	public LogAfterAdvice() {
	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object args[], Object target) {
		log.info((new StringBuffer())
				.append("日志拦截  正常退出 [")
				.append(target.getClass().getName())
				.append(".")
				.append(method.getName())
				.append("] 返回值为 ")
				.append(returnValue == null ? "无返回值" : returnValue.toString()).toString());
	}

}