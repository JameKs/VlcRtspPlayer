/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.jofc2;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.json.JsonWriter;

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
public class NullAwareJsonWriter extends JsonWriter {

	/**
	 * 构建器。
	 * 
	 * @param writer Writer
	 * @param lineIndenter char[]
	 * @param newLine String
	 * @param mode int
	 */
	public NullAwareJsonWriter(Writer writer, char[] lineIndenter, String newLine, int mode) {
		super(writer, lineIndenter, newLine, mode);
	}

	/**
	 * 构建器。
	 * 
	 * @param writer Writer
	 * @param lineIndenter char[]
	 * @param newLine String
	 */
	public NullAwareJsonWriter(Writer writer, char[] lineIndenter, String newLine) {
		super(writer, lineIndenter, newLine);
	}

	/**
	 * 构建器。
	 * 
	 * @param writer Writer
	 * @param lineIndenter char[]
	 */
	public NullAwareJsonWriter(Writer writer, char[] lineIndenter) {
		super(writer, lineIndenter);
	}

	/**
	 * 构建器。
	 * 
	 * @param writer Writer
	 * @param mode int
	 */
	public NullAwareJsonWriter(Writer writer, int mode) {
		super(writer, mode);
	}

	/**
	 * 构建器。
	 * 
	 * @param writer Writer
	 * @param lineIndenter String
	 * @param newLine String
	 */
	public NullAwareJsonWriter(Writer writer, String lineIndenter, String newLine) {
		super(writer, lineIndenter, newLine);
	}

	/**
	 * 构建器。
	 * 
	 * @param writer Writer
	 * @param lineIndenter String
	 */
	public NullAwareJsonWriter(Writer writer, String lineIndenter) {
		super(writer, lineIndenter);
	}

	/**
	 * 构建器。
	 * 
	 * @param writer Writer
	 */
	public NullAwareJsonWriter(Writer writer) {
		super(writer);
	}
	
	protected void writeText(String text) {
        int length = text.length();
        for (int i = 0; i < length; i++ ) {
            char c = text.charAt(i);
            switch (c) {
            case '"':
                this.writer.write("\\\"");
                break;
            case '\\':
                this.writer.write("\\\\");
                break;
            // turn this off - it is no CTRL char anyway
            // case '/':
            // this.writer.write("\\/");
            // break;
            case '\b':
                this.writer.write("\\b");
                break;
            case '\f':
                this.writer.write("\\f");
                break;
            case '\n':
                this.writer.write("\\n");
                break;
            case '\r':
                this.writer.write("\\r");
                break;
            case '\t':
                this.writer.write("\\t");
                break;
            default:
                if (c > 0x1f) {
                    this.writer.write(c);
                } else {
                    this.writer.write("\\u");
                    String hex = "000" + Integer.toHexString(c);
                    this.writer.write(hex.substring(hex.length() - 4));
                }
            }
        }
    }

}
