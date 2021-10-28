/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  java.lang.Byte
 *  java.lang.Class
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.Random
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySheep
extends EntityAnimal {
    private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container(){

        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return false;
        }
    }, 2, 1);
    private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);
    private int sheepTimer;
    private EntityAIEatGrass entityAIEatGrass = new EntityAIEatGrass(this);

    public static float[] getDyeRgb(EnumDyeColor dyeColor) {
        return (float[])DYE_TO_RGB.get((Object)dyeColor);
    }

    public EntitySheep(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 1.3f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, this.entityAIEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
        this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
    }

    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.sheepTimer = Math.max((int)0, (int)(this.sheepTimer - 1));
        }
        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if (!this.getSheared()) {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor().getMetadata()), 0.0f);
        }
        int i = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + lootingModifier);
        for (int j = 0; j < i; ++j) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_mutton, 1);
                continue;
            }
            this.dropItem(Items.mutton, 1);
        }
    }

    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.wool);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.sheepTimer <= 0) {
            return 0.0f;
        }
        if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0f;
        }
        return this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0f : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0f;
    }

    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0f;
            return 0.62831855f + 0.2199115f * MathHelper.sin(f * 28.7f);
        }
        return this.sheepTimer > 0 ? 0.62831855f : this.rotationPitch / 57.295776f;
    }

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() == Items.shears && !this.getSheared() && !this.isChild()) {
            if (!this.worldObj.isRemote) {
                this.setSheared(true);
                int i = 1 + this.rand.nextInt(3);
                for (int j = 0; j < i; ++j) {
                    EntityItem entityitem = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor().getMetadata()), 1.0f);
                    entityitem.motionY += (double)(this.rand.nextFloat() * 0.05f);
                    entityitem.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                    entityitem.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                }
            }
            itemstack.damageItem(1, player);
            this.playSound("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.interact(player);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("Sheared", this.getSheared());
        tagCompound.setByte("Color", (byte)this.getFleeceColor().getMetadata());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.setSheared(tagCompund.getBoolean("Sheared"));
        this.setFleeceColor(EnumDyeColor.byMetadata(tagCompund.getByte("Color")));
    }

    @Override
    protected String getLivingSound() {
        return "mob.sheep.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.sheep.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.sheep.say";
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound("mob.sheep.step", 0.15f, 1.0f);
    }

    public EnumDyeColor getFleeceColor() {
        return EnumDyeColor.byMetadata(this.dataWatcher.getWatchableObjectByte(16) & 15);
    }

    public void setFleeceColor(EnumDyeColor color) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, (byte)(b0 & 240 | color.getMetadata() & 15));
    }

    public boolean getSheared() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        if (sheared) {
            this.dataWatcher.updateObject(16, (byte)(b0 | 16));
        } else {
            this.dataWatcher.updateObject(16, (byte)(b0 & -17));
        }
    }

    public static EnumDyeColor getRandomSheepColor(Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return EnumDyeColor.BLACK;
        }
        if (i < 10) {
            return EnumDyeColor.GRAY;
        }
        if (i < 15) {
            return EnumDyeColor.SILVER;
        }
        if (i < 18) {
            return EnumDyeColor.BROWN;
        }
        return random.nextInt(500) == 0 ? EnumDyeColor.PINK : EnumDyeColor.WHITE;
    }

    @Override
    public EntitySheep createChild(EntityAgeable ageable) {
        EntitySheep entitysheep = (EntitySheep)ageable;
        EntitySheep entitysheep1 = new EntitySheep(this.worldObj);
        entitysheep1.setFleeceColor(this.getDyeColorMixFromParents(this, entitysheep));
        return entitysheep1;
    }

    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setFleeceColor(EntitySheep.getRandomSheepColor(this.worldObj.rand));
        return livingdata;
    }

    private EnumDyeColor getDyeColorMixFromParents(EntityAnimal father, EntityAnimal mother) {
        int i = ((EntitySheep)father).getFleeceColor().getDyeDamage();
        int j = ((EntitySheep)mother).getFleeceColor().getDyeDamage();
        this.inventoryCrafting.getStackInSlot(0).setItemDamage(i);
        this.inventoryCrafting.getStackInSlot(1).setItemDamage(j);
        ItemStack itemstack = CraftingManager.getInstance().findMatchingRecipe(this.inventoryCrafting, ((EntitySheep)father).worldObj);
        int k = itemstack != null && itemstack.getItem() == Items.dye ? itemstack.getMetadata() : (this.worldObj.rand.nextBoolean() ? i : j);
        return EnumDyeColor.byDyeDamage(k);
    }

    @Override
    public float getEyeHeight() {
        return 0.95f * this.height;
    }

    static {
        DYE_TO_RGB.put((Object)EnumDyeColor.WHITE, (Object)new float[]{1.0f, 1.0f, 1.0f});
        DYE_TO_RGB.put((Object)EnumDyeColor.ORANGE, (Object)new float[]{0.85f, 0.5f, 0.2f});
        DYE_TO_RGB.put((Object)EnumDyeColor.MAGENTA, (Object)new float[]{0.7f, 0.3f, 0.85f});
        DYE_TO_RGB.put((Object)EnumDyeColor.LIGHT_BLUE, (Object)new float[]{0.4f, 0.6f, 0.85f});
        DYE_TO_RGB.put((Object)EnumDyeColor.YELLOW, (Object)new float[]{0.9f, 0.9f, 0.2f});
        DYE_TO_RGB.put((Object)EnumDyeColor.LIME, (Object)new float[]{0.5f, 0.8f, 0.1f});
        DYE_TO_RGB.put((Object)EnumDyeColor.PINK, (Object)new float[]{0.95f, 0.5f, 0.65f});
        DYE_TO_RGB.put((Object)EnumDyeColor.GRAY, (Object)new float[]{0.3f, 0.3f, 0.3f});
        DYE_TO_RGB.put((Object)EnumDyeColor.SILVER, (Object)new float[]{0.6f, 0.6f, 0.6f});
        DYE_TO_RGB.put((Object)EnumDyeColor.CYAN, (Object)new float[]{0.3f, 0.5f, 0.6f});
        DYE_TO_RGB.put((Object)EnumDyeColor.PURPLE, (Object)new float[]{0.5f, 0.25f, 0.7f});
        DYE_TO_RGB.put((Object)EnumDyeColor.BLUE, (Object)new float[]{0.2f, 0.3f, 0.7f});
        DYE_TO_RGB.put((Object)EnumDyeColor.BROWN, (Object)new float[]{0.4f, 0.3f, 0.2f});
        DYE_TO_RGB.put((Object)EnumDyeColor.GREEN, (Object)new float[]{0.4f, 0.5f, 0.2f});
        DYE_TO_RGB.put((Object)EnumDyeColor.RED, (Object)new float[]{0.6f, 0.2f, 0.2f});
        DYE_TO_RGB.put((Object)EnumDyeColor.BLACK, (Object)new float[]{0.1f, 0.1f, 0.1f});
    }

}

