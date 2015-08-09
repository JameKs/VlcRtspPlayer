/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.persistence.query.Executor;
import com.mqm.frame.infrastructure.persistence.query.QueryBuilder;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.security.role.service.IRoleMemberService;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.synchro.ISynRoleInfoToThirdParty;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 角色成员管理服务实现。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class RoleMemberServiceImpl extends MyBatisServiceImpl<FbrpSecRoleMember> implements
		IRoleMemberService {
	
	private IRoleService roleService;
	
	/**
	 * 设置IRoleService。
	 * 
	 * @param roleService IRoleService
	 */
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public List findRoleMemberByRoleId(String roleId) throws FbrpException {
		List list = new ArrayList();
		QueryBuilder builder = this.selectFrom(FbrpSecRoleMember.class,"rm",FbrpSecStaff.class,"s").where("rm.staffId=s.id");
		builder.fetchAssociation("staff");
		Executor<FbrpSecRoleMember> executor = builder.builtFor(FbrpSecRoleMember.class);
	    PMap pm = this.newMapInstance();
	    pm.eq("rm.roleId", roleId);
	    pm.ne("s.delFlag",FbrpSecStaff.DEL_FLAG_DELETED);
		List<FbrpSecRoleMember> list2 = executor.findBy(pm);
		if(list2!=null && !list2.isEmpty()){
			for(FbrpSecRoleMember rmVO:list2){
				FbrpSecStaff sVO = rmVO.getStaff();
				rmVO.setExt1(sVO.getName());
				list.add(rmVO);
			}
		}
		return list;
	}

	@Override
	public List findRoleMemberByStaffId(String staffId) throws FbrpException {
		PMap pm = this.newMapInstance();
		pm.eq("staffId", staffId);
		pm.eq("delFlag", FbrpSecRoleMember.DEFAULT_DELFLAG);	
		List<FbrpSecRoleMember>  list = this.findBy(pm);
		// 根据roleid查询FbrpSecRoleVO中角色名称。 20100916
		if (list != null && list.size() > 0) {			
			List<String> idList = new ArrayList<String>();
			for(FbrpSecRoleMember vo:list){
				idList.add(vo.getId());
			}
			PMap pm2 = this.newMapInstance();
			pm2.in("id", idList);
			List<FbrpSecRole>  groupList = this.roleService.findBy(pm2);			
			
			if (groupList != null && groupList.size() > 0) {
				Map map = new HashMap();
				for (int i = 0; i < groupList.size(); i++) {
					FbrpSecRole groupvo =  groupList.get(i);
					map.put(groupvo.getId(), groupvo.getName());
				}
				for (int i = 0; i < list.size(); i++) {
					FbrpSecRoleMember vo =  list.get(i);
					if (map.get(vo.getRoleId()) != null) {
						vo.setExt1((String) map.get(vo.getRoleId()));
					}
				}
			}
		}
		return list;
	}

	@Override
	public void deleteData(List deleteBeanList) throws FbrpException {
		List synRoleInfoToThirdPartyList = ContextUtil.getBeansByClass(ISynRoleInfoToThirdParty.class);

		if (deleteBeanList != null) {
			int size = deleteBeanList.size();
			for (int i = 0; i < size; i++) {
				FbrpSecRoleMember vo = (FbrpSecRoleMember) deleteBeanList.get(i);
				PMap pm = this.newMapInstance();
				pm.eq("roleId", vo.getRoleId());
				pm.eq("staffId", vo.getStaffId());
				this.deleteBy(pm);
				// 调用ISynRoleInfoToThirdParty进行角色信息同步保存
				Iterator itAd = synRoleInfoToThirdPartyList.iterator();
				while (itAd.hasNext()) {
					((ISynRoleInfoToThirdParty) itAd.next()).deleteMember(vo);
				}
			}
		}
	}
	
	/**
	 * 根据角色Id获角色成员列表。
	 * 
	 * @param roleId String
	 * 
	 * @return List
	 */
	public List getRoleMembersByRoleId(String roleId){
		PMap pm = this.newMapInstance();
		pm.eq("roleId",roleId);
		pm.eq("delFlag","n");
		List<FbrpSecRoleMember> list = this.findBy(pm);
		return list;
	}
	
	/**
	 * 根据人员Id获取数据。
	 * 
	 * @param staffId String
	 * 
	 * @return List
	 */
	public List getRoleMembersByStaffId(String staffId){
		PMap pm = this.newMapInstance();
		pm.eq("staffId", staffId);
		pm.eq("delFlag", "n");
		List<FbrpSecRoleMember> list = this.findBy(pm);
		return list;		
	}
	
	/**
	 * 保存角色成员列表。
	 * 
	 * @param list List
	 */
	public void saveOrUpdateAll(List<FbrpSecRoleMember> list){
		List<FbrpSecRoleMember> list1 = new ArrayList<FbrpSecRoleMember>();
		for (FbrpSecRoleMember vo : list) {
			if (StringUtil.isStrEmpty(vo.getId())) {
				vo.setId(getKeyGen().getUUIDKey());
				list1.add(vo);
			}
		}
		list.removeAll(list1);
		if(list1.size()>0){
			this.batchCreate(list1);
		}
		if(list.size()>0){
			this.batchUpdate(list);
		}
		
	} 
	
	/**
	 * 根据主键删除。
	 * 
	 * @param id String
	 */
	public void deleteById(String id){
		FbrpSecRoleMember vo = this.find(id);
		vo.setDelFlag("y");
		this.update(vo);
	}
	
	/**
	 * 批量删除。
	 * 
	 * @param list List
	 * 
	 * @return int
	 */
	public int deleteAll(List list) {
		int i = 0;
		for (; i < list.size(); i++) {
			this.deleteById((String) list.get(i));
		}
		return i;
	}
	
	private FbrpSecRoleMember createRoleMember() {
		FbrpSecRoleMember newMember = new FbrpSecRoleMember();
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
	
	@Override
	public void updateToStaff(String staffId,List<FbrpSecRole> list){		
		deleteRoleMemberByStaffId(staffId);
		//TODO xiaocheng_lu 目前是遍历插入，需改进
		for(FbrpSecRole vo : list){
			FbrpSecRoleMember rm = createRoleMember();
			rm.setStaffId(staffId);
			rm.setRoleId(vo.getId());
			this.create(rm);
		}
		List synRoleInfoToThirdPartyList = ContextUtil.getBeansByClass(ISynRoleInfoToThirdParty.class);
		// 调用ISynRoleInfoToThirdParty进行角色信息同步保存
		Iterator itAd = synRoleInfoToThirdPartyList.iterator();
		while (itAd.hasNext()) {
			((ISynRoleInfoToThirdParty) itAd.next()).synUserRoleInfos(staffId, list);
		}
	}
	
	@Override
	public void deleteRoleMemberByStaffId(String staffId){
	    this.deleteBy("staffId", staffId);
	}
	
	@Override
	public void deleteAllRoleMemberByStaffId(String staffId) {
		this.deleteBy("staffId", staffId);
	}

	@Override
	public void updateToRole(String roleId,List<FbrpSecStaff> list){
		deleteRoleMemberByRoleId(roleId);
		//TODO xiaocheng_lu 目前是遍历插入，需改进
		for(FbrpSecStaff vo : list){
			FbrpSecRoleMember rm = createRoleMember();
			rm.setStaffId(vo.getId());
			rm.setRoleId(roleId);
			this.create(rm);
		}
		List synRoleInfoToThirdPartyList = ContextUtil.getBeansByClass(ISynRoleInfoToThirdParty.class);
		// 调用ISynRoleInfoToThirdParty进行角色信息同步保存
		Iterator itAd = synRoleInfoToThirdPartyList.iterator();
		while (itAd.hasNext()) {
			((ISynRoleInfoToThirdParty) itAd.next()).synUserRoleInfosForRoleId(roleId, list);
		}
	}

	@Override
	public void deleteRoleMemberByRoleId(String roleId) {
		this.deleteBy("roleId", roleId);
	}
	
}
