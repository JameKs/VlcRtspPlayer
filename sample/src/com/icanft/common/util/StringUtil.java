/**
 * StringUtil.java
 * 2015
 * 2015年5月19日
 */
package com.icanft.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.icanft.cdgl.vo.Cdxx;
import com.icanft.cdgl.vo.Dept;
import com.icanft.cdgl.vo.Gw;
import com.icanft.jssq.vo.CdxxRole;
import com.icanft.xtgl.yhgl.vo.User;

/**
 * <pre>
 * 字符串处理类
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月19日
 */
public class StringUtil {
	/**
	 * 将获取的list数据转换成符合分页数据源格式的json串 items为extjs store proxy
	 * reader中设置的root，填充表格的数据；totalCount为数据总数。
	 * 
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static String pageListToJson(List<Object> list, long count) {

		JsonObject object = new JsonObject();
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(list);
		// items为extjs store proxy reader中设置的root，即为数据源；totalCount为数据总数。
		object.add("items", jsonElement);
		object.addProperty("totalCount", count);
		return object.toString();
	}
	
	/**
	 * @param list
	 * @param totalSize
	 * @return
	 */
	public static String pageListToJsonPD(List<Map<String, Object>> list,
			long totalSize) {
		JsonObject object = new JsonObject();
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(list);
		// items为extjs store proxy reader中设置的root，即为数据源；totalCount为数据总数。
		object.add("items", jsonElement);
		object.addProperty("totalCount", totalSize);
		return object.toString();
	}

	/**
	 * 将获取的list数据转换成符合分页数据源格式的json串 items为extjs store proxy
	 * reader中设置的root，填充表格的数据；totalCount为数据总数。
	 * 
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static String pageListToXml(List<Object> list, long count) {

		JsonObject object = new JsonObject();
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(list);
		// items为extjs store proxy reader中设置的root，即为数据源；totalCount为数据总数。
		object.add("items", jsonElement);
		object.addProperty("totalCount", count);
		return object.toString();
	}

	public static Map<String, Object> transStrToPanel(Cdxx cd) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("xtype", "panel");// 菜单显示名称
		obj.put("title", cd.getCnName());// 菜单显示名称
		obj.put("id", cd.getId());// 菜单
		return obj;
	}

	public static Map<String, Object> transStrToTree(Cdxx cd) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("text", cd.getCnName());// 菜单显示名称
		obj.put("leaf", "1".equals(cd.getLeaf()));// 是不是叶子节点
		obj.put("url", cd.getUrl());// 节点链接地址
		obj.put("ccsd", cd.getCcsd());// 菜单排序
		obj.put("ccsx", cd.getCcsx());// 菜单排序
		obj.put("cnName", cd.getCnName());// 菜单中文名
		obj.put("enName", cd.getEnName());// 菜单英文名
		obj.put("cdDm", cd.getCdDm());// 菜单代码
		obj.put("id", cd.getId());// 菜单id
		obj.put("pId", cd.getpId());// 父菜单id
		return obj;
	}
	
	public static Map<String, Object> transUserToTree(User user) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("text", user.getUserName());// 菜单显示名称
		obj.put("leaf", true);// 是不是叶子节点
		obj.put("id", user.getId());// 菜单id
		return obj;
	}
	
	public static Map<String, Object> transGwToTree(Gw gw) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("text", gw.getGwMc());// 菜单显示名称
		obj.put("leaf", false);// 是不是叶子节点
		obj.put("id", gw.getId());// 菜单id
		return obj;
	}
	
	public static Map<String, Object> transDeptToTree(Dept dept) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("text", dept.getDeptMc());// 菜单显示名称
		obj.put("id", dept.getId());// 菜单id
		obj.put("leaf", false);// 是不是叶子节点
		return obj;
	}
	
	public static Map<String, Object> transStrToCheckTree(CdxxRole cd) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("text", cd.getCnName());// 菜单显示名称
		obj.put("leaf", "1".equals(cd.getLeaf()));// 是不是叶子节点
		obj.put("id", cd.getId());// 菜单id
		obj.put("checked", StringUtils.isNotEmpty(cd.getRoleId()));// ID
		return obj;
	}

	public static String object2String(final List<Map<String, Object>> list) {
		JSONArray arry = JSONArray.fromObject(list);// 序列化
		return arry.toString();// 输出的即为JSON字符串
	}

	/**
	 * JSON字符串特殊字符处理，比如：“\A1;1300”
	 * 
	 * @param s
	 * @return String
	 */
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 我的收藏夹
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> serializeMenuPanel(List list) {
		List<Map<String, Object>> tempDate = new ArrayList<Map<String, Object>>();//作为树的载体
		Iterator<Cdxx> it = list.iterator();
		
		while (it.hasNext()) {// 当顶层菜单有数据的时候 依次循环
			Cdxx cd = it.next();
			// 首先生成本菜单数据
			Map<String, Object> obj = StringUtil.transStrToPanel(cd);

			// 将本菜单放入载体
			tempDate.add(obj);
		}
		return tempDate;
	}

}
