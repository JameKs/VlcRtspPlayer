/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jndi.JndiTemplate;

import com.mqm.frame.dbtool.service.IDsService;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDsVO;
import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.exception.FbrpException;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;

/**
 * <pre>
 * 数据源服务实现。
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
public class DsServiceImpl extends MyBatisServiceImpl<FbrpDbtoolDs> implements
		IDsService {

	private static final Log log = LogFactory.getLog(DsServiceImpl.class);

	// 数据源缓存
	private Map<String, DataSource> dataSourceMap = Collections
			.synchronizedMap(new HashMap<String, DataSource>());

	@Override
	@SuppressWarnings("unchecked")
	public List<FbrpDbtoolDs> getAllFbrpDbtoolDsVO() {
		/*
		 * List<FbrpDbtoolDsVO> list = this.getHibernateTemplate().find( "from "
		 * + FbrpDbtoolDsVO.class.getName() ); return list;
		 */

		return this.list();
	}

	/**
	 * 保存最新的数据源信息。
	 * 
	 * @param fbrpDbtoolDsVO
	 *            FbrpDbtoolDs
	 */
	public void saveOrUpdateFbrpDbtoolDsVO(FbrpDbtoolDs fbrpDbtoolDsVO) {
		if (fbrpDbtoolDsVO.getId() == null
				|| fbrpDbtoolDsVO.getId().length() == 0) {
			// fbrpDbtoolDsVO.setId(this.getKeyGen().getUUIDKey());
		} else {
			// 更新前应先销毁缓存中相应的数据源
			if (this.dataSourceMap.containsKey(fbrpDbtoolDsVO.getId())) {
				DataSource dataSource = (DataSource) this.dataSourceMap
						.get(fbrpDbtoolDsVO.getId());
				if (dataSource instanceof BasicDataSource) {
					try {
						((BasicDataSource) dataSource).close();
					} catch (Exception ex) {
						log.error("", ex);
					}
				}
				this.dataSourceMap.remove(fbrpDbtoolDsVO.getId());
			}
		}
		// 如果为普通型数据库则清除JNDI的名称
		if (!BaseConstants.DATASOURSE_JNDI.equals(fbrpDbtoolDsVO.getDbType())) {
			fbrpDbtoolDsVO.setJndiName("");
		}
		// this.getHibernateTemplate().saveOrUpdate(fbrpDbtoolDsVO);
		this.createOrUpdate(fbrpDbtoolDsVO);
	}

	@Override
	public FbrpDbtoolDs getFbrpDbtoolDsVObyId(String id) {
		/*
		 * FbrpDbtoolDsVO fbrpDbtoolDsVO = (FbrpDbtoolDsVO) this
		 * .getHibernateTemplate().get(FbrpDbtoolDsVO.class, id); return
		 * fbrpDbtoolDsVO;
		 */
		return this.find(id);
	}

	@Override
	public Object[] getListByParams(FbrpDbtoolDs fbrpDbtoolDsVO, int offSet,
			int pageSize) throws FbrpException {
		/*
		 * Object[] objectArray = null; HqlWrapper hqlWrapper = new
		 * HqlWrapper(); hqlWrapper.setHql("from " +
		 * FbrpDbtoolDsVO.class.getName()); if (fbrpDbtoolDsVO != null) {
		 * hqlWrapper.setConditionIncludeIgnoreCase("name",
		 * fbrpDbtoolDsVO.getName()); if
		 * (!"-1".equals(fbrpDbtoolDsVO.getDbType())) {
		 * hqlWrapper.setConditionIncludeIgnoreCase("dbType",
		 * fbrpDbtoolDsVO.getDbType()); }
		 * hqlWrapper.setConditionIncludeIgnoreCase("url",
		 * fbrpDbtoolDsVO.getUrl()); // 当前应用
		 * //hqlWrapper.setConditionEqual("appId", this.getAppId());
		 * 
		 * objectArray = this.find_Hibernate_ComposedHQL(hqlWrapper, offSet,
		 * pageSize); }
		 */
		PMap pm = this.newMapInstance();
		if (fbrpDbtoolDsVO != null) {
			pm.includeIgnoreCase("name", fbrpDbtoolDsVO.getName());
			if (!"-1".equals(fbrpDbtoolDsVO.getDbType())) {
				pm.includeIgnoreCase("dbType", fbrpDbtoolDsVO.getDbType());
			}
			pm.includeIgnoreCase("url", fbrpDbtoolDsVO.getUrl());
		}
		PagedResult<FbrpDbtoolDs> pagedQuery = this.pagedQuery(pm, offSet,
				pageSize);
		Long count = pagedQuery.getTotal();
		Object[] objectArray = { pagedQuery.getData(), count.intValue() };
		return objectArray;
	}

	/**
	 * 批量删除定义的数据源。
	 * 
	 * @param idList
	 *            ID集合
	 * @return int 被删除的行数
	 */
	public int deleteAllByIdList(List<String> idList) {
		for (String id : idList) {
			// 关闭并销毁已初始化的数据源
			if (this.dataSourceMap.containsKey(id)) {
				DataSource dataSource = (DataSource) this.dataSourceMap.get(id);
				if (dataSource instanceof BasicDataSource) {
					try {
						((BasicDataSource) dataSource).close();
					} catch (Exception ex) {
						log.error("", ex);
					}
				}
				this.dataSourceMap.remove(id);
			}
		}
		int deletedRows = 0;
		// this.deleteAllByIn_Hibernate(FbrpDbtoolDsVO.class, "id", idList);
		super.deleteByIds(idList);
		return deletedRows;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FbrpDbtoolDs> getListByAppId(String appId) {
		/*
		 * List<FbrpDbtoolDsVO> list = this.getHibernateTemplate().find( "from "
		 * + FbrpDbtoolDsVO.class.getName() ); return list;
		 */

		return this.list();
	}

	/**
	 * 初始化普通的DataSource。
	 * 
	 * @param fbrpDbtoolDsVO
	 *            初始化DataSource所必须的参数
	 * 
	 * @return 初始化后的DataSource
	 */
	private synchronized DataSource initDataSource(FbrpDbtoolDs fbrpDbtoolDsVO) {
		// 连接信息为空或测试连接未通过则返回空
		if (fbrpDbtoolDsVO == null || (!this.testConnection(fbrpDbtoolDsVO))) {
			return null;
		}
		if (dataSourceMap.containsKey(fbrpDbtoolDsVO.getId())) {
			return (DataSource) dataSourceMap.get(fbrpDbtoolDsVO.getId());
		} else {
			if (log.isInfoEnabled()) {
				log.info("实例化一新的DataSource:  " + fbrpDbtoolDsVO.getId());
			}
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setUrl(fbrpDbtoolDsVO.getUrl());
			dataSource.setDriverClassName(fbrpDbtoolDsVO.getDriver());
			dataSource.setUsername(fbrpDbtoolDsVO.getUsername());
			dataSource.setPassword(fbrpDbtoolDsVO.getPasswd());
			dataSource.setMaxActive(fbrpDbtoolDsVO.getMaxActive() == null ? 8
					: fbrpDbtoolDsVO.getMaxActive().intValue());
			dataSource.setMinIdle(fbrpDbtoolDsVO.getMinIdle() == null ? 0
					: fbrpDbtoolDsVO.getMinIdle().intValue());
			dataSource.setMaxIdle(fbrpDbtoolDsVO.getMaxIdle() == null ? 8
					: fbrpDbtoolDsVO.getMaxIdle().intValue());
			dataSource.setMaxWait(fbrpDbtoolDsVO.getMaxWait() == null ? 60000
					: fbrpDbtoolDsVO.getMaxWait().intValue());
			dataSource
					.setValidationQuery(fbrpDbtoolDsVO.getValidationQuery() == null ? ""
							: fbrpDbtoolDsVO.getValidationQuery().trim());
			this.dataSourceMap.put(fbrpDbtoolDsVO.getId(), dataSource);
			return dataSource;
		}
	}

	/**
	 * 根据Jndi名初始化一个JNDI数据源。
	 * 
	 * @param fbrpDbtoolDsVO
	 *            JNDI连接信息
	 * 
	 * @return JNDI数据源
	 */
	private synchronized DataSource initJndiDataSource(
			FbrpDbtoolDs fbrpDbtoolDsVO) {
		if (fbrpDbtoolDsVO == null) {
			return null;
		}
		if (dataSourceMap.containsKey(fbrpDbtoolDsVO.getId())) {
			return (DataSource) dataSourceMap.get(fbrpDbtoolDsVO.getId());
		}
		JndiTemplate jndiTemplate = new JndiTemplate();
		DataSource dataSource = null;
		try {
			// 查找JNDI数据源
			dataSource = (DataSource) jndiTemplate.lookup(fbrpDbtoolDsVO
					.getJndiName());
			// 缓存JNDI数据源
			this.dataSourceMap.put(fbrpDbtoolDsVO.getId(), dataSource);
		} catch (Exception e) {
			log.error("初始化JNDI数据源[" + fbrpDbtoolDsVO.getJndiName() + "]失败", e);
		}
		return dataSource;
	}

	/**
	 * 获取JNDI数据源。
	 * 
	 * @param fbrpDbtoolDsVO
	 *            FbrpDbtoolDs
	 * 
	 * @return DataSource
	 */
	private DataSource getJndiDataSource(FbrpDbtoolDs fbrpDbtoolDsVO) {
		if (this.dataSourceMap.containsKey(fbrpDbtoolDsVO.getId())) {
			return this.dataSourceMap.get(fbrpDbtoolDsVO.getId());
		} else {
			return this.initJndiDataSource(fbrpDbtoolDsVO);
		}
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate(String dsId) {
		if (dsId == null) {
			return null;
		} else if (this.dataSourceMap.containsKey(dsId)) {
			DataSource dataSource = this.dataSourceMap.get(dsId);
			return new SimpleJdbcTemplate(dataSource);
			// 本地资料库
		} else if ("fbrp_security_localDBAuthAp".equals(dsId)) {
			return this.getSimpleJdbcTemplate();
		} else {
			FbrpDbtoolDs fbrpDbtoolDsVO = this.getFbrpDbtoolDsVObyId(dsId);
			if (fbrpDbtoolDsVO == null) {
				return null;
			}
			if (BaseConstants.DATASOURSE_JNDI
					.equals(fbrpDbtoolDsVO.getDbType())) {
				// JNDI处理
				DataSource dataSource = this.getJndiDataSource(fbrpDbtoolDsVO);
				if (dataSource == null) {
					return null;
				} else {
					return new SimpleJdbcTemplate(dataSource);
				}
			} else {
				// 常规处理
				DataSource dataSource = initDataSource(fbrpDbtoolDsVO);
				return new SimpleJdbcTemplate(dataSource);
			}
		}
	}

	@Override
	public boolean testConnection(FbrpDbtoolDs fbrpDbtoolDsVO)
			throws FbrpException {
		// 处理结果
		boolean flag = false;
		// JNDI数据源处理
		if (BaseConstants.DATASOURSE_JNDI.equals(fbrpDbtoolDsVO.getDbType())) {
			JndiTemplate jndiTemplate = new JndiTemplate();
			DataSource dataSource = null;
			try {
				// 查找JNDI数据源
				dataSource = (DataSource) jndiTemplate.lookup(fbrpDbtoolDsVO
						.getJndiName());
			} catch (Exception e) {
				log.error("", e);
				return false;
			}
			if (dataSource != null) {
				flag = true;
			}
		} else {
			// 常规处理
			String driver = fbrpDbtoolDsVO.getDriver();
			String url = fbrpDbtoolDsVO.getUrl();
			Connection conn = null;
			try {
				if ((!StringUtil.isEmpty(driver)) && (!StringUtil.isEmpty(url))) {
					Class.forName(driver);
					// 设定登录超时时间 add by gongguanyuan
					DriverManager.setLoginTimeout(3);
					conn = DriverManager.getConnection(url,
							fbrpDbtoolDsVO.getUsername(),
							fbrpDbtoolDsVO.getPasswd());
					if (conn != null) {
						flag = true;
					}
				}
			} catch (ClassNotFoundException e) {
				log.info("", e);
			} catch (Exception e) {
				log.info("", e);
			} finally {
				JdbcUtils.closeConnection(conn);
			}
		}
		return flag;
	}

	/**
	 * 销毁数据源。
	 * 
	 * @throws Exception
	 *             抛出异常
	 */
	public void destroy() throws Exception {
		Iterator<Map.Entry<String, DataSource>> it = dataSourceMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			try {
				Map.Entry<String, DataSource> entry = it.next();
				DataSource dataSource = (DataSource) entry.getValue();
				if (dataSource instanceof BasicDataSource) {
					((BasicDataSource) dataSource).close();
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		// 清空Map
		dataSourceMap.clear();
	}

	@Override
	public Map<String, FbrpDbtoolDs> getDefaultDsVOMap(
			List<FbrpCommonCodeValue> dbTypeList) {
		// 返回值
		Map<String, FbrpDbtoolDs> defaultDsVOMap = new HashMap<String, FbrpDbtoolDs>();
		// 数据库类型及默认连接数
		for (int i = 0; i < dbTypeList.size(); i++) {
			FbrpDbtoolDs ds = new FbrpDbtoolDs();
			FbrpCommonCodeValue codeValueVO = (FbrpCommonCodeValue) dbTypeList
					.get(i);

			ds.setDbType(codeValueVO.getValue1());
			ds.setDriver(codeValueVO.getValue2());
			ds.setUrl(codeValueVO.getValue3());
			ds.setValidationQuery(codeValueVO.getValue4());
			ds.setMaxActive(FbrpDbtoolDs.DEFAULT_MAX_ACTIVE);
			ds.setMaxIdle(FbrpDbtoolDs.DEFAULT_MAX_IDLE);
			ds.setMinIdle(FbrpDbtoolDs.DEFAULT_MIN_IDLE);
			ds.setMaxWait(FbrpDbtoolDs.DEFAULT_MAX_WAIT);
			defaultDsVOMap.put(codeValueVO.getValue1(), ds);
		}
		return defaultDsVOMap;
	}

	/**
	 * Object类型转换为String类型。
	 * 
	 * @param input
	 *            Object
	 * 
	 * @return String
	 */
	public String objectToString(Object input) {
		return (input == null) ? "" : input.toString();
	}

	/**
	 * Object类型转换为Long类型。
	 * 
	 * @param input
	 *            Object
	 * 
	 * @return Long
	 */
	public Long objectToLong(Object input) {
		if (input == null) {
			return Long.valueOf(0);
		}
		return Pattern.matches("[0-9]+", input.toString()) ? Long.valueOf(input
				.toString()) : Long.valueOf(0);
	}

	@Override
	public PagedResult<FbrpDbtoolDsVO> pagedQuery(FbrpDbtoolDsVO fbrpDbtoolDs,
			int pageIndex, int pageSize) {
	    PagedResult<FbrpDbtoolDsVO> pr = this.pagedQuery("selectDs", fbrpDbtoolDs, pageIndex, pageSize);
		return pr;
	}
	
	@Override
	public String createOrUpdateDs(FbrpDbtoolDs fbrpDbtoolDs){
		String name = fbrpDbtoolDs.getName().trim();
		Map<String,String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("id", fbrpDbtoolDs.getId());
		if(StringUtil.isEmpty(this.findDs(map))){
			super.createOrUpdate(fbrpDbtoolDs);
			return "1";			
		}else{
			return "2";
		}		
	}
	
	/**
	 * 查找报表服务器，检验是否存在相同的名字。
	 * 
	 * @param map Map<String,String>
	 * 
	 * @return FbrpDbtoolDs
	 */
	public FbrpDbtoolDs findDs(Map<String,String> map){
		String statement = this.getStatement("selectOne");
		FbrpDbtoolDs ds = this.getSqlSessionTemplate().selectOne(statement, map);
		return ds;
	}
	
}
