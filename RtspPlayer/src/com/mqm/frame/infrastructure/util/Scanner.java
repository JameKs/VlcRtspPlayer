/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/**
 * 
 * <pre>
 * 工具类，提供在类路径下Java类的扫描功能。
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
public class Scanner {

	private static final Log log = LogFactory.getLog(Scanner.class);

	private static final String PATH = "classpath*:com/foresee/**/vo/*.class";
	private static final ResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();
	private static final MetadataReaderFactory READER_FACTORY = new SimpleMetadataReaderFactory();

	/**
	 * 扫描。
	 * 
	 * @return List
	 */
	public static List<MetadataReader> scan() {
		ArrayList<MetadataReader> list = new ArrayList<MetadataReader>();
		try {
			Resource[] resources = RESOLVER.getResources(PATH);
			for (Resource res : resources) {
				MetadataReader meta = READER_FACTORY.getMetadataReader(res);
				list.add(meta);
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return list;
	}

	/**
	 * 扫描相关元数据。
	 * 
	 * @param superClazz Class
	 * @param annoClazz Class
	 * 
	 * @return List
	 */
	public static List<Class<?>> scanClass(Class<?> superClazz,
			Class<?> annoClazz) {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		List<MetadataReader> list = scan();
		String annoName = annoClazz.getName();
		for (MetadataReader reader : list) {
			Set<String> annotationTypes = reader.getAnnotationMetadata()
					.getAnnotationTypes();
			if (annotationTypes.contains(annoName)) {
				String name = reader.getClassMetadata().getClassName();
				try {
					Class<?> clazz = Class.forName(name);
					if (superClazz.isAssignableFrom(clazz)) {
						ret.add(clazz);
					}
				} catch (ClassNotFoundException e) {
					log.error("", e);
				}
			}
		}
		return ret;
	}
}
