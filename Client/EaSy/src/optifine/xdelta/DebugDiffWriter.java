/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package optifine.xdelta;

import java.io.IOException;
import java.io.PrintStream;
import optifine.xdelta.DiffWriter;

public class DebugDiffWriter
implements DiffWriter {
    byte[] buf = new byte[256];
    int buflen = 0;

    @Override
    public void addCopy(int offset, int length) throws IOException {
        if (this.buflen > 0) {
            this.writeBuf();
        }
        System.err.println("COPY off: " + offset + ", len: " + length);
    }

    @Override
    public void addData(byte b) throws IOException {
        if (this.buflen < 256) {
            this.buf[this.buflen++] = b;
        } else {
            this.writeBuf();
        }
    }

    private void writeBuf() {
        System.err.print("DATA: ");
        for (int i = 0; i < this.buflen; ++i) {
            if (this.buf[i] == 10) {
                System.err.print("\\n");
                continue;
            }
            System.err.print(String.valueOf((char)((char)this.buf[i])));
        }
        System.err.println("");
        this.buflen = 0;
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }
}

