/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.util.List
 *  java.util.Random
 */
package net.minecraft.entity.monster;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube
extends EntitySlime {
    public EntityMagmaCube(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }

    @Override
    public int getTotalArmorValue() {
        return this.getSlimeSize() * 3;
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        return 15728880;
    }

    @Override
    public float getBrightness(float partialTicks) {
        return 1.0f;
    }

    @Override
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.FLAME;
    }

    @Override
    protected EntitySlime createInstance() {
        return new EntityMagmaCube(this.worldObj);
    }

    @Override
    protected Item getDropItem() {
        return Items.magma_cream;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        Item item = this.getDropItem();
        if (item != null && this.getSlimeSize() > 1) {
            int i = this.rand.nextInt(4) - 2;
            if (lootingModifier > 0) {
                i += this.rand.nextInt(lootingModifier + 1);
            }
            for (int j = 0; j < i; ++j) {
                this.dropItem(item, 1);
            }
        }
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }

    @Override
    protected void alterSquishAmount() {
        this.squishAmount *= 0.9f;
    }

    @Override
    protected void jump() {
        this.motionY = 0.42f + (float)this.getSlimeSize() * 0.1f;
        this.isAirBorne = true;
    }

    @Override
    protected void handleJumpLava() {
        this.motionY = 0.22f + (float)this.getSlimeSize() * 0.05f;
        this.isAirBorne = true;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected boolean canDamagePlayer() {
        return true;
    }

    @Override
    protected int getAttackStrength() {
        return super.getAttackStrength() + 2;
    }

    @Override
    protected String getJumpSound() {
        return this.getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    @Override
    protected boolean makesSoundOnLand() {
        return true;
    }
}

