/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package optifine;

import java.io.IOException;
import java.io.InputStream;

public interface IResourceProvider {
    public InputStream getResourceStream(String var1) throws IOException;
}

