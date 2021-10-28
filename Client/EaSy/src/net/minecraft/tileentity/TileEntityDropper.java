/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package net.minecraft.tileentity;

import net.minecraft.tileentity.TileEntityDispenser;

public class TileEntityDropper
extends TileEntityDispenser {
    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.dropper";
    }

    @Override
    public String getGuiID() {
        return "minecraft:dropper";
    }
}

