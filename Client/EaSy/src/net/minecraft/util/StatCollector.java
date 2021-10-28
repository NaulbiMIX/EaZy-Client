/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.util;

import net.minecraft.util.StringTranslate;

public class StatCollector {
    private static StringTranslate localizedName = StringTranslate.getInstance();
    private static StringTranslate fallbackTranslator = new StringTranslate();

    public static String translateToLocal(String key) {
        return localizedName.translateKey(key);
    }

    public static /* varargs */ String translateToLocalFormatted(String key, Object ... format) {
        return localizedName.translateKeyFormat(key, format);
    }

    public static String translateToFallback(String key) {
        return fallbackTranslator.translateKey(key);
    }

    public static boolean canTranslate(String key) {
        return localizedName.isKeyTranslated(key);
    }

    public static long getLastTranslationUpdateTimeInMilliseconds() {
        return localizedName.getLastUpdateTimeInMilliseconds();
    }
}

