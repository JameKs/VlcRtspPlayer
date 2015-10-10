/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * <pre>
 * 存储在HttpSession中的用户信息。
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
public class UserProfileVO implements Serializable {

	private static final long serialVersionUID = 0x6f37ceaca189ea22L;
	private boolean appAdmin;
	private String staffId;
	private String staffCode;
	private String staffName;
	private String loginId;
	private String passwd;
	private String deptId;
	private String deptName;
	//主税务机关
	private String orgId;
	//当前用户税务机关
	private String swjgBm;
	
	//当前用户管理职能所有税务机关
	private List<String> orgIds;
	//当前用户税务机关与操作税务机关映射关系
	private Map<String, String> orgSjfwMap;
	//当前用户能够操作的所有部门
	private List<String> deptCodes;
	
	private String orgName;
	//机构全称
	private String fullOrgName;
	private String orgType;
	private String tel;
	private String defaultFlag;
	private List roleList;
	private String loginIp;
	private String lastRequestDate;
	private String onlineTime;
	private String sessionId;
	private String currentAppId;
	private List grantedAuthorties;

	/**
	 * 默认的构造方法。
	 */
	public UserProfileVO() {
		staffId = "";
		staffName = "";
		deptId = "";
		deptName = "";
		orgId = "";
		orgName = "";
		tel = "";
		defaultFlag = "0";
	}

	/**
	 * 获取lastRequestDate。
	 * 
	 * @return String
	 */
	public String getLastRequestDate() {
		return lastRequestDate;
	}

	/**
	 * 设置lastRequestDate。
	 * 
	 * @param lastRequestDate String
	 */
	public void setLastRequestDate(String lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}

	/**
	 * 获取onlineTime。
	 * 
	 * @return String
	 */
	public String getOnlineTime() {
		return onlineTime;
	}

	/**
	 * 设置onlineTime。
	 * 
	 * @param onlineTime String
	 */
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * 获取sessionId。
	 * 
	 * @return String
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * 设置sessionId。
	 * 
	 * @param sessionId  String
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 获取roleList。
	 * 
	 * @return List
	 */
	public List getRoleList() {
		return this.getRoleList(false);
	}
	
	/**
	 * 获取roleList。
	 * 
	 * @param onlyFbrpSecRoleVO boolean
	 * @return List
	 */ 
	public List getRoleList(boolean onlyFbrpSecRoleVO) {
		if(onlyFbrpSecRoleVO){
			List list = new ArrayList();
			for (Object obj : this.roleList) {
				String clzName = obj.getClass().getName();
				if("com.mqm.frame.security.role.vo.FbrpSecRole".equals(clzName)){
					list.add(obj);
				}
			}
			return list;
		}else{
			return this.roleList;
		}
		
	}

	/**
	 * 设置roleList。
	 * 
	 * @param roleList List
	 */
	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	/**
	 * 获取deptId。
	 * 
	 * @return String
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * 获取deptName。
	 * 
	 * @return String
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 获取orgId。
	 * 
	 * @return String
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * 获取orgName。
	 * 
	 * @return String
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 获取staffId。
	 * 
	 * @return String
	 */
	public String getStaffId() {
		return staffId;
	}

	/**
	 * 获取staffName。
	 * 
	 * @return String
	 */
	public String getStaffName() {
		return staffName;
	}

	/**
	 * 获取tel。
	 * 
	 * @return String
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置deptId。
	 * 
	 * @param string String
	 */
	public void setDeptId(String string) {
		deptId = string;
	}

	/**
	 * 设置deptName。
	 * 
	 * @param string String
	 */
	public void setDeptName(String string) {
		deptName = string;
	}

	/**
	 * 设置orgId。
	 * 
	 * @param string String
	 */
	public void setOrgId(String string) {
		orgId = string;
	}

	/**
	 * 设置orgName。
	 * 
	 * @param string String
	 */
	public void setOrgName(String string) {
		orgName = string;
	}

	/**
	 * 设置staffId。
	 * 
	 * @param string String
	 */
	public void setStaffId(String string) {
		staffId = string;
	}

	/**
	 * 设置staffName。
	 * 
	 * @param string String
	 */
	public void setStaffName(String string) {
		staffName = string;
	}

	/**
	 * 设置tel。
	 * 
	 * @param string String
	 */
	public void setTel(String string) {
		tel = string;
	}

	/**
	 * 获取defaultFlag。
	 *  
	 * @return String
	 */
	public String getDefaultFlag() {
		return defaultFlag;
	}

	/**
	 * 设置defaultFlag。
	 * 
	 * @param defaultFlag String
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/**
	 * 获取passwd。
	 * 
	 * @return String
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * 设置passwd。
	 * 
	 * @param passwd String
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 获取loginId。
	 * 
	 * @return String
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * 设置loginId。
	 * 
	 * @param loginId String
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * 获取loginIp。
	 * 
	 * @return String
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设置loginIp。
	 * 
	 * @param loginIp String
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 获取currentAppId。
	 * 
	 * @return String
	 */
	public String getCurrentAppId() {
		return currentAppId;
	}

	/**
	 * 设置currentAppId。
	 * 
	 * @param currentAppId String
	 */
	public void setCurrentAppId(String currentAppId) {
		this.currentAppId = currentAppId;
	}

	/**
	 *  返回 staffCode。
	 * 
	 * @return 返回 staffCode。
	 */
	public String getStaffCode() {
		return staffCode;
	}

	/**
	 * 设置 staffCode。
	 * 
	 * @param staffCode
	 *            设置 staffCode。
	 */
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	/**
	 *  设置 grantedAuthorties。
	 * 
	 * @param grantedAuthorties
	 *            设置 grantedAuthorties。
	 */
	public void setGrantedAuthorties(List grantedAuthorties) {
		this.grantedAuthorties = grantedAuthorties;
	}

	/**
	 * 返回 appAdmin。
	 * 
	 * @return 返回 appAdmin。
	 */
	public boolean isAppAdmin() {
		return appAdmin;
	}

	/**
	 *  设置 appAdmin。
	 * 
	 * @param appAdmin
	 *            设置 appAdmin。
	 */
	public void setAppAdmin(boolean appAdmin) {
		this.appAdmin = appAdmin;
	}

	/**
	 * 返回 grantedAuthorties。
	 * 
	 * @return 返回 grantedAuthorties。
	 */
	public List getGrantedAuthorties() {
		return grantedAuthorties;
	}

	/**
	 * 返回 orgType。
	 * 
	 * @return String
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * 设置 orgType。
	 * 
	 * @param orgType 
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 管理机关IDs的SET方法。
	 * 
	 * @param orgIds List<String>
	 */
	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}
	
	/**
	 * 管理机关IDs的GET方法。
	 * 
	 * @return List<String>
	 */
	public List<String> getOrgIds() {
		return orgIds;
	}
	
	@Override
	public String toString() {
		return (new ToStringBuilder(this)).append("staffId", getStaffId())
				.append("staffName", getStaffName())
				.append("deptId", getDeptId())
				.append("deptName", getDeptName()).append("orgId", getOrgId())
				.append("orgName", getOrgName()).append("tel", getTel())
				.append("appAdmin", isAppAdmin()).toString();
	}

	/**
	 * 返回 deptCodes。
	 * 
	 * @return List<String>
	 */
	public List<String> getDeptCodes() {
		return deptCodes;
	}

	/**
	 * 设置 deptCodes。
	 * 
	 * @param deptCodes List<String>
	 */
	public void setDeptCodes(List<String> deptCodes) {
		this.deptCodes = deptCodes;
	}

	/**
	 * 返回 swjgBm。
	 * 
	 * @return String
	 */
	public String getSwjgBm() {
		return swjgBm;
	}

	/**
	 * 设置 swjgBm。
	 * 
	 * @param swjgBm String
	 */
	public void setSwjgBm(String swjgBm) {
		this.swjgBm = swjgBm;
	}

	/**
	 * @return 返回 orgSjfwMap。
	 */
	public Map<String, String> getOrgSjfwMap() {
		return orgSjfwMap;
	}

	/**
	 * @param orgSjfwMap 设置 orgSjfwMap。
	 */
	public void setOrgSjfwMap(Map<String, String> orgSjfwMap) {
		this.orgSjfwMap = orgSjfwMap;
	}

	/**
	 * @return 返回 fullOrgName。
	 */
	public String getFullOrgName() {
		return fullOrgName;
	}

	/**
	 * @param fullOrgName 设置 fullOrgName。
	 */
	public void setFullOrgName(String fullOrgName) {
		this.fullOrgName = fullOrgName;
	}
	
	
	

}