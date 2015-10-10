/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.web;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * <pre>
 *      Title: 定义树节点的类,实现org.richfaces.model.TreeNode接口,并增加了一些属性。
 *      Description: 定义树节点的类。
 * </pre>
 * 
 * @param <T> ValueObject
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 *      修改记录
 *      修改后版本:  修改人：guanyuan_gong 修改日期:2010-11-5下午01:18:57修改内容:
 * </pre>
 */
public class TreeNodeEx<T> implements TreeNode<T> {

	private static final long serialVersionUID = -6658651540679600144L;
	// 树结点的ID，保持唯一性.
	private String id;
	// 树结点的显示文本
	private String text;
	// 树结点的提示信息
	private String qtip;
	// 树结点的选中标志
	private Boolean checked;
	// 结点URL地址
	private String url;
	// 结点图标图片路径
	private String icon;
	
	private String parentId;
	
	private boolean leaf = false;

	private T data; //节点的里对象
	private TreeNode<T> parent;
	private Map<Object, TreeNode<T>> childrenMap;

	/**
	 * 默认的构造方法。
	 */
	public TreeNodeEx() {
		this.childrenMap = new LinkedHashMap<Object, TreeNode<T>>();
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.TreeNode#getData()
	 */
	@Override
	public T getData() {
		return this.data;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.TreeNode#getChild(java.lang.Object)
	 */
	@Override
	public TreeNode<T> getChild(Object identifier) {
		return ((TreeNode<T>) this.childrenMap.get(identifier));
	}
	
	/**
	 * 增加孩子节点。
	 * 
	 * @param identifier 可以指定为新增的子节点的id;
	 * @param child 为新增的子节点
	 */
	public void addChild(Object identifier, TreeNode<T> child) {
		child.setParent(this);
		this.childrenMap.put(identifier, child);
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.TreeNode#removeChild(java.lang.Object)
	 */
	@Override
	public void removeChild(Object identifier) {
		TreeNode<T> treeNode = (TreeNode<T>) this.childrenMap.remove(identifier);
		if (treeNode != null) {
			treeNode.setParent(null);
		}
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.TreeNode#setData(T)
	 */
	@Override
	public void setData(T data) {
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.TreeNode#getParent()
	 */
	@Override
	public TreeNode<T> getParent() {
		return this.parent;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.TreeNode#setParent(org.richfaces.model.TreeNode<T>)
	 */
	@Override
	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.TreeNode#Iterator(java.util.Map.Entry<Object, TreeNode<T>>)
	 */
	@Override
	public Iterator<Map.Entry<Object, TreeNode<T>>> getChildren() {
		return this.childrenMap.entrySet().iterator();
	}

	/**
	 * 是否为叶节点。
	 * 
	 * @return boolean
	 */
	public boolean isLeaf() {
		return this.childrenMap.isEmpty();
	}

	/**
	 * 构建器。
	 * 
	 * @param id String
	 * @param text String
	 * @param qtip String
	 */
	public TreeNodeEx(String id, String text, String qtip) {
		this.childrenMap = new LinkedHashMap<Object, TreeNode<T>>();
		this.id = id;
		this.text = text;
		this.qtip = qtip;
		
	}

	/**
	 * 构建器。
	 * 
	 * @param id String
	 * @param text String
	 * @param qtip String
	 * @param data T
	 */
	public TreeNodeEx(String id, String text, String qtip, T data) {
		this.childrenMap = new LinkedHashMap<Object, TreeNode<T>>();
		this.id = id;
		this.text = text;
		this.qtip = qtip;
		this.data = data;
	}

	/**
	 * 构建器。
	 * 
	 * @param id String
	 * @param text String
	 * @param qtip String
	 * @param url String
	 */
	public TreeNodeEx(String id, String text, String qtip, String url) {
		this.childrenMap = new LinkedHashMap<Object, TreeNode<T>>();
		this.id = id;
		this.text = text;
		this.qtip = qtip;
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
        if(null == obj) {
			return false;
		}
        if(!(obj instanceof TreeNodeEx)) {
			return false;
		}
        TreeNodeEx node = (TreeNodeEx)obj;
        if(null == getId() || null == node.getId()) {
			return false;
		} else {
			return getId().equals(node.getId());
		}
    
	}

   /**
    * 获取id。
    * 
    * @return String
    */
	public String getId() {
		return id;
	}

	/**
	 * 设置id。
	 * 
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取text。
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置text。
	 * 
	 * @param text String
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 获取qtip。
	 * 
	 * @return String
	 */
	public String getQtip() {
		return qtip;
	}

	/**
	 * 设置qtip。
	 * 
	 * @param qtip String
	 */
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	/**
	 * 获取checked。
	 * 
	 * @return Boolean
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * 设置checked。
	 * 
	 * @param checked Boolean
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/**
	 * 获取url。
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置url。
	 * 
	 * @param url String
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取图标。
	 * 
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置图标。
	 * 
	 * @param icon String
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取parentId。
	 * 
	 * @return String
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置parentId。
	 * 
	 * @param parentId String
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 设置叶子。
	 * 
	 * @param leaf boolean
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

}
