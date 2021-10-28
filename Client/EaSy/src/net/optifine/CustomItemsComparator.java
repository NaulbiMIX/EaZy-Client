/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Comparator
 */
package net.optifine;

import java.util.Comparator;
import net.minecraft.src.Config;
import net.optifine.CustomItemProperties;

public class CustomItemsComparator
implements Comparator {
    public int compare(Object o1, Object o2) {
        CustomItemProperties customitemproperties = (CustomItemProperties)o1;
        CustomItemProperties customitemproperties1 = (CustomItemProperties)o2;
        if (customitemproperties.weight != customitemproperties1.weight) {
            return customitemproperties1.weight - customitemproperties.weight;
        }
        return !Config.equals(customitemproperties.basePath, customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name);
    }
}

