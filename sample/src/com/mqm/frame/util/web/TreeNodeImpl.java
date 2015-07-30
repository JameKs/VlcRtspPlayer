package com.mqm.frame.util.web;
 
 import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
 
 /**
  * Default {@link TreeNode} implementation based on {@link LinkedHashMap} to preserve .
  * elements ordering
  * 
  * @author Nick Belaevski - nbelaevski@exadel.com
  * created 16.11.2006
  * @param <T> Object
  */
 public class TreeNodeImpl<T> implements TreeNode<T> {
 	
 	private static final long serialVersionUID = -5498990493803705085L;
 	private T data;
 	private TreeNode<T> parent;
 	
 	private Map<Object, TreeNode<T>> childrenMap = 
 		new LinkedHashMap<Object, TreeNode<T>>();
 	
 	/**
 	 * 获取data。
 	 * 
 	 * @return T
 	 */
 	public T getData() {
 		return data;
 	}
 
 	/**
 	 * 获取child。
 	 * 
 	 * @param identifier Object
 	 * 
 	 * @return TreeNode<T>
 	 */
 	public TreeNode<T> getChild(Object identifier) {
 		return (TreeNode<T>) childrenMap.get(identifier);
 	}
 
 	/**
 	 * 添加child。
 	 * 
 	 * @param identifier Object
 	 * 
 	 * @param child TreeNode<T>
 	 */
 	public void addChild(Object identifier, TreeNode<T> child) {
 		child.setParent(this);
 		childrenMap.put(identifier, child);
 	}
 
 	/**
 	 * 移出child。
 	 * 
 	 * @param identifier Object
 	 */
 	public void removeChild(Object identifier) {
 		TreeNode<T> treeNode = childrenMap.remove(identifier);
 		if (treeNode != null) {
 			treeNode.setParent(null);
 		}
 	}
 
 	/**
 	 * 设置data。
 	 * 
 	 * @param data T
 	 */
 	public void setData(T data) {
 		this.data = data;
 	}
 	
 	/**
 	 * 获取parent。
 	 * 
 	 * @return TreeNode<T>
 	 */
 	public TreeNode<T> getParent() {
 		return parent;
 	}
 	 
 	/**
 	 * 设置parent。
 	 * 
 	 * @param parent TreeNode<T>
 	 */
 	public void setParent(TreeNode<T> parent) {
 		this.parent = parent;
 	}
 
 	/**
 	 * 获取children。
 	 * 
 	 * @return Iterator<Map.Entry<Object, TreeNode<T>>>
 	 */
 	public Iterator<Map.Entry<Object, TreeNode<T>>> getChildren() {
 		return childrenMap.entrySet().iterator();
 	}
 
 	/**
 	 * 判断是否是叶子节点。
 	 * 
 	 * @return boolean
 	 */
 	public boolean isLeaf() {
 		return childrenMap.isEmpty();
 	}
 
 }