/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.xtgl.yhgl.dao.impl;

import java.util.List;

import com.icanft.common.DefaultDaoImpl;
import com.icanft.xtgl.yhgl.dao.IUserDao;
import com.icanft.xtgl.yhgl.vo.User;

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
public class UserDaoImpl extends DefaultDaoImpl implements IUserDao {

	public void insert(User user) {
		String statement = this.getStatement("insert");
		sqlSessionTemplate.insert(statement, user);

	}

	public void delete(String id) {
		String statement = this.getStatement("delete");
		sqlSessionTemplate.delete(statement, id);

	}

	public void update(User user) {
		String statement = this.getStatement("update");
		sqlSessionTemplate.update(statement, user);

	}

	public User findById(String id) {
		String statement = this.getStatement("findById");
		return (User)sqlSessionTemplate.selectOne(statement, id);
	}
	
	public User findByLoginId(String loginId) {
		String statement = this.getStatement("findByLoginId");
		return (User)sqlSessionTemplate.selectOne(statement, loginId);
	}

	public List findList(User user,int pageIndex, int pageSize) {
		return pagedQuery("findList", user, pageIndex, pageSize);
	}

	public long findListCount(User user) {
		String statement = this.getStatement("findListCount");
		return (Long)sqlSessionTemplate.selectOne(statement, user);
	}

	public User login(User user) {
		String statement = this.getStatement("login");
		return (User)sqlSessionTemplate.selectOne(statement, user);
	}

}
