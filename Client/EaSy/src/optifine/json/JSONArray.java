/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package optifine.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import optifine.json.JSONAware;
import optifine.json.JSONStreamAware;
import optifine.json.JSONValue;

public class JSONArray
extends ArrayList
implements List,
JSONAware,
JSONStreamAware {
    private static final long serialVersionUID = 3957988303675231981L;

    public static void writeJSONString(List list, Writer out) throws IOException {
        if (list == null) {
            out.write("null");
        } else {
            boolean flag = true;
            Iterator iterator = list.iterator();
            out.write(91);
            while (iterator.hasNext()) {
                if (flag) {
                    flag = false;
                } else {
                    out.write(44);
                }
                Object object = iterator.next();
                if (object == null) {
                    out.write("null");
                    continue;
                }
                JSONValue.writeJSONString(object, out);
            }
            out.write(93);
        }
    }

    @Override
    public void writeJSONString(Writer out) throws IOException {
        JSONArray.writeJSONString(this, out);
    }

    public static String toJSONString(List list) {
        if (list == null) {
            return "null";
        }
        boolean flag = true;
        StringBuffer stringbuffer = new StringBuffer();
        Iterator iterator = list.iterator();
        stringbuffer.append('[');
        while (iterator.hasNext()) {
            if (flag) {
                flag = false;
            } else {
                stringbuffer.append(',');
            }
            Object object = iterator.next();
            if (object == null) {
                stringbuffer.append("null");
                continue;
            }
            stringbuffer.append(JSONValue.toJSONString(object));
        }
        stringbuffer.append(']');
        return stringbuffer.toString();
    }

    @Override
    public String toJSONString() {
        return JSONArray.toJSONString(this);
    }

    public String toString() {
        return this.toJSONString();
    }
}

