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
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX
extends EntityFX {
    private float flameScale;

    protected EntityFlameFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.motionX = this.motionX * 0.009999999776482582 + xSpeedIn;
        this.motionY = this.motionY * 0.009999999776482582 + ySpeedIn;
        this.motionZ = this.motionZ * 0.009999999776482582 + zSpeedIn;
        this.posX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.flameScale = this.particleScale;
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
    }

    @Override
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0f - f * f * 0.5f);
        super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;
        f = MathHelper.clamp_float(f, 0.0f, 1.0f);
        int i = super.getBrightnessForRender(partialTicks);
        int j = i & 255;
        int k = i >> 16 & 255;
        if ((j += (int)(f * 15.0f * 16.0f)) > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    @Override
    public float getBrightness(float partialTicks) {
        float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;
        f = MathHelper.clamp_float(f, 0.0f, 1.0f);
        float f1 = super.getBrightness(partialTicks);
        return f1 * f + (1.0f - f);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }

    public static class Factory
    implements IParticleFactory {
        @Override
        public /* varargs */ EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            return new EntityFlameFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }

}

