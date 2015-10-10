/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.org.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.persistence.query.Executor;
import com.mqm.frame.infrastructure.persistence.query.QueryBuilder;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.infrastructure.util.VOUtils;
import com.mqm.frame.security.org.service.IOrgService;
import com.mqm.frame.security.org.vo.FbrpSecOrg;
import com.mqm.frame.security.org.vo.FbrpSecOrgMember;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.synchro.ISynOrgInfoToThirdParty;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 机构服务实现类。
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
@SuppressWarnings("rawtypes")
public class OrgServiceImpl extends MyBatisServiceImpl<FbrpSecOrg> implements
		IOrgService {
	
	/**
	 * 设置当前登录用户机构信息。
	 * 
	 * @param user
	 *            UserProfileVO
	 */
	public void queryOrgInfoForLoginUser(UserProfileVO user) {
		/*
		 * String hql =
		 * "select org from FbrpSecOrgMemberVO orgmember,FbrpSecOrgVO org where "
		 * +
		 * "orgmember.orgId=org.id and orgmember.staffId=? and org.delFlag<>'"+
		 * ValueObject.DEL_FLAG_DELETED+"' " +"and orgmember.relationType='0'";
		 * List orgList =
		 * this.getHibernateTemplate().find(hql,userProfileVO.getStaffId());
		 */
		QueryBuilder builder = this.selectFrom(FbrpSecOrgMember.class, "m",
				FbrpSecOrg.class, "o").where("m.orgId=o.id");
		Executor<FbrpSecOrg> executor = builder.builtFor(FbrpSecOrg.class);
		PMap pm = this.newMapInstance();
		pm.eq("m.staffId", user.getStaffId());
		pm.ne("o.delFlag", ValueObject.DEL_FLAG_DELETED);
		pm.eq("m.relationType", "0");
		FbrpSecOrg org = executor.findFirstBy(pm);
		if (org != null) {
			user.setDeptId(org.getId());
			user.setDeptName(org.getName());
			user.setOrgId(org.getId());
			user.setOrgName(org.getName());
		}
	}

	@Override
	public FbrpSecOrg getOrgByOrgId(String orgId) {
		/*
		 * String hql = "from FbrpSecOrgVO where id=?"; List queryList =
		 * this.getHibernateTemplate().find(hql, orgid); // List queryList =
		 * (List) rs[0]; if (queryList.size() > 0) { FbrpSecOrgVO orgVO =
		 * (FbrpSecOrgVO) queryList.get(0); if (orgVO.getParentId() != null &&
		 * !"".equals(orgVO.getParentId())) { List superList =
		 * this.getHibernateTemplate().find(hql, orgVO.getParentId());
		 * FbrpSecOrgVO superOrg = (FbrpSecOrgVO) superList.get(0);
		 * orgVO.setSuperOrgName(superOrg.getName()); } FbrpSecOrgVO owner =
		 * this.getOwnerId(orgVO.getParentId()); if (owner != null) { List
		 * ownerList = this.getHibernateTemplate().find(hql, owner.getId());
		 * FbrpSecOrgVO ownerOrg = (FbrpSecOrgVO) ownerList.get(0);
		 * orgVO.setOwnerOrgName(ownerOrg.getName()); } return orgVO; } else {
		 * return null; }
		 */
		FbrpSecOrg vo = this.find(orgId);
		if (vo == null) {
			return null;
		}
		String pId = vo.getParentId();
		if (pId != null && !"".equals(pId)) {
			FbrpSecOrg pVo = this.find(pId);
			vo.setSuperOrgName(pVo.getName());
		}
		return vo;

	}

	@Override
	public int getOrgCountBySuperOrg(FbrpSecOrg superOrg) throws FbrpException {
		return getOrgCountBySuperOrg(superOrg, Boolean.FALSE);
	}

	@Override
	public int getOrgCountBySuperOrg(FbrpSecOrg superOrg, Boolean delFlag) {
		// List countList;
		// StringBuffer hql = new StringBuffer();
		PMap pm = this.newMapInstance();
		if (superOrg == null) {
			/*
			 * hql.append(
			 * "select count(id) from FbrpSecOrgVO where parentId is null"); if
			 * (delFlag != null) { hql.append(" and delFlag='")
			 * .append(delFlag.booleanValue() ? ValueObject.DEL_FLAG_DELETED :
			 * ValueObject.DEL_FLAG_NORMAL).append("'"); } countList =
			 * this.getHibernateTemplate().find(hql.toString());
			 */
			pm.isNull("parentId");
			if (delFlag != null) {
				pm.eq("delFlag",
						delFlag.booleanValue() ? ValueObject.DEL_FLAG_DELETED
								: ValueObject.DEL_FLAG_NORMAL);
			}

		} else {

			/*
			 * hql.append("select count(id) from FbrpSecOrgVO where parentId=? ")
			 * ; if (delFlag != null) { hql.append(" and delFlag='")
			 * .append(delFlag.booleanValue() ? ValueObject.DEL_FLAG_DELETED :
			 * ValueObject.DEL_FLAG_NORMAL).append("'"); } String type =
			 * superOrg.getExt2(); if (type != null && !"".equals(type.trim()))
			 * { // map.put("type", superOrg.getExt2()); if
			 * (FbrpSecOrgVO.KIND_ORG.equals(type)) {
			 * hql.append(" and orgType='").append(FbrpSecOrgVO.KIND_ORG)
			 * .append("'"); } else if (FbrpSecOrgVO.KIND_DEPT.equals(type)) {
			 * hql.append(" and (orgType='") .append(FbrpSecOrgVO.KIND_DEPT)
			 * .append("' or orgType='").append("TEAM") .append("')"); } }
			 * countList = this.getHibernateTemplate().find(hql.toString(),
			 * superOrg.getId());
			 */
			pm.eq("parentId", superOrg.getId());
			if (delFlag != null) {
				pm.eq("delFlag",
						delFlag.booleanValue() ? ValueObject.DEL_FLAG_DELETED
								: ValueObject.DEL_FLAG_NORMAL);
			}
			String type = superOrg.getExt2();
			if (hasText(type)) {
				if (FbrpSecOrg.KIND_ORG.equals(type)) {
					pm.eq("orgType", FbrpSecOrg.KIND_ORG);
				} else if (FbrpSecOrg.KIND_DEPT.equals(type)) {
					pm.in("orgType", new String[] { FbrpSecOrg.KIND_DEPT,
							"TEAM" });
				}
			}

		}
		return (int) this.count(pm);
	}

	@Override
	public FbrpSecOrg save(FbrpSecOrg orgVO, UserProfileVO user) {
		/*
		 * String hql = "from FbrpSecOrgVO where code=?"; List<FbrpSecOrgVO>
		 * list = this.getHibernateTemplate().find(hql, orgVO.getCode()); if
		 * (list != null && !list.isEmpty()) { FbrpSecOrgVO vo = list.get(0); if
		 * (vo.getDelFlag().equals(FbrpSecOrgVO.DEL_FLAG_DELETED)) {
		 * this.getHibernateTemplate().delete(vo); } else { throw new
		 * FbrpException("机构编号已存在!"); } }
		 */

		FbrpSecOrg vo = this.findFirstBy("code", orgVO.getCode());
		if (vo != null) {
			if (FbrpSecOrg.DEL_FLAG_DELETED.equals(vo.getDelFlag())) {
				this.delete(vo);
			} else {
				throw new FbrpException("机构编号已存在!");
			}
		}
		orgVO.setId(this.getKeyGen().getUUIDKey());
		orgVO.setDelFlag(FbrpSecOrg.DEL_FLAG_NORMAL);
		if (user != null) {
			orgVO.setCreatorId(user.getStaffId());
		}
		orgVO.setCreatedTime(this.getServerTime().getServerDateTime());
		preparedProperties(orgVO);

		// this.getHibernateTemplate().save(orgVO);
		this.create(orgVO);
		// 调用IOrgAdapter对机构信息进行同步保存
		List staffAdapters = ContextUtil
				.getBeansByClass(ISynOrgInfoToThirdParty.class);
		Iterator itAd = staffAdapters.iterator();
		while (itAd.hasNext()) {
			((ISynOrgInfoToThirdParty) itAd.next()).save(orgVO);
		}
		return orgVO;
	}

	private void preparedProperties(FbrpSecOrg orgVO) {
		if (orgVO.getParentId() == null || "".equals(orgVO.getParentId())) {
			orgVO.setParentId(null);
		}
		if (orgVO.getStatus() == null) {
			orgVO.setStatus(FbrpSecOrg.STATUS_DEFAULT);
		}
		if (orgVO.getOrgType() == null) {
			orgVO.setOrgType(FbrpSecOrg.KIND_DEFAULT);
		}
		if (orgVO.getDelFlag() == null) {
			orgVO.setDelFlag(FbrpSecOrg.DEL_FLAG_NORMAL);
		}
	}

	@Override
	public List save(List orgs, UserProfileVO user) throws FbrpException {
		for (Iterator it = orgs.iterator(); it.hasNext();) {
			save((FbrpSecOrg) it.next(), user);
		}
		return orgs;
	}

	@Override
	public FbrpSecOrg update(FbrpSecOrg orgVo, UserProfileVO user)
			throws FbrpException {
		if (user != null) {
			orgVo.setLastModifierId(user.getStaffId());
			orgVo.setLastModifiedTime(new Date());
		}
		orgVo.setLastModifiedTime(this.getServerTime().getServerDateTime());
		preparedProperties(orgVo);
		// this.getHibernateTemplate().update(orgVo);
		this.update(orgVo);
		// 调用IOrgAdapter对机构信息进行同步保存
		List synOrgInfoToThirdPartyList = ContextUtil
				.getBeansByClass(ISynOrgInfoToThirdParty.class);
		Iterator itAd = synOrgInfoToThirdPartyList.iterator();
		while (itAd.hasNext()) {
			if (ValueObject.DEL_FLAG_NORMAL.equals(orgVo.getDelFlag())) {
				((ISynOrgInfoToThirdParty) itAd.next()).update(orgVo);
			} else {
				((ISynOrgInfoToThirdParty) itAd.next()).delete(orgVo);
			}
		}
		return orgVo;
	}

	@Override
	public List getAllOrg() throws FbrpException {
		/*
		 * return this.getHibernateTemplate().find(
		 * "from FbrpSecOrgVO where delFlag='n' ");
		 */
		List<FbrpSecOrg> list = this.findBy("delFlag", "n");
		return list;
	}

	/**
	 * 通过机构ID集合找到对应的机构集合。
	 * 
	 * @param idList
	 *            机构ID集合
	 * @return 机构集合
	 */
	public List<FbrpSecOrg> getAllOrgByIdList(List<String> idList) {
		/*
		 * List<FbrpSecOrgVO> fbrpSecOrgVOList = null; fbrpSecOrgVOList =
		 * this.queryAllByIn_Hibernate(FbrpSecOrgVO.class, "id", idList);
		 */
		List<FbrpSecOrg> fbrpSecOrgVOList = this.findByIds(idList);
		return fbrpSecOrgVOList;
	}

	@Override
	public FbrpSecOrg getOrgByStaffid(String staffid) {
		/*
		 * String hql =
		 * "select org from FbrpSecOrgMemberVO orgmember,FbrpSecOrgVO org where  orgmember.orgId=org.id and orgmember.staffId=? and orgmember.relationType='0'"
		 * ; List list = this.getHibernateTemplate().find(hql, staffid); if
		 * (list != null && !list.isEmpty()) { return (FbrpSecOrgVO)
		 * list.get(0); } else { return null; }
		 */
		QueryBuilder builder = this.selectFrom(FbrpSecOrgMember.class, "m",
				FbrpSecOrg.class, "o");
		builder.where("m.orgId=o.id");
		Executor<FbrpSecOrg> executor = builder.builtFor(FbrpSecOrg.class);
		PMap pm = this.newMapInstance();
		pm.eq("m.staffId", staffid);
		pm.eq("m.relationType", "0");
		return executor.findFirstBy(pm);
	}

	@Override
	public List getStaffListByOrgid(String orgid) {
		return getStaffListByOrgid(orgid, 0, 9999);
	}

	@Override
	public List getStaffListByOrgid(String orgid, int offset, int pageSize) {
		/*
		 * StringBuffer hql = new StringBuffer(); hql.append(
		 * "select staff from FbrpSecOrgMemberVO orgmember,FbrpSecStaffVO staff "
		 * ) .append("where orgmember.staffId=staff.id ")
		 * .append("and orgmember.orgId='").append(orgid).append("'"); Object[]
		 * _result = this.find_Hibernate_ComposedHQL(hql.toString(), null, null,
		 * null, offset, pageSize, true, true, null, null, null); return (List)
		 * _result[0];
		 */
		QueryBuilder builder = this.selectFrom(FbrpSecOrgMember.class, "m",
				FbrpSecStaff.class, "s");
		builder.where("m.staffId=s.id");
		Executor<FbrpSecStaff> executor = builder.builtFor(FbrpSecStaff.class);
		return executor.findBy("m.orgId", orgid);
	}

	@Override
	public void saveOrgMember(String orgid, String staffids) {
		// deleteOrgMemberByOrgid(orgid);
		// String[] staffid = staffids.split(",");
		// for(int i=0;i<staffid.length;i++){
		FbrpSecOrgMember vo = new FbrpSecOrgMember();
		vo.setOrgId(orgid);
		vo.setStaffId(staffids);
		vo.setId(this.getKeyGen().getUUIDKey());
		vo.setStatus(FbrpSecOrgMember.DEFAULT_STATUS);
		vo.setDelFlag(FbrpSecOrgMember.DEFAULT_DELFLAG);
		this.deleteOrgMemberByOrgidAndStaffid(orgid, staffids);
		String statement = this.getStatement("insert");
		this.getSqlSessionTemplate().insert(statement, vo);
		// this.getHibernateTemplate().save(vo);
		// }
	}

	@Override
	public void deleteOrgMemberByStaffid(String staffid) {
		/*
		 * String hql = "delete from FbrpSecOrgMemberVO where staffId=?";
		 * this.getHibernateTemplate().bulkUpdate(hql, staffid);
		 */
		String statement = this.getStatement("deleteByStaffId");
		this.getSqlSessionTemplate().delete(statement, staffid);
	}

	@Override
	public void deleteOrgMemberByOrgidAndStaffid(String orgid, String staffid) {
		/*
		 * String hql =
		 * "delete from FbrpSecOrgMemberVO where orgId=? and staffId=? ";
		 * this.getHibernateTemplate().bulkUpdate(hql, orgid, staffid);
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgid", orgid);
		map.put("staffid", staffid);
		String statement = this.getStatement("deleteByOrgIdAndStaffId");
		this.getSqlSessionTemplate().delete(statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FbrpSecOrg> getOwnerOrg() {
		/*
		 * List<FbrpSecOrgVO> list = (List<FbrpSecOrgVO>) this
		 * .getHibernateTemplate().execute(new HibernateCallback() { public
		 * Object doInHibernate(Session session) throws HibernateException,
		 * SQLException { Query query = session .createQuery(
		 * "select id from FbrpSecOrgVO where parentId is null and delFlag = ?"
		 * ); query.setString(0, ValueObject.DEL_FLAG_NORMAL); Criteria criteria
		 * = session .createCriteria(FbrpSecOrgVO.class); List<String> idList =
		 * query.list();
		 * 
		 * if (idList == null || idList.size() < 1) { return null; }
		 * criteria.add(Restrictions.in("id", idList)); return criteria.list();
		 * }
		 * 
		 * });
		 */
		String statement = this.getStatement("select_id");
		List<FbrpSecOrg> vos = this.getSqlSessionTemplate()
				.selectList(statement, ValueObject.DEL_FLAG_NORMAL);
		if (vos == null || vos.size() < 1) {
			return null;
		}
		return vos;
	}

	// TODO ljx-lxc 定义了sql语句没有使用
	@SuppressWarnings("unchecked")
	@Override
	public void deleteOrgFlag(final FbrpSecOrg updateFbrpSecOrgVO,
			UserProfileVO user) {
		// FbrpSecOrgVO vo = this.getHibernateTemplate().get(FbrpSecOrgVO.class,
		// updateFbrpSecOrgVO.getId());
		FbrpSecOrg vo = this.find(updateFbrpSecOrgVO.getId());
		vo.setDelFlag(ValueObject.DEL_FLAG_DELETED);
		// this.getHibernateTemplate().update(vo);
		this.update(vo);
		// String sql = "delete from FbrpSecOrgMemberVO where orgId=? ";
		this.deleteOrgChild(Arrays.asList(vo), user);
	}

	/**
	 * 删除机构子节点。
	 * 
	 * @param list
	 *            List
	 * @param user
	 *            UserProfileVO
	 */
	public void deleteOrgChild(List<FbrpSecOrg> list, UserProfileVO user) {
		// List<FbrpSecOrgVO> voList =
		// this.getHibernateTemplate().find("from FbrpSecOrgVO where parentId=? ",fbrpSecOrgVO.getId());
		List<FbrpSecOrg> voList = this.findBy("parentId",
				VOUtils.extractIds(list));
		for (FbrpSecOrg vo : voList) {
			vo.setDelFlag(ValueObject.DEL_FLAG_DELETED);
			// this.getHibernateTemplate().update(vo);
			this.update(vo);
			/*
			 * String sql = "delete from FbrpSecOrgMemberVO where orgId=? ";
			 * this.getHibernateTemplate().bulkUpdate(sql, vo.getId());
			 */
			String statement = this.getStatement("deleteByOrgId");
			this.getSqlSessionTemplate().delete(statement, vo.getId());
			this.deleteOrgChild(Arrays.asList(vo), user);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FbrpSecOrg> getAllOrgChild(String id, String delFlag) {
		// FbrpSecOrgVO vo =
		// this.getHibernateTemplate().get(FbrpSecOrgVO.class,id);
		FbrpSecOrg vo = this.find(id);
		// String sql="from FbrpSecOrgVO where parentId=? and delFlag=?";
		// List<FbrpSecOrgVO> voList =
		// this.getHibernateTemplate().find(sql,vo.getId(),delFlag);
		PMap pm = this.newMapInstance();
		pm.eq("parentId", vo.getId());
		pm.eq("delFlag", delFlag);
		List<FbrpSecOrg> voList = this.findBy(pm);
		List<FbrpSecOrg> resultList = new ArrayList<FbrpSecOrg>();
		if (voList != null && voList.size() > 0) {
			for (FbrpSecOrg fbrpSecOrgVO : voList) {
				resultList.addAll(this.getAllOrgChild(fbrpSecOrgVO.getId(),
						delFlag));
			}
		}
		voList.addAll(resultList);
		return voList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getStaffByOrgId(final String id) {
		String statement = this.getStatement("select_staffId");
		List<String> list = this.getSqlSessionTemplate()
				.selectList(statement, id);
		return list;
	}

	/**
	 * 根据主键获得FbrpSecOrgVO对象。
	 * 
	 * @param id
	 *            String
	 * @return FbrpSecOrgVO
	 */
	public FbrpSecOrg getFbrpSecOrgVOById(String id) {
		// return this.getHibernateTemplate().get(FbrpSecOrgVO.class, id);
		return this.find(id);
	}

	@Override
	public void deleteStaffForOrg(String staffId, String orgId) {
		/*
		 * StringBuffer sb = new StringBuffer(); sb.append(" delete from ");
		 * sb.append(FbrpSecOrgMemberVO.class.getName()).append(" t ");
		 * sb.append(" where t.staffId=? and t.orgId=? ");
		 * getHibernateTemplate().bulkUpdate(sb.toString(), staffId, orgId);
		 */
		String statement = this.getStatement("deleteByOrgIdAndStaffId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffid", staffId);
		map.put("orgid", orgId);
		this.getSqlSessionTemplate().delete(statement, map);
	}

	@Override
	public void updateToOrg(String orgId, List<FbrpSecStaff> list,
			String relationType) {
		deleteRoleMemberByorgId(orgId, relationType);
		// TODO xiaocheng_lu 目前是遍历插入，需改进
		for (FbrpSecStaff vo : list) {
			FbrpSecOrgMember rm = createOrgMember();
			rm.setStaffId(vo.getId());
			rm.setOrgId(orgId);
			rm.setRelationType(relationType);
			// getHibernateTemplate().save(rm);
			String statement = this.getStatement("insert");
			this.getSqlSessionTemplate().insert(statement, rm);
		}
	}

	private void deleteRoleMemberByorgId(String orgId, String relationType) {
		/*
		 * String hql =
		 * "delete from FbrpSecOrgMemberVO where orgId=? and relationType=?";
		 * this.getHibernateTemplate().bulkUpdate(hql, orgId,relationType);
		 */
		String statement = this.getStatement("deleteByOrgIdAndRelationType");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("relationType", relationType);
		this.getSqlSessionTemplate().delete(statement, map);
	}

	@Override
	public void updateToOrg(String orgId, List<FbrpSecStaff> list) {
		deleteRoleMemberByStaffId(orgId);
		// TODO xiaocheng_lu 目前是遍历插入，需改进
		for (FbrpSecStaff vo : list) {
			FbrpSecOrgMember rm = createOrgMember();
			rm.setStaffId(vo.getId());
			rm.setOrgId(orgId);
			// getHibernateTemplate().save(rm);
			String statement = this.getStatement("insert");
			this.getSqlSessionTemplate().insert(statement, rm);
		}
	}

	/**
	 * 通过StaffId删除RoleMember。
	 * 
	 * @param orgId
	 *            String
	 */
	public void deleteRoleMemberByStaffId(String orgId) {
		/*
		 * String hql = "delete from FbrpSecOrgMemberVO where orgId=?";
		 * this.getHibernateTemplate().bulkUpdate(hql, orgId);
		 */
		String statement = this.getStatement("deleteByOrgId");
		this.getSqlSessionTemplate().delete(statement, orgId);
	}

	private FbrpSecOrgMember createOrgMember() {
		FbrpSecOrgMember newMember = new FbrpSecOrgMember();

		newMember.setId(getKeyGen().getUUIDKey());
		newMember.setCreatedTime(new Date());
		newMember.setStatus(FbrpSecRoleMember.DEFAULT_STATUS);
		newMember.setDelFlag(FbrpSecRoleMember.DEFAULT_DELFLAG);
		UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		if (user != null) {
			newMember.setCreatorId(user.getLoginId());
		}
		return newMember;
	}

	/**
	 * 获取该机构下的直接子机构。
	 * 
	 * @param orgId
	 *            如果orgId为空，则查顶级机构
	 * 
	 * @return List&lt;FbrpSecOrgVO&gt; 子机构集合
	 */
	public List<FbrpSecOrg> getChildOrgById(String orgId) {
		List tempOrgList = null;
		if (StringUtil.isEmpty(orgId)) {
			/*
			 * hql =
			 * "select org, (select count(*) from FbrpSecOrgVO where parentId=org.id  and delFlag='n') from FbrpSecOrgVO org where org.parentId is null and delFlag='n'"
			 * ; tempOrgList = this.getHibernateTemplate().find(hql);
			 */
			String statement = this.getStatement("select_orgAndCount1");
			tempOrgList = this.getSqlSessionTemplate().selectList(statement);

		} else {
			/*
			 * hql =
			 * "select org, (select count(*) from FbrpSecOrgVO where parentId=org.id and delFlag='n') from FbrpSecOrgVO org where org.parentId=? and delFlag='n'"
			 * ; tempOrgList = this.getHibernateTemplate().find(hql, orgId);
			 */
			String statement = this.getStatement("select_orgAndCount2");
			tempOrgList = this.getSqlSessionTemplate().selectList(statement,
					orgId);
		}

		List<FbrpSecOrg> secOrgList = new ArrayList<FbrpSecOrg>();
		if (tempOrgList != null && tempOrgList.size() > 0) {
			for (int i = 0; i < tempOrgList.size(); i++) {
				FbrpSecOrg vo = (FbrpSecOrg) tempOrgList.get(i);
				secOrgList.add(vo);
			}
		}
		return secOrgList;
	}
	
}