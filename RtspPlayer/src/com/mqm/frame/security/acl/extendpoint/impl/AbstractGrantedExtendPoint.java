/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.security.acl.extendpoint.IGrantExtendPoint;
import com.mqm.frame.security.acl.extendpoint.IPrincipalType;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * <pre>
 * 数据权限扩展点的抽象实现。
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
public abstract class AbstractGrantedExtendPoint extends DefaultServiceImpl
		implements IGrantExtendPoint {

	private Map<String, String> resultValueMap = new HashMap<String, String>();

	private String grantName = "";

	private int sortNo = Integer.MAX_VALUE;
	
	private IPrincipalType principalType;

	@Override
	public String getGrantName() {
		return this.grantName;
	}

	@Override
	public abstract List getAll();

	@Override
	public List getWith(List<String> roleIds){
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public List getAllByPrincipals(List principals) {
		IGrantService grantService = (IGrantService) ContextUtil.getBean(IGrantService.BEAN_ID);
	/*	UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		if (grantService.isAdmin(user.getLoginId())) {
			return getAll();
		}*/

		//boolean flag = principals.contains(grantService.getAppAdminRole().getId());
		List result = new ArrayList();
		Map map = grantService.getAllFilter(getAll(), principals);
		result.addAll(map.keySet());
		return result;
	}

	@Override
	public void updateGranted(List insertlist, List deletelist, String principalid) {
		IGrantService aclGrantedService = (IGrantService) ContextUtil.getBean(IGrantService.BEAN_ID);
		aclGrantedService.updateToPrincipal(insertlist, deletelist, principalid);
	}
	
	@Override
	public void saveObject(Object object) {
		IGrantService grantService = (IGrantService) ContextUtil.getBean(IGrantService.BEAN_ID);
		grantService.insertGrant(object, BaseConstants.GRANT_TYPE_ADMIN,grantService.getAppAdminRole().getId());
	}

	@Override
	public Map<String, String> getResultValueMap() {
		return resultValueMap;
	}

	/**
	 * 设置 resultValueMap。
	 * 
	 * @param resultValueMap Map<String, String>
	 */
	public void setResultValueMap(Map<String, String> resultValueMap) {
		this.resultValueMap = resultValueMap;
	}

	@Override
	public int getSortNo() {
		return this.sortNo;
	}

	/**
	 * 设置sortNo。
	 * 
	 * @param sortNo int
	 */
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * 设置grantName。
	 * 
	 * @param grantName String
	 */
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}

	@Override
	public IPrincipalType getPrincipalType() {
		return principalType;
	}

	/**
	 * 设置principalType。
	 * 
	 * @param principalType IPrincipalType
	 */
	public void setPrincipalType(IPrincipalType principalType) {
		this.principalType = principalType;
	}

}
