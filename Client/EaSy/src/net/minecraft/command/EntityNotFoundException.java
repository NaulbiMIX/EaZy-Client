/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class EntityNotFoundException
extends CommandException {
    public EntityNotFoundException() {
        this("commands.generic.entity.notFound", new Object[0]);
    }

    public /* varargs */ EntityNotFoundException(String p_i46035_1_, Object ... p_i46035_2_) {
        super(p_i46035_1_, p_i46035_2_);
    }
}

