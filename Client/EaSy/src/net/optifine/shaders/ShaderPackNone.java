/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.shaders;

import java.io.InputStream;
import net.optifine.shaders.IShaderPack;

public class ShaderPackNone
implements IShaderPack {
    @Override
    public void close() {
    }

    @Override
    public InputStream getResourceAsStream(String resName) {
        return null;
    }

    @Override
    public boolean hasDirectory(String name) {
        return false;
    }

    @Override
    public String getName() {
        return "OFF";
    }
}

