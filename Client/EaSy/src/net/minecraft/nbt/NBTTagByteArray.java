/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.DataInput
 *  java.io.DataOutput
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagByteArray
extends NBTBase {
    private byte[] data;

    NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] data) {
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.data.length);
        output.write(this.data);
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(192L);
        int i = input.readInt();
        sizeTracker.read(8 * i);
        this.data = new byte[i];
        input.readFully(this.data);
    }

    @Override
    public byte getId() {
        return 7;
    }

    @Override
    public String toString() {
        return "[" + this.data.length + " bytes]";
    }

    @Override
    public NBTBase copy() {
        byte[] abyte = new byte[this.data.length];
        System.arraycopy((Object)this.data, (int)0, (Object)abyte, (int)0, (int)this.data.length);
        return new NBTTagByteArray(abyte);
    }

    @Override
    public boolean equals(Object p_equals_1_) {
        return super.equals(p_equals_1_) ? Arrays.equals((byte[])this.data, (byte[])((NBTTagByteArray)p_equals_1_).data) : false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode((byte[])this.data);
    }

    public byte[] getByteArray() {
        return this.data;
    }
}

