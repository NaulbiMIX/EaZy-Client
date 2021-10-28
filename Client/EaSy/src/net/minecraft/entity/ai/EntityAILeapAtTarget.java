/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.util.Random
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

public class EntityAILeapAtTarget
extends EntityAIBase {
    EntityLiving leaper;
    EntityLivingBase leapTarget;
    float leapMotionY;

    public EntityAILeapAtTarget(EntityLiving leapingEntity, float leapMotionYIn) {
        this.leaper = leapingEntity;
        this.leapMotionY = leapMotionYIn;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        this.leapTarget = this.leaper.getAttackTarget();
        if (this.leapTarget == null) {
            return false;
        }
        double d0 = this.leaper.getDistanceSqToEntity(this.leapTarget);
        if (!(d0 < 4.0) && !(d0 > 16.0)) {
            if (!this.leaper.onGround) {
                return false;
            }
            return this.leaper.getRNG().nextInt(5) == 0;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return !this.leaper.onGround;
    }

    @Override
    public void startExecuting() {
        double d0 = this.leapTarget.posX - this.leaper.posX;
        double d1 = this.leapTarget.posZ - this.leaper.posZ;
        float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        this.leaper.motionX += d0 / (double)f * 0.5 * 0.800000011920929 + this.leaper.motionX * 0.20000000298023224;
        this.leaper.motionZ += d1 / (double)f * 0.5 * 0.800000011920929 + this.leaper.motionZ * 0.20000000298023224;
        this.leaper.motionY = this.leapMotionY;
    }
}

