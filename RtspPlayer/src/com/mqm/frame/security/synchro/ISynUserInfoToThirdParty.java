/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.synchro;

import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 此接口用于扩展平台用户数据维护操作, 若需要与外部应用同步用户数据时实现此接口. 实现的接口会被UserDetailsServiceImpl自动调用
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface ISynUserInfoToThirdParty {

	/**
	 * 同步保存用户信息。
	 * 
	 * @param user
	 *            要保存的用户数据
	 * @throws FbrpException
	 *             保存出错
	 */
	void save(FbrpSecStaff user) throws FbrpException;

	/**
	 * 同步修改用户信息。
	 * 
	 * @param user
	 *            要修改的用户数据
	 * @throws FbrpException
	 *             修改出错
	 */
	void update(FbrpSecStaff user) throws FbrpException;

	/**
	 * 同步删除用户信息。
	 * 
	 * @param user
	 *            要删除的用户
	 * @throws FbrpException
	 *             删除出错
	 */
	void delete(FbrpSecStaff user) throws FbrpException;
}
