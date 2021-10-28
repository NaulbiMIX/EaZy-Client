/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IHopper
extends IInventory {
    public World getWorld();

    public double getXPos();

    public double getYPos();

    public double getZPos();
}

