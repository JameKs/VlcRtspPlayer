/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.aopdebuglog;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class LogMethodTimeAdvice implements MethodInterceptor {

	private static final Log log = LogFactory.getLog(LogMethodTimeAdvice.class);

	/**
	 * 默认的构造方法。
	 */
	public LogMethodTimeAdvice() {
	}

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor# invoke(MethodInvocation invocation) throws Throwable
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		StopWatch clock = new StopWatch();
		clock.start();
		Object result = invocation.proceed();
		clock.stop();
		Class params[] = invocation.getMethod().getParameterTypes();
		String simpleParams[] = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			simpleParams[i] = params[i].getName();
		}
		log.info((new StringBuffer())
				.append("日志拦截  执行方法 [")
				.append(invocation.getThis().getClass().getName())
				.append(".")
				.append(invocation.getMethod().getName())
				.append("]成功， 耗时为：")
				.append(clock.getTime())
				.append("")
				.append("ms").toString());
		return result;
	}

}