/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.service;

import java.util.Iterator;

/**
 * 
 * <pre>
 * 用来判断URL是否可以访问。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IUrlVoterExtender {

	/**
	 * 对URL进行扩展权限判断.
	 * 
	 * @param configAttributes
	 *            ConfigAttribute对象Iterator, 保存了当前访问的URL路径
	 *            
	 * @return 根据权限判断url是否可以访问
	 */
	boolean isAccessable(Iterator configAttributes);

}
