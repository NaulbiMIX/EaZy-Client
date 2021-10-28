/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  java.awt.image.BufferedImage
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.Map
 *  java.util.Set
 */
package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.optifine.reflect.ReflectorForge;

public class DefaultResourcePack
implements IResourcePack {
    public static final Set<String> defaultResourceDomains = ImmutableSet.of((Object)"minecraft", (Object)"realms");
    private final Map<String, File> mapAssets;

    public DefaultResourcePack(Map<String, File> mapAssetsIn) {
        this.mapAssets = mapAssetsIn;
    }

    @Override
    public InputStream getInputStream(ResourceLocation location) throws IOException {
        InputStream inputstream = this.getResourceStream(location);
        if (inputstream != null) {
            return inputstream;
        }
        InputStream inputstream1 = this.getInputStreamAssets(location);
        if (inputstream1 != null) {
            return inputstream1;
        }
        throw new FileNotFoundException(location.getResourcePath());
    }

    public InputStream getInputStreamAssets(ResourceLocation location) throws IOException, FileNotFoundException {
        File file1 = (File)this.mapAssets.get((Object)location.toString());
        return file1 != null && file1.isFile() ? new FileInputStream(file1) : null;
    }

    private InputStream getResourceStream(ResourceLocation location) {
        String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
        InputStream inputstream = ReflectorForge.getOptiFineResourceStream(s);
        return inputstream != null ? inputstream : DefaultResourcePack.class.getResourceAsStream(s);
    }

    @Override
    public boolean resourceExists(ResourceLocation location) {
        return this.getResourceStream(location) != null || this.mapAssets.containsKey((Object)location.toString());
    }

    @Override
    public Set<String> getResourceDomains() {
        return defaultResourceDomains;
    }

    @Override
    public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        try {
            FileInputStream inputstream = new FileInputStream((File)this.mapAssets.get((Object)"pack.mcmeta"));
            return AbstractResourcePack.readMetadata(metadataSerializer, (InputStream)inputstream, metadataSectionName);
        }
        catch (RuntimeException var4) {
            return null;
        }
        catch (FileNotFoundException var51) {
            return null;
        }
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").getResourcePath()));
    }

    @Override
    public String getPackName() {
        return "Default";
    }
}

