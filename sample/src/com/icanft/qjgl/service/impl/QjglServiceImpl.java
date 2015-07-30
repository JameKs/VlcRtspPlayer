/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.qjgl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icanft.common.wf.rw.service.IWfRwService;
import com.icanft.common.wf.rw.vo.WfRw;
import com.icanft.common.wf.rw.vo.WfSpyj;
import com.icanft.qjgl.dao.IQjglDao;
import com.icanft.qjgl.service.IQjglService;
import com.icanft.qjgl.vo.Qjxx;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class QjglServiceImpl implements IQjglService {
	
	private static final Logger log = Logger.getLogger(QjglServiceImpl.class);
	
	private IWfRwService wfRwService;
	
	private IQjglDao qjglDao;
	
	@Override
	public void insert(Qjxx qjxx){
		qjglDao.insert(qjxx);
	}

	@Override
	public void update(Qjxx qjxx){
		qjglDao.update(qjxx);
	}
	
	@Override
	public List findList(Qjxx qjxx){
		List qj = qjglDao.findList(qjxx);
		return qj;
	}

	@Override
	public long findListCount(Qjxx qjxx){
		long count = qjglDao.findListCount(qjxx);
		return count;
	}
	
	public void insertOrUpdateSpyj(WfSpyj spyj){
		wfRwService.insertOrUpdateSpyj(spyj);
	}
	/**
	 * 插入业务数据和任务记录
	 * @param qjxx
	 */
	@Override
	public void insertYwAndRw(Qjxx qjxx){
		if(StringUtils.isNotEmpty(qjxx.getId())){
			qjxx.setXgr(qjxx.getCjr());
			qjglDao.update(qjxx);
			qjxx.getId();
		}else{
			qjglDao.insert(qjxx);
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assignee", qjxx.getUserId());
		
		WfRw rw = new WfRw();
		rw.setYhrwId(qjxx.getId());
		rw.setRwlxDm(qjxx.getRwlxDm());
		rw.setUserId(qjxx.getUserId());
		rw.setCjr(qjxx.getCjr());
		rw.setZxZt("0");
		
		String rwId = wfRwService.startProcByKey(rw ,"xbhssq-ys", variables);
		qjxx.setRwId(rwId);
		qjxx.setXgr(qjxx.getCjr());
		qjglDao.update(qjxx); //同一个事物再次更新时不生效，所以改成了insert
	}
	
	/**
	 * 更新业务数据和任务记录（办理我的代办）
	 * @param qjxx
	 */
	@Override
	public void updateYwAndRw(Qjxx qjxx, String spyj){
		//完成流程
		String taskId = qjxx.getTaskId();
		String rwId = qjxx.getRwId();
		String procInsId = qjxx.getProcInsId();

		//activiti流程参数
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assignee", qjxx.getUserId());
		
		//审批已经意见
		WfSpyj wfSpyj = new WfSpyj();
		wfSpyj.setYhrwId(qjxx.getId());
		wfSpyj.setUserId(qjxx.getUserId());
		wfSpyj.setSpyj(spyj);
		wfSpyj.setCjr(qjxx.getXgr());
		wfSpyj.setXgr(qjxx.getXgr());
		
		wfRwService.completeTask(taskId, rwId, procInsId, variables, wfSpyj);
		
		

	}
	
	/**
	 * 我的代办任务信息
	 * @param qjxx
	 * @return
	 */
	@Override
	public Qjxx findById(String id){
		Qjxx tmpQjxx = qjglDao.findById(id);
		  
	    // 根据当前人的ID查询  
		WfRw rw = wfRwService.getActTask(tmpQjxx.getRwId());   
        
        tmpQjxx.setTaskId(rw.getTaskId());
        tmpQjxx.setName(rw.getName());  //任务名称 
        tmpQjxx.setProcDefId(rw.getProcDefId());//流程定义ID
        tmpQjxx.setProcInsId(rw.getProcInsId());
        tmpQjxx.setOwner(rw.getOwner());//拥有人
        tmpQjxx.setAssignee(rw.getAssignee());//签收人
        tmpQjxx.setPriority(rw.getPriority());//优先级
        tmpQjxx.setCreateTime(rw.getCreateTime());//创建时间
        tmpQjxx.setDueDate(rw.getDueDate());//到期时间
	  
		return tmpQjxx;
	}
	
	/**
	 * 
	 * @param qjxx
	 * @return
	 */
	@Override
	public List findSpyjByYhrwId(String yhrwId){
		List list = wfRwService.findSpyjByYhrwId(yhrwId);
	   
		return list;
	}
	
	/* 
	 * 我的代办
	 */
	@Override
	public List getUserDbTask(Qjxx qjxx){
		List<Qjxx> results = new ArrayList<Qjxx>();  
		 
		int firstResult = 1;
		int maxResults =10;
	    // 根据当前人的ID查询  
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("userId", qjxx.getUserId());
		map.put("rwlxDm", qjxx.getRwlxDm());
		List<WfRw> rws = wfRwService.getUserDbTaskByYwlx(map, firstResult, maxResults);   
		
	    // 根据流程的业务ID查询实体并关联  
	    for (WfRw rw : rws) {  
	    	Qjxx tmpQjxx = qjglDao.findById(rw.getYhrwId());
	    	tmpQjxx.setTaskId(rw.getTaskId());
	        tmpQjxx.setName(rw.getName());  //任务名称 
	        tmpQjxx.setProcDefId(rw.getProcDefId());//流程定义ID
	        tmpQjxx.setProcInsId(rw.getProcInsId());
	        tmpQjxx.setOwner(rw.getOwner());//拥有人
	        tmpQjxx.setAssignee(rw.getAssignee());//签收人
	        tmpQjxx.setPriority(rw.getPriority());//优先级
	        tmpQjxx.setCreateTime(rw.getCreateTime());//创建时间
	        tmpQjxx.setDueDate(rw.getDueDate());//到期时间
	    	
	        results.add(tmpQjxx);  
	    }  
	  
	    return results;  
	}
	
	/**
	 * 我的代办记录数
	 */
	@Override
	public long getUserDbTaskCount(Qjxx qjxx){
	    // 根据当前人的ID查询  
		long count = wfRwService.getUserDbTaskCount(qjxx.getUserId());   
	    return count;  
	}


	/**
	 * @param qjglDao the qjglDao to set
	 */
	public void setQjglDao(IQjglDao qjglDao) {
		this.qjglDao = qjglDao;
	}

	/**
	 * @param wfRwService the wfRwService to set
	 */
	public void setWfRwService(IWfRwService wfRwService) {
		this.wfRwService = wfRwService;
	}

	
	
}
