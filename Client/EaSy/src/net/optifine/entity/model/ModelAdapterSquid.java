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
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.src.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorField;

public class ModelAdapterSquid
extends ModelAdapter {
    public ModelAdapterSquid() {
        super(EntitySquid.class, "squid", 0.7f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelSquid();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelSquid)) {
            return null;
        }
        ModelSquid modelsquid = (ModelSquid)model;
        if (modelPart.equals((Object)"body")) {
            return (ModelRenderer)Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_body);
        }
        String s = "tentacle";
        if (modelPart.startsWith(s)) {
            ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_tentacles);
            if (amodelrenderer == null) {
                return null;
            }
            String s1 = modelPart.substring(s.length());
            int i = Config.parseInt(s1, -1);
            return --i >= 0 && i < amodelrenderer.length ? amodelrenderer[i] : null;
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderSquid rendersquid = new RenderSquid(rendermanager, modelBase, shadowSize);
        return rendersquid;
    }
}

