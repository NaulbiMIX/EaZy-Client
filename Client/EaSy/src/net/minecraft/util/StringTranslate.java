/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Maps
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.CharSequence
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.nio.charset.Charset
 *  java.util.IllegalFormatException
 *  java.util.Map
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  org.apache.commons.io.Charsets
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class StringTranslate {
    private static final Pattern numericVariablePattern = Pattern.compile((String)"%(\\d+\\$)?[\\d\\.]*[df]");
    private static final Splitter equalSignSplitter = Splitter.on((char)'=').limit(2);
    private static StringTranslate instance = new StringTranslate();
    private final Map<String, String> languageList = Maps.newHashMap();
    private long lastUpdateTimeInMilliseconds;

    public StringTranslate() {
        try {
            InputStream inputstream = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
            for (String s : IOUtils.readLines((InputStream)inputstream, (Charset)Charsets.UTF_8)) {
                String[] astring;
                if (s.isEmpty() || s.charAt(0) == '#' || (astring = (String[])Iterables.toArray((Iterable)equalSignSplitter.split((CharSequence)s), String.class)) == null || astring.length != 2) continue;
                String s1 = astring[0];
                String s2 = numericVariablePattern.matcher((CharSequence)astring[1]).replaceAll("%$1s");
                this.languageList.put((Object)s1, (Object)s2);
            }
            this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
        }
        catch (IOException inputstream) {
            // empty catch block
        }
    }

    static StringTranslate getInstance() {
        return instance;
    }

    public static synchronized void replaceWith(Map<String, String> p_135063_0_) {
        StringTranslate.instance.languageList.clear();
        StringTranslate.instance.languageList.putAll(p_135063_0_);
        StringTranslate.instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
    }

    public synchronized String translateKey(String key) {
        return this.tryTranslateKey(key);
    }

    public synchronized /* varargs */ String translateKeyFormat(String key, Object ... format) {
        String s = this.tryTranslateKey(key);
        try {
            return String.format((String)s, (Object[])format);
        }
        catch (IllegalFormatException var5) {
            return "Format error: " + s;
        }
    }

    private String tryTranslateKey(String key) {
        String s = (String)this.languageList.get((Object)key);
        return s == null ? key : s;
    }

    public synchronized boolean isKeyTranslated(String key) {
        return this.languageList.containsKey((Object)key);
    }

    public long getLastUpdateTimeInMilliseconds() {
        return this.lastUpdateTimeInMilliseconds;
    }
}

