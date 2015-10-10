package com.mqm.frame.sys.menu.dao;

import java.util.List;
import java.util.Map;

import com.mqm.frame.common.IDefaultDao;

public interface IMenuDao<T> extends IDefaultDao<T> {

	public List findMenuByUserId(String userId);
	
	public List findByParentId(String pId);
	
	public List getNodesByIdAndUserId(Map map);

	public List getChildrenNodes(String pId);

	public long getChildrenNodesCount(String pId);

	public List getWdscjByUserId(Map map);
	
	public List getAllDept();
	
	public List getAllGw();
	
	public List getAllUser();

}
