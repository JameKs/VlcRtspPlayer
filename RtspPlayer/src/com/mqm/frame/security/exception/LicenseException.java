/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <pre>
 * License过期异常。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class LicenseException extends AuthenticationException{

	private static final long serialVersionUID = -4479566921290099338L;

	/**
	 * 构建LicenseException。
	 * 
	 * @param msg String
	 */
	public LicenseException(String msg) {
		super(msg);
	}

}
