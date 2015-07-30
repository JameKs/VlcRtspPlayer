/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
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
public class MessageResources extends AbstractMessageResources {

	private static final Log log = LogFactory.getLog(MessageResources.class);

	/** 属性文件后缀。*/
	public static final String PROPERTY_POSTFIX = ".properties";
	
	private int cacheMillis = -1;
	private ReloadableResourceBundleMessageSource rMessageSource = new ReloadableResourceBundleMessageSource();
	private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	/**
	 * 默认的构造方法。
	 */
	public MessageResources() {
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param baseName  String
	 */
	public MessageResources(String baseName) {
		setBaseNames(new String[] { baseName });
	}

	/**
	 * 自定义的构造方法。
	 * 
	 * @param baseNames String[]
	 */
	public MessageResources(String[] baseNames) {
		setBaseNames(baseNames);
	}

	/**
	 * 设置BaseNames。
	 * 
	 * @param baseName  String
	 */
	public void setBaseName(String baseName) {
		setBaseNames(new String[] { baseName });
	}

	/**
	 * 设置BaseNames。
	 * 
	 * @param baseNames String[] 
	 */
	public void setBaseNames(String[] baseNames) {
		List<String> baseNameList = new ArrayList<String>();
		try {
			for (String baseName : baseNames) {
				Resource[] resources = resolver.getResources(baseName);
				for (Resource resource : resources) {
					String fileName = resource.getURI().toString();
					baseNameList.add(fileName.substring(0, fileName.indexOf(PROPERTY_POSTFIX)));

					if (log.isInfoEnabled()) {
						log.info("Add properties file: [" + resource.getDescription() + "]");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.rMessageSource.setCacheSeconds(this.getCacheMillis());
		this.rMessageSource.setBasenames(baseNameList.toArray(new String[baseNameList.size()]));
	}

	@Override
	public String getMessage(String key, Object[] params, String defaultMessage, Locale locale) {
		return this.rMessageSource.getMessage(key, params, defaultMessage, locale);
	}

	/**
	 * 返回 cacheMillis。
	 * 
	 * @return 返回 cacheMillis。
	 */
	public int getCacheMillis() {
		return cacheMillis;
	}

	/**
	 * 设置 cacheMillis。
	 * 
	 * @param cacheMillis
	 *            设置 cacheMillis。
	 */
	public void setCacheMillis(int cacheMillis) {
		this.cacheMillis = cacheMillis;
	}

}
