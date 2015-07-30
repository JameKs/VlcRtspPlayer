package com.icanft.cdgl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.icanft.cdgl.dao.ICdxxDao;
import com.icanft.cdgl.service.ICdxxService;
import com.icanft.cdgl.vo.Cdxx;
import com.icanft.cdgl.vo.Dept;
import com.icanft.cdgl.vo.Gw;
import com.icanft.common.constant.BaseConstants;
import com.icanft.common.util.StringUtil;
import com.icanft.xtgl.yhgl.vo.User;

public class CdxxServiceImpl implements ICdxxService {

	private ICdxxDao cdxxDao;

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#add(com.icanft.cdgl.vo.Cdxx)
	 */
	@Override
	public void insert(Cdxx cdxx) {
		cdxxDao.insert(cdxx);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#deleteById(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		cdxxDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#update(com.icanft.cdgl.vo.Cdxx)
	 */
	@Override
	public void update(Cdxx cdxx) {
		cdxxDao.update(cdxx);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#findById(java.lang.String)
	 */
	@Override
	public Cdxx findById(String id) {
		return cdxxDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#findAll()
	 */
	@Override
	public List findAll(String hasRoot) {
		return cdxxDao.findAll(hasRoot);
	}
	
	/**
	 * 查询用户菜单
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List findAllUserMenu(String userId) {
		List allNodes = cdxxDao.findAllUserMenu(userId);
		return allNodes;
	}

	
	/**
	 * 获得第一级菜单，并递归获取整个树
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List getMenuJsonTree() {
//		List<Cdxx> childCdxxs = this.getChildrenNodes("0");//此处rs为数据库顶层菜单返回结果
		Map map = new HashMap();
		map.put("pId", "0");
		List<Map<String, Object>> tree = this.serialize(map);//此处为入口
		return tree;// 输出的即为JSON字符串
	}
	
	/**
	 * 获得panel菜单
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List getUserMenuPanel(Map map) {
		// 首先生成本菜单数据
		List<Cdxx> cdxxs = this.getNodesByIdAndUserId(map);
		List<Map<String, Object>> menuPanel = StringUtil.serializeMenuPanel(cdxxs);
		return menuPanel;
	}
	
	
	/**
	 * 获得每个panel菜单下面的子树
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List getUserMenuPanelTree(Map map) {
		List<Map<String, Object>> tree = this.serialize(map);//此处为入口
		return tree;// 输出的即为JSON字符串
	}
	
	/**
	 * 我的收藏夹
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List getWdscjByUserId(Map map) {
		map.put("wdscj", "wdscj");
		List<Map<String, Object>> tree = this.serialize(map);//此处为入口
		return tree;
	}
	
	@Override
	public List<Cdxx> getNodesByIdAndUserId(Map map) {
		
		List<Cdxx> cds = cdxxDao.getNodesByIdAndUserId(map);

		return cds;
	}

	@Override
	public void setCdxxDao(ICdxxDao cdxxDao) {
		this.cdxxDao = cdxxDao;
	}
	
	/**
	 * 递归获取整个树
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	private List<Map<String, Object>> serialize(Map map) {
		
		String userId = (String)map.get("id");
		String pId = (String)map.get("pId");
		String wdscj = (String)map.get("wdscj");
		List<Cdxx> allNodes = null;
		if(StringUtils.isNotEmpty(userId)){
			allNodes = cdxxDao.findAllUserMenu(userId);
		} else {
			allNodes = cdxxDao.findAll(BaseConstants.TREE_HAS_ROOT);
		}
		
		List<Cdxx> list;
		if(StringUtils.isEmpty(wdscj)){
			list = this.getChildrenNodes(pId, allNodes);
		}else{
			list = cdxxDao.getWdscjByUserId(map);//此处rs为数据库顶层菜单返回结果
		}
		
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

		Iterator<Cdxx> it = rootList.iterator();
		// 当顶层菜单有数据的时候 依次循环
		while (it.hasNext()) {
			Cdxx cd = it.next();
			//allNodes.remove(cd);//删除本菜单
			Map<String, Object> obj = StringUtil.transStrToTree(cd);// 首先生成本菜单数据

			if (this.hasChildren(cd.getId(), allNodes)) {// 如果有子菜单

				List<Cdxx> childs = this.getChildrenNodes(cd.getId(), allNodes);// 查询出子菜单

				obj.put("children", this.serializeTree(childs, allNodes));// 递归生成子菜单数据 并与父菜单关联
			}

			tempDate.add(obj);// 将本菜单放入载体
		}
		return tempDate;
	}
	
	public List<Cdxx> getChildrenNodes(String pId, List allNodes) {
		List<Cdxx> cds = new ArrayList<Cdxx>();
		Iterator<Cdxx> it = allNodes.iterator();
		while (it.hasNext()) {
			Cdxx cd = it.next();
			if (pId.equals(cd.getpId())) {
				cds.add(cd);
			}
		}
		return cds;
	}
	
	public boolean hasChildren(String pId, List allNodes) {

		List<Cdxx> cds = new ArrayList<Cdxx>();
		Iterator<Cdxx> it = allNodes.iterator();
		while (it.hasNext()) {
			Cdxx cd = it.next();
			if (pId.equals(cd.getpId())) {
				cds.add(cd);
				return true ;
			}
		}
		// 此处查询数据库中当前层级总条数
		return false;
	}
	
	/**
	 * 获得第一级菜单，并递归获取整个树
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List getDeptGwUserTree() {
		List<Dept> deptList = cdxxDao.getAllDept();
		
		List<Gw> gwList = cdxxDao.getAllGw();
		
		List<User> userList = cdxxDao.getAllUser();

		
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();//作为树的载体
		
		for(Dept d : deptList){
			Map<String, Object> objD = StringUtil.transDeptToTree(d);// 首先生成本菜单数据
			for(Gw g : gwList){
				if (StringUtils.equals(g.getDeptId(), d.getId())) {// 如果有子菜单
					Map<String, Object> objG = StringUtil.transGwToTree(g);// 首先生成本菜单数据
					for(User u : userList){
						if (StringUtils.equals(g.getId(), u.getGwId())) {// 如果有子菜单
							Map<String, Object> objU = StringUtil.transUserToTree(u);// 首先生成本菜单数据
							objG.put("children",objU);// 递归生成子菜单数据 并与父菜单关联
						}
					}
					objD.put("children",objG);// 递归生成子菜单数据 并与父菜单关联
				}
			}
			tree.add(objD);// 将本菜单放入载体
		}
			
		return tree;
	}
}
