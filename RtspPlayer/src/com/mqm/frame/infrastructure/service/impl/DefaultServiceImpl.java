/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.service.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.FacesRequestAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mqm.frame.infrastructure.auditlog.service.IAuditLogger;
import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.service.IDefaultService;
import com.mqm.frame.infrastructure.service.IKeyGen;
import com.mqm.frame.infrastructure.service.IServerTime;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 服务实现类必须继承的基类。
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
public class DefaultServiceImpl extends ApplicationObjectSupport implements
		IDefaultService {

	private static final Log log = LogFactory.getLog(DefaultServiceImpl.class);

	private IServerTime serverTime;
	private IKeyGen keyGen;

	private DataSource dataSource;
	private IAuditLogger auditLogger;

	private SqlSessionTemplate sqlSessionTemplate;
	private SimpleJdbcTemplate simpleJdbcTemplate;

	private LobHandler lobHandler;

	// 为集成测试工作准备
	private boolean productionFlag = true;

	@Override
	public void auditLog(String bizType, String opType, String opInfo) {
		if (!this.productionFlag || this.auditLogger == null) {
			return;
		}
		RequestAttributes requestAttributes = null;
		try {
			requestAttributes = RequestContextHolder.currentRequestAttributes();
		} catch (IllegalStateException e) {
			log.info("非Http请求线程调用日志记录");
		}
		if (requestAttributes == null || this.getUser() == null) {
			return;
		}
		if (requestAttributes instanceof ServletRequestAttributes) {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
			this.auditLogger.auditLog(bizType, opType, opInfo, this.getUser()
					.getLoginId(), this.getUser().getStaffName(),
					servletRequestAttributes.getRequest().getRemoteAddr());

		} else if (requestAttributes instanceof FacesRequestAttributes) {
			FacesRequestAttributes facesRequestAttributes = (FacesRequestAttributes) requestAttributes;

			HttpServletRequest request = (HttpServletRequest) facesRequestAttributes
					.getAttribute(RequestAttributes.REFERENCE_REQUEST,
							RequestAttributes.SCOPE_REQUEST);

			this.auditLogger.auditLog(bizType, opType, opInfo, this.getUser()
					.getLoginId(), this.getUser().getStaffName(), request
					.getRemoteAddr());
		}
	}

	/**
	 * 借助BatchSqlUpdate批量执行JDBC操作。
	 * 
	 * @param sql
	 *            执行SQL，比如“update emp set sal=? where empno = ?”
	 * @param parameterTypes
	 *            参数类型，比如“new int[]{Types.FLOAT, Types.INTEGER}”
	 * @param valueList
	 *            参数值集合，每项值的数量要保持同参数类型的长度一致
	 */
	protected void batchCommit_JDBC(String sql, int[] parameterTypes,
			List<Object[]> valueList) {
		BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(this.dataSource, sql);
		batchSqlUpdate.setTypes(parameterTypes);
		for (Object[] valueArray : valueList) {
			batchSqlUpdate.update(valueArray);
		}
		batchSqlUpdate.flush();
	}

	/**
	 * 获取到当前登录用户的登录名。
	 * 
	 * @return 当前登录用户的登录名
	 */
	public String getLoginId() {
		if (!this.productionFlag) {
			return "TestForLoginId";
		}
		UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		if (user != null) {
			return user.getLoginId();
		} else {
			return "";
		}
	}

	/**
	 * 获取UserProfileVO。
	 * 
	 * @return UserProfileVO
	 */
	public UserProfileVO getUser() {
		if (!this.productionFlag) {
			UserProfileVO user = new UserProfileVO();
			user.setCurrentAppId("TestForCurrentAppId");
			user.setStaffId("TestForStaffId");
			user.setStaffName("TestForStaffName");
			user.setDeptId("TestForDeptId");
			user.setDeptName("TestForDeptName");
			user.setOrgId("TestForOrgId");
			user.setOrgName("TestForOrgName");
			user.setLoginId("TestForLoginId");
			user.setPasswd("TestForPasswd");
			return user;
		}
		UserProfileVO user = (UserProfileVO) ContextUtil.get("UserProfile",
				ContextUtil.SCOPE_SESSION);
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}

	/**
	 * 设置 sqlSessionTemplate。
	 * 
	 * @param sqlSessionTemplate
	 *            设置 sqlSessionTemplate。
	 */
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	/**
	 * 返回 sqlSessionTemplate。
	 * 
	 * @return 返回 sqlSessionTemplate。
	 */
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	/**
	 * 返回 simpleJdbcTemplate。
	 * 
	 * @return 返回 simpleJdbcTemplate。
	 */
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	/**
	 * 设置 simpleJdbcTemplate。
	 * 
	 * @param simpleJdbcTemplate
	 *            设置 simpleJdbcTemplate。
	 */
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	/**
	 * 获取serverTime。
	 * 
	 * @return IServerTime
	 */
	public IServerTime getServerTime() {
		return serverTime;
	}

	/**
	 * 设置serverTime。
	 * 
	 * @param serverTime
	 *            IServerTime
	 */
	public void setServerTime(IServerTime serverTime) {
		this.serverTime = serverTime;
	}

	/**
	 * 设置keyGen。
	 * 
	 * @param keyGen
	 *            IKeyGen
	 */
	public void setKeyGen(IKeyGen keyGen) {
		this.keyGen = keyGen;
	}

	/**
	 * 获取keyGen。
	 * 
	 * @return IKeyGen
	 */
	public IKeyGen getKeyGen() {
		return keyGen;
	}

	/**
	 * 设置 productionFlag。
	 * 
	 * @param productionFlag
	 *            设置 productionFlag。
	 */
	public void setProductionFlag(boolean productionFlag) {
		this.productionFlag = productionFlag;
	}

	/**
	 * 返回 productionFlag。
	 * 
	 * @return 返回 productionFlag。
	 */
	public boolean isProductionFlag() {
		return productionFlag;
	}

	/**
	 * 设置 dataSource。
	 * 
	 * @param dataSource
	 *            设置 dataSource。
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 设置 auditLogger。
	 * 
	 * @param auditLogger
	 *            设置 auditLogger。
	 */
	public void setAuditLogger(IAuditLogger auditLogger) {
		this.auditLogger = auditLogger;
	}

	protected PMap newMapInstance() {
		return this.newMapInstance(null, null);
	}

	protected PMap newMapInstance(String key, Object value) {
		PMap pm = new PMap();
		if (key != null && value != null) {
			pm.add(key, value);
		}
		return pm;
	}

	/**
	 * 分页查找。
	 * 
	 * @param key
	 *            String
	 * 
	 * @param parameter
	 *            Object
	 * 
	 * @param pageIndex
	 *            int
	 * 
	 * @param pageSize
	 *            int
	 * 
	 * @return PagedResult<T>
	 * 
	 * @param <T>
	 *            ValueObject
	 */
	public <T> PagedResult<T> pagedQuery(String key, Object parameter,
			int pageIndex, int pageSize) {
		RowBounds rb = this.getRowBounds(pageIndex, pageSize);
		String statement = this.getStatement(key);
		List<T> list = (List<T>) this.getSqlSessionTemplate().selectList(
				statement, parameter, rb);
		String statementCount = statement + "_count";
		Configuration config = this.getSqlSessionTemplate().getConfiguration();
		if (!config.hasStatement(statementCount)) {
			// TODO luxiaocheng 将来实现自动配置count语句
			throw new IllegalArgumentException("请提供 " + statementCount
					+ " 用于分页查询时统计");
		}
		// 使用缓存机制读取Total。
		Long total = this.queryTotalByUseCache(statementCount, pageIndex,
				parameter);
		PagedResult<T> pr = new PagedResult<T>(list, total, pageIndex, pageSize);
		pr.setSql(this.revolveRuntimeSQL(statement, parameter));
		return pr;
	}
	
	public <T> PagedResult<T> pagedQueryCloseDbLink(String key, Object parameter,
			int pageIndex, int pageSize, String... names) {
		RowBounds rb = this.getRowBounds(pageIndex, pageSize);
		String statement = this.getStatement(key);
		List<T> list = this.selectListCloseDbLink(statement, parameter, rb, names);
		String statementCount = statement + "_count";
		Configuration config = this.getSqlSessionTemplate().getConfiguration();
		if (!config.hasStatement(statementCount)) {
			// TODO luxiaocheng 将来实现自动配置count语句
			throw new IllegalArgumentException("请提供 " + statementCount
					+ " 用于分页查询时统计");
		}
		// 使用缓存机制读取Total。
		Long total = this.queryTotalByUseCacheCloseDbLink(statementCount, pageIndex,
				parameter, names);
		PagedResult<T> pr = new PagedResult<T>(list, total, pageIndex, pageSize);
		pr.setSql(this.revolveRuntimeSQL(statement, parameter));
		return pr;
	}

	/**
	 * 使用缓存机制读取Total。
	 * 
	 * @param statementCount String
	 * @param pageIndex int
	 * @param parameter Object
	 * @return Long
	 * 
	 * @author zengziwen@foresee.cn 2012-12-15
	 */
	public Long queryTotalByUseCache(String statementCount, int pageIndex,
			Object parameter) {
		Long total = 0L;
		// 1、缓存total数 但pageIndex=1时，不进行缓存。 2、 对SQL进行哈希。
		String userRunSql = this.createCacheCountSQLKey(statementCount,
				parameter);
		Long cacheTotal = null;
		String cacheKey = null;
		if (userRunSql != null && this.existsHttpSession()) {
			cacheKey = String.valueOf(userRunSql.hashCode());
			cacheTotal = (Long) ContextUtil.get("SQL_COUNT_KEY" + cacheKey,
					ContextUtil.SCOPE_SESSION);
		}
		if (cacheTotal != null && pageIndex != 1) {
			total = cacheTotal;
			log.debug(statementCount + "获取缓存total:" + total);
		} else {
			total = (Long) this.getSqlSessionTemplate().selectOne(
					statementCount, parameter);
			if (cacheKey != null&&this.existsHttpSession()) {
				ContextUtil.put("SQL_COUNT_KEY" + cacheKey, total,
						ContextUtil.SCOPE_SESSION);
			}
		}
		return total;
	}
	
	public Long queryTotalByUseCacheCloseDbLink(String statementCount, int pageIndex,
			Object parameter, String... names) {
		Long total = 0L;
		// 1、缓存total数 但pageIndex=1时，不进行缓存。 2、 对SQL进行哈希。
		String userRunSql = this.createCacheCountSQLKey(statementCount,
				parameter);
		Long cacheTotal = null;
		String cacheKey = null;
		if (userRunSql != null && this.existsHttpSession()) {
			cacheKey = String.valueOf(userRunSql.hashCode());
			cacheTotal = (Long) ContextUtil.get("SQL_COUNT_KEY" + cacheKey,
					ContextUtil.SCOPE_SESSION);
		}
		if (cacheTotal != null && pageIndex != 1) {
			total = cacheTotal;
			log.debug(statementCount + "获取缓存total:" + total);
		} else {
			total = (Long) this.selectOneCloseDbLink(statementCount, parameter, names);
			if (cacheKey != null&&this.existsHttpSession()) {
				ContextUtil.put("SQL_COUNT_KEY" + cacheKey, total,
						ContextUtil.SCOPE_SESSION);
			}
		}
		return total;
	}

	/**
	 * 获取运行时sql。
	 * 
	 * @param statementId
	 *            String
	 * @param parameter
	 *            Object
	 * @return String
	 */
	public String revolveRuntimeSQL(String statementId, Object parameter) {
		try {
			Configuration configuration = this.getSqlSessionTemplate()
					.getConfiguration();
			MappedStatement mappedStatement = configuration
					.getMappedStatement(statementId);
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			return boundSql.getSql() + rehandleSql(statementId, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO luxiaocheng 需要处理的问题
		return "(读到SQL过程中发生错误.)";
	}
	
	/**
	 * 获取运行时sql的业务规则说明。
	 * 
	 * @param statementId
	 *            String
	 * @param parameter
	 *            Object
	 * @return String
	 */
	private String rehandleSql(String statementId, Object parameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		String statement = this.getStatement(statementId);
		String comment = null;
		map.put("key", statement);
		map.put("cs", null);
		List<Map> list = this.getSqlSessionTemplate().selectList("com.foresee.bi.tsmp.common.service.impl.YwgzsmServiceImpl.findYwgzcs",map);
		
		for (Map o : list) {
			if(o==null){
				continue;
			}
			HashMap obj = new HashMap();
			try {
				obj = objectMapper.readValue(String.valueOf(o.get("YWGZ_CS")), HashMap.class);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(StringUtil.isEmpty(parameter)){
				break;
			}else if(parameter instanceof String || parameter instanceof Number){//处理字符串或数字
				String str = String.valueOf(parameter);
				Iterator iter = obj.entrySet().iterator();
				int i=1;
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry)iter.next();
					Object key = entry.getKey();
					if(String.valueOf(obj.get(key)).equalsIgnoreCase(str)){
						map.put("cs", o.get("YWGZ_CS"));
						i = 0;
						break;
					}
				}
				if(i==0){
					break;
				}
			}else if(parameter instanceof Map){//处理Map
				Map para = (Map)parameter;
				Iterator iter = obj.entrySet().iterator();
				if(para.size()!=obj.size()){
					continue;
				}
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry)iter.next();
					Object key = entry.getKey();
					if(para.get(key)!=null){
						if(!String.valueOf(obj.get(key)).equalsIgnoreCase(String.valueOf(para.get(key)))){
							break;
						}else if(!iter.hasNext()){
							map.put("cs", o.get("YWGZ_CS"));
						}
					}else{
						break;
					}
				}
				if(map.get("cs")!=null){
					break;
				}
			}else if(parameter instanceof List){//处理List
//				List para = (List)parameter;
//				Iterator iter = para.iterator();
//				Iterator objIt = obj.entrySet().iterator();
//				int i = para.size();
//				if(i!=obj.size()){
//					continue;
//				}
//				while(iter.hasNext()){
//					String temp = String.valueOf(iter.next());
//					if(temp!=null){
//						while(objIt.hasNext()){
//							Map.Entry entry = (Map.Entry)objIt.next();
//							String value = String.valueOf(entry.getValue());
//							if(value.equals(temp)){
//								i -= 1;
//								break;
//							}
//						}
//					}
//				}
//				if(i==0){
//					map.put("cs", o.get("YWGZ_CS"));
//					break;
//				}
			}else if(parameter.getClass().getDeclaredFields().length>0){//处理VO
				Method [] method = parameter.getClass().getMethods();
				Iterator iter = obj.entrySet().iterator();
				while(iter.hasNext()){
					Map.Entry entry = (Map.Entry)iter.next();
					String key = (String)entry.getKey();
					String value = String.valueOf(entry.getValue());
					String methodName = "get" + key.substring(0, 1).toUpperCase()+key.substring(1);//获得get方法名
					String methodValue = null;
					for(Method m : method){
						if(m.getName().equals(methodName)){
							try {
								methodValue = String.valueOf(m.invoke(parameter));//获得属性值
							}catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
					}
					if(methodValue!=null){
						if(!methodValue.equals(value)){
							break;
						}else if(!iter.hasNext()){
							map.put("cs", o.get("YWGZ_CS"));
						}
					}else{
						break;
					}
				}
				if(map.get("cs")!=null){
					break;
				}
			}
		}
		List<Map<String, String>> ywgzsmList = this.getSqlSessionTemplate().selectList("com.foresee.bi.tsmp.common.service.impl.YwgzsmServiceImpl.findYwgzsmMap", map);
		if(ywgzsmList!=null && ywgzsmList.size()>0)
		{
			String descYwgz = StringUtil.isEmpty(ywgzsmList.get(0).get("YWGZ_SM"))?" ":ywgzsmList.get(0).get("YWGZ_SM");
			String descJcywkj =StringUtil.isEmpty(ywgzsmList.get(0).get("JCYWKJSM"))?" ":ywgzsmList.get(0).get("JCYWKJSM");
			
			return "&" +descYwgz+"&"+ descJcywkj;
		}else
			return "& & ";
		
	}

	/**
	 * 构建运count SQL与参数组成Key。
	 * 
	 * @param statementId
	 *            String
	 * @param parameter
	 *            Object
	 * @return String
	 */
	private String createCacheCountSQLKey(String statementId, Object parameter) {
		try {
			Configuration configuration = this.getSqlSessionTemplate()
					.getConfiguration();
			MappedStatement mappedStatement = configuration
					.getMappedStatement(statementId);
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			List<ParameterMapping> list = boundSql.getParameterMappings();
			StringBuffer sbf = new StringBuffer();
			if (list != null) {
				for (ParameterMapping p : list) {
					String name = p.getProperty();
					Object object = boundSql.getAdditionalParameter(name);
					String value = object == null ? "" : object.toString();// TODO
																			// 可能存嵌套其它对象的情况，暂不考虑。
																			// add
																			// by
																			// zengzw
					sbf.append(name).append("=").append(value).append(";");
				}
			}
			return boundSql.getSql() + ";" + sbf.toString();
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 根据id查询实体。
	 * 
	 * @param key
	 *            String
	 * @param id
	 *            String
	 * @param <T>
	 *            ValueObject
	 * 
	 * @return T
	 */
	public <T> T find(String key, String id) {
		String statement = this.getStatement(key);
		return (T) this.getSqlSessionTemplate().selectOne(statement, id);
	}

	/**
	 * 根据条件查询实体的列表。
	 * 
	 * @param key
	 *            String
	 * @param map
	 *            Map<String,Object>
	 * @param <T>
	 *            ValueObject
	 * 
	 * @return List<T>
	 */
	public <T> List<T> findBy(String key, Map<String, Object> map) {
		String statement = this.getStatement(key);
		return (List<T>) this.getSqlSessionTemplate()
				.selectList(statement, map);
	}

	/**
	 * 列出所有的实体。
	 * 
	 * @param key
	 *            String
	 * @param orders
	 *            Order
	 * @param <T>
	 *            ValueObject
	 * 
	 * @return List<T>
	 */
	public <T> List<T> list(String key, Order... orders) {
		return this.findBy(key, Collections.<String, Object> emptyMap());
	}

	/**
	 * 查找符合条件的实体列表。
	 * 
	 * @param key
	 *            String
	 * @param property
	 *            String
	 * @param value
	 *            Object
	 * @param <T>
	 *            ValueObject
	 * 
	 * @return List<T>
	 */
	public <T> List<T> findBy(String key, String property, Object value) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(property, value);
		return this.findBy(key, map);
	}

	/**
	 * 查找符合条件的一个实体。
	 * 
	 * @param key
	 *            String
	 * @param property
	 *            String
	 * @param value
	 *            Object
	 * @param <T>
	 *            ValueObject
	 * 
	 * @return T
	 */
	public <T> T findExactBy(String key, String property, Object value) {
		List<T> list = this.findBy(key, property, value);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查找符合条件的一个实体。
	 * 
	 * @param key
	 *            String
	 * @param map
	 *            Map<String,Object>
	 * @param <T>
	 *            ValueObject
	 * 
	 * @return T
	 */
	public <T> T findExactBy(String key, Map<String, Object> map) {
		List<T> list = this.findBy(key, map);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据条件插入实体。
	 * 
	 * @param key
	 *            String
	 * 
	 * @param t
	 *            T
	 * 
	 * @param <T>
	 *            ValueObject
	 */
	public <T extends ValueObject> void create(String key, T t) {
		if (!StringUtils.hasText(t.getId())) {
			t.setId(this.getKeyGen().getUUIDKey());
		}
		String statement = this.getStatement(key);
		this.getSqlSessionTemplate().insert(statement, t);
	}

	/**
	 * 根据条件更新实体。
	 * 
	 * @param key
	 *            String
	 * 
	 * @param t
	 *            T
	 * 
	 * @param <T>
	 *            ValueObject
	 */
	public <T extends ValueObject> void update(String key, T t) {
		if (!StringUtils.hasText(t.getId())) {
			throw new IllegalArgumentException("更新操作必须提供实体类的ID值");
		}
		String statement = this.getStatement(key);
		this.getSqlSessionTemplate().update(statement, t);
	}

	/**
	 * 根据条件删除实体。
	 * 
	 * @param key
	 *            String
	 * 
	 * @param t
	 *            T
	 * 
	 * @param <T>
	 *            ValueObject
	 */
	public <T extends ValueObject> void delete(String key, T t) {
		this.delete(key, t.getId());
	}

	/**
	 * 根据条件删除实体。
	 * 
	 * @param key
	 *            String
	 * 
	 * @param id
	 *            String
	 * 
	 * @return int
	 */
	public int delete(String key, String id) {
		if (!StringUtils.hasText(id)) {
			throw new IllegalArgumentException("删除操作必须提供实体类的ID值");
		}
		String statement = this.getStatement(key);
		return this.getSqlSessionTemplate().delete(statement, id);
	}

	protected void batchCreate(String statement, List<?> list) {
		this.batchCreate(statement, list, 1000);
	}

	protected void batchCreate(String statement, List<?> list, int frequency) {
		String statementId = this.getStatement(statement);
		SqlSession session = this.openBatchSession();
		try {
			for (int i = 0; i < list.size(); i++) {
				if (i > 0 && (i % frequency) == 0) {
					session.flushStatements();
				}
				session.insert(statementId, list.get(i));
			}
			session.flushStatements();
		} catch (Exception e) {
			throw new FbrpException("批量插入数据时发生错误!", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	protected void batchUpdate(String statement, List<?> list) {
		this.batchUpdate(statement, list, 1000);
	}

	protected void batchUpdate(String statement, List<?> list, int frequency) {
		String statementId = this.getStatement(statement);
		SqlSession session = this.openBatchSession();
		try {
			for (int i = 0; i < list.size(); i++) {
				if (i > 0 && (i % frequency) == 0) {
					session.flushStatements();
				}
				session.update(statementId, list.get(i));
			}
			session.flushStatements();
		} catch (Exception e) {
			throw new FbrpException("批量更新数据时发生错误!", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 根据条件删除实体。
	 * 
	 * @param key
	 *            String
	 * @param map
	 *            Map<String,Object>
	 * 
	 * @return int
	 */
	public int deleteBy(String key, Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException("条件删除操作必须提供至少一个条件");
		}
		String statement = this.getStatement(key);
		return this.getSqlSessionTemplate().delete(statement, map);
	}

	protected String getStatement(String key) {
		String statement = null;
		if (key != null && key.indexOf(".") > -1) {
			statement = key;
		} else {
			statement = this.getClass().getName() + "." + key;
		}
		return statement;
	}

	protected RowBounds getRowBounds(int pageIndex, int pageSize) {
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		int offset = (pageIndex - 1) * pageSize;
		RowBounds rb = new RowBounds(offset, pageSize);
		return rb;
	}

	protected boolean hasText(String str) {
		return StringUtils.hasText(str);
	}

	protected static Order asc(String property) {
		return Order.ASC(property);
	}

	protected static Order desc(String property) {
		return Order.DESC(property);
	}

	private SqlSession openBatchSession() {
		SqlSessionFactory factory = this.getSqlSessionTemplate()
				.getSqlSessionFactory();
		SqlSession session = factory.openSession(ExecutorType.BATCH, false);
		return session;
	}

	/**
	 * lobHandler的SET方法。
	 * 
	 * @param lobHandler LobHandler
	 */
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	/**
	 * lobHandler的GET方法。
	 * 
	 * @return LobHandler
	 */
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	private boolean existsHttpSession(){
		ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		Object obj = requestAttributes.getRequest().getSession(false);
		return obj == null ? false : true;
	}
	
	protected <T> List<T> selectListCloseDbLink(String key, Object param, RowBounds rowBounds, String... names) {
		List<T> list = null;
		String statement = this.getStatement(key);
		SqlSession session = this.openSession();
		try {
			if(null != rowBounds) {
				list = session.selectList(statement, param, rowBounds);
			} else {
				list = session.selectList(statement, param);
			}
			this.closeDbLink(session, names);
		} catch (Exception e) {
			this.closeDbLink(session, names);
			throw new FbrpException("关闭DBLINK时发生错误!", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	protected <T> List<T> selectListCloseDbLink(String key, Object param, String... names) {
		return this.selectListCloseDbLink(key, param, null, names);
	}
	
	protected <T> T selectOneCloseDbLink(String key, Object param, String... names) {
		T t = null;
		String statement = this.getStatement(key);
		SqlSession session = this.openSession();
		try {
			t = session.selectOne(statement, param);
			this.closeDbLink(session, names);
		} catch (Exception e) {
			this.closeDbLink(session, names);
			throw new FbrpException("关闭DBLINK时发生错误!", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}
	
	protected <T> void insertCloseDbLink(String key, T param, String... names) {
		String statement = this.getStatement(key);
		SqlSession session = this.openSession();
		try {
			session.insert(statement, param);
			this.closeDbLink(session, names);
		} catch (Exception e) {
			this.closeDbLink(session, names);
			throw new FbrpException("关闭DBLINK时发生错误!", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	protected <T> void updateCloseDbLink(String key, T param, String... names) {
		String statement = this.getStatement(key);
		SqlSession session = this.openSession();
		try {
			session.update(statement, param);
			this.closeDbLink(session, names);
		} catch (Exception e) {
			this.closeDbLink(session, names);
			throw new FbrpException("关闭DBLINK时发生错误!", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	private SqlSession openSession() {
		SqlSessionFactory factory = this.getSqlSessionTemplate()
				.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		return session;
	}
	
	private void closeDbLink(SqlSession session, String... names) {
		String closeDbLink = DefaultServiceImpl.class.getName() + ".closeDbLink";
		Map<String, String> map = new HashMap<String, String>();
		for(String name : names) {
			map.put("DB_LINK_NAME", name);
			session.update(closeDbLink, map);
		}
	}
	
}
