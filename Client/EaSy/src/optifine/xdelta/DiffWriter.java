/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package optifine.xdelta;

import java.io.IOException;

public interface DiffWriter {
    public void addCopy(int var1, int var2) throws IOException;

    public void addData(byte var1) throws IOException;

    public void flush() throws IOException;

    public void close() throws IOException;
}

