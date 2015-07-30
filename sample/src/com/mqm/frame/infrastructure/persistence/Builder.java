/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.builder.xml.dynamic.IfSqlNode;
import org.apache.ibatis.builder.xml.dynamic.MixedSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SqlNode;
import org.apache.ibatis.builder.xml.dynamic.TextSqlNode;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.ReflectionUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class Builder {
	
	private static Map<Class<?>,String> jdbcTypeMap = new HashMap<Class<?>, String>();
	static{
		jdbcTypeMap.put(String.class, "VARCHAR");
		jdbcTypeMap.put(Date.class, "DATE");
		jdbcTypeMap.put(Integer.class, "NUMERIC");
		jdbcTypeMap.put(int.class, "NUMERIC");
		jdbcTypeMap.put(Long.class, "NUMERIC");
		jdbcTypeMap.put(long.class, "NUMERIC");
	}
	
	protected IfSqlNode getInNode(Configuration config) {
		ArrayList<SqlNode> nn = new ArrayList<SqlNode>();
		nn.add(new TextSqlNode("${item.property} ${item.inFlag} "));
		nn.add(new ForEachSqlNode(config, new TextSqlNode("'${itemx}'"), "item.values", "indexx", "itemx", "(", ")", ","));
		MixedSqlNode m = new MixedSqlNode(nn);
		ForEachSqlNode fe1 = new ForEachSqlNode(config, m, "ins", "index", "item", " and ", "", " and ");
		
		IfSqlNode inNode = new IfSqlNode(fe1, "ins!=null");
		return inNode;
	}

	  protected static List<Method> getGetterMethods(Class<?> cls) {  
	    	List<Method> ms = new ArrayList<Method>();
	        Method[] methods = cls.getMethods();
	        for (Method method : methods) {  
	          String name = method.getName();  
	          if (name.startsWith("get") && name.length() > 3) {  
	            if (method.getParameterTypes().length == 0) {  
	              ms.add(method); 
	            }  
	          } else if (name.startsWith("is") && name.length() > 2) {  
	            if (method.getParameterTypes().length == 0) {  
	              ms.add(method); 
	            }  
	          }  
	        }  
	        return ms;
	      } 
	  
	  protected String findJdbcType(JdbcType jdbcType,Class<?> cls){
		  String str = null;
		  if(jdbcType!=null){
			  str = jdbcType.name();
		  }else{
			  str = jdbcTypeMap.get(cls);
		  }
		  if(str!=null){
			  return ",jdbcType="+str;
		  }
		  return "";
	  }
	  
	  //TODO luxiaocheng 该方法抄用Mybatis类中的方法，但由于private的原因，部分使用反射，可能影响效率,重构之
	  protected List<SqlNode> parseDynamicTags(XNode node,Map<?,?> nodeHandlers) {
		    List<SqlNode> contents = new ArrayList<SqlNode>();
		    NodeList children = node.getNode().getChildNodes();
		    for (int i = 0; i < children.getLength(); i++) {
		      XNode child = node.newXNode(children.item(i));
		      String nodeName = child.getNode().getNodeName();
		      if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE
		          || child.getNode().getNodeType() == Node.TEXT_NODE) {
		        String data = child.getStringBody("");
		        contents.add(new TextSqlNode(data));
		      } else {
		        Object handler = nodeHandlers.get(nodeName);
		        if (handler == null) {
		          throw new BuilderException("Unknown element <" + nodeName + "> in SQL statement.");
		        }
		        Method method = ReflectionUtils.findMethod(handler.getClass(), "handleNode", XNode.class,List.class);
		        method.setAccessible(true);
		        ReflectionUtils.invokeMethod(method,handler,child,contents);

		      }
		    }
		    return contents;
		  }
	  
		protected List<SqlNode> findIncludeNode(Configuration config, String sqlId) {
			XNode node = config.getSqlFragments().get(sqlId);
			XMLStatementBuilder tmp = new XMLStatementBuilder(config, null, null);
			Field field = ReflectionUtils.findField(tmp.getClass(), "nodeHandlers");
			field.setAccessible(true);
			Map<?,?> nodeHandlers = (Map<?,?>)ReflectionUtils.getField(field, tmp);
			List<SqlNode> nodes = parseDynamicTags(node, nodeHandlers);
			return nodes;
		}

		protected void preparedSqlFragment(Configuration config) {
			StringBuffer sb = new StringBuffer();
			sb.append("<sql id=\"").append(Constants.COMMON_SELECT_CONDITION).append("\">");
			sb.append("<foreach item=\"item\" collection=\"params\">");
			sb.append(" ${item.logic} ${item.columnx} ${item.operator} <if test=\"item.valuex!=null\">#{item.valuex}</if>");
			sb.append("</foreach>");
			sb.append("</sql>");
			XPathParser parser = new XPathParser(sb.toString());
			XNode node = parser.evalNode("/sql");
			config.getSqlFragments().put(Constants.COMMON_SELECT_CONDITION, node);
		}


}
