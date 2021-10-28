/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.System
 */
package optifine.xdelta;

import java.io.IOException;
import optifine.xdelta.SeekableSource;

public class ByteArraySeekableSource
implements SeekableSource {
    byte[] source;
    long lastPos = 0L;

    public ByteArraySeekableSource(byte[] source) {
        this.source = source;
    }

    @Override
    public void seek(long pos) throws IOException {
        this.lastPos = pos;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int i = this.source.length - (int)this.lastPos;
        if (i <= 0) {
            return -1;
        }
        if (i < len) {
            len = i;
        }
        System.arraycopy((Object)this.source, (int)((int)this.lastPos), (Object)b, (int)off, (int)len);
        this.lastPos += (long)len;
        return len;
    }

    @Override
    public long length() throws IOException {
        return this.source.length;
    }

    @Override
    public void close() throws IOException {
        this.source = null;
    }
}

