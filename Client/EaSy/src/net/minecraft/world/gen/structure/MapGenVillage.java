/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Random
 *  java.util.Set
 */
package net.minecraft.world.gen.structure;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class MapGenVillage
extends MapGenStructure {
    public static final List<BiomeGenBase> villageSpawnBiomes = Arrays.asList((Object[])new BiomeGenBase[]{BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna});
    private int terrainType;
    private int field_82665_g = 32;
    private int field_82666_h = 8;

    public MapGenVillage() {
    }

    public MapGenVillage(Map<String, String> p_i2093_1_) {
        this();
        for (Map.Entry entry : p_i2093_1_.entrySet()) {
            if (((String)entry.getKey()).equals((Object)"size")) {
                this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.terrainType, 0);
                continue;
            }
            if (!((String)entry.getKey()).equals((Object)"distance")) continue;
            this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
        }
    }

    @Override
    public String getStructureName() {
        return "Village";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        boolean flag;
        int i = chunkX;
        int j = chunkZ;
        if (chunkX < 0) {
            chunkX -= this.field_82665_g - 1;
        }
        if (chunkZ < 0) {
            chunkZ -= this.field_82665_g - 1;
        }
        int k = chunkX / this.field_82665_g;
        int l = chunkZ / this.field_82665_g;
        Random random = this.worldObj.setRandomSeed(k, l, 10387312);
        k *= this.field_82665_g;
        l *= this.field_82665_g;
        return i == (k += random.nextInt(this.field_82665_g - this.field_82666_h)) && j == (l += random.nextInt(this.field_82665_g - this.field_82666_h)) && (flag = this.worldObj.getWorldChunkManager().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, villageSpawnBiomes));
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new Start(this.worldObj, this.rand, chunkX, chunkZ, this.terrainType);
    }

    public static class Start
    extends StructureStart {
        private boolean hasMoreThanTwoComponents;

        public Start() {
        }

        public Start(World worldIn, Random rand, int x, int z, int size) {
            super(x, z);
            List<StructureVillagePieces.PieceWeight> list = StructureVillagePieces.getStructureVillageWeightedPieceList(rand, size);
            StructureVillagePieces.Start structurevillagepieces$start = new StructureVillagePieces.Start(worldIn.getWorldChunkManager(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, size);
            this.components.add((Object)structurevillagepieces$start);
            structurevillagepieces$start.buildComponent(structurevillagepieces$start, (List<StructureComponent>)this.components, rand);
            List<StructureComponent> list1 = structurevillagepieces$start.field_74930_j;
            List<StructureComponent> list2 = structurevillagepieces$start.field_74932_i;
            while (!list1.isEmpty() || !list2.isEmpty()) {
                if (list1.isEmpty()) {
                    int i = rand.nextInt(list2.size());
                    StructureComponent structurecomponent = (StructureComponent)list2.remove(i);
                    structurecomponent.buildComponent(structurevillagepieces$start, (List<StructureComponent>)this.components, rand);
                    continue;
                }
                int j = rand.nextInt(list1.size());
                StructureComponent structurecomponent2 = (StructureComponent)list1.remove(j);
                structurecomponent2.buildComponent(structurevillagepieces$start, (List<StructureComponent>)this.components, rand);
            }
            this.updateBoundingBox();
            int k = 0;
            for (StructureComponent structurecomponent1 : this.components) {
                if (structurecomponent1 instanceof StructureVillagePieces.Road) continue;
                ++k;
            }
            this.hasMoreThanTwoComponents = k > 2;
        }

        @Override
        public boolean isSizeableStructure() {
            return this.hasMoreThanTwoComponents;
        }

        @Override
        public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
        }

        @Override
        public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
            this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
        }
    }

}

