/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.config;

import net.minecraft.util.ResourceLocation;
import net.optifine.config.IObjectLocator;
import net.optifine.util.EntityUtils;

public class EntityClassLocator
implements IObjectLocator {
    @Override
    public Object getObject(ResourceLocation loc) {
        Class oclass = EntityUtils.getEntityClassByName(loc.getResourcePath());
        return oclass;
    }
}

