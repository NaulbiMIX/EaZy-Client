/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.net.URL
 */
package javax.jnlp;

import java.net.URL;

public interface BasicService {
    public URL getCodeBase();

    public boolean isOffline();

    public boolean showDocument(URL var1);

    public boolean isWebBrowserSupported();
}

