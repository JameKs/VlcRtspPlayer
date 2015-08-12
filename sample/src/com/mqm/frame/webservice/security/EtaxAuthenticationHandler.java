package com.mqm.frame.webservice.security;

import java.io.IOException;
import java.util.HashMap;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wss4j.common.ext.WSPasswordCallback;

/**
 * 
 * <pre>
 * 电厅客户端文书类WebService服务身份验证�?
 * </pre>
 * @author hukun  hukun@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版�?     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class EtaxAuthenticationHandler implements CallbackHandler {
	
	private static final Log log = LogFactory.getLog(EtaxAuthenticationHandler.class);
	
	/**
	 * 身份认证信息
	 */
	private HashMap<String,String> identities = null;


	public HashMap<String, String> getIdentities() {
		return identities;
	}

	public void setIdentities(HashMap<String, String> identities) {
		this.identities = identities;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for(Callback callback : callbacks) {
			WSPasswordCallback wspcb = (WSPasswordCallback)callback;
			System.out.println("identifier: " + wspcb.getIdentifier()); 
			log.info(wspcb.getIdentifier());
			if (wspcb.getIdentifier() != null) {
				String passwd = identities.get(wspcb.getIdentifier());
				wspcb.setPassword(passwd);
			}
		}
    } 
		
	}
	
	

