/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.String
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderDragon
extends RenderLiving<EntityDragon> {
    private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
    private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
    private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    protected ModelDragon modelDragon;

    public RenderDragon(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelDragon(0.0f), 0.5f);
        this.modelDragon = (ModelDragon)this.mainModel;
        this.addLayer(new LayerEnderDragonEyes(this));
        this.addLayer(new LayerEnderDragonDeath());
    }

    @Override
    protected void rotateCorpse(EntityDragon bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
        float f = (float)bat.getMovementOffsets(7, partialTicks)[0];
        float f1 = (float)(bat.getMovementOffsets(5, partialTicks)[1] - bat.getMovementOffsets(10, partialTicks)[1]);
        GlStateManager.rotate(-f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f1 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, 1.0f);
        if (bat.deathTime > 0) {
            float f2 = ((float)bat.deathTime + partialTicks - 1.0f) / 20.0f * 1.6f;
            if ((f2 = MathHelper.sqrt_float(f2)) > 1.0f) {
                f2 = 1.0f;
            }
            GlStateManager.rotate(f2 * this.getDeathMaxRotation(bat), 0.0f, 0.0f, 1.0f);
        }
    }

    @Override
    protected void renderModel(EntityDragon entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
        if (entitylivingbaseIn.deathTicks > 0) {
            float f = (float)entitylivingbaseIn.deathTicks / 200.0f;
            GlStateManager.depthFunc(515);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, f);
            this.bindTexture(enderDragonExplodingTextures);
            this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.depthFunc(514);
        }
        this.bindEntityTexture(entitylivingbaseIn);
        this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
        if (entitylivingbaseIn.hurtTime > 0) {
            GlStateManager.depthFunc(514);
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 0.0f, 0.0f, 0.5f);
            this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.depthFunc(515);
        }
    }

    @Override
    public void doRender(EntityDragon entity, double x, double y, double z, float entityYaw, float partialTicks) {
        BossStatus.setBossStatus(entity, false);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        if (entity.healingEnderCrystal != null) {
            this.drawRechargeRay(entity, x, y, z, partialTicks);
        }
    }

    protected void drawRechargeRay(EntityDragon dragon, double p_180574_2_, double p_180574_4_, double p_180574_6_, float p_180574_8_) {
        float f = (float)dragon.healingEnderCrystal.innerRotation + p_180574_8_;
        float f1 = MathHelper.sin(f * 0.2f) / 2.0f + 0.5f;
        f1 = (f1 * f1 + f1) * 0.2f;
        float f2 = (float)(dragon.healingEnderCrystal.posX - dragon.posX - (dragon.prevPosX - dragon.posX) * (double)(1.0f - p_180574_8_));
        float f3 = (float)((double)f1 + dragon.healingEnderCrystal.posY - 1.0 - dragon.posY - (dragon.prevPosY - dragon.posY) * (double)(1.0f - p_180574_8_));
        float f4 = (float)(dragon.healingEnderCrystal.posZ - dragon.posZ - (dragon.prevPosZ - dragon.posZ) * (double)(1.0f - p_180574_8_));
        float f5 = MathHelper.sqrt_float(f2 * f2 + f4 * f4);
        float f6 = MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_180574_2_, (float)p_180574_4_ + 2.0f, (float)p_180574_6_);
        GlStateManager.rotate((float)(-Math.atan2((double)f4, (double)f2)) * 180.0f / 3.1415927f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((float)(-Math.atan2((double)f5, (double)f3)) * 180.0f / 3.1415927f - 90.0f, 1.0f, 0.0f, 0.0f);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        this.bindTexture(enderDragonCrystalBeamTextures);
        GlStateManager.shadeModel(7425);
        float f7 = 0.0f - ((float)dragon.ticksExisted + p_180574_8_) * 0.01f;
        float f8 = MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4) / 32.0f - ((float)dragon.ticksExisted + p_180574_8_) * 0.01f;
        worldrenderer.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
        int i = 8;
        for (int j = 0; j <= 8; ++j) {
            float f9 = MathHelper.sin((float)(j % 8) * 3.1415927f * 2.0f / 8.0f) * 0.75f;
            float f10 = MathHelper.cos((float)(j % 8) * 3.1415927f * 2.0f / 8.0f) * 0.75f;
            float f11 = (float)(j % 8) * 1.0f / 8.0f;
            worldrenderer.pos(f9 * 0.2f, f10 * 0.2f, 0.0).tex(f11, f8).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos(f9, f10, f6).tex(f11, f7).color(255, 255, 255, 255).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableCull();
        GlStateManager.shadeModel(7424);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDragon entity) {
        return enderDragonTextures;
    }
}

