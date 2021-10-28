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
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.src.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorField;

public class ModelAdapterBoat
extends ModelAdapter {
    public ModelAdapterBoat() {
        super(EntityBoat.class, "boat", 0.5f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelBoat();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelBoat)) {
            return null;
        }
        ModelBoat modelboat = (ModelBoat)model;
        if (modelPart.equals((Object)"bottom")) {
            return modelboat.boatSides[0];
        }
        if (modelPart.equals((Object)"back")) {
            return modelboat.boatSides[1];
        }
        if (modelPart.equals((Object)"front")) {
            return modelboat.boatSides[2];
        }
        if (modelPart.equals((Object)"right")) {
            return modelboat.boatSides[3];
        }
        return modelPart.equals((Object)"left") ? modelboat.boatSides[4] : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"bottom", "back", "front", "right", "left"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderBoat renderboat = new RenderBoat(rendermanager);
        if (!Reflector.RenderBoat_modelBoat.exists()) {
            Config.warn("Field not found: RenderBoat.modelBoat");
            return null;
        }
        Reflector.setFieldValue(renderboat, Reflector.RenderBoat_modelBoat, modelBase);
        renderboat.shadowSize = shadowSize;
        return renderboat;
    }
}

