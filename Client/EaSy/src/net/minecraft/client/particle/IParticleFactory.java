/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public interface IParticleFactory {
    public /* varargs */ EntityFX getEntityFX(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int ... var15);
}

