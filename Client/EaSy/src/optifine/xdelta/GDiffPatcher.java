/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.DataInputStream
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.io.RandomAccessFile
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package optifine.xdelta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import optifine.xdelta.ByteArraySeekableSource;
import optifine.xdelta.PatchException;
import optifine.xdelta.RandomAccessFileSeekableSource;
import optifine.xdelta.SeekableSource;

public class GDiffPatcher {
    public GDiffPatcher(File sourceFile, File patchFile, File outputFile) throws IOException, PatchException {
        RandomAccessFileSeekableSource randomaccessfileseekablesource = new RandomAccessFileSeekableSource(new RandomAccessFile(sourceFile, "r"));
        FileInputStream inputstream = new FileInputStream(patchFile);
        FileOutputStream outputstream = new FileOutputStream(outputFile);
        try {
            GDiffPatcher.runPatch(randomaccessfileseekablesource, (InputStream)inputstream, (OutputStream)outputstream);
        }
        catch (IOException ioexception) {
            throw ioexception;
        }
        catch (PatchException patchexception) {
            throw patchexception;
        }
        finally {
            randomaccessfileseekablesource.close();
            inputstream.close();
            outputstream.close();
        }
    }

    public GDiffPatcher(byte[] source, InputStream patch, OutputStream output) throws IOException, PatchException {
        this(new ByteArraySeekableSource(source), patch, output);
    }

    public GDiffPatcher(SeekableSource source, InputStream patch, OutputStream out) throws IOException, PatchException {
        GDiffPatcher.runPatch(source, patch, out);
    }

    private static void runPatch(SeekableSource source, InputStream patch, OutputStream out) throws IOException, PatchException {
        DataOutputStream dataoutputstream = new DataOutputStream(out);
        DataInputStream datainputstream = new DataInputStream(patch);
        try {
            byte[] abyte = new byte[256];
            int i = 0;
            if (datainputstream.readUnsignedByte() == 209 && datainputstream.readUnsignedByte() == 255 && datainputstream.readUnsignedByte() == 209 && datainputstream.readUnsignedByte() == 255 && datainputstream.readUnsignedByte() == 4) {
                i += 5;
                block21 : while (datainputstream.available() > 0) {
                    int j = datainputstream.readUnsignedByte();
                    int k = 0;
                    int l = 0;
                    switch (j) {
                        case 0: {
                            continue block21;
                        }
                        case 1: {
                            GDiffPatcher.append(1, (InputStream)datainputstream, (OutputStream)dataoutputstream);
                            i += 2;
                            continue block21;
                        }
                        case 2: {
                            GDiffPatcher.append(2, (InputStream)datainputstream, (OutputStream)dataoutputstream);
                            i += 3;
                            continue block21;
                        }
                        case 246: {
                            GDiffPatcher.append(246, (InputStream)datainputstream, (OutputStream)dataoutputstream);
                            i += 247;
                            continue block21;
                        }
                        case 247: {
                            k = datainputstream.readUnsignedShort();
                            GDiffPatcher.append(k, (InputStream)datainputstream, (OutputStream)dataoutputstream);
                            i += k + 3;
                            continue block21;
                        }
                        case 248: {
                            k = datainputstream.readInt();
                            GDiffPatcher.append(k, (InputStream)datainputstream, (OutputStream)dataoutputstream);
                            i += k + 5;
                            continue block21;
                        }
                        case 249: {
                            l = datainputstream.readUnsignedShort();
                            k = datainputstream.readUnsignedByte();
                            GDiffPatcher.copy(l, k, source, (OutputStream)dataoutputstream);
                            i += 4;
                            continue block21;
                        }
                        case 250: {
                            l = datainputstream.readUnsignedShort();
                            k = datainputstream.readUnsignedShort();
                            GDiffPatcher.copy(l, k, source, (OutputStream)dataoutputstream);
                            i += 5;
                            continue block21;
                        }
                        case 251: {
                            l = datainputstream.readUnsignedShort();
                            k = datainputstream.readInt();
                            GDiffPatcher.copy(l, k, source, (OutputStream)dataoutputstream);
                            i += 7;
                            continue block21;
                        }
                        case 252: {
                            l = datainputstream.readInt();
                            k = datainputstream.readUnsignedByte();
                            GDiffPatcher.copy(l, k, source, (OutputStream)dataoutputstream);
                            i += 8;
                            continue block21;
                        }
                        case 253: {
                            l = datainputstream.readInt();
                            k = datainputstream.readUnsignedShort();
                            GDiffPatcher.copy(l, k, source, (OutputStream)dataoutputstream);
                            i += 7;
                            continue block21;
                        }
                        case 254: {
                            l = datainputstream.readInt();
                            k = datainputstream.readInt();
                            GDiffPatcher.copy(l, k, source, (OutputStream)dataoutputstream);
                            i += 9;
                            continue block21;
                        }
                        case 255: {
                            long i1 = datainputstream.readLong();
                            k = datainputstream.readInt();
                            GDiffPatcher.copy(i1, k, source, (OutputStream)dataoutputstream);
                            i += 13;
                            continue block21;
                        }
                    }
                    GDiffPatcher.append(j, (InputStream)datainputstream, (OutputStream)dataoutputstream);
                    i += j + 1;
                }
                return;
            }
            System.err.println("magic string not found, aborting!");
        }
        catch (PatchException patchexception) {
            throw patchexception;
        }
        finally {
            dataoutputstream.flush();
        }
    }

    protected static void copy(long offset, int length, SeekableSource source, OutputStream output) throws IOException, PatchException {
        if (offset + (long)length > source.length()) {
            throw new PatchException("truncated source file, aborting");
        }
        byte[] abyte = new byte[256];
        source.seek(offset);
        while (length > 0) {
            int i = length > 256 ? 256 : length;
            int j = source.read(abyte, 0, i);
            output.write(abyte, 0, j);
            length -= j;
        }
    }

    protected static void append(int length, InputStream patch, OutputStream output) throws IOException {
        byte[] abyte = new byte[256];
        while (length > 0) {
            int i = length > 256 ? 256 : length;
            int j = patch.read(abyte, 0, i);
            output.write(abyte, 0, j);
            length -= j;
        }
    }

    public static void main(String[] argv) {
        if (argv.length != 3) {
            System.err.println("usage GDiffPatch source patch output");
            System.err.println("aborting..");
        } else {
            try {
                File file1 = new File(argv[0]);
                File file2 = new File(argv[1]);
                File file3 = new File(argv[2]);
                if (file1.length() > Integer.MAX_VALUE || file2.length() > Integer.MAX_VALUE) {
                    System.err.println("source or patch is too large, max length is 2147483647");
                    System.err.println("aborting..");
                    return;
                }
                new GDiffPatcher(file1, file2, file3);
                System.out.println("finished patching file");
            }
            catch (Exception exception) {
                System.err.println("error while patching: " + (Object)((Object)exception));
            }
        }
    }
}

