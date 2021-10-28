/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.command;

import net.minecraft.command.SyntaxErrorException;

public class WrongUsageException
extends SyntaxErrorException {
    public /* varargs */ WrongUsageException(String message, Object ... replacements) {
        super(message, replacements);
    }
}

