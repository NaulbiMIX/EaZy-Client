/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ListenableFuture
 *  java.lang.Object
 *  java.lang.Runnable
 */
package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
    public ListenableFuture<Object> addScheduledTask(Runnable var1);

    public boolean isCallingFromMinecraftThread();
}

