/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * AbstractPageBean子类。
 * </pre>
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class PrincipalPageBean {

	private List principals = new ArrayList();

	private Object principalObject;

	/**
	 * 查询。
	 * 
	 */
	public void query() {
	}

	/**
	 * 返回principals。
	 * 
	 * @return 返回principals。
	 */
	public List getPrincipals() {
		return principals;
	}
	
	/**
	 * 返回principalObject。
	 * 
	 * @return 返回principalObject。
	 */
	public Object getPrincipalObject() {
		return principalObject;
	}

	/**
	 * 设置principalObject。
	 * 
	 * @param principalObject Object
	 */
	public void setPrincipalObject(Object principalObject) {
		this.principalObject = principalObject;
	}

	/**
	 * 设置principals。
	 * 
	 * @param principals List
	 */
	public void setPrincipals(List principals) {
		this.principals = principals;
	}

}
