/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S00PacketKeepAlive
implements Packet<INetHandlerPlayClient> {
    private int id;

    public S00PacketKeepAlive() {
    }

    public S00PacketKeepAlive(int idIn) {
        this.id = idIn;
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleKeepAlive(this);
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.id = buf.readVarIntFromBuffer();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.id);
    }

    public int func_149134_c() {
        return this.id;
    }
}
