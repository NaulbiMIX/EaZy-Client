/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 */
package net.minecraft.block.properties;

import java.util.Collection;

public interface IProperty<T extends Comparable<T>> {
    public String getName();

    public Collection<T> getAllowedValues();

    public Class<T> getValueClass();

    public String getName(T var1);
}

