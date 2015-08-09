/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.factory;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpSqlSessionFactoryBean extends SqlSessionFactoryBean {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Configuration config = this.getObject().getConfiguration();
		new AutoConfigBuilder().built(config);
	}

}
