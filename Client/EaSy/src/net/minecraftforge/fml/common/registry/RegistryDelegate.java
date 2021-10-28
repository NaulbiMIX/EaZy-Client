/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 */
package net.minecraftforge.fml.common.registry;

import net.minecraft.util.ResourceLocation;

public interface RegistryDelegate<T> {
    public T get();

    public ResourceLocation name();

    public Class<T> type();
}

