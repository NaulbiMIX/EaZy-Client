/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBlockBlob
extends WorldGenerator {
    private final Block field_150545_a;
    private final int field_150544_b;
    private static final String __OBFID = "CL_00000402";

    public WorldGenBlockBlob(Block p_i45450_1_, int p_i45450_2_) {
        super(false);
        this.field_150545_a = p_i45450_1_;
        this.field_150544_b = p_i45450_2_;
    }

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        Block var4;
        while (p_180709_3_.getY() > 3 && (worldIn.isAirBlock(p_180709_3_.offsetDown()) || (var4 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock()) != Blocks.grass && var4 != Blocks.dirt && var4 != Blocks.stone)) {
            p_180709_3_ = p_180709_3_.offsetDown();
        }
        if (p_180709_3_.getY() <= 3) {
            return false;
        }
        int var12 = this.field_150544_b;
        for (int var5 = 0; var12 >= 0 && var5 < 3; ++var5) {
            int var6 = var12 + p_180709_2_.nextInt(2);
            int var7 = var12 + p_180709_2_.nextInt(2);
            int var8 = var12 + p_180709_2_.nextInt(2);
            float var9 = (float)(var6 + var7 + var8) * 0.333f + 0.5f;
            for (BlockPos var11 : BlockPos.getAllInBox(p_180709_3_.add(-var6, -var7, -var8), p_180709_3_.add(var6, var7, var8))) {
                if (!(var11.distanceSq(p_180709_3_) <= (double)(var9 * var9))) continue;
                worldIn.setBlockState(var11, this.field_150545_a.getDefaultState(), 4);
            }
            p_180709_3_ = p_180709_3_.add(-(var12 + 1) + p_180709_2_.nextInt(2 + var12 * 2), 0 - p_180709_2_.nextInt(2), -(var12 + 1) + p_180709_2_.nextInt(2 + var12 * 2));
        }
        return true;
    }
}

