/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.bjgl.controller;

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

import com.mqm.frame.common.Converter.DateConverter;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.StringUtil;
import com.rtsp.bjgl.service.IBjglService;
import com.rtsp.bjgl.vo.Bj;

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
public class BjglController {
	
	private static final Logger log = Logger.getLogger(BjglController.class);
	
	@Resource
	private IBjglService bjglService;
	
    @InitBinder  
    protected void initBinder(HttpServletRequest request,  
                                  ServletRequestDataBinder binder) throws Exception {  
        //对于需要转换为Date类型的属性，使用DateEditor进行处理  
        binder.registerCustomEditor(Date.class, new DateConverter());  
    }  
    /**
	 * 进入摄像头管理界面
	 * @return
	 */
	@RequestMapping(value="sxt.do")
	public String main(ModelMap map , HttpServletRequest req){
		return "/front/sxtgl/sxt";
	}
	
	/**
	 * 查询摄像头信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="sxt.do", params="cx")
	public String cx(ModelMap map , Bj bj , HttpServletRequest req){
		List list = bjglService.findList(bj);
		int count = bjglService.findListCount(bj);
		
		return StringUtil.pageListToJson(list, count);
	}
	
	/**
	 * 新增摄像头
	 * @return
	 */
	@RequestMapping(value="sxt.do", params="xz")
	public String xz(ModelMap map, Bj bj , HttpServletRequest req){
		bjglService.insert(bj);
		return "{\"success\":true,\"msg\":\"新增成功\"}";
	}
	
	/**
	 * 保存年假申请业务
	 * @return
	 */
	@RequestMapping(value="sxt.do", params="xg")
	@ResponseBody
	public String xg(ModelMap map, Bj bj , HttpServletRequest req, @ModelAttribute("user") User user){
		bj.setCjr(user.getLoginId());
		bjglService.update(bj);
		return "{\"success\":true,\"msg\":\"修改成功\"}";
	}
	
	/**
	 * 更新年假申请业务
	 * @return
	 */
	@RequestMapping(value="sxt.do", params="sc")
	@ResponseBody
	public String sc(ModelMap map , Bj bj , HttpServletRequest req,  @ModelAttribute("user") User user){
		bj.setXgr(user.getLoginId());
		bjglService.delete(bj);
		return "{\"success\":true,\"msg\":\"更新成功\"}";
	}
	
}
