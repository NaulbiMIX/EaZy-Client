/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.config;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.IObjectLocator;

public class ItemLocator
implements IObjectLocator {
    @Override
    public Object getObject(ResourceLocation loc) {
        Item item = Item.getByNameOrId(loc.toString());
        return item;
    }
}

