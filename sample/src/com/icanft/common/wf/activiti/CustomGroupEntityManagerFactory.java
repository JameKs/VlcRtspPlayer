/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public class CustomGroupEntityManagerFactory implements SessionFactory {  
    private GroupEntityManager groupEntityManager;  
  
    @Autowired  
    public void setGroupEntityManager(GroupEntityManager groupEntityManager) {  
        this.groupEntityManager = groupEntityManager;  
    }  
  
    public Class<?> getSessionType() {  
        // 返回原始的GroupEntityManager类型  
        return GroupEntityManager.class;  
    }  
  
    public Session openSession() {  
        // 返回自定义的GroupEntityManager实例  
        return groupEntityManager;  
    }  
}  


  