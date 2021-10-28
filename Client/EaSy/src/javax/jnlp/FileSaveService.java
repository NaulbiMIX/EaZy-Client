/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package javax.jnlp;

import java.io.IOException;
import java.io.InputStream;
import javax.jnlp.FileContents;

public interface FileSaveService {
    public FileContents saveFileDialog(String var1, String[] var2, InputStream var3, String var4) throws IOException;

    public FileContents saveAsFileDialog(String var1, String[] var2, FileContents var3) throws IOException;
}

