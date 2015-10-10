/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.staff.vo.GyUuvOrgVO;
import com.mqm.frame.security.staff.vo.StaffOAVO;
import com.mqm.frame.security.staff.vo.StaffUumVO;

/**
 * 
 * <pre>
 * 人员管理服务接口。
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
public interface IStaffService extends IGenericService<FbrpSecStaff> {

	/**
	 * 常量。
	 */
	public static final String BEAN_ID = "fbrp_security_staffService";

	/**
	 * 根据所提供的FbrpSecStaff查询条件，查询符合条件的PagedResult 。
	 * 
	 * @param vo FbrpSecStaff
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param orders Order
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	public abstract PagedResult<FbrpSecStaff> pagedQuery(FbrpSecStaff vo, int pageIndex, int pageSize,
			Order... orders);
	
	
	/**
	 * 条件分页查询。
	 * 
	 * @param conStaffVO
	 *            条件对象
	 * @param offSet
	 *            起始记录
	 * @param pageSize
	 *            页尺寸
	 * 
	 * @return Object[]
	 */
	public Object[] queryByParam(FbrpSecStaff conStaffVO, int offSet,
			int pageSize);

	/**
	 * 新建、更新数据。
	 * 
	 * @param staffVO
	 *            FbrpSecStaff
	 */
	public void saveOrUpdate(FbrpSecStaff staffVO);

	/**
	 * 批量新建、更新。
	 * 
	 * @param staffs
	 *            List<FbrpSecStaff>
	 */
	public void saveOrUpdateAll(List<FbrpSecStaff> staffs);

	/**
	 * 通过id删除一条记录。
	 * 
	 * @param id
	 *            String
	 */
	public void deleteStaffById(String id);

	/*
	 * JSF方法开始
	 */
	/**
	 * 按staffVO的条件进行查询，查询属于volist这个范围内的员工信息 用于按部门进行条件查询。
	 * 
	 * @param staffVO
	 *            FbrpSecStaff
	 * @param idList
	 *            List<String>
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return Object[]
	 */
	public Object[] getStaffByParam(FbrpSecStaff staffVO, List<String> idList,
			int offset, int pageSize);

	/**
	 * 根据条件查询。
	 * 
	 * @param conStaffVO
	 *            FbrpSecStaff
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * @param filterSupadmin
	 *            boolean
	 * 
	 * @return Object[]
	 */
	public Object[] queryByParam(FbrpSecStaff conStaffVO, int offSet,
			int pageSize, boolean filterSupadmin);

	/**
	 * 根据Logic删除Staff。
	 * 
	 * @param staffVO
	 *            FbrpSecStaff
	 */
	public void deleteStaffByLogic(FbrpSecStaff staffVO);

	/**
	 * 通过角色ID查找拥有该角色的所有人员。
	 * 
	 * @param roleId
	 *            String
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return Object[] 返回结果包括集合及总数
	 */
	public Object[] queryByRoleId(String roleId, int offset, int pageSize);

	/**
	 * 分页查询所有人员，返回结果包括集合及总数。
	 * 
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return Object[] 返回结果包括集合及总数
	 */
	public abstract Object[] query(int offset, int pageSize);

	/**
	 * 查询操作。
	 * 
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * @param filterSupadmin
	 *            boolean
	 * 
	 * @return Object[]
	 */
	public abstract Object[] query(int offset, int pageSize,
			boolean filterSupadmin);

	/**
	 * 校验登录ID。
	 * 
	 * @param id
	 *            String
	 * @param loginId
	 *            String
	 * 
	 * @return boolean
	 */
	public abstract boolean checkLoginIdExist(String id, String loginId);

	/**
	 * 校验code。
	 * 
	 * @param id
	 *            String
	 * @param code
	 *            String
	 * 
	 * @return boolean
	 */
	public abstract boolean checkCodeExist(String id, String code);

	/**
	 * 通过角色查询Staff。
	 * 
	 * @param role
	 *            FbrpSecRole
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	public PagedResult<FbrpSecStaff> queryStaffByRole(FbrpSecRole role,
			int offset, int pageSize);

	/**
	 * 查找用户时，把相应用机构信息也查出来。
	 * 
	 * @param staff
	 *            FbrpSecStaff
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * @param filterSupadmin
	 *            boolean
	 * 
	 * @return Object[] 当前页集合+总记录数
	 */
	public abstract Object[] queryStaffContainsOrg(FbrpSecStaff staff,
			int offSet, int pageSize, boolean filterSupadmin);

	/**
	 * 跟据授权机构查询人员。
	 * 
	 * @param staff
	 *            人员查询条件
	 * @param offSet offset
	 * @param pageSize 每页数量
	 * @param orgId
	 *            授权机构的ID
	 * 
	 * @return Object[]
	 */
	public Object[] queryStaffByGrantOrg(FbrpSecStaff staff, int offSet,
			int pageSize, String orgId);

	/**
	 * 查询不是机构orgId 的所属机构成员，也不是授权机构成员且人员没有所属机构。
	 * 
	 * @param staff
	 *            人员查询条件
	 * @param offSet offset
	 * @param pageSize 每页数量
	 * @param orgId 机构ID
	 * 
	 * @return Object[]
	 */
	public Object[] queryStaffNoContainOrg(FbrpSecStaff staff, int offSet,
			int pageSize, String orgId);

	/**
	 * 查询不是机构orgId 的所属机构成员，也不是授权机构成员。
	 * 
	 * @param staff
	 *            FbrpSecStaff
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * @param orgId
	 *            String
	 * 
	 * @return Object[]
	 */
	public Object[] queryStaffNOGrantOrg(FbrpSecStaff staff, int offSet,
			int pageSize, String orgId);

	/**
	 * 查找所属机构的成员。
	 * 
	 * @param staffVO
	 *            FbrpSecStaff
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * @param orgId
	 *            String
	 * 
	 * @return Object[]
	 */
	public Object[] queryStaffByContainOrg(FbrpSecStaff staffVO, int offSet,
			int pageSize, String orgId);

	/**
	 * 根据机构查询机构的成员(包括所属机构和授权机构)。
	 * 
	 * @param staffVO
	 *            FbrpSecStaff
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * @param orgId
	 *            机构的ID
	 * 
	 * @return Object[]
	 */
	public Object[] queryStaffByOrg(FbrpSecStaff staffVO, int offSet,
			int pageSize, String orgId);
	
	/**
	 * 导入csv文件到人员表。
	 * 
	 * @param file
	 *            File
	 * 
	 * @return List<String>
	 * 
	 * @throws IOException
	 *             IO异常
	 */
	public List<String> saveCSVData(File file) throws IOException;

	/**
	 * 导入csv文件到人员表。
	 * 
	 * @param inputStream
	 *            InputStream
	 * 
	 * @return List<String>
	 * 
	 * @throws IOException
	 *             IO异常
	 */
	public List<String> saveCSVData(InputStream inputStream) throws IOException;

	/**
	 * 查询操作。
	 * 
	 * @param staffVO
	 *            FbrpSecStaff
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return Object[]
	 */
	public Object[] queryByParamfilter(FbrpSecStaff staffVO, int offset,
			int pageSize);

	/**
	 * 查询人员信息。
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param staff FbrpSecStaff
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	public PagedResult<FbrpSecStaff> queryMember(int pageIndex, int pageSize, FbrpSecStaff staff);
	
	/**
	 * 根据角色查询其人员。
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param staff FbrpSecStaff
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	public PagedResult<FbrpSecStaff> queryMemberById(int pageIndex, int pageSize, FbrpSecStaff staff);
	
	/**
	 * 根据角色查询不属于本角色的人员。
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param staff FbrpSecStaff
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	public PagedResult<FbrpSecStaff> queryExistedMemberById(int pageIndex, int pageSize, FbrpSecStaff staff);
	
	/**
	 * 添加角色下的人员。
	 * 
	 * @param list List<FbrpSecRoleMember>
	 */
	public void batchAddMembers(List<FbrpSecRoleMember> list);
	
	/**
	 * 删除角色下的人员。
	 * 
	 * @param list List<FbrpSecRoleMember>
	 */
	public void batchUpdateMembers(List<FbrpSecRoleMember> list);
	
	/**
	 * 手动在平台添加人员。
	 * 
	 * @param staff StaffUumVO
	 * @param staffId String 
	 */
	public void saveUser(StaffUumVO staff, String staffId);
	
	/**
	 * 查询同步用户信息。
	 * 
	 * @param map Map<String, String>
	 * @param pageIndex int
	 * @param pageSize int
	 * 
	 * @return PagedResult<StaffOAVO>
	 */
	public PagedResult<StaffOAVO> selectUser(Map<String, String> map, int pageIndex, int pageSize);
	
	/**
	 * 检查本地数据库中是否存在该信息。
	 * 
	 * @param accountName String
	 * 
	 * @return boolean
	 */
	public boolean checkOwn(String accountName);
	
	/**
	 * 更新同步状态。
	 * 
	 * @param codeList List<String>
	 * @param actionType String
	 */
	public void updateUserUum(List<String> codeList, String actionType);
	
	/**
	 * 查询同步用户信息。
	 * 
	 * @param accountName String
	 * @param actionType String
	 * 
	 * @return StaffOAVO
	 */
	public StaffOAVO selectUser(String accountName, String actionType);
	
	/**
	 * 对比人员机构是否相同。
	 * 
	 * @param vo StaffOAVO
	 * 
	 * @return boolean
	 */
	public boolean selectUserOrg(StaffOAVO vo);
	
	/**
	 * 批量同步添加人员。
	 * 
	 * @param vos List<StaffOAVO>
	 * @param uvos List<StaffOAVO>
	 * @param codeList List<String>
	 * @param actionType String
	 * @param staffId String
	 */
	public void batchAddUser(List<StaffOAVO> vos, List<StaffOAVO> uvos, List<String> codeList, String actionType, String staffId);
	
	/**
	 * 批量同步修改人员。
	 * 
	 * @param vos List<StaffOAVO>
	 * @param codeList List<String>
	 * @param actionType String
	 */
	public void batchUpdateUser(List<StaffOAVO> vos, List<String> codeList, String actionType);
	
	/**
	 * 批量同步移动人员。
	 * 
	 * @param vos List<StaffOAVO>
	 * @param codeList List<String>
	 * @param actionType String
	 */
	public void batchUpdateUserMov(List<StaffOAVO> vos, List<String> codeList, String actionType);
	
	/**
	 * 查询所有下级机关和本级机关。
	 * 
	 * @param orgCode String
	 * 
	 * @return List<GyUuvOrgVO>
	 */
	public List<GyUuvOrgVO> findAllOrg(String orgCode);
	
	/**
	 * 查询所有当前机关的子机关。
	 *  
	 * @param orgCode String
	 * 
	 * @return List<GyUuvOrgVO>
	 */
	public List<GyUuvOrgVO> findChildrenOrg(String orgCode);
	
	/**
	 * 查询当前机关是否有子机关。
	 * 
	 * @param orgCode String
	 * 
	 * @return boolean (true:有，false:没有)
	 */
	public boolean findChildrenOrgOwn(String orgCode);
	
	/**
	 * 查询用户权限机关。
	 * 
	 * @param accountName String
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectYhqxjg(String accountName);
	
	/**
	 * 查找有权限的主税务机构。
	 * 
	 * @param orgCode String
	 * 
	 * @return String
	 */
	public String selectMainOrgForGlznbz(String orgCode);
	
	/**
	 * 根据用户登录ID和税务机构编码来判断用户是否拥有改权限。
	 * 
	 * @param accountName String
	 * @param orgCode String
	 * 
	 * @return boolean （true：是，false：否）
	 */
	public boolean selectAdminOwn(String accountName, String orgCode);
	
	/**
	 * 保存税务人员机关权限。
	 * 
	 * @param list List<Map<String, Object>>
	 * @param accountName String
	 */
	public void saveYhqxjg(List<Map<String, Object>> list, String accountName);
	
	/**
	 * 检查用户是否拥有权限。
	 * 
	 * @param accountName String
	 * @param url String
	 * 
	 * @return boolean
	 */
	public boolean checkStaffGrant(String accountName, String url);
	
	/**
	 * 查找税务人员当前的税务机构。
	 * 
	 * @param accountname String
	 * 
	 * @return orgcode String
	 */
	public String selectUserOldOrgCode(String accountname);
	
	/**
	 * 查找税务人员的登录密码。
	 * 
	 * @param accountname String
	 * @return String
	 */
	public String selectPassword(String accountname);
	
}
