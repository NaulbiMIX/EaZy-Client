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
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterChicken
extends ModelAdapter {
    public ModelAdapterChicken() {
        super(EntityChicken.class, "chicken", 0.3f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelChicken();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelChicken)) {
            return null;
        }
        ModelChicken modelchicken = (ModelChicken)model;
        if (modelPart.equals((Object)"head")) {
            return modelchicken.head;
        }
        if (modelPart.equals((Object)"body")) {
            return modelchicken.body;
        }
        if (modelPart.equals((Object)"right_leg")) {
            return modelchicken.rightLeg;
        }
        if (modelPart.equals((Object)"left_leg")) {
            return modelchicken.leftLeg;
        }
        if (modelPart.equals((Object)"right_wing")) {
            return modelchicken.rightWing;
        }
        if (modelPart.equals((Object)"left_wing")) {
            return modelchicken.leftWing;
        }
        if (modelPart.equals((Object)"bill")) {
            return modelchicken.bill;
        }
        return modelPart.equals((Object)"chin") ? modelchicken.chin : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderChicken renderchicken = new RenderChicken(rendermanager, modelBase, shadowSize);
        return renderchicken;
    }
}

