/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItemWitch;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderWitch
extends RenderLiving {
    private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");
    private static final String __OBFID = "CL_00001033";

    public RenderWitch(RenderManager p_i46131_1_) {
        super(p_i46131_1_, new ModelWitch(0.0f), 0.5f);
        this.addLayer(new LayerHeldItemWitch(this));
    }

    public void func_180590_a(EntityWitch p_180590_1_, double p_180590_2_, double p_180590_4_, double p_180590_6_, float p_180590_8_, float p_180590_9_) {
        ((ModelWitch)this.mainModel).field_82900_g = p_180590_1_.getHeldItem() != null;
        super.doRender(p_180590_1_, p_180590_2_, p_180590_4_, p_180590_6_, p_180590_8_, p_180590_9_);
    }

    protected ResourceLocation func_180589_a(EntityWitch p_180589_1_) {
        return witchTextures;
    }

    @Override
    public void func_82422_c() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }

    protected void preRenderCallback(EntityWitch p_77041_1_, float p_77041_2_) {
        float var3 = 0.9375f;
        GlStateManager.scale(var3, var3, var3);
    }

    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.func_180590_a((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
        this.preRenderCallback((EntityWitch)p_77041_1_, p_77041_2_);
    }

    @Override
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.func_180590_a((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.func_180589_a((EntityWitch)p_110775_1_);
    }

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.func_180590_a((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}

