/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.profiler;

import net.minecraft.profiler.PlayerUsageSnooper;

public interface IPlayerUsage {
    public void addServerStatsToSnooper(PlayerUsageSnooper var1);

    public void addServerTypeToSnooper(PlayerUsageSnooper var1);

    public boolean isSnooperEnabled();
}

