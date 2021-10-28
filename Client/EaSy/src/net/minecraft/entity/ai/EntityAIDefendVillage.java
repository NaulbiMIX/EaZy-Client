/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.util.Random
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.village.Village;

public class EntityAIDefendVillage
extends EntityAITarget {
    EntityIronGolem irongolem;
    EntityLivingBase villageAgressorTarget;

    public EntityAIDefendVillage(EntityIronGolem ironGolemIn) {
        super(ironGolemIn, false, true);
        this.irongolem = ironGolemIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Village village = this.irongolem.getVillage();
        if (village == null) {
            return false;
        }
        this.villageAgressorTarget = village.findNearestVillageAggressor(this.irongolem);
        if (this.villageAgressorTarget instanceof EntityCreeper) {
            return false;
        }
        if (!this.isSuitableTarget(this.villageAgressorTarget, false)) {
            if (this.taskOwner.getRNG().nextInt(20) == 0) {
                this.villageAgressorTarget = village.getNearestTargetPlayer(this.irongolem);
                return this.isSuitableTarget(this.villageAgressorTarget, false);
            }
            return false;
        }
        return true;
    }

    @Override
    public void startExecuting() {
        this.irongolem.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
    }
}

