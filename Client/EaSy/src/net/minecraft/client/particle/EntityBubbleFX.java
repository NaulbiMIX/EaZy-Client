/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.util.Random
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBubbleFX
extends EntityFX {
    protected EntityBubbleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(32);
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.motionX = xSpeedIn * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.motionY = ySpeedIn * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.motionZ = zSpeedIn * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8500000238418579;
        this.motionY *= 0.8500000238418579;
        this.motionZ *= 0.8500000238418579;
        if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
            this.setDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }

    public static class Factory
    implements IParticleFactory {
        @Override
        public /* varargs */ EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            return new EntityBubbleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }

}

