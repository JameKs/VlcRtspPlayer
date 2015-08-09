/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service;

import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.service.IDefaultService;
import com.mqm.frame.security.role.vo.FbrpSecRole;

/**
 * 
 * <pre>
 * 提供数据权限服务（基于Spring Security ACL构建而来）。
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
public interface IGrantService extends IDefaultService {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_security_grantService";

	/**
	 * 获取principals所拥有的resourceClassName类型的资源的列表。
	 * 
	 * @param objectClassName
	 *            数据资源对应的Java类的FQN（全限定名）
	 * @param principals
	 *            授权的主体的ID列表（用户所在的授权的主体）
	 * 
	 * @return Map<String, List> key为资源的ID,value为用户所拥有的权限的列表(mask值)
	 */
	public Map<String, List> getGrantsByTypeAndPrincipals(
			String objectClassName, List principals);

	/**
	 * 根据父类资源获取继承父类权限的资源和权限列表(权限之间的继承关系，并不是对象之间的继承关系)。
	 * 
	 * @param object 具体的资源的对象
	 *            （如菜单，资源，操作，组织等）
	 * @param principals 授权主体
	 *            （如角色） 的ID的列表
	 * @return key为资源的ID,value为对应资源的权限列表
	 */
	public Map<String, List> getGrantsByParentAndPrincipals(Object object,
			List principals);

	/**
	 * 获取对象的父类的权限(权限之间的继承关系，并不是对象之间的继承关系)。
	 * 
	 * @param object
	 *            Object
	 * @param principal 授权主体
	 *            (如角色)
	 * 
	 * @return Map<Object, List>
	 */
	public Map<Object, List> getParentGrants(Object object, String principal);

	/**
	 * 更新principal所拥有的权限。
	 * 
	 * @param toGrantObjectList
	 *            主体拟被授权操作的资源列表
	 * @param currentObjectList
	 *            主体当前被授权操作的资源列表
	 * @param principal
	 *            授权主体 (如角色)
	 */
	public void updateToPrincipal(List toGrantObjectList,
			List currentObjectList, String principal);

	/**
	 * 将object的mask的权限授予 principal。
	 * 
	 * @param object
	 *            具体的资源对象
	 * 
	 * @param mask 权限的mask值
	 * 
	 * @param principal
	 *            授权主体（如角色，不能为空）
	 */
	public void insertGrant(Object object, int mask, String principal);

	/**
	 * 设置list资源中的对象权限继承parent的权限，parent必须在ACL中存在。
	 * 
	 * @param parentObject
	 *            Object
	 * @param objectList
	 *            List
	 */
	public void insertGrant(Object parentObject, List objectList);

	/**
	 * 删除principal 对object的Permission的权限。
	 * 若principal为空，则认为删除数据库中object这条记录，因此方法会删除ObjectIdentity中的记录。
	 * 
	 * @param object
	 *            Object
	 * @param mask
	 *            int
	 * @param principal
	 *            String
	 */
	public void deleteGrants(Object object, int mask, String principal);

	/**
	 * 删除某数据资源对应的ACL。
	 * 
	 * @param object
	 *            数据资源
	 */
	public void deleteAcl(Object object);

	/**
	 * 得到当前登录用户对数据资源的权限列表。
	 * 
	 * @param object
	 *            Object
	 * @return 返回值为权限的mask代码列表
	 */
	public List getGrantsByResource(Object object);

	/**
	 * 在Acl中创建一条用于授权的记录。
	 * 
	 * @param object
	 *            资源对象
	 */
	public void createAcl(Object object);

	/**
	 * 过滤资源objectList中principals拥有权限记录。
	 * 
	 * @param objectList
	 *            List
	 * @param principals
	 *            List
	 * 
	 * @return Map
	 */
	public Map getAllFilter(List objectList, List principals);

	/**
	 * 根据资源，查找授权主体的列表(对该资源具有read或admin权限)。
	 * 
	 * @param object
	 *            资源
	 * @return List
	 */
	public List getAllPrincipals(Object object);

	/**
	 * 根据资源的类型和唯一标识，查找授权主体的列表(对该资源具有read或admin权限)。
	 * 
	 * @param objectClassName
	 *            数据资源类型
	 * @param id
	 *            标识数据资源
	 * 
	 * @return List
	 */
	public List getAllPrincipals(String objectClassName, String id);

	/**
	 * 修改某一数据资源的权限 ,默认将'object'的admin的权限赋给principals集合。
	 * 
	 * @param object
	 *            具体的资源(菜单,资源,操作,组织等)
	 * @param principals
	 *            授权的主体(角色)的ID的列表
	 */
	public void updateGrants(Object object, List principals);

	/**
	 * 判断是否超级管理员, 超级管理员拥有全部系统权限。
	 * 
	 * @param loginId
	 *            String
	 * 
	 * @return boolean
	 */
	public boolean isAdmin(String loginId);

	/**
	 * 获取当前业务应用的应用管理员角色。
	 * 
	 * @return FbrpSecRole 业务应用管理员角色
	 */
	public FbrpSecRole getAppAdminRole();

	/**
	 * 判断角色是否为应用的管理员。
	 * 
	 * @param roleId
	 *            String roleId
	 * 
	 * @return boolean 判断某角色是否是业务应用管理员
	 */
	public boolean isAppAdminRole(String roleId);

	/**
	 * 插入Grant。
	 * 
	 * @param objectId
	 *            具体的资源对象的唯一标识
	 * @param objectClassName
	 *            资源对象的类型
	 * 
	 * @param mask
	 *            权限的mask值
	 * 
	 * @param principal
	 *            授权主体（如角色，不能为空）
	 */
	public void insertGrant(String objectId, String objectClassName, int mask,
			String principal);

	/**
	 * 将objects 的权限批量的授给 principals。
	 * 
	 * @param objects
	 *            资源对象的列表
	 * @param mask
	 *            权限的mask值
	 * @param principals
	 *            授权主体列表
	 */
	public void insertGrants(List<Object> objects, int mask, List principals);

	/**
	 * 批量的删除 授权主体列表 所拥有的资源对象列表的权限。
	 * 
	 * @param objects
	 *            资源对象的列表
	 * 
	 * @param mask
	 *            权限的mask值
	 * 
	 * @param principals
	 *            授权主体列表
	 */
	public void deleteGrants(List<Object> objects, int mask, List principals);

	/**
	 * 更新授权主体列表 所拥有的资源对象列表的权限。
	 * 
	 * @param roleId String
	 * @param newEntry Map<String,String>
	 */
	public void updateGrant(String roleId, Map<String,String> newEntry);

}
