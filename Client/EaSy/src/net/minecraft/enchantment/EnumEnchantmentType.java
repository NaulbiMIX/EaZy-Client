/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public enum EnumEnchantmentType {
    ALL,
    ARMOR,
    ARMOR_FEET,
    ARMOR_LEGS,
    ARMOR_TORSO,
    ARMOR_HEAD,
    WEAPON,
    DIGGER,
    FISHING_ROD,
    BREAKABLE,
    BOW;
    

    public boolean canEnchantItem(Item p_77557_1_) {
        if (this == ALL) {
            return true;
        }
        if (this == BREAKABLE && p_77557_1_.isDamageable()) {
            return true;
        }
        if (p_77557_1_ instanceof ItemArmor) {
            if (this == ARMOR) {
                return true;
            }
            ItemArmor itemarmor = (ItemArmor)p_77557_1_;
            if (itemarmor.armorType == 0) {
                return this == ARMOR_HEAD;
            }
            if (itemarmor.armorType == 2) {
                return this == ARMOR_LEGS;
            }
            if (itemarmor.armorType == 1) {
                return this == ARMOR_TORSO;
            }
            if (itemarmor.armorType == 3) {
                return this == ARMOR_FEET;
            }
            return false;
        }
        if (p_77557_1_ instanceof ItemSword) {
            return this == WEAPON;
        }
        if (p_77557_1_ instanceof ItemTool) {
            return this == DIGGER;
        }
        if (p_77557_1_ instanceof ItemBow) {
            return this == BOW;
        }
        if (p_77557_1_ instanceof ItemFishingRod) {
            return this == FISHING_ROD;
        }
        return false;
    }
}

