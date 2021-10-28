/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  java.io.IOException
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3FPacketCustomPayload
implements Packet<INetHandlerPlayClient> {
    private String channel;
    private PacketBuffer data;

    public S3FPacketCustomPayload() {
    }

    public S3FPacketCustomPayload(String channelName, PacketBuffer dataIn) {
        this.channel = channelName;
        this.data = dataIn;
        if (dataIn.writerIndex() > 1048576) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.channel = buf.readStringFromBuffer(20);
        int i = buf.readableBytes();
        if (i < 0 || i > 1048576) {
            throw new IOException("Payload may not be larger than 1048576 bytes");
        }
        this.data = new PacketBuffer(buf.readBytes(i));
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeString(this.channel);
        buf.writeBytes(this.data);
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleCustomPayload(this);
    }

    public String getChannelName() {
        return this.channel;
    }

    public PacketBuffer getBufferData() {
        return this.data;
    }
}

