/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.mqm.frame.dbtool.dialect.IDialect;
import com.mqm.frame.dbtool.service.IDsService;
import com.mqm.frame.dbtool.util.DbTypeUtils;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * <pre>
 * 对外暴露的方言实例，以简化模块间交互。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DbDialect extends ApplicationObjectSupport {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_dbtool_dbDialect";
	
	private IDsService dsService;
	
	/**
	 * 查询方言。
	 * 
	 * @param ds FbrpDbtoolDs
	 * 
	 * @return IDialect
	 */
	public IDialect getDialectByDsId(FbrpDbtoolDs ds){
		return this.getDialectByDsId(ds.getId());
	}
	
	/**
	 * 查询方言。
	 * 
	 * @param dsId String
	 * @return IDialect
	 */
	public IDialect getDialectByDsId(String dsId){
		SimpleJdbcTemplate simpleJdbcTemplate = 
			this.dsService.getSimpleJdbcTemplate(dsId);
		if(simpleJdbcTemplate == null){
			return null;
		}
		IDialect dialect = null;
		if(!"fbrp_security_localDBAuthAp".equals(dsId)){
			FbrpDbtoolDs fbrpDbtoolDsVO = this.dsService.getFbrpDbtoolDsVObyId(dsId);
			if(!BaseConstants.DATASOURSE_JNDI.equals(fbrpDbtoolDsVO.getDbType())){
				dialect = (IDialect)this.getApplicationContext().getBean(this.getDialectName(fbrpDbtoolDsVO.getDbType()));
				return dialect;
			}
		}
		String dialectName = DbTypeUtils.analyseDbType(simpleJdbcTemplate);
		dialect = (IDialect)this.getApplicationContext().getBean(this.getDialectName(dialectName));
		return dialect;
	}
	
	/**
	 * 依据数据库类型返回对应的IDialect。
	 * 
	 * @param dbType 数据库类型，注意不要传入“JNDI”
	 * 
	 * @return IDialect IDialect方言
	 */
	public IDialect getDialectByDbType(String dbType){
		if(BaseConstants.DATASOURSE_JNDI.equals(dbType)){
			return null;
		}
		IDialect dialect = (IDialect)this.getApplicationContext().getBean(this.getDialectName(dbType));
		return dialect;
	}

	private String getDialectName(String dbType){
		StringBuffer dialectName = new StringBuffer("fbrp_dbtool_");
		dialectName.append(InternationalizationUtil.toLowerCase(dbType));
		dialectName.append("Dialect");
		return dialectName.toString();
	}
	
	/**
	 * 设置 dsService。
	 * 
	 * @param dsService dsService
	 */
	public void setDsService(IDsService dsService) {
		this.dsService = dsService;
	}
	
}
