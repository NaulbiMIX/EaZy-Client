/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.network.handshake;

import net.minecraft.network.INetHandler;
import net.minecraft.network.handshake.client.C00Handshake;

public interface INetHandlerHandshakeServer
extends INetHandler {
    public void processHandshake(C00Handshake var1);
}

