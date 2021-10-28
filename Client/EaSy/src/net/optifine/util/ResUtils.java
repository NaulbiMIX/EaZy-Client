/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Enumeration
 *  java.util.LinkedHashSet
 *  java.util.Locale
 *  java.util.Properties
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipFile
 */
package net.optifine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class ResUtils {
    public static String[] collectFiles(String prefix, String suffix) {
        return ResUtils.collectFiles(new String[]{prefix}, new String[]{suffix});
    }

    public static String[] collectFiles(String[] prefixes, String[] suffixes) {
        LinkedHashSet set = new LinkedHashSet();
        IResourcePack[] airesourcepack = Config.getResourcePacks();
        for (int i = 0; i < airesourcepack.length; ++i) {
            IResourcePack iresourcepack = airesourcepack[i];
            Object[] astring = ResUtils.collectFiles(iresourcepack, prefixes, suffixes, (String[])null);
            set.addAll((Collection)Arrays.asList((Object[])astring));
        }
        String[] astring1 = (String[])set.toArray((Object[])new String[set.size()]);
        return astring1;
    }

    public static String[] collectFiles(IResourcePack rp, String prefix, String suffix, String[] defaultPaths) {
        return ResUtils.collectFiles(rp, new String[]{prefix}, new String[]{suffix}, defaultPaths);
    }

    public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes) {
        return ResUtils.collectFiles(rp, prefixes, suffixes, (String[])null);
    }

    public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
        if (rp instanceof DefaultResourcePack) {
            return ResUtils.collectFilesFixed(rp, defaultPaths);
        }
        if (!(rp instanceof AbstractResourcePack)) {
            Config.warn("Unknown resource pack type: " + rp);
            return new String[0];
        }
        AbstractResourcePack abstractresourcepack = (AbstractResourcePack)rp;
        File file1 = abstractresourcepack.resourcePackFile;
        if (file1 == null) {
            return new String[0];
        }
        if (file1.isDirectory()) {
            return ResUtils.collectFilesFolder(file1, "", prefixes, suffixes);
        }
        if (file1.isFile()) {
            return ResUtils.collectFilesZIP(file1, prefixes, suffixes);
        }
        Config.warn("Unknown resource pack file: " + (Object)file1);
        return new String[0];
    }

    private static String[] collectFilesFixed(IResourcePack rp, String[] paths) {
        if (paths == null) {
            return new String[0];
        }
        ArrayList list = new ArrayList();
        for (int i = 0; i < paths.length; ++i) {
            String s = paths[i];
            ResourceLocation resourcelocation = new ResourceLocation(s);
            if (!rp.resourceExists(resourcelocation)) continue;
            list.add((Object)s);
        }
        String[] astring = (String[])list.toArray((Object[])new String[list.size()]);
        return astring;
    }

    private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
        ArrayList list = new ArrayList();
        String s = "assets/minecraft/";
        File[] afile = tpFile.listFiles();
        if (afile == null) {
            return new String[0];
        }
        for (int i = 0; i < afile.length; ++i) {
            File file1 = afile[i];
            if (file1.isFile()) {
                String s3 = basePath + file1.getName();
                if (!s3.startsWith(s) || !StrUtils.startsWith(s3 = s3.substring(s.length()), prefixes) || !StrUtils.endsWith(s3, suffixes)) continue;
                list.add((Object)s3);
                continue;
            }
            if (!file1.isDirectory()) continue;
            String s1 = basePath + file1.getName() + "/";
            String[] astring = ResUtils.collectFilesFolder(file1, s1, prefixes, suffixes);
            for (int j = 0; j < astring.length; ++j) {
                String s2 = astring[j];
                list.add((Object)s2);
            }
        }
        String[] astring1 = (String[])list.toArray((Object[])new String[list.size()]);
        return astring1;
    }

    private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
        ArrayList list = new ArrayList();
        String s = "assets/minecraft/";
        try {
            ZipFile zipfile = new ZipFile(tpFile);
            Enumeration enumeration = zipfile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
                String s1 = zipentry.getName();
                if (!s1.startsWith(s) || !StrUtils.startsWith(s1 = s1.substring(s.length()), prefixes) || !StrUtils.endsWith(s1, suffixes)) continue;
                list.add((Object)s1);
            }
            zipfile.close();
            String[] astring = (String[])list.toArray((Object[])new String[list.size()]);
            return astring;
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            return new String[0];
        }
    }

    private static boolean isLowercase(String str) {
        return str.equals((Object)str.toLowerCase(Locale.ROOT));
    }

    public static Properties readProperties(String path, String module) {
        ResourceLocation resourcelocation = new ResourceLocation(path);
        try {
            InputStream inputstream = Config.getResourceStream(resourcelocation);
            if (inputstream == null) {
                return null;
            }
            PropertiesOrdered properties = new PropertiesOrdered();
            properties.load(inputstream);
            inputstream.close();
            Config.dbg("" + module + ": Loading " + path);
            return properties;
        }
        catch (FileNotFoundException var5) {
            return null;
        }
        catch (IOException var6) {
            Config.warn("" + module + ": Error reading " + path);
            return null;
        }
    }

    public static Properties readProperties(InputStream in, String module) {
        if (in == null) {
            return null;
        }
        try {
            PropertiesOrdered properties = new PropertiesOrdered();
            properties.load(in);
            in.close();
            return properties;
        }
        catch (FileNotFoundException var3) {
            return null;
        }
        catch (IOException var4) {
            return null;
        }
    }
}

