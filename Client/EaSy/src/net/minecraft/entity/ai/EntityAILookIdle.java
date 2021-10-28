/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.util.Random
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;

public class EntityAILookIdle
extends EntityAIBase {
    private EntityLiving idleEntity;
    private double lookX;
    private double lookZ;
    private int idleTime;

    public EntityAILookIdle(EntityLiving entitylivingIn) {
        this.idleEntity = entitylivingIn;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return this.idleEntity.getRNG().nextFloat() < 0.02f;
    }

    @Override
    public boolean continueExecuting() {
        return this.idleTime >= 0;
    }

    @Override
    public void startExecuting() {
        double d0 = 6.283185307179586 * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos((double)d0);
        this.lookZ = Math.sin((double)d0);
        this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
    }

    @Override
    public void updateTask() {
        --this.idleTime;
        this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + (double)this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0f, this.idleEntity.getVerticalFaceSpeed());
    }
}

