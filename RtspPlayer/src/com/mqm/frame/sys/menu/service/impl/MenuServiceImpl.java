package com.mqm.frame.sys.menu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.menu.dao.IMenuDao;
import com.mqm.frame.sys.menu.service.IMenuService;
import com.mqm.frame.sys.menu.vo.JsonTree;
import com.mqm.frame.sys.menu.vo.MenuVO;

public class MenuServiceImpl implements IMenuService<MenuVO> {

	private IMenuDao menuDao;

	/**
	 * 查询用户菜单
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List findMenuByUserId(String userId) {
		List allNodes = menuDao.findMenuByUserId(userId);
		return allNodes;
	}
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(MenuVO t) {
		menuDao.insert("insert", t);
	}

	@Override
	public void deleteById(String id) {
		menuDao.deleteById("delete", id);
	}

	@Override
	public void update(MenuVO t) {
		menuDao.update("update", t);
	}

	@Override
	public MenuVO findById(String id) {
		return (MenuVO)menuDao.findById("findById", id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(MenuVO t) {
		return menuDao.findList("findList", t);
	}

	@Override
	public List findPageList(MenuVO t, int pageIndex, int pageSize) {
		return menuDao.findPageList("findList", t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(MenuVO t) {
		return menuDao.findListCount("findList", t);
	}
	
	@Override
	public List findAll() {
		return menuDao.findAll("findAll");
	}
	
	/**
	 * 获得整个菜单的树结构
	 * @return
	 */
	@Override
	public List getTree() {
		List<MenuVO> menus = this.findAll();
		
		Collections.sort(menus, new Comparator<MenuVO>() {
			@Override
			public int compare(MenuVO o1, MenuVO o2) {
				return o1.getSortNo() - o2.getSortNo();
			}
		});
		//BaseConstants.TREE_HAS_ROOT 需要增加虚拟根节点
		List<JsonTree> jsonTrees = new ArrayList<JsonTree>();

		for (MenuVO vo : menus) {
			JsonTree jsonTree = new JsonTree();
			jsonTree.setId(vo.getId());
			jsonTree.setpId(vo.getpId());
			jsonTree.setName(vo.getCnName());

			if ("0".equals(jsonTree.getpId())) {
				jsonTree.setOpen(true);
			}
			jsonTree.getExts().put("url", vo.getUrl());
			jsonTree.getExts().put("icon", vo.getImageUrl());
			jsonTree.getExts().put("cnName", vo.getCnName());
			jsonTree.getExts().put("enName", vo.getEnName());
			jsonTree.getExts().put("sortNo", String.valueOf(vo.getSortNo()));
			jsonTree.getExts().put("cdDm", vo.getCdDm());
			jsonTree.getExts().put("leaf", vo.getLeaf());
			jsonTrees.add(jsonTree);
		}
		return jsonTrees;
	}
	
	/**
	 * 获得菜单的name和Id列表用于修改菜单使用
	 * @return
	 */
	@Override
	public List getPTree() {
		List<MenuVO> menus = this.findAll();
		//BaseConstants.TREE_HAS_ROOT 需要增加虚拟根节点
		List<Map<String, Object>> pJsonTree = new ArrayList<Map<String, Object>>();
		for (MenuVO vo : menus) {
			Map<String, Object> hashMap = new HashMap<String,Object>();
			hashMap.put("id", vo.getId());
			hashMap.put("name", vo.getCnName());

			pJsonTree.add(hashMap);
		}
		return pJsonTree;
	}
	
	/**
	 * 获得菜单导航列表
	 * @return
	 */
	@Override
	public Map<String ,Object> getMenuTree(boolean isAdmin,String userId) {
		List<MenuVO> voList = null;
		if (isAdmin) {//管理员加载所有的
			voList = this.findAll();
		} else {
			voList = this.findMenuByUserId(userId);
		}
		
		Collections.sort(voList, new Comparator<MenuVO>() {
			@Override
			public int compare(MenuVO o1, MenuVO o2) {
				return o1.getSortNo() - o2.getSortNo();
			}
		});

		List<JsonTree> jsonTrees = new ArrayList<JsonTree>();

		for (MenuVO vo : voList) {
			JsonTree jsonTree = new JsonTree();
			jsonTree.setId(vo.getId());
			jsonTree.setpId(vo.getpId());
			jsonTree.setName(vo.getCnName());

			if ("E1801EBE06D211E588790023540C477B".equals(jsonTree.getpId())) {
				jsonTree.setOpen(true);
			}
			jsonTree.getExts().put("url", vo.getUrl());
			jsonTree.getExts().put("icon", vo.getImageUrl());
			jsonTrees.add(jsonTree);
		}

		// TODO luxiaocheng 需要优化的代码
		HashMap<String, JsonTree> map = new HashMap<String, JsonTree>();
		HashMap<String, List<JsonTree>> collection = new HashMap<String, List<JsonTree>>();
		List<JsonTree> topList = new ArrayList<JsonTree>();
		for (JsonTree vo : jsonTrees) {
			map.put(vo.getId(), vo);// 非根节点
			if ("E1801EBE06D211E588790023540C477B".equals(vo.getpId())) {// 根节点
				collection.put(vo.getId(), new ArrayList<JsonTree>());
				topList.add(vo);// 根节点
			}
		}
		for (JsonTree vo : jsonTrees) {
			String p = vo.getpId();
			if (!"E1801EBE06D211E588790023540C477B".equals(p)) {// 非根节点
				int safe = 0;
				while (!collection.containsKey(p) && map.containsKey(p)) {// 非根节点包含，根节点不包含
					p = map.get(p).getpId();
					if (safe++ > 30) {
						break;
					}
				}
				if (collection.containsKey(p)) {
					collection.get(p).add(vo);
				}
			}
		}
		Map<String ,Object> hashMap = new HashMap<String ,Object>();
		hashMap.put("treeData", JSONArray.fromObject(collection).toString());
		hashMap.put("top", JSONArray.fromObject(topList).toString());
		return hashMap;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
}
