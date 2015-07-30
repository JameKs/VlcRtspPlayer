/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.qjgl.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.icanft.common.Converter.DateConverter;
import com.icanft.common.util.StringUtil;
import com.icanft.common.wf.rw.vo.WfSpyj;
import com.icanft.qjgl.service.IQjglService;
import com.icanft.qjgl.vo.Qjxx;
import com.icanft.xtgl.yhgl.vo.User;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
@Controller
@RequestMapping(value="/qjgl")
@SessionAttributes("user")
public class QjglController {
	
	private static final Logger log = Logger.getLogger(QjglController.class);
	
	@Resource
	private IQjglService qjglServiceImpl;
	
    @InitBinder  
    protected void initBinder(HttpServletRequest request,  
                                  ServletRequestDataBinder binder) throws Exception {  
        //对于需要转换为Date类型的属性，使用DateEditor进行处理  
        binder.registerCustomEditor(Date.class, new DateConverter());  
    }  
	/**
	 * 进入年假申请界面
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="njsq")
	public String njsq(ModelMap map , HttpServletRequest req){
		return "/admin/qjgl/njsq";
	}
	
	/**
	 * 进入年假列表界面
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="njlb")
	public String njlb(ModelMap map , HttpServletRequest req){
		return "/admin/qjgl/njlb";
	}
	
	/**
	 * 进入年假列表界面
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="njmx")
	public String njmx(ModelMap map, String id, HttpServletRequest req){
		Qjxx qj = qjglServiceImpl.findById(id);
		List spyj = qjglServiceImpl.findSpyjByYhrwId(id);
		map.put("qjxx", qj);
		map.put("spyjList", spyj);
		return "/admin/qjgl/njmx";
	}
	
	/**
	 * 保存年假申请业务
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="insert")
	@ResponseBody
	public String insert(ModelMap map, Qjxx qjxx, HttpServletRequest req, @ModelAttribute("user") User user){
		qjxx.setCjr(user.getLoginId());
		qjglServiceImpl.insert(qjxx);
		return "{\"success\":true,\"msg\":\"保存成功\"}";
	}
	
	/**
	 * 更新年假申请业务
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="update")
	@ResponseBody
	public String update(ModelMap map , Qjxx qjxx , HttpServletRequest req,  @ModelAttribute("user") User user){
		qjxx.setXgr(user.getLoginId());
		qjglServiceImpl.update(qjxx);
		return "{\"success\":true,\"msg\":\"更新成功\"}";
	}
	
	/**
	 * 查询未启动流程的业务
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="findList")
	@ResponseBody
	public String findList(ModelMap map , Qjxx qjxx , HttpServletRequest req,  @ModelAttribute("user") User user){
		qjxx.setUserId(user.getId());
		List list = qjglServiceImpl.getUserDbTask(qjxx);
		long count = qjglServiceImpl.getUserDbTaskCount(qjxx);
		String jsonString = StringUtil.pageListToJson(list, count);
		return jsonString;
	}
	
	/**
	 * 保存审核意见
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="insertOrUpdateSpyj")
	@ResponseBody
	public String insertOrUpdateSpyj(ModelMap map, WfSpyj spyj, HttpServletRequest req,  @ModelAttribute("user") User user){
		spyj.setUserId(user.getId());
		spyj.setCjr(user.getLoginId());
		spyj.setXgr(user.getLoginId());
		qjglServiceImpl.insertOrUpdateSpyj(spyj);
		return "{\"success\":true,\"msg\":\"提交成功\"}";
	}
	
	/**
	 * 提交年假申请业务并启动流程
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="insertYwAndRw")
	@ResponseBody
	public String insertNjsqYwQdlc(ModelMap map , Qjxx qjxx , HttpServletRequest req,  @ModelAttribute("user") User user){
		String userId = user.getId();
		qjxx.setUserId(userId);
		qjxx.setCjr(user.getLoginId());
		qjglServiceImpl.insertYwAndRw(qjxx);
		return "{\"success\":true,\"msg\":\"提交成功\"}";
	}
	
	/**
	 * 提交审核并进行下步流程
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="updateYwAndRw")
	@ResponseBody
	public String updateYwAndRw(ModelMap map, Qjxx qjxx,String spyj, HttpServletRequest req,  @ModelAttribute("user") User user){
		qjxx.setUserId(user.getId());
		qjxx.setXgr(user.getLoginId());
		qjglServiceImpl.updateYwAndRw(qjxx,spyj);
		return "{\"success\":true,\"msg\":\"提交成功\"}";
	}
	
	/**
	 * 查询代办流程
	 * @return
	 */
	@RequestMapping(value="njgl.do", params="getUserDbTask")
	@ResponseBody
	public String getUserDbTask(ModelMap map , Qjxx qjxx , HttpServletRequest req,  @ModelAttribute("user") User user){
		String userId = user.getId();
		qjxx.setUserId(userId);
		
		List list = qjglServiceImpl.getUserDbTask(qjxx);
		long count = qjglServiceImpl.getUserDbTaskCount(qjxx);
		String jsonString = StringUtil.pageListToJson(list, count);
		return jsonString;
	}

}
