/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.util.FieldUtils;

import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.security.acl.vo.FbrpSecAclEntry;
import com.mqm.frame.security.acl.vo.FbrpSecAclObjectIdentity;
import com.mqm.frame.security.acl.vo.FbrpSecAclSid;

/**
 * <pre>
 * 实现了Spring Security的LookupStrategy接口。
 * </pre>
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class LookupStrategyImpl extends DefaultServiceImpl implements LookupStrategy {

	private static final Log log = LogFactory.getLog(LookupStrategyImpl.class);

	private AclAuthorizationStrategy aclAuthorizationStrategy;

	private AuditLogger auditLogger;

	private AclCache aclCache;

	private PermissionFactory permissionFactory;

	private final Field fieldAces = FieldUtils.getField(AclImpl.class, "aces");

	private int batchSize = 50;

	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) {
		Map<ObjectIdentity, Acl> result = new HashMap<ObjectIdentity, Acl>();
		Map<String, List> map = new HashMap<String, List>();
		int i = 0;
		for (ObjectIdentity objectIdentity : objects) {
			boolean aclFound = false;
			if (result.containsKey(objectIdentity)) {
				aclFound = true;
			}
			Acl acl = null;
			acl = this.aclCache.getFromCache(objectIdentity);
			if (acl != null) {
				if (acl.isSidLoaded(sids)) {
					result.put(acl.getObjectIdentity(), acl);
					aclFound = true;
				} else {
					log.error("Error: SID-filtered element detected when implementation does not perform SID filtering "
							+ "- have you added something to the cache manually?");
					return null;
				}
			}
			if (!aclFound) {
				if (!map.containsKey(objectIdentity.getType())) {
					List list = new ArrayList();
					map.put(objectIdentity.getType(), list);
				}
				map.get(objectIdentity.getType()).add(objectIdentity.getIdentifier());
				i++;
			}
			if (i % batchSize == 0 || i == objects.size()) {
				Map<ObjectIdentity, Acl> ss = lookupObjectIdentities(map, sids);
				for (Acl macl : ss.values()) {
					this.aclCache.putInCache((MutableAcl) macl);
					result.put(macl.getObjectIdentity(), macl);
				}
				map.clear();

			}
		}
		return result;
	}

	private Map<ObjectIdentity, Acl> lookupObjectIdentities(Map<String, List> map, List sids) {
		Map<ObjectIdentity, Acl> result = new HashMap<ObjectIdentity, Acl>();

		for (Map.Entry<String, List> entry : map.entrySet()) {
			String key = entry.getKey();
			List value = entry.getValue();
			Map<ObjectIdentity, Acl> acls = getAcls(value, key, sids);
			result.putAll(acls);
		}

		return result;
	}

	private Map<ObjectIdentity, Acl> getAcls(List objectids, String type, List sids) {
		/*
		 * StringBuffer hql = new StringBuffer(); HqlWrapper hqlWrapper = new
		 * HqlWrapper(); hql.append(
		 * "select objectIdentityvo,sidvo from FbrpSecAclClassVO classVo,FbrpSecAclObjectIdentityVO "
		 * ); hql.append(
		 * "objectIdentityvo, FbrpSecAclSidVO sidvo where objectIdentityvo.objectIdClass=classVo.id and sidvo.id=objectIdentityvo.ownerSid"
		 * ); hqlWrapper.setHql(hql.toString());
		 * hqlWrapper.setConditionEqual("classVo.className", type);
		 * hqlWrapper.setConditionIn
		 * ("objectIdentityvo.objectIdIdentity",objectids);
		 * //hqlWrapper.setConditionEqual("classVo.appId", this.getAppId());
		 * //hqlWrapper.setConditionEqual("objectIdentityvo.appId",
		 * this.getAppId()); Object[] o =
		 * this.find_Hibernate_ComposedHQL(hqlWrapper, 0,Integer.MAX_VALUE,
		 * true, false);
		 */

		String statement = this.getStatement("getAcls");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("className", type);

		paramMap.put("list",objectids );
		List<FbrpSecAclObjectIdentity> selectList = this.getSqlSessionTemplate()
				.selectList(statement, paramMap);

		List<Object[]> list = new ArrayList<Object[]>();
		for (FbrpSecAclObjectIdentity vo : selectList) {
			FbrpSecAclSid sidVO = vo.getFbrpSecAclSidVO();
			Object[] objArray = { vo, sidVO };
			list.add(objArray);
		}

		// List<Object[]> list = (List) o[0];
		Map<ObjectIdentity, Acl> result = new HashMap<ObjectIdentity, Acl>();
		List ids = new ArrayList();
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			ids.add(((FbrpSecAclObjectIdentity) list.get(i)[0]).getId());
			FbrpSecAclObjectIdentity aclVO = (FbrpSecAclObjectIdentity) list.get(i)[0];
			Acl parentacl = null;
			if (aclVO.getParentObject() != null) {
				parentacl = getParentAcl(aclVO.getParentObject(), sids);

			}
			FbrpSecAclSid sidvo = (FbrpSecAclSid) list.get(0)[1];
			Sid owner = new PrincipalSid(sidvo.getSid());
			ObjectIdentity identity = new ObjectIdentityImpl(type, aclVO.getObjectIdIdentity());
			map.put(((FbrpSecAclObjectIdentity) list.get(i)[0]).getId(), identity);
//			Acl acl = new AclImpl(identity, aclVO.getId(), aclAuthorizationStrategy, auditLogger, parentacl, null,
//					1 == (aclVO.getEntriesInheriting()), owner);
			Acl acl = new AclImpl(identity, aclVO.getId(), aclAuthorizationStrategy, auditLogger);
			result.put(identity, acl);
		}
		setAces(ids, result, map);
		return result;
	}

	private void setAces(List ids, Map<ObjectIdentity, Acl> objAcl, Map<String, ObjectIdentity> map) {
		if (ids == null || ids.isEmpty()) {
			return;
		}
		/*
		 * StringBuffer acehql = new StringBuffer(); acehql.append(
		 * "from FbrpSecAclEntryVO entryvo,FbrpSecAclSidVO sidvo where entryvo.sid=sidvo.id "
		 * ); HqlWrapper hqlWrapper = new HqlWrapper();
		 * hqlWrapper.setHql(acehql.toString());
		 * hqlWrapper.setConditionIn("entryvo.aclObjectIdentity", ids);
		 * //hqlWrapper.setConditionEqual("entryvo.appId", this.getAppId());
		 * //hqlWrapper.setConditionEqual("sidvo.appId", this.getAppId());
		 * hqlWrapper.setOrderBy("entryvo.aceOrder asc"); Object[] o =
		 * this.find_Hibernate_ComposedHQL(hqlWrapper, 0, Integer.MAX_VALUE,
		 * true, false); List<Object[]> aclentrys = (List) o[0];
		 */
	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("list", ids);
		String statement = this.getStatement("setAces");
		List<FbrpSecAclEntry> selectList = this.getSqlSessionTemplate().selectList(statement,
				paramMap);

		List<Object[]> aclentrys = new ArrayList<Object[]>();
		for (FbrpSecAclEntry vo : selectList) {
			FbrpSecAclSid sidVO = vo.getSidVO();
			Object[] objArray = { vo, sidVO };
			aclentrys.add(objArray);
		}

		for (int i = 0; i < aclentrys.size(); i++) {
			FbrpSecAclEntry entryVo = (FbrpSecAclEntry) aclentrys.get(i)[0];
			FbrpSecAclSid aclSidVO = (FbrpSecAclSid) aclentrys.get(i)[1];
			Acl acl = objAcl.get(map.get(entryVo.getAclObjectIdentity()));
			Sid recipient = new PrincipalSid(aclSidVO.getSid());
			AccessControlEntryImpl acess = new AccessControlEntryImpl(entryVo.getId(), acl, recipient,
					permissionFactory.buildFromMask(Integer.parseInt(entryVo.getMask() + "")),
					entryVo.getGranting() == 1L, 1L == entryVo.getAuditSuccess(), 1L == entryVo.getAuditFailure());
			fieldAces.setAccessible(true);
			List<AccessControlEntryImpl> aces = readAces(acl);
			if (!aces.contains(acess)) {
				aces.add(acess);
			}
		}
	}

	private Acl readaclById(ObjectIdentity object, List<Sid> sids) {
		/*
		 * StringBuffer hql = new StringBuffer(); hql.append(
		 * "select objectIdentityvo from FbrpSecAclClassVO classVo,FbrpSecAclObjectIdentityVO "
		 * ); hql.append(
		 * "objectIdentityvo where objectIdentityvo.objectIdClass=classVo.id ");
		 * hql.append(
		 * "and classVo.className=? and objectIdentityvo.objectIdIdentity=? ");
		 * 
		 * List<FbrpSecAclObjectIdentityVO> list = this.getHibernateTemplate()
		 * .find(hql.toString(), object.getType(), object.getIdentifier()); if
		 * (list == null || list.isEmpty()) { log.error("数据不存在......"); return
		 * null; }
		 * 
		 * FbrpSecAclObjectIdentityVO aclVO = list.get(0);
		 */

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("className", object.getType());
		map.put("objectIdIdentity", object.getIdentifier());
		String statement = this.getStatement("readaclById1");
		FbrpSecAclObjectIdentity aclVO = (FbrpSecAclObjectIdentity) this.getSqlSessionTemplate().selectOne(statement,
				map);

		Acl parentacl = null;
		if (aclVO.getParentObject() != null) {
			parentacl = getParentAcl(aclVO.getParentObject(), sids);
		}
		/*
		 * FbrpSecAclSidVO sidvo = this.getHibernateTemplate().get(
		 * FbrpSecAclSidVO.class, aclVO.getOwnerSid());
		 */

		FbrpSecAclSid sidvo = this.find("findById", aclVO.getOwnerSid());

		Sid owner = new PrincipalSid(sidvo.getSid());

//		Acl acl = new AclImpl(object, aclVO.getId(), aclAuthorizationStrategy, auditLogger, parentacl, null,
//				1 == (aclVO.getEntriesInheriting()), owner);
		Acl acl = new AclImpl(object, aclVO.getId(), aclAuthorizationStrategy, auditLogger);

		/*
		 * StringBuffer acehql = new StringBuffer(); acehql.append(
		 * "from FbrpSecAclEntryVO entryvo,FbrpSecAclSidVO sidvo where entryvo.aclObjectIdentity=? and entryvo.sid=sidvo.id "
		 * ); acehql.append("order by entryvo.aceOrder asc ");
		 * 
		 * List<Object[]> listaclAclEntryAndsid = this.getHibernateTemplate()
		 * .find(acehql.toString(), aclVO.getId());
		 */

		String statement2 = this.getStatement("readaclById2");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", aclVO.getId());
		List<FbrpSecAclObjectIdentity> selectList = this.getSqlSessionTemplate()
				.selectList(statement2, paramMap);

		List<Object[]> listaclAclEntryAndsid = new ArrayList<Object[]>();
		for (FbrpSecAclObjectIdentity vo : selectList) {
			FbrpSecAclSid sidVO = vo.getFbrpSecAclSidVO();
			Object[] objArray = { vo, sidVO };
			listaclAclEntryAndsid.add(objArray);
		}

		for (int i = 0; i < listaclAclEntryAndsid.size(); i++) {
			FbrpSecAclEntry entryVo = (FbrpSecAclEntry) listaclAclEntryAndsid.get(i)[0];
			FbrpSecAclSid aclSidVO = ((FbrpSecAclSid) listaclAclEntryAndsid.get(i)[1]);
			Sid recipient = new PrincipalSid(aclSidVO.getSid());
			AccessControlEntryImpl acess = new AccessControlEntryImpl(entryVo.getId(), acl, recipient,
					permissionFactory.buildFromMask(Integer.parseInt(entryVo.getMask() + "")),
					entryVo.getGranting() == 1L, 1L == entryVo.getAuditSuccess(), 1L == entryVo.getAuditFailure());
			fieldAces.setAccessible(true);
			List<AccessControlEntryImpl> aces = readAces(acl);
			if (!aces.contains(acess)) {
				aces.add(acess);
			}
		}
		return acl;
	}

	/**
	 * 获得父类的ACL。
	 * 
	 * @param id
	 *            String
	 * @param sids
	 *            List
	 * @return Acl
	 */
	private Acl getParentAcl(String id, List<Sid> sids) {
		Acl acl = this.aclCache.getFromCache(id);
		if (acl == null || !acl.isSidLoaded(sids)) {
			/*
			 * StringBuffer hql = new StringBuffer(); hql.append(
			 * "select classVo.className,objectIdentityvo.objectIdIdentity from FbrpSecAclClassVO classVo,FbrpSecAclObjectIdentityVO "
			 * ); hql.append(
			 * "objectIdentityvo where objectIdentityvo.objectIdClass=classVo.id "
			 * ); hql.append("and objectIdentityvo.id=? "); List<Object[]> list
			 * = this.getHibernateTemplate().find( hql.toString(), id);
			 */

			String statement = this.getStatement("getParentAcl");
			List<FbrpSecAclObjectIdentity> selectList = this.getSqlSessionTemplate()
					.selectList(statement, id);

			List<Object[]> list = new ArrayList<Object[]>();
			for (FbrpSecAclObjectIdentity vo : selectList) {
				String className = vo.getFbrpSecAclClassVO().getClassName();
				String objectIdIdentity = vo.getObjectIdIdentity();
				Object[] objArray = { className, objectIdIdentity };
				list.add(objArray);
			}

			if (list == null || list.isEmpty()) {
				return null;
			}
			ObjectIdentityImpl identity = new ObjectIdentityImpl(list.get(0)[0] + "", list.get(0)[1] + "");
			return readaclById(identity, sids);
		} else {
			return acl;
		}
	}

	private List<AccessControlEntryImpl> readAces(Acl acl) {
		try {
			return (List<AccessControlEntryImpl>) fieldAces.get(acl);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Could not obtain AclImpl.aces field", e);
		}
	}

	/**
	 * 设置aclAuthorizationStrategy。
	 * 
	 * @param aclAuthorizationStrategy
	 *            AclAuthorizationStrategy
	 */
	public void setAclAuthorizationStrategy(AclAuthorizationStrategy aclAuthorizationStrategy) {
		this.aclAuthorizationStrategy = aclAuthorizationStrategy;
	}

	/**
	 * 设置auditLogger。
	 * 
	 * @param auditLogger
	 *            AuditLogger
	 */
	public void setAuditLogger(AuditLogger auditLogger) {
		this.auditLogger = auditLogger;
	}

	/**
	 * 设置aclCache。
	 * 
	 * @param aclCache
	 *            AclCache
	 */
	public void setAclCache(AclCache aclCache) {
		this.aclCache = aclCache;
	}

	/**
	 * 获取aclCache。
	 * 
	 * @return AclCache
	 */
	public AclCache getAclCache() {
		return aclCache;
	}

	/**
	 * 设置permissionFactory。
	 * 
	 * @param permissionFactory
	 *            PermissionFactory
	 */
	public void setPermissionFactory(PermissionFactory permissionFactory) {
		this.permissionFactory = permissionFactory;
	}

}
