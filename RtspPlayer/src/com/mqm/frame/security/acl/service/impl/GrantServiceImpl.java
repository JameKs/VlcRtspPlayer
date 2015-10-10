/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;

import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.security.acl.extendpoint.IPrincipalType;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.security.acl.vo.FbrpSecAclEntry;
import com.mqm.frame.security.acl.vo.FbrpSecAclObjectIdentity;
import com.mqm.frame.security.acl.vo.FbrpSecAclSid;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.service.ISecurityStrategyService;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 提供数据权限服务（基于Spring Security ACL构建而来）。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class GrantServiceImpl extends DefaultServiceImpl implements
		IGrantService {

	private static final Log log = LogFactory.getLog(GrantServiceImpl.class);

	private PermissionFactory permissionFactory;

	private MutableAclService mutableAclService;

	private ISecurityStrategyService securityStrategyService;
	
	@Override
	public void updateGrant(String roleId,Map<String,String> newEntry){
		Set<String> newObjIds = newEntry.keySet();
		
		String statement1 = this.getStatement("findGrantEntryByRoleId");
		List<Map<String,Object>> olds = this.getSqlSessionTemplate().selectList(statement1, roleId);
		Set<String> oldObjIds = new HashSet<String>();
		for (Map<String,Object> entry : olds) {
			oldObjIds.add((String)entry.get("objId"));
		}
		
		List<String> delEntryIds =new ArrayList<String>();
		for (Map<String,Object> entry : olds) {
			String objId = (String)entry.get("objId");
			if(!newObjIds.contains(objId)){
				delEntryIds.add((String)entry.get("id"));
			}
		}
		if(!delEntryIds.isEmpty()){
			String statement2 = this.getStatement("deleteGrantEntryByIds");
			HashMap<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("list", delEntryIds);
			this.getSqlSessionTemplate().delete(statement2, parameter);
		}
		
		//-------------------------------------------------------------------------------
		//List<Map<String,Object>> insertGrantEntry  = new ArrayList<Map<String,Object>>();
		Set<String> set = new HashSet<String>();
		for (Map.Entry<String, String> ent : newEntry.entrySet()) {
			if(!oldObjIds.contains(ent.getKey())){
				set.add(ent.getKey());
			}
		}
		if(set.isEmpty()){
			return;
		}
		
		
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("list", set);
		String statement3 = this.getStatement("findIdentityByObjIds");
		List<Map<String,Object>> identitys = this.getSqlSessionTemplate().selectList(statement3,parameter);
		Map<String,String> identityMapping = new HashMap<String, String>();
		for (Map<String,Object> map : identitys) {
			identityMapping.put((String)map.get("objId"), (String)map.get("identityId"));
		}
		
		//----
		Map<String,String> clsMapping = new HashMap<String, String>();
		String statement4 = this.getStatement("findAclClass");
		List<Map<String,String>> list = this.getSqlSessionTemplate().selectList(statement4);
		for (Map<String, String> map : list) {
			clsMapping.put(map.get("clsName"), map.get("clsId"));
		}
		
		//----
		//TODO luxiaocheng 此处默认置为“系统管理员”角色，有问题
		String owerSid = "E5128CD896122D5800C584322CAB1600";
		
		List<FbrpSecAclObjectIdentity> insertIdentitys = new ArrayList<FbrpSecAclObjectIdentity>();
		for (String objId : set) {
			if(!identityMapping.containsKey(objId)){
				FbrpSecAclObjectIdentity vo = new FbrpSecAclObjectIdentity();
				String id = this.getKeyGen().getUUIDKey();
				identityMapping.put(objId, id);
				vo.setId(id);
				vo.setObjectIdIdentity(objId);
				String cls = newEntry.get(objId);
				vo.setObjectIdClass(clsMapping.get(cls));
				vo.setEntriesInheriting(0L);
				vo.setOwnerSid(owerSid);
				insertIdentitys.add(vo);
			}
		}
		if(!insertIdentitys.isEmpty()){
			//this.updateGrants(object, principals)
			this.batchCreate("insertIndentity", insertIdentitys);
		}
		
		
		String sid = this.preparingSid(roleId, olds);
		List<FbrpSecAclEntry> insertGrantEntry = new ArrayList<FbrpSecAclEntry>();
		for (Map.Entry<String, String> ent : newEntry.entrySet()) {
			String objId = ent.getKey();
			if(set.contains(objId)){
				FbrpSecAclEntry vo = new FbrpSecAclEntry();
				vo.setId(this.getKeyGen().getUUIDKey());
				String identityId = identityMapping.get(objId);
				vo.setAclObjectIdentity(identityId);
				vo.setAceOrder(0L);
				vo.setMask(16L);
				vo.setGranting(1L);
				vo.setAuditSuccess(0L);
				vo.setAuditFailure(0L);
				vo.setSid(sid);
				insertGrantEntry.add(vo);
			}
		}
		this.batchCreate("insertEntry", insertGrantEntry);
		
	}


	private String preparingSid(String roleId, List<Map<String, Object>> olds) {
		String sid = "";
		if(!olds.isEmpty()){
			sid = (String)olds.get(0).get("sid");
		}else{
			sid = this.getSqlSessionTemplate().selectOne("findSidByRoleId", roleId);
			if(sid==null){
				sid = this.getKeyGen().getUUIDKey();
				FbrpSecAclSid vo = new FbrpSecAclSid();			
				vo.setId(sid);
				vo.setPrincipal(0L);
				vo.setSid(roleId);
				String statement = MutableAclServiceImpl.class.getName()+"."+"insert2";
				this.getSqlSessionTemplate().insert(statement, vo);
			}
		}
		return sid;
	}
	
	
	@Override
	public Map<String, List> getGrantsByTypeAndPrincipals(
			String objectClassName, List principals) {
		String statement = this.getStatement("getGrantsByTypeAndPrincipals");
		List<String> list = this.getSqlSessionTemplate().selectList(statement,
				objectClassName);

		Map<String, List> result = new HashMap<String, List>();
		for (int i = 0; i < list.size(); i++) {
			String id = list.get(i);
			ObjectIdentity identityImpl = new ObjectIdentityImpl(
					objectClassName, id);
			MutableAcl acl = (MutableAcl) this.mutableAclService
					.readAclById(identityImpl);
			if (acl == null) {
				continue;
			}
			List permissions = getPermissions(acl, principals);
			result.put(id, permissions);
		}
		return result;
	}

	@Override
	public void updateToPrincipal(List toGrantObjectList, List currentObjectList, String principal) {
		for (int i = 0; i < toGrantObjectList.size(); i++) {
			Object object = (Object) toGrantObjectList.get(i);
			if (currentObjectList != null && currentObjectList.size() > 0) {
				int index = isContain(currentObjectList, (String) (this.createObjectIdentity(object).getIdentifier()));
				if (index != -1) {
					currentObjectList.remove(index);
					toGrantObjectList.remove(i);
					i--;
				}
			}
		}
		if (currentObjectList != null && currentObjectList.size() > 0) {
			for (int i = 0; i < currentObjectList.size(); i++) {
				ObjectIdentity identity = this.createObjectIdentity(currentObjectList.get(i));
				MutableAcl acl = (MutableAcl) this.mutableAclService.readAclById(identity);
				if (acl != null) {
					for (int j = 0; j < acl.getEntries().size(); j++) {
						AccessControlEntry entry = acl.getEntries().get(j);
						PrincipalSid principalSid = (PrincipalSid) entry.getSid();
						if (principalSid.getPrincipal().equals(principal) && entry.isGranting()) {
							try {
								acl.deleteAce(j);
								this.mutableAclService.updateAcl(acl);
							} catch (Exception e) {
								log.error("当前用户无法删除" + identity + "的权限", e);
							}
							break;
						}
					}
				}
			}
		}

		for (int m = 0; m < toGrantObjectList.size(); m++) {
			Object object = toGrantObjectList.get(m);
			ObjectIdentity identity = this.createObjectIdentity(object);
			MutableAcl acl = (MutableAcl) this.mutableAclService.readAclById(identity);
			if (acl == null) {
				acl = (MutableAcl) this.mutableAclService.createAcl(identity);
			}
			if (acl != null) {
				Permission permission = permissionFactory.buildFromMask(BaseConstants.GRANT_TYPE_ADMIN);
				PrincipalSid pSid = new PrincipalSid(principal);
				acl.insertAce(acl.getEntries().size(), permission, pSid, true);
				this.mutableAclService.updateAcl(acl);
			}
		}
	}

	@Override
	public void insertGrant(Object object, int mask, String principal) {
		MutableAcl acl;
		ObjectIdentity oid = this.createObjectIdentity(object);
		acl = (MutableAcl) this.mutableAclService.readAclById(oid);
		if (acl == null) {
			acl = mutableAclService.createAcl(oid);
		}
		if (acl == null) {
			return;
		}
		if (principal != null) {
			PrincipalSid ps = new PrincipalSid(principal);
			acl.insertAce(acl.getEntries().size(),
					permissionFactory.buildFromMask(mask), ps, true);
			this.mutableAclService.updateAcl(acl);
			if (log.isDebugEnabled()) {
				log.debug("Added permission " + mask + " for Sid " + principal
						+ " " + object.getClass().getName() + " " + object);
			}
		} else {
			log.error("principal不能为空....");
		}
	}

	@Override
	public void deleteGrants(Object object, int mask, String principal) {
		MutableAcl acl = null;
		ObjectIdentity oid = this.createObjectIdentity(object);
		acl = (MutableAcl) this.mutableAclService.readAclById(oid);
		if (acl == null) {
			return;
		}
		for (int i = 0; i < acl.getEntries().size(); i++) {
			AccessControlEntry ace = acl.getEntries().get(i);
			PrincipalSid ps = (PrincipalSid) ace.getSid();
			if ((ace.getPermission().getMask() == mask && ps.getPrincipal()
					.equals(principal))) {
				acl.deleteAce(i);
				i--;
				break;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("delete permission " + mask + " for Sid " + principal
					+ " " + object.getClass().getName() + " " + object);
		}
		//this.mutableAclService.updateAcl(acl);
	}

	@Override
	public void deleteAcl(Object object) {
		ObjectIdentity oid = this.createObjectIdentity(object);
		this.mutableAclService.deleteAcl(oid, true);
	}

	@Override
	public Map<String, List> getGrantsByParentAndPrincipals(Object object,
			List principals) {
		ObjectIdentity identity = this.createObjectIdentity(object);
		MutableAcl acl = (MutableAcl) this.mutableAclService
				.readAclById(identity);
		List partentPermissions = getPermissions(acl, principals);
		List<ObjectIdentity> list = this.mutableAclService
				.findChildren(identity);
		Map<ObjectIdentity, Acl> result = this.mutableAclService
				.readAclsById(list);
		Map<String, List> mapresult = new HashMap<String, List>();

		for (Map.Entry<ObjectIdentity, Acl> entry : result.entrySet()) {
			ObjectIdentity key = entry.getKey();
			Acl value = entry.getValue();
			List permissions = getPermissions((MutableAcl) value, principals);
			permissions.addAll(partentPermissions);
			mapresult.put(key.getIdentifier().toString(), permissions);
		}
		
		return mapresult;
	}

	private List getPermissions(MutableAcl acl, List sids) {
		List permissions = new ArrayList();
		for (int i = 0; i < acl.getEntries().size(); i++) {
			AccessControlEntry ace = acl.getEntries().get(i);
			PrincipalSid ps = (PrincipalSid) ace.getSid();
			if (sids != null
					&& (sids.contains(ps.getPrincipal()) && ace.isGranting())) {
				permissions.add(ace.getPermission().getMask());
			}
		}
		return permissions;
	}

	@Override
	public Map<Object, List> getParentGrants(Object object, String sid) {
		ObjectIdentity identity = createObjectIdentity(object);
		MutableAcl acl = (MutableAcl) this.mutableAclService
				.readAclById(identity);
		MutableAcl parentacl = (MutableAcl) acl.getParentAcl();
		ObjectIdentity parentidentity = parentacl.getObjectIdentity();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", parentidentity.getType());
		map.put("id", parentidentity.getIdentifier().toString());
		Object valueObject = this.findBy("getParentGrants", map);
		Map resultMap = new HashMap();
		List list = getPermissions(parentacl, Arrays.asList(sid));
		resultMap.put(valueObject, list);
		return resultMap;
	}

	@Override
	public List getGrantsByResource(Object object) {
		List principalids = new ArrayList();
		Map<String, IPrincipalType> map = this.getApplicationContext()
				.getBeansOfType(IPrincipalType.class);
		List pincipals = new ArrayList();
		for (IPrincipalType principalType : map.values()) {
			pincipals.addAll(principalType.getCurrentPrincipals());
		}
		for (int i = 0; i < pincipals.size(); i++) {
			principalids.add(createObjectIdentity(pincipals.get(i))
					.getIdentifier());
		}
		return (List) getAllFilter(Arrays.asList(object), principalids).get(
				object);
	}

	private int isContain(List list, String id) {
		for (int i = 0; i < list.size(); i++) {
			String targetid = (String) createObjectIdentity(list.get(i))
					.getIdentifier();
			if (targetid.equals(id)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void createAcl(Object object) {
		ObjectIdentity identity = this.createObjectIdentity(object);
		this.mutableAclService.createAcl(identity);
	}

	@Override
	public Map getAllFilter(List objectList, List principals) {
		Map result = new HashMap();
		List listobjectIdentity = new ArrayList();
		Map<ObjectIdentity, Object> valuemap = new HashMap<ObjectIdentity, Object>();
		for (Object object : objectList) {
			ObjectIdentity identity = this.createObjectIdentity(object);
			listobjectIdentity.add(identity);
			valuemap.put(identity, object);
		}
		Map<ObjectIdentity, Acl> map = this.mutableAclService.readAclsById(listobjectIdentity);
		for (Acl acl : map.values()) {
			if (acl == null) {
				continue;
			}
			List permissions = getPermissions((MutableAcl) acl, principals);
			if (permissions.size() > 0) {
				result.put(valuemap.get(acl.getObjectIdentity()), permissions);
			}
		}
		return result;
	}

	@Override
	public void insertGrant(Object parentObject, List objectList) {
		ObjectIdentity parentidentity = this.createObjectIdentity(parentObject);
		Acl acl = this.mutableAclService.readAclById(parentidentity);
		if (acl != null) {
			for (int i = 0; i < objectList.size(); i++) {
				MutableAcl childAcl = (MutableAcl) this.mutableAclService
						.createAcl(this.createObjectIdentity(objectList.get(i)));
				childAcl.setParent(acl);
				this.mutableAclService.updateAcl(childAcl);
			}
		} else {
			log.error(parentObject + "的权限不存在.......");
		}
	}

	@Override
	public List getAllPrincipals(Object object) {
		ObjectIdentity identity = this.createObjectIdentity(object);
		return getSids(identity);
	}

	@Override
	public List getAllPrincipals(String objectClassName, String id) {
		ObjectIdentity identity = new ObjectIdentityImpl(objectClassName, id);
		return getSids(identity);
	}

	private List getSids(ObjectIdentity identity) {
		MutableAcl acl = (MutableAcl) this.mutableAclService
				.readAclById(identity);
		if (acl == null) {
			return null;
		}
		List list = new ArrayList();
		for (AccessControlEntry entry : acl.getEntries()) {
			if (entry.isGranting()) {
				PrincipalSid sid = (PrincipalSid) entry.getSid();
				list.add(sid.getPrincipal());
			}
		}
		return list;
	}

	@Override
	public void updateGrants(Object object, List principals) {
		ObjectIdentity identity = this.createObjectIdentity(object);
		Acl acl = this.mutableAclService.readAclById(identity);
		if (acl == null) {
			acl = this.mutableAclService.createAcl(identity);
		}
		MutableAcl mutableAcl = (MutableAcl) acl;
		for (int i = 0; i < mutableAcl.getEntries().size(); i++) {
			mutableAcl.deleteAce(i);
			i--;
		}
		for (int i = 0; i < principals.size(); i++) {
			PrincipalSid sid = new PrincipalSid((String) principals.get(i));
			mutableAcl.insertAce(mutableAcl.getEntries().size(),
					this.permissionFactory
							.buildFromMask(BaseConstants.GRANT_TYPE_ADMIN),
					sid, true);
		}
		this.mutableAclService.updateAcl(mutableAcl);
	}

	@Override
	public void insertGrant(String objectId, String objectClassName, int mask,
			String principal) {
		ObjectIdentity identity = new ObjectIdentityImpl(objectClassName,
				objectId);
		MutableAcl acl = (MutableAcl) this.mutableAclService
				.readAclById(identity);
		if (acl == null) {
			acl = mutableAclService.createAcl(identity);
		}
		// TODO luxiaocheng@foresee.cn 临时代码，必须删除掉的
		if (acl == null) {
			return;
		}
		if (principal != null) {
			PrincipalSid ps = new PrincipalSid(principal);
			acl.insertAce(acl.getEntries().size(),
					permissionFactory.buildFromMask(mask), ps, true);
			this.mutableAclService.updateAcl(acl);
			if (log.isDebugEnabled()) {
				log.debug("Added permission " + mask + " for Sid " + principal
						+ " " + objectClassName + " " + objectId);
			}
		} else {
			log.error("principal不能为空....");
		}
	}

	@Override
	public void insertGrants(List<Object> objects, int mask, List principals) {
		if (objects == null || principals == null) {
			return;
		}
		for (int i = 0; i < objects.size(); i++) {
			for (int j = 0; j < principals.size(); j++) {
				insertGrant(objects.get(i), mask, (String) principals.get(j));
			}
		}
	}

	@Override
	public void deleteGrants(List<Object> objects, int mask, List principals) {
		if (objects == null || principals == null) {
			return;
		}
		for (int i = 0; i < objects.size(); i++) {
			for (int j = 0; j < principals.size(); j++) {
				deleteGrants(objects.get(i), mask, (String) principals.get(j));
			}
		}
	}

	@Override
	public boolean isAdmin(String loginId) throws FbrpException {
		if ((loginId != null)
				&& (loginId.equalsIgnoreCase(this.securityStrategyService
						.getAdminStaffId()))) {
			return true;
		}
		return false;
	}

	@Override
	public FbrpSecRole getAppAdminRole() throws FbrpException {
		String adminStaffId = this.securityStrategyService.getAdminStaffId();
		String statement = this.getStatement("getAppAdminRole");
		List<FbrpSecRole> list = this.getSqlSessionTemplate().selectList(
				statement, adminStaffId);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean isAppAdminRole(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.securityStrategyService.getAdminStaffId());
		map.put("roleId", roleId);
		String statement = this.getStatement("isAppAdminRole");
		List<FbrpSecRole> list = this.getSqlSessionTemplate().selectList(
				statement, map);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	private ObjectIdentity createObjectIdentity(Object object) {
		ObjectIdentity identity = new ObjectIdentityImpl(object);
		return identity;
	}

	/**
	 * 设置securityStrategyService。
	 * 
	 * @param securityStrategyService
	 *            ISecurityStrategyService
	 */
	public void setSecurityStrategyService(
			ISecurityStrategyService securityStrategyService) {
		this.securityStrategyService = securityStrategyService;
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

	/**
	 * 设置mutableAclService。
	 * 
	 * @param mutableAclService
	 *            MutableAclService
	 */
	public void setMutableAclService(MutableAclService mutableAclService) {
		this.mutableAclService = mutableAclService;
	}

}
