package com.mqm.frame.webservice;
/**
 * 
 * <pre>
 * 电厅客户端ws服务流程处理�?
 * </pre>
 * @author hukun  hukun@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版�?     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IServiceProcess {
	/**
	 * 服务流程处理
	 * @param reqXml 请求XML报文
	 * @return 反馈XML报文
	 */
	public String doProcess(String reqXml);
}
