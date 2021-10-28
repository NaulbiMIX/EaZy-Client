/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package net.minecraft.client.resources;

import java.util.Map;
import net.minecraft.client.resources.Locale;

public class I18n {
    private static Locale i18nLocale;

    static void setLocale(Locale i18nLocaleIn) {
        i18nLocale = i18nLocaleIn;
    }

    public static /* varargs */ String format(String translateKey, Object ... parameters) {
        return i18nLocale.formatMessage(translateKey, parameters);
    }

    public static Map getLocaleProperties() {
        return I18n.i18nLocale.properties;
    }
}

