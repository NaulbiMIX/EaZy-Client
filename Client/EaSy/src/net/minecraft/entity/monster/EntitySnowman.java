/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  java.lang.Class
 *  java.lang.String
 *  java.util.Random
 */
package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntitySnowman
extends EntityGolem
implements IRangedAttackMob {
    public EntitySnowman(World worldIn) {
        super(worldIn);
        this.setSize(0.7f, 1.9f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<Entity>(this, EntityLiving.class, 10, true, false, IMob.mobSelector));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY);
            int k = MathHelper.floor_double(this.posZ);
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.drown, 1.0f);
            }
            BlockPos blockPos = new BlockPos(i, 0, k);
            BlockPos blockPos2 = new BlockPos(i, j, k);
            if (this.worldObj.getBiomeGenForCoords(blockPos).getFloatTemperature(blockPos2) > 1.0f) {
                this.attackEntityFrom(DamageSource.onFire, 1.0f);
            }
            for (int l = 0; l < 4; ++l) {
                i = MathHelper.floor_double(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25f));
                BlockPos blockpos = new BlockPos(i, j = MathHelper.floor_double(this.posY), k = MathHelper.floor_double(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25f)));
                if (this.worldObj.getBlockState(blockpos).getBlock().getMaterial() != Material.air) continue;
                BlockPos blockPos3 = new BlockPos(i, 0, k);
                if (!(this.worldObj.getBiomeGenForCoords(blockPos3).getFloatTemperature(blockpos) < 0.8f) || !Blocks.snow_layer.canPlaceBlockAt(this.worldObj, blockpos)) continue;
                this.worldObj.setBlockState(blockpos, Blocks.snow_layer.getDefaultState());
            }
        }
    }

    @Override
    protected Item getDropItem() {
        return Items.snowball;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int i = this.rand.nextInt(16);
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.snowball, 1);
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
        EntitySnowball entitysnowball = new EntitySnowball(this.worldObj, this);
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858;
        double d1 = target.posX - this.posX;
        double d2 = d0 - entitysnowball.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 0.2f;
        entitysnowball.setThrowableHeading(d1, d2 + (double)f, d3, 1.6f, 12.0f);
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(entitysnowball);
    }

    @Override
    public float getEyeHeight() {
        return 1.7f;
    }
}

