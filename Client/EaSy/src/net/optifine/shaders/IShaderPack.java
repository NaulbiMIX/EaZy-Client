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

public interface IShaderPack {
    public String getName();

    public InputStream getResourceAsStream(String var1);

    public boolean hasDirectory(String var1);

    public void close();
}

