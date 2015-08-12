package com.mqm.frame.sys.user.service.impl;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.user.dao.IUserDao;
import com.mqm.frame.sys.user.service.IUserService;
import com.mqm.frame.sys.user.vo.User;

public class UserServiceImpl extends DefaultServiceImpl<User> implements IUserService<User> {
	
	private IUserDao userDao;

	public User findByLoginId(String loginId) {
		return (User)userDao.findByLoginId("findByLoginId" , loginId);
	}

	/**
	 * @param ryxxDao the ryxxDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
