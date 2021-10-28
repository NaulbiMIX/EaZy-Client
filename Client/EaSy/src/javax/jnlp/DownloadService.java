/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.net.URL
 */
package javax.jnlp;

import java.io.IOException;
import java.net.URL;
import javax.jnlp.DownloadServiceListener;

public interface DownloadService {
    public boolean isResourceCached(URL var1, String var2);

    public boolean isPartCached(String var1);

    public boolean isPartCached(String[] var1);

    public boolean isExtensionPartCached(URL var1, String var2, String var3);

    public boolean isExtensionPartCached(URL var1, String var2, String[] var3);

    public void loadResource(URL var1, String var2, DownloadServiceListener var3) throws IOException;

    public void loadPart(String var1, DownloadServiceListener var2) throws IOException;

    public void loadPart(String[] var1, DownloadServiceListener var2) throws IOException;

    public void loadExtensionPart(URL var1, String var2, String var3, DownloadServiceListener var4) throws IOException;

    public void loadExtensionPart(URL var1, String var2, String[] var3, DownloadServiceListener var4) throws IOException;

    public void removeResource(URL var1, String var2) throws IOException;

    public void removePart(String var1) throws IOException;

    public void removePart(String[] var1) throws IOException;

    public void removeExtensionPart(URL var1, String var2, String var3) throws IOException;

    public void removeExtensionPart(URL var1, String var2, String[] var3) throws IOException;

    public DownloadServiceListener getDefaultProgressWindow();
}

