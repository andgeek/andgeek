package me.andgeek.develop.util;

import android.text.format.DateFormat;

/**
 * 描述： 时间工具
 * 
 * @creator Administrator
 * @create-time 2014-3-13 下午6:10:47
 * @revision 1.0
 */
public class TimeUtils {
    
    public static CharSequence format(long timestamp, String pattern) {
        String localform = pattern;
        if ("".equals(localform)) {
            localform = "yyyy-MM-dd HH:mm:ss";
        }
        return DateFormat.format(pattern, timestamp);
    }
    
}
