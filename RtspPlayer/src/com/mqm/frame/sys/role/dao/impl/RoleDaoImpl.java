/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.mqm.frame.sys.role.dao.impl;

import java.util.List;
import com.mqm.frame.common.DefaultDaoImpl;
import com.mqm.frame.sys.role.dao.IRoleDao;
import com.mqm.frame.sys.role.vo.Role;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年8月9日
 */
public class RoleDaoImpl extends DefaultDaoImpl<Role> implements IRoleDao<Role>{

	/* (non-Javadoc)
	 * @see com.mqm.frame.sys.role.dao.IRoleDao#findByUserLoginId(java.lang.String)
	 */
	@Override
	public List findByUserLoginId(String loginId) {
		// TODO Auto-generated method stub
		return null;
	}

}
