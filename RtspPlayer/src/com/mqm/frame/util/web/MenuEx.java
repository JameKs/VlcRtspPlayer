/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.web;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 动态菜单。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class MenuEx {

	private String id;// 菜单唯一编号
	private String name;// 菜单名称
	private String menuUrl;// 菜单url
	private String imageUrl;// 菜单图片
	private String parentId;// 菜单父编号

	private MenuEx parentMenu;// 父菜单
	private List<MenuEx> childrenList;// 子菜单

	/**
	 * 增加子节点。
	 * 
	 * @param menuEx
	 *            MenuEx
	 */
	public void addChildren(MenuEx menuEx) {
		if (childrenList == null) {
			childrenList = new ArrayList<MenuEx>();
		}
		childrenList.add(menuEx);
	}

	/**
	 * 判断是否有子菜单。
	 * 
	 * @return boolean
	 */
	public boolean haveSubMenu() {
		if (childrenList != null && childrenList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 返回 id。
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id。
	 * 
	 * @param id
	 *            String
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 name。
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name。
	 * 
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 menuUrl。
	 * 
	 * @return String
	 */
	public String getMenuUrl() {
		return menuUrl;
	}

	/**
	 * 设置 menuUrl。
	 * 
	 * @param menuUrl
	 *            String
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * 返回 imageUrl。
	 * 
	 * @return String
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 设置 imageUrl。
	 * 
	 * @param imageUrl
	 *            String
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 返回 parentId。
	 * 
	 * @return String
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置 parentId。
	 * 
	 * @param parentId
	 *            String
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 返回 parentMenu。
	 * 
	 * @return MenuEx
	 */
	public MenuEx getParentMenu() {
		return parentMenu;
	}

	/**
	 * 设置 parentMenu。
	 * 
	 * @param parentMenu
	 *            MenuEx
	 */
	public void setParentMenu(MenuEx parentMenu) {
		this.parentMenu = parentMenu;
	}

	/**
	 * 返回 childernList。
	 * 
	 * @return List<MenuEx>
	 */
	public List<MenuEx> getChildrenList() {
		return childrenList;
	}

	/**
	 * 设置 childernList。
	 * 
	 * @param childrenList
	 *            List<MenuEx>
	 */
	public void setChildrenList(List<MenuEx> childrenList) {
		this.childrenList = childrenList;
	}

	/**
	 * 获取html。
	 * 
	 * @param top boolean
	 * 
	 * @return String
	 */
	public String getHtml(boolean top){
		StringBuffer sb = new StringBuffer();
		boolean hasChild = this.getChildrenList()!=null&&!this.getChildrenList().isEmpty();
		if(top){
			sb.append("<a tabindex=\"0\" href=\"javascript:void(0)\" class=\"fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all topMenuItem\" id=\"").append(this.getId()).append("\">");
			sb.append("<span class=\"ui-icon ui-icon-triangle-1-s\"></span>").append(this.getName());
			sb.append("</a>");
			sb.append("<div class=\"hidden\">");
			if(hasChild){
				sb.append("<ul>");
				for (MenuEx menu : this.getChildrenList()) {
					sb.append(menu.getHtml(false));
				}
				sb.append("</ul>");
			}
			sb.append("</div>");
		}else{
			if(hasChild){
				sb.append("<li><a href=\"#\">").append(this.getName()).append("</a>");
			}else{
				sb.append("<li><a href=\"javascript:openTab('").append(this.id).append("','").append(this.name).append("','").append(this.menuUrl).append("','").append(this.imageUrl).append("')\">").append(this.getName()).append("</a>");
			}
			if(hasChild){
				sb.append("<ul>");
				for (MenuEx menu : this.getChildrenList()) {
					sb.append(menu.getHtml(false));
				}
				sb.append("</ul>");
			}

			sb.append("</li>");
		}
		
		
		
		
		return sb.toString();
	}
}
