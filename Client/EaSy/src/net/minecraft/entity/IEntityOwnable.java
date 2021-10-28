/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.entity;

import net.minecraft.entity.Entity;

public interface IEntityOwnable {
    public String getOwnerId();

    public Entity getOwner();
}

