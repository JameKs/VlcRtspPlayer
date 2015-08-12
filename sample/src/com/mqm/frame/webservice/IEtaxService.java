package com.mqm.frame.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
/**
 * 
 * <pre>
 * 电厅客户端WebService服务入口�?
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
public interface IEtaxService {
	/**
	 * 电厅客户端文书类接入WEBSERVICE服务接口
	 * 
	 * @param reqXml 请求XML报文
	 * @return 反馈业务处理结果XML报文
	 */
	@WebMethod
	public String doService(@WebParam(name="reqXml")String reqXml);	
}
