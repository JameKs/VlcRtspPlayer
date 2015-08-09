/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint;

import java.util.List;

/**
 * <pre>
 * 程序的中文名称。
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
public interface IPrincipalType {
	
	/**
	 * 授权主体的名字。
	 * 
	 * @return String
	 */
	public String getPrincipalName();

	/**
	 * 根据人员ID，获取人员所在的授权的主体（如人员的角色）。
	 * 
	 * @param staffId
	 *            人员的ID
	 *            
	 * @return List
	 */
	public List getPrincipalsByStaffId(String staffId);

	/**
	 * 获取当前登录用户所拥有的授权主体。
	 * 
	 * @return List
	 */
	public List getCurrentPrincipals();


}
