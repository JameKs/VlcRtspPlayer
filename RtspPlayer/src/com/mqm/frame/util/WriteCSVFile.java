/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
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
public class WriteCSVFile {
	
	private static final Log log = LogFactory.getLog(WriteCSVFile.class);

    /** 下载文字的编码. */
    private static final String CSVWRITER_CHARSET = "CSVWRITER_CHARSET";

    /** Writer 实例对象. */
    private OutputStreamWriter in = null;

    /** StreamTokenizer. */
    private StreamTokenizer parser;

    /** separator文字. */
    private char separator;

    /** quote文字. */
    private char quote;

    /** escape文字. */
    private char escape;

    /** 文字code. */
    private String charsetName = "GBK";

    /** 每列是否设定quote文字的判断标志位. */
    private boolean[] hasQuoteFlags;

    /** 每列是否设定escape文字的判断标志位. */
    private boolean[] hasEscapeFlags;

    /**
     * Constructor. <BR>
     * 写CSV文件。
     * 
     * @param valueFile 指定文件
     * @param valueSeparator 指定的Separator文字
     * @param valueQuote 指定的Quote文字
     * @param valueEscape 指定的Escape文字
     * @param valueAppend 指定新的/追加
     * 
     * @throws Exception 异常
     */
    public WriteCSVFile(
            File valueFile,
            char valueSeparator,
            char valueQuote,
            char valueEscape,
            boolean valueAppend) throws Exception {
        this(valueFile, valueSeparator, valueQuote, valueEscape, valueAppend, null);
    }

    /**
     * Constructor. <BR>
     * 写CSV文件。
     * 
     * @param valueFile 指定文件
     * @param valueSeparator 指定的Separator文字
     * @param valueQuote 指定的Quote文字
     * @param valueEscape 指定的Escape文字
     * @param valueAppend 指定新的/追加
     * @param valueCharsetName 指定文字code
     * 
     * @throws Exception 异常
     */
    public WriteCSVFile(
            File valueFile,
            char valueSeparator,
            char valueQuote,
            char valueEscape,
            boolean valueAppend,
            String valueCharsetName) throws Exception {
        this.separator = valueSeparator;
        this.quote = valueQuote;
        this.escape = valueEscape;
        if (valueCharsetName != null) {
            this.charsetName = valueCharsetName;
        }

        try {
            if (charsetName != null) {
                in = new OutputStreamWriter(new FileOutputStream(valueFile, valueAppend), this.charsetName);
            }
            else {
                in = new OutputStreamWriter(new FileOutputStream(valueFile, valueAppend));
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage(), e);
        }
        catch (FileNotFoundException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * 关闭流.
     * 
     * @throws Exception 异常
     */
    public void close() throws Exception {

        if (in != null) {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new Exception(e.getMessage(), e);
            }
            in = null;
        }
    }

    /**
     * 用String数据写csv文件。
     * 
     * @param columns String数组
     * @throws Exception 异常
     *  
     */
    public void write(String[] columns) throws Exception {

        if (columns == null) {
            log.error("The input parameter is null.");
            return;
        }
        StringBuffer line = new StringBuffer();

        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            boolean hasQuote = getItemFlags(this.hasQuoteFlags, i);
            boolean hasNoQuote = (this.hasQuoteFlags == null);
            boolean hasEscape = getItemFlags(this.hasEscapeFlags, i);

            StringBuffer newColumn = new StringBuffer();
            boolean addQuote = false;

            if (column != null) {
                int len = column.length();
                int p = -1;
                while (++p < len) {
                    char chr = column.charAt(p);
                    if (hasNoQuote && (chr == quote)) {
                        if (!addQuote) {
                            addQuote = true;
                        }
                        newColumn.append(quote);
                    }
                    else if (chr == separator) {
                        if (!addQuote) {
                            addQuote = true;
                        }
                    }
                    else if (hasEscape && (chr == escape)) {
                        newColumn.append(escape);
                    }
                    newColumn.append(chr);
                }
            }

            if ((hasNoQuote && addQuote) || (!hasNoQuote && hasQuote)) {
                line.append(quote).append(newColumn).append(quote);
            }
            else {
                line.append(newColumn);
            }

            if (i < columns.length - 1) {
                line.append(separator);
            }
        }

        try {
            write(line.toString());
        }
        catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        }

    }

    /**
     * 追加改行文字。
     * 
     * @throws Exception 异常
     *  
     */
    public void newLine() throws Exception {
    	
        try {
            write(System.getProperty("line.separator"));
        }
        catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * 
     * 写文字列。
     * 
     * @param value 文字列
     * @return result
     * @throws IOException 异常
     * @throws Exception 异常
     */
    private boolean write(String value) throws IOException, Exception {

        if (value == null) {
            return false;
        }

        if (in != null) {
            in.write(value);
            return true;
        }

        return false;
    }

    /**
     * 
     * 每列的Quote文字设定。 <BR>
     * true:有 false:无
     * 
     * @param quoteflags quoteflags[]
     */
    public void setQuoteFlags(boolean[] quoteflags) {
        if (quoteflags != null) {
            this.hasQuoteFlags = (boolean[])quoteflags.clone();
        }
        else {
            this.hasQuoteFlags = null;
        }
    }

    /**
     * 每列的escape文字设定。<BR>
     * true:有 false:无。
     * 
     * @param escapeflags escapeflags[]
     */
    public void setEscapeFlags(boolean[] escapeflags) {
        if (escapeflags != null) {
            this.hasEscapeFlags = (boolean[])escapeflags.clone();
        }
        else {
            this.hasEscapeFlags = null;
        }
    }

    /**
     * 
     * 取得每列设定的Quote标志位。<BR>
     * 如果没有设定返回0个数组。
     * 
     * @return hasQuoteFlags 
     */
    public boolean[] getQuoteFlags() {
        if (this.hasQuoteFlags == null) {
            return new boolean[0];
        }
        else {
            return this.hasQuoteFlags;
        }
    }

    /**
     * 
     * 取得每列设定的escape标志位。<BR>
     * 如果没有设定返回0个数组。
     * 
     * @return hasEscapeFlags 
     */
    public boolean[] getEscapeFlags() {
        if (this.hasEscapeFlags == null) {
            return new boolean[0];
        }
        else {
            return this.hasEscapeFlags;
        }
    }

    /**
     * 
     * 每列的处理有无判断。 <BR>
     * 
     * @param itemNo itemNo
     * @param flags flags
     * @return boolean 
     */
    private boolean getItemFlags(boolean[] flags, int itemNo) {
        if (flags == null) {
            return true;
        }
        else {
            return flags[itemNo];
        }
    }
}
