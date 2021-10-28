/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerFileData {
    public void writePlayerData(EntityPlayer var1);

    public NBTTagCompound readPlayerData(EntityPlayer var1);

    public String[] getAvailablePlayerDat();
}

