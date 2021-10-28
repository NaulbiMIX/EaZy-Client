/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.List
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class JsonException
extends IOException {
    private final List<Entry> field_151383_a = Lists.newArrayList();
    private final String exceptionMessage;

    public JsonException(String message) {
        this.field_151383_a.add((Object)new Entry());
        this.exceptionMessage = message;
    }

    public JsonException(String message, Throwable cause) {
        super(cause);
        this.field_151383_a.add((Object)new Entry());
        this.exceptionMessage = message;
    }

    public void func_151380_a(String p_151380_1_) {
        ((Entry)this.field_151383_a.get(0)).func_151373_a(p_151380_1_);
    }

    public void func_151381_b(String p_151381_1_) {
        ((Entry)this.field_151383_a.get(0)).field_151376_a = p_151381_1_;
        this.field_151383_a.add(0, (Object)new Entry());
    }

    public String getMessage() {
        return "Invalid " + ((Entry)this.field_151383_a.get(this.field_151383_a.size() - 1)).toString() + ": " + this.exceptionMessage;
    }

    public static JsonException func_151379_a(Exception p_151379_0_) {
        if (p_151379_0_ instanceof JsonException) {
            return (JsonException)((Object)p_151379_0_);
        }
        String s = p_151379_0_.getMessage();
        if (p_151379_0_ instanceof FileNotFoundException) {
            s = "File not found";
        }
        return new JsonException(s, p_151379_0_);
    }

    public static class Entry {
        private String field_151376_a = null;
        private final List<String> field_151375_b = Lists.newArrayList();

        private Entry() {
        }

        private void func_151373_a(String p_151373_1_) {
            this.field_151375_b.add(0, (Object)p_151373_1_);
        }

        public String func_151372_b() {
            return StringUtils.join(this.field_151375_b, (String)"->");
        }

        public String toString() {
            if (this.field_151376_a != null) {
                return !this.field_151375_b.isEmpty() ? this.field_151376_a + " " + this.func_151372_b() : this.field_151376_a;
            }
            return !this.field_151375_b.isEmpty() ? "(Unknown file) " + this.func_151372_b() : "(Unknown file)";
        }
    }

}

