/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.service;

import java.util.List;

import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 角色成员管理服务接口。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IRoleMemberService extends IGenericService<FbrpSecRoleMember>{
	
	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_security_roleMemberService";

	/**
	 * 按部门id取得群组-人员关系对象。
	 * 
	 * @param roleId String
	 * 
	 * @return List
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public List findRoleMemberByRoleId(String roleId)
			throws FbrpException;

	/**
	 * 按人员id取得群组-人员关系对象。
	 * 
	 * @param staffId String
	 * 
	 * @return List
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public List findRoleMemberByStaffId(String staffId)
			throws FbrpException;

	/**
	 * 批量删除群组成员关系。
	 * 
	 * @param deleteBeanList List
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public void deleteData(List deleteBeanList) throws FbrpException;
	
	/**
	 * 根据角色Id获角色成员列表。
	 *  
	 * @param roleId String
	 * 
	 * @return List
	 */
	public List getRoleMembersByRoleId(String roleId);
	
	/**
	 * 根据人员Id获取数据。
	 * 
	 * @param staffId String
	 * 
	 * @return List
	 */
	public List getRoleMembersByStaffId(String staffId);
	
	/**
	 * 保存角色成员列表。
	 * 
	 * @param list List<FbrpSecRoleMember>
	 */
	public void saveOrUpdateAll(List<FbrpSecRoleMember> list);
	
	/**
	 * 根据主键删除。
	 * 
	 * @param id String
	 */
	public void deleteById(String id);
	
	/**
	 * 批量删除。
	 * 
	 * @param list List
	 * 
	 * @return int
	 */
	public int deleteAll(List list);

	/**
	 * 删除角色成员。
	 * 
	 * @param staffId String
	 */
	public void deleteRoleMemberByStaffId(String staffId);
	
	/**
	 * 删除某用户的所有角色。
	 * 
	 * @param staffId String
	 */
	public void deleteAllRoleMemberByStaffId(String staffId);

	/**
	 * 更新角色信息。
	 * 
	 * @param staffId String
	 * @param list List<FbrpSecRole>
	 */
	public void updateToStaff(String staffId, List<FbrpSecRole> list);

	/**
	 * 基于角色ID删除角色成员。
	 * 
	 * @param roleId String
	 */
	public void deleteRoleMemberByRoleId(String roleId);

	/**
	 * 更新用户角色信息。
	 * 
	 * @param roleId String
	 * @param list List<FbrpSecStaff>
	 */
	public void updateToRole(String roleId, List<FbrpSecStaff> list);
}
