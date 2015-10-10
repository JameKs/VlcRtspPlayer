/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.sys.user.dao.impl;

import com.mqm.frame.common.DefaultDaoImpl;
import com.mqm.frame.sys.user.dao.IUserDao;
import com.mqm.frame.sys.user.vo.User;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author meihu  meihu@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class UserDaoImpl extends DefaultDaoImpl<User> implements IUserDao<User> {

	@Override
	public User findByLoginId(String loginId) {
		String statement = this.getStatement("findByLoginId");
		User user = (User)sqlSessionTemplate.selectOne(statement, loginId);
		return user;
	}

}
