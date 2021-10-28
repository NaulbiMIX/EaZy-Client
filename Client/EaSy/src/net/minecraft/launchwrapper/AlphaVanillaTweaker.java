/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package net.minecraft.launchwrapper;

import java.io.File;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class AlphaVanillaTweaker
implements ITweaker {
    private List<String> args;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = args;
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.registerTransformer("net.minecraft.launchwrapper.injector.AlphaVanillaTweakInjector");
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.launchwrapper.injector.AlphaVanillaTweakInjector";
    }

    @Override
    public String[] getLaunchArguments() {
        return (String[])this.args.toArray((Object[])new String[this.args.size()]);
    }
}

