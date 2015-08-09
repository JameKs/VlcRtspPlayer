/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.ChildrenExistException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;

import com.mqm.frame.security.acl.vo.FbrpSecAclClass;
import com.mqm.frame.security.acl.vo.FbrpSecAclObjectIdentity;
import com.mqm.frame.security.acl.vo.FbrpSecAclSid;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.util.StringUtil;

/**
 * <pre>
 * 实现了Spring Security的MutableAclService接口，用于创建、删除、更新Acl。
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
public class MutableAclServiceImpl extends AclServiceImpl<FbrpSecAclClass> implements MutableAclService {

	private static final Log log = LogFactory.getLog(MutableAclServiceImpl.class);

	private AclCache aclCache;

	@Override
	public MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException {
		if (isExit(objectIdentity)) {
			return (MutableAcl) super.readAclById(objectIdentity);
		}

		List<FbrpSecAclClass> list = this.findBy("className", objectIdentity.getType());
		FbrpSecAclClass aclClassVo = null;

		FbrpSecAclSid sidVo = null;

		if (list != null && !list.isEmpty()) {
			aclClassVo = list.get(0);
		} else {
			aclClassVo = new FbrpSecAclClass();
			aclClassVo.setClassName(objectIdentity.getType());
			aclClassVo.setId(this.getKeyGen().getUUIDKey());
			this.create(aclClassVo);
		}
		PrincipalSid sid = null;

		List<FbrpSecRole> roles = new ArrayList<FbrpSecRole>();

		roles.addAll(this.getUser().getRoleList());

		sid = new PrincipalSid(roles.get(0).getId());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sid", sid.getPrincipal());
		map.put("principal", 0L);

		String statement = this.getStatement("createAcl");
		List<FbrpSecAclSid> listsid = this.getSqlSessionTemplate().selectList(statement, map);

		// List<FbrpSecAclClassVO> listsid = this.findBy(pm);

		if (listsid != null && !listsid.isEmpty()) {
			sidVo = listsid.get(0);
		} else {
			sidVo = new FbrpSecAclSid();
			sidVo.setId(this.getKeyGen().getUUIDKey());
			sidVo.setSid(sid.getPrincipal());
			sidVo.setPrincipal(0L);
			// this.getHibernateTemplate().save(sidVo);
			this.getSqlSessionTemplate().insert(this.getStatement("insert2"), sidVo);
		}
        if(!StringUtil.isStrEmpty(objectIdentity.getIdentifier().toString())){
        	FbrpSecAclObjectIdentity aclObjectIdentityVO = new FbrpSecAclObjectIdentity();
    		aclObjectIdentityVO.setId(this.getKeyGen().getUUIDKey());
    		aclObjectIdentityVO.setObjectIdClass(aclClassVo.getId());
    		aclObjectIdentityVO.setOwnerSid(sidVo.getId());
    		aclObjectIdentityVO.setEntriesInheriting(0L);
    		aclObjectIdentityVO.setObjectIdIdentity(objectIdentity.getIdentifier().toString());
    		String statement2 = this.getStatement("insert");
    		this.getSqlSessionTemplate().insert(statement2, aclObjectIdentityVO);
        }
		return (MutableAcl) super.readAclById(objectIdentity);
	}

	private boolean isExit(ObjectIdentity objectIdentity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("className", objectIdentity.getType());
		map.put("objectIdIdentity", objectIdentity.getIdentifier());
		String statement = this.getStatement("isExit");
		List<String> list = this.getSqlSessionTemplate().selectList(statement, map);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren) throws ChildrenExistException {
		if (deleteChildren) {
			List<ObjectIdentity> list = this.findChildren(objectIdentity);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					deleteAcl(list.get(i), deleteChildren);
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("className", objectIdentity.getType());
		map.put("objectIdIdentity", objectIdentity.getIdentifier());

		String statement = this.getStatement("deleteAcl");
		List<FbrpSecAclObjectIdentity> selectList = this.getSqlSessionTemplate()
				.selectList(statement, map);
		List<Object[]> list = new ArrayList<Object[]>();

		for (FbrpSecAclObjectIdentity vo : selectList) {
			FbrpSecAclClass fbrpSecAclClassVO = vo.getFbrpSecAclClassVO();
			Object[] objArray = { fbrpSecAclClassVO, vo };
			list.add(objArray);
		}

		if (list == null || list.isEmpty()) {
			log.error("数据不存在....");
			return;
		}
		FbrpSecAclObjectIdentity aclObjectIdentityVO = (FbrpSecAclObjectIdentity) list.get(0)[1];

		deleteIdentityVoAndEntries(aclObjectIdentityVO);

		this.aclCache.evictFromCache(objectIdentity);
	}

	private void deleteIdentityVoAndEntries(FbrpSecAclObjectIdentity aclObjectIdentityVO) {
		String statement1 = this.getStatement("deleteIdentityVoAndEntries1");
		this.getSqlSessionTemplate().delete(statement1, aclObjectIdentityVO.getId());

		String statement2 = this.getStatement("deleteIdentityVoAndEntries2");
		this.getSqlSessionTemplate().delete(statement2, aclObjectIdentityVO.getId());
	}

	@Override
	public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {

		/*
		 * String hql =
		 * "delete from FbrpSecAclEntryVO where aclObjectIdentity=?";
		 * 
		 * this.getHibernateTemplate().bulkUpdate(hql, acl.getId());
		 */

		this.delete("deleteIdentityVoAndEntries1", acl.getId().toString());

		List<AccessControlEntry> aces = acl.getEntries();

		// hql =
		// "update FbrpSecAclObjectIdentityVO set parentObject=?, ownerSid=?, entriesInheriting=? where id=?";

		MutableAcl mutableAcl = (MutableAcl) acl.getParentAcl();
		PrincipalSid sid = (PrincipalSid) acl.getOwner();
		String parentid = "";

		if (mutableAcl != null) {
			parentid = mutableAcl.getId().toString();
		}

		// List<FbrpSecAclSidVO> aclSidVOs =
		// this.getHibernateTemplate().find("from FbrpSecAclSidVO where sid=? and principal=?",sid.getPrincipal(),
		// 0l);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sid", sid.getPrincipal());
		map.put("principal", 0L);
		List<FbrpSecAclSid> aclSidVOs = this.findBy("createAcl", map);
		// this.getHibernateTemplate().bulkUpdate(hql,
		// parentid,aclSidVOs.get(0).getId(), acl.isEntriesInheriting() ? 1l :
		// 0l,acl.getId());

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("parentObject", parentid);
		map2.put("ownerSid", aclSidVOs.get(0).getId());
		map2.put("entriesInheriting", acl.isEntriesInheriting() ? 1L : 0L);
		map2.put("id", acl.getId());

		String statement = this.getStatement("updateAcl");
		this.getSqlSessionTemplate().update(statement, map2);

		String sql = "INSERT INTO fbrp_sec_acl_entry (id,acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) VALUES(?, ?, ?, ?, ?, ?, ?,?)";

		int[] parameterTypes = { Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR, Types.NUMERIC,
				Types.NUMERIC, Types.NUMERIC, Types.NUMERIC };

		List valueList = getValueList(aces);

		this.batchCommit_JDBC(sql, parameterTypes, valueList);

		clearCacheIncludingChildren(acl.getObjectIdentity());

		MutableAcl resultacl = (MutableAcl) super.readAclById(acl.getObjectIdentity());
		return resultacl;
	}

	/**
	 * 根据ace列表，得到AclEntryVo对象。
	 * 
	 * @param aces
	 *            List
	 * @return List<FbrpSecAclEntryVO>
	 */
	private List<Object[]> getValueList(List<AccessControlEntry> aces) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < aces.size(); i++) {
			AccessControlEntryImpl ace = (AccessControlEntryImpl) aces.get(i);
			Object[] o = new Object[8];
			o[0] = this.getKeyGen().getUUIDKey();
			o[1] = ((MutableAcl) ace.getAcl()).getId().toString();
			o[2] = i;
			o[3] = getSid(ace);
			o[4] = ace.getPermission().getMask();
			o[5] = ace.isGranting() ? 1 : 0;
			// o[6] = this.getAppId();
			o[6] = 0;
			o[7] = 0;
			list.add(o);
		}
		return list;
	}

	// 清理缓存
	private void clearCacheIncludingChildren(ObjectIdentity objectIdentity) {
		List<ObjectIdentity> children = super.findChildren(objectIdentity);
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				clearCacheIncludingChildren(children.get(i));
			}
		}
		this.aclCache.evictFromCache(objectIdentity);
	}

	private String getSid(AccessControlEntry ace) {
		Sid sid = ace.getSid();
		String sidName = ((PrincipalSid) sid).getPrincipal();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("principal", 0L);
		map.put("sid", sidName);
		List<FbrpSecAclSid> listAclSidVos = this.findBy("getSid", map);
		if (listAclSidVos == null || listAclSidVos.isEmpty()) {
			FbrpSecAclSid vo = new FbrpSecAclSid();
			vo.setId(this.getKeyGen().getUUIDKey());
			vo.setPrincipal(0L);
			vo.setSid(sidName);
			this.create("insert2", vo);
			return vo.getId();
		}
		return listAclSidVos.get(0).getId();
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

}
