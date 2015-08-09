/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.io.Serializable;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CounterTest implements Serializable {

	private static final long serialVersionUID = 1L;
	private int confirmations;
	private int cancellations;

	/**
	 * 自增confirmations。
	 */
	public void incrementConfirmations() {
		confirmations++;
	}

	/**
	 * 自增cancellations。
	 */
	public void incrementCancellations() {
		cancellations++;
	}

	/**
	 * 获取confirmations。
	 * 
	 * @return int
	 */
	public int getConfirmations() {
		return confirmations;
	}

	/**
	 * 获取cancellations。
	 * 
	 * @return int
	 */
	public int getCancellations() {
		return cancellations;
	}

}