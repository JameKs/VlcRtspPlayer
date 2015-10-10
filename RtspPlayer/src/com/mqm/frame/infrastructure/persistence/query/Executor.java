/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.StringUtils;

import com.mqm.frame.infrastructure.persistence.Operator;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.persistence.Param;
import com.mqm.frame.infrastructure.persistence.model.InModel;
import com.mqm.frame.infrastructure.util.PagedResult;

/**
 * 
 * <pre>
 * 联合查询执行器。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 * @param <T>
 */
public class Executor<T> {
	private String namespace;
	private SqlSessionTemplate sqlSessionTemplate;
	private Map<String, Map<String, String>> cModels;
	private Map<String, String> aliasMap;
	
	/**
	 * 自定义的构造方法。
	 * 
	 * @param namespace String
	 * @param sqlSessionTemplate SqlSessionTemplate
	 * @param cModels Map<String, Map<String, String>> 
	 * @param aliasMap Map<String, String> 
	 */
	public Executor(String namespace ,SqlSessionTemplate sqlSessionTemplate,Map<String, Map<String, String>> cModels,Map<String, String> aliasMap){
		this.namespace = namespace;
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.cModels = cModels;
		this.aliasMap = aliasMap;
	}
	
	/**
	 * 返回记录总数。
	 * 
	 * @return long
	 */
	public long count() {
		return this.count(new PMap());
	}

	/**
	 * 返回符合条件的记录总数。
	 * 
	 * @param pm PMap
	 * 
	 * @return long
	 */
	public long count(PMap pm) {
		String sqlId = this.getStatementId("countByMap");
		Map<String, Object> map = this.prepareParameter(pm);
		return (Long) this.getSession().selectOne(sqlId, map);
	}

	/**
	 * 返回符合条件的记录总数。
	 * 
	 * @param property String
	 * @param value Object
	 * 
	 * @return long
	 */
	public long count(String property, Object value) {
		PMap pm = new PMap();
		pm.eq(property, value);
		return this.count(pm);
	}
	
	/**
	 * 根据PMap条件分页查询数据，并可以作排序操作。
	 * 
	 * @param pm PMap
	 * @param pageIndex int
	 * @param pageSize int
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(PMap pm,int pageIndex,int pageSize,Order ...orders){
		String sqlId = this.getStatementId("selectByMap");
		Map<String, Object> map = this.prepareParameter(pm, orders);
		if(pageIndex==-1&&pageSize==-1){
			return (List<T>)this.getSession().selectList(sqlId,map);
		}else{
			RowBounds rb = this.getRowBounds(pageIndex, pageSize);
			return (List<T>)this.getSession().selectList(sqlId,map,rb);
		}
	}
	
	/**
	 * 根据条件查询数据，并可作排序。
	 * 
	 * @param property String
	 * @param value Object
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> findBy(String property ,Object value ,Order ...orders){
		return this.findBy(this.newMapInstance(property, value), orders);
	}
	
	/**
	 * 根据条件分页查找数据，并可作排序。
	 * 
	 * @param property String
	 * @param value Object
	 * @param pageIndex int
	 * @param pageSize int
	 * @param orders Order
	 * 
	 * @return List<T>
	 */
	public List<T> findBy(String property ,Object value ,int pageIndex,int pageSize,Order ...orders){
		PMap map = this.newMapInstance(property, value);
		return this.findBy(map,pageIndex,pageSize, orders);
	}
	
	/**
	 * 根据PMap条件查询数据。
	 * 
	 * @param pm PMap
	 * @param orders Order
	 * 
	 * @return   List<T>
	 */
	public List<T> findBy(PMap pm,Order ...orders){
		return this.findBy(pm, -1, -1, orders);
	}
	
	/**
	 * 根据条件查找一个实体。
	 * 
	 * @param name String
	 * @param value Object
	 * @param orders Order
	 * 
	 * @return T
	 */
	public T findFirstBy(String name, Object value,Order ...orders){
		List<T> list = this.findBy(name, value,orders);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据PMap条件返回一个实体。
	 * 
	 * @param pm PMap
	 * @param orders Order
	 * 
	 * @return T 
	 */
	public T findFirstBy(PMap pm,Order ...orders){
		List<T> list = this.findBy(pm,orders);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	private String getStatementId(String key) {
		return this.namespace+"."+key;
	}
	
	/**
	 * 列出所有数据。
	 * 
	 * @param orders Order
	 * 
	 * @return  List<T>
	 */
	public List<T> list(Order... orders) {
		return this.findBy(this.newMapInstance(), orders);
	}

	/**
	 * 分页列出所有的数据。
	 * 
	 * @param pageIndex int
	 * @param pageSize int
	 * @param orders Order
	 * 
	 * @return List<T> 
	 */
	public List<T> list(int pageIndex, int pageSize, Order... orders) {
		PMap map = this.newMapInstance();
		return this.findBy(map, pageIndex, pageSize, orders);
	}
	
	/**
	 * 分页查询。
	 * 
	 * @param property String
	 * @param value Object
	 * @param pageIndex int
	 * @param pageSize int
	 * @param orders Order
	 * @return PagedResult<T>
	 */
	public PagedResult<T> pagedQuery(String property, Object value, int pageIndex, int pageSize,
			Order... orders) {
		PMap map = this.newMapInstance(property, value);
		return this.pagedQuery(map, pageIndex, pageSize, orders);
	}
	
	/**
	 * 根据PMap条件返回查询结果的包装PagedResult。
	 * 
	 * @param pm PMap
	 * @param pageIndex int
	 * @param pageSize int
	 * @param orders Order
	 * 
	 * @return PagedResult<T>
	 */
	@SuppressWarnings("unchecked")
	public PagedResult<T> pagedQuery(PMap pm, int pageIndex, int pageSize, Order ...orders){
		int offset = (pageIndex-1)*pageSize;
		RowBounds rb = new RowBounds(offset, pageSize);
		String statement = this.getStatementId("selectByMap");
		Map<String, Object> map = this.prepareParameter(pm, orders);
		List<T> list = (List<T>)this.getSession().selectList(statement,map,rb);
				
		String statement2 = this.getStatementId("countByMap");
		if(!this.getSession().getConfiguration().hasStatement(statement2)){
			throw new IllegalArgumentException("请提供 "+statement2+" 用于分页查询时统计");
		}
		Long total = (Long)this.getSession().selectOne(statement2,map);
		return new PagedResult<T>(list, total,pageIndex,pageSize);
	}
	
	private SqlSessionTemplate getSession() {
		return sqlSessionTemplate;
	}
	
	private Map<String, Object> prepareParameter(PMap pm, Order... orders) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<InModel> ins = new ArrayList<InModel>();
		List<Param> params = new ArrayList<Param>(); 
		for (Param p : pm.list()) {
			Operator op = p.getOperator();
			String property = p.getProperty();
			String column = this.toColumn(p.getAlias(),p.getProperty());
			p.setColumn(column);
			if(!StringUtils.hasText(column)){
				throw new IllegalArgumentException("未能找到与("+property+")属性相对应的列");
			}
			if(op==Operator.IN||op==Operator.NOT_IN){
				boolean notIn = (op==Operator.NOT_IN);
				ins.add(new InModel(column, p.getValue(),notIn));
			}else{
				params.add(p);
			}
		}
		map.put("params", params);
		if(!ins.isEmpty()){
			map.put("ins", ins);
		}
		
		if(orders!=null&&orders.length>0){
			for (Order order : orders) {
				if(!StringUtils.hasLength(order.getColumn())){
					String prop = order.getProperty();
					String[] arr = prop.split("[\\.]");
					String col = this.toColumn(arr[0],arr[1]);
					order.setColumn(col);
				}
			}
			map.put("orderbys", orders);
		}
		return map;
	}

	private String toColumn(String alias,String propName) {
		String className = this.aliasMap.get(alias);
		Map<String, String> mm = this.cModels.get(className);
		return mm.get(propName);
	}
	protected PMap newMapInstance(){
		return this.newMapInstance(null,null);
	}
	
	protected PMap newMapInstance(String key,Object value){
		PMap pm = new PMap();
		if(key!=null&&value!=null){
			pm.add(key, value);
		}
		return pm;
	}

	private RowBounds getRowBounds(int pageIndex, int pageSize) {
		if(pageIndex<=0){
			pageIndex = 1;
		}
		int offset = (pageIndex-1)*pageSize;
		RowBounds rb = new RowBounds(offset, pageSize);
		return rb;
	}
}
