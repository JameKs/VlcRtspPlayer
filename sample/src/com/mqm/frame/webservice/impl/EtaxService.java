package com.mqm.frame.webservice.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.mqm.frame.webservice.IEtaxService;
/**
 * 
 * <pre>
 * 电厅客户端WebService服务入口实现�?
 * </pre>
 * @author hukun  hukun@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版�?     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@WebService(targetNamespace="http://cn.gov.chinatax.etax.service/")
@SOAPBinding(style = Style.DOCUMENT)
public class EtaxService implements IEtaxService {
	/**
	 * 服务流程实现�?
	 
	private IServiceProcess serviceProcess  = null;
	@WebMethod(exclude=true)
	public IServiceProcess getServiceProcess() {
		return serviceProcess;
	}
	
	@WebMethod(exclude=true)
	public void setServiceProcess(IServiceProcess serviceProcess) {
		this.serviceProcess = serviceProcess;
	}
	*/
	@Override
	@WebMethod
	public String doService(@WebParam(name="reqXml")String reqXml) {
		System.out.println("--进了接口!");
		String res = reqXml;
		return res;
	}

}
