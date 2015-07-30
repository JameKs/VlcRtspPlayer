/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.synchro;

import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 此接口用于扩展平台员工数据维护操作, 若需要与外部应用同步员工数据时实现此接口. 实现的接口会被StaffServiceImpl自动调用。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface ISynStaffInfoToThirdParty {

	/**
	 * 同步保存员工数据。
	 * 
	 * @param staff FbrpSecStaff
	 * @throws FbrpException FBRP异常
	 */
	void save(FbrpSecStaff staff) throws FbrpException;

	/**
	 * 同步修改员工数据。
	 * 
	 * @param staff FbrpSecStaff
	 * @throws FbrpException FBRP异常
	 */
	void update(FbrpSecStaff staff) throws FbrpException;

	/**
	 * 同步删除员工数据。
	 * 
	 * @param staff FbrpSecStaff
	 * @throws FbrpException FBRP异常
	 */
	void delete(FbrpSecStaff staff) throws FbrpException;
}
