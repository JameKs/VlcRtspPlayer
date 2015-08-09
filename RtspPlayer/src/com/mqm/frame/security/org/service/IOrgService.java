/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.org.service;

import java.util.List;

import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.security.org.vo.FbrpSecOrg;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 机构服务接口。
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
public interface IOrgService extends IGenericService<FbrpSecOrg> {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_security_orgService";

	/**
	 * 设置当前登录用户机构信息。
	 * 
	 * @param userProfileVO
	 *            UserProfileVO
	 */
	public void queryOrgInfoForLoginUser(UserProfileVO userProfileVO);

	/**
	 * 根据组织ID，获得组织具体信息。
	 * 
	 * @param orgId
	 *            String
	 * 
	 * @return FbrpSecOrg
	 */
	public FbrpSecOrg getOrgByOrgId(String orgId);

	/**
	 * 获得某个组织机构下面的所有下级分支机构数量。
	 * 
	 * @param org
	 *            FbrpSecOrg
	 * 
	 * @return int
	 * 
	 * @throws FbrpException
	 *             异常
	 */
	public int getOrgCountBySuperOrg(FbrpSecOrg org) throws FbrpException;

	/**
	 * 查询下级机构中的成员数量。
	 * 
	 * @param org
	 *            FbrpSecOrg
	 * @param delFlag
	 *            Boolean
	 * 
	 * @return int
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public int getOrgCountBySuperOrg(FbrpSecOrg org, Boolean delFlag)
			throws FbrpException;

	/**
	 * 保存新建的组织。
	 * 
	 * @param org
	 *            FbrpSecOrg
	 * @param user
	 *            UserProfileVO
	 * 
	 * @return List<FbrpSecOrgVO>, 返回值为保存后的对象, 写入了delFlag等属性默认值
	 * 
	 * @throws FbrpException
	 *             异常
	 */
	public FbrpSecOrg save(FbrpSecOrg org, UserProfileVO user)
			throws FbrpException;

	/**
	 * 批量保存组织。
	 * 
	 * @param orgs
	 *            List
	 * @param user
	 *            UserProfileVO
	 * 
	 * @return List
	 * 
	 * @throws FbrpException
	 *             异常
	 */
	public List save(List orgs, UserProfileVO user) throws FbrpException;

	/**
	 * 更新某个机构的信息。
	 * 
	 * @param org
	 *            FbrpSecOrg
	 * @param user
	 *            UserProfileVO
	 * 
	 * @return FbrpSecOrg
	 * 
	 * @throws FbrpException
	 *             异常
	 */
	public FbrpSecOrg update(FbrpSecOrg org, UserProfileVO user)
			throws FbrpException;

	/**
	 * 取得所有的部门信息。
	 * 
	 * @return List
	 * 
	 * @throws FbrpException
	 *             异常
	 */
	public List getAllOrg() throws FbrpException;

	/**
	 * 根据用户编号取得所属机构信息。
	 * 
	 * @param staffid
	 *            String
	 * 
	 * @return FbrpSecOrg
	 */
	public FbrpSecOrg getOrgByStaffid(String staffid);

	/**
	 * 根据机构取得所有成员列表 。
	 * 
	 * @param orgid
	 *            String
	 * 
	 * @return List
	 */
	public List getStaffListByOrgid(String orgid);

	/**
	 * 基于机构ID查询用户信息。
	 * 
	 * @param orgid
	 *            String
	 * @param offset
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return List
	 */
	public List getStaffListByOrgid(String orgid, int offset, int pageSize);

	/**
	 * 保存组织成员信息。
	 * 
	 * @param orgid
	 *            机构ID
	 * @param staffids
	 *            人员编号字符串，以逗号分隔
	 */
	public void saveOrgMember(String orgid, String staffids);

	/**
	 * 根据人员编号删除所属机构信息。
	 * 
	 * @param staffid
	 *            String
	 */
	public void deleteOrgMemberByStaffid(String staffid);

	/**
	 * 根据机构号和人员编号删除机构成员信息。
	 * 
	 * @param orgid
	 *            String
	 * @param staffid
	 *            String
	 */
	public void deleteOrgMemberByOrgidAndStaffid(String orgid, String staffid);

	/**
	 * 获取所有的根机构。
	 * 
	 * @return List
	 */
	public List getOwnerOrg();

	/**
	 * 逻辑删除某个节点，以及其下所有节点。
	 * 
	 * @param updateFbrpSecOrgVO
	 *            FbrpSecOrg
	 * @param user
	 *            UserProfileVO
	 */
	public void deleteOrgFlag(FbrpSecOrg updateFbrpSecOrgVO, UserProfileVO user);

	/**
	 * 根据id得到其下所有的子节点对象。
	 * 
	 * @param id
	 *            String
	 * @param delFlag
	 *            删除标识
	 * 
	 * @return List String
	 */
	public List<FbrpSecOrg> getAllOrgChild(String id, String delFlag);

	/**
	 * 根据orgId查询出当前机构的所有员工的ID的集合(过滤部门信息用)。
	 * 
	 * @param id
	 *            String
	 * 
	 * @return List<String>
	 */
	public List<String> getStaffByOrgId(final String id);

	/**
	 * 通过机构ID集合找到对应的机构集合。
	 * 
	 * @param idList
	 *            机构ID集合
	 * 
	 * @return 机构集合
	 */
	public List<FbrpSecOrg> getAllOrgByIdList(List<String> idList);

	/**
	 * 根据主键获得FbrpSecOrgVO对象。
	 * 
	 * @param id
	 *            String
	 * 
	 * @return FbrpSecOrg
	 */
	public FbrpSecOrg getFbrpSecOrgVOById(String id);

	/**
	 * 从当前部门中删除该员工。
	 * 
	 * @param staffId
	 *            String
	 * @param orgId
	 *            String
	 */
	public void deleteStaffForOrg(String staffId, String orgId);

	/**
	 * 更新。
	 * 
	 * @param orgId
	 *            String
	 * @param list
	 *            List
	 * @param relationType
	 *            String
	 */
	public void updateToOrg(String orgId, List<FbrpSecStaff> list,
			String relationType);

	/**
	 * 更新操作。
	 * 
	 * @param orgId
	 *            String
	 * @param list
	 *            List<FbrpSecStaff>
	 */
	public void updateToOrg(String orgId, List<FbrpSecStaff> list);

	/**
	 * 获取该机构下的直接子节点。
	 * 
	 * @param orgId
	 *            如果orgId为空，则查顶级机构。
	 * 
	 * @return List&lt;FbrpSecOrgVO&gt; 子机构集合
	 */
	public List<FbrpSecOrg> getChildOrgById(String orgId);

}
