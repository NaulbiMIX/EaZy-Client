/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.client.resources;

import java.io.InputStream;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

public interface IResource {
    public ResourceLocation getResourceLocation();

    public InputStream getInputStream();

    public boolean hasMetadata();

    public <T extends IMetadataSection> T getMetadata(String var1);

    public String getResourcePackName();
}

