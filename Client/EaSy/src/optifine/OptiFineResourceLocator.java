/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.InputStream
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package optifine;

import java.io.InputStream;
import optifine.IOptiFineResourceLocator;

public class OptiFineResourceLocator {
    private static IOptiFineResourceLocator resourceLocator;

    public static void setResourceLocator(IOptiFineResourceLocator resourceLocator) {
        Class<OptiFineResourceLocator> oclass = OptiFineResourceLocator.class;
        System.getProperties().put((Object)(oclass.getName() + ".class"), oclass);
    }

    public static InputStream getOptiFineResourceStream(String name) {
        return resourceLocator == null ? null : resourceLocator.getOptiFineResourceStream(name);
    }
}

