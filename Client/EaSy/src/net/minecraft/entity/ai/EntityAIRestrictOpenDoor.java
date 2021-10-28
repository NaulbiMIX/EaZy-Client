/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.String
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;

public class EntityAIRestrictOpenDoor
extends EntityAIBase {
    private EntityCreature entityObj;
    private VillageDoorInfo frontDoor;

    public EntityAIRestrictOpenDoor(EntityCreature creatureIn) {
        this.entityObj = creatureIn;
        if (!(creatureIn.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
        }
    }

    @Override
    public boolean shouldExecute() {
        if (this.entityObj.worldObj.isDaytime()) {
            return false;
        }
        BlockPos blockpos = new BlockPos(this.entityObj);
        Village village = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockpos, 16);
        if (village == null) {
            return false;
        }
        this.frontDoor = village.getNearestDoor(blockpos);
        if (this.frontDoor == null) {
            return false;
        }
        return (double)this.frontDoor.getDistanceToInsideBlockSq(blockpos) < 2.25;
    }

    @Override
    public boolean continueExecuting() {
        if (this.entityObj.worldObj.isDaytime()) {
            return false;
        }
        return !this.frontDoor.getIsDetachedFromVillageFlag() && this.frontDoor.func_179850_c(new BlockPos(this.entityObj));
    }

    @Override
    public void startExecuting() {
        ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
        ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
    }

    @Override
    public void resetTask() {
        ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
        this.frontDoor = null;
    }

    @Override
    public void updateTask() {
        this.frontDoor.incrementDoorOpeningRestrictionCounter();
    }
}

