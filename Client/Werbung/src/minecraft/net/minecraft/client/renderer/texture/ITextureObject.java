/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public interface ITextureObject {
    public void func_174936_b(boolean var1, boolean var2);

    public void func_174935_a();

    public void loadTexture(IResourceManager var1) throws IOException;

    public int getGlTextureId();
}

