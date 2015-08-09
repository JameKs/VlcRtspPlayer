package com.mqm.frame.common.login.service.impl;

import com.mqm.frame.common.login.service.ILoginService;
import com.mqm.frame.sys.user.dao.IUserDao;
import com.mqm.frame.sys.user.vo.User;

public class LoginServiceImpl implements ILoginService {

	private IUserDao userDao;

	public User login(User user) {
		User sessionUser = userDao.login(user);
		return sessionUser;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
