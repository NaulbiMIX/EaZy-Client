/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.RandomAccessFile
 *  java.lang.Object
 */
package optifine.xdelta;

import java.io.IOException;
import java.io.RandomAccessFile;
import optifine.xdelta.SeekableSource;

public class RandomAccessFileSeekableSource
implements SeekableSource {
    RandomAccessFile raf;

    public RandomAccessFileSeekableSource(RandomAccessFile raf) {
        this.raf = raf;
    }

    @Override
    public void seek(long pos) throws IOException {
        this.raf.seek(pos);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.raf.read(b, off, len);
    }

    @Override
    public long length() throws IOException {
        return this.raf.length();
    }

    @Override
    public void close() throws IOException {
        this.raf.close();
    }
}

