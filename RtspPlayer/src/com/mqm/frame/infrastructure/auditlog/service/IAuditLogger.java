/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.auditlog.service;

/**
 * 
 * <pre>
 * 日志服务接口。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IAuditLogger{

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_infrastructure_auditLogger";

	/**
	 * 记录登录日志信息到日志表。
	 * 
	 * @param bizType
	 *            业务模块类型，对应菜单编号，同时需要在com.foresee.fbrp.utils.constants.
	 *            BaseConstants中需要事先定义
	 * @param opType
	 *            操作类型，在码表FBRP_COMMON_CODE_INFO及com.foresee.fbrp.utils.constants
	 *            .BaseConstants中需要事先定义
	 * @param opInfo
	 *            操作信息，开发人员提供
	 * @param opCode
	 *            登录用户代码
	 * @param opName
	 *            登录用户名称
	 * @param ipaddr
	 *            登录用户地址
	 */
	public void auditLog(String bizType, String opType, String opInfo,
			String opCode, String opName, String ipaddr);

}
