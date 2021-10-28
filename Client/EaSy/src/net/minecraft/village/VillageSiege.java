/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Random
 */
package net.minecraft.village;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

public class VillageSiege {
    private World worldObj;
    private boolean field_75535_b;
    private int field_75536_c = -1;
    private int field_75533_d;
    private int field_75534_e;
    private Village theVillage;
    private int field_75532_g;
    private int field_75538_h;
    private int field_75539_i;

    public VillageSiege(World worldIn) {
        this.worldObj = worldIn;
    }

    public void tick() {
        if (this.worldObj.isDaytime()) {
            this.field_75536_c = 0;
        } else if (this.field_75536_c != 2) {
            if (this.field_75536_c == 0) {
                float f = this.worldObj.getCelestialAngle(0.0f);
                if ((double)f < 0.5 || (double)f > 0.501) {
                    return;
                }
                this.field_75536_c = this.worldObj.rand.nextInt(10) == 0 ? 1 : 2;
                this.field_75535_b = false;
                if (this.field_75536_c == 2) {
                    return;
                }
            }
            if (this.field_75536_c != -1) {
                if (!this.field_75535_b) {
                    if (!this.func_75529_b()) {
                        return;
                    }
                    this.field_75535_b = true;
                }
                if (this.field_75534_e > 0) {
                    --this.field_75534_e;
                } else {
                    this.field_75534_e = 2;
                    if (this.field_75533_d > 0) {
                        this.spawnZombie();
                        --this.field_75533_d;
                    } else {
                        this.field_75536_c = 2;
                    }
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean func_75529_b() {
        list = this.worldObj.playerEntities;
        iterator = list.iterator();
        do lbl-1000: // 4 sources:
        {
            if (!iterator.hasNext()) {
                return false;
            }
            entityplayer = (EntityPlayer)iterator.next();
            if (entityplayer.isSpectator()) ** GOTO lbl-1000
            this.theVillage = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(entityplayer), 1);
            if (this.theVillage == null || this.theVillage.getNumVillageDoors() < 10 || this.theVillage.getTicksSinceLastDoorAdding() < 20 || this.theVillage.getNumVillagers() < 20) ** GOTO lbl-1000
            blockpos = this.theVillage.getCenter();
            f = this.theVillage.getVillageRadius();
            flag = false;
            for (i = 0; i < 10; ++i) {
                f1 = this.worldObj.rand.nextFloat() * 3.1415927f * 2.0f;
                this.field_75532_g = blockpos.getX() + (int)((double)(MathHelper.cos(f1) * f) * 0.9);
                this.field_75538_h = blockpos.getY();
                this.field_75539_i = blockpos.getZ() + (int)((double)(MathHelper.sin(f1) * f) * 0.9);
                flag = false;
                for (Village village : this.worldObj.getVillageCollection().getVillageList()) {
                    if (village == this.theVillage || !village.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) continue;
                    flag = true;
                    break;
                }
                if (!flag) break;
            }
            if (!flag) continue;
            return false;
        } while ((vec3 = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) == null);
        this.field_75534_e = 0;
        this.field_75533_d = 20;
        return true;
    }

    private boolean spawnZombie() {
        EntityZombie entityzombie;
        Vec3 vec3 = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
        if (vec3 == null) {
            return false;
        }
        try {
            entityzombie = new EntityZombie(this.worldObj);
            entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityzombie)), null);
            entityzombie.setVillager(false);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        entityzombie.setLocationAndAngles(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(entityzombie);
        BlockPos blockpos = this.theVillage.getCenter();
        entityzombie.setHomePosAndDistance(blockpos, this.theVillage.getVillageRadius());
        return true;
    }

    private Vec3 func_179867_a(BlockPos p_179867_1_) {
        for (int i = 0; i < 10; ++i) {
            BlockPos blockpos = p_179867_1_.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
            if (!this.theVillage.func_179866_a(blockpos) || !SpawnerAnimals.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, blockpos)) continue;
            return new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        }
        return null;
    }
}

