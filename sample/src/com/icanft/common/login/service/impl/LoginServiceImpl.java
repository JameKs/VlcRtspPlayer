package com.icanft.common.login.service.impl;

import com.icanft.common.login.service.ILoginService;
import com.icanft.xtgl.yhgl.dao.IUserDao;
import com.icanft.xtgl.yhgl.vo.User;

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
