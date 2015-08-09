/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

/**
 * <pre>
 * 树型结点接口。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
// TODO xiaocheng_lu 该接口可能需要重新放一个包下
public interface IFbrpTree {
	/**
	 * 得到唯一标识。
	 * 
	 * @return 标识
	 */
	public String getId();

	/**
	 * 得到名称。
	 * 
	 * @return 节点名称
	 */
	public String getName();

	/**
	 * 得到父结点的唯一标识。
	 * 
	 * @return 父结点的唯一标识
	 */
	public String getParentId();
}
