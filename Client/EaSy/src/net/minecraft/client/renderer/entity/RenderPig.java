/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderPig
extends RenderLiving<EntityPig> {
    private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");

    public RenderPig(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
        this.addLayer(new LayerSaddle(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPig entity) {
        return pigTextures;
    }
}

