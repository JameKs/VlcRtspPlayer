/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;

import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.service.IDefaultService;
import com.mqm.frame.security.acl.vo.FbrpSecAclClass;

/**
 * <pre>
 * 实现了Spring Security的AclService接口。
 * </pre>
 * 
 * @param <T>
 *            ValueObject
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class AclServiceImpl<T> extends MyBatisServiceImpl<FbrpSecAclClass> implements AclService, IDefaultService {

	private static final Log log = LogFactory.getLog(AclServiceImpl.class);

	private LookupStrategy lookupStrategy;

	@Override
	public List<ObjectIdentity> findChildren(ObjectIdentity parentIdentity) {
		if (parentIdentity.getIdentifier() == null) {
			return null;
		}

		String statement = "com.mqm.frame.security.acl.service.impl.AclServiceImpl.select_id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("className", parentIdentity.getType());
		map.put("objectIdIdentity", parentIdentity.getIdentifier());
		List<String> listobjid = this.getSqlSessionTemplate().selectList(statement, map);

		if (listobjid == null || listobjid.isEmpty()) {
			return null;
		}
		Object parentid = listobjid.get(0);
		// 根据父类的id查找子类的对象，不包括子类的子类。
		/*
		 * hql = new StringBuffer(); hql.append(
		 * "select classvo.className, objdentity.objectIdIdentity from FbrpSecAclObjectIdentityVO objdentity, "
		 * ); hql.append(
		 * "FbrpSecAclClassVO classvo where objdentity.parentObject=? and objdentity.objectIdClass=classvo.id "
		 * );
		 * 
		 * List<Object[]> list =
		 * this.getHibernateTemplate().find(hql.toString(),parentid);
		 */
		// String statement2 = this.getStatement("select_children");
		String statement2 = "com.mqm.frame.security.acl.service.impl.AclServiceImpl.select_children";

		List<Map<String, Object>> selectList = this.getSqlSessionTemplate().selectList(
				statement2, parentid);

		List result = new ArrayList();
		for (int i = 0; i < selectList.size(); i++) {
			Map<String, Object> map2 = selectList.get(i);
			String identity = (String) map2.get("OBJECT_ID_IDENTITY");
			Serializable identifier = (Serializable) map2.get("CLASS");
			ObjectIdentity identity2 = new ObjectIdentityImpl(identity, identifier);
			result.add(identity2);
		}
		return result;
	}

	@Override
	public Acl readAclById(ObjectIdentity object) throws NotFoundException {
		return readAclById(object, null);
	}

	@Override
	public Acl readAclById(ObjectIdentity object, List<Sid> sids) throws NotFoundException {
		Map<ObjectIdentity, Acl> map = readAclsById(Arrays.asList(object), sids);
		return map.get(object);
	}

	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects) throws NotFoundException {
		return readAclsById(objects, null);
	}

	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) throws NotFoundException {
		Map<ObjectIdentity, Acl> result = this.lookupStrategy.readAclsById(objects, sids);
		if (result == null || result.isEmpty()) {
			log.error("未找到.......");
		}
		return result;
	}

	/**
	 * 设置lookupStrategy。
	 * 
	 * @param lookupStrategy
	 *            LookupStrategy
	 */
	public void setLookupStrategy(LookupStrategy lookupStrategy) {
		this.lookupStrategy = lookupStrategy;
	}

	/**
	 * 获取lookupStrategy。
	 * 
	 * @return LookupStrategy
	 */
	public LookupStrategy getLookupStrategy() {
		return this.lookupStrategy;
	}

}
