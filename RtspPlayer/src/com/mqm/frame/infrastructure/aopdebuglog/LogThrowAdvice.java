/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.aopdebuglog;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;

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
public class LogThrowAdvice implements ThrowsAdvice, Serializable {

	private static final long serialVersionUID = 0x7e80aa71789d1c3eL;
	
	private static final Log log = LogFactory.getLog(LogThrowAdvice.class);

	/**
	 * 默认的构造方法。
	 */
	public LogThrowAdvice() {
	}

	/**
	 * 抛出异常后的处理。
	 *  
	 * @param method Method
	 * @param args Object
	 * @param target Object
	 * @param subclass Throwable
	 */
	public void afterThrowing(Method method, Object args[], Object target,
			Throwable subclass) {
		StringBuffer sb = new StringBuffer("日志拦截  非正常退出 [");
		sb.append(target.getClass().getName()).append(".").append(method.getName());
		sb.append("] 抛出的异常为：").append(subclass.toString());
		sb.append(" 异常堆栈信息如下：").append(subclass.getMessage());
	}

}