/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
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
public class PackageUtil {

	private static final Log log = LogFactory.getLog(PackageUtil.class);
	
	/**
	 * Implementation-Version。
	 * 
	 * @param clazz 类
	 * 
	 * @return 包的版本信息
	 */
	public static String getImplementationVersion(Class clazz) {
		Package pkg = clazz.getPackage();
		if(pkg != null) {
			return pkg.getImplementationVersion()+"\n\r";
		} 
		return "";
	}
	
	
	
	/**
	 * Implementation-Version。
	 * 
	 * @param className 类名
	 * 
	 * @return 包的版本信息
	 */
	public static String getImplementationVersion(String className) {
		try {
			Class clazz = PackageUtil.class.getClassLoader().loadClass(className);
			Package pkg = clazz.getPackage();
			if(pkg != null) {
				return pkg.getImplementationVersion()+"\n\r";
			}
		} catch (ClassNotFoundException e) {
			log.error("PackageUtil " + className + " ClassNotFoundException", e);
		}
		return "";
	}
	
	/**
	 * Implementation-Title。
	 * 
	 * @param clazz 类
	 * 
	 * @return 包的标题
	 */
	public static String getImplementationTitle(Class clazz) {
		Package pkg = clazz.getPackage();
		if(pkg != null) {
			return pkg.getImplementationTitle()+"\n\r";
		}
		return "";
	}
	
	/**
	 * Implementation-Title。
	 *
	 * @param className 类路径
	 * 
	 * @return 包的标题
	 */
	public static String getImplementationTitle(String className) {
		try {
			Class clazz = PackageUtil.class.getClassLoader().loadClass(className);
			Package pkg = clazz.getPackage();
			if(pkg != null) {
				return pkg.getImplementationTitle()+"\n\r";
			}
		} catch (ClassNotFoundException e) {
			log.error("PackageUtil " + className + " ClassNotFoundException", e);
		}
		return "";
	}

	/**
	 * Implementation-Vendor。
	 * 
	 * @param clazz 类
	 * 
	 * @return 提供该实现的组织、供应商或公司的名称。
	 */
	public static String getImplementationVendor(Class clazz) {
		Package pkg = clazz.getPackage();
		if(pkg != null) {
			return pkg.getImplementationVendor()+"\n\r";
		}
		return "";
	}

	/**
	 * Implementation-Vendor。
	 * 
	 * @param className 类路径
	 * 
	 * @return 提供该实现的组织、供应商或公司的名称。
	 */
	public static String getImplementationVendor(String className) {
		try {
			Class clazz = PackageUtil.class.getClassLoader().loadClass(className);
			Package pkg = clazz.getPackage();
			if(pkg != null) {
				return pkg.getImplementationVendor()+"\n\r";
			}
		} catch (ClassNotFoundException e) {
			log.error("PackageUtil " + className + " ClassNotFoundException", e);
		}
		return "";
	}
	
	/**
	 * 取得title+version。
	 * 
	 * @param className 类路径
	 * 
	 * @return 包的标题和提供该实现的组织、供应商或公司的名称。
	 */
	public static String getImplementationTitleAndVersion(String className) {
		try {
			Class clazz = PackageUtil.class.getClassLoader().loadClass(className);
			Package pkg = clazz.getPackage();
			if(pkg!=null && pkg.getImplementationTitle()!=null && pkg.getImplementationVersion()!=null) {
				return pkg.getImplementationTitle().substring(pkg.getImplementationTitle().lastIndexOf(".")+1)+":"+pkg.getImplementationVersion()+"\n\r";
			}
		} catch (ClassNotFoundException e) {
			log.error("PackageUtil " + className + " ClassNotFoundException", e);
		}
		return "";
	}
	
	/**
	 * 取得title+version。
	 * 
	 * @param clazz 类名
	 * 
	 * @return 包的标题和提供该实现的组织、供应商或公司的名称。
	 */
	public static String getImplementationTitleAndVersion(Class clazz) {
		Package pkg = clazz.getPackage();
		if(pkg!=null && pkg.getImplementationTitle()!=null && pkg.getImplementationVersion()!=null) {
			return pkg.getImplementationTitle().substring(pkg.getImplementationTitle().lastIndexOf(".")+1)+":"+pkg.getImplementationVersion()+"\n\r";
		} 
		return "";
	}
}
