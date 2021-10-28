/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 */
package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAILookAtTradePlayer
extends EntityAIWatchClosest {
    private final EntityVillager theMerchant;

    public EntityAILookAtTradePlayer(EntityVillager theMerchantIn) {
        super(theMerchantIn, EntityPlayer.class, 8.0f);
        this.theMerchant = theMerchantIn;
    }

    @Override
    public boolean shouldExecute() {
        if (this.theMerchant.isTrading()) {
            this.closestEntity = this.theMerchant.getCustomer();
            return true;
        }
        return false;
    }
}

