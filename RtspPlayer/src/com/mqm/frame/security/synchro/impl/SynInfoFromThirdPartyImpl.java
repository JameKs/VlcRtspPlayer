/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.synchro.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mqm.frame.dbtool.service.ISqlExecutionEngineService;
import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.synchro.ISynInfoFromThirdParty;
import com.mqm.frame.security.vo.FbrpSecAuthRdbms;
import com.mqm.frame.util.StringUtil;

/**
 * 
 * <pre>
 * 非本地认证同步。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class SynInfoFromThirdPartyImpl extends DefaultServiceImpl implements ISynInfoFromThirdParty {

	private ISqlExecutionEngineService sqlExecutionEngineService;

	@Override
	public void synchroStaffs(String appId) {
		FbrpSecAuthRdbms rdbms = this.findRdbmsVO(appId);

		// 第一步：同步所有用户,仅将FBRP中不存在的用户存入 FBRP中，不删除第三方系统中没有的用户
		List<FbrpSecStaff> fullStaffs = this.synchroFullStaffs(appId, rdbms);

		// 第二步：同步角色，仅将FBRP中不存在的角色存入 FBRP中，不删除第三方系统中没有的角色
		List<FbrpSecRole> fullRoles = this.synchroFullRoles(appId, rdbms);

		this.fullRelation(appId, rdbms, fullStaffs, fullRoles);
	}

	private void fullRelation(String appId, FbrpSecAuthRdbms rdbms,
			List<FbrpSecStaff> fullStaffs, List<FbrpSecRole> fullRoles) {
		String dsId = rdbms.getDsId();
		String sql = "delete from fbrp_sec_role_member where ext1='"
				+ rdbms.getSynFlag() + "'";
//		this.sqlExecutionEngineService.executeSql(
//				BaseConstants.AUTH_localDataBase, sql);

		String sql2 = "select * from " + rdbms.getRelationTable();
		List<Map<String, Object>> list2 = this.sqlExecutionEngineService
				.querySql(dsId, sql2, -1, -1);

		Map<String, String> sKeyMapping = new HashMap<String, String>();
		for (FbrpSecStaff vo : fullStaffs) {
			if (rdbms.getSynFlag().equals(vo.getExt1())
					&& !StringUtil.isEmpty(vo.getExt2())) {
				sKeyMapping.put(vo.getExt2(), vo.getId());
			}
		}

		Map<String, String> rKeyMapping = new HashMap<String, String>();
		for (FbrpSecRole vo : fullRoles) {
			if (rdbms.getSynFlag().equals(vo.getExt1())
					&& !StringUtil.isEmpty(vo.getExt2())) {
				rKeyMapping.put(vo.getExt2(), vo.getId());
			}
		}
		// Map<String, String> tableRelation = rdbms.getTableRelation();
		Map<String, String> relationMapping = rdbms.getRelationMapping();
		String uCol = relationMapping.get("STAFF_ID");
		String rCol = relationMapping.get("ROLE_ID");
		Map<String, String> indexMap = new HashMap<String, String>(); // 判断重复
		for (Map<String, Object> map : list2) {
			Object the3ThUId = map.get(uCol);
			if (the3ThUId != null) {
				the3ThUId = the3ThUId + "";
			}
			String fbrpUId = sKeyMapping.get(the3ThUId);

			Object the3RId = map.get(rCol);
			if (the3RId != null) {
				the3RId = the3RId + "";
			}
			String fbrpRId = rKeyMapping.get(the3RId);
			if (!StringUtil.isEmpty(fbrpUId) && !StringUtil.isEmpty(fbrpRId)) {
				String synFlag = rdbms.getSynFlag();
				String indexKey = appId + "-" + fbrpUId + "-" + fbrpRId;
				if (!indexMap.containsKey(indexKey)) {
					indexMap.put(indexKey, null);
					this.saveRelationToFbrp(appId, fbrpUId, fbrpRId, synFlag);
				} else {
					// 为什么会出现这种情况呢？ yanlong 2011-6-24
				}
			}
		}
	}

	private List<FbrpSecRole> synchroFullRoles(String appId,
			FbrpSecAuthRdbms rdbms) {
		FbrpSecRole[] the3ThRoles = null;
//		this.authenticationAdapter
//				.getAllGroupInfoByappId(appId);
		String sql = "select r.id ID,r.code CODE,r.name NAME,r.ext1 EXT1 ,r.ext2 EXT2 from fbrp_sec_role r where r.del_flag = 'n' and r.app_id='"
				+ appId + "'";
		List<Map<String, Object>> list = null;
//		this.sqlExecutionEngineService
//				.querySql(BaseConstants.AUTH_localDataBase, sql, -1, -1);
		Map<String, FbrpSecRole> fbrpKeys = new HashMap<String, FbrpSecRole>();
		for (Map<String, Object> map : list) {
			Object object = map.get("EXT2");
			if (object != null) {
				FbrpSecRole vo = new FbrpSecRole();
				vo.setId((String) map.get("ID"));
				vo.setCode((String) map.get("CODE"));
				vo.setName((String) map.get("NAME"));
				vo.setExt1((String) map.get("EXT1"));
				vo.setExt2((String) map.get("EXT2"));
				fbrpKeys.put(object.toString(), vo);
			}
		}

		List<FbrpSecRole> rets = new ArrayList<FbrpSecRole>();
		for (FbrpSecRole the3ThRole : the3ThRoles) {
			String ext2 = the3ThRole.getExt2();
			if (!fbrpKeys.containsKey(ext2)) {
				FbrpSecRole fbrpRole = this.saveRoleToFbrp(appId, the3ThRole,
						rdbms.getSynFlag());
				rets.add(fbrpRole);
			} else {
				rets.add(fbrpKeys.get(ext2));
			}
		}
		return rets;
	}

	private List<FbrpSecStaff> synchroFullStaffs(String appId,
			FbrpSecAuthRdbms rdbms) {
		FbrpSecStaff[] the3ThStaffs = null; 
//				authenticationAdapter
//				.getAllStaffInfo(appId);
		String sql = "select u.login_id LOGIN_ID,u.id ID,u.code CODE,u.name NAME ,u.ext1 EXT1,u.ext2 EXT2"
				+ " from fbrp_sec_staff u where u.del_flag = 'n'";
		List<Map<String, Object>> list = null;
//		this.sqlExecutionEngineService
//				.querySql(BaseConstants.AUTH_localDataBase, sql, -1, -1);
		Map<String, FbrpSecStaff> fbrpKeys = new HashMap<String, FbrpSecStaff>();
		for (Map<String, Object> map : list) {
			Object object = map.get("LOGIN_ID");
			if (object != null) {
				FbrpSecStaff vo = new FbrpSecStaff();
				vo.setId((String) map.get("ID"));
				vo.setCode((String) map.get("CODE"));
				vo.setName((String) map.get("NAME"));
				vo.setLoginid((String) map.get("LOGIN_ID"));
				vo.setExt1((String) map.get("EXT1"));
				vo.setExt2((String) map.get("EXT2"));
				fbrpKeys.put(object.toString(), vo);
			}
		}
		List<FbrpSecStaff> rets = new ArrayList<FbrpSecStaff>();
		for (FbrpSecStaff the3ThStaff : the3ThStaffs) {
			String loginid = the3ThStaff.getLoginid();
			if (!fbrpKeys.containsKey(loginid)) {
				FbrpSecStaff fbrpStaff = this.saveStaffToFbrp(the3ThStaff,
						rdbms.getSynFlag());
				rets.add(fbrpStaff);
			} else {
				rets.add(fbrpKeys.get(loginid));
			}
		}
		return rets;
	}

	/**
	 * 同步用户及其角色。 该方法假定用户已在第三方数据库中验证通过。
	 * 
	 * @param appId String
	 * @param userName String
	 */
	public void synchroStaffs(String appId, String userName) {
		FbrpSecStaff the3ThStaff = null; 
//				authenticationAdapter
//				.getStaffInfoByStaffname(appId, userName);
		if (the3ThStaff == null) {
			return;
		}
		List<FbrpSecStaff> list = this.findFbrpStaff(the3ThStaff.getLoginid());
		FbrpSecAuthRdbms rdbms = this.findRdbmsVO(appId);
		int size = list.size();
		FbrpSecStaff staff = null;
		if (size == 0) {
			staff = this.saveStaffToFbrp(the3ThStaff, rdbms.getSynFlag());
		} else {
			staff = list.get(0);
		}
		this.synchroRoles(appId, rdbms, staff);
	}

	@Override
	public void synchroRoles(String appId, FbrpSecStaff staff) {
		FbrpSecAuthRdbms rdbms = this.findRdbmsVO(appId);
		this.synchroRoles(appId, rdbms, staff);
	}

	private void synchroRoles(String appId, FbrpSecAuthRdbms rdbms,
			FbrpSecStaff staff) {
		FbrpSecRole[] the3ThRoles = null; 
//				authenticationAdapter.getGroupInfoByStaff(
//				appId, staff.getLoginid());
		if (the3ThRoles == null) {
			return;
		}
		List<Map<String, Object>> fbrpRoles = null;
//		this.findFbrpRoles(
//				BaseConstants.AUTH_localDataBase, appId, staff);
		List<String> the3PKey = new ArrayList<String>();
		List<String> fbrpPKey = new ArrayList<String>();
		Map<String, String> mm = new HashMap<String, String>();
		for (FbrpSecRole vo : the3ThRoles) {
			the3PKey.add(vo.getExt2());
		}
		for (Map<String, Object> map : fbrpRoles) {
			String key = (String) map.get("EXT2");
			String id = (String) map.get("ID");
			fbrpPKey.add(key);
			mm.put(key, id);
		}
		// ---------------------------------------------------------
		List<String> forDelKeyset = this.copyList(fbrpPKey);
		forDelKeyset.removeAll(the3PKey);// 剩下的就是要删除的关系
		for (String pk : the3PKey) {
			mm.remove(pk);
		}

		if (!forDelKeyset.isEmpty()) {
			Collection<String> values = mm.values();
			/*
			 * String delRelationHql = "delete from FbrpSecRoleMemberVO  " +
			 * " where " +
			 * " staffId='"+staff.getId()+"' and ext1='"+rdbms.getSynFlag
			 * ()+"' and roleId in("+getStr(values.toArray())+")";
			 * this.getHibernateTemplate().bulkUpdate(delRelationHql);
			 */
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("staffId", staff.getId());
			map.put("ext1", rdbms.getSynFlag());
			map.put("list",values);
			this.deleteBy("delete", map);
		}

		// ---------------------------------------------------------
		List<String> forAddKeyset = this.copyList(the3PKey);
		forAddKeyset.removeAll(fbrpPKey);// 剩下的就是要新添加的关系
		if (!forAddKeyset.isEmpty()) {
			// 第一步：在FBRP中查找已存在的实体(角色)
			/*
			 * String hql3 = "from FbrpSecRoleVO r where " +
			 * " r.appId='"+appId+"' " + " and r.ext1='"+rdbms.getSynFlag()+"' "
			 * + " and r.ext2 in("+getStr(forAddKeyset.toArray())+")";
			 * List<FbrpSecRoleVO> roles =
			 * this.getHibernateTemplate().find(hql3);
			 */

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ext1", rdbms.getSynFlag());
			map.put("ext2", getStr(forAddKeyset.toArray()));
			List<FbrpSecRole> roles = this.findBy("find", map);

			List<String> theFbrpExistKeyset = new ArrayList<String>();
			for (FbrpSecRole r : roles) {
				theFbrpExistKeyset.add(r.getExt2());
			}
			// 第二步：去除FBRP中已存在的角色，留下来的才是真正需要新增的角色
			List<String> addKeyset = this.copyList(forAddKeyset);
			addKeyset.removeAll(theFbrpExistKeyset);

			// 第三步：新增角色
			if (!addKeyset.isEmpty()) {
				for (FbrpSecRole m : the3ThRoles) {
					if (addKeyset.contains(m.getExt2())) {
						FbrpSecRole role = this.saveRoleToFbrp(appId, m,
								rdbms.getSynFlag());
						roles.add(role);
					}
				}
			}
			// 第四步：加入关系
			for (FbrpSecRole vi : roles) {
				this.saveRelationToFbrp(appId, staff.getId(), vi.getId(),
						rdbms.getSynFlag());
			}
		}
	}

	private String getStr(Object[] strArray) {
		String returnStr = "";
		for (Object str : strArray) {
			if ("".equals(returnStr)) {
				returnStr = "'" + str + "'";
			} else {
				returnStr += ",'" + str + "'";
			}
		}
		return returnStr;
	}

	private FbrpSecRoleMember saveRelationToFbrp(String appId, String staffId,
			String roleId, String synFlag) {
		FbrpSecRoleMember rm = new FbrpSecRoleMember();
		rm.setStaffId(staffId);
		rm.setStatus("y");
		rm.setRoleId(roleId);
		rm.setDelFlag(ValueObject.DEL_FLAG_NORMAL);
		rm.setId(getKeyGen().getUUIDKey());
		rm.setExt1(synFlag);
		// this.getHibernateTemplate().save(rm);
		this.create("insert1", rm);
		return rm;
	}

	private FbrpSecRole saveRoleToFbrp(String appId, FbrpSecRole m,
			String synFlag) {
		FbrpSecRole role = new FbrpSecRole();
		role.setCode(m.getCode());
		role.setName(m.getName());
		role.setDelFlag(ValueObject.DEL_FLAG_NORMAL);
		role.setStatus("y");
		role.setAppId(appId);
		role.setExt1(synFlag);
		role.setExt2(m.getExt2());
		role.setId(this.getKeyGen().getUUIDKey());
		// this.getHibernateTemplate().save(role);
		this.create("insert2", role);
		return role;
	}

	/**
	 * 同步用户到 FBRP中。
	 * 
	 * @param the3Th
	 *            FbrpSecStaff
	 * @param synFlag
	 *            String
	 * 
	 * @return FbrpSecStaff
	 */
	private FbrpSecStaff saveStaffToFbrp(FbrpSecStaff the3Th, String synFlag) {
		FbrpSecStaff vo = new FbrpSecStaff();
		vo.setId(getKeyGen().getUUIDKey());
		vo.setPasswd(getKeyGen().getUUIDKey());
		vo.setCode(the3Th.getCode());
		vo.setName(the3Th.getName());
		vo.setLoginid(the3Th.getLoginid());
		vo.setExt1(synFlag);
		vo.setExt2(the3Th.getExt2());
		vo.setStatus("y");
		vo.setDelFlag(FbrpSecStaff.DEL_FLAG_NORMAL);
		// this.getHibernateTemplate().save(vo);
		this.create("insert3", vo);
		return vo;
	}

	private List<FbrpSecStaff> findFbrpStaff(String loginId) {
		/*
		 * String staffTableName = FbrpSecStaffVO.class.getName(); String hql =
		 * "from "+ staffTableName+" t where t.loginid=? and t.delFlag=?";
		 * String delFlag = FbrpSecStaffVO.DEL_FLAG_NORMAL; List<FbrpSecStaffVO>
		 * list = getHibernateTemplate().find(hql,loginId,delFlag);
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", loginId);
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);

		return this.findBy("findFbrpStaff", map);
	}

	/**
	 * 查找FbrpRoles。
	 * 
	 * @param dsId String
	 * @param appId String
	 * @param staff  FbrpSecStaff
	 * @return  List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findFbrpRoles(String dsId, String appId,
			FbrpSecStaff staff) {
		String roleHql = "select r.* from fbrp_sec_staff s,fbrp_sec_role r ,fbrp_sec_role_member rm where "
				+ " r.id=rm.role_id and s.id=rm.staff_id and s.login_id='"
				+ staff.getLoginid() + "' and r.app_id = '" + appId + "'";
		List<Map<String, Object>> roles = this.sqlExecutionEngineService
				.querySql(dsId, roleHql, -1, -1);
		return roles;
	}

	/**
	 * 根据appId找到具体配置信息。
	 * 
	 * @param appId
	 *            String
	 * 
	 * @return FbrpSecAuthRdbms
	 */
	public FbrpSecAuthRdbms findRdbmsVO(String appId) {
		/*
		 * HibernateTemplate ht = this.getHibernateTemplate(); String hql =
		 * "from "+FbrpSecAuthRdbmsVO.class; List list = ht.find(hql);
		 */
//		String statement = "com.mqm.frame.security.authenticationadapter.RdbmsAuthenticationAdapter.list";
//		List list = this.getSqlSessionTemplate().selectList(statement);
//		if (list.size() > 0) {
//			return (FbrpSecAuthRdbms) list.get(0);
//		}
		return null;
	}

	protected <T> List<T> copyList(List<T> list) {
		List<T> ret = new ArrayList<T>();
		for (T t : list) {
			ret.add(t);
		}
		return ret;
	}

	protected String listToString(List<String> list) {
		if (list.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String id : list) {
			sb.append(",").append("'").append(id).append("'");
		}
		String para = sb.substring(1);
		return para;
	}

	/**
	 * 设置sqlExecutionEngineService。
	 * 
	 * @param sqlExecutionEngineService ISqlExecutionEngineService
	 */
	public void setSqlExecutionEngineService(
			ISqlExecutionEngineService sqlExecutionEngineService) {
		this.sqlExecutionEngineService = sqlExecutionEngineService;
	}

}
