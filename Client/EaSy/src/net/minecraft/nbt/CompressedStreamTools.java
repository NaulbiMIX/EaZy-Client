/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedInputStream
 *  java.io.BufferedOutputStream
 *  java.io.DataInput
 *  java.io.DataInputStream
 *  java.io.DataOutput
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.zip.GZIPInputStream
 *  java.util.zip.GZIPOutputStream
 */
package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.util.ReportedException;

public class CompressedStreamTools {
    public static NBTTagCompound readCompressed(InputStream is) throws IOException {
        NBTTagCompound nbttagcompound;
        DataInputStream datainputstream = new DataInputStream((InputStream)new BufferedInputStream((InputStream)new GZIPInputStream(is)));
        try {
            nbttagcompound = CompressedStreamTools.read((DataInput)datainputstream, NBTSizeTracker.INFINITE);
        }
        finally {
            datainputstream.close();
        }
        return nbttagcompound;
    }

    public static void writeCompressed(NBTTagCompound p_74799_0_, OutputStream outputStream) throws IOException {
        DataOutputStream dataoutputstream = new DataOutputStream((OutputStream)new BufferedOutputStream((OutputStream)new GZIPOutputStream(outputStream)));
        try {
            CompressedStreamTools.write(p_74799_0_, (DataOutput)dataoutputstream);
        }
        finally {
            dataoutputstream.close();
        }
    }

    public static void safeWrite(NBTTagCompound p_74793_0_, File p_74793_1_) throws IOException {
        File file1 = new File(p_74793_1_.getAbsolutePath() + "_tmp");
        if (file1.exists()) {
            file1.delete();
        }
        CompressedStreamTools.write(p_74793_0_, file1);
        if (p_74793_1_.exists()) {
            p_74793_1_.delete();
        }
        if (p_74793_1_.exists()) {
            throw new IOException("Failed to delete " + (Object)p_74793_1_);
        }
        file1.renameTo(p_74793_1_);
    }

    public static void write(NBTTagCompound p_74795_0_, File p_74795_1_) throws IOException {
        DataOutputStream dataoutputstream = new DataOutputStream((OutputStream)new FileOutputStream(p_74795_1_));
        try {
            CompressedStreamTools.write(p_74795_0_, (DataOutput)dataoutputstream);
        }
        finally {
            dataoutputstream.close();
        }
    }

    public static NBTTagCompound read(File p_74797_0_) throws IOException {
        NBTTagCompound nbttagcompound;
        if (!p_74797_0_.exists()) {
            return null;
        }
        DataInputStream datainputstream = new DataInputStream((InputStream)new FileInputStream(p_74797_0_));
        try {
            nbttagcompound = CompressedStreamTools.read((DataInput)datainputstream, NBTSizeTracker.INFINITE);
        }
        finally {
            datainputstream.close();
        }
        return nbttagcompound;
    }

    public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
        return CompressedStreamTools.read((DataInput)inputStream, NBTSizeTracker.INFINITE);
    }

    public static NBTTagCompound read(DataInput p_152456_0_, NBTSizeTracker p_152456_1_) throws IOException {
        NBTBase nbtbase = CompressedStreamTools.func_152455_a(p_152456_0_, 0, p_152456_1_);
        if (nbtbase instanceof NBTTagCompound) {
            return (NBTTagCompound)nbtbase;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void write(NBTTagCompound p_74800_0_, DataOutput p_74800_1_) throws IOException {
        CompressedStreamTools.writeTag(p_74800_0_, p_74800_1_);
    }

    private static void writeTag(NBTBase p_150663_0_, DataOutput p_150663_1_) throws IOException {
        p_150663_1_.writeByte((int)p_150663_0_.getId());
        if (p_150663_0_.getId() != 0) {
            p_150663_1_.writeUTF("");
            p_150663_0_.write(p_150663_1_);
        }
    }

    private static NBTBase func_152455_a(DataInput p_152455_0_, int p_152455_1_, NBTSizeTracker p_152455_2_) throws IOException {
        byte b0 = p_152455_0_.readByte();
        if (b0 == 0) {
            return new NBTTagEnd();
        }
        p_152455_0_.readUTF();
        NBTBase nbtbase = NBTBase.createNewByType(b0);
        try {
            nbtbase.read(p_152455_0_, p_152455_1_, p_152455_2_);
            return nbtbase;
        }
        catch (IOException ioexception) {
            CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
            crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
            crashreportcategory.addCrashSection("Tag type", b0);
            throw new ReportedException(crashreport);
        }
    }
}

