/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * BFOList的可序列化包装。
 * </pre>
 * @author luoshifei  luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ListHelper implements Externalizable{

    private static final long serialVersionUID = 1L;

    private List list;

    /**
     * list 中元素的个数。
     */
    private int objNum;

	/* (non-Javadoc)
	 * @see java.io.Externalizable#readExternal( java.io.ObjectInput) throws IOException, ClassNotFoundException
	 */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        list = new ArrayList();
        objNum = in.readInt();
        for (int i = 0; i < objNum; i++) {
            list.add(in.readObject());
        }
    }

    /* (non-Javadoc)
	 * @see java.io.Externalizable#readExternal( java.io.ObjectOutput) throws IOException
	 */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(objNum);
        for (int i = 0; i < list.size(); i++) {
            out.writeObject(list.get(i));
        }

    }

    /**
     * 获取list。
     * 
     * @return List
     */
    public List getList() {
        return list;
    }

    /**
     * 设置list。
     * 
     * @param list List
     */
    public void setList(List list) {
        this.list = list;
        objNum = list.size();
    }
    
}
