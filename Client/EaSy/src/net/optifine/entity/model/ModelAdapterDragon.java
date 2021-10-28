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
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.boss.EntityDragon;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorFields;

public class ModelAdapterDragon
extends ModelAdapter {
    public ModelAdapterDragon() {
        super(EntityDragon.class, "dragon", 0.5f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelDragon(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelDragon)) {
            return null;
        }
        ModelDragon modeldragon = (ModelDragon)model;
        if (modelPart.equals((Object)"head")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 0);
        }
        if (modelPart.equals((Object)"spine")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 1);
        }
        if (modelPart.equals((Object)"jaw")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 2);
        }
        if (modelPart.equals((Object)"body")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 3);
        }
        if (modelPart.equals((Object)"rear_leg")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 4);
        }
        if (modelPart.equals((Object)"front_leg")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 5);
        }
        if (modelPart.equals((Object)"rear_leg_tip")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 6);
        }
        if (modelPart.equals((Object)"front_leg_tip")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 7);
        }
        if (modelPart.equals((Object)"rear_foot")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 8);
        }
        if (modelPart.equals((Object)"front_foot")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 9);
        }
        if (modelPart.equals((Object)"wing")) {
            return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 10);
        }
        return modelPart.equals((Object)"wing_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 11) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "spine", "jaw", "body", "rear_leg", "front_leg", "rear_leg_tip", "front_leg_tip", "rear_foot", "front_foot", "wing", "wing_tip"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderDragon renderdragon = new RenderDragon(rendermanager);
        renderdragon.mainModel = modelBase;
        renderdragon.shadowSize = shadowSize;
        return renderdragon;
    }
}

