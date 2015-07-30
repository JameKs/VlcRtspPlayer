/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * Title:       序列化文件读取和写入操作类。
 * Description: 序列化文件读取和写入操作类。
 * </pre>
 * @author duanpeng rtao_duanpeng/xmkfzx@xmkfzx
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ReadRes {

    /***************************************************************************
     * 向文件中写入一个集合
     * 流程：
     * 1.遍历BFOList集合并将其每个对象序列化
     * 2.通过对象流将逐个对象序列写入文件中。
     * 
     * @param   fileppath   要写入文件的路径
     * 
     * @param   list        要写入的集合 
     * 
     * @throws  Exception   文件没有找到或创建对象流失败异常
     */
    public static void write(List list,String fileppath)throws Exception{
        ObjectOutputStream os = null;
        FileOutputStream fs = null;
        try {
            List blist;
            fs = new FileOutputStream(fileppath);
            os = new ObjectOutputStream(fs);
            //ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileppath));
            int size = list.size();
            os.writeInt(size);
            for(Iterator iter = list.iterator();iter.hasNext();){
                ListHelper bh = new ListHelper();
                blist = (List) iter.next();
                bh.setList(blist);
                os.writeObject(bh);
            }  
        }catch (Exception e) {
           throw e;
        }finally{
            if(os!=null){
                os.close();
            }
            if(fs!=null){
                fs.close();
            }
        }
    }
    
    /***************************************************************************
     * 向文件中写入一个集合。
     * 
     * @param   list        要写入的集合 
     * @param   filepath   要写入文件的路径
     * 
     * @throws  IOException   文件没有找到或创建对象流失败异常
     */
    public static void write2(List list,String filepath)throws IOException{
        ObjectOutputStream os = null;
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(filepath);
            os = new ObjectOutputStream(fs);
            int size = list.size();
            os.writeInt(size);
            for(Iterator iter = list.iterator();iter.hasNext();){
                List voList = (List) iter.next();
                os.writeObject(voList);
            }  
        }catch (IOException e) {
           throw e;
        }finally{
            if(os!=null){
                os.close();
            }
            if(fs!=null){
                fs.close();
            }
        }
    }
    
    /***************************************************************************
     * 从文件中读取一个集合
     * 流程：
     * 1.通过对象流读取文件
     * 2.遍历BFOList集合通过BFOListHelper类将逐个对象反序列化。
     * 
     * @param   fileppath   要写入文件的路径
     * 
     * @return  list        读取文件得到的集合
     * 
     * @throws  Exception   文件没有找到或创建对象流失败异常
     */
    public static List readList(String fileppath)throws Exception{
        List list = new ArrayList();
        FileInputStream fis =null;
        ObjectInputStream is = null;
        try {
            fis = new FileInputStream(fileppath);
            is = new ObjectInputStream(fis);
            //ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileppath));
            ListHelper bh;
            int size = is.readInt();
            for(int i = 0; i < size; i++){
                bh = (ListHelper) is.readObject();
                list.add(bh.getList());
            }
        }
        catch (Exception e) {
           throw e;
        }finally{
            if(fis!=null){
                fis.close();
            }
            if(is!=null){
                is.close();
            }
        }
        return list;
    }
    /***************************************************************************
     * 从文件中读取一个集合
     * 流程：
     * 1.通过对象流读取文件为了避免远程读取文件使用流的方式读取
     * 2.遍历BFOList集合通过BFOListHelper类将逐个对象反序列化。
     * 
     * @param   inputStream   要读取文件流
     * 
     * @return  list          读取文件得到的集合
     * 
     * @throws  Exception     文件没有找到或创建对象流失败异常
     */
    public static List readList(InputStream inputStream)throws Exception{
        List list = new ArrayList();
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(inputStream);
            //ObjectInputStream in = new ObjectInputStream(inputStream);
            ListHelper bh;
            int size = in.readInt();
            for(int i = 0; i < size; i++){
                bh = (ListHelper) in.readObject();
                list.add(bh.getList());
            }
        }catch(Exception e){
            throw e;
        }
        finally{
            if(in!=null){
                in.close();
            }
        }
        return list;    
    }
}
