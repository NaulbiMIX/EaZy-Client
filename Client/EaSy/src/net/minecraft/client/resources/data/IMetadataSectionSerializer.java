/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializer
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializer;
import net.minecraft.client.resources.data.IMetadataSection;

public interface IMetadataSectionSerializer<T extends IMetadataSection>
extends JsonDeserializer<T> {
    public String getSectionName();
}

