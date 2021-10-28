/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class PlayerNotFoundException
extends CommandException {
    public PlayerNotFoundException() {
        this("commands.generic.player.notFound", new Object[0]);
    }

    public /* varargs */ PlayerNotFoundException(String message, Object ... replacements) {
        super(message, replacements);
    }
}

