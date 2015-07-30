/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.aopdebuglog;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

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
public class LogBeforeAdvice implements MethodBeforeAdvice, Serializable {

	private static final long serialVersionUID = 0x6dfc657857cdb010L;
	private static final Log log = LogFactory.getLog(LogBeforeAdvice.class);

	/**
	 * 默认的构造方法。
	 */
	public LogBeforeAdvice() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.aop.MethodBeforeAdvice# before(Method method, Object args[], Object target) 
	 */
	@Override
	public void before(Method method, Object args[], Object target) {
		StringBuffer sb = new StringBuffer("");
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					sb.append(args[i].toString());
					sb.append(",");
				}
			}

		} else {
			sb.append("无输入参数");
		}
		log.info((new StringBuffer())
				.append("日志拦截  开始进入 [")
				.append(target.getClass().getName())
				.append(".")
				.append(method.getName())
				.append("] 参数为 ")
				.append(sb).toString()); 
	}

}