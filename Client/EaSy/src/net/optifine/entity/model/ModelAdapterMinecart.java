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
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.src.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorField;

public class ModelAdapterMinecart
extends ModelAdapter {
    public ModelAdapterMinecart() {
        super(EntityMinecart.class, "minecart", 0.5f);
    }

    protected ModelAdapterMinecart(Class entityClass, String name, float shadow) {
        super(entityClass, name, shadow);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelMinecart();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelMinecart)) {
            return null;
        }
        ModelMinecart modelminecart = (ModelMinecart)model;
        if (modelPart.equals((Object)"bottom")) {
            return modelminecart.sideModels[0];
        }
        if (modelPart.equals((Object)"back")) {
            return modelminecart.sideModels[1];
        }
        if (modelPart.equals((Object)"front")) {
            return modelminecart.sideModels[2];
        }
        if (modelPart.equals((Object)"right")) {
            return modelminecart.sideModels[3];
        }
        if (modelPart.equals((Object)"left")) {
            return modelminecart.sideModels[4];
        }
        return modelPart.equals((Object)"dirt") ? modelminecart.sideModels[5] : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"bottom", "back", "front", "right", "left", "dirt"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderMinecart renderminecart = new RenderMinecart(rendermanager);
        if (!Reflector.RenderMinecart_modelMinecart.exists()) {
            Config.warn("Field not found: RenderMinecart.modelMinecart");
            return null;
        }
        Reflector.setFieldValue(renderminecart, Reflector.RenderMinecart_modelMinecart, modelBase);
        renderminecart.shadowSize = shadowSize;
        return renderminecart;
    }
}

