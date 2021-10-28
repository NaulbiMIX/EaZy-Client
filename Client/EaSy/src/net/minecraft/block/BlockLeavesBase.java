/*
 * Decompiled with CFR 0.0.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockLeavesBase
extends Block {
    protected boolean fancyGraphics;

    protected BlockLeavesBase(Material materialIn, boolean fancyGraphics) {
        super(materialIn);
        this.fancyGraphics = fancyGraphics;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return !this.fancyGraphics && worldIn.getBlockState(pos).getBlock() == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
    }
}

