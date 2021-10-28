/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;

public enum EnumCreatureType {
    MONSTER(IMob.class, 70, Material.air, false, false),
    CREATURE(EntityAnimal.class, 10, Material.air, true, true),
    AMBIENT(EntityAmbientCreature.class, 15, Material.air, true, false),
    WATER_CREATURE(EntityWaterMob.class, 5, Material.water, true, false);
    
    private final Class creatureClass;
    private final int maxNumberOfCreature;
    private final Material creatureMaterial;
    private final boolean isPeacefulCreature;
    private final boolean isAnimal;
    private static final String __OBFID = "CL_00001551";

    private EnumCreatureType(Class p_i1596_3_, int p_i1596_4_, Material p_i1596_5_, boolean p_i1596_6_, boolean p_i1596_7_) {
        this.creatureClass = p_i1596_3_;
        this.maxNumberOfCreature = p_i1596_4_;
        this.creatureMaterial = p_i1596_5_;
        this.isPeacefulCreature = p_i1596_6_;
        this.isAnimal = p_i1596_7_;
    }

    public Class getCreatureClass() {
        return this.creatureClass;
    }

    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }

    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
    }

    public boolean getAnimal() {
        return this.isAnimal;
    }
}

