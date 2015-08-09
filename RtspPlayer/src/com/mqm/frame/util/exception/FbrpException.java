/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.exception;

/**
 * 
 * <pre>
 * 服务层考虑抛出的异常类型，以触发事务的回滚操作。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpException extends RuntimeException {

	private static final long serialVersionUID = 7092610902670422579L;

	/**
	 * 默认的构造方法。
	 */
	public FbrpException() {
	}

	/**
	 * 自定义的构造方法1。
	 * 
	 * @param cause Throwable
	 */
	public FbrpException(Throwable cause) {
		super(cause);
	}

	/**
	 * 自定义的构造方法2。
	 * 
	 * @param message String
	 */
	public FbrpException(String message){
		super(message);
	}
	/**
	 * 自定义的构造方法3。
	 * 
	 * @param message String
	 * @param cause Throwable
	 */
	public FbrpException(String message, Throwable cause){
		super(message, cause);
	}

}