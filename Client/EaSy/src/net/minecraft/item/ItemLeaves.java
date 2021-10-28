/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLeaves
extends ItemBlock {
    private final BlockLeaves leaves;

    public ItemLeaves(BlockLeaves block) {
        super(block);
        this.leaves = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage | 4;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        return this.leaves.getRenderColor(this.leaves.getStateFromMeta(stack.getMetadata()));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + this.leaves.getWoodType(stack.getMetadata()).getUnlocalizedName();
    }
}

