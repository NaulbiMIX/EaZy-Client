/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  java.lang.Object
 */
package net.minecraft.util;

import com.google.gson.JsonElement;

public interface IJsonSerializable {
    public void fromJson(JsonElement var1);

    public JsonElement getSerializableElement();
}

