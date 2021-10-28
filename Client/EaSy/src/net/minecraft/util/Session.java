/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.util.UUIDTypeAdapter
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.UUID
 */
package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private final String username;
    private final String playerID;
    private final String token;
    private final Type sessionType;

    public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
        this.username = usernameIn;
        this.playerID = playerIDIn;
        this.token = tokenIn;
        this.sessionType = Type.setSessionType(sessionTypeIn);
    }

    public String getSessionID() {
        return "token:" + this.token + ":" + this.playerID;
    }

    public String getPlayerID() {
        return this.playerID;
    }

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

    public GameProfile getProfile() {
        try {
            UUID uuid = UUIDTypeAdapter.fromString((String)this.getPlayerID());
            return new GameProfile(uuid, this.getUsername());
        }
        catch (IllegalArgumentException var2) {
            return new GameProfile((UUID)null, this.getUsername());
        }
    }

    public Type getSessionType() {
        return this.sessionType;
    }

    public static enum Type {
        LEGACY("legacy"),
        MOJANG("mojang");
        
        private static final Map<String, Type> SESSION_TYPES;
        private final String sessionType;

        private Type(String sessionTypeIn) {
            this.sessionType = sessionTypeIn;
        }

        public static Type setSessionType(String sessionTypeIn) {
            return (Type)((Object)SESSION_TYPES.get((Object)sessionTypeIn.toLowerCase()));
        }

        static {
            SESSION_TYPES = Maps.newHashMap();
            for (Type session$type : Type.values()) {
                SESSION_TYPES.put((Object)session$type.sessionType, (Object)session$type);
            }
        }
    }

}

