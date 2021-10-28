/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner
extends EntityMinecart {
    private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic(){
        private static final String __OBFID = "CL_00001679";

        @Override
        public void func_98267_a(int p_98267_1_) {
            EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)p_98267_1_);
        }

        @Override
        public World getSpawnerWorld() {
            return EntityMinecartMobSpawner.this.worldObj;
        }

        @Override
        public BlockPos func_177221_b() {
            return new BlockPos(EntityMinecartMobSpawner.this);
        }
    };
    private static final String __OBFID = "CL_00001678";

    public EntityMinecartMobSpawner(World worldIn) {
        super(worldIn);
    }

    public EntityMinecartMobSpawner(World worldIn, double p_i1726_2_, double p_i1726_4_, double p_i1726_6_) {
        super(worldIn, p_i1726_2_, p_i1726_4_, p_i1726_6_);
    }

    @Override
    public EntityMinecart.EnumMinecartType func_180456_s() {
        return EntityMinecart.EnumMinecartType.SPAWNER;
    }

    @Override
    public IBlockState func_180457_u() {
        return Blocks.mob_spawner.getDefaultState();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.mobSpawnerLogic.readFromNBT(tagCompund);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        this.mobSpawnerLogic.writeToNBT(tagCompound);
    }

    @Override
    public void handleHealthUpdate(byte p_70103_1_) {
        this.mobSpawnerLogic.setDelayToMin(p_70103_1_);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }

    public MobSpawnerBaseLogic func_98039_d() {
        return this.mobSpawnerLogic;
    }

}

