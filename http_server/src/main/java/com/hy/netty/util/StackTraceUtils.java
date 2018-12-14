/*
 * Decompiled with CFR 0_134.
 */
package com.hy.netty.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceUtils {
    public static String stackTrace(Throwable e) {
        if (e == null) {
            return "null";
        }
        StringWriter stringWriter = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(stringWriter);){
            e.printStackTrace(printWriter);
            String string = stringWriter.toString();
            return string;
        }
    }
}

