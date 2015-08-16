package com.mqm.frame.sys.user.service.impl;

import java.util.List;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.user.dao.IUserDao;
import com.mqm.frame.sys.user.service.IUserService;
import com.mqm.frame.sys.user.vo.User;

public class UserServiceImpl implements IUserService<User> {
	
	private IUserDao userDao;

	public User findByLoginId(String loginId) {
		return (User)userDao.findByLoginId("findByLoginId" , loginId);
	}
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(User t) {
		userDao.insert("insert", t);
	}

	@Override
	public void deleteById(String id) {
		userDao.deleteById("deleteById", id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		for(String id : ids){
			this.deleteById(id);
		}
	}

	@Override
	public void update(User t) {
		userDao.update("update", t);
	}

	@Override
	public User findById(String id) {
		return (User)userDao.findById("findById", id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(User t) {
		return userDao.findList("findList", t);
	}

	@Override
	public List findPageList(User t, int pageIndex, int pageSize) {
		return userDao.findPageList("findList", t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(User t) {
		return userDao.findListCount("findListCount", t);
	}
	
	@Override
	public List findAll() {
		return userDao.findAll("findAll");
	}

	/**
	 * @param ryxxDao the ryxxDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
