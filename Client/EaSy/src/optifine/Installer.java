/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.Component
 *  java.awt.Dimension
 *  java.awt.Font
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.Writer
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.net.URI
 *  java.net.URL
 *  java.security.CodeSource
 *  java.security.ProtectionDomain
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.Enumeration
 *  java.util.Locale
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipFile
 *  javax.swing.JFileChooser
 *  javax.swing.JOptionPane
 *  javax.swing.JScrollPane
 *  javax.swing.JTextArea
 */
package optifine;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import optifine.Patcher;
import optifine.ProfileIcon;
import optifine.Utils;
import optifine.json.JSONArray;
import optifine.json.JSONObject;
import optifine.json.JSONParser;
import optifine.json.JSONWriter;
import optifine.json.ParseException;

public class Installer {
    public static void main(String[] args) {
        try {
            File file1 = Utils.getWorkingDirectory();
            Installer.doInstall(file1);
        }
        catch (Exception exception) {
            String s = exception.getMessage();
            if (s != null && s.equals((Object)"QUIET")) {
                return;
            }
            exception.printStackTrace();
            String s1 = Utils.getExceptionStackTrace(exception);
            s1 = s1.replace((CharSequence)"\t", (CharSequence)"  ");
            JTextArea jtextarea = new JTextArea(s1);
            jtextarea.setEditable(false);
            Font font = jtextarea.getFont();
            Font font1 = new Font("Monospaced", font.getStyle(), font.getSize());
            jtextarea.setFont(font1);
            JScrollPane jscrollpane = new JScrollPane((Component)jtextarea);
            jscrollpane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog((Component)null, (Object)jscrollpane, (String)"Error", (int)0);
        }
    }

    public static void doInstall(File dirMc) throws Exception {
        Utils.dbg("Dir minecraft: " + (Object)dirMc);
        File file1 = new File(dirMc, "libraries");
        Utils.dbg("Dir libraries: " + (Object)file1);
        File file2 = new File(dirMc, "versions");
        Utils.dbg("Dir versions: " + (Object)file2);
        String s = Installer.getOptiFineVersion();
        Utils.dbg("OptiFine Version: " + s);
        String[] astring = Utils.tokenize(s, "_");
        String s1 = astring[1];
        Utils.dbg("Minecraft Version: " + s1);
        String s2 = Installer.getOptiFineEdition(astring);
        Utils.dbg("OptiFine Edition: " + s2);
        String s3 = s1 + "-OptiFine_" + s2;
        Utils.dbg("Minecraft_OptiFine Version: " + s3);
        Installer.copyMinecraftVersion(s1, s3, file2);
        Installer.installOptiFineLibrary(s1, s2, file1, false);
        Installer.installLaunchwrapperLibrary(s1, s2, file1);
        Installer.updateJson(file2, s3, file1, s1, s2);
        Installer.updateLauncherJson(dirMc, s3);
    }

    public static boolean doExtract(File dirMc) throws Exception {
        Utils.dbg("Dir minecraft: " + (Object)dirMc);
        File file1 = new File(dirMc, "libraries");
        Utils.dbg("Dir libraries: " + (Object)file1);
        File file2 = new File(dirMc, "versions");
        Utils.dbg("Dir versions: " + (Object)file2);
        String s = Installer.getOptiFineVersion();
        Utils.dbg("OptiFine Version: " + s);
        String[] astring = Utils.tokenize(s, "_");
        String s1 = astring[1];
        Utils.dbg("Minecraft Version: " + s1);
        String s2 = Installer.getOptiFineEdition(astring);
        Utils.dbg("OptiFine Edition: " + s2);
        String s3 = s1 + "-OptiFine_" + s2;
        Utils.dbg("Minecraft_OptiFine Version: " + s3);
        return Installer.installOptiFineLibrary(s1, s2, file1, true);
    }

    private static void updateLauncherJson(File dirMc, String mcVerOf) throws IOException, ParseException {
        JSONObject jsonobject2;
        JSONObject jsonobject;
        File file1 = new File(dirMc, "launcher_profiles.json");
        if (file1.exists() && file1.isFile()) {
            JSONParser jsonparser = new JSONParser();
            String s = Utils.readFile(file1, "UTF-8");
            jsonobject = (JSONObject)jsonparser.parse(s);
            JSONObject jsonobject1 = (JSONObject)jsonobject.get((Object)"profiles");
            jsonobject2 = (JSONObject)jsonobject1.get((Object)"OptiFine");
            if (jsonobject2 == null) {
                jsonobject2 = new JSONObject();
                jsonobject2.put((Object)"name", (Object)"OptiFine");
                jsonobject2.put((Object)"created", Installer.formatDateMs(new Date()));
                jsonobject1.put((Object)"OptiFine", (Object)jsonobject2);
            }
        } else {
            Utils.showErrorMessage("File not found: " + (Object)file1);
            throw new RuntimeException("QUIET");
        }
        jsonobject2.put((Object)"type", (Object)"custom");
        jsonobject2.put((Object)"lastVersionId", (Object)mcVerOf);
        jsonobject2.put((Object)"lastUsed", Installer.formatDateMs(new Date()));
        jsonobject2.put((Object)"icon", (Object)ProfileIcon.DATA);
        jsonobject.put((Object)"selectedProfile", (Object)"OptiFine");
        FileOutputStream fileoutputstream = new FileOutputStream(file1);
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter((OutputStream)fileoutputstream, "UTF-8");
        JSONWriter jsonwriter = new JSONWriter((Writer)outputstreamwriter);
        jsonwriter.writeObject(jsonobject);
        outputstreamwriter.flush();
        outputstreamwriter.close();
    }

    private static void updateJson(File dirMcVers, String mcVerOf, File dirMcLib, String mcVer, String ofEd) throws IOException, ParseException {
        File file1 = new File(dirMcVers, mcVerOf);
        File file2 = new File(file1, mcVerOf + ".json");
        String s = Utils.readFile(file2, "UTF-8");
        JSONParser jsonparser = new JSONParser();
        JSONObject jsonobject = (JSONObject)jsonparser.parse(s);
        JSONObject jsonobject1 = new JSONObject();
        jsonobject1.put((Object)"id", (Object)mcVerOf);
        jsonobject1.put((Object)"inheritsFrom", (Object)mcVer);
        jsonobject1.put((Object)"time", Installer.formatDate(new Date()));
        jsonobject1.put((Object)"releaseTime", Installer.formatDate(new Date()));
        jsonobject1.put((Object)"type", (Object)"release");
        JSONArray jsonarray = new JSONArray();
        jsonobject1.put((Object)"libraries", (Object)jsonarray);
        String s1 = (String)jsonobject.get((Object)"mainClass");
        if (!s1.startsWith("net.minecraft.launchwrapper.")) {
            s1 = "net.minecraft.launchwrapper.Launch";
            jsonobject1.put((Object)"mainClass", (Object)s1);
            String s2 = (String)jsonobject.get((Object)"minecraftArguments");
            if (s2 != null) {
                s2 = s2 + "  --tweakClass optifine.OptiFineTweaker";
                jsonobject1.put((Object)"minecraftArguments", (Object)s2);
            } else {
                jsonobject1.put((Object)"minimumLauncherVersion", (Object)"21");
                JSONObject jsonobject2 = new JSONObject();
                JSONArray jsonarray1 = new JSONArray();
                jsonarray1.add((Object)"--tweakClass");
                jsonarray1.add((Object)"optifine.OptiFineTweaker");
                jsonobject2.put((Object)"game", (Object)jsonarray1);
                jsonobject1.put((Object)"arguments", (Object)jsonobject2);
            }
            JSONObject jsonobject4 = new JSONObject();
            jsonobject4.put((Object)"name", (Object)("optifine:launchwrapper-of:" + Installer.getLaunchwrapperVersion()));
            jsonarray.add(0, (Object)jsonobject4);
        }
        JSONObject jsonobject3 = new JSONObject();
        jsonobject3.put((Object)"name", (Object)("optifine:OptiFine:" + mcVer + "_" + ofEd));
        jsonarray.add(0, (Object)jsonobject3);
        FileOutputStream fileoutputstream = new FileOutputStream(file2);
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter((OutputStream)fileoutputstream, "UTF-8");
        JSONWriter jsonwriter = new JSONWriter((Writer)outputstreamwriter);
        jsonwriter.writeObject(jsonobject1);
        outputstreamwriter.flush();
        outputstreamwriter.close();
    }

    private static Object formatDate(Date date) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            String s1 = simpledateformat.format(date);
            return s1;
        }
        catch (Exception var4) {
            SimpleDateFormat simpledateformat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String s = simpledateformat1.format(date);
            return s;
        }
    }

    private static Object formatDateMs(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");
        String s = simpledateformat.format(date);
        return s;
    }

    public static String getOptiFineEdition(String[] ofVers) {
        if (ofVers.length <= 2) {
            return "";
        }
        String s = "";
        for (int i = 2; i < ofVers.length; ++i) {
            if (i > 2) {
                s = s + "_";
            }
            s = s + ofVers[i];
        }
        return s;
    }

    private static boolean installOptiFineLibrary(String mcVer, String ofEd, File dirMcLib, boolean selectTarget) throws Exception {
        File file1 = Installer.getOptiFineZipFile();
        File file2 = new File(dirMcLib, "optifine/OptiFine/" + mcVer + "_" + ofEd);
        File file3 = new File(file2, "OptiFine-" + mcVer + "_" + ofEd + ".jar");
        if (selectTarget) {
            file3 = new File(file1.getParentFile(), "OptiFine_" + mcVer + "_" + ofEd + "_MOD.jar");
            JFileChooser jfilechooser = new JFileChooser(file3.getParentFile());
            jfilechooser.setSelectedFile(file3);
            int i = jfilechooser.showSaveDialog((Component)null);
            if (i != 0) {
                return false;
            }
            file3 = jfilechooser.getSelectedFile();
            if (file3.exists()) {
                JOptionPane.setDefaultLocale((Locale)Locale.ENGLISH);
                int j = JOptionPane.showConfirmDialog((Component)null, (Object)("The file \"" + file3.getName() + "\" already exists.\nDo you want to overwrite it?"), (String)"Save", (int)1);
                if (j != 0) {
                    return false;
                }
            }
        }
        if (file3.equals((Object)file1)) {
            JOptionPane.showMessageDialog((Component)null, (Object)"Source and target file are the same.", (String)"Save", (int)0);
            return false;
        }
        Utils.dbg("Source: " + (Object)file1);
        Utils.dbg("Dest: " + (Object)file3);
        File file4 = dirMcLib.getParentFile();
        File file5 = new File(file4, "versions/" + mcVer + "/" + mcVer + ".jar");
        if (!file5.exists()) {
            Installer.showMessageVersionNotFound(mcVer);
            throw new RuntimeException("QUIET");
        }
        if (file3.getParentFile() != null) {
            file3.getParentFile().mkdirs();
        }
        Patcher.process(file5, file1, file3);
        return true;
    }

    private static boolean installLaunchwrapperLibrary(String mcVer, String ofEd, File dirMcLib) throws Exception {
        String s = Installer.getLaunchwrapperVersion();
        String s1 = "launchwrapper-of-" + s + ".jar";
        File file1 = new File(dirMcLib, "optifine/launchwrapper-of/" + s);
        File file2 = new File(file1, s1);
        Utils.dbg("Source: " + s1);
        Utils.dbg("Dest: " + (Object)file2);
        InputStream inputstream = Installer.class.getResourceAsStream("/" + s1);
        if (inputstream == null) {
            throw new IOException("File not found: " + s1);
        }
        if (file2.getParentFile() != null) {
            file2.getParentFile().mkdirs();
        }
        FileOutputStream fileoutputstream = new FileOutputStream(file2);
        Utils.copyAll(inputstream, (OutputStream)fileoutputstream);
        fileoutputstream.flush();
        inputstream.close();
        fileoutputstream.close();
        return true;
    }

    public static File getOptiFineZipFile() throws Exception {
        URL url = Installer.class.getProtectionDomain().getCodeSource().getLocation();
        Utils.dbg("URL: " + (Object)url);
        URI uri = url.toURI();
        File file1 = new File(uri);
        return file1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isPatchFile() throws Exception {
        File file1 = Installer.getOptiFineZipFile();
        ZipFile zipfile = new ZipFile(file1);
        try {
            Enumeration enumeration = zipfile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
                if (!zipentry.getName().startsWith("patch/")) continue;
                boolean bl = true;
                return bl;
            }
            boolean zipentry = false;
            return zipentry;
        }
        finally {
            zipfile.close();
        }
    }

    private static void copyMinecraftVersion(String mcVer, String mcVerOf, File dirMcVer) throws IOException {
        File file1 = new File(dirMcVer, mcVer);
        if (!file1.exists()) {
            Installer.showMessageVersionNotFound(mcVer);
            throw new RuntimeException("QUIET");
        }
        File file2 = new File(dirMcVer, mcVerOf);
        file2.mkdirs();
        Utils.dbg("Dir version MC: " + (Object)file1);
        Utils.dbg("Dir version MC-OF: " + (Object)file2);
        File file3 = new File(file1, mcVer + ".jar");
        File file4 = new File(file2, mcVerOf + ".jar");
        if (!file3.exists()) {
            Installer.showMessageVersionNotFound(mcVer);
            throw new RuntimeException("QUIET");
        }
        Utils.copyFile(file3, file4);
        File file5 = new File(file1, mcVer + ".json");
        File file6 = new File(file2, mcVerOf + ".json");
        Utils.copyFile(file5, file6);
    }

    private static void showMessageVersionNotFound(String mcVer) {
        Utils.showErrorMessage("Cannot find Minecraft " + mcVer + ".\nYou must download and start Minecraft " + mcVer + " once in the official launcher.");
    }

    public static String getOptiFineVersion() throws IOException {
        InputStream inputstream = Installer.class.getResourceAsStream("/net/optifine/Config.class");
        if (inputstream == null) {
            inputstream = Installer.class.getResourceAsStream("/Config.class");
        }
        if (inputstream == null) {
            inputstream = Installer.class.getResourceAsStream("/VersionThread.class");
        }
        if (inputstream == null) {
            throw new IOException("OptiFine version not found");
        }
        return Installer.getOptiFineVersion(inputstream);
    }

    public static String getOptiFineVersion(ZipFile zipFile) throws IOException {
        ZipEntry zipentry = zipFile.getEntry("net/optifine/Config.class");
        if (zipentry == null) {
            zipentry = zipFile.getEntry("Config.class");
        }
        if (zipentry == null) {
            zipentry = zipFile.getEntry("VersionThread.class");
        }
        if (zipentry == null) {
            throw new IOException("OptiFine version not found");
        }
        InputStream inputstream = zipFile.getInputStream(zipentry);
        String s = Installer.getOptiFineVersion(inputstream);
        inputstream.close();
        return s;
    }

    public static String getOptiFineVersion(InputStream in) throws IOException {
        byte[] abyte1;
        byte b0;
        byte[] abyte = Utils.readAll(in);
        int i = Utils.find(abyte, abyte1 = "OptiFine_".getBytes("ASCII"));
        if (i < 0) {
            return null;
        }
        while (i < abyte.length && (b0 = abyte[i]) >= 32 && b0 <= 122) {
            ++i;
        }
        String s = new String(abyte, i, i - i, "ASCII");
        return s;
    }

    public static String getMinecraftVersionFromOfVersion(String ofVer) {
        if (ofVer == null) {
            return null;
        }
        String[] astring = Utils.tokenize(ofVer, "_");
        if (astring.length < 2) {
            return null;
        }
        String s = astring[1];
        return s;
    }

    private static String getLaunchwrapperVersion() throws IOException {
        String s = "/launchwrapper-of.txt";
        InputStream inputstream = Installer.class.getResourceAsStream(s);
        if (inputstream == null) {
            throw new IOException("File not found: " + s);
        }
        String s1 = Utils.readText(inputstream, "ASCII");
        if (!(s1 = s1.trim()).matches("[0-9\\.]+")) {
            throw new IOException("Invalid launchwrapper version: " + s1);
        }
        return s1;
    }
}

