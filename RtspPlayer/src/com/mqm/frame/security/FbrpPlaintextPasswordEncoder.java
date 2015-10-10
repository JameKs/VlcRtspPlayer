/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

/**
 * <pre>
 * 自定义Spring Security内置的PlaintextPasswordEncoder，忽略私钥（Salt）。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpPlaintextPasswordEncoder extends PlaintextPasswordEncoder {

	@Override
	protected String mergePasswordAndSalt(String password, Object salt,
			boolean strict) {
        if (password == null) {
            password = "";
        }
        return password;
	}
	
}
