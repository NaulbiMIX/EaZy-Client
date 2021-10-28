/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Random
 */
package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenNetherBridge
extends MapGenStructure {
    private List<BiomeGenBase.SpawnListEntry> spawnList = Lists.newArrayList();

    public MapGenNetherBridge() {
        this.spawnList.add((Object)new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
        this.spawnList.add((Object)new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
        this.spawnList.add((Object)new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        this.spawnList.add((Object)new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
    }

    @Override
    public String getStructureName() {
        return "Fortress";
    }

    public List<BiomeGenBase.SpawnListEntry> getSpawnList() {
        return this.spawnList;
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        int i = chunkX >> 4;
        int j = chunkZ >> 4;
        this.rand.setSeed((long)(i ^ j << 4) ^ this.worldObj.getSeed());
        this.rand.nextInt();
        if (this.rand.nextInt(3) != 0) {
            return false;
        }
        if (chunkX != (i << 4) + 4 + this.rand.nextInt(8)) {
            return false;
        }
        return chunkZ == (j << 4) + 4 + this.rand.nextInt(8);
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new Start(this.worldObj, this.rand, chunkX, chunkZ);
    }

    public static class Start
    extends StructureStart {
        public Start() {
        }

        public Start(World worldIn, Random p_i2040_2_, int p_i2040_3_, int p_i2040_4_) {
            super(p_i2040_3_, p_i2040_4_);
            StructureNetherBridgePieces.Start structurenetherbridgepieces$start = new StructureNetherBridgePieces.Start(p_i2040_2_, (p_i2040_3_ << 4) + 2, (p_i2040_4_ << 4) + 2);
            this.components.add((Object)structurenetherbridgepieces$start);
            structurenetherbridgepieces$start.buildComponent(structurenetherbridgepieces$start, (List<StructureComponent>)this.components, p_i2040_2_);
            List<StructureComponent> list = structurenetherbridgepieces$start.field_74967_d;
            while (!list.isEmpty()) {
                int i = p_i2040_2_.nextInt(list.size());
                StructureComponent structurecomponent = (StructureComponent)list.remove(i);
                structurecomponent.buildComponent(structurenetherbridgepieces$start, (List<StructureComponent>)this.components, p_i2040_2_);
            }
            this.updateBoundingBox();
            this.setRandomHeight(worldIn, p_i2040_2_, 48, 70);
        }
    }

}

