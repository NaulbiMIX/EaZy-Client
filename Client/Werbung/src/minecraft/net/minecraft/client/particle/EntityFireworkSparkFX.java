/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityFireworkSparkFX
extends EntityFX {
    private int baseTextureIndex = 160;
    private boolean field_92054_ax;
    private boolean field_92048_ay;
    private final EffectRenderer field_92047_az;
    private float fadeColourRed;
    private float fadeColourGreen;
    private float fadeColourBlue;
    private boolean hasFadeColour;
    private static final String __OBFID = "CL_00000905";

    public EntityFireworkSparkFX(World worldIn, double p_i46356_2_, double p_i46356_4_, double p_i46356_6_, double p_i46356_8_, double p_i46356_10_, double p_i46356_12_, EffectRenderer p_i46356_14_) {
        super(worldIn, p_i46356_2_, p_i46356_4_, p_i46356_6_);
        this.motionX = p_i46356_8_;
        this.motionY = p_i46356_10_;
        this.motionZ = p_i46356_12_;
        this.field_92047_az = p_i46356_14_;
        this.particleScale *= 0.75f;
        this.particleMaxAge = 48 + this.rand.nextInt(12);
        this.noClip = false;
    }

    public void setTrail(boolean p_92045_1_) {
        this.field_92054_ax = p_92045_1_;
    }

    public void setTwinkle(boolean p_92043_1_) {
        this.field_92048_ay = p_92043_1_;
    }

    public void setColour(int p_92044_1_) {
        float var2 = (float)((p_92044_1_ & 16711680) >> 16) / 255.0f;
        float var3 = (float)((p_92044_1_ & 65280) >> 8) / 255.0f;
        float var4 = (float)((p_92044_1_ & 255) >> 0) / 255.0f;
        float var5 = 1.0f;
        this.setRBGColorF(var2 * var5, var3 * var5, var4 * var5);
    }

    public void setFadeColour(int p_92046_1_) {
        this.fadeColourRed = (float)((p_92046_1_ & 16711680) >> 16) / 255.0f;
        this.fadeColourGreen = (float)((p_92046_1_ & 65280) >> 8) / 255.0f;
        this.fadeColourBlue = (float)((p_92046_1_ & 255) >> 0) / 255.0f;
        this.hasFadeColour = true;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
        if (!this.field_92048_ay || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
            super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
        }
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        if (this.particleAge > this.particleMaxAge / 2) {
            this.setAlphaF(1.0f - ((float)this.particleAge - (float)(this.particleMaxAge / 2)) / (float)this.particleMaxAge);
            if (this.hasFadeColour) {
                this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2f;
                this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2f;
                this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2f;
            }
        }
        this.setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.motionY -= 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9100000262260437;
        this.motionY *= 0.9100000262260437;
        this.motionZ *= 0.9100000262260437;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        if (this.field_92054_ax && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
            EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, this.field_92047_az);
            var1.setAlphaF(0.99f);
            var1.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
            var1.particleAge = var1.particleMaxAge / 2;
            if (this.hasFadeColour) {
                var1.hasFadeColour = true;
                var1.fadeColourRed = this.fadeColourRed;
                var1.fadeColourGreen = this.fadeColourGreen;
                var1.fadeColourBlue = this.fadeColourBlue;
            }
            var1.field_92048_ay = this.field_92048_ay;
            this.field_92047_az.addEffect(var1);
        }
    }

    @Override
    public int getBrightnessForRender(float p_70070_1_) {
        return 15728880;
    }

    @Override
    public float getBrightness(float p_70013_1_) {
        return 1.0f;
    }
}

