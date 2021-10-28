/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Enumeration
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Set
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipFile
 *  java.util.zip.ZipOutputStream
 */
package optifine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import optifine.Differ;
import optifine.HashUtils;
import optifine.IResourceProvider;
import optifine.Utils;
import optifine.ZipResourceProvider;
import optifine.xdelta.GDiffPatcher;
import optifine.xdelta.PatchException;

public class Patcher {
    public static final String CONFIG_FILE = "patch.cfg";
    public static final String CONFIG_FILE2 = "patch2.cfg";
    public static final String CONFIG_FILE3 = "patch3.cfg";
    public static final String PREFIX_PATCH = "patch/";
    public static final String SUFFIX_DELTA = ".xdelta";
    public static final String SUFFIX_MD5 = ".md5";

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            Utils.dbg("Usage: Patcher <base.jar> <diff.jar> <mod.jar>");
            return;
        } else {
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            File file3 = new File(args[2]);
            if (file1.getName().equals((Object)"AUTO")) {
                file1 = Differ.detectBaseFile(file2);
            }
            if (!file1.exists() || !file1.isFile()) throw new IOException("Base file not found: " + (Object)file1);
            if (!file2.exists() || !file2.isFile()) throw new IOException("Diff file not found: " + (Object)file3);
            Patcher.process(file1, file2, file3);
        }
    }

    public static void process(File baseFile, File diffFile, File modFile) throws Exception {
        ZipFile zipfile = new ZipFile(diffFile);
        Map<String, String> map = Patcher.getConfigurationMap(zipfile);
        Pattern[] apattern = Patcher.getConfigurationPatterns(map);
        ZipOutputStream zipoutputstream = new ZipOutputStream((OutputStream)new FileOutputStream(modFile));
        ZipFile zipfile1 = new ZipFile(baseFile);
        ZipResourceProvider zipresourceprovider = new ZipResourceProvider(zipfile1);
        Enumeration enumeration = zipfile.entries();
        while (enumeration.hasMoreElements()) {
            ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
            InputStream inputstream = zipfile.getInputStream(zipentry);
            byte[] abyte = Utils.readAll(inputstream);
            String s = zipentry.getName();
            if (s.startsWith(PREFIX_PATCH) && s.endsWith(SUFFIX_DELTA)) {
                String s2;
                byte[] abyte1;
                byte[] abyte2;
                String s3;
                s = s.substring(PREFIX_PATCH.length());
                s = s.substring(0, s.length() - SUFFIX_DELTA.length());
                byte[] abyte3 = Patcher.applyPatch(s, abyte, apattern, map, zipresourceprovider);
                String s1 = PREFIX_PATCH + s + SUFFIX_MD5;
                ZipEntry zipentry2 = zipfile.getEntry(s1);
                if (zipentry2 != null && !(s2 = new String(abyte1 = Utils.readAll(zipfile.getInputStream(zipentry2)), "ASCII")).equals((Object)(s3 = HashUtils.toHexString(abyte2 = HashUtils.getHashMd5(abyte3))))) {
                    throw new Exception("MD5 not matching, name: " + s + ", saved: " + s2 + ", patched: " + s3);
                }
                ZipEntry zipentry3 = new ZipEntry(s);
                zipoutputstream.putNextEntry(zipentry3);
                zipoutputstream.write(abyte3);
                zipoutputstream.closeEntry();
                Utils.dbg("Mod: " + s);
                continue;
            }
            if (s.startsWith(PREFIX_PATCH) && s.endsWith(SUFFIX_MD5)) continue;
            ZipEntry zipentry1 = new ZipEntry(s);
            zipoutputstream.putNextEntry(zipentry1);
            zipoutputstream.write(abyte);
            zipoutputstream.closeEntry();
            Utils.dbg("Same: " + zipentry1.getName());
        }
        zipoutputstream.close();
    }

    public static byte[] applyPatch(String name, byte[] bytesDiff, Pattern[] patterns, Map<String, String> cfgMap, IResourceProvider resourceProvider) throws IOException, PatchException {
        String s = Patcher.getPatchBase(name = Utils.removePrefix(name, "/"), patterns, cfgMap);
        if (s == null) {
            throw new IOException("No patch base, name: " + name + ", patterns: " + Utils.arrayToCommaSeparatedString((Object[])patterns));
        }
        InputStream inputstream = resourceProvider.getResourceStream(s);
        if (inputstream == null) {
            throw new IOException("Base resource not found: " + s);
        }
        byte[] abyte = Utils.readAll(inputstream);
        ByteArrayInputStream inputstream1 = new ByteArrayInputStream(bytesDiff);
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        new GDiffPatcher(abyte, (InputStream)inputstream1, (OutputStream)bytearrayoutputstream);
        bytearrayoutputstream.close();
        return bytearrayoutputstream.toByteArray();
    }

    public static Pattern[] getConfigurationPatterns(Map<String, String> cfgMap) {
        String[] astring = (String[])cfgMap.keySet().toArray((Object[])new String[0]);
        Pattern[] apattern = new Pattern[astring.length];
        for (int i = 0; i < astring.length; ++i) {
            String s = astring[i];
            apattern[i] = Pattern.compile((String)s);
        }
        return apattern;
    }

    public static Map<String, String> getConfigurationMap(ZipFile modZip) throws IOException {
        Map<String, String> map = Patcher.getConfigurationMap(modZip, CONFIG_FILE);
        Map<String, String> map1 = Patcher.getConfigurationMap(modZip, CONFIG_FILE2);
        Map<String, String> map2 = Patcher.getConfigurationMap(modZip, CONFIG_FILE3);
        map.putAll(map1);
        map.putAll(map2);
        return map;
    }

    public static Map<String, String> getConfigurationMap(ZipFile modZip, String pathConfig) throws IOException {
        LinkedHashMap map = new LinkedHashMap();
        if (modZip == null) {
            return map;
        }
        ZipEntry zipentry = modZip.getEntry(pathConfig);
        if (zipentry == null) {
            return map;
        }
        InputStream inputstream = modZip.getInputStream(zipentry);
        String[] astring = Utils.readLines(inputstream, "ASCII");
        inputstream.close();
        for (int i = 0; i < astring.length; ++i) {
            String s = astring[i].trim();
            if (s.startsWith("#") || s.length() <= 0) continue;
            String[] astring1 = Utils.tokenize(s, "=");
            if (astring1.length != 2) {
                throw new IOException("Invalid patch configuration: " + s);
            }
            String s1 = astring1[0].trim();
            String s2 = astring1[1].trim();
            map.put((Object)s1, (Object)s2);
        }
        return map;
    }

    public static String getPatchBase(String name, Pattern[] patterns, Map<String, String> cfgMap) {
        name = Utils.removePrefix(name, "/");
        for (int i = 0; i < patterns.length; ++i) {
            Pattern pattern = patterns[i];
            Matcher matcher = pattern.matcher((CharSequence)name);
            if (!matcher.matches()) continue;
            String s = (String)cfgMap.get((Object)pattern.pattern());
            if (s != null && s.trim().equals((Object)"*")) {
                return name;
            }
            return s;
        }
        return null;
    }
}

