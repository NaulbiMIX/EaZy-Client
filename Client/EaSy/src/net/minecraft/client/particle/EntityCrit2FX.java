/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCrit2FX
extends EntityFX {
    float field_174839_a;

    protected EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46284_8_, double p_i46284_10_, double p_i46284_12_) {
        this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46284_8_, p_i46284_10_, p_i46284_12_, 1.0f);
    }

    protected EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46285_8_, double p_i46285_10_, double p_i46285_12_, float p_i46285_14_) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += p_i46285_8_ * 0.4;
        this.motionY += p_i46285_10_ * 0.4;
        this.motionZ += p_i46285_12_ * 0.4;
        this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896 + 0.6000000238418579);
        this.particleRed = this.particleBlue;
        this.particleScale *= 0.75f;
        this.particleScale *= p_i46285_14_;
        this.field_174839_a = this.particleScale;
        this.particleMaxAge = (int)(6.0 / (Math.random() * 0.8 + 0.6));
        this.particleMaxAge = (int)((float)this.particleMaxAge * p_i46285_14_);
        this.noClip = false;
        this.setParticleTextureIndex(65);
        this.onUpdate();
    }

    @Override
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge * 32.0f;
        f = MathHelper.clamp_float(f, 0.0f, 1.0f);
        this.particleScale = this.field_174839_a * f;
        super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
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
        this.particleGreen = (float)((double)this.particleGreen * 0.96);
        this.particleBlue = (float)((double)this.particleBlue * 0.9);
        this.motionX *= 0.699999988079071;
        this.motionY *= 0.699999988079071;
        this.motionZ *= 0.699999988079071;
        this.motionY -= 0.019999999552965164;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }

    public static class MagicFactory
    implements IParticleFactory {
        @Override
        public /* varargs */ EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            EntityCrit2FX entityfx = new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            entityfx.setRBGColorF(entityfx.getRedColorF() * 0.3f, entityfx.getGreenColorF() * 0.8f, entityfx.getBlueColorF());
            entityfx.nextTextureIndexX();
            return entityfx;
        }
    }

    public static class Factory
    implements IParticleFactory {
        @Override
        public /* varargs */ EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            return new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }

}

