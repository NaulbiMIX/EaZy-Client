/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.Reader
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.charset.Charset
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
    private static final Logger logger = LogManager.getLogger();
    private final Map<String, File> resourceMap = Maps.newHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResourceIndex(File p_i1047_1_, String p_i1047_2_) {
        if (p_i1047_2_ != null) {
            File file1 = new File(p_i1047_1_, "objects");
            File file2 = new File(p_i1047_1_, "indexes/" + p_i1047_2_ + ".json");
            BufferedReader bufferedreader = null;
            try {
                bufferedreader = Files.newReader((File)file2, (Charset)Charsets.UTF_8);
                JsonObject jsonobject = new JsonParser().parse((Reader)bufferedreader).getAsJsonObject();
                JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "objects", null);
                if (jsonobject1 != null) {
                    for (Map.Entry entry : jsonobject1.entrySet()) {
                        JsonObject jsonobject2 = (JsonObject)entry.getValue();
                        String s = (String)entry.getKey();
                        String[] astring = s.split("/", 2);
                        String s1 = astring.length == 1 ? astring[0] : astring[0] + ":" + astring[1];
                        String s2 = JsonUtils.getString(jsonobject2, "hash");
                        File file3 = new File(file1, s2.substring(0, 2) + "/" + s2);
                        this.resourceMap.put((Object)s1, (Object)file3);
                    }
                }
            }
            catch (JsonParseException var20) {
                logger.error("Unable to parse resource index file: " + (Object)file2);
            }
            catch (FileNotFoundException var21) {
                logger.error("Can't find the resource index file: " + (Object)file2);
            }
            finally {
                IOUtils.closeQuietly((Reader)bufferedreader);
            }
        }
    }

    public Map<String, File> getResourceMap() {
        return this.resourceMap;
    }
}

