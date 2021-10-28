/*
 * Decompiled with CFR 0.145.
 */
package com.sun.jna.platform.win32;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.nio.Buffer;
import java.util.Map;

public interface Kernel32
extends StdCallLibrary {
    public static final Kernel32 INSTANCE = Native.loadLibrary("kernel32", Kernel32.class, W32APIOptions.UNICODE_OPTIONS);

    public Pointer LocalFree(Pointer var1);

    public Pointer GlobalFree(Pointer var1);

    public WinDef.HMODULE GetModuleHandle(String var1);

    public void GetSystemTime(WinBase.SYSTEMTIME var1);

    public int GetTickCount();

    public int GetCurrentThreadId();

    public WinNT.HANDLE GetCurrentThread();

    public int GetCurrentProcessId();

    public WinNT.HANDLE GetCurrentProcess();

    public int GetProcessId(WinNT.HANDLE var1);

    public int GetProcessVersion(int var1);

    public boolean GetExitCodeProcess(WinNT.HANDLE var1, IntByReference var2);

    public boolean TerminateProcess(WinNT.HANDLE var1, int var2);

    public int GetLastError();

    public void SetLastError(int var1);

    public int GetDriveType(String var1);

    public int FormatMessage(int var1, Pointer var2, int var3, int var4, PointerByReference var5, int var6, Pointer var7);

    public int FormatMessage(int var1, Pointer var2, int var3, int var4, Buffer var5, int var6, Pointer var7);

    public WinNT.HANDLE CreateFile(String var1, int var2, int var3, WinBase.SECURITY_ATTRIBUTES var4, int var5, int var6, WinNT.HANDLE var7);

    public boolean CreateDirectory(String var1, WinBase.SECURITY_ATTRIBUTES var2);

    public boolean ReadFile(WinNT.HANDLE var1, Buffer var2, int var3, IntByReference var4, WinBase.OVERLAPPED var5);

    public WinNT.HANDLE CreateIoCompletionPort(WinNT.HANDLE var1, WinNT.HANDLE var2, Pointer var3, int var4);

    public boolean GetQueuedCompletionStatus(WinNT.HANDLE var1, IntByReference var2, ByReference var3, PointerByReference var4, int var5);

    public boolean PostQueuedCompletionStatus(WinNT.HANDLE var1, int var2, Pointer var3, WinBase.OVERLAPPED var4);

    public int WaitForSingleObject(WinNT.HANDLE var1, int var2);

    public int WaitForMultipleObjects(int var1, WinNT.HANDLE[] var2, boolean var3, int var4);

    public boolean DuplicateHandle(WinNT.HANDLE var1, WinNT.HANDLE var2, WinNT.HANDLE var3, WinNT.HANDLEByReference var4, int var5, boolean var6, int var7);

    public boolean CloseHandle(WinNT.HANDLE var1);

    public boolean ReadDirectoryChangesW(WinNT.HANDLE var1, WinNT.FILE_NOTIFY_INFORMATION var2, int var3, boolean var4, int var5, IntByReference var6, WinBase.OVERLAPPED var7, OVERLAPPED_COMPLETION_ROUTINE var8);

    public int GetShortPathName(String var1, char[] var2, int var3);

    public Pointer LocalAlloc(int var1, int var2);

    public boolean WriteFile(WinNT.HANDLE var1, byte[] var2, int var3, IntByReference var4, WinBase.OVERLAPPED var5);

    public WinNT.HANDLE CreateEvent(WinBase.SECURITY_ATTRIBUTES var1, boolean var2, boolean var3, String var4);

    public boolean SetEvent(WinNT.HANDLE var1);

    public boolean PulseEvent(WinNT.HANDLE var1);

    public WinNT.HANDLE CreateFileMapping(WinNT.HANDLE var1, WinBase.SECURITY_ATTRIBUTES var2, int var3, int var4, int var5, String var6);

    public Pointer MapViewOfFile(WinNT.HANDLE var1, int var2, int var3, int var4, int var5);

    public boolean UnmapViewOfFile(Pointer var1);

    public boolean GetComputerName(char[] var1, IntByReference var2);

    public WinNT.HANDLE OpenThread(int var1, boolean var2, int var3);

    public WinNT.HANDLE OpenProcess(int var1, boolean var2, int var3);

    public WinDef.DWORD GetTempPath(WinDef.DWORD var1, char[] var2);

    public WinDef.DWORD GetVersion();

    public boolean GetVersionEx(WinNT.OSVERSIONINFO var1);

    public boolean GetVersionEx(WinNT.OSVERSIONINFOEX var1);

    public void GetSystemInfo(WinBase.SYSTEM_INFO var1);

    public void GetNativeSystemInfo(WinBase.SYSTEM_INFO var1);

    public boolean IsWow64Process(WinNT.HANDLE var1, IntByReference var2);

    public boolean GlobalMemoryStatusEx(WinBase.MEMORYSTATUSEX var1);

    public WinDef.DWORD GetLogicalDriveStrings(WinDef.DWORD var1, char[] var2);

    public boolean GetDiskFreeSpaceEx(String var1, WinNT.LARGE_INTEGER.ByReference var2, WinNT.LARGE_INTEGER.ByReference var3, WinNT.LARGE_INTEGER.ByReference var4);

    public boolean DeleteFile(String var1);

    public boolean CreatePipe(WinNT.HANDLEByReference var1, WinNT.HANDLEByReference var2, WinBase.SECURITY_ATTRIBUTES var3, int var4);

    public boolean SetHandleInformation(WinNT.HANDLE var1, int var2, int var3);

    public int GetFileAttributes(String var1);

    public static interface OVERLAPPED_COMPLETION_ROUTINE
    extends StdCallLibrary.StdCallCallback {
        public void callback(int var1, int var2, WinBase.OVERLAPPED var3);
    }

}

