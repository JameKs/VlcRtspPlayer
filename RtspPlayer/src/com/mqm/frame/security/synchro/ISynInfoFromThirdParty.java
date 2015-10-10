/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.synchro;

import com.mqm.frame.infrastructure.service.IDefaultService;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;

/**
 * 
 * <pre>
 * 当FBRP作为被集成目标时，比如RIDE集成FBRP，则需要使用到这一接口，
 * 以从这些各种第三方应用中同步重要信息到FBRP中。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface ISynInfoFromThirdParty extends IDefaultService {
	
	/**
	 * 同步所有用户。
	 * 
	 * @param appId 应用ID
	 */
	public void synchroStaffs(String appId);
	
	/**
	 * 同步指定用户。
	 * 
	 * @param appId 应用ID
	 * @param userName 同步的具体用户
	 */
	public void synchroStaffs(String appId,String userName);
	
	/**
	 * 同步指定用户的角色及其关联信息。
	 * 主要用于在用户存在时，并验证通过后，此时可以同步其角色信息。
	 * 
	 * @param appId String
	 * @param staff FbrpSecStaff
	 */
	public void synchroRoles(String appId, FbrpSecStaff staff);
}
