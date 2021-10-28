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
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityBat;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorFields;

public class ModelAdapterBat
extends ModelAdapter {
    public ModelAdapterBat() {
        super(EntityBat.class, "bat", 0.25f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelBat();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelBat)) {
            return null;
        }
        ModelBat modelbat = (ModelBat)model;
        if (modelPart.equals((Object)"head")) {
            return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 0);
        }
        if (modelPart.equals((Object)"body")) {
            return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 1);
        }
        if (modelPart.equals((Object)"right_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 2);
        }
        if (modelPart.equals((Object)"left_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 3);
        }
        if (modelPart.equals((Object)"outer_right_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 4);
        }
        return modelPart.equals((Object)"outer_left_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 5) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderBat renderbat = new RenderBat(rendermanager);
        renderbat.mainModel = modelBase;
        renderbat.shadowSize = shadowSize;
        return renderbat;
    }
}

