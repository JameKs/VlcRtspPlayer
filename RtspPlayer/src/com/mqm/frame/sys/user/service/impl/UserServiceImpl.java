package com.mqm.frame.sys.user.service.impl;

import java.util.List;

import com.mqm.frame.sys.user.dao.IUserDao;
import com.mqm.frame.sys.user.service.IUserService;
import com.mqm.frame.sys.user.vo.User;

public class UserServiceImpl implements IUserService<User> {
	
	private IUserDao<User> userDao;

	public User findByLoginId(String loginId) {
		return (User)userDao.findByLoginId(loginId);
	}
	
	@Override
	public void insert(User t) {
		userDao.insert(t);
	}

	@Override
	public void deleteById(String id) {
		userDao.deleteById(id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		for(String id : ids){
			this.deleteById(id);
		}
	}

	@Override
	public void update(User t) {
		userDao.update(t);
	}

	@Override
	public User findById(String id) {
		return (User)userDao.findById(id);
	}

	@Override
	public List<User> findList(User t) {
		return userDao.findList(t);
	}

	@Override
	public List<User> findListPage(User t, int pageIndex, int pageSize) {
		return userDao.findListPage(t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(User t) {
		return userDao.findListCount(t);
	}
	
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	 * @param ryxxDao the ryxxDao to set
	 */
	public void setUserDao(IUserDao<User> userDao) {
		this.userDao = userDao;
	}

}
