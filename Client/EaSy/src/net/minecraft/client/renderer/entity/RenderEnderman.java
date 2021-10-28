/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.util.Random
 */
package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class RenderEnderman
extends RenderLiving<EntityEnderman> {
    private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
    private ModelEnderman endermanModel = (ModelEnderman)this.mainModel;
    private Random rnd = new Random();

    public RenderEnderman(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelEnderman(0.0f), 0.5f);
        this.addLayer(new LayerEndermanEyes(this));
        this.addLayer(new LayerHeldBlock(this));
    }

    @Override
    public void doRender(EntityEnderman entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.endermanModel.isCarrying = entity.getHeldBlockState().getBlock().getMaterial() != Material.air;
        this.endermanModel.isAttacking = entity.isScreaming();
        if (entity.isScreaming()) {
            double d0 = 0.02;
            x += this.rnd.nextGaussian() * d0;
            z += this.rnd.nextGaussian() * d0;
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityEnderman entity) {
        return endermanTextures;
    }
}

