/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  java.lang.ClassCastException
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Type
 *  java.util.ArrayList
 *  java.util.List
 */
package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.JsonUtils;

public class TextureMetadataSectionSerializer
extends BaseMetadataSectionSerializer<TextureMetadataSection> {
    public TextureMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
        JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
        boolean flag = JsonUtils.getBoolean(jsonobject, "blur", false);
        boolean flag1 = JsonUtils.getBoolean(jsonobject, "clamp", false);
        ArrayList list = Lists.newArrayList();
        if (jsonobject.has("mipmaps")) {
            try {
                JsonArray jsonarray = jsonobject.getAsJsonArray("mipmaps");
                for (int i = 0; i < jsonarray.size(); ++i) {
                    JsonElement jsonelement = jsonarray.get(i);
                    if (jsonelement.isJsonPrimitive()) {
                        try {
                            list.add((Object)jsonelement.getAsInt());
                            continue;
                        }
                        catch (NumberFormatException numberformatexception) {
                            throw new JsonParseException("Invalid texture->mipmap->" + i + ": expected number, was " + (Object)jsonelement, (Throwable)numberformatexception);
                        }
                    }
                    if (!jsonelement.isJsonObject()) continue;
                    throw new JsonParseException("Invalid texture->mipmap->" + i + ": expected number, was " + (Object)jsonelement);
                }
            }
            catch (ClassCastException classcastexception) {
                throw new JsonParseException("Invalid texture->mipmaps: expected array, was " + (Object)jsonobject.get("mipmaps"), (Throwable)classcastexception);
            }
        }
        return new TextureMetadataSection(flag, flag1, (List<Integer>)list);
    }

    @Override
    public String getSectionName() {
        return "texture";
    }
}

