/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.launchwrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogWrapper {
    public static LogWrapper log = new LogWrapper();
    private Logger myLog;
    private static boolean configured;

    private static void configureLogging() {
        LogWrapper.log.myLog = LogManager.getLogger((String)"LaunchWrapper");
        configured = true;
    }

    public static void retarget(Logger to) {
        LogWrapper.log.myLog = to;
    }

    public static /* varargs */ void log(String logChannel, Level level, String format, Object ... data) {
        LogWrapper.makeLog(logChannel);
        LogManager.getLogger((String)logChannel).log(level, String.format((String)format, (Object[])data));
    }

    public static /* varargs */ void log(Level level, String format, Object ... data) {
        if (!configured) {
            LogWrapper.configureLogging();
        }
        LogWrapper.log.myLog.log(level, String.format((String)format, (Object[])data));
    }

    public static /* varargs */ void log(String logChannel, Level level, Throwable ex, String format, Object ... data) {
        LogWrapper.makeLog(logChannel);
        LogManager.getLogger((String)logChannel).log(level, String.format((String)format, (Object[])data), ex);
    }

    public static /* varargs */ void log(Level level, Throwable ex, String format, Object ... data) {
        if (!configured) {
            LogWrapper.configureLogging();
        }
        LogWrapper.log.myLog.log(level, String.format((String)format, (Object[])data), ex);
    }

    public static /* varargs */ void severe(String format, Object ... data) {
        LogWrapper.log(Level.ERROR, format, data);
    }

    public static /* varargs */ void warning(String format, Object ... data) {
        LogWrapper.log(Level.WARN, format, data);
    }

    public static /* varargs */ void info(String format, Object ... data) {
        LogWrapper.log(Level.INFO, format, data);
    }

    public static /* varargs */ void fine(String format, Object ... data) {
        LogWrapper.log(Level.DEBUG, format, data);
    }

    public static /* varargs */ void finer(String format, Object ... data) {
        LogWrapper.log(Level.TRACE, format, data);
    }

    public static /* varargs */ void finest(String format, Object ... data) {
        LogWrapper.log(Level.TRACE, format, data);
    }

    public static void makeLog(String logChannel) {
        LogManager.getLogger((String)logChannel);
    }
}

