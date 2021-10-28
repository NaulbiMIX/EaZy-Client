/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Set
 */
package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelAdapterArmorStand;
import net.optifine.entity.model.ModelAdapterBanner;
import net.optifine.entity.model.ModelAdapterBat;
import net.optifine.entity.model.ModelAdapterBlaze;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelAdapterBook;
import net.optifine.entity.model.ModelAdapterCaveSpider;
import net.optifine.entity.model.ModelAdapterChest;
import net.optifine.entity.model.ModelAdapterChestLarge;
import net.optifine.entity.model.ModelAdapterChicken;
import net.optifine.entity.model.ModelAdapterCow;
import net.optifine.entity.model.ModelAdapterCreeper;
import net.optifine.entity.model.ModelAdapterDragon;
import net.optifine.entity.model.ModelAdapterEnderChest;
import net.optifine.entity.model.ModelAdapterEnderCrystal;
import net.optifine.entity.model.ModelAdapterEnderman;
import net.optifine.entity.model.ModelAdapterEndermite;
import net.optifine.entity.model.ModelAdapterGhast;
import net.optifine.entity.model.ModelAdapterGuardian;
import net.optifine.entity.model.ModelAdapterHeadHumanoid;
import net.optifine.entity.model.ModelAdapterHeadSkeleton;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.ModelAdapterIronGolem;
import net.optifine.entity.model.ModelAdapterLeadKnot;
import net.optifine.entity.model.ModelAdapterMagmaCube;
import net.optifine.entity.model.ModelAdapterMinecart;
import net.optifine.entity.model.ModelAdapterMinecartMobSpawner;
import net.optifine.entity.model.ModelAdapterMinecartTnt;
import net.optifine.entity.model.ModelAdapterMooshroom;
import net.optifine.entity.model.ModelAdapterOcelot;
import net.optifine.entity.model.ModelAdapterPig;
import net.optifine.entity.model.ModelAdapterPigZombie;
import net.optifine.entity.model.ModelAdapterRabbit;
import net.optifine.entity.model.ModelAdapterSheep;
import net.optifine.entity.model.ModelAdapterSheepWool;
import net.optifine.entity.model.ModelAdapterSign;
import net.optifine.entity.model.ModelAdapterSilverfish;
import net.optifine.entity.model.ModelAdapterSkeleton;
import net.optifine.entity.model.ModelAdapterSlime;
import net.optifine.entity.model.ModelAdapterSnowman;
import net.optifine.entity.model.ModelAdapterSpider;
import net.optifine.entity.model.ModelAdapterSquid;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.entity.model.ModelAdapterWitch;
import net.optifine.entity.model.ModelAdapterWither;
import net.optifine.entity.model.ModelAdapterWitherSkull;
import net.optifine.entity.model.ModelAdapterWolf;
import net.optifine.entity.model.ModelAdapterZombie;

public class CustomModelRegistry {
    private static Map<String, ModelAdapter> mapModelAdapters = CustomModelRegistry.makeMapModelAdapters();

    private static Map<String, ModelAdapter> makeMapModelAdapters() {
        LinkedHashMap map = new LinkedHashMap();
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterArmorStand());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterBat());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterBlaze());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterBoat());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterCaveSpider());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterChicken());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterCow());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterCreeper());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterDragon());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterEnderCrystal());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterEnderman());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterEndermite());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterGhast());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterGuardian());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterHorse());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterIronGolem());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterLeadKnot());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterMagmaCube());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterMinecart());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterMinecartTnt());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterMinecartMobSpawner());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterMooshroom());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterOcelot());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterPig());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterPigZombie());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterRabbit());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSheep());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSilverfish());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSkeleton());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSlime());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSnowman());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSpider());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSquid());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterVillager());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterWitch());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterWither());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterWitherSkull());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterWolf());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterZombie());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSheepWool());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterBanner());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterBook());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterChest());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterChestLarge());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterEnderChest());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterHeadHumanoid());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterHeadSkeleton());
        CustomModelRegistry.addModelAdapter((Map<String, ModelAdapter>)map, new ModelAdapterSign());
        return map;
    }

    private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
        CustomModelRegistry.addModelAdapter(map, modelAdapter, modelAdapter.getName());
        String[] astring = modelAdapter.getAliases();
        if (astring != null) {
            for (int i = 0; i < astring.length; ++i) {
                String s = astring[i];
                CustomModelRegistry.addModelAdapter(map, modelAdapter, s);
            }
        }
        ModelBase modelbase = modelAdapter.makeModel();
        String[] astring1 = modelAdapter.getModelRendererNames();
        for (int j = 0; j < astring1.length; ++j) {
            String s1 = astring1[j];
            ModelRenderer modelrenderer = modelAdapter.getModelRenderer(modelbase, s1);
            if (modelrenderer != null) continue;
            Config.warn("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + s1);
        }
    }

    private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter, String name) {
        if (map.containsKey((Object)name)) {
            Config.warn("Model adapter already registered for id: " + name + ", class: " + modelAdapter.getEntityClass().getName());
        }
        map.put((Object)name, (Object)modelAdapter);
    }

    public static ModelAdapter getModelAdapter(String name) {
        return (ModelAdapter)mapModelAdapters.get((Object)name);
    }

    public static String[] getModelNames() {
        Set set = mapModelAdapters.keySet();
        String[] astring = (String[])set.toArray((Object[])new String[set.size()]);
        return astring;
    }
}

