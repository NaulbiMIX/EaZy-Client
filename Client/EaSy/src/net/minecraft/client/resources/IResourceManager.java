/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Set
 */
package net.minecraft.client.resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public interface IResourceManager {
    public Set<String> getResourceDomains();

    public IResource getResource(ResourceLocation var1) throws IOException;

    public List<IResource> getAllResources(ResourceLocation var1) throws IOException;
}

