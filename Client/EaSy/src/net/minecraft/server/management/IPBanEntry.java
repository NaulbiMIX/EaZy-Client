/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Date
 */
package net.minecraft.server.management;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;
import net.minecraft.server.management.BanEntry;

public class IPBanEntry
extends BanEntry<String> {
    public IPBanEntry(String valueIn) {
        this(valueIn, null, null, null, null);
    }

    public IPBanEntry(String valueIn, Date startDate, String banner, Date endDate, String banReason) {
        super(valueIn, startDate, banner, endDate, banReason);
    }

    public IPBanEntry(JsonObject json) {
        super(IPBanEntry.getIPFromJson(json), json);
    }

    private static String getIPFromJson(JsonObject json) {
        return json.has("ip") ? json.get("ip").getAsString() : null;
    }

    @Override
    protected void onSerialization(JsonObject data) {
        if (this.getValue() != null) {
            data.addProperty("ip", (String)this.getValue());
            super.onSerialization(data);
        }
    }
}

