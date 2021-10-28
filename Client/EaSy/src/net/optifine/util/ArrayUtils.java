/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.optifine.util;

public class ArrayUtils {
    public static boolean contains(Object[] arr, Object val) {
        if (arr == null) {
            return false;
        }
        for (int i = 0; i < arr.length; ++i) {
            Object object = arr[i];
            if (object != val) continue;
            return true;
        }
        return false;
    }
}

