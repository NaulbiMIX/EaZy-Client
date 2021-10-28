/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.DataInput
 *  java.io.DataOutput
 *  java.io.IOException
 *  java.lang.String
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagEnd
extends NBTBase {
    NBTTagEnd() {
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(64L);
    }

    @Override
    void write(DataOutput output) throws IOException {
    }

    @Override
    public byte getId() {
        return 0;
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
}

