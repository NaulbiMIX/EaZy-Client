/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture
extends AbstractTexture {
    private static final Logger logger = LogManager.getLogger();
    protected final ResourceLocation textureLocation;
    private static final String __OBFID = "CL_00001052";

    public SimpleTexture(ResourceLocation p_i1275_1_) {
        this.textureLocation = p_i1275_1_;
    }

    @Override
    public void loadTexture(IResourceManager p_110551_1_) throws IOException {
        this.deleteGlTexture();
        try (InputStream var2 = null;){
            IResource var3 = p_110551_1_.getResource(this.textureLocation);
            var2 = var3.getInputStream();
            BufferedImage var4 = TextureUtil.func_177053_a(var2);
            boolean var5 = false;
            boolean var6 = false;
            if (var3.hasMetadata()) {
                try {
                    TextureMetadataSection var7 = (TextureMetadataSection)var3.getMetadata("texture");
                    if (var7 != null) {
                        var5 = var7.getTextureBlur();
                        var6 = var7.getTextureClamp();
                    }
                }
                catch (RuntimeException var11) {
                    logger.warn("Failed reading metadata of: " + this.textureLocation, (Throwable)var11);
                }
            }
            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), var4, var5, var6);
        }
    }
}

