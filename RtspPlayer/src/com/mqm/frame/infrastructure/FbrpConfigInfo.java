/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.util.StringUtil;

/**
 * <pre>
 * 存储fbrp.properties中声明的重要配置信息。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpConfigInfo extends ValueObject {

	private static final long serialVersionUID = -560731973144603858L;

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_infrastrucute_fbrpConfigInfo";
	
	private Map<String,String> map = new HashMap<String, String>();
	
	private int defaultPageSize;
	private List<Integer> pageSizeList;
	

	/**
	 * 设置客户的页面大小。
	 * 
	 * @param customPageSize String
	 */
	public void setCustomPageSize(String customPageSize){
		String defautCustome = "10,[20],50,100,200";
		try {
			if(StringUtil.isEmpty(customPageSize)){
				customPageSize = defautCustome;
			}
			String[] arr = customPageSize.split(",");
			if(arr.length==0){
				arr = defautCustome.split(",");
			}
			List<Integer> list = new ArrayList<Integer>();
			for (String size : arr) {
				size = size.trim();
				if(size.matches("\\d{1,5}")){
					list.add(Integer.parseInt(size));
				}else if(size.matches("\\[\\d{1,5}\\]")){
					size = size.substring(1,size.length()-1);
					int i = Integer.parseInt(size);
					list.add(i);
					this.defaultPageSize = i;
				}
			}
			this.pageSizeList = list;
			if(this.defaultPageSize==0){
				this.defaultPageSize = list.get(0);
			}
		} catch (Exception e) {
			this.defaultPageSize = 10;
			this.pageSizeList = new ArrayList<Integer>();
			this.pageSizeList.add(10);
			this.pageSizeList.add(20);
			this.pageSizeList.add(50);
			this.pageSizeList.add(100);
			this.pageSizeList.add(200);
		}
	}
	
	/**
	 * 获取DefaultPageSize。
	 * 
	 * @return int
	 */
	public int getDefaultPageSize(){
		return this.defaultPageSize;
	}
	
	/**
	 * 获取pageSizeList。
	 * 
	 * @return List<Integer>
	 */
	public List<Integer> getPageSizeList(){
		return this.pageSizeList;
	}


   /**
    * 根据key取出map中的value。
    * 
    * @param key String
    * 
    * @return String
    */
	public String get(String key){
		return map.get(key);
	}
	
	/**
	 * 维护map。
	 * 
	 * @param subMap Map<String,String>
	 */
	public void add(Map<String,String> subMap){
		if(subMap==null||subMap.isEmpty()){
			return;
		}
		for (Map.Entry<String, String> ent : subMap.entrySet()) {
			this.map.put(ent.getKey(), ent.getValue());
		}
	}
	
	/**
	 * 维护map。
	 * 
	 * @param key String 
	 * @param value String
	 */
	public void add(String key,String value){
		this.map.put(key, value);
	}

	/**
	 * 返回map。
	 * 
	 * @return Map<String, String> 
	 */
	public Map<String, String> getMap() {
		return Collections.unmodifiableMap(this.map);
	}
	
}
