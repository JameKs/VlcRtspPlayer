/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.synchro;

import com.mqm.frame.security.org.vo.FbrpSecOrg;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 此接口用于扩展平台机构数据维护操作, 若需要与外部应用同步机构数据时实现此接口. 实现的接口会被orgServiceImpl自动调用。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface ISynOrgInfoToThirdParty {

	/**
	 * 同步保存机构数据。
	 * 
	 * @param orgVo FbrpSecOrg
	 * 
	 * @throws FbrpException 异常
	 */
	void save(FbrpSecOrg orgVo) throws FbrpException;

	/**
	 * 同步修改机构数据。
	 * 
	 * @param orgVo FbrpSecOrg
	 * 
	 * @throws FbrpException 异常
	 */
	void update(FbrpSecOrg orgVo) throws FbrpException;

	/**
	 * 同步删除机构数据。
	 * 
	 * @param orgVo FbrpSecOrg
	 * 
	 * @throws FbrpException 异常
	 */
	void delete(FbrpSecOrg orgVo) throws FbrpException;

	/**
	 * 根据机构id同步删除机构数据。
	 * 
	 * @param orgId String
	 * 
	 * @throws FbrpException 异常
	 */
	void deleteUnitById(String orgId) throws FbrpException;
}
