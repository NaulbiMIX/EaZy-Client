/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionHealth
extends Potion {
    private static final String __OBFID = "CL_00001527";

    public PotionHealth(int p_i45898_1_, ResourceLocation p_i45898_2_, boolean p_i45898_3_, int p_i45898_4_) {
        super(p_i45898_1_, p_i45898_2_, p_i45898_3_, p_i45898_4_);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return p_76397_1_ >= 1;
    }
}
