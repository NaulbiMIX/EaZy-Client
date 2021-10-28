/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class SyntaxErrorException
extends CommandException {
    public SyntaxErrorException() {
        this("commands.generic.snytax", new Object[0]);
    }

    public /* varargs */ SyntaxErrorException(String message, Object ... replacements) {
        super(message, replacements);
    }
}

