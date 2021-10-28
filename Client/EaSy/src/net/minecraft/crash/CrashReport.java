/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  java.io.File
 *  java.io.FileWriter
 *  java.io.PrintStream
 *  java.io.PrintWriter
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.Exception
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.OutOfMemoryError
 *  java.lang.Runtime
 *  java.lang.StackOverflowError
 *  java.lang.StackTraceElement
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.management.ManagementFactory
 *  java.lang.management.RuntimeMXBean
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.List
 *  java.util.concurrent.Callable
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.gen.layer.IntCache;
import net.optifine.CrashReporter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
    private static final Logger logger = LogManager.getLogger();
    private final String description;
    private final Throwable cause;
    private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
    private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
    private File crashReportFile;
    private boolean firstCategoryInCrashReport = true;
    private StackTraceElement[] stacktrace = new StackTraceElement[0];
    private boolean reported = false;

    public CrashReport(String descriptionIn, Throwable causeThrowable) {
        this.description = descriptionIn;
        this.cause = causeThrowable;
        this.populateEnvironment();
    }

    private void populateEnvironment() {
        this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable<String>(){

            public String call() {
                return "1.8.9";
            }
        });
        this.theReportCategory.addCrashSectionCallable("Operating System", new Callable<String>(){

            public String call() {
                return System.getProperty((String)"os.name") + " (" + System.getProperty((String)"os.arch") + ") version " + System.getProperty((String)"os.version");
            }
        });
        this.theReportCategory.addCrashSectionCallable("Java Version", new Callable<String>(){

            public String call() {
                return System.getProperty((String)"java.version") + ", " + System.getProperty((String)"java.vendor");
            }
        });
        this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable<String>(){

            public String call() {
                return System.getProperty((String)"java.vm.name") + " (" + System.getProperty((String)"java.vm.info") + "), " + System.getProperty((String)"java.vm.vendor");
            }
        });
        this.theReportCategory.addCrashSectionCallable("Memory", new Callable<String>(){

            public String call() {
                Runtime runtime = Runtime.getRuntime();
                long i = runtime.maxMemory();
                long j = runtime.totalMemory();
                long k = runtime.freeMemory();
                long l = i / 1024L / 1024L;
                long i2 = j / 1024L / 1024L;
                long j2 = k / 1024L / 1024L;
                return k + " bytes (" + j2 + " MB) / " + j + " bytes (" + i2 + " MB) up to " + i + " bytes (" + l + " MB)";
            }
        });
        this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable<String>(){

            public String call() {
                RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
                List list = runtimemxbean.getInputArguments();
                int i = 0;
                StringBuilder stringbuilder = new StringBuilder();
                for (String s : list) {
                    if (!s.startsWith("-X")) continue;
                    if (i++ > 0) {
                        stringbuilder.append(" ");
                    }
                    stringbuilder.append(s);
                }
                return String.format((String)"%d total; %s", (Object[])new Object[]{i, stringbuilder.toString()});
            }
        });
        this.theReportCategory.addCrashSectionCallable("IntCache", new Callable<String>(){

            public String call() throws Exception {
                return IntCache.getCacheSizes();
            }
        });
        if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
            Object instance = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            Reflector.callString(instance, Reflector.FMLCommonHandler_enhanceCrashReport, this, this.theReportCategory);
        }
    }

    public String getDescription() {
        return this.description;
    }

    public Throwable getCrashCause() {
        return this.cause;
    }

    public void getSectionsInStringBuilder(StringBuilder builder) {
        if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0) {
            this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])((Object[])((CrashReportCategory)this.crashReportSections.get(0)).getStackTrace()), (int)0, (int)1);
        }
        if (this.stacktrace != null && this.stacktrace.length > 0) {
            builder.append("-- Head --\n");
            builder.append("Stacktrace:\n");
            for (StackTraceElement stacktraceelement : this.stacktrace) {
                builder.append("\t").append("at ").append(stacktraceelement.toString());
                builder.append("\n");
            }
            builder.append("\n");
        }
        for (CrashReportCategory crashreportcategory : this.crashReportSections) {
            crashreportcategory.appendToStringBuilder(builder);
            builder.append("\n\n");
        }
        this.theReportCategory.appendToStringBuilder(builder);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getCauseStackTraceOrString() {
        StringWriter stringwriter = null;
        PrintWriter printwriter = null;
        Throwable throwable = this.cause;
        if (throwable.getMessage() == null) {
            if (throwable instanceof NullPointerException) {
                throwable = new NullPointerException(this.description);
            } else if (throwable instanceof StackOverflowError) {
                throwable = new StackOverflowError(this.description);
            } else if (throwable instanceof OutOfMemoryError) {
                throwable = new OutOfMemoryError(this.description);
            }
            throwable.setStackTrace(this.cause.getStackTrace());
        }
        String s = throwable.toString();
        try {}
        catch (Throwable throwable2) {
            IOUtils.closeQuietly((Writer)stringwriter);
            IOUtils.closeQuietly(printwriter);
            throw throwable2;
        }
        stringwriter = new StringWriter();
        printwriter = new PrintWriter((Writer)stringwriter);
        throwable.printStackTrace(printwriter);
        s = stringwriter.toString();
        IOUtils.closeQuietly((Writer)stringwriter);
        IOUtils.closeQuietly((Writer)printwriter);
        return s;
    }

    public String getCompleteReport() {
        if (!this.reported) {
            this.reported = true;
            CrashReporter.onCrashReport(this, this.theReportCategory);
        }
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Minecraft Crash Report ----\n");
        Reflector.call(Reflector.BlamingTransformer_onCrash, new Object[]{stringbuilder});
        Reflector.call(Reflector.CoreModManager_onCrash, new Object[]{stringbuilder});
        stringbuilder.append("// ");
        stringbuilder.append(CrashReport.getWittyComment());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time: ");
        stringbuilder.append(new SimpleDateFormat().format(new Date()));
        stringbuilder.append("\n");
        stringbuilder.append("Description: ");
        stringbuilder.append(this.description);
        stringbuilder.append("\n\n");
        stringbuilder.append(this.getCauseStackTraceOrString());
        stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int i = 0; i < 87; ++i) {
            stringbuilder.append("-");
        }
        stringbuilder.append("\n\n");
        this.getSectionsInStringBuilder(stringbuilder);
        return stringbuilder.toString();
    }

    public File getFile() {
        return this.crashReportFile;
    }

    public boolean saveToFile(File toFile) {
        if (this.crashReportFile != null) {
            return false;
        }
        if (toFile.getParentFile() != null) {
            toFile.getParentFile().mkdirs();
        }
        try {
            FileWriter filewriter = new FileWriter(toFile);
            filewriter.write(this.getCompleteReport());
            filewriter.close();
            this.crashReportFile = toFile;
            return true;
        }
        catch (Throwable throwable) {
            logger.error("Could not save crash report to " + (Object)toFile, throwable);
            return false;
        }
    }

    public CrashReportCategory getCategory() {
        return this.theReportCategory;
    }

    public CrashReportCategory makeCategory(String name) {
        return this.makeCategoryDepth(name, 1);
    }

    public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength) {
        CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);
        if (this.firstCategoryInCrashReport) {
            int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
            StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
            StackTraceElement stacktraceelement = null;
            StackTraceElement stacktraceelement2 = null;
            int j = astacktraceelement.length - i;
            if (j < 0) {
                System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
            }
            if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
                stacktraceelement = astacktraceelement[j];
                if (astacktraceelement.length + 1 - i < astacktraceelement.length) {
                    stacktraceelement2 = astacktraceelement[astacktraceelement.length + 1 - i];
                }
            }
            this.firstCategoryInCrashReport = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement2);
            if (i > 0 && !this.crashReportSections.isEmpty()) {
                CrashReportCategory crashreportcategory2 = (CrashReportCategory)this.crashReportSections.get(this.crashReportSections.size() - 1);
                crashreportcategory2.trimStackTraceEntriesFromBottom(i);
            } else if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length) {
                this.stacktrace = new StackTraceElement[j];
                System.arraycopy((Object)astacktraceelement, (int)0, (Object)this.stacktrace, (int)0, (int)this.stacktrace.length);
            } else {
                this.firstCategoryInCrashReport = false;
            }
        }
        this.crashReportSections.add((Object)crashreportcategory);
        return crashreportcategory;
    }

    private static String getWittyComment() {
        String[] astring = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};
        try {
            return astring[(int)(System.nanoTime() % (long)astring.length)];
        }
        catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }

    public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn) {
        CrashReport crashreport = causeIn instanceof ReportedException ? ((ReportedException)((Object)causeIn)).getCrashReport() : new CrashReport(descriptionIn, causeIn);
        return crashreport;
    }

}

