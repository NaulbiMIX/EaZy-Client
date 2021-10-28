/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package net.minecraft.util;

import net.minecraft.util.ChatComponentTranslation;

public class ChatComponentTranslationFormatException
extends IllegalArgumentException {
    public ChatComponentTranslationFormatException(ChatComponentTranslation component, String message) {
        super(String.format((String)"Error parsing: %s: %s", (Object[])new Object[]{component, message}));
    }

    public ChatComponentTranslationFormatException(ChatComponentTranslation component, int index) {
        super(String.format((String)"Invalid index %d requested for %s", (Object[])new Object[]{index, component}));
    }

    public ChatComponentTranslationFormatException(ChatComponentTranslation component, Throwable cause) {
        super(String.format((String)"Error while parsing: %s", (Object[])new Object[]{component}), cause);
    }
}

