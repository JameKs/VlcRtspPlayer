/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class OFCException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    /**
     * 构建器。
     * 
     * @param t Throwable
     */
    public OFCException(Throwable t) {
        super(t);
    }
    
    /**
     * 构建器。
     * 
     * @param message String
     */
    public OFCException(String message) {
        super(message);
    }
    
    /**
     * 构建器。
     * @param message String 
     * @param t Throwable
     */
    public OFCException(String message, Throwable t) {
        super(message, t);
    }
    
}
