/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * CSV文件读入类。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ReadCSVFile {
	
	private static final Log log = LogFactory.getLog(ReadCSVFile.class);

    /** Reader instance。 */
    private InputStreamReader in;

    /** BufferedReader。 */
    private BufferedReader parser;

    /** Separate文字。 */
    private char separator;

    /** quote文字。 */
    private char quote;

    /** escape文字。 */
    private char escape;

    /** 下载文字的编码。 */
    private static final String CSVREADER_CHARSET = "CSVREADER_CHARSET";

    /** 文字编码。 */
    private String charsetName = "GBK";

    /** 禁止文字是否检查标志位。 */
    private boolean prohibitedCharCheck = true;

    /** 禁止文字检查类。 */
    private ProhibitedCharCheck prohibitedCharChecker;

    /** 每列是否设定quote文字的判断标志位。 */
    private boolean[] hasQuoteFlags;

    /** 每列是否设定escape文字的判断标志位。 */
    private boolean[] hasEscapeFlags;

    /**
     * Constructor。 <BR>
     * CSV文件读入。
     * 
     * @param valueFile 指定的文件
     * @param valueSeparator 指定的Separator文字
     * @param valueQuote 指定的Quote文字
     * @param valueEscape 指定的Escape文字
     * 
     * @throws Exception 异常
     */
    public ReadCSVFile(
            File valueFile,
            char valueSeparator,
            char valueQuote,
            char valueEscape) throws Exception {
        this(valueFile, valueSeparator, valueQuote, valueEscape, null);
    }

    /**
     * Constructor。 <BR>
     * CSV文件读入。
     * 
     * @param valueFile 指定的文件
     * @param valueSeparator 指定的Separator文字
     * @param valueQuote 指定的Quote文字
     * @param valueEscape 指定的Escape文字
     * @param valueCharsetName 指定的文字格式
     * 
     * @throws Exception 异常
     *  
     */
    public ReadCSVFile(
            File valueFile,
            char valueSeparator,
            char valueQuote,
            char valueEscape,
            String valueCharsetName) throws Exception {
        this.separator = valueSeparator;
        this.quote = valueQuote;
        this.escape = valueEscape;
        if (valueCharsetName != null) {
            this.charsetName = valueCharsetName;
        }

        try {
            if (this.charsetName == null) {
                in = new InputStreamReader(new FileInputStream(valueFile));
            }
            else {
                in = new InputStreamReader(new FileInputStream(valueFile), this.charsetName);
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage(), e);
        }
        catch (FileNotFoundException e) {
            throw new Exception(e.getMessage(), e);
        }

        parser = new BufferedReader(in);
    }

    /**
     * Constructor。 <BR>
     * CSV文件读入。
     * 
     * @param is InputStream
     * @param valueSeparator 指定的Separator文字
     * @param valueQuote 指定的Quote文字
     * @param valueEscape 指定的Escape文字
     * 
     * @throws Exception 异常
     *  
     */
    public ReadCSVFile(
            InputStream is,
            char valueSeparator,
            char valueQuote,
            char valueEscape) throws Exception {
        this(is, valueSeparator, valueQuote, valueEscape, null);
    }

    /**
     * Constructor。 <BR>
     * CSV文件读入。
     * 
     * @param is InputStream
     * @param valueSeparator 指定的Separator文字
     * @param valueQuote 指定的Quote文字
     * @param valueEscape 指定的Escape文字
     * @param valueCharsetName 指定的文字格式
     * 
     * @throws Exception 异常
     *  
     */
    public ReadCSVFile(
            InputStream is,
            char valueSeparator,
            char valueQuote,
            char valueEscape,
            String valueCharsetName) throws Exception {
        this.separator = valueSeparator;
        this.quote = valueQuote;
        this.escape = valueEscape;
        if (valueCharsetName != null) {
            this.charsetName = valueCharsetName;
        }

        try {
            if (this.charsetName == null) {
                in = new InputStreamReader(is);
            }
            else {
                in = new InputStreamReader(is, this.charsetName);
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage(), e); 
        }
        parser = new BufferedReader(in);
    }

    /**
     * 关闭Input stream。
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
     * 逐行加载实例化的对象。 <BR>
     * String数组对应每列的值。 <BR>
     * 如果此行没有数据返回NULL。
     * 
     * @return String数组
     * 
     * @throws Exception 异常
     */
    public String[] readLine() throws Exception {

        String line;
        try {
            line = parser.readLine();
        }
        catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        }

        if (prohibitedCharCheck) {
            if (prohibitedCharChecker == null) {
                prohibitedCharChecker = new ProhibitedCharCheck();
            }
            if (prohibitedCharChecker.isProhibited(line)) {
                //错误信息取得
                char prohibitedChar = prohibitedCharChecker.getFoundChar();
                log.error(String.valueOf(prohibitedChar));
                return null;
            }
        }

        return analysize(line);
    }

    /**
     * 
     * 设定禁止文字判断标志位。<BR>
     * true:执行禁止文字判断（默认）。<BR>
     * false:不执行禁止文字判断。
     * 
     * @param chkFlg chkFlg
     */
    public void setCharCheckFlg(boolean chkFlg) {
        this.prohibitedCharCheck = chkFlg;
    }

    /**
     * 
     * 只有与默认的禁止文字不同的场合设定。
     * 
     * @param checker checker
     */
    public void setChecker(ProhibitedCharCheck checker) {
        if (checker != null) {
            this.prohibitedCharChecker = checker;
        }
    }

    /**
     * 
     * 返回检查完后的文字列。
     * 
     * @return prohibitedCharChecker
     */
    public ProhibitedCharCheck getChecker() {
        return this.prohibitedCharChecker;
    }

    /**
     * 读取CSV每行的文字。
     * 
     * @param line CSV文件1行的String
     * 
     * @return String数组
     */
	private String[] analysize(String line) {

        if (line == null || line.length() == 0) {
            log.error("analyze line is null or the length is 0.");
            return null;
        }
        int len = line.length();
        int i;

        List<String> columns = new ArrayList<String>();

        StringBuffer column = new StringBuffer();
        boolean hasQuote = getItemFlags(this.hasQuoteFlags, columns.size());
        boolean hasEscape = getItemFlags(this.hasEscapeFlags, columns.size());
        boolean columnStart = true;
        boolean quoteStart = false;
        char chr, chrNext;
        boolean gonext = false;

        for (i = 0; i < len - 1; i++) {

            if (gonext) {
                gonext = false;
                continue;
            }

            chr = line.charAt(i);
            chrNext = line.charAt(i + 1);

            if (hasQuote && (chr == quote)) {
                if (columnStart) {
                    quoteStart = true;
                }
                else {
                    if (quoteStart) {
                        if (chrNext == quote) {
                            //以quote开始的列的文字累加
                            column.append(quote);
                            gonext = true;
                        }
                        else if (chrNext == separator) {
                            //列的开始和最后加上quote
                            quoteStart = false;
                        }
                        else {
                            //必须以quote+quote格式，否则视为错误
                            String errMessage = "ERROR Line : quote只出现一次。";
                            log.error(errMessage);
                        }
                    }
                    else {
                        column.append(quote);
                    }
                }
            }
            else if (chr == separator) {
                if (quoteStart) {
                    //separator更能丧失
                    column.append(separator);
                }
                else {
                    //根据separator列来分离
                    columns.add(column.toString());
                    column = new StringBuffer();
                    hasQuote = getItemFlags(this.hasQuoteFlags, columns.size());
                    hasEscape = getItemFlags(this.hasEscapeFlags, columns.size());
                    columnStart = true;
                    continue;
                }
            }
            else if (hasEscape && (chr == escape)) {
                if (chrNext == escape) {
                    //escape文字+escape文字
                    //取得escape文字
                    column.append(escape);
                }
                else {
                    column.append(chrNext);
                }
                gonext = true;
            }
            else {
                column.append(chr);
            }

            if (columnStart) {
                columnStart = false;
            }
        }

        //此行的结束文字处理
        if (!gonext && (i < len)) {
            chr = line.charAt(i);
            //列的开始和结束列文字
            if (hasQuote && (chr == quote) && quoteStart) {
            }
            else if (chr == separator) {

                if (quoteStart) {
                    String errMessage = "ERROR Line：qoute开始有、qoute结束有。";
                    log.error(errMessage);
                }

                columns.add(column.toString());
                column = new StringBuffer();
                hasQuote = getItemFlags(this.hasQuoteFlags, columns.size());
                hasEscape = getItemFlags(this.hasEscapeFlags, columns.size());

            }
            else if (hasEscape && (chr == escape)) {
                column.append(chr);
                /*
                 * "\"不允许。
                 */
                String errMessage = "ERROR Line : \\出现在最后。";
                log.error(errMessage);
            }
            else {
                column.append(chr);
            }
        }

        columns.add(column.toString());

        return (String[])columns.toArray(new String[0]);
    }

    /**
     * 
     * 每列的Quote文字设定 <BR>
     * true:有 false:无。
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
     * 
     * 每列的escape文字设定 <BR>
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
     * 取得每列设定的Quote标志位 <BR>
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
     * 取得每列设定的escape标志位 <BR>
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
     * 每列的处理有无判断。
     * 
     * @param itemNo itemNo
     * @param flags flags
     * 
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
