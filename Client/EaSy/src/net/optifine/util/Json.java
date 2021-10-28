/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json {
    public static float getFloat(JsonObject obj, String field, float def) {
        JsonElement jsonelement = obj.get(field);
        return jsonelement == null ? def : jsonelement.getAsFloat();
    }

    public static boolean getBoolean(JsonObject obj, String field, boolean def) {
        JsonElement jsonelement = obj.get(field);
        return jsonelement == null ? def : jsonelement.getAsBoolean();
    }

    public static String getString(JsonObject jsonObj, String field) {
        return Json.getString(jsonObj, field, null);
    }

    public static String getString(JsonObject jsonObj, String field, String def) {
        JsonElement jsonelement = jsonObj.get(field);
        return jsonelement == null ? def : jsonelement.getAsString();
    }

    public static float[] parseFloatArray(JsonElement jsonElement, int len) {
        return Json.parseFloatArray(jsonElement, len, null);
    }

    public static float[] parseFloatArray(JsonElement jsonElement, int len, float[] def) {
        if (jsonElement == null) {
            return def;
        }
        JsonArray jsonarray = jsonElement.getAsJsonArray();
        if (jsonarray.size() != len) {
            throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + len + ", array: " + (Object)jsonarray);
        }
        float[] afloat = new float[jsonarray.size()];
        for (int i = 0; i < afloat.length; ++i) {
            afloat[i] = jsonarray.get(i).getAsFloat();
        }
        return afloat;
    }

    public static int[] parseIntArray(JsonElement jsonElement, int len) {
        return Json.parseIntArray(jsonElement, len, null);
    }

    public static int[] parseIntArray(JsonElement jsonElement, int len, int[] def) {
        if (jsonElement == null) {
            return def;
        }
        JsonArray jsonarray = jsonElement.getAsJsonArray();
        if (jsonarray.size() != len) {
            throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + len + ", array: " + (Object)jsonarray);
        }
        int[] aint = new int[jsonarray.size()];
        for (int i = 0; i < aint.length; ++i) {
            aint[i] = jsonarray.get(i).getAsInt();
        }
        return aint;
    }
}
