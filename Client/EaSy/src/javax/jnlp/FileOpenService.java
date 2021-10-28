/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package javax.jnlp;

import java.io.IOException;
import javax.jnlp.FileContents;

public interface FileOpenService {
    public FileContents openFileDialog(String var1, String[] var2) throws IOException;

    public FileContents[] openMultiFileDialog(String var1, String[] var2) throws IOException;
}

