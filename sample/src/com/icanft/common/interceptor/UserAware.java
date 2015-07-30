/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.interceptor;

import com.icanft.xtgl.yhgl.vo.User;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author meihu  meihu@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class UserAware {
	
	private User user;
	
	public void setUser(User user){
		this.user = user;
	}
}
