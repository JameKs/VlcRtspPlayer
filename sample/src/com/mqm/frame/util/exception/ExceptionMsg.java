/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>
 * 将相关信息、异常写入到FacesContext中。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ExceptionMsg {

	private static final Log log = LogFactory.getLog(ExceptionMsg.class);
	private String exceptionMsg = "";

	/**
	 * 处理消息。
	 * 
	 * @param message 消息
	 */
	public void setMainMsg(String message){
		setMainMsg(message, null);
	}
	
	/**
	 * 处理异常信息。
	 * 
	 * @param ex 传入的异常
	 */
	public void setMainMsg(Exception ex) {
		setMainMsg(null, ex);
	}

	/**
	 * 处理消息和异常信息。
	 * 
	 * @param message 消息
	 * @param ex 传入的异常
	 */
	public void setMainMsg(String message, Exception ex) {		
		/*StringBuffer detailMessage = new StringBuffer();
		if(message != null && !"".equals(message.trim())){
			detailMessage.append(message);
		}else{
			Iterator<FacesMessage> it = FacesContext.getCurrentInstance().getMessages();
			while(it.hasNext()){
				it.remove();
			}
			exceptionMsg = null;
			return;
		}
		if(ex != null){
			log.error("", ex);
			detailMessage.append("：").append(ex.getMessage());			
		}
		exceptionMsg = detailMessage.toString();
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
				detailMessage.toString(), null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);*/
	}

	/**
	 * 获取信息。
	 * 
	 * @return String
	 */
	public String getMessage(){
		return exceptionMsg;
	}
	
}