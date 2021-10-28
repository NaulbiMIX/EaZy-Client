/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.List
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge
extends Item {
    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        if (renderPass != 1) {
            return super.getColorFromItemStack(stack, renderPass);
        }
        NBTBase nbtbase = ItemFireworkCharge.getExplosionTag(stack, "Colors");
        if (!(nbtbase instanceof NBTTagIntArray)) {
            return 9079434;
        }
        NBTTagIntArray nbttagintarray = (NBTTagIntArray)nbtbase;
        int[] aint = nbttagintarray.getIntArray();
        if (aint.length == 1) {
            return aint[0];
        }
        int i = 0;
        int j = 0;
        int k = 0;
        for (int l : aint) {
            i += (l & 16711680) >> 16;
            j += (l & 65280) >> 8;
            k += (l & 255) >> 0;
        }
        return (i /= aint.length) << 16 | (j /= aint.length) << 8 | (k /= aint.length);
    }

    public static NBTBase getExplosionTag(ItemStack stack, String key) {
        NBTTagCompound nbttagcompound;
        if (stack.hasTagCompound() && (nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion")) != null) {
            return nbttagcompound.getTag(key);
        }
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NBTTagCompound nbttagcompound;
        if (stack.hasTagCompound() && (nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion")) != null) {
            ItemFireworkCharge.addExplosionInfo(nbttagcompound, tooltip);
        }
    }

    public static void addExplosionInfo(NBTTagCompound nbt, List<String> tooltip) {
        int[] aint1;
        boolean flag3;
        boolean flag4;
        byte b0 = nbt.getByte("Type");
        if (b0 >= 0 && b0 <= 4) {
            tooltip.add((Object)StatCollector.translateToLocal("item.fireworksCharge.type." + b0).trim());
        } else {
            tooltip.add((Object)StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }
        int[] aint = nbt.getIntArray("Colors");
        if (aint.length > 0) {
            boolean flag = true;
            String s = "";
            for (int i : aint) {
                if (!flag) {
                    s = s + ", ";
                }
                flag = false;
                boolean flag1 = false;
                for (int j = 0; j < ItemDye.dyeColors.length; ++j) {
                    if (i != ItemDye.dyeColors[j]) continue;
                    flag1 = true;
                    s = s + StatCollector.translateToLocal(new StringBuilder().append("item.fireworksCharge.").append(EnumDyeColor.byDyeDamage(j).getUnlocalizedName()).toString());
                    break;
                }
                if (flag1) continue;
                s = s + StatCollector.translateToLocal("item.fireworksCharge.customColor");
            }
            tooltip.add((Object)s);
        }
        if ((aint1 = nbt.getIntArray("FadeColors")).length > 0) {
            boolean flag2 = true;
            String s1 = StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ";
            for (int l : aint1) {
                if (!flag2) {
                    s1 = s1 + ", ";
                }
                flag2 = false;
                boolean flag5 = false;
                for (int k = 0; k < 16; ++k) {
                    if (l != ItemDye.dyeColors[k]) continue;
                    flag5 = true;
                    s1 = s1 + StatCollector.translateToLocal(new StringBuilder().append("item.fireworksCharge.").append(EnumDyeColor.byDyeDamage(k).getUnlocalizedName()).toString());
                    break;
                }
                if (flag5) continue;
                s1 = s1 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
            }
            tooltip.add((Object)s1);
        }
        if (flag3 = nbt.getBoolean("Trail")) {
            tooltip.add((Object)StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }
        if (flag4 = nbt.getBoolean("Flicker")) {
            tooltip.add((Object)StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }
}

