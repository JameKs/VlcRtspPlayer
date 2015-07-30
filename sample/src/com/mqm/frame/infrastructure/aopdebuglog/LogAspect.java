/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.aopdebuglog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.Ordered;

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
public class LogAspect implements Ordered {

	private static final Log log = LogFactory.getLog(LogAspect.class);
	private int order;

	/**
	 * 默认的构造方法。
	 */
	public LogAspect() {
		order = 1;
	}

	/**
	 * 获取Order。
	 * 
	 * @return int
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * 设置Order。
	 * 
	 * @param order int
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * 记录异常信息。
	 * 
	 * @param jp JoinPoint
	 * @param ex  Throwable
	 */
	public void exceptionLog(JoinPoint jp, Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ex.getStackTrace().length; i++) {
			StackTraceElement ste = ex.getStackTrace()[i];
			sb.append((new StringBuilder()).append(ste.toString()).append("\n")
					.toString());
		}
		log.info((new StringBuffer())
				.append("日志拦截  非正常退出 [")
				.append(jp.getTarget().getClass().getName())
				.append(".")
				.append(jp.getSignature().getName())
				.append("] 抛出的异常为：")
				.append(ex.toString())
				.append(" \r\n 异常堆栈信息如下：\\:\r\n")
				.append(sb).toString());
		log.error((new StringBuilder())
				.append("日志拦截: 非正常退出 [")
				.append(jp.getTarget().getClass().getName())
				.append(".")
				.append(jp.getSignature().getName())
				.append("] 抛出的异常为：")
				.append(ex)
				.append(" \n 异常堆栈信息如下：\n")
				.append(sb.toString()).toString());
	}

	/**
	 * 记录日志之前。
	 * 
	 * @param jp  ProceedingJoinPoint
	 * 
	 * @throws Throwable 异常
	 */
	public void beforeLog(ProceedingJoinPoint jp) throws Throwable {
		Object params[] = jp.getArgs();
		StringBuffer sb = new StringBuffer("");
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					sb.append(params[i].toString());
					sb.append(",");
				}
			}

		} else {
			sb.append("无输入参数");
		}
		log.info((new StringBuffer())
				.append("日志拦截  开始进入 [")
				.append(jp.getTarget().getClass().getName())
				.append(".")
				.append(jp.getSignature().getName())
				.append("] 参数为 ")
				.append(sb).toString());
	}

	/**
	 * 记录日志之后。
	 * 
	 * @param jp ProceedingJoinPoint
	 * 
	 * @return Object
	 * 
	 * @throws Throwable 异常
	 */
	public Object afterLog(ProceedingJoinPoint jp) throws Throwable {
		Object rtvar = jp.proceed();
		log.info(new StringBuffer()
				.append("日志拦截  正常退出 [")
				.append(jp.getTarget().getClass().getName())
				.append(".")
				.append(jp.getSignature().getName())
				.append("] 返回值为\r\n ")
				.append(rtvar == null ? "null" : rtvar.toString()).toString());
		return rtvar;
	}

	/**
	 * 记录日志。
	 * 
	 * @param jp ProceedingJoinPoint
	 * @return Object
	 * @throws Throwable 异常
	 */
	public Object aroundLog(ProceedingJoinPoint jp) throws Throwable {
		long procTime = System.currentTimeMillis();
		Object params[] = jp.getArgs();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < params.length; i++) {
			sb.append(params[i].toString());
			sb.append(",");
		}
		log.info((new StringBuffer())
				.append("日志拦截  开始进入 [")
				.append(jp.getTarget().getClass().getName())
				.append(".")
				.append(jp.getSignature().getName())
				.append("] 参数为 ")
				.append(sb).toString());
		Object obj;
		try {
			Object result = jp.proceed();
			obj = result;
		} finally {
			procTime = System.currentTimeMillis() - procTime;
			log.info((new StringBuffer())
					.append("日志拦截  正常退出 [")
					.append(jp.getTarget().getClass().getName())
					.append(".")
					.append(jp.getSignature().getName())
					.append("] 返回值为 \r\n ")
					.append(jp.proceed().toString()).toString());
			log.info((new StringBuffer())
					.append("日志拦截  正常退出 [")
					.append(jp.getTarget().getClass().getName())
					.append(".")
					.append(jp.getSignature().getName())
					.append("] 返回值为 \r\n ")
					.append(String.valueOf(procTime)).toString());
		}
		return obj;
	}

}