/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 */
package optifine;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class OptiFineTweaker
implements ITweaker {
    private List<String> args;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        OptiFineTweaker.dbg("OptiFineTweaker: acceptOptions");
        this.args = new ArrayList(args);
        this.args.add((Object)"--gameDir");
        this.args.add((Object)gameDir.getAbsolutePath());
        this.args.add((Object)"--assetsDir");
        this.args.add((Object)assetsDir.getAbsolutePath());
        this.args.add((Object)"--version");
        this.args.add((Object)profile);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        OptiFineTweaker.dbg("OptiFineTweaker: injectIntoClassLoader");
        classLoader.registerTransformer("optifine.OptiFineClassTransformer");
    }

    @Override
    public String getLaunchTarget() {
        OptiFineTweaker.dbg("OptiFineTweaker: getLaunchTarget");
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        OptiFineTweaker.dbg("OptiFineTweaker: getLaunchArguments");
        return (String[])this.args.toArray((Object[])new String[this.args.size()]);
    }

    private static void dbg(String str) {
        System.out.println(str);
    }
}

