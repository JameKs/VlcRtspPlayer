/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.yhbjgxsz.service.impl;

import org.apache.log4j.Logger;

import com.mqm.frame.common.DefaultServiceImpl;
import com.rtsp.yhbjgxsz.dao.IYhbjszDao;
import com.rtsp.yhbjgxsz.service.IYhbjszService;
import com.rtsp.yhbjgxsz.vo.YhBjVo;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class YhbjszServiceImpl extends DefaultServiceImpl<YhBjVo>  implements IYhbjszService<YhBjVo> {
	
	private static final Logger log = Logger.getLogger(YhbjszServiceImpl.class);
	
	private IYhbjszDao yhbjszDao;

	/**
	 * @param yhbjszDao the yhbjszDao to set
	 */
	public void setYhbjszDao(IYhbjszDao yhbjszDao) {
		this.yhbjszDao = yhbjszDao;
	}
	
	
	
	
	
}
