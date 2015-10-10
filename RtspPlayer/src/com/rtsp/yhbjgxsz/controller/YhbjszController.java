/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.yhbjgxsz.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.common.DefaultController;
import com.mqm.frame.common.Converter.DateConverter;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.constants.BaseConstants;
import com.rtsp.yhbjgxsz.service.IYhbjszService;
import com.rtsp.yhbjgxsz.vo.YhBjVO;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
@Controller
@RequestMapping(value="/yhbjsz")
public class YhbjszController extends DefaultController {
	
	private static final Logger log = Logger.getLogger(YhbjszController.class);
	
	@Resource
	private IYhbjszService yhbjszService;
	
    @InitBinder  
    protected void initBinder(HttpServletRequest request,  
                                  ServletRequestDataBinder binder) throws Exception {  
        //对于需要转换为Date类型的属性，使用DateEditor进行处理  
        binder.registerCustomEditor(Date.class, new DateConverter());  
    }

	/**
	 * 进入用户班级管理
	 * @return
	 */
	@RequestMapping(value="yhbjsz.do")
	public String njsq(ModelMap map , HttpServletRequest req){
		
		return "/front/yhbjsz/yhbjsz";
	}
	
	/**
	 * 查询用户班级信息列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="yhbjsz.do", params="find")
	public String find(ModelMap map , YhBjVO yhBjVo , HttpServletRequest req){
		List list = yhbjszService.findList(yhBjVo);
		int count = yhbjszService.findListCount(yhBjVo);
		
		return StringUtil.pageListToJson(list, count);
	}
	
	/**
	 * 进入用户选择班级界面(关系明细界面)
	 * @return
	 */
	@RequestMapping(value="yhbjsz.do", params="gxmx")
	public String gxmx(ModelMap map, YhBjVO yhBjVo , HttpServletRequest req){
		List list = yhbjszService.findList(yhBjVo);
		map.put("qjxx", list);
		return "/admin/qjgl/njmx";
	}
	
	/**
	 * 新增关系
	 * @return
	 */
	@RequestMapping(value="yhbjsz.do", params="insert")
	@ResponseBody
	public String insert(ModelMap map, YhBjVO yhBjVo , HttpServletRequest req){
		User user = this.getUser();
		yhBjVo.setCjr(user.getLoginId());
		yhbjszService.insert(yhBjVo);
		return BaseConstants.INSERT_SUCC;
	}
	
	/**
	 * 删除关系
	 * @return
	 */
	@RequestMapping(value="yhbjsz.do", params="deleteById")
	@ResponseBody
	public String deleteById(ModelMap map , YhBjVO yhBjVo , HttpServletRequest req){
		User user = this.getUser();
		yhBjVo.setXgr(user.getLoginId());
		yhbjszService.deleteById(yhBjVo.getId());
		return BaseConstants.DELETE_SUCC;
	}
	
}
