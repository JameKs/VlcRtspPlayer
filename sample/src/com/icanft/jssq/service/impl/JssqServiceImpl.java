/**
 * JsglServiceImpl.java
 * 2015
 * 2015年5月18日
 */
package com.icanft.jssq.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.icanft.cdgl.vo.Menu;
import com.icanft.common.util.StringUtil;
import com.icanft.jsgl.vo.Role;
import com.icanft.jssq.dao.IJssqDao;
import com.icanft.jssq.service.IJssqService;
import com.icanft.jssq.vo.CdxxRole;
import com.icanft.jssq.vo.RoleCd;

/**
 * @author Administrator
 * 
 */
public class JssqServiceImpl implements IJssqService {

	private IJssqDao jssqDao;

	@Override
	public List findList(Role role) {
		return jssqDao.findList(role);
	}

	@Override
	public int findListCount(Role role) {
		return jssqDao.findListCount(role);
	}

	@Override
	public List findRoleMenuTree(String roleId) {
		List<Map<String, Object>> tree = this.serialize(roleId,"E1801EBE06D211E588790023540C477B");// 此处为入口
		return tree;// 输出的即为JSON字符串
	}
	
	@Override
	public List findRoleMenuZtree(String roleId) {
		List<Menu> allNodes = jssqDao.findRoleMenuZtree(roleId==null?"xxxxx":roleId);
		return allNodes;// 输出的即为JSON字符串
	}
	
	/**
	 * 递归获取整个树
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	private List<Map<String, Object>> serialize(String roleId, String pId) {
		
		List<CdxxRole> allNodes = jssqDao.getAllNodes(roleId==null?"xxxxx":roleId);
		
		List<CdxxRole> list = this.getChildrenNodes(pId, allNodes);
		
		List<Map<String, Object>> tempDate = serializeTree(list, allNodes);
		
		return tempDate;
	}

	/**
	 * 递归获取整个树
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	private List<Map<String, Object>> serializeTree(List rootList, List allNodes) {
		List<Map<String, Object>> tempDate = new ArrayList<Map<String, Object>>();//作为树的载体

		Iterator<CdxxRole> it = rootList.iterator();
		// 当顶层菜单有数据的时候 依次循环
		while (it.hasNext()) {
			CdxxRole cd = it.next();
			//allNodes.remove(cd);//删除本菜单
			Map<String, Object> obj = StringUtil.transStrToCheckTree(cd);// 首先生成本菜单数据

			if (this.hasChildren(cd.getId(), allNodes)) {// 如果有子菜单

				List<CdxxRole> childs = this.getChildrenNodes(cd.getId(), allNodes);// 查询出子菜单

				obj.put("children", this.serializeTree(childs, allNodes));// 递归生成子菜单数据 并与父菜单关联
			}

			tempDate.add(obj);// 将本菜单放入载体
		}
		return tempDate;
	}

	public List<CdxxRole> getChildrenNodes(String pId, List allNodes) {
		List<CdxxRole> cds = new ArrayList<CdxxRole>();
		Iterator<CdxxRole> it = allNodes.iterator();
		while (it.hasNext()) {
			CdxxRole cd = it.next();
			if (pId.equals(cd.getpId())) {
				cds.add(cd);
			}
		}
		return cds;
	}
	
	public boolean hasChildren(String pId, List allNodes) {

		Iterator<CdxxRole> it = allNodes.iterator();
		while (it.hasNext()) {
			CdxxRole cd = it.next();
			if (pId.equals(cd.getpId())) {
				return true ;
			}
		}
		// 此处查询数据库中当前层级总条数
		return false;
	}

	@Override
	public void save(String idStr, String roleId, String loginId) {
		RoleCd gx = new RoleCd();
		gx.setRoleId(roleId);
		gx.setCjr(loginId);
		//
		jssqDao.delete(gx);

		// 解析树
		String[] ids = idStr.split("#");
		for (int i = 0; i < ids.length - 1; i++) {

			gx.setCdId(ids[i]);
			
			jssqDao.insert(gx);
		}

	}

	/**
	 * @param jsglDao
	 *            the jsglDao to set
	 */
	public void setJssqDao(IJssqDao jssqDao) {
		this.jssqDao = jssqDao;
	}
}
