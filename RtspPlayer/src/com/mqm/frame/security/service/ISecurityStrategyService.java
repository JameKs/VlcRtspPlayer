/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.service;

import java.util.List;

import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 这个接口用来访问系统安全策略数据。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface ISecurityStrategyService {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_security_securityStrategyService";

	/**
	 * 取得所有策略数据列表。
	 * 
	 * @return List<IPArcSecurityStrategyVO>
	 * 
	 * @throws FbrpException FBRP异常
	 */
	List getAllStrategies() throws FbrpException;

	/**
	 * 根据策略名称得到策略值。
	 * 
	 * @param strategyName String
	 * 
	 * @return 策略值
	 */
	String getStrategyValue(String strategyName);

	/**
	 * 得到默认最大登录失败次数策略值。
	 * 
	 * @return 最大登录失败次数
	 */
	int getMaxFailedLoginCount();

	/**
	 * 得到超级管理员角色编码。
	 * 
	 * @return 超级管理员角色编码
	 */
	String getAdminRoleCode();

	/**
	 * 得到超级管理员员工号。
	 * 
	 * @return 超级管理员员工号
	 */
	String getAdminStaffId();

	/**
	 * 得到默认密码过期天数。
	 * 
	 * @return 默认密码过期天数
	 */
	int getPasswordExpireDays();

	/**
	 * 返回默认密码。
	 * 
	 * @return String
	 */
	String getDefaultPasswd();

}
