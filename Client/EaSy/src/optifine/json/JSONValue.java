/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Reader
 *  java.io.StringReader
 *  java.io.Writer
 *  java.lang.Boolean
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.List
 *  java.util.Map
 */
package optifine.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import optifine.json.JSONArray;
import optifine.json.JSONAware;
import optifine.json.JSONObject;
import optifine.json.JSONParser;
import optifine.json.JSONStreamAware;
import optifine.json.ParseException;

public class JSONValue {
    public static Object parse(Reader in) {
        try {
            JSONParser jsonparser = new JSONParser();
            return jsonparser.parse(in);
        }
        catch (Exception var2) {
            return null;
        }
    }

    public static Object parse(String s) {
        StringReader stringreader = new StringReader(s);
        return JSONValue.parse((Reader)stringreader);
    }

    public static Object parseWithException(Reader in) throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        return jsonparser.parse(in);
    }

    public static Object parseWithException(String s) throws ParseException {
        JSONParser jsonparser = new JSONParser();
        return jsonparser.parse(s);
    }

    public static void writeJSONString(Object value, Writer out) throws IOException {
        if (value == null) {
            out.write("null");
        } else if (value instanceof String) {
            out.write(34);
            out.write(JSONValue.escape((String)value));
            out.write(34);
        } else if (value instanceof Double) {
            if (!((Double)value).isInfinite() && !((Double)value).isNaN()) {
                out.write(value.toString());
            } else {
                out.write("null");
            }
        } else if (!(value instanceof Float)) {
            if (value instanceof Number) {
                out.write(value.toString());
            } else if (value instanceof Boolean) {
                out.write(value.toString());
            } else if (value instanceof JSONStreamAware) {
                ((JSONStreamAware)value).writeJSONString(out);
            } else if (value instanceof JSONAware) {
                out.write(((JSONAware)value).toJSONString());
            } else if (value instanceof Map) {
                JSONObject.writeJSONString((Map)value, out);
            } else if (value instanceof List) {
                JSONArray.writeJSONString((List)value, out);
            } else {
                out.write(value.toString());
            }
        } else if (!((Float)value).isInfinite() && !((Float)value).isNaN()) {
            out.write(value.toString());
        } else {
            out.write("null");
        }
    }

    public static String toJSONString(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + JSONValue.escape((String)value) + "\"";
        }
        if (value instanceof Double) {
            return !((Double)value).isInfinite() && !((Double)value).isNaN() ? value.toString() : "null";
        }
        if (value instanceof Float) {
            return !((Float)value).isInfinite() && !((Float)value).isNaN() ? value.toString() : "null";
        }
        if (value instanceof Number) {
            return value.toString();
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof JSONAware) {
            return ((JSONAware)value).toJSONString();
        }
        if (value instanceof Map) {
            return JSONObject.toJSONString((Map)value);
        }
        return value instanceof List ? JSONArray.toJSONString((List)value) : value.toString();
    }

    public static String escape(String s) {
        if (s == null) {
            return null;
        }
        StringBuffer stringbuffer = new StringBuffer();
        JSONValue.escape(s, stringbuffer);
        return stringbuffer.toString();
    }

    static void escape(String s, StringBuffer sb) {
        block9 : for (int i = 0; i < s.length(); ++i) {
            char c0 = s.charAt(i);
            switch (c0) {
                case '\b': {
                    sb.append("\\b");
                    continue block9;
                }
                case '\t': {
                    sb.append("\\t");
                    continue block9;
                }
                case '\n': {
                    sb.append("\\n");
                    continue block9;
                }
                case '\f': {
                    sb.append("\\f");
                    continue block9;
                }
                case '\r': {
                    sb.append("\\r");
                    continue block9;
                }
                case '\"': {
                    sb.append("\\\"");
                    continue block9;
                }
                case '\\': {
                    sb.append("\\\\");
                    continue block9;
                }
                default: {
                    if (c0 >= '\u0000' && c0 <= '\u001f' || c0 >= '' && c0 <= '\u009f' || c0 >= '\u2000' && c0 <= '\u20ff') {
                        String s1 = Integer.toHexString((int)c0);
                        sb.append("\\u");
                        for (int j = 0; j < 4 - s1.length(); ++j) {
                            sb.append('0');
                        }
                        sb.append(s1.toUpperCase());
                        continue block9;
                    }
                    sb.append(c0);
                }
            }
        }
    }
}

