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

import com.mqm.frame.common.DefaultController;
import com.mqm.frame.common.Converter.DateConverter;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;
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
@RequestMapping(value="/bj")
public class BjglController extends DefaultController {
	
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
	@RequestMapping(value="bj.do")
	public String main(ModelMap map , HttpServletRequest req){
		return "/front/sxtgl/sxt";
	}
	
	/**
	 * 查询摄像头信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="bj.do", params="findList")
	public String findList(ModelMap map , Bj bj , HttpServletRequest req){
		List list = bjglService.findList(bj);
		int count = bjglService.findListCount(bj);
		
		return StringUtil.pageListToJson(list, count);
	}
	
	/**
	 * 新增摄像头
	 * @return
	 */
	@RequestMapping(value="bj.do", params="insert")
	@ResponseBody
	public String insert(ModelMap map, Bj bj , HttpServletRequest req){
		User user = this.getUser();
		bj.setCjr(user.getLastLoginIp());
		bjglService.insert(bj);
		return BaseConstants.INSERT_SUCC;
	}
	
	/**
	 * 保存年假申请业务
	 * @return
	 */
	@RequestMapping(value="bj.do", params="update")
	@ResponseBody
	public String update(ModelMap map, Bj bj , HttpServletRequest req){
		User user = this.getUser();
		bj.setCjr(user.getLoginId());
		bjglService.update(bj);
		return BaseConstants.UPDATE_SUCC;
	}
	
	/**
	 * 更新年假申请业务
	 * @return
	 */
	@RequestMapping(value="bj.do", params="deleteById")
	@ResponseBody
	public String deleteById(ModelMap map , Bj bj , HttpServletRequest req){
		User user = this.getUser();
		bj.setXgr(user.getLoginId());
		bjglService.deleteById(bj.getId());
		return BaseConstants.DELETE_SUCC;
	}
	
	@RequestMapping(value="bj.do" , params="deleteByIds")
	@ResponseBody
	public String deleteByIds(String ids , ModelMap map , HttpServletRequest req){
		String[] temIds = ids.split(",");
		bjglService.deleteByIds(temIds);
		return BaseConstants.DELETE_SUCC;
	}
	
	@RequestMapping(value="bj.do" , params="findById")
	public Bj findById(String id , ModelMap map , HttpServletRequest req){
		Bj bj = (Bj)bjglService.findById(id);
		map.put("user", bj );
		return bj;
	}
	
	
}
