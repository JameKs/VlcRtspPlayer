/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.aopdebuglog;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>
 * Around装备。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class LogAroundAdvice implements MethodInterceptor, Serializable {

	private static final long serialVersionUID = 0x34c2077772f8f27L;
	
	private static final Log log = LogFactory.getLog(LogAroundAdvice.class);

	/**
	 * 默认的构造方法。
	 */
	public LogAroundAdvice() {
	}

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor# invoke(MethodInvocation invocation) throws Throwable
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long procTime = System.currentTimeMillis();
		Object args[] = invocation.getArguments();
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
				.append(invocation.getClass().getName())
				.append(".")
				.append(invocation.getMethod().getName())
				.append("] 参数为 ")
				.append(sb).toString());
		Object returnObject = null;
		Object obj;
		try {
			returnObject = (Serializable) invocation.proceed();
			obj = returnObject;
		} finally {
			procTime = System.currentTimeMillis() - procTime;
			
			log.info((new StringBuffer())
					.append("日志拦截  正常退出 [")
					.append(invocation.getClass().getName())
					.append(".")
					.append(invocation.getMethod().getName())
					.append("] 返回值为 ")
					.append(returnObject == null ? "无返回值" : returnObject.toString()).toString());
			log.info((new StringBuffer())
					.append("日志拦截  正常退出 [")
					.append(invocation.getClass().getName())
					.append(".")
					.append(invocation.getMethod().getName())
					.append("] 返回值为 ")
					.append(String.valueOf(procTime)).toString());
		}
		return obj;
	}

}