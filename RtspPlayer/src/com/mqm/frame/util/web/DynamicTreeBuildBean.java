/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.util.IFbrpTree;

/**
 * <pre>
 *      Title: 动态生成树。
 *      Description: 根据节点列表，动态生成树。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 *      修改记录
 *      修改后版本:  修改人：guanyuan_gong 修改日期:2010-11-5下午01:18:57修改内容:
 * </pre>
 */
public class DynamicTreeBuildBean {

	private static final Log log = LogFactory.getLog(DynamicTreeBuildBean.class);

	/**
	 * 获取树节点。<br>
	 * 处理流程：<br>
	 * 1、根据nodeList进行自动数据重组，形成树形结构 <br>
	 * 2、默认跟节点是0<br>
	 * 3、nodeList里一定要有id为0的treeNode对象。
	 * 
	 * @param nodeList
	 *            要显示的树节点集合
	 * @return TreeNode 返回树节点
	 */
	public TreeNodeEx getTree(List<TreeNodeEx> nodeList) {
		return this.getTree(nodeList, "0");
	}

	/**
	 * 获取树节点。<br>
	 * 处理流程：<br>
	 * 1、根据nodelList进行自动数据重组，形成树形结构。 <br>
	 * 2、默认跟节点是rootNodeId。<br>
	 * 
	 * @param nodeList
	 *            要显示的树节点集合
	 * @param rootNodeId
	 *            树的根节点
	 * @return TreeNode 返回树节点
	 */
	public TreeNodeEx getTree(List<TreeNodeEx> nodeList, String rootNodeId) {
		log.debug("=====getTree()开始=====");
		if (nodeList == null || nodeList.size() < 1) {
			return null;
		}
		if (rootNodeId == null || rootNodeId.equals("")) {
			rootNodeId = "0";
		}

		TreeNodeEx node = null;
		TreeNodeEx parentnode = null;
		int rootIndex = -1;
		for (int i = 0; i < nodeList.size(); i++) {
			node = (TreeNodeEx) nodeList.get(i);
			if (rootNodeId.equals(node.getId())) {
				// 对根节点(identifier = 0)不做处理
				rootIndex = i;
				continue;
			}
			parentnode = new TreeNodeEx();
			parentnode.setId(node.getParentId());
			int j = nodeList.indexOf(parentnode); // 父结点的下标
			if (j == -1) {
				// 没有找到当前结点的父结点
				if(log.isDebugEnabled()){
					log.debug("结点id为" + node.getId() + "的结点没有父结点");
				}
			} else {
				// 将当前结点放到其父结点的子结点列表中
				((TreeNodeEx) nodeList.get(j)).addChild(node.getId(), node);
				((TreeNodeEx) nodeList.get(j)).setLeaf(false);
			}
		}
		// logger.debug("=====构建树结束=====");

		/* 返回根节点(id=0) */
		if (rootIndex == -1) {
			// logger.debug("没有找到根节点，无法创建树！");
			return null;
		} else {
			// logger.debug("=====getTree()结束=====");
			return (TreeNodeEx) nodeList.get(rootIndex);
		}
	}

	/**
	 * 传入实现IFbrpTree接口的VO集合，及顶级(可视)的虚拟根结点，构造树。
	 * 
	 * @param vos 实现IFbrpTree接口的VO集合
	 * @param top (可视的)顶级虚拟根结点，不可为null，并且准备好id及parentId的值，一般设置id为0，prarentId为-1即可
	 * 
	 * @return 返回树节点
	 */
	public  TreeNodeEx buildTree(List<? extends IFbrpTree> vos, IFbrpTree top) {
		if(top==null||top.getId()==null||"".equals(top.getId().trim())||top.getParentId()==null||"".equals(top.getParentId().trim())){
			throw new IllegalArgumentException("必须提供顶级点实体，并且准备好id及parentId的值");
		}
		List<TreeNodeEx> nodeList = new ArrayList<TreeNodeEx>();
		TreeNodeEx<IFbrpTree> rootNode = new TreeNodeEx<IFbrpTree>();
		rootNode.setId(top.getParentId());
		nodeList.add(rootNode);

		TreeNodeEx<IFbrpTree> topNode = new TreeNodeEx<IFbrpTree>();
		topNode.setId(top.getId());
		topNode.setText(top.getName());
		topNode.setParentId(top.getParentId());
		topNode.setData(top);
		nodeList.add(topNode);
		
		for (IFbrpTree vo : vos) {
			TreeNodeEx<IFbrpTree> resNode = new TreeNodeEx<IFbrpTree>();
			resNode.setId(vo.getId());
			String pId = vo.getParentId();
			resNode.setParentId((null == pId || "".equals(pId.trim())) ? top.getId() : pId);
			resNode.setData(vo);
			nodeList.add(resNode);
		}
		
		return getTree(nodeList,top.getParentId());
	}
	
}
