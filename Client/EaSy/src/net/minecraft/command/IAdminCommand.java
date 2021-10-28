/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public interface IAdminCommand {
    public /* varargs */ void notifyOperators(ICommandSender var1, ICommand var2, int var3, String var4, Object ... var5);
}

