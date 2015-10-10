/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.query;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.apache.ibatis.builder.xml.dynamic.DynamicSqlSource;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.builder.xml.dynamic.IfSqlNode;
import org.apache.ibatis.builder.xml.dynamic.MixedSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SqlNode;
import org.apache.ibatis.builder.xml.dynamic.TextSqlNode;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.StringUtils;

import com.mqm.frame.infrastructure.persistence.Builder;
import com.mqm.frame.infrastructure.persistence.Constants;
import com.mqm.frame.infrastructure.persistence.factory.AutoConfigBuilder;
import com.mqm.frame.infrastructure.persistence.model.ColumnModel;
import com.mqm.frame.infrastructure.persistence.model.TableModel;
import com.mqm.frame.infrastructure.persistence.strategy.FbrpNamingStrategy;
import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 关联查询构造器。
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
public class QueryBuilder extends Builder {
	private SqlSessionTemplate sqlSessionTemplate;
	private Map<Class<?>, String> models = new LinkedHashMap<Class<?>, String>();
	private String when;
	private String aProperty;
	private static Set<String> set = new HashSet<String>();

	/**
	 * 自定义的构造方法。
	 * 
	 * @param sqlSessionTemplate
	 *            SqlSessionTemplate
	 */
	public QueryBuilder(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	/**
	 * 增加模型。
	 * 
	 * @param cls
	 *            Class<?>
	 * @param alias
	 *            String
	 * 
	 * @return QueryBuilder
	 */
	public QueryBuilder addModel(Class<?> cls, String alias) {
		Entity annoEntity = cls.getAnnotation(Entity.class);
		if (annoEntity == null) {
			throw new FbrpException("仅支持Entity对象，实体类必须添加@Entity注解");
		}
		if (!StringUtils.hasText(alias) || !alias.matches("[\\w][\\w|\\d|_]*")) {
			throw new IllegalArgumentException("别名不合法");
		}
		alias = InternationalizationUtil.toLowerCase(alias);
		if (models.size() > 3) {
			throw new FbrpException("NQuery不支持超过3个表的联合查询，复杂查询请使用MyBatis映射文件");
		}
		if (models.containsValue(alias)) {
			throw new IllegalArgumentException("已包含别名：" + alias);
		}
		models.put(cls, alias);
		return this;
	}

	/**
	 * 增加model。
	 * 
	 * @param cls
	 *            Class<?>
	 * 
	 * @return QueryBuilder
	 */
	public QueryBuilder addModel(Class<?> cls) {
		String alias = "t" + (models.size() + 1);
		return this.addModel(cls, alias);
	}

	/**
	 * 连接条件where。
	 * 
	 * @param when
	 *            String
	 * @return QueryBuilder
	 */
	public QueryBuilder where(String when) {
		this.when = when;
		return this;
	}

	/**
	 * 设置关联的属性。
	 * 
	 * @param property
	 *            String
	 */
	public void fetchAssociation(String property) {
		this.aProperty = property;
	}

	/**
	 * 指明查询哪张表。
	 * 
	 * @param cls
	 *            Class<T>
	 * @param <T>
	 *            ValueObject
	 * 
	 * @return <T>
	 */
	public <T> Executor<T> builtFor(Class<T> cls) {
		StringBuffer nsSb = new StringBuffer();
		for (Map.Entry<Class<?>, String> ent : this.models.entrySet()) {
			nsSb.append(ent.getKey().getName());
			nsSb.append("_");
			nsSb.append(ent.getValue());
			nsSb.append("_");
		}
		if (this.aProperty != null) {
			nsSb.append(aProperty);
		}
		nsSb.append(cls.getName());
		String namespace = nsSb.toString();
		Map<String, TableModel> mapping = AutoConfigBuilder.resolveMapping();
		Map<String, Map<String, String>> cModels = AutoConfigBuilder.resolveModel();
		Map<String, String> aliasMap = new HashMap<String, String>();
		for (Map.Entry<Class<?>, String> ent : this.models.entrySet()) {
			aliasMap.put(ent.getValue(), ent.getKey().getName());
		}

		if (set.contains(namespace)) {
			return new Executor<T>(namespace, this.getSession(), cModels, aliasMap);
		}

		Configuration config = this.getSession().getConfiguration();
		ResultMap rm = config.getResultMap("vo." + cls.getSimpleName());
		ArrayList<ResultMap> rms = new ArrayList<ResultMap>();
		rms.add(rm);

		Class<?> retCls = null;
		Method md = null;
		String a1 = "";
		if (this.aProperty != null) {

			List<Method> getterMethods = getGetterMethods(cls);
			for (Method method : getterMethods) {
				String name = PropertyNamer.methodToProperty(method.getName());
				if (this.aProperty.equals(name)) {
					retCls = method.getReturnType();
					md = method;
					break;
				}

			}
			if (retCls != null) {
				String rmKey = "vox." + cls.getSimpleName() + retCls.getSimpleName();
				if (!config.hasResultMap(rmKey)) {
					String aCol = "";
					if (md.getAnnotation(OneToOne.class) != null) {
						aCol = md.getAnnotation(OneToOne.class).mappedBy();
					} else {
						aCol = FbrpNamingStrategy.INSTANCE.foreignKeyColumnName(aProperty);
					}
					String subKey = "vox." + retCls.getSimpleName() + "_x2";
					this.builtResultMap2(mapping, config, retCls, subKey);
					ResultMapping.Builder builder = new ResultMapping.Builder(config, this.aProperty, aCol, retCls);
					builder.nestedResultMapId(subKey);
					ResultMapping rmx = builder.build();
					List<ResultMapping> rmOld = rm.getResultMappings();
					List<ResultMapping> dest = new ArrayList<ResultMapping>();
					for (Object obj : rmOld) {
						if (obj instanceof ResultMapping) {
							dest.add((ResultMapping) obj);
						}
					}
					dest.add(rmx);

					ResultMap.Builder b = new ResultMap.Builder(config, rmKey, cls, dest);
					ResultMap rm2 = b.build();
					rm2.forceNestedResultMaps();
					config.addResultMap(rm2);

					rms.clear();
					rms.add(rm2);

				} else {
					ResultMap rm2 = config.getResultMap(rmKey);
					rms.clear();
					rms.add(rm2);
				}

				String alias = models.get(retCls);
				List<ColumnModel> allCols = mapping.get(retCls.getName()).getAllCols();
				StringBuffer sb = new StringBuffer();
				for (ColumnModel cm : allCols) {
					sb.append(",").append(alias).append(".").append(cm.getColumn()).append(" as ").append("alias$_")
							.append(cm.getSort());
				}
				a1 = sb.toString();
			}

		}

		String tables = "";
		for (Map.Entry<Class<?>, String> ent : this.models.entrySet()) {
			TableModel table = mapping.get(ent.getKey().getName());
			String alias = ent.getValue();
			tables = tables + table.getTableName() + " " + alias + ",";
		}
		tables = tables.substring(0, tables.length() - 1);

		String retAlias = models.get(cls);
		String whenStr = convertWhenString(cModels, aliasMap);

		List<SqlNode> nodes = findIncludeNode(config, Constants.COMMON_SELECT_CONDITION);
		nodes.add(this.getInNode(config));

		preparedSelectStatement(namespace, config, rms, tables, nodes, retAlias, a1, whenStr, false);
		preparedSelectStatement(namespace, config, rms, tables, nodes, retAlias, a1, whenStr, true);

		set.add(namespace);
		return new Executor<T>(namespace, this.getSession(), cModels, aliasMap);
	}

	private void builtResultMap2(Map<String, TableModel> mapping, Configuration config, Class<?> retCls, String subKey) {
		List<ResultMapping> rms = new ArrayList<ResultMapping>();
		List<ColumnModel> allCols = mapping.get(retCls.getName()).getAllCols();
		for (ColumnModel cm : allCols) {
			String colx = "alias$_" + cm.getSort();
			ResultMapping.Builder bb = new ResultMapping.Builder(config, cm.getProperty(), colx, cm.getJavaType());
			if (cm.isPk()) {
				ArrayList<ResultFlag> flags = new ArrayList<ResultFlag>();
				flags.add(ResultFlag.ID);
				bb.flags(flags);
			}
			rms.add(bb.build());
		}
		ResultMap.Builder b = new ResultMap.Builder(config, subKey, retCls, rms);
		ResultMap rm = b.build();
		config.addResultMap(rm);
	}

	private ResultMapping nestedResultMap(Map<String, TableModel> mapping, Configuration config, Class<?> retCls,
			ResultMapping.Builder builder) {
		// builder.nestedResultMapId("vo."+retCls.getSimpleName());
		List<ColumnModel> allCols = mapping.get(retCls.getName()).getAllCols();
		String alias = models.get(retCls);
		List<ResultMapping> list = new ArrayList<ResultMapping>();
		for (ColumnModel cm : allCols) {
			String prop = alias + "_" + cm.getSort();
			ResultMapping.Builder bb = new ResultMapping.Builder(config, cm.getProperty(), prop, cm.getJavaType());
			if (cm.isPk()) {
				ArrayList<ResultFlag> flags = new ArrayList<ResultFlag>();
				flags.add(ResultFlag.ID);
				bb.flags(flags);
			}
			list.add(bb.build());
		}
		builder.composites(list);
		ResultMapping rmx = builder.build();
		return rmx;
	}

	private void preparedSelectStatement(String namespace, Configuration config, ArrayList<ResultMap> rms,
			String tables, List<SqlNode> ifs, String retAlias, String a1, String whenStr, boolean count) {
		List<SqlNode> nodes = new ArrayList<SqlNode>();

		TextSqlNode sqlNode = null;
		if (count) {
			sqlNode = new TextSqlNode("select count(*) from " + tables + " where " + whenStr + " ");
		} else {

			sqlNode = new TextSqlNode("select " + retAlias + ".*" + a1 + " from " + tables + " where " + whenStr + " ");
		}
		nodes.add(sqlNode);
		nodes.addAll(ifs);

		TextSqlNode orderNode = new TextSqlNode(" ${item.column} ${item.order} ");

		if (!count) {
			ForEachSqlNode forEachNode = new ForEachSqlNode(config, orderNode, "orderbys", "index", "item", "ORDER BY",
					"", ",");
			IfSqlNode ifSqlNode = new IfSqlNode(forEachNode, "orderbys!=null");
			nodes.add(ifSqlNode);
		}

		MixedSqlNode mixNode = new MixedSqlNode(nodes);

		SqlSource sqlSource = new DynamicSqlSource(config, mixNode);
		String id = namespace + ".selectByMap";
		if (count) {
			id = namespace + ".countByMap";
		}
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, sqlSource, SqlCommandType.SELECT);
		if (!count) {
			builder.resultMaps(rms);
		} else {
			ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(config, builder.id() + "-Inline",
					Long.class, new ArrayList<ResultMapping>());
			ArrayList<ResultMap> rms2 = new ArrayList<ResultMap>();

			rms2.add(inlineResultMapBuilder.build());
			builder.resultMaps(rms2);
		}
		MappedStatement ms = builder.build();
		config.addMappedStatement(ms);
	}

	private String convertWhenString(Map<String, Map<String, String>> cModels, Map<String, String> aliasMap) {
		String regex = "([a-zA-Z_]+?[a-z0-9A-Z_]*)[\\.]([a-zA-Z_]+?[a-z0-9A-Z_]*)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(this.when);
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			String alias = m.group(1);
			String property = m.group(2);
			Map<String, String> map = cModels.get(aliasMap.get(alias));
			String colName = map.get(property);
			m.appendReplacement(sb, alias + "." + colName);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private SqlSessionTemplate getSession() {
		return sqlSessionTemplate;
	}

}
