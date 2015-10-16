package me.andgeek.develop.util;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 描述： 用于Log输出
 * 
 * @create-time 2014-3-13 下午6:10:10
 * @revision 1.0
 */
@SuppressLint({ "InlinedApi", "UseSparseArrays" })
public final class LogUtils {
    
    private static final String TAG = LogUtils.class.getSimpleName();
    
    private static final boolean DEBUG = true;
    
    /**
     * Conceal ctor to avoid creating instance.
     */
    private LogUtils() {
    }
    
    /**
     * @description v级别log
     * @date 2015年8月17日
     * @param log
     *            输出信息
     */
    public static void v(String log) {
        print(log, Log.VERBOSE);
    }
    
    /**
     * @description d级别log
     * @date 2015年8月17日
     * @param log
     *            输出信息
     */
    public static void d(String log) {
        print(log, Log.DEBUG);
    }
    
    /**
     * @description i级别log
     * @date 2015年8月17日
     * @param log
     *            输出信息
     */
    public static void i(String log) {
        print(log, Log.INFO);
    }
    
    /**
     * @description w级别log
     * @date 2015年8月17日
     * @param log
     *            输出信息
     */
    public static void w(String log) {
        print(log, Log.WARN);
    }
    
    /**
     * @description e级别log
     * @date 2015年8月17日
     * @param log
     *            输出信息
     */
    public static void e(String log) {
        print(log, Log.ERROR);
    }
    
    private static void print(String log, int level) {
        if (!DEBUG) {
            return;
        }
        
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        int currentIndex = -1;
        String methodName = "";
        for (int i = 0; i < stackTraceElement.length; i++) {
            methodName = stackTraceElement[i].getMethodName();
            if (methodName.compareTo("v") == 0 || methodName.compareTo("d") == 0
                || methodName.compareTo("i") == 0
                || methodName.compareTo("w") == 0
                || methodName.compareTo("e") == 0
                || methodName.compareTo("a") == 0) {
                currentIndex = i + 1;
                break;
            }
        }
        
        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String lineNumber = String.valueOf(stackTraceElement[currentIndex].getLineNumber());
        
        String formatLog = "at " + fullClassName
                           + "."
                           + methodName
                           + "("
                           + className
                           + ".java:"
                           + lineNumber
                           + ") \n"
                           + log;
        switch (level) {
            case Log.VERBOSE:
                Log.v(TAG, formatLog);
                break;
            case Log.DEBUG:
                Log.d(TAG, formatLog);
                break;
            case Log.INFO:
                Log.i(TAG, formatLog);
                break;
            case Log.WARN:
                Log.w(TAG, formatLog);
                break;
            case Log.ERROR:
                Log.e(TAG, formatLog);
                break;
            default:
                break;
        }
    }
    
    // //////////////////////////////////Intent
    // Log///////////////////////////////////////////////////////////////////////////////////////
    // IntentLogger.dump("test", getIntent());
    private static final HashMap<Integer, String> FLAGS = new HashMap<Integer, String>();
    
    static {
        FLAGS.put(Intent.FLAG_ACTIVITY_CLEAR_TASK, "FLAG_ACTIVITY_CLEAR_TASK");
        FLAGS.put(Intent.FLAG_ACTIVITY_SINGLE_TOP, "FLAG_ACTIVITY_SINGLE_TOP");
        FLAGS.put(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT, "FLAG_ACTIVITY_BROUGHT_TO_FRONT");
        FLAGS.put(Intent.FLAG_ACTIVITY_CLEAR_TOP, "FLAG_ACTIVITY_CLEAR_TOP");
        FLAGS.put(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS, "FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS");
        FLAGS.put(Intent.FLAG_ACTIVITY_FORWARD_RESULT, "FLAG_ACTIVITY_FORWARD_RESULT");
        FLAGS.put(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY, "FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY");
        FLAGS.put(Intent.FLAG_ACTIVITY_MULTIPLE_TASK, "FLAG_ACTIVITY_MULTIPLE_TASK");
        // FLAGS.put(Intent.FLAG_ACTIVITY_NEW_DOCUMENT,
        // "FLAG_ACTIVITY_NEW_DOCUMENT");
        FLAGS.put(Intent.FLAG_ACTIVITY_NEW_TASK, "FLAG_ACTIVITY_NEW_TASK");
        FLAGS.put(Intent.FLAG_ACTIVITY_NO_ANIMATION, "FLAG_ACTIVITY_NO_ANIMATION");
        FLAGS.put(Intent.FLAG_ACTIVITY_NO_HISTORY, "FLAG_ACTIVITY_NO_HISTORY");
        FLAGS.put(Intent.FLAG_ACTIVITY_NO_USER_ACTION, "FLAG_ACTIVITY_NO_USER_ACTION");
        FLAGS.put(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP, "FLAG_ACTIVITY_PREVIOUS_IS_TOP");
        FLAGS.put(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, "FLAG_ACTIVITY_REORDER_TO_FRONT");
        FLAGS.put(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED, "FLAG_ACTIVITY_RESET_TASK_IF_NEEDED");
        // FLAGS.put(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS,
        // "FLAG_ACTIVITY_RETAIN_IN_RECENTS");
        FLAGS.put(Intent.FLAG_ACTIVITY_TASK_ON_HOME, "FLAG_ACTIVITY_TASK_ON_HOME");
        FLAGS.put(Intent.FLAG_DEBUG_LOG_RESOLUTION, "FLAG_DEBUG_LOG_RESOLUTION");
        FLAGS.put(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES, "FLAG_EXCLUDE_STOPPED_PACKAGES");
        FLAGS.put(Intent.FLAG_FROM_BACKGROUND, "FLAG_FROM_BACKGROUND");
        FLAGS.put(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION, "FLAG_GRANT_PERSISTABLE_URI_PERMISSION");
        // FLAGS.put(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION,
        // "FLAG_GRANT_PREFIX_URI_PERMISSION");
        FLAGS.put(Intent.FLAG_GRANT_READ_URI_PERMISSION, "FLAG_GRANT_READ_URI_PERMISSION");
        FLAGS.put(Intent.FLAG_GRANT_WRITE_URI_PERMISSION, "FLAG_GRANT_WRITE_URI_PERMISSION");
        FLAGS.put(Intent.FLAG_INCLUDE_STOPPED_PACKAGES, "FLAG_INCLUDE_STOPPED_PACKAGES");
        FLAGS.put(Intent.FLAG_RECEIVER_FOREGROUND, "FLAG_RECEIVER_FOREGROUND");
        FLAGS.put(Intent.FLAG_RECEIVER_NO_ABORT, "FLAG_RECEIVER_NO_ABORT");
        FLAGS.put(Intent.FLAG_RECEIVER_REGISTERED_ONLY, "FLAG_RECEIVER_REGISTERED_ONLY");
        FLAGS.put(Intent.FLAG_RECEIVER_REPLACE_PENDING, "FLAG_RECEIVER_REPLACE_PENDING");
        FLAGS.put(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, "FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET");
    }
    
    /**
     * @description 输出Intent信息 ，例如：Action,Data...
     * @date 2015年8月17日
     * @param intent
     */
    public static void print(Intent intent) {
        if (intent == null) {
            Log.v(TAG, "no intent found");
            return;
        }
        Bundle extras = intent.getExtras();
        Log.v(TAG, "Intent[@" + Integer.toHexString(intent.hashCode()) + "] content:");
        Log.v(TAG, "Action   : " + intent.getAction());
        Log.v(TAG, "Category : " + intent.getCategories());
        Log.v(TAG, "Data     : " + intent.getDataString());
        Log.v(TAG, "Component: " + intent.getComponent().getPackageName() + "/" + intent.getComponent().getClassName());
        printFlags(TAG, intent.getFlags());
        Log.v(TAG, "HasExtras: " + (extras != null && !extras.isEmpty()));
        printExtras(extras);
    }
    
    public static void printFlags(String tag, Intent intent) {
        if (intent == null) {
            return;
        }
        printFlags(tag, intent.getFlags());
    }
    
    private static void printFlags(String tag, int flags) {
        Log.v(tag, "Flags    : " + Integer.toBinaryString(flags));
        for (int flag : FLAGS.keySet()) {
            if ((flag & flags) != 0) {
                Log.v(tag, "Flag     : " + FLAGS.get(flag));
            }
        }
    }
    
    public static void printExtras(Intent intent) {
        if (intent == null) {
            return;
        }
        printExtras(intent.getExtras());
    }
    
    public static void printExtras(Bundle extras) {
        if (extras == null) {
            return;
        }
        for (String key : extras.keySet()) {
            Log.v(TAG, "Extra[" + key + "] :" + String.valueOf(extras.get(key)));
        }
    }
    
}
