/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class AjaxBaseAction {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	private Map<String,String> map = new HashMap<String, String>();
	
	/**
	 * 初始化。
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public void init(HttpServletRequest request, HttpServletResponse response) {
	/*	ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		*/
		
		this.request = request;
		this.response = response;
	}

	/**
	 * 服务。
	 */
	public void doService(){
		
	}
	
	/**
	 * 直接向浏览器写文本。
	 * 
	 * @param text
	 *            要写出的文本
	 * @throws IOException
	 */
	protected void renderText(String text) throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(text);
	}

}
