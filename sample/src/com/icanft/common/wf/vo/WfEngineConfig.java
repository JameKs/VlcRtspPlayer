/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.vo;

/**
 * <pre>
 * 引擎配置项类。
 * </pre>
 * 
 * @author liuyaping liuyaping@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class WfEngineConfig {
	
	private static final long serialVersionUID = -8724325191662744597L;
	/**
	 * 引擎配置项ID。
	 */
	private String id;
	/**
	 * 配置的KEY。
	 */
	private String templateName;
	/**
	 * 配置的名称。
	 */
	private String mainVersionNum;
	/**
	 * 配置的值。
	 */
	private String topic;
	/**
	 * 发起人。
	 */
	private String sponsor;
	/**
	 * 子节点。
	 */
	private String parentNodeId;
	/**
	 * 返回 id。
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 id。
	 * 
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 templateName。
	 * 
	 * @return String
	 */
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * 设置 templateName。
	 * 
	 * @param templateName String
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	/**
	 * 返回 mainVersionNum。
	 * 
	 * @return String
	 */
	public String getMainVersionNum() {
		return mainVersionNum;
	}
	/**
	 * 设置 mainVersionNum。
	 * 
	 * @param mainVersionNum String
	 */
	public void setMainVersionNum(String mainVersionNum) {
		this.mainVersionNum = mainVersionNum;
	}
	/**
	 * 返回 topic。
	 * 
	 * @return String
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * 设置 topic。
	 * 
	 * @param topic String
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	/**
	 * 返回 sponsor。
	 * 
	 * @return String
	 */
	public String getSponsor() {
		return sponsor;
	}
	/**
	 * 设置 sponsor。
	 * 
	 * @param sponsor String
	 */
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	/**
	 * 返回 parentNodeId。
	 * 
	 * @return String
	 */
	public String getparentNodeId() {
		return parentNodeId;
	}
	/**
	 * 设置 parentNodeId。
	 * 
	 * @param parentNodeId String
	 */
	public void setparentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	
}
