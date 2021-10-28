/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.config;

public class RangeInt {
    private int min;
    private int max;

    public RangeInt(int min, int max) {
        this.min = Math.min((int)min, (int)max);
        this.max = Math.max((int)min, (int)max);
    }

    public boolean isInRange(int val) {
        if (val < this.min) {
            return false;
        }
        return val <= this.max;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public String toString() {
        return "min: " + this.min + ", max: " + this.max;
    }
}

