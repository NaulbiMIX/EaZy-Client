/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  java.lang.Object
 *  java.util.Set
 */
package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

public class ItemSpade
extends ItemTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet((Object[])new Block[]{Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand});

    public ItemSpade(Item.ToolMaterial material) {
        super(1.0f, material, EFFECTIVE_ON);
    }

    @Override
    public boolean canHarvestBlock(Block blockIn) {
        if (blockIn == Blocks.snow_layer) {
            return true;
        }
        return blockIn == Blocks.snow;
    }
}
