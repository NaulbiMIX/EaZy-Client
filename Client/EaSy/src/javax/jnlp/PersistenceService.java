/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.net.MalformedURLException
 *  java.net.URL
 */
package javax.jnlp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jnlp.FileContents;

public interface PersistenceService {
    public static final int CACHED = 0;
    public static final int TEMPORARY = 1;
    public static final int DIRTY = 2;

    public long create(URL var1, long var2) throws MalformedURLException, IOException;

    public FileContents get(URL var1) throws MalformedURLException, IOException, FileNotFoundException;

    public void delete(URL var1) throws MalformedURLException, IOException;

    public String[] getNames(URL var1) throws MalformedURLException, IOException;

    public int getTag(URL var1) throws MalformedURLException, IOException;

    public void setTag(URL var1, int var2) throws MalformedURLException, IOException;
}

