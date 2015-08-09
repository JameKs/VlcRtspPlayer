/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 
 * <pre>
 * 程序的中文名称。
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
public class PMap {
	private List<Param> params = new ArrayList<Param>();

	/**
	 * 增加Param。
	 * 
	 * @param p Param
	 */
	public void add(Param p) {
		this.params.add(p);
	}
	/**
	 * 根据key，value增加。
	 * 
	 * @param key  String
	 * @param value Object
	 */
	public void add(String key, Object value){
		if(value instanceof Collection<?>){
			this.in(key, (Collection<?>)value);
		}else if(value.getClass().isArray()){
			this.in(key, (Object[])value);
		}else{
			this.eq(key, value);
		}
	}

	private boolean check(Object value) {
		return value != null && !"".equals(value);
	}
	private boolean check(Collection<?> collection){
		return collection!=null && !collection.isEmpty();
	}
	private boolean check(Object[] objs){
		return objs!=null && objs.length>0;
	}
	
	/**
	 * 设置条件eq。
	 * 
	 * @param key String
	 * @param value Object
	 */
	public void eq(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, value));
		}else{
			this.isNull(key);
		}
	}

	/**
	 * 设置条件eqIgnoreCase。
	 * 
	 * @param key  String
	 * @param value Object
	 */
	public void eqIgnoreCase(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, value, true));
		}else{
			this.isNull(key);
		}
	}
	/**
	 * 设置条件notEquals。
	 * 
	 * @param key String
	 * @param value Object
	 */
	public void ne(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, Operator.NOT, value));
		}else{
			this.isNotNull(key);
		}
	}

	/**
	 * 设置条件notIgnoreCase。
	 * 
	 * @param key String
	 * @param value Object
	 */
	public void notIgnoreCase(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, value));
		}else{
			this.isNotNull(key);
		}
	}

	/**
	 * 设置条件小于。
	 * 
	 * @param key String
	 * @param value Object
	 */
	public void lt(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, Operator.LT, value));
		}
	}

   /**
    * 设置条件小于等于。
    * 
    * @param key String
    * @param value Object
    */
	public void lq(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, Operator.LQ, value));
		}
	}

	/**
	 * 设置条件大于。
	 * 
	 * @param key String
	 * @param value Object
	 */
	public void gt(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, Operator.GT, value));
		}
	}

	/**
	 * 设置条件大于等于。
	 * 
	 * @param key  String
	 * @param value Object
	 */
	public void gq(String key, Object value) {
		if (check(value)) {
			this.add(new Param(key, Operator.GQ, value));
		}
	}

	/**
	 * 设置条件like。
	 * 
	 * @param key String
	 * @param value String
	 */
	public void like(String key, String value) {
		if (check(value)) {
			this.add(new Param(key, Operator.LIKE, value));
		}
	}

	/**
	 * 设置条件likeIgnoreCase。
	 * 
	 * @param key String
	 * @param value String
	 */
	public void likeIgnoreCase(String key, String value) {
		if (check(value)) {
			this.add(new Param(key, Operator.LIKE, value, true));
		}
	}

	/**
	 * 设置条件include。
	 * 
	 * @param key String
	 * @param value String
	 */
	public void include(String key, String value) {
		if (check(value)) {
			this.add(new Param(key, Operator.LIKE, "%" + value + "%"));
		}
	}

	/**
	 * 设置条件includeIgnoreCase。
	 * 
	 * @param key String
	 * @param value String
	 */
	public void includeIgnoreCase(String key, String value) {
		if (check(value)) {
			this.add(new Param(key, Operator.LIKE, "%" + value + "%", true));
		}
	}

	/**
	 * 设置条件in。
	 * 
	 * @param key String
	 * @param list Collection<?>
	 */
	public void in(String key, Collection<?> list) {
		if (check(list)) {
			this.add(new Param(key, Operator.IN, list));
		}
	}

	/**
	 * 设置条件in。
	 * 
	 * @param key String
	 * @param objs Object[]
	 */
	public void in(String key, Object[] objs) {
		if (check(objs)) {
			this.add(new Param(key, Operator.IN, objs));
		}
	}

	/**
	 * 设置条件notIn。
	 * 
	 * @param key String
	 * @param list Collection<?>
	 */
	public void notIn(String key, Collection<?> list) {
		if (check(list)) {
			this.add(new Param(key, Operator.NOT_IN, list));
		}
	}

	/**
	 * 设置条件notIn。
	 * 
	 * @param key  String
	 * @param objs Object[]
	 */
	public void notIn(String key, Object[] objs) {
		if (check(objs)) {
			this.add(new Param(key, Operator.NOT_IN, objs));
		}
	}
	
	/**
	 * 设置条件range。
	 * 
	 * @param key  String
	 * @param start Object
	 * @param end Object
	 */
	public void range(String key,Object start,Object end){
		this.gq(key, start);
		this.lq(key, end);
	}
	
	/**
	 * 设置条件为空。
	 * 
	 * @param key String
	 */
	public void isNull(String key){
		this.add(new Param(key, Operator.IS_NULL,null));
	}
	/**
	 * 设置条件为非空。
	 * 
	 * @param key String
	 */
	public void isNotNull(String key){
		this.add(new Param(key, Operator.IS_NOT_NULL,null));
	}
	/**
	 * 判断是否为空的。
	 * 
	 * @return boolean
	 */
	public boolean isEmpty(){
		return this.params.isEmpty();
	}
	/**
	 * 列出所有数据。
	 * 
	 * @return List<Param>
	 */
	public List<Param> list(){
		return Collections.unmodifiableList(this.params);
	}
	/**
	 * 增加OrConditions。
	 * 
	 * @param p1  Param
	 * @param p2 Param
	 * @param ps Param
	 */
	public void addOrConditions(Param p1,Param p2,Param ...ps){
		this.add(new Param(Operator.L_BRACKET));
		p1.setLogic("");
		this.add(p1);
		p2.setLogic("or");
		this.add(p2);
		for (Param p : ps) {
			p.setLogic("or");
			this.add(p);
		}
		Param l = new Param(Operator.R_BRACKET);
		l.setLogic("");
		this.add(l);
	}
	
}
