/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.service.impl;

import java.util.List;

import com.mqm.frame.common.codeinfo.service.ICodeValueService;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;
import com.mqm.frame.security.service.ISecurityStrategyService;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 安全性策略实现类。
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
public class SecurityStrategyServiceImpl implements
		ISecurityStrategyService {

	private ICodeValueService codeValueService;

	@Override
	public List<FbrpCommonCodeValue> getAllStrategies() throws FbrpException {
		return this.codeValueService.getCodeInfoList(BaseConstants.SECURITY_STRATEGY_TYPE);
	}

	@Override
	public String getStrategyValue(String strategyName) {
		for (FbrpCommonCodeValue vo : getAllStrategies()) {
			if (vo.getName().equals(strategyName)) {
				return vo.getValue1();
			}
		}
		return null;
	}

	@Override
	public int getMaxFailedLoginCount() {
		for (FbrpCommonCodeValue vo : getAllStrategies()) {
			if (BaseConstants.MAX_FAILED_LOGIN_COUNT.equals(vo.getName())) {
				return Integer.parseInt(vo.getValue1());
			}
		}
		return BaseConstants.DEFAULT_MAX_FAILED_LOGIN_COUNT;
	}

	@Override
	public String getAdminRoleCode() {
		for (FbrpCommonCodeValue vo : getAllStrategies()) {
			if (BaseConstants.ADMIN_ROLECODE.equals(vo.getName())) {
				return vo.getValue1();
			}
		}
		return BaseConstants.DEFAULT_ADMIN_ROLECODE;
	}

	@Override
	public String getAdminStaffId() {
		for (FbrpCommonCodeValue vo : getAllStrategies()) {
			if (BaseConstants.ADMIN_STAFFID.equals(vo.getName())) {
				return vo.getValue1();
			}
		}
		return BaseConstants.DEFAULT_ADMIN_STAFFID;
	}

	@Override
	public int getPasswordExpireDays() {
		for (FbrpCommonCodeValue vo : getAllStrategies()) {
			if (BaseConstants.PASSWORD_EXPIRE_DAYS.equals(vo.getName())) {
				return Integer.parseInt(vo.getValue1());
			}
		}
		return BaseConstants.DEFAULT_PASSWORD_EXPIRE_DAYS;
	}

	@Override
	public String getDefaultPasswd() {
		for (FbrpCommonCodeValue vo : getAllStrategies()) {
			if (BaseConstants.DEFAILT_PASSWORD.equals(vo.getName())) {
				return vo.getValue1();
			}
		}		
		return BaseConstants.DEFAULT_DEFAULT_PASSWORD;
	}

	/**
	 * 设置 codeValueService。
	 * 
	 * @param codeValueService
	 *            设置 codeValueService。
	 */
	public void setCodeValueService(ICodeValueService codeValueService) {
		this.codeValueService = codeValueService;
	}

}
