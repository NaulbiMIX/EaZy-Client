/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.network.status;

import net.minecraft.network.INetHandler;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;

public interface INetHandlerStatusServer
extends INetHandler {
    public void processPing(C01PacketPing var1);

    public void processServerQuery(C00PacketServerQuery var1);
}

