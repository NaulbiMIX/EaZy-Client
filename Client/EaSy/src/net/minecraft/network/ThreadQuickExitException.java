/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.RuntimeException
 *  java.lang.StackTraceElement
 *  java.lang.Throwable
 */
package net.minecraft.network;

public final class ThreadQuickExitException
extends RuntimeException {
    public static final ThreadQuickExitException INSTANCE = new ThreadQuickExitException();

    private ThreadQuickExitException() {
        this.setStackTrace(new StackTraceElement[0]);
    }

    public synchronized Throwable fillInStackTrace() {
        this.setStackTrace(new StackTraceElement[0]);
        return this;
    }
}

