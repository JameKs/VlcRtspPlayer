/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.persistence.query.Executor;
import com.mqm.frame.infrastructure.persistence.query.QueryBuilder;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.role.vo.FbrpSecRoleVO;
import com.mqm.frame.security.synchro.ISynRoleInfoToThirdParty;
import com.mqm.frame.util.exception.FbrpException;
import com.mqm.frame.util.hibernate.hql.HqlWrapper;

/**
 * 
 * <pre>
 * 角色管理服务实现。
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
public class RoleServiceImpl extends MyBatisServiceImpl<FbrpSecRole> implements IRoleService {

	private IGrantService grantService;

	/**
	 * 得到所有的记录。
	 * 
	 * @param aPageSize
	 *            int
	 * 
	 * @return Object[] 所有记录集合
	 * 
	 * @throws FbrpException
	 *             异常
	 */
	public Object[] getAll(int aPageSize) throws FbrpException {
		PagedResult<FbrpSecRole> pagedQuery = this.pagedQuery("delFlag", FbrpSecRole.DEFAULT_DELFLAG, 0, aPageSize);
		Object[] objArray = { pagedQuery.getData(), pagedQuery.getTotal() };
		return objArray;
	}

	/**
	 * 获得用户的角色集合。
	 * 
	 * @param staffId
	 *            用户ID
	 * 
	 * @return List
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public List getRolesByStaffId(String staffId) throws FbrpException {
		QueryBuilder builder = this.selectFrom(FbrpSecRoleMember.class, "rm", FbrpSecRole.class, "r").where(
				"rm.roleId=r.id");
		Executor<FbrpSecRole> executor = builder.builtFor(FbrpSecRole.class);
		PMap pm = this.newMapInstance();
		pm.eq("rm.staffId", staffId);
		pm.eq("rm.delFlag", ValueObject.DEL_FLAG_NORMAL);
		pm.eq("r.delFlag", ValueObject.DEL_FLAG_NORMAL);
		List<FbrpSecRole> list = executor.findBy(pm);
		if (list == null) {
			return new ArrayList();
		}
		return list;
	}

	@Override
	public FbrpSecRole getRoleByRoleId(String roleId) throws FbrpException {
		if (roleId == null || roleId.length() == 0) {
			return null;
		}
		return this.findFirstBy("id", roleId);
	}

	/**
	 * 设置grantService。
	 * 
	 * @param grantService
	 *            IGrantService
	 */
	public void setGrantService(IGrantService grantService) {
		this.grantService = grantService;
	}

	@Override
	public void saveOrUpdate(FbrpSecRole fbrpSecRoleVO) {
		this.createOrUpdate(fbrpSecRoleVO);

		// 新增或更新组
		List synRoleInfoToThirdPartyList = ContextUtil.getBeansByClass(ISynRoleInfoToThirdParty.class);
		Iterator itAd = synRoleInfoToThirdPartyList.iterator();
		while (itAd.hasNext()) {
			((ISynRoleInfoToThirdParty) itAd.next()).save(fbrpSecRoleVO);
		}
	}

	@Override
	public FbrpSecRole getRoleById(String id) {
		return this.find(id);
	}

	/**
	 * 角色编码是否存在。
	 * 
	 * @param code
	 *            String
	 * 
	 * @return boolean
	 */
	public boolean getRoles(String code) {
		PMap pm = this.newMapInstance();
		pm.eq("delFlag", "n");
		pm.eq("code", code);
		List<FbrpSecRole> list = this.findBy(pm);

		if (list != null && list.size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据主键删除角色。
	 * 
	 * @param id
	 *            String
	 */
	public void deleteById(String id) {
		FbrpSecRole vo = this.find(id);
		vo.setDelFlag("y");
		this.update(vo);
		// 删除组
		List synRoleInfoToThirdPartyList = ContextUtil.getBeansByClass(ISynRoleInfoToThirdParty.class);
		Iterator itAd = synRoleInfoToThirdPartyList.iterator();
		FbrpSecRole roleVO = this.getRoleById(id);
		while (itAd.hasNext()) {
			((ISynRoleInfoToThirdParty) itAd.next()).deleteGroupById(roleVO.getCode());
		}
	}

	/**
	 * 根据条件查询记录结果，并统计记录数。
	 * 
	 * @param fbrpSecRoleVO
	 *            FbrpSecRole
	 * @param offSet
	 *            int
	 * @param pageSize
	 *            int
	 * 
	 * @return Object[]
	 */
	public Object[] queryByParam(FbrpSecRole fbrpSecRoleVO, int offSet, int pageSize) {
		PMap pm = this.newMapInstance();
		if (fbrpSecRoleVO != null) {
			pm.includeIgnoreCase("code", fbrpSecRoleVO.getCode());
			pm.includeIgnoreCase("name", fbrpSecRoleVO.getName());
			pm.includeIgnoreCase("remark", fbrpSecRoleVO.getRemark());
		}
		pm.eq("delFlag", "n");
		FbrpSecRole roleVO = grantService.getAppAdminRole();
		pm.ne("id", roleVO.getId());
		
		PagedResult<FbrpSecRole> pagedQuery = this.pagedQuery(pm, offSet, pageSize);
		
		Long count = pagedQuery.getTotal();
		Object[] objectArray = { pagedQuery.getData(), count.intValue() };
		return objectArray;
	}

	/**
	 * 检查。
	 * 
	 * @param id
	 *            String
	 * @param propName
	 *            String
	 * @param value
	 *            String
	 * 
	 * @return boolean
	 */
	private boolean check(String id, String propName, String value) {
		PMap pm = this.newMapInstance();
		pm.eq("delFlag", FbrpSecRole.DEL_FLAG_NORMAL);
		pm.eq(propName, value);
		if (id != null) {
			pm.ne("id", id);
		}
		return this.count(pm) > 0;
	}

	@Override
	public boolean checkNameExist(String id, String name) {
		return check(id, "name", name);
	}

	@Override
	public boolean checkCodeExist(String id, String code) {
		return check(id, "code", code);
	}

	/**
	 * 只返回count。
	 * 
	 * @param hqlWrapper
	 *            HqlWrapper
	 * 
	 * @return int
	 */
	private int countOnly(HqlWrapper hqlWrapper) {
		return 0;
	}

	@Override
	// TODO ljx-lxc 这个方法本身有问题
	public List<FbrpSecRole> findRoleByAppId(String appId) {
		return null;
	}

	@Override
	public Object[] findRolesByIds(List ids, int offset, int pageSize) {
		if (ids == null || ids.size() == 0) {
			return null;
		}
		PMap pm = this.newMapInstance();
		pm.in("id", ids);
		PagedResult<FbrpSecRole> pagedQuery = this.pagedQuery(pm, offset, pageSize);
		Object[] objArray = { pagedQuery.getData(), pagedQuery.getTotal() };
		return objArray;
	}

	@Override
	public  PagedResult<FbrpSecRole> queryByParamFilterAdminRole(FbrpSecRole fbrpSecRoleVO, int offSet, int pageSize,String userLoginOrgId) {
		PMap pm = this.newMapInstance();
		if (fbrpSecRoleVO != null) {
			pm.includeIgnoreCase("code", fbrpSecRoleVO.getCode());
			pm.includeIgnoreCase("name", fbrpSecRoleVO.getName());
		}
		FbrpSecRole roleVO = grantService.getAppAdminRole();
		Map<String,String> param = new HashMap<String,String>();
		param.put("id", roleVO.getId()	);
		param.put("delFlag", "n");
		param.put("orgCode", userLoginOrgId);
		param.put("code",  fbrpSecRoleVO.getCode());
		param.put("name", fbrpSecRoleVO.getName());
		param.put("remark", fbrpSecRoleVO.getRemark());
		PagedResult<FbrpSecRole> pr = super.pagedQuery("selectRoleList", param, offSet, pageSize);
		return pr;
	}

	@Override
	public PagedResult<FbrpSecRole> pagedQuery(FbrpSecRole vo, int pageIndex, int pageSize, Order... orders) {
		return super.pagedQuery(toMap(vo), pageIndex, pageSize, orders);
	}

	private PMap toMap(FbrpSecRole vo) {
		PMap pm = this.newMapInstance();
		pm.includeIgnoreCase("code", vo.getCode());
		pm.includeIgnoreCase("name", vo.getName());
		pm.includeIgnoreCase("remark", vo.getRemark());
		return pm;
	}

	@Override
	public Object[] queryByStaffId(String staffId, int pageIndex, int pageSize) {
		QueryBuilder builder = this.selectFrom(FbrpSecRole.class, FbrpSecRoleMember.class).where("t1.id=t2.roleId");
		Executor<FbrpSecRole> executor = builder.builtFor(FbrpSecRole.class);
		PMap pm = this.newMapInstance();
		pm.ne("t1.delFlag", FbrpSecRole.DEL_FLAG_DELETED);
		pm.eq("t2.staffId", staffId);
		PagedResult<FbrpSecRole> pr = executor.pagedQuery(pm, pageIndex, pageSize);
		return new Object[] { pr.getData(), pr.getTotal() };
	}

	@Override
	public PagedResult<FbrpSecRoleVO> queryRole(int pageIndex, int pageSize, FbrpSecRoleVO role) {
		String key = "selectRole";
		PagedResult<FbrpSecRoleVO> result = super.pagedQuery(key, role, pageIndex, pageSize);
		return result;
	}

	@Override
	public PagedResult<FbrpSecRoleVO> queryRoleById(int pageIndex, int pageSize, FbrpSecRoleVO role) {
		String key = "selectRoleById";
		PagedResult<FbrpSecRoleVO> result = super.pagedQuery(key, role, pageIndex, pageSize);
		return result;
	}

	@Override
	public PagedResult<FbrpSecRoleVO> queryExistedRoleById(int pageIndex, int pageSize, FbrpSecRoleVO role) {
		String key = "selectExistedRoleById";
		PagedResult<FbrpSecRoleVO> result = super.pagedQuery(key, role, pageIndex, pageSize);
		return result;
	}

	@Override
	public void batchAddRoles(List<FbrpSecRoleMember> list) {
		this.batchCreate("addRole", list);
	}

	@Override
	public void batchUpdateRoles(List<FbrpSecRoleMember> list) {
		this.batchUpdate("deleteRole", list);
	}

	@Override
	public boolean ifExistCode(String code) {
	     Long result = this.count("code", code);
	     if(result > 0) {
	    	 return true;
	     } else {
	    	 return false;
	     }
	}

}
