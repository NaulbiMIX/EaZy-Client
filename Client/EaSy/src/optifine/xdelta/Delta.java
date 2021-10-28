/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedInputStream
 *  java.io.BufferedOutputStream
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.io.PushbackInputStream
 *  java.io.RandomAccessFile
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package optifine.xdelta;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import optifine.xdelta.ByteArraySeekableSource;
import optifine.xdelta.Checksum;
import optifine.xdelta.DebugDiffWriter;
import optifine.xdelta.DeltaException;
import optifine.xdelta.DiffWriter;
import optifine.xdelta.GDiffWriter;
import optifine.xdelta.RandomAccessFileSeekableSource;
import optifine.xdelta.SeekableSource;
import optifine.xdelta.SeekableSourceInputStream;

public class Delta {
    public static final int S = 16;
    public static final boolean debug = false;
    public static final int buff_size = 1024;

    public static void computeDelta(SeekableSource source, InputStream targetIS, int targetLength, DiffWriter output) throws IOException, DeltaException {
        int i = (int)source.length();
        Checksum checksum = new Checksum();
        checksum.generateChecksums(new SeekableSourceInputStream(source), i);
        source.seek(0L);
        PushbackInputStream pushbackinputstream = new PushbackInputStream((InputStream)new BufferedInputStream(targetIS), 1024);
        boolean flag = false;
        byte[] abyte = new byte[16];
        long j = 0L;
        byte[] abyte1 = new byte[1];
        byte[] abyte2 = new byte[16];
        if (targetLength > 16 && i > 16) {
            int l3;
            int i4 = l3 = pushbackinputstream.read(abyte, 0, 16);
            long i1 = j = Checksum.queryChecksum(abyte, 16);
            boolean flag1 = false;
            while (!flag) {
                int j1 = checksum.findChecksumIndex(j);
                if (j1 != -1) {
                    boolean flag2 = true;
                    int k1 = j1 * 16;
                    int l1 = 15;
                    source.seek(k1);
                    if (!flag1 && source.read(abyte2, 0, 16) != -1) {
                        for (int i2 = 0; i2 < 16; ++i2) {
                            if (abyte2[i2] == abyte[i2]) continue;
                            flag2 = false;
                        }
                    } else {
                        flag1 = true;
                    }
                    if (flag2 & !flag1) {
                        long l4 = System.currentTimeMillis();
                        boolean flag3 = true;
                        byte[] abyte3 = new byte[1024];
                        byte[] abyte4 = new byte[1024];
                        int j2 = 0;
                        int k2 = 0;
                        boolean l2 = false;
                        do {
                            if ((j2 = source.read(abyte3, 0, 1024)) == -1) {
                                flag1 = true;
                                break;
                            }
                            k2 = pushbackinputstream.read(abyte4, 0, j2);
                            if (k2 == -1) break;
                            int i3 = Math.min((int)j2, (int)k2);
                            int j3 = 0;
                            do {
                                ++i4;
                                ++l1;
                                flag3 = abyte3[j3] == abyte4[j3];
                                ++j3;
                                if (flag3) continue;
                                abyte1[0] = abyte4[j3 - 1];
                                if (k2 == -1) continue;
                                pushbackinputstream.unread(abyte4, j3, k2 - j3);
                            } while (j3 < i3 && flag3);
                            abyte1[0] = abyte4[j3 - 1];
                        } while (flag3 && targetLength - i4 > 0);
                        output.addCopy(k1, l1);
                        if (targetLength - i4 <= 15) {
                            abyte[0] = abyte1[0];
                            int i5 = targetLength - i4;
                            pushbackinputstream.read(abyte, 1, i5);
                            i4 += i5;
                            for (int k3 = 0; k3 < i5 + 1; ++k3) {
                                output.addData(abyte[k3]);
                            }
                            flag = true;
                            continue;
                        }
                        abyte[0] = abyte1[0];
                        pushbackinputstream.read(abyte, 1, 15);
                        i4 += 15;
                        i1 = j = Checksum.queryChecksum(abyte, 16);
                        continue;
                    }
                }
                if (targetLength - i4 > 0) {
                    pushbackinputstream.read(abyte1, 0, 1);
                    ++i4;
                    output.addData(abyte[0]);
                    i1 = Checksum.incrementChecksum(i1, abyte[0], abyte1[0]);
                    for (int k4 = 0; k4 < 15; ++k4) {
                        abyte[k4] = abyte[k4 + 1];
                    }
                    abyte[15] = abyte1[0];
                    j = Checksum.queryChecksum(abyte, 16);
                    continue;
                }
                for (int j4 = 0; j4 < 16; ++j4) {
                    output.addData(abyte[j4]);
                }
                flag = true;
            }
        } else {
            int k;
            while ((k = pushbackinputstream.read(abyte)) >= 0) {
                for (int l = 0; l < k; ++l) {
                    output.addData(abyte[l]);
                }
            }
        }
    }

    public static void computeDelta(byte[] source, InputStream targetIS, int targetLength, DiffWriter output) throws IOException, DeltaException {
        Delta.computeDelta(new ByteArraySeekableSource(source), targetIS, targetLength, output);
    }

    public static void computeDelta(File sourceFile, File targetFile, DiffWriter output) throws IOException, DeltaException {
        int i = (int)targetFile.length();
        RandomAccessFileSeekableSource seekablesource = new RandomAccessFileSeekableSource(new RandomAccessFile(sourceFile, "r"));
        FileInputStream inputstream = new FileInputStream(targetFile);
        try {
            Delta.computeDelta(seekablesource, (InputStream)inputstream, i, output);
        }
        catch (IOException ioexception) {
            throw ioexception;
        }
        catch (DeltaException deltaexception) {
            throw deltaexception;
        }
        finally {
            output.flush();
            seekablesource.close();
            inputstream.close();
            output.close();
        }
    }

    public static void main(String[] argv) {
        new Delta();
        if (argv.length != 3) {
            System.err.println("usage Delta [-d] source target [output]");
            System.err.println("either -d or an output filename must be specified.");
            System.err.println("aborting..");
        } else {
            try {
                DiffWriter diffwriter = null;
                File file1 = null;
                File file2 = null;
                if (argv[0].equals((Object)"-d")) {
                    file1 = new File(argv[1]);
                    file2 = new File(argv[2]);
                    diffwriter = new DebugDiffWriter();
                } else {
                    file1 = new File(argv[0]);
                    file2 = new File(argv[1]);
                    diffwriter = new GDiffWriter(new DataOutputStream((OutputStream)new BufferedOutputStream((OutputStream)new FileOutputStream(new File(argv[2])))));
                }
                if (file1.length() > Integer.MAX_VALUE || file2.length() > Integer.MAX_VALUE) {
                    System.err.println("source or target is too large, max length is 2147483647");
                    System.err.println("aborting..");
                    return;
                }
                Delta.computeDelta(file1, file2, diffwriter);
                diffwriter.flush();
                diffwriter.close();
            }
            catch (Exception exception) {
                System.err.println("error while generating delta: " + (Object)((Object)exception));
            }
        }
    }
}

