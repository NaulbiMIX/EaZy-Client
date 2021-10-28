/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.optifine.entity.model.ModelAdapter;

public abstract class ModelAdapterBiped
extends ModelAdapter {
    public ModelAdapterBiped(Class entityClass, String name, float shadowSize) {
        super(entityClass, name, shadowSize);
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelBiped)) {
            return null;
        }
        ModelBiped modelbiped = (ModelBiped)model;
        if (modelPart.equals((Object)"head")) {
            return modelbiped.bipedHead;
        }
        if (modelPart.equals((Object)"headwear")) {
            return modelbiped.bipedHeadwear;
        }
        if (modelPart.equals((Object)"body")) {
            return modelbiped.bipedBody;
        }
        if (modelPart.equals((Object)"left_arm")) {
            return modelbiped.bipedLeftArm;
        }
        if (modelPart.equals((Object)"right_arm")) {
            return modelbiped.bipedRightArm;
        }
        if (modelPart.equals((Object)"left_leg")) {
            return modelbiped.bipedLeftLeg;
        }
        return modelPart.equals((Object)"right_leg") ? modelbiped.bipedRightLeg : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "headwear", "body", "left_arm", "right_arm", "left_leg", "right_leg"};
    }
}

