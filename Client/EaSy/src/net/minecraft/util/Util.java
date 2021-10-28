/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Enum
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.OutOfMemoryError
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.concurrent.ExecutionException
 *  java.util.concurrent.FutureTask
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.Logger;

public class Util {
    public static EnumOS getOSType() {
        String s = System.getProperty((String)"os.name").toLowerCase();
        return s.contains((CharSequence)"win") ? EnumOS.WINDOWS : (s.contains((CharSequence)"mac") ? EnumOS.OSX : (s.contains((CharSequence)"solaris") ? EnumOS.SOLARIS : (s.contains((CharSequence)"sunos") ? EnumOS.SOLARIS : (s.contains((CharSequence)"linux") ? EnumOS.LINUX : (s.contains((CharSequence)"unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }

    public static <V> V runTask(FutureTask<V> task, Logger logger) {
        try {
            task.run();
            return (V)task.get();
        }
        catch (ExecutionException executionexception) {
            logger.fatal("Error executing task", (Throwable)executionexception);
            if (executionexception.getCause() instanceof OutOfMemoryError) {
                OutOfMemoryError outofmemoryerror = (OutOfMemoryError)executionexception.getCause();
                throw outofmemoryerror;
            }
        }
        catch (InterruptedException interruptedexception) {
            logger.fatal("Error executing task", (Throwable)interruptedexception);
        }
        return null;
    }

    public static enum EnumOS {
        LINUX,
        SOLARIS,
        WINDOWS,
        OSX,
        UNKNOWN;
        
    }

}

