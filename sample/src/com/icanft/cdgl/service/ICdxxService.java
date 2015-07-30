/**
 * ICdxxService.java
 * 2015
 * 2015年5月14日
 */
package com.icanft.cdgl.service;

import java.util.List;
import java.util.Map;

import com.icanft.cdgl.dao.ICdxxDao;
import com.icanft.cdgl.vo.Cdxx;

/**
 * @author Administrator
 *
 */
public interface ICdxxService {

	public abstract void insert(Cdxx cdxx);

	public abstract void delete(String id);

	public abstract void update(Cdxx cdxx);

	public abstract Cdxx findById(String id);

	public abstract List findAll(String hasRoot);
	
	public abstract List findAllUserMenu(String userId);

	/**
	 * 获得整个菜单树
	 * @param node
	 * @return
	 */
	public abstract List getMenuJsonTree();

	/**
	 * 获得用户菜单
	 * @param userId
	 * @return
	 */
	public abstract List getUserMenuPanel(Map map);
	
	/**
	 * 获得用户菜单
	 * @param userId
	 * @return
	 */
	public abstract List getUserMenuPanelTree(Map map);

	/**
	 * 查询用户的顶层菜单
	 * 
	 * @return
	 */
	public abstract List getNodesByIdAndUserId(Map map);

	/**
	 * 查询当前菜单的子菜单
	 * 
	 * @param rs
	 * @return
	 */
//	public abstract List getChildrenNodes(String pId);

	/**
	 * 查询当前菜单的子菜单count
	 * 
	 * @param rs
	 * @return
	 */
//	public abstract long getChildrenNodesCounts(String pId);

	/**
	 * @param cdxxDao
	 *            the cdxxDao to set
	 */
	public abstract void setCdxxDao(ICdxxDao cdxxDao);

	/**
	 * 我的收藏夹
	 * @param map
	 * @return
	 */
	public abstract List getWdscjByUserId(Map map);

	/**
	 * 获取部门各位人员树
	 * @param node
	 * @return
	 */
	public abstract List getDeptGwUserTree();
}