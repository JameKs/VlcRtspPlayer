/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.dialect.impl;

import java.util.Map;

import com.mqm.frame.dbtool.dialect.IDialect;
import com.mqm.frame.dbtool.service.IDsService;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTable;
import com.mqm.frame.dbtool.vo.FbrpDbtoolTableCol;

/**
 * <pre>
 * 数据库方言的抽象类, 可以方便对数据源的元数据进行操作.实现了<tt>IDialect</tt>接口。<p>
 * 子类必须实现supportsPaged方法,getPagedString方法,getPagedString方法。
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
public abstract class AbstractDialect implements IDialect {

	private IDsService dsService;

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getTableColInfo(FbrpDbtoolDs ds,final String tableName)
	 */
	@Override
	public Map<String, FbrpDbtoolTableCol> getTableColInfo(FbrpDbtoolDs ds,
			final String tableName) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getTables(FbrpDbtoolDs ds)
	 */
	@Override
	public Map<String, FbrpDbtoolTable> getTables(FbrpDbtoolDs ds) {
		return this.getTables(ds.getId());
	}
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.dbtool.dialect.IDialect#getTables(String dsId)
	 */
	@Override
	public Map<String, FbrpDbtoolTable> getTables(String dsId) {
		return null;
	}

	/**
	 *  设置 dsService。
	 * 
	 * @param dsService IDsService
	 *          
	 */
	public void setDsService(IDsService dsService) {
		this.dsService = dsService;
	}

	/**
	 * 返回 dsService。
	 * 
	 * @return  IDsService
	 */
	public IDsService getDsService() {
		return dsService;
	}

}
