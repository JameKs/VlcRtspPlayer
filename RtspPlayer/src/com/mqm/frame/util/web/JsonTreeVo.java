package com.mqm.frame.util.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 * 对ZTree进行传递数据的JSON类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class JsonTreeVo implements Serializable{

	private static final long serialVersionUID = 578985975678848208L;
	
	private String id;
	private String pId;
	private String name;
    private boolean open;
    private String click;
    private boolean checked;
    private Map<String,String> exts = new HashMap<String,String>(); 
	
	/**
	 * 默认的构造方法。
	 */
	public JsonTreeVo(){
		
	}
	
	/**
	 * 自定义的构造方法。
	 * 
	 * @param id 本节点的ID
	 * @param pId 父节点的ID
	 * @param name 本节点显示的名称
	 * @param open 标记是否展开
	 * @param click click后要触发的事件
	 */
	public JsonTreeVo(String id, String pId, String name, boolean open,
			String click) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.click = click;
	}

	/**
	 * 获取ID。
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置ID。
	 * 
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}
    /**
     * 获取父节点ID。
     * 
     * @return String
     */
	public String getpId() {
		return pId;
	}
    /**
     * 设置父节点ID。
     * 
     * @param pId String
     */
	public void setpId(String pId) {
		this.pId = pId;
	}
	/**
	 * 设置本节点显示的名称。
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取本节点显示的名称。
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取是否展开的标记。
	 * 
	 * @return boolean
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * 设置是否展开。
	 * 
	 * @param open boolean
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * 获取click事件绑定的函数。
	 * 
	 * @return String
	 */
	public String getClick() {
		return click;
	}

	/**
	 * 设置click事件绑定的函数。
	 * 
	 * @param click String
	 */
	public void setClick(String click) {
		this.click = click;
	}

	/**
	 * 获取是否选中状态。
	 * 
	 * @return boolean
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * 设置是否选中状态。
	 * 
	 * @param checked boolean
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * 获取exts。
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getExts() {
		return exts;
	}

	/**
	 * 设置exts。
	 * 
	 * @param exts Map<String, String>
	 */
	public void setExts(Map<String, String> exts) {
		this.exts = exts;
	}

}
