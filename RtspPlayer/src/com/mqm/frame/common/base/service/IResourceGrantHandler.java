/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.base.service;

import java.util.List;

import com.mqm.frame.util.exception.FbrpException;

/**
 * <pre>
 * 此接口用于扩展平台对角色进行报表资源授权操作, 若需要与外部应用同步角色跟报表资源关系时实现此接口. 实现的接口会被RoleGrantReportAction自动调用。
 * 
 * 需要同步的系统包括：报表服务器（如BI.Office\Cognos）、外部系统等。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface IResourceGrantHandler {

	/**
	 * 先解除deleteList里资源跟bipRoleVO的授权关系。 然后再授予bipRoleVO跟insertList的权限。
	 * 
	 * @param deleteList List
	 * @param insertList List
	 * @param bipRoleVO Object
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public void synResourceGrant(List deleteList, List insertList,
			Object bipRoleVO) throws FbrpException;

}
