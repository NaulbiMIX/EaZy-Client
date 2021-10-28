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
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterSnowman
extends ModelAdapter {
    public ModelAdapterSnowman() {
        super(EntitySnowman.class, "snow_golem", 0.5f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelSnowMan();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelSnowMan)) {
            return null;
        }
        ModelSnowMan modelsnowman = (ModelSnowMan)model;
        if (modelPart.equals((Object)"body")) {
            return modelsnowman.body;
        }
        if (modelPart.equals((Object)"body_bottom")) {
            return modelsnowman.bottomBody;
        }
        if (modelPart.equals((Object)"head")) {
            return modelsnowman.head;
        }
        if (modelPart.equals((Object)"left_hand")) {
            return modelsnowman.leftHand;
        }
        return modelPart.equals((Object)"right_hand") ? modelsnowman.rightHand : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "body_bottom", "head", "right_hand", "left_hand"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderSnowMan rendersnowman = new RenderSnowMan(rendermanager);
        rendersnowman.mainModel = modelBase;
        rendersnowman.shadowSize = shadowSize;
        return rendersnowman;
    }
}

