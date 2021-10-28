/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Random
 *  java.util.Set
 */
package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenMineshaft
extends MapGenStructure {
    private double field_82673_e = 0.004;

    public MapGenMineshaft() {
    }

    @Override
    public String getStructureName() {
        return "Mineshaft";
    }

    public MapGenMineshaft(Map<String, String> p_i2034_1_) {
        for (Map.Entry entry : p_i2034_1_.entrySet()) {
            if (!((String)entry.getKey()).equals((Object)"chance")) continue;
            this.field_82673_e = MathHelper.parseDoubleWithDefault((String)entry.getValue(), this.field_82673_e);
        }
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max((int)Math.abs((int)chunkX), (int)Math.abs((int)chunkZ));
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new StructureMineshaftStart(this.worldObj, this.rand, chunkX, chunkZ);
    }
}

