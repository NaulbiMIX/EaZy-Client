/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.StackTraceElement
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.HashMap
 *  java.util.Map
 */
package net.optifine;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.optifine.http.FileUploadThread;
import net.optifine.http.IFileUploadListener;
import net.optifine.shaders.Shaders;

public class CrashReporter {
    public static void onCrashReport(CrashReport crashReport, CrashReportCategory category) {
        try {
            Throwable throwable = crashReport.getCrashCause();
            if (throwable == null) {
                return;
            }
            if (throwable.getClass().getName().contains((CharSequence)".fml.client.SplashProgress")) {
                return;
            }
            CrashReporter.extendCrashReport(category);
            if (throwable.getClass() == Throwable.class) {
                return;
            }
            GameSettings gamesettings = Config.getGameSettings();
            if (gamesettings == null) {
                return;
            }
            if (!gamesettings.snooperEnabled) {
                return;
            }
            String s = "http://optifine.net/crashReport";
            String s1 = CrashReporter.makeReport(crashReport);
            byte[] abyte = s1.getBytes("ASCII");
            IFileUploadListener ifileuploadlistener = new IFileUploadListener(){

                @Override
                public void fileUploadFinished(String url, byte[] content, Throwable exception) {
                }
            };
            HashMap map = new HashMap();
            map.put((Object)"OF-Version", (Object)Config.getVersion());
            map.put((Object)"OF-Summary", (Object)CrashReporter.makeSummary(crashReport));
            FileUploadThread fileuploadthread = new FileUploadThread(s, (Map)map, abyte, ifileuploadlistener);
            fileuploadthread.setPriority(10);
            fileuploadthread.start();
            Thread.sleep((long)1000L);
        }
        catch (Exception exception) {
            Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }

    private static String makeReport(CrashReport crashReport) {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("OptiFineVersion: " + Config.getVersion() + "\n");
        stringbuffer.append("Summary: " + CrashReporter.makeSummary(crashReport) + "\n");
        stringbuffer.append("\n");
        stringbuffer.append(crashReport.getCompleteReport());
        stringbuffer.append("\n");
        return stringbuffer.toString();
    }

    private static String makeSummary(CrashReport crashReport) {
        Throwable throwable = crashReport.getCrashCause();
        if (throwable == null) {
            return "Unknown";
        }
        StackTraceElement[] astacktraceelement = throwable.getStackTrace();
        String s = "unknown";
        if (astacktraceelement.length > 0) {
            s = astacktraceelement[0].toString().trim();
        }
        String s1 = throwable.getClass().getName() + ": " + throwable.getMessage() + " (" + crashReport.getDescription() + ") [" + s + "]";
        return s1;
    }

    public static void extendCrashReport(CrashReportCategory cat) {
        cat.addCrashSection("OptiFine Version", Config.getVersion());
        cat.addCrashSection("OptiFine Build", Config.getBuild());
        if (Config.getGameSettings() != null) {
            cat.addCrashSection("Render Distance Chunks", "" + Config.getChunkViewDistance());
            cat.addCrashSection("Mipmaps", "" + Config.getMipmapLevels());
            cat.addCrashSection("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
            cat.addCrashSection("Antialiasing", "" + Config.getAntialiasingLevel());
            cat.addCrashSection("Multitexture", "" + Config.isMultiTexture());
        }
        cat.addCrashSection("Shaders", "" + Shaders.getShaderPackName());
        cat.addCrashSection("OpenGlVersion", "" + Config.openGlVersion);
        cat.addCrashSection("OpenGlRenderer", "" + Config.openGlRenderer);
        cat.addCrashSection("OpenGlVendor", "" + Config.openGlVendor);
        cat.addCrashSection("CpuCount", "" + Config.getAvailableProcessors());
    }

}

