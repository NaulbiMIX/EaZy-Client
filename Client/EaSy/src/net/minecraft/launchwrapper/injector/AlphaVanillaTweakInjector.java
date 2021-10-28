/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.applet.Applet
 *  java.applet.AppletStub
 *  java.awt.BorderLayout
 *  java.awt.Color
 *  java.awt.Component
 *  java.awt.Dimension
 *  java.awt.Frame
 *  java.awt.LayoutManager
 *  java.awt.event.WindowAdapter
 *  java.awt.event.WindowEvent
 *  java.awt.event.WindowListener
 *  java.io.File
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.ClassNotFoundException
 *  java.lang.IllegalAccessException
 *  java.lang.InstantiationException
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.Runtime
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Field
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Modifier
 *  java.net.MalformedURLException
 *  java.net.URL
 *  java.util.HashMap
 *  java.util.Map
 *  javax.swing.JPanel
 */
package net.minecraft.launchwrapper.injector;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.launchwrapper.injector.VanillaTweakInjector;

public class AlphaVanillaTweakInjector
implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        return bytes;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz;
        try {
            clazz = AlphaVanillaTweakInjector.getaClass("net.minecraft.client.MinecraftApplet");
        }
        catch (ClassNotFoundException ignored) {
            clazz = AlphaVanillaTweakInjector.getaClass("com.mojang.minecraft.MinecraftApplet");
        }
        System.out.println("AlphaVanillaTweakInjector.class.getClassLoader() = " + (Object)AlphaVanillaTweakInjector.class.getClassLoader());
        Constructor constructor = clazz.getConstructor(new Class[0]);
        Object object = constructor.newInstance(new Object[0]);
        for (Field field : clazz.getDeclaredFields()) {
            String name = field.getType().getName();
            if (name.contains((CharSequence)"awt") || name.contains((CharSequence)"java") || name.equals((Object)"long")) continue;
            System.out.println("Found likely Minecraft candidate: " + (Object)field);
            Field fileField = AlphaVanillaTweakInjector.getWorkingDirField(name);
            if (fileField == null) continue;
            System.out.println("Found File, changing to " + (Object)Launch.minecraftHome);
            fileField.setAccessible(true);
            fileField.set(null, (Object)Launch.minecraftHome);
            break;
        }
        AlphaVanillaTweakInjector.startMinecraft((Applet)object, args);
    }

    private static void startMinecraft(final Applet applet, String[] args) {
        HashMap params = new HashMap();
        String name = "Player" + System.currentTimeMillis() % 1000L;
        if (args.length > 0) {
            name = args[0];
        }
        String sessionId = "-";
        if (args.length > 1) {
            sessionId = args[1];
        }
        params.put((Object)"username", (Object)name);
        params.put((Object)"sessionid", (Object)sessionId);
        Frame launcherFrameFake = new Frame();
        launcherFrameFake.setTitle("Minecraft");
        launcherFrameFake.setBackground(Color.BLACK);
        JPanel panel = new JPanel();
        launcherFrameFake.setLayout((LayoutManager)new BorderLayout());
        panel.setPreferredSize(new Dimension(854, 480));
        launcherFrameFake.add((Component)panel, (Object)"Center");
        launcherFrameFake.pack();
        launcherFrameFake.setLocationRelativeTo(null);
        launcherFrameFake.setVisible(true);
        launcherFrameFake.addWindowListener((WindowListener)new WindowAdapter(){

            public void windowClosing(WindowEvent e) {
                System.exit((int)1);
            }
        });
        class LauncherFake
        extends Applet
        implements AppletStub {
            private static final long serialVersionUID = 1L;

            LauncherFake() {
            }

            public void appletResize(int width, int height) {
            }

            public boolean isActive() {
                return true;
            }

            public URL getDocumentBase() {
                try {
                    return new URL("http://www.minecraft.net/game/");
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public URL getCodeBase() {
                try {
                    return new URL("http://www.minecraft.net/game/");
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public String getParameter(String paramName) {
                if (Map.this.containsKey((Object)paramName)) {
                    return (String)Map.this.get((Object)paramName);
                }
                System.err.println("Client asked for parameter: " + paramName);
                return null;
            }
        }
        LauncherFake fakeLauncher = (Map)params.new LauncherFake();
        applet.setStub((AppletStub)fakeLauncher);
        fakeLauncher.setLayout((LayoutManager)new BorderLayout());
        fakeLauncher.add((Component)applet, (Object)"Center");
        fakeLauncher.validate();
        launcherFrameFake.removeAll();
        launcherFrameFake.setLayout((LayoutManager)new BorderLayout());
        launcherFrameFake.add((Component)fakeLauncher, (Object)"Center");
        launcherFrameFake.validate();
        applet.init();
        applet.start();
        Runtime.getRuntime().addShutdownHook(new Thread(){

            public void run() {
                applet.stop();
            }
        });
        VanillaTweakInjector.loadIconsOnFrames();
    }

    private static Class<?> getaClass(String name) throws ClassNotFoundException {
        return Launch.classLoader.findClass(name);
    }

    private static Field getWorkingDirField(String name) throws ClassNotFoundException {
        Class<?> clazz = AlphaVanillaTweakInjector.getaClass(name);
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic((int)field.getModifiers()) || !field.getType().getName().equals((Object)"java.io.File")) continue;
            return field;
        }
        return null;
    }

}

