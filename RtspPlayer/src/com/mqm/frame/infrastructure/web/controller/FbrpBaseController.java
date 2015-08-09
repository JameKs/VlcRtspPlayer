/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.mqm.frame.infrastructure.FbrpConfigInfo;
import com.mqm.frame.infrastructure.service.IDefaultService;
import com.mqm.frame.infrastructure.startup.ContextInit;
import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.infrastructure.vo.DateConverter;
import com.mqm.frame.util.exception.ExceptionMsg;
import com.mqm.frame.util.exception.FbrpException;
/**
 * <pre>
 * FBRP内置的Spring Web MVC基类。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FbrpBaseController  {

	private static final Log log = LogFactory.getLog(FbrpBaseController.class);

	private IDefaultService defaultService = null;

	protected ExceptionMsg exceptionMsg = new ExceptionMsg();
	protected ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 返回FbrpConfigInfo。
	 * 
	 * @return FbrpConfigInfo
	 */
	public FbrpConfigInfo getFbrpConfigInfo() {
		ApplicationContext applicationContext = ContextUtil.getApplicationContext();
		FbrpConfigInfo fbrpConfigInfo = (FbrpConfigInfo) applicationContext.getBean(FbrpConfigInfo.BEAN_ID);
		return fbrpConfigInfo;
	}

	/**
	 * 返回 defaultService。
	 * 
	 * @return IDefaultService
	 */
	public IDefaultService getDefaultService() {
		if (defaultService == null) {
			defaultService = (IDefaultService) ContextInit.getContext().getBean(IDefaultService.BEAN_ID);
		}
		return defaultService;
	}

	/**
	 * 日志审计。
	 * 
	 * @param bizType
	 *            操作的模块
	 * @param opType
	 *            操作类型
	 * @param opInfo
	 *            操作信息
	 */
	public void auditLog(String bizType, String opType, String opInfo) {
		this.getDefaultService().auditLog(bizType, opType, opInfo);
	}

	/**
	 * 获取UserProfileVO。
	 * 
	 * @return UserProfileVO
	 */
	public UserProfileVO getUser() {
		return (UserProfileVO) ContextUtil.get("UserProfile", ContextUtil.SCOPE_SESSION);
	}

	/**
	 * 下载文件。
	 * 
	 * @param object
	 *            Object
	 * @param fileName
	 *            String
	 * @param contentType
	 *            String
	 * @param response
	 *      HttpServletResponse
	 * @throws IOException
	 *             异常
	 */
	public void downLoadFile(Object object, String fileName, String contentType, HttpServletResponse response)
			throws IOException {
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", ("attachment; filename=" + new String(fileName.getBytes("GBK"),
				"ISO8859_1")));
		try {
			ServletOutputStream out = response.getOutputStream();
			// 更改这里的方法，不能一次全部输出，需要分段输出才行
			if (object.getClass().isInstance(new ByteArrayOutputStream())) {
				// ByteArrayOutputStream baos = castToBAOStream(object,
				// 100);
				ByteArrayOutputStream baos = (ByteArrayOutputStream) object;
				baos.writeTo(out);
				out.flush();
			} else {
				InputStream in = null;
				try{
					in = (InputStream) object;
					byte[] buffer = new byte[1024];
					int iLength = 0;
					while ((iLength = in.read(buffer)) != -1) {
						out.write(buffer, 0, iLength);
						out.flush();
					}
				} finally{
					if(in != null){
						in.close();
					}
				}
			}
		} catch (IOException e) {
			log.error("下载文件出现异常", e);
		}
	}

	protected int getPageIndex(HttpServletRequest request) {
		String str = request.getParameter("pageIndex");
		if (str == null && !StringUtils.hasText(str)) {
			return 1;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			log.info("pageIndex 参数转换出现错误!", e);
		}
		return 1;
	}

	protected int getPageSize(HttpServletRequest request) {
		String str = request.getParameter("pageSize");
		if (str == null && !StringUtils.hasText(str)) {
			return 10;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			log.info("pageSize 参数转换出现错误!", e);
		}
		return 10;
	}
	
	/**
	 * 将对象转换为JSON字符串。
	 * 
	 * @param object Object
	 * 
	 * @return String
	 */
	protected String toJson(Object object){
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new FbrpException("JSON数据转换时出现错误:"+object.getClass().getName(), e);
		}
	}
	
	/**
	 * 获取session保存数据。
	 * 
	 * @param t 载体
	 * @param o 本体
	 * @param session_key session的键值
	 * @param mark_name 用于判断的标签名称
	 */
	public <T> void setValueObject(T t, T o, String session_key, String mark_name) {
		this.setValueObject(t, o, session_key, mark_name, null);
	}
	
	/**
	 * 获取session保存数据。
	 * 
	 * @param t 载体
	 * @param o 本体
	 * @param session_key session的键值
	 * @param mark_name 用于判断的标签名称
	 * @param pattern 日期格式
	 */
	public <T> void setValueObject(T t, T o, String session_key, String mark_name, String pattern) {
		UserProfileVO user = this.getUser();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)ContextUtil.get(session_key + user.getLoginId(),
				ContextUtil.SCOPE_SESSION);
		if(null != map) {
			try {
				ConvertUtilsBean cub = new ConvertUtilsBean();
				DateConverter dc = new DateConverter(pattern);
				BigDecimalConverter bdc = new BigDecimalConverter(new BigDecimal("0"));
				cub.register(dc, Date.class);
				BeanUtilsBean bub = new BeanUtilsBean(cub, new PropertyUtilsBean());
				ConvertUtils.register(bdc, BigDecimal.class);
				for(String key : map.keySet()) {
					bub.setProperty(t, key, map.get(key));
				}
				if(null != t && "back".equals(BeanUtils.getProperty(t, mark_name))) {
					BeanUtils.copyProperties(o, t);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			ContextUtil.remove(session_key + user.getLoginId(), ContextUtil.SCOPE_SESSION);
			
		}
	}
}
