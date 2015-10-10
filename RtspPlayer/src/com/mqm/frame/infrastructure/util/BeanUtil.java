/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luoshifei  luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class BeanUtil {

	private static final Log log = LogFactory.getLog(BeanUtil.class);

	protected static final String DATA_TO_STRING_FORMATE = "yyyy-MM-dd";
	private static final String PROP_SEP = ".";

	protected static final Converter DATE_CONVERTER = new Converter() {

		@Override
		public Object convert(Class arg0, Object arg1) {
			String p = (String) arg1;
			if (p == null || p.trim().length() == 0) {
				return null;
			}
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(p.trim());
			} catch (Exception e) {
				log.error("", e);
				return null;
			}
		}

	};

	/**
	 * 将Object类型转换成Map类型。
	 * 
	 * @param fieldNameList List
	 * @param object Object
	 * 
	 * @return Map
	 */
	public static Map objectToMap(List fieldNameList, Object object) {
		Map ret = new HashMap();
		for (Iterator it = fieldNameList.iterator(); it.hasNext();) {
			String fieldName = (String) it.next();
			String fs[] = fieldName.split(quote("."));
			try {
				Object o = object;
				int i = 0;
				do {
					if (i >= fs.length) {
						break;
					}
					Map objDesc = PropertyUtils.describe(o);
					o = objDesc.get(fs[i]);
					if (o == null) {
						break;
					}
					i++;
				} while (true);
				ret.put(fieldName, o);
			} catch (Exception e) {
				log.error("", e);
			}
		}

		return ret;
	}

	/**
	 * 进行Quote处理。
	 * 
	 * @param s String
	 * 
	 * @return String
	 */
	public static String quote(String s) {
		int slashEIndex = s.indexOf("\\E");
		if (slashEIndex == -1) {
			return (new StringBuilder()).append("\\Q").append(s).append("\\E")
					.toString();
		}
		StringBuffer sb = new StringBuffer(s.length() * 2);
		sb.append("\\Q");
		slashEIndex = 0;
		int current = 0;
		while ((slashEIndex = s.indexOf("\\E", current)) != -1) {
			sb.append(s.substring(current, slashEIndex));
			current = slashEIndex + 2;
			sb.append("\\E\\\\E\\Q");
		}
		sb.append(s.substring(current, s.length()));
		sb.append("\\E");
		return sb.toString();
	}

	/**
	 * 将Object类型的数据装换成Map类型的数据。
	 * 
	 * @param o Object
	 * @return Map
	 */
	public static Map objectToMap(Object o) {
		return objectToMap(o, "");
	}

	private static Map objectToMap(Object o, String prefix) {
		Map ret = new HashMap();
		if (o == null) {
			return ret;
		}
		try {
			Map objDesc = PropertyUtils.describe(o);
			prefix = "".equals(prefix) ? "" : (new StringBuilder())
					.append(prefix).append(".").toString();
			for (Iterator it = objDesc.keySet().iterator(); it.hasNext();) {
				String key = it.next().toString();
				Object val = objDesc.get(key);
				if (val != null && (val instanceof ValueObject)
						&& !o.equals(val)) {
					ret.putAll(objectToMap(val,
							(new StringBuilder()).append(prefix).append(key)
									.toString()));
				} else {
					ret.put((new StringBuilder()).append(prefix).append(key)
							.toString(), val);
				}
			}

		} catch (Exception e) {
			log.error("", e);
		}
		log.debug((new StringBuilder()).append("Object ").append(o)
				.append(" convert to map: ").append(ret).toString());
		return ret;
	}

	/**
	 * 将Map对象映射到Object中。
	 * 
	 * @param map Map
	 * @param clazz Class
	 * 
	 * @return Object
	 */
	public static Object mapToObject(Map map, Class clazz) {
		Object bean = null;
		try {
			bean = clazz.newInstance();
		} catch (Exception e) {
			log.error((new StringBuilder()).append("Unable to instant object ")
					.append(clazz).toString());
			return null;
		}
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			try {
				if (key.lastIndexOf(".") > 0) {
					String prop = key.substring(key.lastIndexOf(".") + 1);
					PropertyUtils.setSimpleProperty(getBean(bean, key), prop,
							map.get(key));
				} else {
					PropertyUtils.setSimpleProperty(bean, key, map.get(key));
				}
			} catch (Exception e) {
				log.warn((new StringBuilder())
						.append("Unable to set property ").append(key)
						.append(" to object ").append(bean).toString());
			}
		}

		return bean;
	}

	private static Object getBean(Object bean, String key) {
		String objs[] = key.split(quote("."));
		Object ret = bean;
		for (int i = 0; i < objs.length - 1; i++) {
			try {
				Object newRet = PropertyUtils.getProperty(ret, objs[i]);
				if (newRet == null) {
					newRet = PropertyUtils.getPropertyType(ret, objs[i])
							.newInstance();
					PropertyUtils.setProperty(ret, objs[i], newRet);
				}
				ret = newRet;
			} catch (Exception e) {
				return null;
			}
		}

		return ret;
	}

	/**
	 * 判断int数组中是否存在某一int取值。
	 * 
	 * @param statusList int
	 * @param status int
	 * 
	 * @return boolean
	 */
	public static boolean isExistStatus(int statusList[], int status) {
		for (int i = 0; i < statusList.length; i++) {
			if (status == statusList[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 完成对象间的拷贝。
	 * 
	 * @param dest 目标对象。
	 * @param src 源对象。
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static void copyObject(Object dest, Object src)
			throws FbrpException {
		Map m;
		Map d;
		Iterator iter;
		try {
			if (src == null || src.equals(dest)) {
				return;
			}
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
		try {
			m = BeanUtils.describe(src);

			d = new HashMap();
			iter = m.entrySet().iterator();
			do {
				if (!iter.hasNext()) {
					break;
				}
				java.util.Map.Entry element = (java.util.Map.Entry) iter.next();
				if (element.getValue() != null) {
					d.put(element.getKey(), element.getValue());
				}
			} while (true);
			BeanUtils.populate(dest, d);

		} catch (IllegalAccessException e) {
			log.error("", e);
		} catch (InvocationTargetException e) {
			log.error("", e);
		} catch (NoSuchMethodException e) {
			log.error("", e);
		}
	}

	/**
	 * 复制属性。
	 * 
	 * @param dest Object
	 * @param src Object
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static void copyProperties(Object dest, Object src)
			throws FbrpException {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
	}

	/**
	 * 获取属性。
	 * 
	 * @param bean Object
	 * @param propertyName String
	 * 
	 * @return String
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static String getProperty(Object bean, String propertyName)
			throws FbrpException {
		try {
			return BeanUtils.getProperty(bean, propertyName);
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
	}

	/**
	 * 设置属性。
	 * 
	 * @param bean Object
	 * @param propertyName String
	 * @param value Object
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static void setProperty(Object bean, String propertyName,
			Object value) throws FbrpException {
		try {
			BeanUtils.setProperty(bean, propertyName, value);
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
	}

	/**
	 * 获取Object的属性。
	 * 
	 * @param bean Object
	 * @param propertyName String
	 * 
	 * @return Object
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static Object getObjectProperty(Object bean, String propertyName)
			throws FbrpException {
		try {
			return PropertyUtils.getProperty(bean, propertyName);
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
	}

	/**
	 * 克隆一个Bean。
	 * 
	 * @param bean Object
	 * 
	 * @return Object
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static Object cloneBean(Object bean) throws FbrpException {
		try {
			return BeanUtils.cloneBean(bean);
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
	}

	/**
	 * 将对象转换成Map。
	 * 
	 * @param bean Object
	 * 
	 * @return Map
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static Map describe(Object bean) throws FbrpException {
		try {
			return PropertyUtils.describe(bean);
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
	}

	/**
	 * 借助org.apache.commons.beanutils.BeanUtils.populate完成对象转换。
	 * 
	 * @param bean Object
	 * @param map Map
	 * @throws FbrpException FBRP异常
	 */
	public static void populate(Object bean, Map map) throws FbrpException {
		try {
			ConvertUtils.register(DATE_CONVERTER, java.util.Date.class);
			BeanUtils.populate(bean, map);
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
	}

	/**
	 * 借助trim()操作梳理对象中属性取值中的空格。
	 * 
	 * @param bean Object
	 * 
	 * @return Object
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static Object trim(Object bean) throws FbrpException {
		if (bean == null) {
			return null;
		}
		try {
			Map map = null;
			if (bean instanceof Map) {
				map = (Map) bean;
			} else {
				map = PropertyUtils.describe(bean);
			}
			Iterator it = map.entrySet().iterator();
			do {
				if (!it.hasNext()) {
					break;
				}
				java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
				if (entry.getValue() != null
						&& (entry.getValue() instanceof String)) {
					entry.setValue(entry.getValue().toString().trim());
				}
			} while (true);
			if (!(bean instanceof Map)) {
				PropertyUtils.copyProperties(bean, map);
			}
		} catch (Throwable e) {
			log.error("", e);
			throw new FbrpException(e);
		}
		return bean;
	}

	/**
	 *  借助trim()操作梳理List存储内容中的空格。
	 *  
	 * @param list List
	 * 
	 * @return List
	 * 
	 * @throws FbrpException FBRP异常
	 */
	public static List trim(List list) throws FbrpException {
		if (list == null || list.size() == 0) {
			return list;
		}
		for (Iterator it = list.iterator(); it.hasNext(); trim(it.next())) {
			;
		}
		return list;
	}

}
