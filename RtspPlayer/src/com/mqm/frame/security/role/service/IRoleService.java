/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.service;

import java.util.List;

import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.role.vo.FbrpSecRoleVO;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 角色管理服务接口。
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
public interface IRoleService extends IGenericService<FbrpSecRole> {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_security_roleService";

	/**
	 * 得到所有记录。
	 * 
	 * @param aPageSize
	 *            int
	 * 
	 * @return 所有记录的集合
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public Object[] getAll(int aPageSize) throws FbrpException;

	/**
	 * 根据人员ID查出所属的所有群组。
	 * 
	 * @param staffId
	 *            String
	 * 
	 * @return List
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public List getRolesByStaffId(String staffId) throws FbrpException;

	/**
	 * 根据组ID查出所属的群组。
	 * 
	 * @param roleId
	 *            String
	 * 
	 * @return FbrpSecRoleVO
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public FbrpSecRole getRoleByRoleId(String roleId) throws FbrpException;

	/**
	 * 新增或修改数据。
	 * 
	 * @param fbrpSecRoleVO
	 *            FbrpSecRole
	 */
	public void saveOrUpdate(FbrpSecRole fbrpSecRoleVO);

	/**
	 * 根据id获得FbrpSecRoleVO。
	 * 
	 * @param id
	 *            String
	 * 
	 * @return FbrpSecRoleVO
	 */
	public FbrpSecRole getRoleById(String id);

	/**
	 * 角色编码是否存在。
	 * 
	 * @param code
	 *            String
	 * 
	 * @return boolean
	 */
	public boolean getRoles(String code);

	/**
	 * 根据主键删除角色。
	 * 
	 * @param id
	 *            String
	 */
	public void deleteById(String id);

	/**
	 * 根据条件查询记录结果，并统计记录数。
	 * 
	 * @param fbrpSecRoleVO
	 *            FbrpSecRole
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return Object[]
	 */
	public Object[] queryByParam(FbrpSecRole fbrpSecRoleVO, int offSet, int pageSize);

	/**
	 * 检查某角色是否存在。
	 * 
	 * @param id
	 *            String
	 * @param code
	 *            String
	 * 
	 * @return boolean
	 */
	public boolean checkCodeExist(String id, String code);

	/**
	 * 检查某角色是否存在。
	 * 
	 * @param id
	 *            String
	 * @param name
	 *            String
	 * 
	 * @return boolean
	 */
	public boolean checkNameExist(String id, String name);

	/**
	 * 根据应用查找角色。
	 * 
	 * @param appId
	 *            String
	 * 
	 * @return List&lt;FbrpSecRoleVO&gt; 指定应用下的角色集合
	 */
	public List<FbrpSecRole> findRoleByAppId(String appId);

	/**
	 * 通过ids列表分页获取角色列表。
	 * 
	 * @param ids
	 *            List
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return Object[]
	 */
	public Object[] findRolesByIds(List ids, int offset, int pageSize);

	/**
	 * 分页查询角色，过滤当前业务应用的管理员。
	 * 
	 * @param fbrpSecRoleVO
	 *            FbrpSecRole
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * @param userLoginOrgId 用户登录所属的机关。
	 * @return Object[]
	 */
	public PagedResult<FbrpSecRole> queryByParamFilterAdminRole(FbrpSecRole fbrpSecRoleVO, int offSet, int pageSize,String userLoginOrgId);

	/**
	 * 根据所提供的FbrpSecRole查询条件，查询符合条件的PagedResult 。
	 * 
	 * @param vo
	 *            FbrpSecRole
	 * 
	 * @param pageIndex
	 *            int
	 * 
	 * @param pageSize
	 *            int
	 * 
	 * @param orders
	 *            Order
	 * 
	 * @return PagedResult<FbrpSecRole>
	 */
	public PagedResult<FbrpSecRole> pagedQuery(FbrpSecRole vo, int pageIndex, int pageSize, Order... orders);

	/**
	 * 通过人员ID查找拥有该人员的所有角色。
	 * 
	 * @param staffId
	 *            String
	 * 
	 * @param i
	 *            int
	 * 
	 * @param maxValue
	 *            int
	 * 
	 * @return Object[] 返回结果包括集合及总数
	 */

	public Object[] queryByStaffId(String staffId, int i, int maxValue);

	/**
	 * 查询全部角色。
	 * 
	 * @param pageIndex
	 *            int
	 * 
	 * @param pageSize
	 *            int
	 * 
	 * @param role
	 *            FbrpSecStaffVO
	 * 
	 * @return PagedResult<FbrpSecStaffVO>
	 */
	public PagedResult<FbrpSecRoleVO> queryRole(int pageIndex, int pageSize, FbrpSecRoleVO role);

	/**
	 * 查询未选择的角色。
	 * 
	 * @param pageIndex
	 *            int
	 * 
	 * @param pageSize
	 *            int
	 * 
	 * @param role
	 *            FbrpSecStaffVO
	 * 
	 * @return PagedResult<FbrpSecStaffVO>
	 */
	public PagedResult<FbrpSecRoleVO> queryRoleById(int pageIndex, int pageSize, FbrpSecRoleVO role);

	/**
	 * 查询已选的角色。
	 * 
	 * @param pageIndex
	 *            int
	 * 
	 * @param pageSize
	 *            int
	 * 
	 * @param role
	 *            FbrpSecStaffVO
	 * 
	 * @return PagedResult<FbrpSecStaffVO>
	 */
	public PagedResult<FbrpSecRoleVO> queryExistedRoleById(int pageIndex, int pageSize, FbrpSecRoleVO role);

	/**
	 * 添加人员角色。
	 * 
	 * @param list
	 *            List<FbrpSecRoleMember>
	 */
	public void batchAddRoles(List<FbrpSecRoleMember> list);

	/**
	 * 删除人员角色。
	 * 
	 * @param list
	 *            List<FbrpSecRoleMember>
	 */
	public void batchUpdateRoles(List<FbrpSecRoleMember> list);
	
	/**
	 * 查询角色的编码是否存在。
	 * 
	 * @param code String
	 * 
	 * @return boolean
	 */
	public boolean ifExistCode(String code);
}
