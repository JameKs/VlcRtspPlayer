/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.mqm.frame.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * <pre>
 * session超时的监听器
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月24日
 */
public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		HttpSession ses = event.getSession();
		String id = ses.getId() + ses.getCreationTime();
		User user = (User) ses.getAttribute("user");
		BaseConstants.USER_COUNT++; // 用户数加一
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession ses = event.getSession();
		String id = ses.getId() + ses.getCreationTime();
		User user = (User) ses.getAttribute("user");
		synchronized (this) {
			BaseConstants.USER_COUNT--; // 用户数减一
			//
		}
	}
}
