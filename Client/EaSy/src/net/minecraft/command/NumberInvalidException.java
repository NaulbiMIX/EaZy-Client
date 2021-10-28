/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class NumberInvalidException
extends CommandException {
    public NumberInvalidException() {
        this("commands.generic.num.invalid", new Object[0]);
    }

    public /* varargs */ NumberInvalidException(String message, Object ... replacements) {
        super(message, replacements);
    }
}

