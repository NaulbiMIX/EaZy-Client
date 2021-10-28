/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.command;

public class CommandException
extends Exception {
    private final Object[] errorObjects;

    public /* varargs */ CommandException(String message, Object ... objects) {
        super(message);
        this.errorObjects = objects;
    }

    public Object[] getErrorObjects() {
        return this.errorObjects;
    }
}

