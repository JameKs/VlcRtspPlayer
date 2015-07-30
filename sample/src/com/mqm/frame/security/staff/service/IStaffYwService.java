/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.service;

import com.mqm.frame.infrastructure.service.IDefaultService;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author lijiawei  lijiawei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IStaffYwService extends IDefaultService {

	/**
	 * 删除管事制相关数据。
	 * 
	 * @param orgcode String
	 * @param swryDm String
	 */
	public void delGszDate(String orgCode, String swrydm);
}
