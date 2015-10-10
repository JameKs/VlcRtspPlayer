/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.acl.extendpoint.IGrantExtendPoint;
import com.mqm.frame.security.acl.extendpoint.IGrantShowShape;
import com.mqm.frame.util.IFbrpTree;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.web.JsonTreeVo;
import com.mqm.frame.util.web.TreeNode;

/**
 * 
 * <pre>
 *        授权页面的树形展现形式。
 * </pre>
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class GrantTree extends FbrpBaseController implements IGrantShowShape {

	private static final Log log = LogFactory.getLog(GrantTree.class);

	private IGrantExtendPoint grantExtendPoint;

	private List allrolevalues;

	private List<ValueObject> selvalueTree;

	private TreeNode<ValueObject> choosetree;

	private List principalids;

	private Object jsonValue;

	private Class<? extends ValueObject> clazz;

	/**
	 * 自定义构造器。
	 * 
	 * @param clazz
	 *            Class<? extends ValueObject>
	 */
	public GrantTree(Class<? extends ValueObject> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Class<? extends ValueObject> getModelClass() {
		return this.clazz;
	}

	@Override
	public void setGrantExtendpoint(IGrantExtendPoint grantExtendPoint, boolean isEdit) {
		this.grantExtendPoint = grantExtendPoint;
		if (isEdit) {
			this.allrolevalues = grantExtendPoint.getAll();
		} else {
			this.allrolevalues = new ArrayList();
		}
		this.initselMenuTree();
	}

	@Override
	public void init(List principalids, boolean isEdit) {
		this.principalids = principalids;
		if (isEdit) {
			this.initMenuTree();
		} else {
			this.allrolevalues = this.grantExtendPoint.getWith(principalids);
			this.initMenuTree();
		}

	}

	private void initselMenuTree() {
		List<IFbrpTree> list = allrolevalues;
		List<JsonTreeVo> jsonTrees = new ArrayList<JsonTreeVo>();
		for (IFbrpTree tree : list) {
			JsonTreeVo jsonTree = new JsonTreeVo();
			jsonTree.setId(tree.getId());
			jsonTree.setName(tree.getName());
			jsonTree.setClick("javascript:fillPrincipalList('" + tree.getId() + "');");
			if (tree.getParentId() == null) {
				jsonTree.setpId("0");
			} else {
				jsonTree.setpId(tree.getParentId());
			}

			jsonTrees.add(jsonTree);
		}
			this.jsonValue = jsonTrees;

	}

	private void initMenuTree() {
		List<IFbrpTree> list = allrolevalues;
		List<JsonTreeVo> jsonTrees = new ArrayList<JsonTreeVo>();
		selvalueTree = this.grantExtendPoint.getWith(principalids);
		for (IFbrpTree tree : list) {
			JsonTreeVo jsonTree = new JsonTreeVo();
			jsonTree.setId(tree.getId());
			jsonTree.setName(tree.getName());
			if (tree.getParentId() == null) {
				jsonTree.setpId("0");
			} else {
				jsonTree.setpId(tree.getParentId());
			}
			for (ValueObject obj : selvalueTree) {
				if (obj.getId().equals(tree.getId())) {
					jsonTree.setChecked(true);
				}
			}
			jsonTrees.add(jsonTree);
		}
	
			this.jsonValue = jsonTrees;

	}

	@Override
	public void updateGranted() {
		if (principalids == null || principalids.size() < 1) {
			this.exceptionMsg.setMainMsg("请选择一个授权主体!");
			return;
		}
		List<ValueObject> selValues = new ArrayList<ValueObject>();
		this.grantExtendPoint.updateGranted(selValues, selvalueTree, (String) principalids.get(0));
		this.exceptionMsg.setMainMsg("授权成功!");
	}

	/**
	 * 获取Contain。
	 * 
	 * @param object
	 *            ValueObject
	 * @param list
	 *            List<ValueObject>
	 * 
	 * @return int
	 */
	public int isContain(ValueObject object, List<ValueObject> list) {
		if (list == null || list.isEmpty()) {
			return -1;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(object.getId())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 获取choosetree。
	 * 
	 * @return TreeNode<ValueObject>
	 */
	public TreeNode<ValueObject> getChoosetree() {
		return choosetree;
	}

	/**
	 * 获取allrolevalues。
	 * 
	 * @return List
	 */
	public List getAllrolevalues() {
		return allrolevalues;
	}

	/**
	 * 获取selvalueTree。
	 * 
	 * @return List
	 */
	public List getSelvalueTree() {
		return selvalueTree;
	}

	/**
	 * 获取getJsonValue。
	 * 
	 * @return String
	 */
	public Object getJsonValue() {
		return jsonValue;
	}

	/**
	 * 设置JsonValue。
	 * 
	 * @param jsonValue
	 *            String
	 */
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	/**
	 * 
	 * <pre>
	 * 程序的中文名称。
	 * </pre>
	 * 
	 * @author mmr mmr@foresee.cn
	 * @version 1.00.00
	 * 
	 *          <pre>
	 * 修改记录
	 *    修改后版本:     修改人：  修改日期:     修改内容:
	 * </pre>
	 */
	public class StubfbrpTree extends ValueObject implements IFbrpTree {

		private static final long serialVersionUID = 3730013442141311174L;

		private String imageUrl = "/pages/fbrp/images/tree/iconFolder.gif";

		@Override
		public String getId() {
			return "0";
		}

		@Override
		public String getName() {
			return grantExtendPoint.getGrantName();
		}

		@Override
		public String getParentId() {
			return "-1";
		}

		/**
		 * 获取是否选中。
		 * 
		 * @return boolean
		 */
		public boolean getChecked() {
			return true;
		}

		/**
		 * 获取imageUrl。
		 * 
		 * @return String
		 */
		public String getImageUrl() {
			return imageUrl;
		}

		/**
		 * 设置imageUrl。
		 * 
		 * @param imageUrl
		 *            String
		 */
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

	}

	@Override
	public String getType() {
		return BaseConstants.GRANT_TYPE_TREE;
	}

	@Override
	public IGrantExtendPoint getGrantExtendPoint() {
		return this.grantExtendPoint;
	}

	@Override
	public void reset() {
		if (allrolevalues != null) {
			for (int i = 0; i < allrolevalues.size(); i++) {
				ValueObject object = (ValueObject) allrolevalues.get(i);
				// object.setChecked(false);
			}
		}
	}

	@Override
	public Object[] getResult() {
		Object[] obj = { this.jsonValue };
		return obj;
	}

	@Override
	public int updateGranted(String roleId, String[] ids) {
		if (roleId == null || "".equals(roleId)) {
			return 0;
		}
		List<ValueObject> selValues = new ArrayList<ValueObject>();
		try {
			for (String id : ids) {
				ValueObject instance = this.clazz.newInstance();
				instance.setId(id);
				selValues.add(instance);
			}
		} catch (InstantiationException e) {
			log.error("", e);
		} catch (IllegalAccessException e) {
			log.error("", e);
		}
		List selvalueTree = this.grantExtendPoint.getAllByPrincipals(Arrays.asList(roleId));
		this.grantExtendPoint.updateGranted(selValues, selvalueTree, roleId);
		return 1;
	}

}
