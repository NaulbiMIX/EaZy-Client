/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Closeable
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.ClassNotFoundException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Package
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.ThreadLocal
 *  java.lang.Throwable
 *  java.net.JarURLConnection
 *  java.net.URL
 *  java.net.URLClassLoader
 *  java.net.URLConnection
 *  java.security.CodeSigner
 *  java.security.CodeSource
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 *  java.util.concurrent.ConcurrentHashMap
 *  java.util.jar.Attributes
 *  java.util.jar.Attributes$Name
 *  java.util.jar.JarEntry
 *  java.util.jar.JarFile
 *  java.util.jar.Manifest
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 */
package net.minecraft.launchwrapper;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class LaunchClassLoader
extends URLClassLoader {
    public static final int BUFFER_SIZE = 4096;
    private List<URL> sources;
    private ClassLoader parent = this.getClass().getClassLoader();
    private List<IClassTransformer> transformers = new ArrayList(2);
    private Map<String, Class<?>> cachedClasses = new ConcurrentHashMap();
    private Set<String> invalidClasses = new HashSet(1000);
    private Set<String> classLoaderExceptions = new HashSet();
    private Set<String> transformerExceptions = new HashSet();
    private Map<Package, Manifest> packageManifests = new ConcurrentHashMap();
    private Map<String, byte[]> resourceCache = new ConcurrentHashMap(1000);
    private Set<String> negativeResourceCache = Collections.newSetFromMap((Map)new ConcurrentHashMap());
    private IClassNameTransformer renameTransformer;
    private static final Manifest EMPTY = new Manifest();
    private final ThreadLocal<byte[]> loadBuffer = new ThreadLocal();
    private static final String[] RESERVED_NAMES = new String[]{"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
    private static final boolean DEBUG = Boolean.parseBoolean((String)System.getProperty((String)"legacy.debugClassLoading", (String)"false"));
    private static final boolean DEBUG_FINER = DEBUG && Boolean.parseBoolean((String)System.getProperty((String)"legacy.debugClassLoadingFiner", (String)"false"));
    private static final boolean DEBUG_SAVE = DEBUG && Boolean.parseBoolean((String)System.getProperty((String)"legacy.debugClassLoadingSave", (String)"false"));
    private static File tempFolder = null;

    public LaunchClassLoader(URL[] sources) {
        super(sources, null);
        this.sources = new ArrayList((Collection)Arrays.asList((Object[])sources));
        this.addClassLoaderExclusion("java.");
        this.addClassLoaderExclusion("sun.");
        this.addClassLoaderExclusion("org.lwjgl.");
        this.addClassLoaderExclusion("org.apache.logging.");
        this.addClassLoaderExclusion("net.minecraft.launchwrapper.");
        this.addTransformerExclusion("javax.");
        this.addTransformerExclusion("argo.");
        this.addTransformerExclusion("org.objectweb.asm.");
        this.addTransformerExclusion("com.google.common.");
        this.addTransformerExclusion("org.bouncycastle.");
        this.addTransformerExclusion("net.minecraft.launchwrapper.injector.");
        if (DEBUG_SAVE) {
            int x = 1;
            tempFolder = new File(Launch.minecraftHome, "CLASSLOADER_TEMP");
            while (tempFolder.exists() && x <= 10) {
                tempFolder = new File(Launch.minecraftHome, "CLASSLOADER_TEMP" + x++);
            }
            if (tempFolder.exists()) {
                LogWrapper.info("DEBUG_SAVE enabled, but 10 temp directories already exist, clean them and try again.", new Object[0]);
                tempFolder = null;
            } else {
                LogWrapper.info("DEBUG_SAVE Enabled, saving all classes to \"%s\"", tempFolder.getAbsolutePath().replace('\\', '/'));
                tempFolder.mkdirs();
            }
        }
    }

    public void registerTransformer(String transformerClassName) {
        try {
            IClassTransformer transformer = (IClassTransformer)this.loadClass(transformerClassName).newInstance();
            this.transformers.add((Object)transformer);
            if (transformer instanceof IClassNameTransformer && this.renameTransformer == null) {
                this.renameTransformer = (IClassNameTransformer)((Object)transformer);
            }
        }
        catch (Exception e) {
            LogWrapper.log(Level.ERROR, e, "A critical problem occurred registering the ASM transformer class %s", transformerClassName);
        }
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        if (this.invalidClasses.contains((Object)name)) {
            throw new ClassNotFoundException(name);
        }
        for (String exception : this.classLoaderExceptions) {
            if (!name.startsWith(exception)) continue;
            return this.parent.loadClass(name);
        }
        if (this.cachedClasses.containsKey((Object)name)) {
            return (Class)this.cachedClasses.get((Object)name);
        }
        for (String exception : this.transformerExceptions) {
            if (!name.startsWith(exception)) continue;
            try {
                Class clazz = super.findClass(name);
                this.cachedClasses.put((Object)name, (Object)clazz);
                return clazz;
            }
            catch (ClassNotFoundException e) {
                this.invalidClasses.add((Object)name);
                throw e;
            }
        }
        try {
            String transformedName = this.transformName(name);
            if (this.cachedClasses.containsKey((Object)transformedName)) {
                return (Class)this.cachedClasses.get((Object)transformedName);
            }
            String untransformedName = this.untransformName(name);
            int lastDot = untransformedName.lastIndexOf(46);
            String packageName = lastDot == -1 ? "" : untransformedName.substring(0, lastDot);
            String fileName = untransformedName.replace('.', '/').concat(".class");
            URLConnection urlConnection = this.findCodeSourceConnectionFor(fileName);
            CodeSigner[] signers = null;
            if (lastDot > -1 && !untransformedName.startsWith("net.minecraft.")) {
                if (urlConnection instanceof JarURLConnection) {
                    JarURLConnection jarURLConnection = (JarURLConnection)urlConnection;
                    JarFile jarFile = jarURLConnection.getJarFile();
                    if (jarFile != null && jarFile.getManifest() != null) {
                        Manifest manifest = jarFile.getManifest();
                        JarEntry entry = jarFile.getJarEntry(fileName);
                        Package pkg = this.getPackage(packageName);
                        this.getClassBytes(untransformedName);
                        signers = entry.getCodeSigners();
                        if (pkg == null) {
                            pkg = this.definePackage(packageName, manifest, jarURLConnection.getJarFileURL());
                            this.packageManifests.put((Object)pkg, (Object)manifest);
                        } else if (pkg.isSealed() && !pkg.isSealed(jarURLConnection.getJarFileURL())) {
                            LogWrapper.severe("The jar file %s is trying to seal already secured path %s", jarFile.getName(), packageName);
                        } else if (this.isSealed(packageName, manifest)) {
                            LogWrapper.severe("The jar file %s has a security seal for path %s, but that path is defined and not secure", jarFile.getName(), packageName);
                        }
                    }
                } else {
                    Package pkg = this.getPackage(packageName);
                    if (pkg == null) {
                        pkg = this.definePackage(packageName, null, null, null, null, null, null, null);
                        this.packageManifests.put((Object)pkg, (Object)EMPTY);
                    } else if (pkg.isSealed()) {
                        LogWrapper.severe("The URL %s is defining elements for sealed path %s", new Object[]{urlConnection.getURL(), packageName});
                    }
                }
            }
            byte[] transformedClass = this.runTransformers(untransformedName, transformedName, this.getClassBytes(untransformedName));
            if (DEBUG_SAVE) {
                this.saveTransformedClass(transformedClass, transformedName);
            }
            CodeSource codeSource = urlConnection == null ? null : new CodeSource(urlConnection.getURL(), signers);
            Class clazz = this.defineClass(transformedName, transformedClass, 0, transformedClass.length, codeSource);
            this.cachedClasses.put((Object)transformedName, (Object)clazz);
            return clazz;
        }
        catch (Throwable e) {
            this.invalidClasses.add((Object)name);
            if (DEBUG) {
                LogWrapper.log(Level.TRACE, e, "Exception encountered attempting classloading of %s", name);
                LogManager.getLogger((String)"LaunchWrapper").log(Level.ERROR, "Exception encountered attempting classloading of %s", e);
            }
            throw new ClassNotFoundException(name, e);
        }
    }

    private void saveTransformedClass(byte[] data, String transformedName) {
        if (tempFolder == null) {
            return;
        }
        File outFile = new File(tempFolder, transformedName.replace('.', File.separatorChar) + ".class");
        File outDir = outFile.getParentFile();
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        if (outFile.exists()) {
            outFile.delete();
        }
        try {
            LogWrapper.fine("Saving transformed class \"%s\" to \"%s\"", transformedName, outFile.getAbsolutePath().replace('\\', '/'));
            FileOutputStream output = new FileOutputStream(outFile);
            output.write(data);
            output.close();
        }
        catch (IOException ex) {
            LogWrapper.log(Level.WARN, ex, "Could not save transformed class \"%s\"", transformedName);
        }
    }

    private String untransformName(String name) {
        if (this.renameTransformer != null) {
            return this.renameTransformer.unmapClassName(name);
        }
        return name;
    }

    private String transformName(String name) {
        if (this.renameTransformer != null) {
            return this.renameTransformer.remapClassName(name);
        }
        return name;
    }

    private boolean isSealed(String path, Manifest manifest) {
        Attributes attributes = manifest.getAttributes(path);
        String sealed = null;
        if (attributes != null) {
            sealed = attributes.getValue(Attributes.Name.SEALED);
        }
        if (sealed == null && (attributes = manifest.getMainAttributes()) != null) {
            sealed = attributes.getValue(Attributes.Name.SEALED);
        }
        return "true".equalsIgnoreCase(sealed);
    }

    private URLConnection findCodeSourceConnectionFor(String name) {
        URL resource = this.findResource(name);
        if (resource != null) {
            try {
                return resource.openConnection();
            }
            catch (IOException e) {
                throw new RuntimeException((Throwable)e);
            }
        }
        return null;
    }

    private byte[] runTransformers(String name, String transformedName, byte[] basicClass) {
        if (DEBUG_FINER) {
            LogWrapper.finest("Beginning transform of {%s (%s)} Start Length: %d", name, transformedName, basicClass == null ? 0 : basicClass.length);
            for (IClassTransformer transformer : this.transformers) {
                String transName = transformer.getClass().getName();
                LogWrapper.finest("Before Transformer {%s (%s)} %s: %d", name, transformedName, transName, basicClass == null ? 0 : basicClass.length);
                basicClass = transformer.transform(name, transformedName, basicClass);
                LogWrapper.finest("After  Transformer {%s (%s)} %s: %d", name, transformedName, transName, basicClass == null ? 0 : basicClass.length);
            }
            LogWrapper.finest("Ending transform of {%s (%s)} Start Length: %d", name, transformedName, basicClass == null ? 0 : basicClass.length);
        } else {
            for (IClassTransformer transformer : this.transformers) {
                basicClass = transformer.transform(name, transformedName, basicClass);
            }
        }
        return basicClass;
    }

    public void addURL(URL url) {
        super.addURL(url);
        this.sources.add((Object)url);
    }

    public List<URL> getSources() {
        return this.sources;
    }

    private byte[] readFully(InputStream stream) {
        try {
            int read;
            byte[] buffer = this.getOrCreateBuffer();
            int totalLength = 0;
            while ((read = stream.read(buffer, totalLength, buffer.length - totalLength)) != -1) {
                if ((totalLength += read) < buffer.length - 1) continue;
                byte[] newBuffer = new byte[buffer.length + 4096];
                System.arraycopy((Object)buffer, (int)0, (Object)newBuffer, (int)0, (int)buffer.length);
                buffer = newBuffer;
            }
            byte[] result = new byte[totalLength];
            System.arraycopy((Object)buffer, (int)0, (Object)result, (int)0, (int)totalLength);
            return result;
        }
        catch (Throwable t) {
            LogWrapper.log(Level.WARN, t, "Problem loading class", new Object[0]);
            return new byte[0];
        }
    }

    private byte[] getOrCreateBuffer() {
        byte[] buffer = (byte[])this.loadBuffer.get();
        if (buffer == null) {
            this.loadBuffer.set((Object)new byte[4096]);
            buffer = (byte[])this.loadBuffer.get();
        }
        return buffer;
    }

    public List<IClassTransformer> getTransformers() {
        return Collections.unmodifiableList(this.transformers);
    }

    public void addClassLoaderExclusion(String toExclude) {
        this.classLoaderExceptions.add((Object)toExclude);
    }

    public void addTransformerExclusion(String toExclude) {
        this.transformerExceptions.add((Object)toExclude);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] getClassBytes(String name) throws IOException {
        InputStream classStream;
        URL classResource;
        byte[] data;
        block9 : {
            String reservedName2;
            if (this.negativeResourceCache.contains((Object)name)) {
                return null;
            }
            if (this.resourceCache.containsKey((Object)name)) {
                return (byte[])this.resourceCache.get((Object)name);
            }
            if (name.indexOf(46) == -1) {
                for (String reservedName2 : RESERVED_NAMES) {
                    if (!name.toUpperCase(Locale.ENGLISH).startsWith(reservedName2) || (data = this.getClassBytes("_" + name)) == null) continue;
                    this.resourceCache.put((Object)name, (Object)data);
                    return data;
                }
            }
            classStream = null;
            try {
                String resourcePath = name.replace('.', '/').concat(".class");
                classResource = this.findResource(resourcePath);
                if (classResource != null) break block9;
                if (DEBUG) {
                    LogWrapper.finest("Failed to find class resource %s", resourcePath);
                }
                this.negativeResourceCache.add((Object)name);
                reservedName2 = null;
            }
            catch (Throwable throwable) {
                LaunchClassLoader.closeSilently(classStream);
                throw throwable;
            }
            LaunchClassLoader.closeSilently((Closeable)classStream);
            return reservedName2;
        }
        classStream = classResource.openStream();
        if (DEBUG) {
            LogWrapper.finest("Loading class %s from resource %s", name, classResource.toString());
        }
        byte[] data2 = this.readFully(classStream);
        this.resourceCache.put((Object)name, (Object)data2);
        data = data2;
        LaunchClassLoader.closeSilently((Closeable)classStream);
        return data;
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public void clearNegativeEntries(Set<String> entriesToClear) {
        this.negativeResourceCache.removeAll(entriesToClear);
    }
}

