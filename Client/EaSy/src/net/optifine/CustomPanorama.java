/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Properties
 *  java.util.Random
 */
package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomPanoramaProperties;
import net.optifine.util.MathUtils;
import net.optifine.util.PropertiesOrdered;

public class CustomPanorama {
    private static CustomPanoramaProperties customPanoramaProperties = null;
    private static final Random random = new Random();

    public static CustomPanoramaProperties getCustomPanoramaProperties() {
        return customPanoramaProperties;
    }

    public static void update() {
        customPanoramaProperties = null;
        String[] astring = CustomPanorama.getPanoramaFolders();
        if (astring.length > 1) {
            CustomPanoramaProperties custompanoramaproperties;
            Properties[] aproperties = CustomPanorama.getPanoramaProperties(astring);
            int[] aint = CustomPanorama.getWeights(aproperties);
            int i = CustomPanorama.getRandomIndex(aint);
            String s = astring[i];
            Properties properties = aproperties[i];
            if (properties == null) {
                properties = aproperties[0];
            }
            if (properties == null) {
                properties = new PropertiesOrdered();
            }
            customPanoramaProperties = custompanoramaproperties = new CustomPanoramaProperties(s, properties);
        }
    }

    private static String[] getPanoramaFolders() {
        ArrayList list = new ArrayList();
        list.add((Object)"textures/gui/title/background");
        for (int i = 0; i < 100; ++i) {
            String s = "optifine/gui/background" + i;
            String s1 = s + "/panorama_0.png";
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            if (!Config.hasResource(resourcelocation)) continue;
            list.add((Object)s);
        }
        String[] astring = (String[])list.toArray((Object[])new String[list.size()]);
        return astring;
    }

    private static Properties[] getPanoramaProperties(String[] folders) {
        Properties[] aproperties = new Properties[folders.length];
        for (int i = 0; i < folders.length; ++i) {
            String s = folders[i];
            if (i == 0) {
                s = "optifine/gui";
            } else {
                Config.dbg("CustomPanorama: " + s);
            }
            ResourceLocation resourcelocation = new ResourceLocation(s + "/background.properties");
            try {
                InputStream inputstream = Config.getResourceStream(resourcelocation);
                if (inputstream == null) continue;
                PropertiesOrdered properties = new PropertiesOrdered();
                properties.load(inputstream);
                Config.dbg("CustomPanorama: " + resourcelocation.getResourcePath());
                aproperties[i] = properties;
                inputstream.close();
                continue;
            }
            catch (IOException inputstream) {
                // empty catch block
            }
        }
        return aproperties;
    }

    private static int[] getWeights(Properties[] properties) {
        int[] weights = new int[properties.length];
        for (int i = 0; i < weights.length; ++i) {
            Properties prop = properties[i];
            if (prop == null) {
                prop = properties[0];
            }
            if (prop == null) {
                weights[i] = 1;
                continue;
            }
            String str = prop.getProperty("weight", null);
            weights[i] = Config.parseInt(str, 1);
        }
        return weights;
    }

    private static int getRandomIndex(int[] weights) {
        int i = MathUtils.getSum(weights);
        int j = random.nextInt(i);
        int k = 0;
        for (int l = 0; l < weights.length; ++l) {
            if ((k += weights[l]) <= j) continue;
            return l;
        }
        return weights.length - 1;
    }
}

