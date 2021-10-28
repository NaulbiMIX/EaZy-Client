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
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.optifine.entity.model.ModelAdapter;

public abstract class ModelAdapterQuadruped
extends ModelAdapter {
    public ModelAdapterQuadruped(Class entityClass, String name, float shadowSize) {
        super(entityClass, name, shadowSize);
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelQuadruped)) {
            return null;
        }
        ModelQuadruped modelquadruped = (ModelQuadruped)model;
        if (modelPart.equals((Object)"head")) {
            return modelquadruped.head;
        }
        if (modelPart.equals((Object)"body")) {
            return modelquadruped.body;
        }
        if (modelPart.equals((Object)"leg1")) {
            return modelquadruped.leg1;
        }
        if (modelPart.equals((Object)"leg2")) {
            return modelquadruped.leg2;
        }
        if (modelPart.equals((Object)"leg3")) {
            return modelquadruped.leg3;
        }
        return modelPart.equals((Object)"leg4") ? modelquadruped.leg4 : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4"};
    }
}

