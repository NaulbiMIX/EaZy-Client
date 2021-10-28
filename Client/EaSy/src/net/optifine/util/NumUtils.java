/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.optifine.util;

public class NumUtils {
    public static float limit(float val, float min, float max) {
        if (val < min) {
            return min;
        }
        return val > max ? max : val;
    }

    public static int mod(int x, int y) {
        int i = x % y;
        if (i < 0) {
            i += y;
        }
        return i;
    }
}

