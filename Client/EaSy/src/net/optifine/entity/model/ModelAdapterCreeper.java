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
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterCreeper
extends ModelAdapter {
    public ModelAdapterCreeper() {
        super(EntityCreeper.class, "creeper", 0.5f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelCreeper();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelCreeper)) {
            return null;
        }
        ModelCreeper modelcreeper = (ModelCreeper)model;
        if (modelPart.equals((Object)"head")) {
            return modelcreeper.head;
        }
        if (modelPart.equals((Object)"armor")) {
            return modelcreeper.creeperArmor;
        }
        if (modelPart.equals((Object)"body")) {
            return modelcreeper.body;
        }
        if (modelPart.equals((Object)"leg1")) {
            return modelcreeper.leg1;
        }
        if (modelPart.equals((Object)"leg2")) {
            return modelcreeper.leg2;
        }
        if (modelPart.equals((Object)"leg3")) {
            return modelcreeper.leg3;
        }
        return modelPart.equals((Object)"leg4") ? modelcreeper.leg4 : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "armor", "body", "leg1", "leg2", "leg3", "leg4"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderCreeper rendercreeper = new RenderCreeper(rendermanager);
        rendercreeper.mainModel = modelBase;
        rendercreeper.shadowSize = shadowSize;
        return rendercreeper;
    }
}

