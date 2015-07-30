/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.icanft.common.wf.account.AccountManager;
import com.icanft.common.wf.account.CustomGroup;
/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public class CustomGroupEntityManager extends GroupEntityManager {  
    private static final Log logger = LogFactory  
            .getLog(CustomGroupEntityManager.class);  
  
    @Autowired  
    private AccountManager accountManager;  
  
     
    public GroupEntity findGroupById(final String groupCode) {  
        if (groupCode == null)  
            return null;  
          
            try {  
                CustomGroup bGroup = accountManager.getGroupByGroupId(groupCode);  
                  
                GroupEntity e = new GroupEntity();  
                e.setRevision(1);  
  
                // activiti有3种预定义的组类型：security-role、assignment、user  
                // 如果使用Activiti  
                // Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务  
                e.setType("assignment");  
  
                e.setId(bGroup.getId());  
                e.setName(bGroup.getName());  
                return e;  
            } catch (EmptyResultDataAccessException e) {  
                return null;  
            }  
              
    }  
  
    @Override  
    public List<Group> findGroupsByUser(final String userCode) {  
        if (userCode == null)  
            return null;  
  
        List<CustomGroup> customGroupList = accountManager.getUser(Long.valueOf(userCode)).getGroupList();  
          
        List<Group> gs = new ArrayList<Group>();  
        GroupEntity g;  
        for (CustomGroup customGroup : customGroupList) {  
            g = new GroupEntity();  
            g.setRevision(1);  
            g.setType("assignment");  
  
            g.setId(customGroup.getId());  
            g.setName(customGroup.getName());  
            gs.add(g);  
        }  
        return gs;  
    }  
  
    @Override  
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {  
        throw new RuntimeException("not implement method.");  
    }  
}  