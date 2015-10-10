/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.base.service;

import java.util.List;

import com.mqm.frame.common.base.vo.FbrpInfraMenu;
import com.mqm.frame.infrastructure.base.service.IGenericService;

/**
 * 
 * <pre>
 * 菜单维护操作接口。
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
public interface IMenuService extends IGenericService<FbrpInfraMenu>{

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_admin_menuService";

	/**
	 * 根据菜单ID查找菜单。
	 * 
	 * @param menuId String
	 * 
	 * @return FbrpInfraMenu FbrpInfraMenu
	 */
	public FbrpInfraMenu findWithCache(String menuId);

	/**
	 * 根据菜单ID删除菜单节点。
	 * 
	 * @param id String
	 */
	public void deleteWithCascade(String id);

	/**
	 * 更新菜单节点。
	 * 
	 * @param  menuVo FbrpInfraMenu
	 * 
	 * @return FbrpInfraMenu FbrpInfraMenu
	 */
	public FbrpInfraMenu saveOrUpdate(FbrpInfraMenu menuVo);

	/**
	 * 查询可视菜单。
	 * 
	 * @return List
	 */
	public List<FbrpInfraMenu> findVisibleMenu();

	/**
	 * 查询角色菜单。
	 * 
	 * @param roleIds List<String>
	 * 
	 * @return List<FbrpInfraMenu>
	 */
	public List<FbrpInfraMenu> findRolesMenus(List<String> roleIds);
	
	/**
	 * 查询当前登录人员所在的机构所拥有的菜单。
	 * 
	 * @param orgCode String
	 * 
	 * @return List<FbrpInfraMenu>
	 */
	public List<FbrpInfraMenu> findOrgMenus(String orgCode);

	/**
	 * 通过报表的url查询菜单。
	 * 
	 * @param url String
	 * @return FbrpInfraMenu
	 */
	public FbrpInfraMenu findResMune(String url);
	
}
