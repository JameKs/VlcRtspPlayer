/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <pre>
 * 处理认证适配器异常。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class AuthenticationAdapterAuthenticationException extends
		AuthenticationException {

	private static final long serialVersionUID = -3859141823234454446L;

	/**
	 * 构建器。
	 * 
	 * @param msg String
	 */
	public AuthenticationAdapterAuthenticationException(String msg) {
		super(msg);
	}
	
}
