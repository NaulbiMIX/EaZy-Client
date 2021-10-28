/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Array
 *  java.util.ArrayDeque
 */
package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;

public class ArrayCache {
    private Class elementClass = null;
    private int maxCacheSize = 0;
    private ArrayDeque cache = new ArrayDeque();

    public ArrayCache(Class elementClass, int maxCacheSize) {
        this.elementClass = elementClass;
        this.maxCacheSize = maxCacheSize;
    }

    public synchronized Object allocate(int size) {
        Object object = this.cache.pollLast();
        if (object == null || Array.getLength((Object)object) < size) {
            object = Array.newInstance((Class)this.elementClass, (int)size);
        }
        return object;
    }

    public synchronized void free(Object arr) {
        if (arr != null) {
            Class oclass = arr.getClass();
            if (oclass.getComponentType() != this.elementClass) {
                throw new IllegalArgumentException("Wrong component type");
            }
            if (this.cache.size() < this.maxCacheSize) {
                this.cache.add(arr);
            }
        }
    }
}

