package com.mqm.frame.common.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.mqm.frame.util.constants.BaseConstants;

public class MyExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = Logger
			.getLogger(MyExceptionHandler.class);

	private static final String AJAX_RESULT_NAME = "ajaxResult";
	private static final String XHR_OBJECT_NAME = "XMLHttpRequest";
	private static final String HEADER_REQUEST_WITH = "x-requested-with";

	@Override
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("异常捕获", ex);
		
		//判断是否用户未登录
//		User sessionUser = (User)req.getSession().getAttribute("user");
//		if(sessionUser == null){
//			ModelAndView modelAndView = new ModelAndView("forward:/");
//			return modelAndView;
//		}
		
		boolean isajax = false;
		if (XHR_OBJECT_NAME.equals(req.getHeader(HEADER_REQUEST_WITH))) {
			isajax = true;
		}

		// String requestURI = request.getRequestURI();
		// String fileExtName = StringUtils.getFileExtName(requestURI);
		// boolean isajax = "ajax".equals(fileExtName);
		
		response.setCharacterEncoding("UTF-8");

//		int responseCode = 500;
//
//		response.setStatus(responseCode);

		if (!isajax) {
			ModelAndView modelAndView = new ModelAndView("errorMassagePage");
			modelAndView.addObject("ex", ex);
			return modelAndView;
		}

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.write(BaseConstants.CZ_ERROR);
		writer.flush();
		return null;
	}

}