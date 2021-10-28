/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.world;

import net.minecraft.util.IChatComponent;

public interface IWorldNameable {
    public String getName();

    public boolean hasCustomName();

    public IChatComponent getDisplayName();
}

