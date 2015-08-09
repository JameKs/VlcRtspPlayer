package com.mqm.frame.sys.user.service.impl;

import java.util.List;

import com.mqm.frame.sys.user.dao.IUserDao;
import com.mqm.frame.sys.user.service.IUserService;
import com.mqm.frame.sys.user.vo.User;

public class UserServiceImpl implements IUserService {
	
	private IUserDao userDao;

	public void insert(User user) {
		userDao.insert(user);
	}

	public void delete(String id) {
		userDao.delete(id);
	}

	public void update(User user) {
		userDao.update(user);
	}

	public User findById(String id) {
		return userDao.findById(id);
	}
	
	public User findByLoginId(String loginId) {
		return userDao.findByLoginId(loginId);
	}

	public List findList(User user,int pageIndex, int pageSize) {
		return userDao.findList(user, pageIndex, pageSize);
	}
	
	public long findListCount(User user) {
		return userDao.findListCount(user);
	}

	/**
	 * @param ryxxDao the ryxxDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
