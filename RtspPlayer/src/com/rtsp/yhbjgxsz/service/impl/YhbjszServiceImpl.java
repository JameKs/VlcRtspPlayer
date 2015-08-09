/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.yhbjgxsz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
public class YhbjszServiceImpl implements IYhbjszService {
	
	private static final Logger log = Logger.getLogger(YhbjszServiceImpl.class);
	
	private IYhbjszDao yhbjszDao;
	
	@Override
	public void insert(YhBjVo yhBjVo){
		yhbjszDao.insert(yhBjVo);
	}

	@Override
	public void delete(YhBjVo yhBjVo){
		yhbjszDao.delete(yhBjVo);
	}
	
	@Override
	public List findList(YhBjVo yhBjVo){
		List qj = yhbjszDao.findList(yhBjVo);
		return qj;
	}

	@Override
	public int findListCount(YhBjVo yhBjVo){
		int count = yhbjszDao.findListCount(yhBjVo);
		return count;
	}
	
}
