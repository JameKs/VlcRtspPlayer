/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.auditlog.service;

/**
 * <pre>
 * 供测试目的。
 * </pre>
 * @author mmr  mmr@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class NullAuditLoggerImpl implements IAuditLogger {

	/* (non-Javadoc)
	 * @see com.foresee.fbrp.infrastructure.auditlog.service.IAuditLogger#auditLog(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void auditLog(String bizType, String opType, String opInfo,
			String opCode, String opName, String ipaddr) {
	}

}
