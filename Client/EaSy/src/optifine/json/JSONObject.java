/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package optifine.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import optifine.json.JSONAware;
import optifine.json.JSONStreamAware;
import optifine.json.JSONValue;

public class JSONObject
extends LinkedHashMap
implements Map,
JSONAware,
JSONStreamAware {
    private static final long serialVersionUID = -503443796854799292L;

    public static void writeJSONString(Map map, Writer out) throws IOException {
        if (map == null) {
            out.write("null");
        } else {
            boolean flag = true;
            Iterator iterator = map.entrySet().iterator();
            out.write(123);
            while (iterator.hasNext()) {
                if (flag) {
                    flag = false;
                } else {
                    out.write(44);
                }
                Map.Entry entry = (Map.Entry)iterator.next();
                out.write(34);
                out.write(JSONObject.escape(String.valueOf((Object)entry.getKey())));
                out.write(34);
                out.write(58);
                JSONValue.writeJSONString(entry.getValue(), out);
            }
            out.write(125);
        }
    }

    @Override
    public void writeJSONString(Writer out) throws IOException {
        JSONObject.writeJSONString(this, out);
    }

    public static String toJSONString(Map map) {
        if (map == null) {
            return "null";
        }
        StringBuffer stringbuffer = new StringBuffer();
        boolean flag = true;
        Iterator iterator = map.entrySet().iterator();
        stringbuffer.append('{');
        while (iterator.hasNext()) {
            if (flag) {
                flag = false;
            } else {
                stringbuffer.append(',');
            }
            Map.Entry entry = (Map.Entry)iterator.next();
            JSONObject.toJSONString(String.valueOf((Object)entry.getKey()), entry.getValue(), stringbuffer);
        }
        stringbuffer.append('}');
        return stringbuffer.toString();
    }

    @Override
    public String toJSONString() {
        return JSONObject.toJSONString(this);
    }

    private static String toJSONString(String key, Object value, StringBuffer sb) {
        sb.append('\"');
        if (key == null) {
            sb.append("null");
        } else {
            JSONValue.escape(key, sb);
        }
        sb.append('\"').append(':');
        sb.append(JSONValue.toJSONString(value));
        return sb.toString();
    }

    public String toString() {
        return this.toJSONString();
    }

    public static String toString(String key, Object value) {
        StringBuffer stringbuffer = new StringBuffer();
        JSONObject.toJSONString(key, value, stringbuffer);
        return stringbuffer.toString();
    }

    public static String escape(String s) {
        return JSONValue.escape(s);
    }
}

