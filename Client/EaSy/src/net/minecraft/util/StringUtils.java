/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern patternControlCode = Pattern.compile((String)"(?i)\\u00A7[0-9A-FK-OR]");

    public static String ticksToElapsedTime(int ticks) {
        int i = ticks / 20;
        int j = i / 60;
        return (i %= 60) < 10 ? j + ":0" + i : j + ":" + i;
    }

    public static String stripControlCodes(String text) {
        return patternControlCode.matcher((CharSequence)text).replaceAll("");
    }

    public static boolean isNullOrEmpty(String string) {
        return org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)string);
    }
}

