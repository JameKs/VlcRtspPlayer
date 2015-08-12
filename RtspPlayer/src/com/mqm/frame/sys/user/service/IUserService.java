package com.mqm.frame.sys.user.service;

import com.mqm.frame.common.IDefaultService;

public interface IUserService<T> extends IDefaultService<T> {

	public T findByLoginId(String loginId);

}
