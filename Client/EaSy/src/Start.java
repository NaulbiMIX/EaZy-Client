/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 */
import java.util.Arrays;
import net.minecraft.client.main.Main;

public class Start {
    public static void main(String[] args) {
        Main.main(Start.concat(new String[]{"--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        Object[] result = Arrays.copyOf((Object[])first, (int)(first.length + second.length));
        System.arraycopy(second, (int)0, (Object)result, (int)first.length, (int)second.length);
        return result;
    }
}

