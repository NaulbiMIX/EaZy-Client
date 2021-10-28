/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.UserAuthentication
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.net.Proxy
 */
package de.flori2007.easyclient.utils;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;

public class AuthHelper {
    public static String getCrackedName(String email, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(email);
        auth.setPassword(password);
        try {
            auth.logIn();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return auth.getSelectedProfile().getName();
    }
}

