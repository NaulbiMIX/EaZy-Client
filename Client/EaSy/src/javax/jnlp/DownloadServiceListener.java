/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.net.URL
 */
package javax.jnlp;

import java.net.URL;

public interface DownloadServiceListener {
    public void progress(URL var1, String var2, long var3, long var5, int var7);

    public void validating(URL var1, String var2, long var3, long var5, int var7);

    public void upgradingArchive(URL var1, String var2, int var3, int var4);

    public void downloadFailed(URL var1, String var2);
}

