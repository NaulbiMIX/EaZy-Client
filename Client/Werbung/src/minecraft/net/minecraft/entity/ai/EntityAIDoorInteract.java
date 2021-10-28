/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAIDoorInteract
extends EntityAIBase {
    protected EntityLiving theEntity;
    protected BlockPos field_179507_b = BlockPos.ORIGIN;
    protected BlockDoor doorBlock;
    boolean hasStoppedDoorInteraction;
    float entityPositionX;
    float entityPositionZ;
    private static final String __OBFID = "CL_00001581";

    public EntityAIDoorInteract(EntityLiving p_i1621_1_) {
        this.theEntity = p_i1621_1_;
        if (!(p_i1621_1_.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isCollidedHorizontally) {
            return false;
        }
        PathNavigateGround var1 = (PathNavigateGround)this.theEntity.getNavigator();
        PathEntity var2 = var1.getPath();
        if (var2 != null && !var2.isFinished() && var1.func_179686_g()) {
            for (int var3 = 0; var3 < Math.min(var2.getCurrentPathIndex() + 2, var2.getCurrentPathLength()); ++var3) {
                PathPoint var4 = var2.getPathPointFromIndex(var3);
                this.field_179507_b = new BlockPos(var4.xCoord, var4.yCoord + 1, var4.zCoord);
                if (!(this.theEntity.getDistanceSq(this.field_179507_b.getX(), this.theEntity.posY, this.field_179507_b.getZ()) <= 2.25)) continue;
                this.doorBlock = this.func_179506_a(this.field_179507_b);
                if (this.doorBlock == null) continue;
                return true;
            }
            this.field_179507_b = new BlockPos(this.theEntity).offsetUp();
            this.doorBlock = this.func_179506_a(this.field_179507_b);
            return this.doorBlock != null;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return !this.hasStoppedDoorInteraction;
    }

    @Override
    public void startExecuting() {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float)((double)((float)this.field_179507_b.getX() + 0.5f) - this.theEntity.posX);
        this.entityPositionZ = (float)((double)((float)this.field_179507_b.getZ() + 0.5f) - this.theEntity.posZ);
    }

    @Override
    public void updateTask() {
        float var2;
        float var1 = (float)((double)((float)this.field_179507_b.getX() + 0.5f) - this.theEntity.posX);
        float var3 = this.entityPositionX * var1 + this.entityPositionZ * (var2 = (float)((double)((float)this.field_179507_b.getZ() + 0.5f) - this.theEntity.posZ));
        if (var3 < 0.0f) {
            this.hasStoppedDoorInteraction = true;
        }
    }

    private BlockDoor func_179506_a(BlockPos p_179506_1_) {
        Block var2 = this.theEntity.worldObj.getBlockState(p_179506_1_).getBlock();
        return var2 instanceof BlockDoor && var2.getMaterial() == Material.wood ? (BlockDoor)var2 : null;
    }
}

