/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.util.Random
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public class EntityAuraFX
extends EntityFX {
    protected EntityAuraFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double speedIn) {
        float f;
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, speedIn);
        this.particleRed = f = this.rand.nextFloat() * 0.1f + 0.2f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.setParticleTextureIndex(0);
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.5f;
        this.motionX *= 0.019999999552965164;
        this.motionY *= 0.019999999552965164;
        this.motionZ *= 0.019999999552965164;
        this.particleMaxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = true;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }

    public static class HappyVillagerFactory
    implements IParticleFactory {
        @Override
        public /* varargs */ EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            EntityAuraFX entityfx = new EntityAuraFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            entityfx.setParticleTextureIndex(82);
            entityfx.setRBGColorF(1.0f, 1.0f, 1.0f);
            return entityfx;
        }
    }

    public static class Factory
    implements IParticleFactory {
        @Override
        public /* varargs */ EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            return new EntityAuraFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }

}

