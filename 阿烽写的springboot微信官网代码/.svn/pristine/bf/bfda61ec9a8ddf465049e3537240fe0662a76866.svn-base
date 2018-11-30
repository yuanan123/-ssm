package com.kingtechfin.wxthirdparty.util;

import jdk.internal.dynalink.beans.StaticClass;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mrchao on 2018/6/5.
 */
public class MyUtil {
   public static String getMethodName(int layer, String prefix) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[layer + 1];
        String methodName = e.getMethodName();
        return prefix + methodName;
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrenTimeByLine() {
        return getCurrentTime("yyyy-MM-dd-HH-mm-ss-S");
    }

    public static String getCustomizedDate (String format, Long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String result = simpleDateFormat.format(date);
        return result;
    }

    public static Long get10CurrentTime() {
        return System.currentTimeMillis()/1000;
    }

    private static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        //从前端或者自己模拟一个日期格式，转为String即可
        return formatDate.format(date);
    }


}
