/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITileEntityProvider {
    public TileEntity createNewTileEntity(World var1, int var2);
}

