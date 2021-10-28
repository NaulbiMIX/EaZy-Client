/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;

public interface IRandomEntity {
    public int getId();

    public BlockPos getSpawnPosition();

    public BiomeGenBase getSpawnBiome();

    public String getName();

    public int getHealth();

    public int getMaxHealth();
}

