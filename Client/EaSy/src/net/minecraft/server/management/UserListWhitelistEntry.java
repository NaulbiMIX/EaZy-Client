/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.authlib.GameProfile
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.UUID
 */
package net.minecraft.server.management;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.management.UserListEntry;

public class UserListWhitelistEntry
extends UserListEntry<GameProfile> {
    public UserListWhitelistEntry(GameProfile profile) {
        super(profile);
    }

    public UserListWhitelistEntry(JsonObject json) {
        super(UserListWhitelistEntry.gameProfileFromJsonObject(json), json);
    }

    @Override
    protected void onSerialization(JsonObject data) {
        if (this.getValue() != null) {
            data.addProperty("uuid", ((GameProfile)this.getValue()).getId() == null ? "" : ((GameProfile)this.getValue()).getId().toString());
            data.addProperty("name", ((GameProfile)this.getValue()).getName());
            super.onSerialization(data);
        }
    }

    private static GameProfile gameProfileFromJsonObject(JsonObject json) {
        if (json.has("uuid") && json.has("name")) {
            UUID uuid;
            String s = json.get("uuid").getAsString();
            try {
                uuid = UUID.fromString((String)s);
            }
            catch (Throwable var4) {
                return null;
            }
            return new GameProfile(uuid, json.get("name").getAsString());
        }
        return null;
    }
}

