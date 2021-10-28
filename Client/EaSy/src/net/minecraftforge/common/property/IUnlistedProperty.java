/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraftforge.common.property;

public interface IUnlistedProperty<V> {
    public String getName();

    public boolean isValid(V var1);

    public Class<V> getType();

    public String valueToString(V var1);
}

