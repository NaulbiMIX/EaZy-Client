/*
 * Decompiled with CFR 0.0.
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityAIRestrictSun
extends EntityAIBase {
    private EntityCreature theEntity;

    public EntityAIRestrictSun(EntityCreature creature) {
        this.theEntity = creature;
    }

    @Override
    public boolean shouldExecute() {
        return this.theEntity.worldObj.isDaytime();
    }

    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(true);
    }

    @Override
    public void resetTask() {
        ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(false);
    }
}

