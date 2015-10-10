/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.service.impl;

import java.util.Date;

import com.mqm.frame.infrastructure.service.IServerTime;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 返回服务器当前时间。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ServerTimeImpl implements IServerTime {

	/**
	 * 返回服务器当前时间。
	 * 
	 * @return Date 返回服务器当前时间
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public Date getServerDateTime() throws FbrpException {
		return new Date();
	}

}