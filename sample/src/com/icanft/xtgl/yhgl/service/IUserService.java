package com.icanft.xtgl.yhgl.service;

import java.util.List;

import com.icanft.xtgl.yhgl.vo.User;

public interface IUserService {

	public void insert(User user);

	public void delete(String id);

	public void update(User user);

	public User findById(String id);
	
	public User findByLoginId(String loginId);

	public List findList(User user, int pageIndex, int pageSize) ;
	
	public long findListCount(User user) ;
}
