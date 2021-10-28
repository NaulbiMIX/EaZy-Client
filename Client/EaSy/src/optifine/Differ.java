/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.security.NoSuchAlgorithmException
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Enumeration
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 *  java.util.regex.Pattern
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipFile
 *  java.util.zip.ZipOutputStream
 */
package optifine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import optifine.HashUtils;
import optifine.Installer;
import optifine.Patcher;
import optifine.Utils;
import optifine.xdelta.Delta;
import optifine.xdelta.DeltaException;
import optifine.xdelta.DiffWriter;
import optifine.xdelta.GDiffWriter;

public class Differ {
    public static void main(String[] args) {
        if (args.length < 3) {
            Utils.dbg("Usage: Differ <base.jar> <mod.jar> <diff.jar>");
        } else {
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            File file3 = new File(args[2]);
            try {
                if (file1.getName().equals((Object)"AUTO")) {
                    file1 = Differ.detectBaseFile(file2);
                }
                if (!file1.exists() || !file1.isFile()) {
                    throw new IOException("Base file not found: " + (Object)file1);
                }
                if (!file2.exists() || !file2.isFile()) {
                    throw new IOException("Mod file not found: " + (Object)file2);
                }
                Differ.process(file1, file2, file3);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                System.exit((int)1);
            }
        }
    }

    private static void process(File baseFile, File modFile, File diffFile) throws IOException, DeltaException, NoSuchAlgorithmException {
        ZipFile zipfile = new ZipFile(modFile);
        Map<String, String> map = Patcher.getConfigurationMap(zipfile);
        Pattern[] apattern = Patcher.getConfigurationPatterns(map);
        ZipOutputStream zipoutputstream = new ZipOutputStream((OutputStream)new FileOutputStream(diffFile));
        ZipFile zipfile1 = new ZipFile(baseFile);
        Enumeration enumeration = zipfile.entries();
        HashMap map1 = new HashMap();
        while (enumeration.hasMoreElements()) {
            ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
            if (zipentry.isDirectory()) continue;
            InputStream inputstream = zipfile.getInputStream(zipentry);
            byte[] abyte = Utils.readAll(inputstream);
            String s = zipentry.getName();
            byte[] abyte1 = Differ.makeDiff(s, abyte, apattern, map, zipfile1);
            if (abyte1 != abyte) {
                ZipEntry zipentry1 = new ZipEntry("patch/" + s + ".xdelta");
                zipoutputstream.putNextEntry(zipentry1);
                zipoutputstream.write(abyte1);
                zipoutputstream.closeEntry();
                Differ.addStat((Map<String, Map<String, Integer>>)map1, s, "delta");
                byte[] abyte2 = HashUtils.getHashMd5(abyte);
                String s1 = HashUtils.toHexString(abyte2);
                byte[] abyte3 = s1.getBytes("ASCII");
                ZipEntry zipentry2 = new ZipEntry("patch/" + s + ".md5");
                zipoutputstream.putNextEntry(zipentry2);
                zipoutputstream.write(abyte3);
                zipoutputstream.closeEntry();
                continue;
            }
            ZipEntry zipentry3 = new ZipEntry(s);
            zipoutputstream.putNextEntry(zipentry3);
            zipoutputstream.write(abyte);
            zipoutputstream.closeEntry();
            Differ.addStat((Map<String, Map<String, Integer>>)map1, s, "same");
        }
        zipoutputstream.close();
        Differ.printStats((Map<String, Map<String, Integer>>)map1);
    }

    private static void printStats(Map<String, Map<String, Integer>> mapStats) {
        ArrayList list = new ArrayList((Collection)mapStats.keySet());
        Collections.sort((List)list);
        for (String s : list) {
            Map map = (Map)mapStats.get((Object)s);
            ArrayList list1 = new ArrayList((Collection)map.keySet());
            Collections.sort((List)list1);
            String s1 = "";
            for (String s2 : list1) {
                Integer integer = (Integer)map.get((Object)s2);
                if (s1.length() > 0) {
                    s1 = s1 + ", ";
                }
                s1 = s1 + s2 + " " + (Object)integer;
            }
            Utils.dbg(s + " = " + s1);
        }
    }

    private static void addStat(Map<String, Map<String, Integer>> mapStats, String name, String type) {
        Integer integer;
        Map map;
        int i = name.lastIndexOf(47);
        String s = "";
        if (i >= 0) {
            s = name.substring(0, i);
        }
        if ((map = (Map)mapStats.get((Object)s)) == null) {
            map = new HashMap();
            mapStats.put((Object)s, (Object)map);
        }
        if ((integer = (Integer)map.get((Object)type)) == null) {
            integer = new Integer(0);
        }
        integer = new Integer(integer + 1);
        map.put((Object)type, (Object)integer);
    }

    public static byte[] makeDiff(String name, byte[] bytesMod, Pattern[] patterns, Map<String, String> cfgMap, ZipFile zipBase) throws IOException, DeltaException {
        String s = Patcher.getPatchBase(name, patterns, cfgMap);
        if (s == null) {
            return bytesMod;
        }
        ZipEntry zipentry = zipBase.getEntry(s);
        if (zipentry == null) {
            throw new IOException("Base entry not found: " + s + " in: " + zipBase.getName());
        }
        InputStream inputstream = zipBase.getInputStream(zipentry);
        byte[] abyte = Utils.readAll(inputstream);
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(bytesMod);
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        GDiffWriter diffwriter = new GDiffWriter(new DataOutputStream((OutputStream)bytearrayoutputstream));
        Delta.computeDelta(abyte, (InputStream)bytearrayinputstream, bytesMod.length, (DiffWriter)diffwriter);
        diffwriter.close();
        return bytearrayoutputstream.toByteArray();
    }

    public static File detectBaseFile(File modFile) throws IOException {
        ZipFile zipfile = new ZipFile(modFile);
        String s = Installer.getOptiFineVersion(zipfile);
        if (s == null) {
            throw new IOException("Version not found");
        }
        zipfile.close();
        String s1 = Installer.getMinecraftVersionFromOfVersion(s);
        if (s1 == null) {
            throw new IOException("Version not found");
        }
        File file1 = Utils.getWorkingDirectory();
        File file2 = new File(file1, "versions/" + s1 + "/" + s1 + ".jar");
        Utils.dbg("BaseFile: " + (Object)file2);
        return file2;
    }
}

