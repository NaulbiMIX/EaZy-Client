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
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorField;

public class ModelAdapterWolf
extends ModelAdapter {
    public ModelAdapterWolf() {
        super(EntityWolf.class, "wolf", 0.5f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelWolf();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelWolf)) {
            return null;
        }
        ModelWolf modelwolf = (ModelWolf)model;
        if (modelPart.equals((Object)"head")) {
            return modelwolf.wolfHeadMain;
        }
        if (modelPart.equals((Object)"body")) {
            return modelwolf.wolfBody;
        }
        if (modelPart.equals((Object)"leg1")) {
            return modelwolf.wolfLeg1;
        }
        if (modelPart.equals((Object)"leg2")) {
            return modelwolf.wolfLeg2;
        }
        if (modelPart.equals((Object)"leg3")) {
            return modelwolf.wolfLeg3;
        }
        if (modelPart.equals((Object)"leg4")) {
            return modelwolf.wolfLeg4;
        }
        if (modelPart.equals((Object)"tail")) {
            return (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_tail);
        }
        return modelPart.equals((Object)"mane") ? (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_mane) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderWolf renderwolf = new RenderWolf(rendermanager, modelBase, shadowSize);
        return renderwolf;
    }
}

