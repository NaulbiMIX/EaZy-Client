/*
 * Decompiled with CFR 0.145.
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.util.Map;

public interface Winspool
extends StdCallLibrary {
    public static final Winspool INSTANCE = Native.loadLibrary("Winspool.drv", Winspool.class, W32APIOptions.UNICODE_OPTIONS);
    public static final int PRINTER_ENUM_DEFAULT = 1;
    public static final int PRINTER_ENUM_LOCAL = 2;
    public static final int PRINTER_ENUM_CONNECTIONS = 4;
    public static final int PRINTER_ENUM_FAVORITE = 4;
    public static final int PRINTER_ENUM_NAME = 8;
    public static final int PRINTER_ENUM_REMOTE = 16;
    public static final int PRINTER_ENUM_SHARED = 32;
    public static final int PRINTER_ENUM_NETWORK = 64;
    public static final int PRINTER_ENUM_EXPAND = 16384;
    public static final int PRINTER_ENUM_CONTAINER = 32768;
    public static final int PRINTER_ENUM_ICONMASK = 16711680;
    public static final int PRINTER_ENUM_ICON1 = 65536;
    public static final int PRINTER_ENUM_ICON2 = 131072;
    public static final int PRINTER_ENUM_ICON3 = 262144;
    public static final int PRINTER_ENUM_ICON4 = 524288;
    public static final int PRINTER_ENUM_ICON5 = 1048576;
    public static final int PRINTER_ENUM_ICON6 = 2097152;
    public static final int PRINTER_ENUM_ICON7 = 4194304;
    public static final int PRINTER_ENUM_ICON8 = 8388608;
    public static final int PRINTER_ENUM_HIDE = 16777216;

    public boolean EnumPrinters(int var1, WString var2, int var3, Pointer var4, int var5, IntByReference var6, IntByReference var7);

    public static class PRINTER_INFO_4
    extends Structure {
        public String pPrinterName;
        public String pServerName;
        public WinDef.DWORD Attributes;

        public PRINTER_INFO_4() {
        }

        public PRINTER_INFO_4(int size) {
            super(new Memory(size));
        }
    }

    public static class PRINTER_INFO_1
    extends Structure {
        public int Flags;
        public String pDescription;
        public String pName;
        public String pComment;

        public PRINTER_INFO_1() {
        }

        public PRINTER_INFO_1(int size) {
            super(new Memory(size));
        }
    }

}

