/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.reflect.Field
 *  java.util.Map
 */
package optifine;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Map;
import optifine.Utils;

public class LaunchUtils {
    private static Boolean forgeServer = null;

    public static boolean isForgeServer() {
        if (forgeServer == null) {
            try {
                Class oclass = Class.forName((String)"net.minecraft.launchwrapper.Launch");
                Field field = oclass.getField("blackboard");
                Map map = (Map)field.get(null);
                Map map1 = (Map)map.get((Object)"launchArgs");
                String s = (String)map1.get((Object)"--accessToken");
                String s1 = (String)map1.get((Object)"--version");
                boolean flag = s == null && Utils.equals(s1, "UnknownFMLProfile");
                forgeServer = flag;
            }
            catch (Throwable throwable) {
                System.out.println("Error checking Forge server: " + throwable.getClass().getName() + ": " + throwable.getMessage());
                forgeServer = Boolean.FALSE;
            }
        }
        return forgeServer;
    }
}

