/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityStatus
implements Packet {
    private int field_149164_a;
    private byte field_149163_b;
    private static final String __OBFID = "CL_00001299";

    public S19PacketEntityStatus() {
    }

    public S19PacketEntityStatus(Entity p_i46335_1_, byte p_i46335_2_) {
        this.field_149164_a = p_i46335_1_.getEntityId();
        this.field_149163_b = p_i46335_2_;
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.field_149164_a = data.readInt();
        this.field_149163_b = data.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeInt(this.field_149164_a);
        data.writeByte(this.field_149163_b);
    }

    public void func_180736_a(INetHandlerPlayClient p_180736_1_) {
        p_180736_1_.handleEntityStatus(this);
    }

    public Entity func_149161_a(World worldIn) {
        return worldIn.getEntityByID(this.field_149164_a);
    }

    public byte func_149160_c() {
        return this.field_149163_b;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180736_a((INetHandlerPlayClient)handler);
    }
}

