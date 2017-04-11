package com.apple.http.utils;

import android.util.Log;

import com.apple.http.common.BaseHttpClient;


/**
 * "Less-word" analog of Android {@link Log logger}
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.6.4
 */
public final class L {

    private static final String LOG_FORMAT = "http";
    private static volatile boolean writeDebugLogs = false;
    private static volatile boolean writeLogs = true;

    private L() {
    }

    /**
     * Enables logger (if {@link #disableLogging()} was called before)
     *
     * @deprecated Use {@link #writeLogs(boolean) writeLogs(true)} instead
     */
    @Deprecated
    public static void enableLogging() {
        writeLogs(true);
    }

    /**
     * Disables logger, no logs will be passed to LogCat, all log methods will do nothing
     *
     * @deprecated Use {@link #writeLogs(boolean) writeLogs(false)} instead
     */
    @Deprecated
    public static void disableLogging() {
        writeLogs(false);
    }

    /**
     * Enables/disables detail logging of {@link BaseHttpClient} work.
     */
    public static void writeDebugLogs(boolean writeDebugLogs) {
        L.writeDebugLogs = writeDebugLogs;
    }

    /** Enables/disables logging of {@link BaseHttpClient} completely (even error logs). */
    public static void writeLogs(boolean writeLogs) {
        L.writeLogs = writeLogs;
    }

    public static void d(String message, Object... args) {
        if (writeDebugLogs) {
            log(Log.DEBUG, null, message, args);
        }
    }

    public static void i(String message, Object... args) {
        log(Log.INFO, null, message, args);
    }

    public static void w(String message, Object... args) {
        log(Log.WARN, null, message, args);
    }

    public static void e(Throwable ex) {
        log(Log.ERROR, ex, null);
    }

    public static void e(String message, Object... args) {
        log(Log.ERROR, null, message, args);
    }

    public static void e(Throwable ex, String message, Object... args) {
        log(Log.ERROR, ex, message, args);
    }

    private static void log(int priority, Throwable ex, String message, Object... args) {
        if (!writeLogs) return;
        if (args.length > 0) {
            message = String.format(message, args);
        }

        String log;
        if (ex == null) {
            log = message;
        } else {
            String logMessage = message == null ? ex.getMessage() : message;
            String logBody = Log.getStackTraceString(ex);
            log = String.format(LOG_FORMAT, logMessage, logBody);
        }
        Log.println(priority, BaseHttpClient.TAG, log);
    }
}
