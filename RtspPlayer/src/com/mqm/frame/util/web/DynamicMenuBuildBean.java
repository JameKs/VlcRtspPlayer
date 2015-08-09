/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 动态菜单。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DynamicMenuBuildBean {

	private static final Log log = LogFactory.getLog(DynamicMenuBuildBean.class);
	
	
	/**
	 * 初始化。
	 * 
	 * @param menuList List<MenuEx>
	 * 
	 * @return List<MenuEx>
	 */
	public List<MenuEx> initMenuRelation(List<MenuEx> menuList) {
		//先找出根节点，并设置好父子关系
		List<MenuEx> rootList = new ArrayList<MenuEx>();
		for (int i = 0; i < menuList.size(); i++) {
			MenuEx menu = (MenuEx)menuList.get(i);
			//循环parent_code为空的节点，也就是根节点
			if(menu.getParentId()==null || "0".equals(menu.getParentId())||"".equals(menu.getParentId().trim())) {
				rootList.add(menu);
			}
			//循环设置菜单之间的父子关系
			for (int j = 0;j < menuList.size(); j++) {
				MenuEx menu1 = menuList.get(j);
				if(i==j){
					continue;
				}
				//设置父子关系
				if(menu1.getParentId()!=null && menu1.getParentId().equals(menu.getId())) {
					menu1.setParentMenu(menu);
					menu.addChildren(menu1);
				}
			}
		}
		return rootList;
	}
	
	
	
	
	
}
