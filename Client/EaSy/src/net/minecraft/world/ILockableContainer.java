/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.world;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;

public interface ILockableContainer
extends IInventory,
IInteractionObject {
    public boolean isLocked();

    public void setLockCode(LockCode var1);

    public LockCode getLockCode();
}

