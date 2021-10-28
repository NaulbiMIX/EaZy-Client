/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package net.minecraft.util;

public class ChatAllowedCharacters {
    public static final char[] allowedCharactersArray = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

    public static boolean isAllowedCharacter(char character) {
        return character >= ' ' && character != '';
    }

    public static String filterAllowedCharacters(String input) {
        StringBuilder stringbuilder = new StringBuilder();
        for (char c0 : input.toCharArray()) {
            if (!ChatAllowedCharacters.isAllowedCharacter(c0)) continue;
            stringbuilder.append(c0);
        }
        return stringbuilder.toString();
    }
}

