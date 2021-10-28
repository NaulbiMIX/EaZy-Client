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
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorFields;

public class ModelAdapterSlime
extends ModelAdapter {
    public ModelAdapterSlime() {
        super(EntitySlime.class, "slime", 0.25f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelSlime(16);
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelSlime)) {
            return null;
        }
        ModelSlime modelslime = (ModelSlime)model;
        if (modelPart.equals((Object)"body")) {
            return (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 0);
        }
        if (modelPart.equals((Object)"left_eye")) {
            return (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 1);
        }
        if (modelPart.equals((Object)"right_eye")) {
            return (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 2);
        }
        return modelPart.equals((Object)"mouth") ? (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 3) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "left_eye", "right_eye", "mouth"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderSlime renderslime = new RenderSlime(rendermanager, modelBase, shadowSize);
        return renderslime;
    }
}

