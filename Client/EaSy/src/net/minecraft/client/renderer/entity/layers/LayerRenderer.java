/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.EntityLivingBase;

public interface LayerRenderer<E extends EntityLivingBase> {
    public void doRenderLayer(E var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    public boolean shouldCombineTextures();
}
