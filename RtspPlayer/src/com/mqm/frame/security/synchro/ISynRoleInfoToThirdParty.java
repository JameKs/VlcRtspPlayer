/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.synchro;

import java.util.List;

import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 此接口用于扩展平台用户组数据维护操作, 若需要与外部应用同步用户组数据时实现此接口。
 * 实现的接口会被RoleServiceImpl和RolememberServiceImpl自动调用。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface ISynRoleInfoToThirdParty {

	/**
	 * 同步保存用户组。
	 * 
	 * @param bean
	 *            保存的用户组对象
	 *            
	 * @throws FbrpException
	 *             保存出错时抛出异常, 外部应用抛出的异常需要在实现中统一捕获并转换成ServiceException
	 */
	void save(FbrpSecRole bean) throws FbrpException;

	/**
	 * 同步更新用户组。
	 * 
	 * @param bean
	 *            需要更新的用户组对象
	 *            
	 * @throws FbrpException
	 *             更新出错时抛出异常
	 */
	void update(FbrpSecRole bean) throws FbrpException;

	/**
	 * 根据组id同步删除用户组。
	 * 
	 * @param roleId
	 *            要删除的组id
	 *            
	 * @throws FbrpException
	 *             删除出错
	 */
	void deleteGroupById(String roleId) throws FbrpException;

	/**
	 * 同步设置组成员。
	 * 
	 * @param bean
	 *            要设置的组成员
	 *            
	 * @throws FbrpException
	 *             设置出错
	 */
	void saveMember(FbrpSecRoleMember bean) throws FbrpException;

	/**
	 * 同步删除组成员。
	 * 
	 * @param memberVo
	 *            要删除的组成员
	 *            
	 * @throws FbrpException
	 *             删除出错
	 */
	void deleteMember(FbrpSecRoleMember memberVo) throws FbrpException;
	
	
	/**
	 * 把staffId的角色集合List<FbrpSecRoleVO> list同步到该应用所对应的应用服务器上。
	 * 
	 * @param staffId String
	 * @param list List<FbrpSecRole>
	 * 
	 * @throws FbrpException 异常
	 */
	void synUserRoleInfos(String staffId,List<FbrpSecRole> list )throws FbrpException;
	
	
	/**
	 * 把staffId的角色集合List<FbrpSecRoleVO> list同步到该应用所对应的应用服务器上。
	 * @param roleId  String
	 * @param list List<FbrpSecStaff>
	 * @throws FbrpException 异常
	 */
	void synUserRoleInfosForRoleId(String roleId,List<FbrpSecStaff> list)throws FbrpException;

}
