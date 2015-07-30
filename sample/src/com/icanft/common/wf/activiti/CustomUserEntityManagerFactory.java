/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public class CustomUserEntityManagerFactory implements SessionFactory {  
    private UserEntityManager userEntityManager;  
  
    @Autowired  
    public void setUserEntityManager(UserEntityManager userEntityManager) {  
        this.userEntityManager = userEntityManager;  
    }  
  
    public Class<?> getSessionType() {  
        // 返回原始的UserManager类型  
        return UserEntityManager.class;  
    }  
  
    public Session openSession() {  
        // 返回自定义的UserManager实例  
        return userEntityManager;  
    }  
}  

