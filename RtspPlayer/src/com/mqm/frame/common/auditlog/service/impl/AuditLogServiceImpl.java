/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.auditlog.service.impl;

import java.util.Date;

import com.mqm.frame.common.auditlog.service.IAuditLogService;
import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLogQueryVO;
import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 日志服务实现类。
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
public class AuditLogServiceImpl extends MyBatisServiceImpl<FbrpCommonAuditLog> implements
		IAuditLogService {
	
	@Override
	public void auditLog(String bizType, String opType, String opInfo,
			String opCode, String opName, String ipaddr)
			throws FbrpException {
		FbrpCommonAuditLog vo = new FbrpCommonAuditLog();
		vo.setId(this.getKeyGen().getUUIDKey());
		vo.setBizType(bizType);
		vo.setLogType(opType);
		vo.setLogInfo(opInfo);
		vo.setOpCode(opCode);
		vo.setOpName(opName);
		vo.setIpAddr(ipaddr);
		
		Date date = new Date();
		vo.setCjrDm(opCode);
		vo.setCjsj(date);
		vo.setCreatedTime(date);
		//this.create(vo);
		String statement = this.getStatement("insert");
	    this.getSqlSessionTemplate().insert(statement, vo);
	}
	
	@Override
	public PagedResult<FbrpCommonAuditLog> pagedQuery(FbrpCommonAuditLogQueryVO vo, int pageIndex, int pageSize) {
		PMap pm = toMap(vo);
		return this.pagedQuery(pm, pageIndex, pageSize, desc("createdTime"));
	}

	private PMap toMap(FbrpCommonAuditLogQueryVO vo) {
		PMap pm = this.newMapInstance();
		pm.include("opCode", vo.getOpCode());
		pm.includeIgnoreCase("opName", vo.getOpName());
		pm.include("ipAddr", vo.getIpAddr());
		if((!"-1".equals(vo.getBizType())) && (vo.getBizType() != null)){
			pm.eq("bizType", vo.getBizType());
		}
		if((!"-1".equals(vo.getLogType())) && (vo.getLogType() != null)){
			pm.eq("logType", vo.getLogType());
		}
		pm.range("createdTime", vo.getStartTime(),vo.getEndTime());
		return pm;
	}

	@Override
	public Integer deleteLogsByParams(FbrpCommonAuditLogQueryVO vo) {
		return this.deleteBy(toMap(vo));
	}
	
}
