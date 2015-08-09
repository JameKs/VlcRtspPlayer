/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.dbtool.service.ISqlExecutionEngineService;
import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.persistence.query.Executor;
import com.mqm.frame.infrastructure.persistence.query.QueryBuilder;
import com.mqm.frame.infrastructure.service.IKeyGen;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.security.org.service.IOrgService;
import com.mqm.frame.security.org.vo.FbrpSecOrg;
import com.mqm.frame.security.org.vo.FbrpSecOrgMember;
import com.mqm.frame.security.role.service.IRoleMemberService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.service.ISecurityStrategyService;
import com.mqm.frame.security.service.IUserDetailsService;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.staff.service.IStaffYwService;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.staff.vo.GyUuvOrgVO;
import com.mqm.frame.security.staff.vo.StaffOAVO;
import com.mqm.frame.security.staff.vo.StaffUumVO;
import com.mqm.frame.security.synchro.ISynStaffInfoToThirdParty;
import com.mqm.frame.security.synchro.ISynUserInfoToThirdParty;
import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 人员管理服务实现。
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
public class StaffServiceImpl extends MyBatisServiceImpl<FbrpSecStaff>
		implements IStaffService {

	private static final Log log = LogFactory.getLog(StaffServiceImpl.class);

	private ISecurityStrategyService securityStrategyService;
	private IUserDetailsService userDetailService;
	private IOrgService orgService;
	private IStaffYwService staffYwService;
	
	private IRoleMemberService roleMemberService;

	private ISqlExecutionEngineService sqlExecutionEngineService;
	
	@Override
	public PagedResult<FbrpSecStaff> queryStaffByRole(FbrpSecRole role,
			int offset, int pageSize) {
		QueryBuilder builder = this.selectFrom(FbrpSecStaff.class,
				FbrpSecRoleMember.class);
		builder.where("t1.id=t2.staffId");
		Executor<FbrpSecStaff> execute = builder.builtFor(FbrpSecStaff.class);
		PMap pm = this.newMapInstance();
		pm.ne("t1.delFlag", FbrpSecStaff.DEL_FLAG_DELETED);
		pm.eq("t2.roleId", role.getId());
		return execute.pagedQuery(pm, offset, pageSize);
	}

	@Override
	public boolean checkCodeExist(String id, String code) {
		return check(id, "code", code);
	}

	@Override
	public boolean checkLoginIdExist(String id, String loginId) {
		return check(id, "loginid", loginId);
	}

	private boolean check(String id, String propName, String value) {
		PMap pm = this.newMapInstance();
		pm.eq("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		pm.eq(propName, value);
		pm.ne("id", id);
		return this.count(pm) > 0;
	}

	@Override
	public void update(FbrpSecStaff staff) {
		checkStaff(staff);
		// 调用ISynStaffInfoToThirdParty进行员工信息同步更新
		List synStaffInfoToThirdPartyList = ContextUtil
				.getBeansByClass(ISynStaffInfoToThirdParty.class);
		Iterator itAd = synStaffInfoToThirdPartyList.iterator();
		while (itAd.hasNext()) {
			((ISynStaffInfoToThirdParty) itAd.next()).update(staff);
		}
		super.update(staff);
	}

	private void checkStaff(FbrpSecStaff staff) throws FbrpException {
		if (staff.getId() == null || "".equals(staff.getId())) {
			// error message
			throw new FbrpException("");
		}
		if (staff.getOrgid() == null || !"".equals(staff.getOrgid())) {
			staff.setOrgid(null);
		}

		if (staff.getStatus() == null) {
			staff.setStatus(FbrpSecStaff.STATUS_DEFAULT);
		}
		if (staff.getDelFlag() == null) {
			staff.setDelFlag(FbrpSecStaff.DEL_FLAG_NORMAL);
		}
	}

	@Override
	public Object[] queryByParam(FbrpSecStaff conStaffVO, int offSet,
			int pageSize) {
		return queryByParam(conStaffVO, offSet, pageSize, false);
	}

	@Override
	public Object[] queryByParam(FbrpSecStaff staff, int offSet, int pageSize,
			boolean filterSupadmin) {
		PMap pm = this.newMapInstance();
		pm.includeIgnoreCase("code", staff.getCode());
		pm.includeIgnoreCase("name", staff.getName());
		pm.eq("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		String ext1FilterNonLoginId = staff.getExt1();
		if ("ext1FilterNonLoginId".equals(ext1FilterNonLoginId)) {
			pm.isNotNull("loginid");
		}
		if (filterSupadmin) {
			String adminStaffId = securityStrategyService.getAdminStaffId();
			pm.ne("id", adminStaffId);
		}
		PagedResult<FbrpSecStaff> pr = this.pagedQuery(pm, offSet, pageSize,
				desc("loginid"));
		Object[] objArray = new Object[] { pr.getData(), pr.getTotal() };
		for (FbrpSecStaff user : pr.getData()) {
			boolean isLock = userDetailService.checkAccountLock(user);
			user.setLocked(isLock ? "是" : "否");
		}
		return objArray;
	}

	@Override
	public Object[] queryStaffContainsOrg(FbrpSecStaff staff, int offSet,
			int pageSize, boolean filterSupadmin) {
		Object[] objArray = null;
		if (staff.getOrgid() == null && staff.getRoleid() == null) {
			PMap pm = this.newMapInstance();
			pm.includeIgnoreCase("code", staff.getCode());
			pm.includeIgnoreCase("name", staff.getName());
			pm.eq("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
			if (filterSupadmin) {
				String adminStaffId = securityStrategyService.getAdminStaffId();
				pm.ne("id", adminStaffId);
			}
			PagedResult<FbrpSecStaff> pagedQuery = this.pagedQuery(pm, offSet,
					pageSize, Order.DESC("loginid"));
			objArray = new Object[] { pagedQuery.getData(),
					pagedQuery.getTotal() };

		} else if (staff.getOrgid() != null && staff.getRoleid() == null) {
			QueryBuilder builder = this.selectFrom(FbrpSecStaff.class, "s",
					FbrpSecOrgMember.class, "m");
			builder.where("s.id=m.staffId");
			Executor<FbrpSecStaff> execute = builder
					.builtFor(FbrpSecStaff.class);
			PMap pm = this.newMapInstance();
			pm.eq("m.orgId", staff.getOrgid());
			pm.includeIgnoreCase("s.name", staff.getName());
			pm.eq("s.delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
			if (filterSupadmin) {
				String adminStaffId = securityStrategyService.getAdminStaffId();
				pm.ne("s.id", adminStaffId);
			}
			PagedResult<FbrpSecStaff> pagedQuery = execute.pagedQuery(pm,
					offSet, pageSize, Order.DESC("s.loginid"));
			objArray = new Object[] { pagedQuery.getData(),
					pagedQuery.getTotal() };
		} else if (staff.getOrgid() == null && staff.getRoleid() != null) {
			QueryBuilder builder = this.selectFrom(FbrpSecStaff.class, "s",
					FbrpSecRoleMember.class, "r").where("s.id = r.staffId");
			Executor<FbrpSecStaff> execute = builder
					.builtFor(FbrpSecStaff.class);
			PMap pm = this.newMapInstance();
			pm.eq("r.roleId", staff.getRoleid());
			pm.eq("s.code", staff.getCode());
			pm.eq("s.name", staff.getName());
			pm.eq("s.delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
			PagedResult<FbrpSecStaff> pagedQuery = execute.pagedQuery(pm,
					offSet, pageSize, Order.DESC("s.loginid"));
			objArray = new Object[] { pagedQuery.getData(),
					pagedQuery.getTotal() };
		} else if (staff.getOrgid() != null && staff.getRoleid() != null) {
			QueryBuilder builder = this.selectFrom(FbrpSecRoleMember.class,
					"r", FbrpSecOrgMember.class, "m").where("s.id = m.staffId");
			Executor<FbrpSecStaff> execute = builder
					.builtFor(FbrpSecStaff.class);
			PMap pm = this.newMapInstance();
			pm.eq("r.roleId", staff.getRoleid());
			pm.eq("m.orgId", staff.getOrgid());
			pm.eq("s.code", staff.getCode());
			pm.eq("s.name", staff.getName());
			pm.eq("s.delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
			if (filterSupadmin) {
				String adminStaffId = securityStrategyService.getAdminStaffId();
				pm.ne("s.id", adminStaffId);
			}
			PagedResult<FbrpSecStaff> pagedQuery = execute.pagedQuery(pm,
					offSet, pageSize, Order.DESC("s.loginid"));
			objArray = new Object[] { pagedQuery.getData(),
					pagedQuery.getTotal() };
		}

		List<FbrpSecStaff> staffList = (List) objArray[0];
		for (FbrpSecStaff staffVO : staffList) {
			boolean isLock = userDetailService.checkAccountLock(staffVO);
			staffVO.setLocked(isLock ? "是" : "否");
		}
		// TODO xiaocheng_lu 还需要添加机构信息
		if (staff.getOrgid() == null) {
			List<FbrpSecStaff> list = (List) objArray[0];
			for (int i = 0; i < list.size(); i++) {
				FbrpSecOrg org = this.orgService
						.getOrgByStaffid(list.get(i).getId());
				if (org != null) {
					list.get(i).setOrgName(org.getName());
				}
			}
		} else {
			FbrpSecOrg org = this.orgService.getOrgByOrgId(staff.getOrgid());
			List<FbrpSecStaff> list = (List) objArray[0];
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setOrgName(org.getName());
			}
		}

		return objArray;
	}

	@Override
	public void saveOrUpdate(FbrpSecStaff staffVO) {
		if (staffVO == null) {
			return;
		}
		this.createOrUpdate(staffVO);
	}

	@Override
	public void saveOrUpdateAll(List<FbrpSecStaff> staffs) {
		if (staffs == null || staffs.isEmpty()) {
			return;
		}
		List<FbrpSecStaff> list1 = new ArrayList<FbrpSecStaff>();
		for (FbrpSecStaff vo : staffs) {
			if (StringUtil.isStrEmpty(vo.getId())) {
				vo.setId(getKeyGen().getUUIDKey());
				list1.add(vo);
			}
		}
		staffs.removeAll(list1);
		if (list1.size() > 0) {
			this.batchCreate(list1);
		}
		if (staffs.size() > 0) {
			this.batchUpdate(staffs);
		}

	}

	@Override
	public void deleteStaffById(String id) {
		this.delete(id);
	}

	@Override
	public int delete(FbrpSecStaff staffVO) {
		return super.delete(staffVO);
	}

	@Override
	public void deleteStaffByLogic(FbrpSecStaff staffVO) {
		String staffId = staffVO.getId();
		this.delete(staffId);
		orgService.deleteOrgMemberByStaffid(staffId);
		roleMemberService.deleteAllRoleMemberByStaffId(staffId);
		// 调用ISynUserInfoToThirdParty适配器进行用户信息同步保存
		List synUserInfoToThirdPartyList = ContextUtil
				.getBeansByClass(ISynUserInfoToThirdParty.class);
		Iterator itAd = synUserInfoToThirdPartyList.iterator();
		while (itAd.hasNext()) {
			((ISynUserInfoToThirdParty) itAd.next()).delete(staffVO);
		}
	}

	@Override
	public int deleteAll(List<FbrpSecStaff> staffs) {
		return super.deleteAll(staffs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getStaffByParam(final FbrpSecStaff staffVO,
			final List<String> idList, final int pageIndex, final int pageSize) {
		PMap pm = this.newMapInstance();
		pm.ne("id", BaseConstants.DEFAULT_ADMIN_STAFFID);
		if (staffVO != null) {
			pm.include("code", staffVO.getCode());
			pm.include("name", staffVO.getName());
			pm.include("idNumber", staffVO.getIdNumber());
		}
		pm.eq("delFlag", FbrpSecOrg.DEL_FLAG_NORMAL);
		pm.in("id", idList);
		PagedResult<FbrpSecStaff> pr = this.pagedQuery(pm, pageIndex, pageSize);
		return new Object[] { pr.getData(), pr.getTotal() };
	}

	@Override
	public Object[] queryByRoleId(String roleId, int pageIndex, int pageSize) {
		QueryBuilder builder = this.selectFrom(FbrpSecStaff.class,
				FbrpSecRoleMember.class).where("t1.id=t2.staffId");
		Executor<FbrpSecStaff> executor = builder.builtFor(FbrpSecStaff.class);
		PMap pm = this.newMapInstance();
		pm.ne("t1.delFlag", FbrpSecStaff.DEL_FLAG_DELETED);
		pm.eq("t2.roleId", roleId);
		PagedResult<FbrpSecStaff> pr = executor.pagedQuery(pm, pageIndex,
				pageSize);
		return new Object[] { pr.getData(), pr.getTotal() };
	}

	@Override
	public Object[] query(int pageIndex, int pageSize, boolean filterSupadmin) {
		PMap pm = this.newMapInstance();
		pm.ne("delFlag", FbrpSecStaff.DEL_FLAG_DELETED);
		if (filterSupadmin) {
			String adminId = securityStrategyService.getAdminStaffId();
			pm.ne("id", adminId);
		}
		PagedResult<FbrpSecStaff> pr = this.pagedQuery(pm, pageIndex, pageSize);
		return new Object[] { pr.getData(), pr.getTotal() };
	}

	@Override
	public Object[] query(int offset, int pageSize) {
		return query(offset, pageSize, false);
	}
	
	/**
	 * 导入csv文件到人员表。
	 * 
	 * @param file
	 *            File
	 * 
	 * @return List<String>
	 * 
	 * @throws IOException IOException
	 */
	public List<String> saveCSVData(File file) throws IOException {
		List<String> messageList = new ArrayList<String>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Map<String, String> idMap = new HashMap<String, String>();
		Map<String, String> codeMap = new HashMap<String, String>();
		String msg = "";
		InputStreamReader in = new InputStreamReader(new FileInputStream(file));
		BufferedReader bufferedReader = new BufferedReader(in);
		String line = "";
		String[] headers = null;
		String[] values = null;
		boolean isFirstRow = false;
		int size = 0;
		int cycle = 0;
		int maxFailedLoginCount = securityStrategyService
				.getMaxFailedLoginCount();
		int passwordExpireDays = securityStrategyService
				.getPasswordExpireDays();
		// TODO xiaocheng_lu 导入的数据只精确到日期
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		while (bufferedReader.ready()) {
			valueMap = new HashMap<String, Object>();
			cycle++;
			if (!isFirstRow) {
				line = bufferedReader.readLine();
				headers = line.split(",");
				isFirstRow = true;
			} else {
				line = bufferedReader.readLine();
				values = line.split(",");
				for (int i = 0; i < headers.length; i++) {
					try {
						String headerStr = InternationalizationUtil.toUpperCase(headers[i]);
						if (headerStr.startsWith("\"")
								&& headerStr.lastIndexOf("\"") != -1) {
							headerStr = headerStr.substring(1,
									headerStr.length() - 1);
						}
						String valueStr = values[i];
						if (valueStr.startsWith("\"")
								&& valueStr.lastIndexOf("\"") != -1) {
							valueStr = valueStr.substring(1,
									valueStr.length() - 1);
						}

						valueMap.put(headerStr, valueStr);
					} catch (Exception e) {
						log.error("", e);
					}
				}
				// 判断编码在文件中是否为空
				if (this.isEmpty((String) valueMap.get("CODE"))) {
					msg = "第" + cycle + "行编码为空";
					messageList.add(msg);
					continue;
				}
				// 判断ID在文件中是否为空
				if (this.isEmpty((String) valueMap.get("ID"))) {
					msg = "第" + cycle + "行ID为空";
					messageList.add(msg);
					continue;
				}
				// 判断编码在文件中是否重复
				if (codeMap.containsKey(valueMap.get("CODE"))) {
					msg = "第" + cycle + "行编码存在重复,重复编码为：" + valueMap.get("CODE");
					messageList.add(msg);
					continue;
				} else {
					codeMap.put((String) valueMap.get("CODE"),
							(String) valueMap.get("CODE"));
				}
				// 判断ID在文件中是否重复
				if (idMap.containsKey(valueMap.get("ID"))) {
					msg = "第" + cycle + "行ID存在重复,重复ID为：" + valueMap.get("ID");
					messageList.add(msg);
					continue;
				} else {
					idMap.put((String) valueMap.get("ID"),
							(String) valueMap.get("ID"));
				}
				// 把一些数字类型的字段，从字符串转换为数字类型
				valueMap.put("VERSION", 0);
				valueMap.put("ONLINE_COUNT", 0);
				convertIntegerValue(valueMap, "FAILED_LOGIN_COUNT", 0);
				convertIntegerValue(valueMap, "MAX_FAILED_LOGIN_COUNT",
						maxFailedLoginCount);
				convertIntegerValue(valueMap, "PASSWD_EXPIRE_DAYS",
						passwordExpireDays);

				convertValue(valueMap, "STATUS", "y");
				convertValue(valueMap, "SEX", null);
				convertValue(valueMap, "TEL", null);
				convertValue(valueMap, "LOGIN_ID", null);
				convertValue(valueMap, "ID_NUMBER", null);
				convertValue(valueMap, "EMAIL", null);
				convertValue(valueMap, "ADDRESS", null);
				convertValue(valueMap, "BIRTHDAY", null);
				convertValue(valueMap, "PASSWD", null);
				convertValue(valueMap, "LAST_LOGIN_IP", null);
				convertValue(valueMap, "LOGIN_IP", null);
				convertValue(valueMap, "EXT1", null);
				convertValue(valueMap, "EXT2", null);
				convertValue(valueMap, "EXT3", null);
				convertValue(valueMap, "EXT4", null);
				convertValue(valueMap, "EXT5", null);
				convertValue(valueMap, "EXT6", null);
				convertValue(valueMap, "EXT7", null);
				convertValue(valueMap, "EXT8", null);
				convertValue(valueMap, "EXT9", null);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR,
						(Integer) valueMap.get("PASSWD_EXPIRE_DAYS"));
				try {
					// 对时间进行处理，把时间从字符串按照'yyyy-MM-dd'的格式，做处理
					Date time = calendar.getTime();
					convertDateValue(valueMap, "PASSWD_EXPIRE_TIME", null, sf);
					convertDateValue(valueMap, "ACCOUNT_EXPIRE_TIME", null, sf);
					convertDateValue(valueMap, "LOGIN_TIME", null, sf);
					convertDateValue(valueMap, "CREATED_TIME", time, sf);
					convertDateValue(valueMap, "LAST_MODIFIED_TIME", time, sf);
					convertDateValue(valueMap, "LAST_LOGIN_TIME", null, sf);
				} catch (Exception e) {
					log.error("", e);
					msg = "第" + cycle + "行日期格式不对";
					messageList.add(msg);
				}
				resultList.add(valueMap);
				valueMap.get("MAX_FAILED_LOGIN_COUNT");
				size++;
			}
			if (size == 500) {
				resultList = removeDupliateCode(resultList);
				try {
					size = 0;
					resultList.clear();
				} catch (Exception e) {
					log.error("", e);
					size = 0;
					resultList.clear();
				}
			}
		}
		if (size != 0) {
			try {
				resultList = removeDupliateCode(resultList);
				size = 0;
				resultList.clear();
			} catch (Exception e) {
				log.error("", e);
				resultList.clear();
			}
		}
		return messageList;
	}

	/**
	 * 导入csv文件到人员表。
	 * 
	 * @param inputStream InputStream
	 * 
	 * @return List<String>
	 * 
	 * @throws IOException IOException
	 */
	public List<String> saveCSVData(InputStream inputStream) throws IOException {
		List<String> messageList = new ArrayList<String>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Map<String, String> idMap = new HashMap<String, String>();
		Map<String, String> codeMap = new HashMap<String, String>();
		String msg = "";
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(in);
		String line = "";
		String[] headers = null;
		String[] values = null;
		boolean isFirstRow = false;
		int size = 0;
		int cycle = 0;
		int maxFailedLoginCount = securityStrategyService
				.getMaxFailedLoginCount();
		int passwordExpireDays = securityStrategyService
				.getPasswordExpireDays();
		// TODO xiaocheng_lu 导入的数据只精确到日期
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		while (bufferedReader.ready()) {
			valueMap = new HashMap<String, Object>();
			cycle++;
			if (!isFirstRow) {
				line = bufferedReader.readLine();
				headers = line.split(",");
				isFirstRow = true;
			} else {
				line = bufferedReader.readLine();
				values = line.split(",");
				for (int i = 0; i < headers.length; i++) {
					try {
						String headerStr = InternationalizationUtil.toUpperCase(headers[i]);
						if (headerStr.startsWith("\"")
								&& headerStr.lastIndexOf("\"") != -1) {
							headerStr = headerStr.substring(1,
									headerStr.length() - 1);
						}
						String valueStr = values[i];
						if (valueStr.startsWith("\"")
								&& valueStr.lastIndexOf("\"") != -1) {
							valueStr = valueStr.substring(1,
									valueStr.length() - 1);
						}

						valueMap.put(headerStr, valueStr);
					} catch (Exception e) {
						log.error("", e);
					}
				}
				// 判断编码在文件中是否为空
				if (this.isEmpty((String) valueMap.get("CODE"))) {
					msg = "第" + cycle + "行编码为空";
					messageList.add(msg);
					continue;
				}
				// 判断ID在文件中是否为空
				if (this.isEmpty((String) valueMap.get("ID"))) {
					msg = "第" + cycle + "行ID为空";
					messageList.add(msg);
					continue;
				}
				// 判断编码在文件中是否重复
				if (codeMap.containsKey(valueMap.get("CODE"))) {
					msg = "第" + cycle + "行编码存在重复,重复编码为：" + valueMap.get("CODE");
					messageList.add(msg);
					continue;
				} else {
					codeMap.put((String) valueMap.get("CODE"),
							(String) valueMap.get("CODE"));
				}
				// 判断ID在文件中是否重复
				if (idMap.containsKey(valueMap.get("ID"))) {
					msg = "第" + cycle + "行ID存在重复,重复ID为：" + valueMap.get("ID");
					messageList.add(msg);
					continue;
				} else {
					idMap.put((String) valueMap.get("ID"),
							(String) valueMap.get("ID"));
				}
				// 把一些数字类型的字段，从字符串转换为数字类型
				valueMap.put("VERSION", 0);
				valueMap.put("ONLINE_COUNT", 0);
				convertIntegerValue(valueMap, "FAILED_LOGIN_COUNT", 0);
				convertIntegerValue(valueMap, "MAX_FAILED_LOGIN_COUNT",
						maxFailedLoginCount);
				convertIntegerValue(valueMap, "PASSWD_EXPIRE_DAYS",
						passwordExpireDays);

				convertValue(valueMap, "STATUS", "y");
				convertValue(valueMap, "SEX", null);
				convertValue(valueMap, "TEL", null);
				convertValue(valueMap, "LOGIN_ID", null);
				convertValue(valueMap, "ID_NUMBER", null);
				convertValue(valueMap, "EMAIL", null);
				convertValue(valueMap, "ADDRESS", null);
				convertValue(valueMap, "BIRTHDAY", null);
				convertValue(valueMap, "PASSWD", null);
				convertValue(valueMap, "LAST_LOGIN_IP", null);
				convertValue(valueMap, "LOGIN_IP", null);
				convertValue(valueMap, "EXT1", null);
				convertValue(valueMap, "EXT2", null);
				convertValue(valueMap, "EXT3", null);
				convertValue(valueMap, "EXT4", null);
				convertValue(valueMap, "EXT5", null);
				convertValue(valueMap, "EXT6", null);
				convertValue(valueMap, "EXT7", null);
				convertValue(valueMap, "EXT8", null);
				convertValue(valueMap, "EXT9", null);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR,
						(Integer) valueMap.get("PASSWD_EXPIRE_DAYS"));
				try {
					// 对时间进行处理，把时间从字符串按照'yyyy-MM-dd'的格式，做处理
					Date time = calendar.getTime();
					convertDateValue(valueMap, "PASSWD_EXPIRE_TIME", null, sf);
					convertDateValue(valueMap, "ACCOUNT_EXPIRE_TIME", null, sf);
					convertDateValue(valueMap, "LOGIN_TIME", null, sf);
					convertDateValue(valueMap, "CREATED_TIME", time, sf);
					convertDateValue(valueMap, "LAST_MODIFIED_TIME", time, sf);
					convertDateValue(valueMap, "LAST_LOGIN_TIME", null, sf);
				} catch (Exception e) {
					log.error("", e);
					msg = "第" + cycle + "行日期格式不对";
					messageList.add(msg);
				}
				resultList.add(valueMap);
				valueMap.get("MAX_FAILED_LOGIN_COUNT");
				size++;
			}
			if (size == 500) {
				resultList = removeDupliateCode(resultList);
				try {
					size = 0;
					resultList.clear();
				} catch (Exception e) {
					log.error("", e);
					size = 0;
					resultList.clear();
				}
			}
		}
		if (size != 0) {
			try {
				resultList = removeDupliateCode(resultList);
				size = 0;
				resultList.clear();
			} catch (Exception e) {
				log.error("", e);
				resultList.clear();
			}
		}
		return messageList;
	}

	/**
	 * 针对字符型，如果key字段中的值是空值(包括标识为空值的)，将value的值设置为该值。
	 * 
	 * @param valueMap
	 *            Map<String, Object>
	 * @param key
	 *            String
	 * @param value
	 *            Object
	 */
	private void convertValue(Map<String, Object> valueMap, String key,
			Object value) {
		String str = (String) valueMap.get(key);
		if (this.isEmpty(str)) {
			valueMap.put(key, value);
		}
	}

	/**
	 * 针对整型，如果key字段中的值是空值(包括标识为空值的)，将value的值设置为该值。
	 * 
	 * @param valueMap
	 *            Map<String, Object>
	 * @param key
	 *            String
	 * @param value
	 *            Object
	 */
	private void convertIntegerValue(Map<String, Object> valueMap, String key,
			Object value) {
		String str = (String) valueMap.get(key);
		if (this.isEmpty(str)) {
			valueMap.put(key, value);
		} else {
			Integer v = Integer.valueOf(str);
			valueMap.put(key, v);
		}
	}

	/**
	 * 针对日期型，如果key字段中的值是空值(包括标识为空值的)，将value的值设置为该值。
	 * 
	 * @param valueMap
	 *            Map<String, Object>
	 * @param key
	 *            String
	 * @param value
	 *            Object
	 */
	private void convertDateValue(Map<String, Object> valueMap, String key,
			Object value, SimpleDateFormat sf) throws ParseException {
		String str = (String) valueMap.get(key);
		if (this.isEmpty(str)) {
			valueMap.put(key, value);
		} else {
			Object v = sf.parse(str);
			valueMap.put(key, v);
		}
	}

	/**
	 * 对有重复的ID和CODE从结果列表里去除。
	 * 
	 * @param resultList
	 *            List<Map<String, Object>>
	 * 
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> removeDupliateCode(
			List<Map<String, Object>> resultList) {
		StringBuffer inSQL = new StringBuffer();
		StringBuffer idInSql = new StringBuffer();
		Map<String, Map<String, Object>> tempMap = new HashMap<String, Map<String, Object>>();

		for (Map<String, Object> val : resultList) {
			inSQL.append("'");
			inSQL.append("" + val.get("CODE"));
			inSQL.append("',");
			tempMap.put("XX_" + val.get("CODE"), val);
			idInSql.append("'");
			idInSql.append("" + val.get("ID"));
			idInSql.append("',");
			tempMap.put(val.get("ID") + "_XX", val);
		}
		String sql = "select id,code from FBRP_SEC_STAFF where code in("
				+ inSQL.toString() + "'') or id in(" + idInSql.toString()
				+ "'')";
		Integer count = null;
		List<Map<String, Object>> resultMap = null;
		for (Map<String, Object> val : resultMap) {
			String code = "XX_" + val.get("CODE");
			String id = val.get("ID") + "_XX";
			if (tempMap.containsKey(code)) {
				resultList.remove(tempMap.get(code));
			}
			if (tempMap.containsKey(id)) {
				resultList.remove(tempMap.get(id));
			}
		}
		return resultList;
	}

	@Override
	public Object[] queryByParamfilter(FbrpSecStaff staffVO, int pageIndex,
			int pageSize) {
		Object[] ret = null;
		if (!hasText(staffVO.getOrgid())) {
			PagedResult<FbrpSecStaff> pr = this.pagedQuery(
					this.newMapInstance(), pageIndex, pageSize);
			Long count = pr.getTotal();
			ret = new Object[] { pr.getData(), count.intValue() };
		} else {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", staffVO.getOrgid());
			if (hasText(staffVO.getCode())) {
				map.put("staffCode", "%" + InternationalizationUtil.toLowerCase(staffVO.getCode())
						+ "%");
			}
			if (hasText(staffVO.getName())) {
				map.put("staffName", "%" + InternationalizationUtil.toLowerCase(staffVO.getName())
						+ "%");
			}
			map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
			if (hasText(staffVO.getLoginid())) {
				map.put("loginId", "%" + InternationalizationUtil.toLowerCase(staffVO.getLoginid())
						+ "%");
			}
			map.put("adminStaffId", securityStrategyService.getAdminStaffId());
			String statement = this.getStatement("queryByParamfilter");
			List<?> list = this.getSqlSessionTemplate().selectList(statement,
					map);
			Long i = (Long) this.getSqlSessionTemplate().selectOne(
					statement + "_count", map);
			ret = new Object[] { list, i.intValue() };
		}
		return ret;
	}

	/**
	 * 判断导入文件的字段是否为空。
	 * 
	 * @param str
	 *            String
	 * 
	 * @return boolean
	 */
	private boolean isEmpty(String str) {
		// TODO xiaocheng_lu 使用"null"来标识null值是否合适
		return StringUtil.isEmpty(str) || "null".equals(str);
	}

	@Override
	public Object[] queryStaffByGrantOrg(FbrpSecStaff staff, int offSet,
			int pageSize, String orgId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		if (hasText(staff.getCode())) {
			map.put("staffCode", "%" + InternationalizationUtil.toLowerCase(staff.getCode()) + "%");
		}
		if (hasText(staff.getName())) {
			map.put("staffName", "%" + InternationalizationUtil.toLowerCase(staff.getName()) + "%");
		}
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		if (hasText(staff.getLoginid())) {
			map.put("loginId", "%" + InternationalizationUtil.toLowerCase(staff.getLoginid()) + "%");
		}
		String statement = this.getStatement("queryStaffByGrantOrg");
		List<?> list = this.getSqlSessionTemplate().selectList(statement, map);
		Long i = (Long) this.getSqlSessionTemplate().selectOne(
				statement + "_count", map);
		return new Object[] { list, i.intValue() };
	}

	@Override
	public Object[] queryStaffNoContainOrg(FbrpSecStaff staff, int offSet,
			int pageSize, String orgId) {
		// TODO chengyi_zhu 对于大数据量的情况，速度会很慢。
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("staffId", securityStrategyService.getAdminStaffId());
		if (hasText(staff.getCode())) {
			map.put("staffCode", "%" + InternationalizationUtil.toLowerCase(staff.getCode()) + "%");
		}
		if (hasText(staff.getName())) {
			map.put("staffName", "%" + InternationalizationUtil.toLowerCase(staff.getName()) + "%");
		}
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		if (hasText(staff.getLoginid())) {
			map.put("loginId", "%" + InternationalizationUtil.toLowerCase(staff.getLoginid()) + "%");
		}
		String statement = this.getStatement("queryStaffNoContainOrg");
		List<FbrpSecStaff> list = this
				.getSqlSessionTemplate().selectList(statement, map);
		Long i = (Long) this.getSqlSessionTemplate().selectOne(
				statement + "_count", map);
		return new Object[] { list, i.intValue() };
	}

	@Override
	public Object[] queryStaffByContainOrg(FbrpSecStaff staffVO, int offSet,
			int pageSize, String orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId.trim());
		if (hasText(staffVO.getCode())) {
			map.put("staffCode", "%" + InternationalizationUtil.toLowerCase(staffVO.getCode()) + "%");
		}
		if (hasText(staffVO.getName())) {
			map.put("staffName", "%" + InternationalizationUtil.toLowerCase(staffVO.getName()) + "%");
		}
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		if (hasText(staffVO.getLoginid())) {
			map.put("loginId", "%" + InternationalizationUtil.toLowerCase(staffVO.getLoginid()) + "%");
		}
		String statement = this.getStatement("queryStaffByContainOrg");
		List<FbrpSecStaff> list = this
				.getSqlSessionTemplate().selectList(statement, map);
		Long i = (Long) this.getSqlSessionTemplate().selectOne(
				statement + "_count", map);
		return new Object[] { list, i.intValue() };
	}

	@Override
	public Object[] queryStaffNOGrantOrg(FbrpSecStaff staff, int offSet,
			int pageSize, String orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("staffId", securityStrategyService.getAdminStaffId());
		if (hasText(staff.getCode())) {
			map.put("staffCode", "%" + InternationalizationUtil.toLowerCase(staff.getCode()) + "%");
		}
		if (hasText(staff.getName())) {
			map.put("staffName", "%" + InternationalizationUtil.toLowerCase(staff.getName()) + "%");
		}
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		if (hasText(staff.getLoginid())) {
			map.put("loginId", "%" + InternationalizationUtil.toLowerCase(staff.getLoginid()) + "%");
		}
		String statement = this.getStatement("queryStaffNOGrantOrg");
		List<FbrpSecStaff> list = this
				.getSqlSessionTemplate().selectList(statement, map);
		Long i = (Long) this.getSqlSessionTemplate().selectOne(
				statement + "_count", map);
		return new Object[] { list, i.intValue() };
	}

	@Override
	public Object[] queryStaffByOrg(FbrpSecStaff staffVO, int pageIndex,
			int pageSize, String orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		if (hasText(staffVO.getCode())) {
			map.put("staffCode", "%" + InternationalizationUtil.toLowerCase(staffVO.getCode()) + "%");
		}
		if (hasText(staffVO.getName())) {
			map.put("staffName", "%" + InternationalizationUtil.toLowerCase(staffVO.getName()) + "%");
		}
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		if (hasText(staffVO.getLoginid())) {
			map.put("loginId", "%" + InternationalizationUtil.toLowerCase(staffVO.getLoginid()) + "%");
		}
		String statement = this.getStatement("queryStaffByOrg");
		List<FbrpSecStaff> list = this
				.getSqlSessionTemplate().selectList(statement, map,this.getRowBounds(pageIndex, pageSize));
		Long i = (Long) this.getSqlSessionTemplate().selectOne(
				statement + "_count", map);
		return new Object[] { list, i.intValue() };
	}

	/**
	 * 根据人员信息条件查询。
	 * 
	 * @param vo FbrpSecStaff
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param orders Order
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	@Override
	public PagedResult<FbrpSecStaff> pagedQuery(FbrpSecStaff vo,
			int pageIndex, int pageSize, Order... orders) {
		Map<String,Object> map = new HashMap<String, Object>();
		if(!StringUtil.isStrEmpty(vo.getCode())){
			map.put("staffCode", "%"+vo.getCode()+"%");
		}
		
		if(!StringUtil.isStrEmpty(vo.getLoginid())){
			if(vo.getLoginid().length()==11)//精确区别是否是模糊查询来提高效率
			{
				map.put("loginId", vo.getLoginid());
				map.put("accLoginFlag", "1");//myBatis用来区别是否用like
			}
			else//模湖查询
			{
				map.put("loginId", "%"+vo.getLoginid()+"%");
			}
		}
		if(!StringUtil.isStrEmpty(vo.getName())){
			map.put("staffName", "%"+vo.getName()+"%");
		}
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		if(!StringUtil.isStrEmpty(vo.getRoleid())){
			map.put("roleId", vo.getRoleid());
		}
		map.put("delFlag", FbrpSecStaff.DEL_FLAG_NORMAL);
		if(!StringUtil.isStrEmpty(vo.getOrgCode())){
			map.put("orgCode", vo.getOrgCode());
		}
		return this.pagedQuery("select_byParam", map, pageIndex, pageSize);
	}
	
	private PMap toMap(FbrpSecStaff vo) {
		PMap pm = this.newMapInstance();
		pm.includeIgnoreCase("code", vo.getCode());
		pm.includeIgnoreCase("name", vo.getName());			
		return pm;
	}

	@Override
	public PagedResult<FbrpSecStaff> queryMember(int pageIndex, int pageSize, FbrpSecStaff staff) {
		String key = "selectMember";
		PagedResult<FbrpSecStaff> result = super.pagedQuery(key, staff, pageIndex, pageSize);
		return result;
	}

	@Override
	public PagedResult<FbrpSecStaff> queryMemberById(int pageIndex, int pageSize, FbrpSecStaff staff) {
		String key = "selectMemberById";
		PagedResult<FbrpSecStaff> result = super.pagedQuery(key, staff, pageIndex, pageSize);
		return result;
	}

	@Override
	public PagedResult<FbrpSecStaff> queryExistedMemberById(int pageIndex, int pageSize, FbrpSecStaff staff) {
		String key = "selectExistedMemberById";
		PagedResult<FbrpSecStaff> result = super.pagedQuery(key, staff, pageIndex, pageSize);
		return result;
	}

	@Override
	public void batchAddMembers(List<FbrpSecRoleMember> list) {
		this.batchCreate("addMember", list);
	}

	@Override
	public void batchUpdateMembers(List<FbrpSecRoleMember> list) {
		this.batchUpdate("deleteMember", list);
	}
	
	@Override
	public void saveUser(StaffUumVO user, String staffId) {
		String statement = this.getStatement("addUser");
		String statement1 = this.getStatement("insertYhqxjg");
		FbrpSecStaff staff = new FbrpSecStaff();
		IKeyGen key = this.getKeyGen();
		user.setUserId(key.getUUIDKey());
		this.getSqlSessionTemplate().insert(statement, user);
		staff.setName(user.getUserName());
		staff.setCode(user.getAccountName());
		staff.setLoginid(user.getAccountName());
		staff.setEmail(user.getEmail());
		staff.setSjssjgDm(user.getOrgCode());
		staff.setCjsj(new Date());
		staff.setPasswd("111111");
		staff.setCreatorId(staffId);
		staff.setCreatedTime(new Date());
		staff.setLastModifierId(staffId);
		staff.setLastModifiedTime(new Date());
		staff.setCjrDm(user.getCjrDm());
		staff.setStatus("1");
		staff.setDelFlag("n");
		this.createOrUpdate(staff);
		this.getSqlSessionTemplate().insert(statement1, staff.getCode());
	}
	
	@Override
	public PagedResult<StaffOAVO> selectUser(Map<String, String> map, int pageIndex, int pageSize) {
		PagedResult<StaffOAVO> result = this.pagedQuery("selectUserOfChange", map, pageIndex, pageSize);
		return result;
	}
	
	@Override
	public boolean checkOwn(String accountName) {
		String statement = this.getStatement("checkOwn");
		Long count = this.getSqlSessionTemplate().selectOne(statement, accountName);
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void updateUserUum(List<String> codeList, String actionType) {
		List<Map<String, String>> mList = new ArrayList<Map<String,String>>();
		for(String code : codeList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountName", code);
			map.put("actionType", actionType);
			mList.add(map);
		}
		this.batchUpdate("updateUserUum", mList);
	}
	
	@Override
	public StaffOAVO selectUser(String accountName, String actionType) {
		Map<String, String> map = new HashMap<String, String>();
		String statement = this.getStatement("selectUser");
		map.put("accountName", accountName);
		map.put("actionType", actionType);
		StaffOAVO vo = this.getSqlSessionTemplate().selectOne(statement, map);
		return vo;
	}
	
	@Override
	public boolean selectUserOrg(StaffOAVO vo) {
		String statement = this.getStatement("selectUserOrg");
		Long count = this.getSqlSessionTemplate().selectOne(statement, vo);
		if((count != null) && (count > 0L)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void batchAddUser(List<StaffOAVO> vos, List<StaffOAVO> uvos, List<String> codeList, String actionType, String staffId) {
		List<String> ids = new ArrayList<String>();
		List<String> idt = new ArrayList<String>();
		List<String> accountNames = new ArrayList<String>();
		List<String> daccountNames = new ArrayList<String>();
		this.staffYwService = (IStaffYwService) ContextUtil.getBean("tsmp_admin_rwfpqkService");
		for(StaffOAVO vo : vos) {
			ids.add(vo.getId());
			accountNames.add(vo.getAccountName());
			FbrpSecStaff staff = new FbrpSecStaff();
			staff.setName(vo.getUsername());
			staff.setCode(vo.getAccountName());
			staff.setLoginid(vo.getAccountName());
			staff.setEmail(vo.getEmail());
			staff.setSjssjgDm(vo.getOrgCode());
			staff.setCjsj(new Date());
			staff.setPasswd("111111");
			staff.setCreatorId(staffId);
			staff.setCreatedTime(new Date());
			staff.setLastModifierId(staffId);
			staff.setLastModifiedTime(new Date());
			staff.setCjrDm(vo.getCreaterCode());
			staff.setStatus("1");
			staff.setDelFlag("n");
			this.createOrUpdate(staff);
		}
		for(StaffOAVO vo : uvos) {
			daccountNames.add(vo.getAccountName());
			idt.add(vo.getId());
			String orgcode = this.selectUserOldOrgCode(vo.getAccountName());
			this.staffYwService.delGszDate(orgcode, vo.getAccountName());
		}
		this.batchCreate("addUserOfChange", ids);
		this.batchCreate("insertYhqxjg", accountNames);
		this.batchUpdate("addUserOfChangeToMov", uvos);
		this.batchUpdate("updateStaffMov", uvos);
		this.batchUpdate("deleteYhqxjgForSame", daccountNames);
		this.batchUpdate("updateYhqxjgMov", uvos);
		this.batchUpdate("deleteRoleMember", daccountNames);
		this.batchUpdateUser(uvos, new ArrayList<String>(), "MOD");
		this.updateUserUum(codeList, actionType);
		
		//TODO 更新 T_ADMIN_TASK_DB_DETAIL 表中“同步添加人员”待办数据。
		ids.addAll(idt);
		if(!ids.isEmpty()) {
			this.updateTaskZxfkYb(ids, "dbrw_xtgl_dtbtjry");
		}
	}

	@Override
	public void batchUpdateUser(List<StaffOAVO> vos, List<String> codeList, String actionType) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<String> ids = new ArrayList<String>();
		for(StaffOAVO vo : vos) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", vo.getId());
			map.put("accountName", vo.getAccountName());
			map.put("xgrDm", vo.getXgrDm());
			list.add(map);
			ids.add(vo.getId());
		}
		this.batchUpdate("updateUserOfChange", list);
		this.batchUpdate("updateStaffOfChange", list);
		this.updateUserUum(codeList, actionType);
		
		//TODO 更新 T_ADMIN_TASK_DB_DETAIL 表中“同步修改人员”待办数据为已办。
		if(!ids.isEmpty()) {
			this.updateTaskZxfkYb(ids, "dbrw_xtgl_dtbxgry");
		}
	}
	
	@Override
	public void batchUpdateUserMov(List<StaffOAVO> vos, List<String> codeList, String actionType) {
		List<String> accountNames = new ArrayList<String>();
		List<String> ids = new ArrayList<String>();
		this.staffYwService = (IStaffYwService) ContextUtil.getBean("tsmp_admin_rwfpqkService");
		for(StaffOAVO vo : vos) {
			accountNames.add(vo.getAccountName());
			ids.add(vo.getId());
			String orgcode = this.selectUserOldOrgCode(vo.getAccountName());
			this.staffYwService.delGszDate(orgcode, vo.getAccountName());
		}
		this.batchUpdate("updateUserOfChangeMov", vos);
		this.batchUpdate("updateStaffMov", vos);
		this.batchUpdate("deleteYhqxjgForSame", accountNames);
		this.batchUpdate("updateYhqxjgMov", vos);
		this.batchUpdate("deleteRoleMember", accountNames);
		this.updateUserUum(codeList, actionType);
		
		//TODO 更新 T_ADMIN_TASK_DB_DETAIL 表中“同步移动人员”待办数据为已办。
		if(!ids.isEmpty()) {
			this.updateTaskZxfkYb(ids, "dbrw_xtgl_dtbydry");
		}
	}
	
	@Override
	public List<GyUuvOrgVO> findAllOrg(String orgCode) {
		String statement = this.getStatement("findOrg");
		List<GyUuvOrgVO> list = this.getSqlSessionTemplate().selectList(statement, orgCode);
		return list;
	}
	
	@Override
	public List<GyUuvOrgVO> findChildrenOrg(String orgCode) {
		String statement = this.getStatement("findChildrenOrg");
		List<GyUuvOrgVO> list = this.getSqlSessionTemplate().selectList(statement, orgCode);
		return list;
	}
	
	@Override
	public boolean findChildrenOrgOwn(String orgCode) {
		String statement = this.getStatement("findChildrenOrg_count");
		Long count = this.getSqlSessionTemplate().selectOne(statement, orgCode);
		if((count == null) || (count == 0)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public List<Map<String, String>> selectYhqxjg(String accountName) {
		String statement = this.getStatement("selectYhqxjg");
		List<Map<String, String>> list = this.getSqlSessionTemplate().selectList(statement, accountName);
		return list;
	}
	
	@Override
	public String selectMainOrgForGlznbz(String orgCode) {
		String statement = this.getStatement("selectMainOrgForGlznbz");
		String org = this.getSqlSessionTemplate().selectOne(statement, orgCode);
		return org;
	}
	
	@Override
	public boolean selectAdminOwn(String accountName, String orgCode) {
		String statement = this.getStatement("selectAdminOwn");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgCode", orgCode);
		map.put("accountName", accountName);
		Long count = this.getSqlSessionTemplate().selectOne(statement, map);
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void saveYhqxjg(List<Map<String, Object>> list, String accountName) {
		String deleteStatement = this.getStatement("deleteYhqxjg");
		String saveStatement = this.getStatement("insertYhqxjgOfStaff");
		this.getSqlSessionTemplate().delete(deleteStatement, accountName);
		this.batchCreate(saveStatement, list);
	}
	
	@Override
	public boolean checkStaffGrant(String accountName, String url) {
		String statement = this.getStatement("checkStaffGrant");
		Map<String, Object> map = new HashMap<String, Object>();
		if(url.contains("?")) {
			String url2 = url.substring(0, url.indexOf("?"));
			map.put("url2", url2);
		}
		map.put("accountName", accountName);
		map.put("url", url);
		Long count = this.getSqlSessionTemplate().selectOne(statement, map);
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String selectUserOldOrgCode(String accountname) {
		String statement = this.getStatement("selectUserOldOrgCode");
		String orgcode = this.getSqlSessionTemplate().selectOne(statement, accountname);
		return orgcode;
	}
	
	/**
	 * 更新 T_ADMIN_TASK_DB_DETAIL 表中待办数据为已办。
	 * 
	 * @param ids List<String>
	 * @param task_ly String
	 */
	public void updateTaskZxfkYb(List<String> ids,String task_ly){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("list", ids);
		param.put("task_ly", task_ly);
		this.getSqlSessionTemplate().update(this.getStatement("updateTaskZxfk"),param);
	}
	
	
	@Override
	public String selectPassword(String accountname) {
		String statement = this.getStatement("selectPassword");
		String password = this.getSqlSessionTemplate().selectOne(statement, accountname);
		return password;
	}

	/**
	 * 设置 roleMemberService。
	 * 
	 * @param roleMemberService
	 *            IRoleMemberService
	 */
	public void setRoleMemberService(IRoleMemberService roleMemberService) {
		this.roleMemberService = roleMemberService;
	}

	/**
	 * 设置 securityStrategyService。
	 * 
	 * @param securityStrategyService
	 *            ISecurityStrategyService
	 */
	public void setSecurityStrategyService(
			ISecurityStrategyService securityStrategyService) {
		this.securityStrategyService = securityStrategyService;
	}

	/**
	 * 设置 userDetailService。
	 * 
	 * @param userDetailService
	 *            IUserDetailsService
	 */
	public void setUserDetailService(IUserDetailsService userDetailService) {
		this.userDetailService = userDetailService;
	}

	/**
	 * 设置 orgService。
	 * 
	 * @param orgService
	 *            IOrgService
	 */
	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}

	public IStaffYwService getStaffYwService() {
		return staffYwService;
	}

	public void setStaffYwService(IStaffYwService staffYwService) {
		this.staffYwService = staffYwService;
	}

	/**
	 * 返回 sqlExecutionEngineService。
	 * 
	 * @return ISqlExecutionEngineService
	 */
	public ISqlExecutionEngineService getSqlExecutionEngineService() {
		return sqlExecutionEngineService;
	}

	/**
	 * 设置 sqlExecutionEngineService。
	 * 
	 * @param sqlExecutionEngineService
	 *            ISqlExecutionEngineService
	 */
	public void setSqlExecutionEngineService(
			ISqlExecutionEngineService sqlExecutionEngineService) {
		this.sqlExecutionEngineService = sqlExecutionEngineService;
	}
	
}
