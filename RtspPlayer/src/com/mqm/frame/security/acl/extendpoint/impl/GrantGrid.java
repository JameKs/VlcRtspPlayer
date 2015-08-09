/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.util.FieldUtils;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.security.acl.extendpoint.IGrantExtendPoint;
import com.mqm.frame.security.acl.extendpoint.IGrantShowShape;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * <pre>
 * 授权页面的列表的展现形式。
 * </pre>
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class GrantGrid  implements IGrantShowShape {

	private static final Log log = LogFactory.getLog(GrantGrid.class);

	private IGrantExtendPoint grantExtendPoint;

	private List<ValueObject> allowervalues;

	private List<Map<String, Object>> resultlistMap = new ArrayList<Map<String, Object>>();

	private Map<String, String> resultvalueMap;

	/**
	 * 构建列表。
	 */
	public List<String> columnNames=new ArrayList<String>();

	private List principalids;

	private Class<? extends ValueObject> clazz;
	
	@Override
	public Class<? extends ValueObject> getModelClass(){
		return this.clazz;
	}
	
	/**
	 * 自定义构造器。
	 * 
	 * @param clazz Class<? extends ValueObject>
	 */
	public GrantGrid(Class<? extends ValueObject> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public void setGrantExtendpoint(IGrantExtendPoint extendPoint,boolean isEdit) {
		this.grantExtendPoint = extendPoint;
		if(isEdit){
			columnNames.clear();
			columnNames.addAll(getResultValueMap().keySet());
			this.allowervalues = this.grantExtendPoint.getAll();
			resultlistMap.clear();
			for (int i = 0; i < allowervalues.size(); i++) {
				Map map = new HashMap();
				Class clazz = allowervalues.get(i).getClass();
				for (String key : this.getResultValueMap().keySet()) {
					Field field = FieldUtils.getField(clazz, key);
					map.put(key, getFieldValue(field, allowervalues.get(i)));
				}
				Field field = FieldUtils.getField(clazz, "id");
				map.put("id", getFieldValue(field, allowervalues.get(i)));
				field = FieldUtils.getField(clazz, "checked");
				map.put("checked", getFieldValue(field, allowervalues.get(i)));
				resultlistMap.add(map);
			}
		
		}
	}
	@Override
	public void init(List principalids,boolean isEdit) {
		this.principalids = principalids;
		if(isEdit){
			List list = this.grantExtendPoint.getAllByPrincipals(principalids); //获取已经选择上的
			for (Map map : resultlistMap) {
				if (isContain(map.get("id"), list) != -1) {
					map.put("checked", true);
				} else {
					map.put("checked", false);
				}
			}
		
		}
		else{
			columnNames.clear();
			columnNames.addAll(getResultValueMap().keySet());
			List list = this.grantExtendPoint.getAllByPrincipals(principalids);
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				Class clazz = list.get(i).getClass();
				for (String key : this.getResultValueMap().keySet()) {
					Field field = FieldUtils.getField(clazz, key);
					map.put(key, getFieldValue(field, list.get(i)));
				}
				resultlistMap.add(map);
			}
		}
	}

	private int isContain(Object id, List<ValueObject> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(id)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void updateGranted() {
		if (principalids == null || principalids.size() < 1) {
			//this.exceptionMsg.setMainMsg("请选择一个授权主体!");
			return;
		}
		List<ValueObject> insertlist = new ArrayList<ValueObject>();
		for (Map map : this.resultlistMap) {
			boolean check = (Boolean) map.get("checked");
			if (check) {
				String id = (String) map.get("id");
				int index = isContain(id, allowervalues);
				insertlist.add(allowervalues.get(index));
			}
		}
		this.grantExtendPoint.updateGranted(insertlist, this.grantExtendPoint.getAllByPrincipals(principalids),
				(String) principalids.get(0));
	//	this.exceptionMsg.setMainMsg("授权成功!");
	}
	
	@Override
	public int updateGranted(String roleId,String[] ids) {
		if (roleId == null || "".equals(roleId)) {
			//this.exceptionMsg.setMainMsg("请选择一个授权主体!");
			return 0;
		}

		List<ValueObject> selValues = new ArrayList<ValueObject>();
		try {
			for (String id : ids) {
				ValueObject instance = this.clazz.newInstance();
				instance.setId(id);
				selValues.add(instance);
			}
		} catch (InstantiationException e) {
			log.error("", e);
		} catch (IllegalAccessException e) {
			log.error("", e);
		}
		this.grantExtendPoint.updateGranted(selValues, this.grantExtendPoint.getAllByPrincipals(Arrays.asList(roleId)),roleId);
	   return 1;
	}

	/**
	 * 获取FieldValue。
	 * 
	 * @param field  Field
	 * @param object Object
	 * @return Object
	 */
	public Object getFieldValue(Field field, Object object) {
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	@Override
	public String getType() {
		return BaseConstants.GRANT_TYPE_GRID;
	}

    /**
     * 获取allowervalues。
     * 
     * @return  List<ValueObject>
     */
	public List<ValueObject> getAllowervalues() {
		return allowervalues;
	}

	/**
	 * 获取resultvalueMap。
	 * 
	 * @return  Map<String, String>
	 */
	public Map<String, String> getResultValueMap() {
		this.resultvalueMap = this.grantExtendPoint.getResultValueMap();
		return resultvalueMap;
	}

	/**
	 * 获取resultlistMap。
	 * 
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getResultListMap() {
		return resultlistMap;
	}

	/**
	 * 获取columnNames。
	 * 
	 * @return List<String>
	 */
	public List<String> getColumnNames() {
		return columnNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mqm.frame.security.acl.extendpoint.IGrantShowShape#
	 * getGrantExtendPoint()
	 */
	@Override
	public IGrantExtendPoint getGrantExtendPoint() {
		return this.grantExtendPoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mqm.frame.infrastructure.web.CommonBackingBean#reset()
	 */
	@Override
	public void reset() {
		if(resultlistMap!=null){
			for (Map map : resultlistMap) {
				map.put("checked", false);
			}
		}
	}
	@Override
	public Object[] getResult() {
		// TODO Auto-generated method stub
		
		List<String> title = new ArrayList<String>();
		List<String> tdText = new ArrayList<String>();
		for(String str:columnNames){
			title.add(resultvalueMap.get(str));
			for(Map<String,Object> map:resultlistMap){
				tdText.add((String)map.get(str));
			}
		}
		Object[] obj =  {columnNames,resultlistMap,resultvalueMap};
		return obj;
	}
}
