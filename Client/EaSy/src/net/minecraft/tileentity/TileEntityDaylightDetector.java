/*
 * Decompiled with CFR 0.0.
 */
package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityDaylightDetector
extends TileEntity
implements ITickable {
    @Override
    public void update() {
        if (this.worldObj != null && !this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 20L == 0L) {
            this.blockType = this.getBlockType();
            if (this.blockType instanceof BlockDaylightDetector) {
                ((BlockDaylightDetector)this.blockType).updatePower(this.worldObj, this.pos);
            }
        }
    }
}

