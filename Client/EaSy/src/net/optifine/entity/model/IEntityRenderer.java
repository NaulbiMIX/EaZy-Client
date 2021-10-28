/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 */
package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;

public interface IEntityRenderer {
    public Class getEntityClass();

    public void setEntityClass(Class var1);

    public ResourceLocation getLocationTextureCustom();

    public void setLocationTextureCustom(ResourceLocation var1);
}

