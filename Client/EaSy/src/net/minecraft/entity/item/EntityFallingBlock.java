/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  java.lang.Comparable
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Random
 *  java.util.Set
 */
package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSand;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityFallingBlock
extends Entity {
    private IBlockState fallTile;
    public int fallTime;
    public boolean shouldDropItem = true;
    private boolean canSetAsBlock;
    private boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0f;
    public NBTTagCompound tileEntityData;

    public EntityFallingBlock(World worldIn) {
        super(worldIn);
    }

    public EntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
        super(worldIn);
        this.fallTile = fallingBlockState;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.setPosition(x, y, z);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        Block block = this.fallTile.getBlock();
        if (block.getMaterial() == Material.air) {
            this.setDead();
        } else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            if (this.fallTime++ == 0) {
                BlockPos blockpos = new BlockPos(this);
                if (this.worldObj.getBlockState(blockpos).getBlock() == block) {
                    this.worldObj.setBlockToAir(blockpos);
                } else if (!this.worldObj.isRemote) {
                    this.setDead();
                    return;
                }
            }
            this.motionY -= 0.03999999910593033;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (!this.worldObj.isRemote) {
                BlockPos blockpos1 = new BlockPos(this);
                if (this.onGround) {
                    this.motionX *= 0.699999988079071;
                    this.motionZ *= 0.699999988079071;
                    this.motionY *= -0.5;
                    if (this.worldObj.getBlockState(blockpos1).getBlock() != Blocks.piston_extension) {
                        this.setDead();
                        if (!this.canSetAsBlock) {
                            if (this.worldObj.canBlockBePlaced(block, blockpos1, true, EnumFacing.UP, null, null) && !BlockFalling.canFallInto(this.worldObj, blockpos1.down()) && this.worldObj.setBlockState(blockpos1, this.fallTile, 3)) {
                                TileEntity tileentity;
                                if (block instanceof BlockFalling) {
                                    ((BlockFalling)block).onEndFalling(this.worldObj, blockpos1);
                                }
                                if (this.tileEntityData != null && block instanceof ITileEntityProvider && (tileentity = this.worldObj.getTileEntity(blockpos1)) != null) {
                                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                                    tileentity.writeToNBT(nbttagcompound);
                                    for (String s : this.tileEntityData.getKeySet()) {
                                        NBTBase nbtbase = this.tileEntityData.getTag(s);
                                        if (s.equals((Object)"x") || s.equals((Object)"y") || s.equals((Object)"z")) continue;
                                        nbttagcompound.setTag(s, nbtbase.copy());
                                    }
                                    tileentity.readFromNBT(nbttagcompound);
                                    tileentity.markDirty();
                                }
                            } else if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                                this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0f);
                            }
                        }
                    }
                } else if (this.fallTime > 100 && !this.worldObj.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600) {
                    if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                        this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0f);
                    }
                    this.setDead();
                }
            }
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        int i;
        Block block = this.fallTile.getBlock();
        if (this.hurtEntities && (i = MathHelper.ceiling_float_int(distance - 1.0f)) > 0) {
            ArrayList list = Lists.newArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
            boolean flag = block == Blocks.anvil;
            DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
            for (Entity entity : list) {
                entity.attackEntityFrom(damagesource, Math.min((int)MathHelper.floor_float((float)i * this.fallHurtAmount), (int)this.fallHurtMax));
            }
            if (flag && (double)this.rand.nextFloat() < 0.05000000074505806 + (double)i * 0.05) {
                int j = this.fallTile.getValue(BlockAnvil.DAMAGE);
                if (++j > 2) {
                    this.canSetAsBlock = true;
                } else {
                    this.fallTile = this.fallTile.withProperty(BlockAnvil.DAMAGE, j);
                }
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        Block block = this.fallTile != null ? this.fallTile.getBlock() : Blocks.air;
        ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(block);
        tagCompound.setString("Block", resourcelocation == null ? "" : resourcelocation.toString());
        tagCompound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
        tagCompound.setByte("Time", (byte)this.fallTime);
        tagCompound.setBoolean("DropItem", this.shouldDropItem);
        tagCompound.setBoolean("HurtEntities", this.hurtEntities);
        tagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        tagCompound.setInteger("FallHurtMax", this.fallHurtMax);
        if (this.tileEntityData != null) {
            tagCompound.setTag("TileEntityData", this.tileEntityData);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {
        int i = tagCompund.getByte("Data") & 255;
        this.fallTile = tagCompund.hasKey("Block", 8) ? Block.getBlockFromName(tagCompund.getString("Block")).getStateFromMeta(i) : (tagCompund.hasKey("TileID", 99) ? Block.getBlockById(tagCompund.getInteger("TileID")).getStateFromMeta(i) : Block.getBlockById(tagCompund.getByte("Tile") & 255).getStateFromMeta(i));
        this.fallTime = tagCompund.getByte("Time") & 255;
        Block block = this.fallTile.getBlock();
        if (tagCompund.hasKey("HurtEntities", 99)) {
            this.hurtEntities = tagCompund.getBoolean("HurtEntities");
            this.fallHurtAmount = tagCompund.getFloat("FallHurtAmount");
            this.fallHurtMax = tagCompund.getInteger("FallHurtMax");
        } else if (block == Blocks.anvil) {
            this.hurtEntities = true;
        }
        if (tagCompund.hasKey("DropItem", 99)) {
            this.shouldDropItem = tagCompund.getBoolean("DropItem");
        }
        if (tagCompund.hasKey("TileEntityData", 10)) {
            this.tileEntityData = tagCompund.getCompoundTag("TileEntityData");
        }
        if (block == null || block.getMaterial() == Material.air) {
            this.fallTile = Blocks.sand.getDefaultState();
        }
    }

    public World getWorldObj() {
        return this.worldObj;
    }

    public void setHurtEntities(boolean p_145806_1_) {
        this.hurtEntities = p_145806_1_;
    }

    @Override
    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    public void addEntityCrashInfo(CrashReportCategory category) {
        super.addEntityCrashInfo(category);
        if (this.fallTile != null) {
            Block block = this.fallTile.getBlock();
            category.addCrashSection("Immitating block ID", Block.getIdFromBlock(block));
            category.addCrashSection("Immitating block data", block.getMetaFromState(this.fallTile));
        }
    }

    public IBlockState getBlock() {
        return this.fallTile;
    }
}

