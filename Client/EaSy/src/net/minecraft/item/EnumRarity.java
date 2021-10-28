/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.item;

import net.minecraft.util.EnumChatFormatting;

public enum EnumRarity {
    COMMON(EnumChatFormatting.WHITE, "Common"),
    UNCOMMON(EnumChatFormatting.YELLOW, "Uncommon"),
    RARE(EnumChatFormatting.AQUA, "Rare"),
    EPIC(EnumChatFormatting.LIGHT_PURPLE, "Epic");
    
    public final EnumChatFormatting rarityColor;
    public final String rarityName;

    private EnumRarity(EnumChatFormatting color, String name) {
        this.rarityColor = color;
        this.rarityName = name;
    }
}

