/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 */
package javax.jnlp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.jnlp.JNLPRandomAccessFile;

public interface FileContents {
    public String getName() throws IOException;

    public InputStream getInputStream() throws IOException;

    public OutputStream getOutputStream(boolean var1) throws IOException;

    public long getLength() throws IOException;

    public boolean canRead() throws IOException;

    public boolean canWrite() throws IOException;

    public JNLPRandomAccessFile getRandomAccessFile(String var1) throws IOException;

    public long getMaxLength() throws IOException;

    public long setMaxLength(long var1) throws IOException;
}

