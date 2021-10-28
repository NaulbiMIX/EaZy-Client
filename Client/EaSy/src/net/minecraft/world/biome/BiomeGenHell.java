/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.List
 */
package net.minecraft.world.biome;

import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenHell
extends BiomeGenBase {
    public BiomeGenHell(int id) {
        super(id);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add((Object)new BiomeGenBase.SpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add((Object)new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add((Object)new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
    }
}

