/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.util;

public enum EnumWorldBlockLayer {
    SOLID("Solid"),
    CUTOUT_MIPPED("Mipped Cutout"),
    CUTOUT("Cutout"),
    TRANSLUCENT("Translucent");
    
    private final String layerName;

    private EnumWorldBlockLayer(String layerNameIn) {
        this.layerName = layerNameIn;
    }

    public String toString() {
        return this.layerName;
    }
}

