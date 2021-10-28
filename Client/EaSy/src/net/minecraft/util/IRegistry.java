/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Iterable
 *  java.lang.Object
 */
package net.minecraft.util;

public interface IRegistry<K, V>
extends Iterable<V> {
    public V getObject(K var1);

    public void putObject(K var1, V var2);
}

