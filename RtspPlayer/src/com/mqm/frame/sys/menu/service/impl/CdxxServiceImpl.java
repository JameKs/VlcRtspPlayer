package com.mqm.frame.sys.menu.service.impl;

import java.util.List;

import com.mqm.frame.sys.menu.dao.ICdxxDao;
import com.mqm.frame.sys.menu.service.ICdxxService;
import com.mqm.frame.sys.menu.vo.Cdxx;

public class CdxxServiceImpl implements ICdxxService {

	private ICdxxDao cdxxDao;

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#add(com.icanft.cdgl.vo.Cdxx)
	 */
	@Override
	public void insert(Cdxx cdxx) {
		cdxxDao.insert(cdxx);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#deleteById(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		cdxxDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#update(com.icanft.cdgl.vo.Cdxx)
	 */
	@Override
	public void update(Cdxx cdxx) {
		cdxxDao.update(cdxx);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#findById(java.lang.String)
	 */
	@Override
	public Cdxx findById(String id) {
		return cdxxDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.icanft.cdgl.service.impl.ICdxxService#findAll()
	 */
	@Override
	public List findAll(String hasRoot) {
		return cdxxDao.findAll(hasRoot);
	}
	
	/**
	 * 查询用户菜单
	 * 
	 * @param nodes
	 * @param roles
	 * @return
	 */
	@Override
	public List findAllUserMenu(String userId) {
		List allNodes = cdxxDao.findAllUserMenu(userId);
		return allNodes;
	}

	@Override
	public void setCdxxDao(ICdxxDao cdxxDao) {
		this.cdxxDao = cdxxDao;
	}
	
//	public List<Cdxx> getChildrenNodes(String pId, List allNodes) {
//		List<Cdxx> cds = new ArrayList<Cdxx>();
//		Iterator<Cdxx> it = allNodes.iterator();
//		while (it.hasNext()) {
//			Cdxx cd = it.next();
//			if (pId.equals(cd.getpId())) {
//				cds.add(cd);
//			}
//		}
//		return cds;
//	}
//	
//	public boolean hasChildren(String pId, List allNodes) {
//
//		List<Cdxx> cds = new ArrayList<Cdxx>();
//		Iterator<Cdxx> it = allNodes.iterator();
//		while (it.hasNext()) {
//			Cdxx cd = it.next();
//			if (pId.equals(cd.getpId())) {
//				cds.add(cd);
//				return true ;
//			}
//		}
//		// 此处查询数据库中当前层级总条数
//		return false;
//	}
	
}
