/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.persistence.strategy;

import java.io.Serializable;

import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * FBRP命名策略。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpNamingStrategy implements NamingStrategy, Serializable {
	
	private static final long serialVersionUID = -1581608264588898236L;
	
	/** 构建实例。 */
	public static final NamingStrategy INSTANCE = new FbrpNamingStrategy();

	@Override
	public String classToTableName(String className) {
		String unqualify = unqualify( className );
		if(unqualify.endsWith("VO")){
			unqualify = unqualify.substring(0,unqualify.length()-2);
		}
		return addUnderscores(unqualify);
	}
	
	@Override
	public String propertyToColumnName(String propertyName) {
		return addUnderscores(unqualify( propertyName ));
	}

	protected static String addUnderscores(String name) {
		StringBuffer buf = new StringBuffer( name.replace('.', '_') );
		for (int i=1; i<buf.length()-1; i++) {
			if (
				Character.isLowerCase( buf.charAt(i-1) ) &&
				Character.isUpperCase( buf.charAt(i) ) &&
				Character.isLowerCase( buf.charAt(i+1) )
			) {
				buf.insert(i++, '_');
			}
		}
		return InternationalizationUtil.toLowerCase(buf.toString());
	}
	
	protected static String unqualify(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		return ( loc < 0 ) ? qualifiedName : qualifiedName.substring( qualifiedName.lastIndexOf(".") + 1 );
	}

	@Override
	public String foreignKeyColumnName(String propertyName) {
		return this.propertyToColumnName(propertyName)+"_id";
	}

}
