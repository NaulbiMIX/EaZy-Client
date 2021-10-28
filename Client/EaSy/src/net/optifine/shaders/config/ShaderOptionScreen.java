/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package net.optifine.shaders.config;

import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;

public class ShaderOptionScreen
extends ShaderOption {
    public ShaderOptionScreen(String name) {
        super(name, null, null, new String[0], null, null);
    }

    @Override
    public String getNameText() {
        return Shaders.translate("screen." + this.getName(), this.getName());
    }

    @Override
    public String getDescriptionText() {
        return Shaders.translate("screen." + this.getName() + ".comment", null);
    }
}

