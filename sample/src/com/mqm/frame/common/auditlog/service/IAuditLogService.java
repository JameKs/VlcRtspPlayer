/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.auditlog.service;

import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLogQueryVO;
import com.mqm.frame.infrastructure.auditlog.service.IAuditLogger;
import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.util.PagedResult;

/**
 * 
 * <pre>
 * 日志服务接口。
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
public interface IAuditLogService extends IGenericService<FbrpCommonAuditLog>, IAuditLogger {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_common_auditLogService";

	/**
	 * 获取当前应用的所有日志,如果pageSize<1则返回所有数据。
	 * 
	 * @param vo
	 *            查询条件
	 * @param offset
	 *            偏移位置,用于分页
	 * @param pageSize
	 *            查询数量,用于分页
	 * 
	 * @return Object[],
	 *         Object[0]为数据列表List<FbrpInfraAuditLogVO>,Object[1]为总数Integer.
	 */
	public PagedResult<FbrpCommonAuditLog> pagedQuery(FbrpCommonAuditLogQueryVO vo, int offset, int pageSize);

	/**
	 * 根据删除参数删除日志。
	 * 
	 * @param auditLogQuery
	 *            删除参数
	 * 
	 * @return Integer
	 */
	public Integer deleteLogsByParams(FbrpCommonAuditLogQueryVO auditLogQuery);
}
