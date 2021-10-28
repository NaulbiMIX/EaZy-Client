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
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.monster.EntityWitch;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorField;

public class ModelAdapterWitch
extends ModelAdapter {
    public ModelAdapterWitch() {
        super(EntityWitch.class, "witch", 0.5f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelWitch(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelWitch)) {
            return null;
        }
        ModelWitch modelwitch = (ModelWitch)model;
        if (modelPart.equals((Object)"mole")) {
            return (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_mole);
        }
        if (modelPart.equals((Object)"hat")) {
            return (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_hat);
        }
        if (modelPart.equals((Object)"head")) {
            return modelwitch.villagerHead;
        }
        if (modelPart.equals((Object)"body")) {
            return modelwitch.villagerBody;
        }
        if (modelPart.equals((Object)"arms")) {
            return modelwitch.villagerArms;
        }
        if (modelPart.equals((Object)"left_leg")) {
            return modelwitch.leftVillagerLeg;
        }
        if (modelPart.equals((Object)"right_leg")) {
            return modelwitch.rightVillagerLeg;
        }
        return modelPart.equals((Object)"nose") ? modelwitch.villagerNose : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"mole", "head", "body", "arms", "right_leg", "left_leg", "nose"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderWitch renderwitch = new RenderWitch(rendermanager);
        renderwitch.mainModel = modelBase;
        renderwitch.shadowSize = shadowSize;
        return renderwitch;
    }
}

