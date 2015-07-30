package com.icanft.cdgl.dao;

import java.util.List;
import java.util.Map;

import com.icanft.cdgl.vo.Cdxx;

public interface ICdxxDao {

	public void insert(Cdxx cdxx);

	public void delete(String id);

	public void update(Cdxx cdxx);

	public Cdxx findById(String id);

	public List findByParentId(String pId);

	public List findAll(String hasRoot);
	
	public List findAllUserMenu(String userId);
	
	public List getNodesByIdAndUserId(Map map);

	public List getChildrenNodes(String pId);

	public long getChildrenNodesCount(String pId);

	public List getWdscjByUserId(Map map);
	
	public List getAllDept();
	
	public List getAllGw();
	
	public List getAllUser();

}
