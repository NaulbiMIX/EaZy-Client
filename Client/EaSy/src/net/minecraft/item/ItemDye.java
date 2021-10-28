/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Random
 */
package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemDye
extends Item {
    public static final int[] dyeColors = new int[]{1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    public ItemDye() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(i).getUnlocalizedName();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate;
        Block block;
        if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            return false;
        }
        EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
        if (enumdyecolor == EnumDyeColor.WHITE) {
            if (ItemDye.applyBonemeal(stack, worldIn, pos)) {
                if (!worldIn.isRemote) {
                    worldIn.playAuxSFX(2005, pos, 0);
                }
                return true;
            }
        } else if (enumdyecolor == EnumDyeColor.BROWN && (block = (iblockstate = worldIn.getBlockState(pos)).getBlock()) == Blocks.log && iblockstate.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
            if (side == EnumFacing.DOWN) {
                return false;
            }
            if (side == EnumFacing.UP) {
                return false;
            }
            if (worldIn.isAirBlock(pos = pos.offset(side))) {
                IBlockState iblockstate1 = Blocks.cocoa.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, playerIn);
                worldIn.setBlockState(pos, iblockstate1, 2);
                if (!playerIn.capabilities.isCreativeMode) {
                    --stack.stackSize;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
        IGrowable igrowable;
        IBlockState iblockstate = worldIn.getBlockState(target);
        if (iblockstate.getBlock() instanceof IGrowable && (igrowable = (IGrowable)((Object)iblockstate.getBlock())).canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
            if (!worldIn.isRemote) {
                if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
                    igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                }
                --stack.stackSize;
            }
            return true;
        }
        return false;
    }

    public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
        Block block;
        if (amount == 0) {
            amount = 15;
        }
        if ((block = worldIn.getBlockState(pos).getBlock()).getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(worldIn, pos);
            for (int i = 0; i < amount; ++i) {
                double d0 = itemRand.nextGaussian() * 0.02;
                double d1 = itemRand.nextGaussian() * 0.02;
                double d2 = itemRand.nextGaussian() * 0.02;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (float)pos.getX() + itemRand.nextFloat(), (double)pos.getY() + (double)itemRand.nextFloat() * block.getBlockBoundsMaxY(), (double)((float)pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        if (target instanceof EntitySheep) {
            EntitySheep entitysheep = (EntitySheep)target;
            EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
            if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor) {
                entitysheep.setFleeceColor(enumdyecolor);
                --stack.stackSize;
            }
            return true;
        }
        return false;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < 16; ++i) {
            subItems.add((Object)new ItemStack(itemIn, 1, i));
        }
    }
}

