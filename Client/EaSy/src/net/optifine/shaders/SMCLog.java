/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.optifine.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PREFIX = "[Shaders] ";

    public static void severe(String message) {
        LOGGER.error(PREFIX + message);
    }

    public static void warning(String message) {
        LOGGER.warn(PREFIX + message);
    }

    public static void info(String message) {
        LOGGER.info(PREFIX + message);
    }

    public static void fine(String message) {
        LOGGER.debug(PREFIX + message);
    }

    public static /* varargs */ void severe(String format, Object ... args) {
        String s = String.format((String)format, (Object[])args);
        LOGGER.error(PREFIX + s);
    }

    public static /* varargs */ void warning(String format, Object ... args) {
        String s = String.format((String)format, (Object[])args);
        LOGGER.warn(PREFIX + s);
    }

    public static /* varargs */ void info(String format, Object ... args) {
        String s = String.format((String)format, (Object[])args);
        LOGGER.info(PREFIX + s);
    }

    public static /* varargs */ void fine(String format, Object ... args) {
        String s = String.format((String)format, (Object[])args);
        LOGGER.debug(PREFIX + s);
    }
}

