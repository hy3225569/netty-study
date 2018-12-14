/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  org.apache.commons.lang.StringUtils
 */
package com.hy.netty;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Formatter;

public class Constants {
    //字符编码
    public static final String UTF8_CHARSET = "UTF-8";
    public static final Charset UTF8;
    //换行标识
    public static final String NEWLINE;
    //返回可用处理器的数量,应该和多核处理器有关
    public static final int AVAILABLE_PROCESSORS;
    //默认io线程数目
    public static final int DEFAULT_IO_THREADS;

    static {
        String newLine;
        Charset charset = null;
        try {
            charset = Charset.forName(UTF8_CHARSET);
        }
        catch (UnsupportedCharsetException unsupportedCharsetException) {
            // empty catch block
        }
        UTF8 = charset;
        try {
            newLine = new Formatter().format("%n").toString();
        }
        catch (Exception e) {
            newLine = "\n";
        }
        NEWLINE = newLine;
        DEFAULT_IO_THREADS = AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    }

}

