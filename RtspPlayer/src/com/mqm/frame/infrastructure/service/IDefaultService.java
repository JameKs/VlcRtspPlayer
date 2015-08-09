/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 默认服务接口，其内置了常见的业务方法。通常，其他业务接口可以考虑继承这一接口。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IDefaultService {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_infrastructure_defaultService";
	
	/**
	 * 日志审计。
	 * 
	 * @param bizType 操作的模块
	 * @param opType 操作类型
	 * @param opInfo 操作信息
	 */
	public void auditLog(String bizType, String opType, String opInfo);

	/**
	 * 返回JDBC SimpleJdbcTemplate模板。
	 * 
	 * @return 返回JDBC SimpleJdbcTemplate模板
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public abstract SimpleJdbcTemplate getSimpleJdbcTemplate()
			throws FbrpException;

	/**
	 * 返回 sqlSessionTemplate。
	 * 
	 * @return 返回 sqlSessionTemplate。
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public abstract SqlSessionTemplate getSqlSessionTemplate() throws FbrpException;
}