/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint;

import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.extendpoint.IExtendPoint;

/**
 * <pre>
 * 用于数据权限的扩展点定义。
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
public interface IGrantExtendPoint extends IExtendPoint {

	/**
	 * 在标签页上显示的名字。
	 * 
	 * @return String 权限名称
	 */
	public String getGrantName();

	/**
	 * 获取资源列表（自动过滤）。
	 * 
	 * @return List 被授权访问的数据集合
	 */
	//@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, admin)")
	public List getAll();
	
	/**
	 * 获取首页左侧菜单的资源列表（自动过滤）。
	 * 
	 * @return List 
	 */
	public List getLeftMenu();
	
	/**
	 * 根据角色IDs获取资源列表（自动过滤）。
	 * 
	 * @param roleIds List<String>
	 * 
	 * @return List 被授权访问的数据集合
	 */
	public List getWith(List<String> roleIds);

	/**
	 * 根据授权过滤List中的资源对象列表。
	 * 
	 * @param principals List
	 * 
	 * @return List List
	 */
	public List getAllByPrincipals(List principals);

	/**
	 * 更改权限信息（先删除原有授权的信息，然后重新授权）。
	 * 
	 * @param insertList List
	 * @param deleteList
	 *            原有授权的资源的列表
	 * @param principalId
	 *            需要授权的对象的Id
	 */
	public void updateGranted(List insertList, List deleteList,
			String principalId);

	/**
	 * 通过资源的id获取资源。
	 * 
	 * @param id 资源ID。
	 * 
	 * @return Object 业务数据（数据资源）
	 */
	public Object getObjectById(String id);

	/**
	 * 若资源在页面上的展现形式是列表，则提供要展现的字段的名字和中文描述， 树形的则不需要提供，但资源的VO需要实现IFbrpTree的接口。
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getResultValueMap();

	/**
	 * 保存授权资源时，给应用管理员授权。
	 * 
	 * @param object
	 *            需要保存的资源的对象。
	 */
	public void saveObject(Object object);

	/**
	 * 授权管理Tab页显示时的排列次序，按照其值的大小进行排序，序号小的显示时排在前面，序号不必是连续的。
	 * 
	 * @return int
	 */
	public int getSortNo();

	/**
	 * 获取GrantShowShape。
	 * 
	 * @return IGrantShowShape
	 */
	public IGrantShowShape getGrantShowShape();

	/**
	 * 获取授权主体。
	 * 
	 * @return IPrincipalType
	 */
	public IPrincipalType getPrincipalType();
}
