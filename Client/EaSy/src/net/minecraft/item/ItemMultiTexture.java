/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMultiTexture
extends ItemBlock {
    protected final Block theBlock;
    protected final Function<ItemStack, String> nameFunction;

    public ItemMultiTexture(Block block, Block block2, Function<ItemStack, String> nameFunction) {
        super(block);
        this.theBlock = block2;
        this.nameFunction = nameFunction;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public ItemMultiTexture(Block block, Block block2, String[] namesByMeta) {
        this(block, block2, new Function<ItemStack, String>(){

            public String apply(ItemStack p_apply_1_) {
                int i = p_apply_1_.getMetadata();
                if (i < 0 || i >= val$namesByMeta.length) {
                    i = 0;
                }
                return val$namesByMeta[i];
            }
        });
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + (String)this.nameFunction.apply((Object)stack);
    }

}

