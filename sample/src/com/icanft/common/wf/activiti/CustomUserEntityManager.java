/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.activiti;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.icanft.common.wf.ActivitiUtils;
import com.icanft.common.wf.account.AccountManager;
import com.icanft.common.wf.account.CustomGroup;
import com.icanft.common.wf.account.CustomUser;

/**
 * <pre>
 * 自定义的Activiti用户管理器
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public class CustomUserEntityManager extends UserEntityManager {  
    
	private static final Log logger = LogFactory  
            .getLog(CustomUserEntityManager.class);  
  
    @Autowired  
    private AccountManager accountManager;    
  
    @Override  
    public UserEntity findUserById(final String userCode) {  
        if (userCode == null)  
            return null;  
  
        try {  
            UserEntity userEntity = null;  
            CustomUser customUser = accountManager.getUser(Long.valueOf(userCode)	);  
            userEntity = ActivitiUtils.toActivitiUser(customUser);  
            return userEntity;  
        } catch (EmptyResultDataAccessException e) {  
            return null;  
        }  
    }  
  
    @Override  
    public List<Group> findGroupsByUser(final String userCode) {  
        if (userCode == null)  
            return null;  
  
            List<CustomGroup> customGroups = accountManager.getUser(Long.valueOf(userCode)).getGroupList();  
              
            List<Group> gs = null;  
            gs = ActivitiUtils.toActivitiGroups(customGroups);  
            return gs;  
              
    }  
  
    @Override  
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,  
            String key) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public List<String> findUserInfoKeysByUserIdAndType(String userId,  
            String type) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public long findUserCountByQueryCriteria(UserQueryImpl query) {  
        throw new RuntimeException("not implement method.");  
    }  
}  



 





