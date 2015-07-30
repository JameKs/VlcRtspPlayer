/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.role.vo;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * <pre>
 * 主要用于页面传参的VO。
 * </pre>
 * 
 * @author lijiawei lijiawei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FbrpSecParams extends ValueObject {

	private static final long serialVersionUID = 8205830841294043771L;

	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

}
