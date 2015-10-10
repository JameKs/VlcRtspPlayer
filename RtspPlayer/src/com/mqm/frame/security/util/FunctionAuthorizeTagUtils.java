/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.util;

import java.io.IOException;
import java.util.List;

import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.security.FbrpGrantedAuthority;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public abstract class FunctionAuthorizeTagUtils  {

	/**
	 * 判断是否授权某具体功能。
	 * 
	 * @param function String
	 * 
	 * @return boolean
	 * 
	 * @throws IOException 异常
	 */
	public static boolean isFunctionAuthorize(String function) throws IOException {
		
		UserProfileVO userProfileVO = (UserProfileVO)ContextUtil.get("UserProfile", ContextUtil.SCOPE_SESSION);
		List<FbrpGrantedAuthority> authorties = userProfileVO.getGrantedAuthorties();
		if(authorties != null) {
			for (FbrpGrantedAuthority grantedAuthority : authorties) {
				if(FbrpGrantedAuthority.FUNCTION_GRANT_TYPE.equals(grantedAuthority.getGrantType())) {
					if(function.equals(grantedAuthority.getId())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}