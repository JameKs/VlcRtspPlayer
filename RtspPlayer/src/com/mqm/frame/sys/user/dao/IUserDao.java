package com.mqm.frame.sys.user.dao;

import com.mqm.frame.common.IDefaultDao;

public interface IUserDao<T> extends IDefaultDao<T>{

	public T findByLoginId(String key ,String loginId);

}
