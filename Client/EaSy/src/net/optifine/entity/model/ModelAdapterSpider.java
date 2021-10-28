/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterSpider
extends ModelAdapter {
    public ModelAdapterSpider() {
        super(EntitySpider.class, "spider", 1.0f);
    }

    protected ModelAdapterSpider(Class entityClass, String name, float shadowSize) {
        super(entityClass, name, shadowSize);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelSpider();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelSpider)) {
            return null;
        }
        ModelSpider modelspider = (ModelSpider)model;
        if (modelPart.equals((Object)"head")) {
            return modelspider.spiderHead;
        }
        if (modelPart.equals((Object)"neck")) {
            return modelspider.spiderNeck;
        }
        if (modelPart.equals((Object)"body")) {
            return modelspider.spiderBody;
        }
        if (modelPart.equals((Object)"leg1")) {
            return modelspider.spiderLeg1;
        }
        if (modelPart.equals((Object)"leg2")) {
            return modelspider.spiderLeg2;
        }
        if (modelPart.equals((Object)"leg3")) {
            return modelspider.spiderLeg3;
        }
        if (modelPart.equals((Object)"leg4")) {
            return modelspider.spiderLeg4;
        }
        if (modelPart.equals((Object)"leg5")) {
            return modelspider.spiderLeg5;
        }
        if (modelPart.equals((Object)"leg6")) {
            return modelspider.spiderLeg6;
        }
        if (modelPart.equals((Object)"leg7")) {
            return modelspider.spiderLeg7;
        }
        return modelPart.equals((Object)"leg8") ? modelspider.spiderLeg8 : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", "leg8"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderSpider renderspider = new RenderSpider(rendermanager);
        renderspider.mainModel = modelBase;
        renderspider.shadowSize = shadowSize;
        return renderspider;
    }
}

