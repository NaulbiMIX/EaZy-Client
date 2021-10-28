/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.List
 */
package optifine;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import optifine.LaunchUtils;

public class OptiFineForgeTweaker
implements ITweaker {
    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        OptiFineForgeTweaker.dbg("OptiFineForgeTweaker: acceptOptions");
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        if (LaunchUtils.isForgeServer()) {
            OptiFineForgeTweaker.dbg("OptiFineForgeTweaker: Forge server detected, skipping class transformer");
        } else {
            OptiFineForgeTweaker.dbg("OptiFineForgeTweaker: injectIntoClassLoader");
            classLoader.registerTransformer("optifine.OptiFineClassTransformer");
        }
    }

    @Override
    public String getLaunchTarget() {
        OptiFineForgeTweaker.dbg("OptiFineForgeTweaker: getLaunchTarget");
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        OptiFineForgeTweaker.dbg("OptiFineForgeTweaker: getLaunchArguments");
        return new String[0];
    }

    private static void dbg(String str) {
        System.out.println(str);
    }
}

