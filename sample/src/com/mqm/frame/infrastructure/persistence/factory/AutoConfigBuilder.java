/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.builder.xml.dynamic.ChooseSqlNode;
import org.apache.ibatis.builder.xml.dynamic.DynamicSqlSource;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.builder.xml.dynamic.IfSqlNode;
import org.apache.ibatis.builder.xml.dynamic.MixedSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SetSqlNode;
import org.apache.ibatis.builder.xml.dynamic.SqlNode;
import org.apache.ibatis.builder.xml.dynamic.TextSqlNode;
import org.apache.ibatis.builder.xml.dynamic.WhereSqlNode;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.Builder;
import com.mqm.frame.infrastructure.persistence.Constants;
import com.mqm.frame.infrastructure.persistence.annotation.Type;
import com.mqm.frame.infrastructure.persistence.model.ColumnModel;
import com.mqm.frame.infrastructure.persistence.model.TableModel;
import com.mqm.frame.infrastructure.persistence.strategy.FbrpNamingStrategy;
import com.mqm.frame.infrastructure.util.Scanner;
import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * MyBatis自动配置构建器。
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
public class AutoConfigBuilder extends Builder {

	private static Map<String, TableModel> mapping;
	private static Map<String, Map<String, String>> cModels;

	/**
	 * 构建配置元数据。
	 * 
	 * @param config
	 *            Configuration
	 */
	public void built(Configuration config) {

		Collection<XMLStatementBuilder> inStmtBuilders = config.getIncompleteStatements();
		Collection<XMLStatementBuilder> dest = new ArrayList<XMLStatementBuilder>();
		for (XMLStatementBuilder builder : inStmtBuilders) {
			dest.add(builder);
		}
		config.getIncompleteStatements().clear();

		Map<String, TableModel> mapping = resolveMapping();

		for (Map.Entry<String, TableModel> ent : mapping.entrySet()) {
			List<ResultMapping> rms = new ArrayList<ResultMapping>();
			TableModel table = ent.getValue();
			Class<?> cls = table.getCls();
			for (ColumnModel col : table.getAllCols()) {
				ResultMapping.Builder builder = new ResultMapping.Builder(config, col.getProperty(), col.getColumn(),
						col.getJavaType());
				rms.add(builder.build());
			}
			ResultMap.Builder b = new ResultMap.Builder(config, "vo." + cls.getSimpleName(), cls, rms);
			ResultMap rm = b.build();
			config.addResultMap(rm);

			String alias = null;
			Entity annoEntity = cls.getAnnotation(Entity.class);
			if (StringUtils.hasText(annoEntity.name())) {
				alias = annoEntity.name();
			} else {
				alias = cls.getSimpleName();
			}
			config.getTypeAliasRegistry().registerAlias(alias, cls);

		}
		for (XMLStatementBuilder builder : dest) {
			builder.parseStatementNode();
		}

		// selectById
		preparedSqlFragment(config);
		for (Map.Entry<String, TableModel> ent : mapping.entrySet()) {
			TableModel table = ent.getValue();
			Class<?> cls = table.getCls();
			ResultMap rm = config.getResultMap("vo." + cls.getSimpleName());
			ArrayList<ResultMap> rms = new ArrayList<ResultMap>();
			rms.add(rm);
			preparedFindStatement(config, table, cls, rms);
			preparedInsertStatement(config, table, cls);
			preparedUpdateStatement(config, table, cls, false);
			preparedUpdateStatement(config, table, cls, true);
			preparedDeleteStatement(config, table, cls);
			preparedCheckEixstStatement(config, table, cls);

			List<SqlNode> nodes = findIncludeNode(config, Constants.COMMON_SELECT_CONDITION);
			IfSqlNode inNode = getInNode(config);
			nodes.add(inNode);
			MixedSqlNode includeNode = new MixedSqlNode(nodes);

			preparedQueryStatement(config, table, cls, rms, includeNode, false);
			preparedQueryStatement(config, table, cls, rms, includeNode, true);
			preparedDeleteByStatement(config, table, cls, rms, includeNode);

		}

	}

	private void preparedCheckEixstStatement(Configuration config, TableModel table, Class<?> cls) {
		List<SqlNode> nodes = new ArrayList<SqlNode>();
		nodes.add(new TextSqlNode("select count(*) from " + table.getTableName() + " t "));
		List<SqlNode> ifs = new ArrayList<SqlNode>();
		for (ColumnModel col : table.getPks()) {
			String prop = col.getProperty();
			String test = prop + "!=null ";
			TextSqlNode contents = new TextSqlNode("and t." + col.getColumn() + "<>#{" + prop + "}");
			ifs.add(new IfSqlNode(contents, test));
		}
		for (ColumnModel col : table.getCols()) {
			String prop = col.getProperty();
			String test = prop + "!=null ";
			TextSqlNode contents = new TextSqlNode("and t." + col.getColumn() + "=#{" + prop + "}");
			ifs.add(new IfSqlNode(contents, test));
		}

		MixedSqlNode ifNodes = new MixedSqlNode(ifs);
		nodes.add(new WhereSqlNode(config, ifNodes));
		SqlNode sqlNode = new MixedSqlNode(nodes);
		SqlSource sqlSource = new DynamicSqlSource(config, sqlNode);
		String id = cls.getName() + ".checkExist";
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, sqlSource, SqlCommandType.SELECT);
		ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(config, builder.id() + "-Inline", Long.class,
				new ArrayList<ResultMapping>());
		ArrayList<ResultMap> rms2 = new ArrayList<ResultMap>();

		rms2.add(inlineResultMapBuilder.build());
		builder.resultMaps(rms2);
		MappedStatement ms = builder.build();
		config.addMappedStatement(ms);
	}

	/**
	 * 解析映射信息。
	 * 
	 * @return Map
	 */
	public static Map<String, TableModel> resolveMapping() {
		if (mapping != null) {
			return mapping;
		}
		List<Class<?>> clses = Scanner.scanClass(ValueObject.class, Entity.class);
		mapping = new HashMap<String, TableModel>();

		for (Class<?> cls : clses) {
			List<Method> ms = getGetterMethods(cls);

			Table annoTable = cls.getAnnotation(Table.class);
			String tableName = null;
			if (annoTable != null) {
				tableName = annoTable.name();
			} else {
				tableName = FbrpNamingStrategy.INSTANCE.classToTableName(cls.getName());
			}

			TableModel table = new TableModel(tableName, cls);
			int sort = 0;
			for (Method method : ms) {
				Class<?> rt = method.getReturnType();
				if (!rt.getName().startsWith("java")) {
					continue;
				}
				if (Collection.class.isAssignableFrom(rt) || Map.class.isAssignableFrom(rt)) {
					continue;
				}
				String name = PropertyNamer.methodToProperty(method.getName());
				if (name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name)) {
					continue;
				}

				Column anno = AnnotationUtils.findAnnotation(method, Column.class);
				ColumnModel cm = null;

				if (anno != null) {
					cm = new ColumnModel(name, InternationalizationUtil.toUpperCase(anno.name()), rt, sort++);
				} else {
					Transient transientAnno = AnnotationUtils.findAnnotation(method, Transient.class);
					if (transientAnno == null) {
						String colName = FbrpNamingStrategy.INSTANCE.propertyToColumnName(name);
						cm = new ColumnModel(name, InternationalizationUtil.toUpperCase(colName), rt, sort++);
					}
				}
				if (cm != null) {
					Id idAnno = AnnotationUtils.findAnnotation(method, Id.class);
					if (idAnno != null) {
						cm.setPk(true);
						table.addPk(cm);
					} else {
						table.addColumn(cm);
					}
					Type jtAnno = AnnotationUtils.findAnnotation(method, Type.class);
					if (jtAnno != null) {
						cm.setJdbcType(jtAnno.value());
					}
				}

			}

			mapping.put(cls.getName(), table);

		}
		return mapping;
	}

	private void preparedQueryStatement(Configuration config, TableModel table, Class<?> cls, ArrayList<ResultMap> rms,
			MixedSqlNode includeNode, boolean count) {
		String rs = count ? "count(*)" : "t.*";
		TextSqlNode sqlNode = new TextSqlNode("select " + rs + " from " + table.getTableName() + " t ");

		WhereSqlNode whereNode = new WhereSqlNode(config, includeNode);

		TextSqlNode orderNode = new TextSqlNode(" t.${item.column} ${item.order} ");

		List<SqlNode> contents = new ArrayList<SqlNode>();
		contents.add(sqlNode);
		contents.add(whereNode);
		if (!count) {
			ForEachSqlNode forEachNode = new ForEachSqlNode(config, orderNode, "orderbys", "index", "item", "ORDER BY",
					"", ",");
			IfSqlNode ifSqlNode = new IfSqlNode(forEachNode, "orderbys!=null");
			contents.add(ifSqlNode);
		}

		MixedSqlNode mixed = new MixedSqlNode(contents);
		SqlSource sqlSource = new DynamicSqlSource(config, mixed);
		String id = cls.getName() + (count ? ".countByMap" : ".selectByMap");
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

	private void preparedDeleteStatement(Configuration config, TableModel table, Class<?> cls) {
		TextSqlNode sqlNode = new TextSqlNode("delete from " + table.getTableName() + " t where t.id= #{id}");
		SqlSource sqlSource = new DynamicSqlSource(config, sqlNode);
		String id = cls.getName() + ".deleteById";
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, sqlSource, SqlCommandType.DELETE);
		MappedStatement ms = builder.build();
		config.addMappedStatement(ms);
	}

	private void preparedDeleteByStatement(Configuration config, TableModel table, Class<?> cls,
			ArrayList<ResultMap> rms, MixedSqlNode includeNode) {
		List<SqlNode> nodes = new ArrayList<SqlNode>();
		TextSqlNode sqlNode = new TextSqlNode("delete  from " + table.getTableName() + " t ");
		WhereSqlNode whereNode = new WhereSqlNode(config, includeNode);
		nodes.add(sqlNode);
		nodes.add(whereNode);
		MixedSqlNode mixed = new MixedSqlNode(nodes);
		SqlSource sqlSource = new DynamicSqlSource(config, mixed);
		String id = cls.getName() + ".deleteByMap";
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, sqlSource, SqlCommandType.DELETE);
		MappedStatement ms = builder.build();
		config.addMappedStatement(ms);
	}

	private void preparedUpdateStatement(Configuration config, TableModel table, Class<?> cls, boolean allowNull) {
		SqlNode sqlNode = null;
		if (allowNull) {
			List<SqlNode> nodes = new ArrayList<SqlNode>();
			nodes.add(new TextSqlNode("update " + table.getTableName() + " t"));
			List<SqlNode> chooses = new ArrayList<SqlNode>();
			for (ColumnModel col : table.getCols()) {
				String prop = col.getProperty();
				String test = prop + "!=null ";
				TextSqlNode contents = new TextSqlNode("t." + col.getColumn() + "=#{" + col.getProperty() + "},");
				IfSqlNode whenNode = new IfSqlNode(contents, test);
				String test2 = prop + "==null ";
				TextSqlNode contents2 = new TextSqlNode("t." + col.getColumn() + "=null,");
				IfSqlNode otherwiseNode = new IfSqlNode(contents2, test2);
				List<IfSqlNode> whens = new ArrayList<IfSqlNode>();
				whens.add(whenNode);
				chooses.add(new ChooseSqlNode(whens, otherwiseNode));
			}

			MixedSqlNode ifNodes = new MixedSqlNode(chooses);
			nodes.add(new SetSqlNode(config, ifNodes));
			nodes.add(new TextSqlNode("where t.id=#{id}"));
			sqlNode = new MixedSqlNode(nodes);
		} else {

			List<SqlNode> nodes = new ArrayList<SqlNode>();
			nodes.add(new TextSqlNode("update " + table.getTableName() + " t"));
			List<SqlNode> ifs = new ArrayList<SqlNode>();
			for (ColumnModel col : table.getCols()) {
				String prop = col.getProperty();
				String test = prop + "!=null ";
				TextSqlNode contents = new TextSqlNode("t." + col.getColumn() + "=#{" + prop + "},");
				ifs.add(new IfSqlNode(contents, test));
			}

			MixedSqlNode ifNodes = new MixedSqlNode(ifs);
			nodes.add(new SetSqlNode(config, ifNodes));
			nodes.add(new TextSqlNode("where t.id=#{id}"));
			sqlNode = new MixedSqlNode(nodes);
		}

		DynamicSqlSource sqlSource = new DynamicSqlSource(config, sqlNode);
		String id = cls.getName() + ".update";
		if (allowNull) {
			id = cls.getName() + ".updateAllowNull";
		}
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, sqlSource, SqlCommandType.UPDATE);
		MappedStatement ms = builder.build();
		config.addMappedStatement(ms);
	}

	private void preparedFindStatement(Configuration config, TableModel table, Class<?> cls, ArrayList<ResultMap> rms) {
		//TODO luxiaocheng 此处有BUG，当表主键不是id时，应根据column等定位列名
		TextSqlNode sqlNode = new TextSqlNode("select * from " + table.getTableName() + " t where t.id= #{id}");
		SqlSource sqlSource = new DynamicSqlSource(config, sqlNode);
		String id = cls.getName() + ".selectById";
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, sqlSource, SqlCommandType.SELECT);
		builder.resultMaps(rms);
		MappedStatement ms = builder.build();
		config.addMappedStatement(ms);
	}

	private void preparedInsertStatement(Configuration config, TableModel table, Class<?> cls) {

		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(table.getTableName());
		sb.append(" ( ");
		List<ColumnModel> allCols = table.getAllCols();
		for (ColumnModel col : allCols) {
			sb.append(col.getColumn()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ) ");
		sb.append(" values( ");
		for (ColumnModel col : allCols) {
			String jdbcType = this.findJdbcType(col.getJdbcType(), col.getJavaType());
			sb.append("#{").append(col.getProperty()).append(jdbcType).append("},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ) ");
		TextSqlNode sqlNode = new TextSqlNode(sb.toString());
		SqlSource sqlSource = new DynamicSqlSource(config, sqlNode);
		String id = cls.getName() + ".insert";
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, sqlSource, SqlCommandType.INSERT);
		MappedStatement ms = builder.build();
		config.addMappedStatement(ms);
	}

	/**
	 * 解析模型。
	 * 
	 * @return Map
	 */
	public static Map<String, Map<String, String>> resolveModel() {
		if (cModels != null) {
			return cModels;
		}
		cModels = new HashMap<String, Map<String, String>>();
		for (Map.Entry<String, TableModel> ent : resolveMapping().entrySet()) {
			TableModel table = ent.getValue();
			List<ColumnModel> cols = table.getAllCols();
			HashMap<String, String> value = new HashMap<String, String>();
			for (ColumnModel cm : cols) {
				value.put(cm.getProperty(), cm.getColumn());
			}
			cModels.put(ent.getKey(), value);
		}
		return cModels;
	}

}
