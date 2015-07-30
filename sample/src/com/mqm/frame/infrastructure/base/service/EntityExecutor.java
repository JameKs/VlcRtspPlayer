package com.mqm.frame.infrastructure.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.StringUtils;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.Operator;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.persistence.Param;
import com.mqm.frame.infrastructure.persistence.factory.AutoConfigBuilder;
import com.mqm.frame.infrastructure.persistence.model.InModel;
import com.mqm.frame.infrastructure.service.IKeyGen;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.util.VOUtils;
/**
 * 
 * <pre>
 * 单表实体类操作执行器。
 * </pre>
 * @author luxiaocheng  luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 * @param <T>
 */
public class EntityExecutor<T extends ValueObject> {
	private Class<T> entryClazz;
	private IKeyGen keyGen;
	private SqlSessionTemplate sqlSessionTemplate;

	protected EntityExecutor(Class<T> entryClazz, IKeyGen keyGen, SqlSessionTemplate sqlSessionTemplate) {
		this.entryClazz = entryClazz;
		this.keyGen = keyGen;
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	// CRUD接口=========================================================================
	/**
	 * 创建实体。
	 * 
	 * @param t T
	 */
	public void create(T t) {
		String sqlId = this.getStatementId("insert");
		if (!StringUtils.hasText(t.getId())) {
			t.setId(this.getKeyGen().getUUIDKey());
		}
		this.getSqlSessionTemplate().insert(sqlId, t);
	}

	/**
	 * 更新实体。
	 * 
	 * @param t T
	 */
	public void update(T t) {
		String sqlId = this.getStatementId("update");
		this.getSqlSessionTemplate().update(sqlId, t);
	}

	/**
	 * 更新实体，允许实体的某个属性为NULL。
	 * 
	 * @param t T
	 */
	public void updateAllowNull(T t) {
		String sqlId = this.getStatementId("updateAllowNull");
		this.getSqlSessionTemplate().update(sqlId, t);
	}

	/**
	 * 创建或更新实体。
	 * 
	 * @param t T
	 */
	public void createOrUpdate(T t) {
		if (StringUtils.hasText(t.getId())) {
			this.update(t);
		} else {
			this.create(t);
		}
	}

	/**
	 * 删除实体。
	 * 
	 * @param t T
	 * 
	 * @return int
	 */
	public int delete(T t) {
		return this.delete(t.getId());
	}

	/**
	 * 根据实体类的id号删除实体。
	 * 
	 * @param id String
	 * 
	 * @return int
	 */
	public int delete(String id) {
		if (!StringUtils.hasText(id)) {
			throw new IllegalArgumentException("删除实体时需要提供ID");
		}
		String sqlId = this.getStatementId("deleteById");
		return this.getSqlSessionTemplate().delete(sqlId, id);
	}

	/**
	 * 根据所提供的PMap查询条件，删除符合条件的实体。
	 * 
	 * @param pm PMap
	 * 
	 * @return int
	 */
	public int deleteBy(PMap pm) {
		if (pm == null || pm.isEmpty()) {
			throw new IllegalArgumentException("条件删除时至少需要提供一个条件");
		}
		String statementId = this.getStatementId("deleteByMap");
		Map<String, Object> map = this.prepareParameter(pm);
		return this.getSqlSessionTemplate().delete(statementId, map);
	}

	/**
	 * 更具所提供的一个查询条件，删除符合条件的实体。
	 * 
	 * @param property String
	 * 
	 * @param value Object
	 * 
	 * @return int
	 */
	public int deleteBy(String property, Object value) {
		return this.deleteBy(this.newMapInstance(property, value));
	}

	/**
	 * 根据实体类的列表批量删除实体类。
	 * 
	 * @param list List<T>
	 * 
	 * @return int
	 */
	public int deleteAll(List<T> list) {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		Map<Class<T>, List<T>> map = new HashMap<Class<T>, List<T>>();
		for (T vo : list) {
			List<T> set = map.get(vo.getClass());
			if (set == null) {
				set = new ArrayList<T>();
			}
			set.add(vo);
		}
		int ret = 0;
		for (Map.Entry<Class<T>, List<T>> ent : map.entrySet()) {
			ret = ret + this.deleteByIds(VOUtils.extractIds(ent.getValue()));
		}
		return ret;
	}

	/**
	 * 根据实体类id的列表批量删除实体类。
	 * 
	 * @param ids List<String>
	 * 
	 * @return int
	 */
	public int deleteByIds(List<String> ids) {
		return this.deleteBy("id", ids);
	}

	// Find接口集=========================================================================
	/**
	 * 根据id查找实体类。
	 * 
	 * @param id String
	 * 
	 * @return T
	 */
	public T find(String id) {
		String sqlId = this.getStatementId("selectById");
		return (T) this.getSqlSessionTemplate().selectOne(sqlId, id);
	}

	/**
	 * 根据实体类ids列表查找符合条件的实体类。
	 * 
	 * @param ids List<String>
	 * 
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> findByIds(List<String> ids, Order... orders) {
		return this.findBy("id", ids, orders);
	}

	/**
	 * 根据条件查找实体类的列表。
	 * 
	 * @param property String
	 * 
	 * @param value Object
	 * 
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> findBy(String property, Object value, Order... orders) {
		return this.findBy(this.newMapInstance(property, value), orders);
	}

	/**
	 * 根据条件分页查找实体类的列表。
	 * 
	 * @param property String
	 * 
	 * @param value Object
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> findBy(String property, Object value, int pageIndex, int pageSize, Order... orders) {
		PMap map = this.newMapInstance(property, value);
		return this.findBy(map, pageIndex, pageSize, orders);
	}

	/**
	 * 根据查询条件PMap查询实体类信息并作排序。
	 * 
	 * @param pm PMap
	 * 
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> findBy(PMap pm, Order... orders) {
		return this.findBy(pm, -1, -1, orders);
	}

	/**
	 * 根据查询条件PMap分页查询实体类信息并作排序。
	 * 
	 * @param pm PMap
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> findBy(PMap pm, int pageIndex, int pageSize, Order... orders) {
		String sqlId = this.getStatementId("selectByMap");
		Map<String, Object> map = this.prepareParameter(pm, orders);
		if (pageIndex == -1 && pageSize == -1) {
			return this.getSqlSessionTemplate().selectList(sqlId, map);
		} else {
			RowBounds rb = this.getRowBounds(pageIndex, pageSize);
			return this.getSqlSessionTemplate().selectList(sqlId, map, rb);
		}
	}

	/**
	 * 根据查询条件查询某个实体类，并作排序。
	 * 
	 * @param name String
	 * 
	 * @param value Object
	 * 
	 * @param orders Order
	 * 
	 * @return T
	 */
	public T findFirstBy(String name, Object value, Order... orders) {
		List<T> list = this.findBy(name, value, orders);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据PMap查询条件查询某个实体类，并作排序。
	 * 
	 * @param pm PMap
	 * 
	 * @param orders Order
	 * 
	 * @return T
	 */
	public T findFirstBy(PMap pm, Order... orders) {
		List<T> list = this.findBy(pm, orders);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// List接口集
	// =============================================================================
	/**
	 * 列出某个实体类在数据库中的所有记录。
	 * 
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> list(Order... orders) {
		return this.findBy(this.newMapInstance(), orders);
	}

	/**
	 * 分页列出某个实体类在数据库中的所有记录。
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> list(int pageIndex, int pageSize, Order... orders) {
		PMap map = this.newMapInstance();
		return this.findBy(map, pageIndex, pageSize, orders);
	}

	// Count接口集
	// =============================================================================
	/**
	 * 统计数目。
	 * 
	 * @return long
	 */
	public long count() {
		return this.count(this.newMapInstance());
	}

	/**
	 * 根据PMap条件统计实体类在数据库中的记录数目。
	 * 
	 * @param pm PMap
	 * 
	 * @return long
	 */
	public long count(PMap pm) {
		String sqlId = this.getStatementId("countByMap");
		Map<String, Object> map = this.prepareParameter(pm);
		return (Long) this.getSqlSessionTemplate().selectOne(sqlId, map);
	}

	/**
	 * 根据条件统计实体类在数据库中的记录数目。
	 * 
	 * @param property String
	 * 
	 * @param value Object
	 * 
	 * @return long
	 */
	public long count(String property, Object value) {
		return this.count(this.newMapInstance(property, value));
	}

	// 分页接口集
	// =============================================================================
	/**
	 * 根据查询条件，分页查找实体类信息，并作排序操作。
	 * 
	 * @param property String
	 * 
	 * @param value Object
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param orders Order
	 * 
	 * @return PagedResult<T>
	 */
	public PagedResult<T> pagedQuery(String property, Object value, int pageIndex, int pageSize, Order... orders) {
		PMap map = this.newMapInstance(property, value);
		return this.pagedQuery(map, pageIndex, pageSize, orders);
	}

	/**
	 * 根据PMap查询条件，分页查找实体类信息，并作排序操作。
	 * 
	 * @param pm PMap
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @param orders Order
	 * 
	 * @return PagedResult<T>
	 */
	public PagedResult<T> pagedQuery(PMap pm, int pageIndex, int pageSize, Order... orders) {
		RowBounds rb = this.getRowBounds(pageIndex, pageSize);
		String statement = this.getStatementId("selectByMap");
		Map<String, Object> map = this.prepareParameter(pm, orders);
		List<T> list = this.getSqlSessionTemplate().selectList(statement, map, rb);
		String statementCount = this.getStatementId("countByMap");
		if (!this.getSqlSessionTemplate().getConfiguration().hasStatement(statementCount)) {
			throw new IllegalArgumentException("请提供 " + statementCount + " 用于分页查询时统计");
		}
		Long total = (Long) this.getSqlSessionTemplate().selectOne(statementCount, map);
		return new PagedResult<T>(list, total, pageIndex, pageSize);
	}

	// 批量操作接口集===========================================================================
	/**
	 * 批量插入数据。
	 * 
	 * @param list List<T>
	 */
	public void batchCreate( List<T> list) {
		this.batchCreate(list, 1000);
	}

	/**
	 * 批量插入数据，指定刷新频率。
	 * 
	 * @param list List<T>
	 * 
	 * @param frequency int
	 */
	public void batchCreate(List<T> list, int frequency) {
		String statementId = this.getStatementId("insert");
		SqlSession session = this.openBatchSession();
		IKeyGen keyGen = this.getKeyGen();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			if (!StringUtils.hasText(t.getId())) {
				t.setId(keyGen.getUUIDKey());
			}
			session.insert(statementId, t);
			if (i>0&&(i % frequency) == 0) {
				session.flushStatements();
			}
		}
		session.flushStatements();
		session.close();
	}

	/**
	 * 批量更新数据。
	 * 
	 * @param list List<T>
	 */
	public void batchUpdate(List<T> list) {
		this.batchUpdate( list, 1000);
	}

	/**
	 * 批量更新数据，并指定刷新频率。
	 * 
	 * @param list List<T>
	 * 
	 * @param frequency int
	 */
	public void batchUpdate(List<T> list, int frequency) {
		String statementId = this.getStatementId("update");
		SqlSession session = this.openBatchSession();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			session.update(statementId, t);
			if (i>0&&(i % frequency) == 0) {
				session.flushStatements();
			}
		}
		session.flushStatements();
	}
	
	//====================================================================================
	/**
	 * 检查是否存在。
	 * 
	 * @param t T
	 * 
	 * @return boolean
	 */
	public boolean checkExist(T t){
		String statementId = this.getStatementId("checkExist");
		Long  count = (Long) this.getSqlSessionTemplate().selectOne(statementId, t);
		return count>0;
	}
	
	/**
	 * 检查是否存在。
	 * 
	 * @param prop String
	 * 
	 * @param value Object
	 * 
	 * @param filterId String
	 * 
	 * @return boolean
	 */
	public boolean checkExist(String prop,Object value,String filterId){
		String statementId = this.getStatementId("checkExist");
		PMap pm = this.newMapInstance(prop, value);
		if(filterId!=null){
			pm.ne("id", filterId);	
		}
		Map<String, Object> map = this.prepareParameter(pm);
		Long  count = (Long) this.getSqlSessionTemplate().selectOne(statementId, map);
		return count>0;
	}
	
	/**
	 * 检查是否存在。
	 * 
	 * @param prop String
	 * 
	 * @param value Object
	 * 
	 * @return boolean
	 */
	public boolean checkExist(String prop,Object value){
		return this.checkExist(prop, value, null);
	}
	
	//--------------------------------------------------------------------------------------------------
	/**
	 * 实例化。
	 * 
	 * @return PMap
	 */
	public PMap newMapInstance() {
		return newMapInstance(null, null);
	}

	/**
	 * 实例化。
	 * 
	 * @param key String
	 * 
	 * @param value Object
	 * 
	 * @return PMap
	 */
	public PMap newMapInstance(String key, Object value) {
		PMap pm = new PMap();
		if (key != null && value != null) {
			pm.add(key, value);
		}
		return pm;
	}

	/**
	 * 分页。
	 * 
	 * @param pageIndex int
	 * 
	 * @param pageSize int
	 * 
	 * @return RowBounds
	 */
	public RowBounds getRowBounds(int pageIndex, int pageSize) {
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		int offset = (pageIndex - 1) * pageSize;
		RowBounds rb = new RowBounds(offset, pageSize);
		return rb;
	}
	// --------------------------------------------------------------------------------------------------
	
	
	
	
	protected String getStatementId(String name) {
		return this.entryClazz.getName() + "." + name;
	}

	protected Map<String, Object> prepareParameter(PMap pm, Order... orders) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<InModel> ins = new ArrayList<InModel>();
		List<Param> params = new ArrayList<Param>();
		Map<String, String> prop2Column = AutoConfigBuilder.resolveModel().get(this.entryClazz.getName());
		for (Param p : pm.list()) {
			if (p.isLogicalCalculus()) {
				params.add(p);
				continue;
			}
			Operator op = p.getOperator();
			String property = p.getProperty();
			String column = prop2Column.get(property);
			if (!StringUtils.hasText(column)) {
				throw new IllegalArgumentException("未能找到与(" + property + ")属性相对应的列");
			}
			p.setColumn(column);
			if (op == Operator.IN || op == Operator.NOT_IN) {
				boolean notIn = (op == Operator.NOT_IN);
				ins.add(new InModel(column, p.getValue(), notIn));
			} else {
				params.add(p);
			}
		}
		map.put("params", params);
		if (!ins.isEmpty()) {
			map.put("ins", ins);
		}

		if (orders != null && orders.length > 0) {
			for (Order order : orders) {
				if (!StringUtils.hasText(order.getColumn())) {
					String column = prop2Column.get(order.getProperty());
					order.setColumn(column);
				}
			}
			map.put("orderbys", orders);
		}
		return map;
	}

	private SqlSession openBatchSession() {
		SqlSessionFactory factory = this.getSqlSessionTemplate().getSqlSessionFactory();
		// 解决了3.1.1版mybatis不支持隐式传入 datasource问题
		// Connection connection = this.getSqlSessionTemplate().getConnection();
		SqlSession session = factory.openSession(ExecutorType.BATCH, false);
		return session;
	}
	
	protected IKeyGen getKeyGen() {
		return keyGen;
	}

	protected SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}


}
