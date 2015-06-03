/* 
 * Copyright (C) 2015 Alex. 
 * All Rights Reserved.
 *
 * ALL RIGHTS ARE RESERVED BY Alex. ACCESS TO THIS
 * SOURCE CODE IS STRICTLY RESTRICTED UNDER CONTRACT. THIS CODE IS TO
 * BE KEPT STRICTLY CONFIDENTIAL.
 *
 * UNAUTHORIZED MODIFICATION OF THIS FILE WILL VOID YOUR SUPPORT CONTRACT
 * WITH Alex(zeroapp@126.com). IF SUCH MODIFICATIONS ARE FOR THE PURPOSE
 * OF CIRCUMVENTING LICENSING LIMITATIONS, LEGAL ACTION MAY RESULT.
 */

package com.zeroapp.utils;

/**
 * <p>Title: TODO.</p>
 * <p>Description: TODO.</p>
 *
 * @author Alex(zeroapp@126.com) 2015-6-3.
 * @version $Id$
 */

public class Log {

    public static String TAG = "";

    public static final int LEVEL_ERROR = 1;
    public static final int LEVEL_WARNING = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_DEBUG = 4;
    public static final int LEVEL_VERBOSE = 5;

    /**
     * Send a DEBUG log message.
     * 
     * @param msg
     */
    public static void d(String msg) {
        if (getLogLevel() >= LEVEL_DEBUG) {
            System.out.println(TAG + msg + getTrace());
        }
    }

    /**
     * Send an ERROR log message.
     * 
     * @param msg
     *            The message you would like logged.
     */
    public static void e(String msg) {
        if (getLogLevel() >= LEVEL_ERROR) {
            System.out.println(TAG + msg + getTrace());
        }
    }

  

    public static void i(String msg) {
        if (getLogLevel() >= LEVEL_INFO) {
            System.out.println(TAG + msg + getTrace());
        }
    }

   

    /**
     * Send a VERBOSE log message.
     * 
     * @param msg
     *            The message you would like logged.
     */
    public static void v(String msg) {
        if (getLogLevel() >= LEVEL_VERBOSE) {
            System.out.println(TAG + msg + getTrace());
        }
    }



    /**
     * Send a WARN log message
     * 
     * @param msg
     *            The message you would like logged.
     */
    public static void w(String msg) {
        if (getLogLevel() >= LEVEL_WARNING) {
            System.out.println(TAG + msg + getTrace());
        }
    }



    /**
     * <p>
     * Title: Send a DEBUG trace log message .
     * </p>
     * <p>
     * Description: Send a DEBUG trace log message.
     * </p>
     * 
     */
    public static void trace() {
        if (getLogLevel() >= LEVEL_DEBUG) {
            System.out.println(TAG + getTrace());
        }
    }

    /**
     * 
     * <p>
     * Title: Send a DEBUG trace log message.
     * </p>
     * <p>
     * Description: Send a DEBUG trace log message.
     * </p>
     * 
     * @param tag
     */
    public static void trace(String tag) {
        if (getLogLevel() >= LEVEL_DEBUG) {
            System.out.println(TAG + getTrace());
        }
    }

//    /**
//     * 
//     * <p>
//     * Title: Send a INFO version log message.
//     * </p>
//     * <p>
//     * Description: Send a INFO version log message.
//     * </p>
//     * 
//     * @param context
//     */
//    public static void appVersion(Context context) {
//        if (null == context) {
//            return;
//        }
//        try {
//            // 获取PackageManager的实例
//            PackageManager packageManager = context.getPackageManager();
//            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
//            appVersion(packInfo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 
//     * <p>
//     * Title: Send a INFO version log message.
//     * </p>
//     * <p>
//     * Description: Send a INFO version log message.
//     * </p>
//     * 
//     * @param packInfo
//     */
//    public static void appVersion(PackageInfo packInfo) {
//        int versionCode = packInfo.versionCode;
//        String versionName = packInfo.versionName;
//        StringBuilder info = new StringBuilder(packInfo.packageName);
//        info.append("(VersionCode:").append(versionCode).append(", ").append("VersionName:").append(versionName).append(")");
//        if (getLogLevel() >= LEVEL_INFO) {
//            System.out.println(TAG + info.toString());
//        }
//    }

    private static String getTrace() {
        return "";
//        StackTraceElement te = Thread.currentThread().getStackTrace()[4];
//        StringBuilder builder = new StringBuilder(" - ");
//        return builder.append(te.getMethodName()).append("()#").append(te.getFileName()).append(":").append(te.getLineNumber()).append("(").append(Thread.currentThread().getName()).append(")")
//                .toString();
    }

    private static int getLogLevel() {
        // return SystemProperties.getInt("hisense.appdebug", 0);
        return 5;
    }

}
