/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.base.vo;

import java.util.Date;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IAuditable {

	/**
	 * 获取creatorId。
	 * 
	 * @return String
	 */
    public String getCreatorId();

    /**
     * 设置creatorId。
     * 
     * @param creatorId String
     */
    public void setCreatorId(String creatorId);

    /**
     * 获取createdTime。
     * 
     * @return Date
     */
    public Date getCreatedTime();

    /**
     * 设置createdTime。
     * 
     * @param createdTime Date
     */
    public void setCreatedTime(Date createdTime);

    /**
     * 获取updatorId。
     * 
     * @return String
     */
    public String getUpdatorId();

    /**
     * 设置updatorId。
     * 
     * @param updatorId String
     */
    public void setUpdatorId(String updatorId );

    /**
     * 获取updatedTime。
     * 
     * @return Date
     */
    public Date getUpdatedTime();

    /**
     * 设置updatedTime。
     * 
     * @param updatedTime Date
     */
    public void setUpdatedTime(Date updatedTime);
}
