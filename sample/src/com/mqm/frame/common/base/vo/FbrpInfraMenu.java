/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.base.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.util.IFbrpTree;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpInfraMenu extends ValueObject implements GrantedAuthority,IFbrpTree,Comparable<FbrpInfraMenu> {
	
	private static final long serialVersionUID = 8058704869922130976L;

	private String name;

	private String url;

	private String parentId;

	private String parentName;
	
	private String parentSjssjgDm;

	private Integer sortNo;

	private String imageUrl;

	private String contextRelative;

	private String target;

	private String visible;

	private String tooltip;
	
	//统计数
	private Long times;
	//修改人名称
	private String userName;

	// 父亲菜单
	private FbrpInfraMenu parentVO = null;
	// 儿子菜单列表
	private List childrenVOList = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Transient
	@Override
	public String getAuthority() {
		return this.getId();
	}

	/**
	 * 返回 name。
	 * 
	 * @return 返回 name。
	 */
	@Column(name="name")
	public String getName() {
		return name;
	}

	/**
	 * 设置 name。
	 * 
	 * @param name
	 *            设置 name。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 url。
	 * 
	 * @return 返回 url。
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 url。
	 * 
	 * @param url
	 *            设置 url。
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回 parentId。
	 * 
	 * @return 返回 parentId。
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 *  设置 parentId。
	 * 
	 * @param parentId
	 *            设置 parentId。
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 返回 parentName。
	 * 
	 * @return 返回 parentName。
	 */
	@Transient
	public String getParentName() {
		return parentName;
	}

	/**
	 * 设置 parentName。
	 * 
	 * @param parentName
	 *            设置 parentName。
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

    /**
     * 获取parentSjssjgDm。
     * 
     * @return parentSjssjgDm
     */
	@Transient
	public String getParentSjssjgDm() {
		return parentSjssjgDm;
	}

	/**
	 * 设置parentSjssjgDm。
	 * 
	 * @param parentSjssjgDm String
	 */
	public void setParentSjssjgDm(String parentSjssjgDm) {
		this.parentSjssjgDm = parentSjssjgDm;
	}

	/**
	 *  返回 sortNo。
	 * 
	 * @return 返回 sortNo。
	 */
	public Integer getSortNo() {
		return sortNo;
	}

	/**
	 * 设置 sortNo。
	 * 
	 * @param sortNo
	 *            设置 sortNo。
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * 返回 imageUrl。
	 * 
	 * @return 返回 imageUrl。
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 设置 imageUrl。
	 * 
	 * @param imageUrl
	 *            设置 imageUrl。
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 返回 contextRelative。
	 * 
	 * @return 返回 contextRelative。
	 */
	public String getContextRelative() {
		return contextRelative;
	}

	/**
	 * 设置 contextRelative。
	 * 
	 * @param contextRelative
	 *            设置 contextRelative。
	 */
	public void setContextRelative(String contextRelative) {
		this.contextRelative = contextRelative;
	}

	/**
	 * 返回 target。
	 * 
	 * @return 返回 target。
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 设置 target。
	 * 
	 * @param target
	 *            设置 target。
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 返回 visible。
	 * 
	 * @return 返回 visible。
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * 设置 visible。
	 * 
	 * @param visible
	 *            设置 visible。
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * 设置tooltip。
	 * 
	 * @param tooltip String
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	/**
	 * 获取tooltip。
	 * 
	 * @return String
	 */
	public String getTooltip() {
		return tooltip;
	}
	
	/**
	 * 获取times。
	 * 
	 * @return Long
	 */
	@Transient
	public Long getTimes() {
		return times;
	}

	/**
	 * 设置times。
	 * 
	 * @param times Long
	 */
	public void setTimes(Long times) {
		this.times = times;
	}

	/**
	 * 获取userName。
	 * 
	 * @return String
	 */
	@Transient
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置userName。
	 * 
	 * @param userName String
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 返回 parentVO。
	 * 
	 * @return 返回 parentVO。
	 */
	@Transient
	public FbrpInfraMenu getParentVO() {
		return parentVO;
	}

	/**
	 * 设置 parentVO。
	 * 
	 * @param parentVO
	 *            设置 parentVO。
	 */
	public void setParentVO(FbrpInfraMenu parentVO) {
		this.parentVO = parentVO;
	}

	/**
	 * 返回 childrenVOList。
	 * 
	 * @return 返回 childrenVOList。
	 */
	@Transient
	public List getChildrenVOList() {
		return childrenVOList;
	}

	/**
	 * 增加孩子。
	 * 
	 * @param bam FbrpInfraMenu
	 */
	public void addChildren(FbrpInfraMenu bam) {
		if (childrenVOList == null) {
			childrenVOList = new ArrayList();
		}
		childrenVOList.add(bam);
	}

	/**
	 *  设置 childrenVOList。
	 * 
	 * @param childrenVOList
	 *            设置 childrenVOList。
	 */
	public void setChildrenVOList(List childrenVOList) {
		this.childrenVOList = childrenVOList;
	}

	@Override
	public boolean equals(Object obj) {
		FbrpInfraMenu menu = (FbrpInfraMenu) obj;
		if(super.getId().equals(menu.getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public int compareTo(FbrpInfraMenu o) {
		// TODO Auto-generated method stub
		if(super.getId().equals(o.getId())) {
			return 0;
		} else {
			return 1;
		}
	}

}