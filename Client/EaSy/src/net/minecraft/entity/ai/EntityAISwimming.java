/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.util.Random
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAISwimming
extends EntityAIBase {
    private EntityLiving theEntity;

    public EntityAISwimming(EntityLiving entitylivingIn) {
        this.theEntity = entitylivingIn;
        this.setMutexBits(4);
        ((PathNavigateGround)entitylivingIn.getNavigator()).setCanSwim(true);
    }

    @Override
    public boolean shouldExecute() {
        return this.theEntity.isInWater() || this.theEntity.isInLava();
    }

    @Override
    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8f) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
}

