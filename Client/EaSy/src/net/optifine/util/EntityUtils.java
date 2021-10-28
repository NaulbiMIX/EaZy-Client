/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package net.optifine.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.src.Config;

public class EntityUtils {
    private static final Map<Class, Integer> mapIdByClass = new HashMap();
    private static final Map<String, Integer> mapIdByName = new HashMap();
    private static final Map<String, Class> mapClassByName = new HashMap();

    public static int getEntityIdByClass(Entity entity) {
        return entity == null ? -1 : EntityUtils.getEntityIdByClass(entity.getClass());
    }

    public static int getEntityIdByClass(Class cls) {
        Integer integer = (Integer)mapIdByClass.get((Object)cls);
        return integer == null ? -1 : integer;
    }

    public static int getEntityIdByName(String name) {
        Integer integer = (Integer)mapIdByName.get((Object)name);
        return integer == null ? -1 : integer;
    }

    public static Class getEntityClassByName(String name) {
        Class oclass = (Class)mapClassByName.get((Object)name);
        return oclass;
    }

    static {
        for (int i = 0; i < 1000; ++i) {
            String s;
            Class<? extends Entity> oclass = EntityList.getClassFromID(i);
            if (oclass == null || (s = EntityList.getStringFromID(i)) == null) continue;
            if (mapIdByClass.containsKey(oclass)) {
                Config.warn("Duplicate entity class: " + oclass + ", id1: " + mapIdByClass.get(oclass) + ", id2: " + i);
            }
            if (mapIdByName.containsKey((Object)s)) {
                Config.warn("Duplicate entity name: " + s + ", id1: " + mapIdByName.get((Object)s) + ", id2: " + i);
            }
            if (mapClassByName.containsKey((Object)s)) {
                Config.warn("Duplicate entity name: " + s + ", class1: " + mapClassByName.get((Object)s) + ", class2: " + oclass);
            }
            mapIdByClass.put(oclass, (Object)i);
            mapIdByName.put((Object)s, (Object)i);
            mapClassByName.put((Object)s, oclass);
        }
    }
}

