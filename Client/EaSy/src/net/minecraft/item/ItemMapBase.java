/*
 * Decompiled with CFR 0.0.
 */
package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ItemMapBase
extends Item {
    protected ItemMapBase() {
    }

    @Override
    public boolean isMap() {
        return true;
    }

    public Packet createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
        return null;
    }
}

