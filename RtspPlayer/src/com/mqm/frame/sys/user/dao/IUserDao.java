package com.mqm.frame.sys.user.dao;

import java.util.List;

import com.mqm.frame.sys.user.vo.User;

public interface IUserDao {

	public void insert(User user);

	public void delete(String id);

	public void update(User user);
	
	public User findById(String id);
	
	public User findByLoginId(String loginId);

	public List findList(User user, int pageIndex, int pageSize);
	
	public long findListCount(User user) ;
	
	public User login(User user);

}
