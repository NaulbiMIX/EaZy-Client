/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.util.List
 *  java.util.Random
 */
package net.minecraft.world.biome;

import java.util.List;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenOcean
extends BiomeGenBase {
    public BiomeGenOcean(int id) {
        super(id);
        this.spawnableCreatureList.clear();
    }

    @Override
    public BiomeGenBase.TempCategory getTempCategory() {
        return BiomeGenBase.TempCategory.OCEAN;
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
}

