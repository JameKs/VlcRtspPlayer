/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.icanft.common.wf.service.IProcessManageService;

/**
 * <pre>
 * 工作流程图绘制。
 * </pre>
 * @author zengziwen  zengziwen@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ProcessPicViewServlet  implements  IServletProxyHandler{
	
	private IProcessManageService processManageService ;

	/* (non-Javadoc)
	 * @see com.icanft.common.wf.servlet.IServletProxyHandler#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		doPost(req,resp);
	}

	/* (non-Javadoc)
	 * @see com.icanft.common.wf.servlet.IServletProxyHandler#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
		String processDefId = req.getParameter("processDefId");
		InputStream is = processManageService.getProcessPic(processDefId);
		resp.setContentType("image/png");
		OutputStream out = resp.getOutputStream();
		byte[] b = new byte[1024];   
		int len = -1;   
		while((len = is.read(b, 0, 1024)) != -1) {   
		    out.write(b, 0, len);   
		}      
		out.flush();   
	}

	/**
	 * 设置 processManageService。
	 * 
	 * @param processManageService IProcessManageService
	 */
	public void setProcessManageService(IProcessManageService processManageService) {
		this.processManageService = processManageService;
	}
 
}
