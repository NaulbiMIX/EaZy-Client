/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.File
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.PrintStream
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.net.URI
 *  java.net.URL
 *  java.security.CodeSource
 *  java.security.ProtectionDomain
 *  java.util.Map
 *  java.util.regex.Pattern
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipFile
 */
package optifine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.launchwrapper.IClassTransformer;
import optifine.HashUtils;
import optifine.IOptiFineResourceLocator;
import optifine.IResourceProvider;
import optifine.OptiFineResourceLocator;
import optifine.Patcher;
import optifine.Utils;

public class OptiFineClassTransformer
implements IClassTransformer,
IResourceProvider,
IOptiFineResourceLocator {
    private ZipFile ofZipFile = null;
    private Map<String, String> patchMap = null;
    private Pattern[] patterns = null;
    public static OptiFineClassTransformer instance = null;

    public OptiFineClassTransformer() {
        instance = this;
        try {
            OptiFineClassTransformer.dbg("OptiFine ClassTransformer");
            URL url = OptiFineClassTransformer.class.getProtectionDomain().getCodeSource().getLocation();
            URI uri = url.toURI();
            File file1 = new File(uri);
            this.ofZipFile = new ZipFile(file1);
            OptiFineClassTransformer.dbg("OptiFine ZIP file: " + (Object)file1);
            this.patchMap = Patcher.getConfigurationMap(this.ofZipFile);
            this.patterns = Patcher.getConfigurationPatterns(this.patchMap);
            OptiFineResourceLocator.setResourceLocator(this);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (this.ofZipFile == null) {
            OptiFineClassTransformer.dbg("*** Can not find the OptiFine JAR in the classpath ***");
            OptiFineClassTransformer.dbg("*** OptiFine will not be loaded! ***");
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        String s = name + ".class";
        byte[] abyte = this.getOptiFineResource(s);
        return abyte != null ? abyte : bytes;
    }

    @Override
    public synchronized InputStream getOptiFineResourceStream(String name) {
        byte[] abyte = this.getOptiFineResource(name = Utils.removePrefix(name, "/"));
        if (abyte == null) {
            return null;
        }
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
        return bytearrayinputstream;
    }

    @Override
    public InputStream getResourceStream(String path) {
        path = Utils.ensurePrefix(path, "/");
        return this.getClass().getResourceAsStream(path);
    }

    public synchronized byte[] getOptiFineResource(String name) {
        byte[] abyte = this.getOptiFineResourceZip(name = Utils.removePrefix(name, "/"));
        if (abyte != null) {
            return abyte;
        }
        abyte = this.getOptiFineResourcePatched(name, this);
        return abyte != null ? abyte : null;
    }

    public synchronized byte[] getOptiFineResourceZip(String name) {
        if (this.ofZipFile == null) {
            return null;
        }
        ZipEntry zipentry = this.ofZipFile.getEntry(name = Utils.removePrefix(name, "/"));
        if (zipentry == null) {
            return null;
        }
        try {
            InputStream inputstream = this.ofZipFile.getInputStream(zipentry);
            byte[] abyte = OptiFineClassTransformer.readAll(inputstream);
            inputstream.close();
            if ((long)abyte.length != zipentry.getSize()) {
                OptiFineClassTransformer.dbg("Invalid size, name: " + name + ", zip: " + zipentry.getSize() + ", stream: " + abyte.length);
                return null;
            }
            return abyte;
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            return null;
        }
    }

    public synchronized byte[] getOptiFineResourcePatched(String name, IResourceProvider resourceProvider) {
        if (this.patterns != null && this.patchMap != null && resourceProvider != null) {
            name = Utils.removePrefix(name, "/");
            String s = "patch/" + name + ".xdelta";
            byte[] abyte = this.getOptiFineResourceZip(s);
            if (abyte == null) {
                return null;
            }
            try {
                String s2;
                String s3;
                byte[] abyte3;
                byte[] abyte1 = Patcher.applyPatch(name, abyte, this.patterns, this.patchMap, resourceProvider);
                String s1 = "patch/" + name + ".md5";
                byte[] abyte2 = this.getOptiFineResourceZip(s1);
                if (abyte2 != null && !(s2 = new String(abyte2, "ASCII")).equals((Object)(s3 = HashUtils.toHexString(abyte3 = HashUtils.getHashMd5(abyte1))))) {
                    throw new IOException("MD5 not matching, name: " + name + ", saved: " + s2 + ", patched: " + s3);
                }
                return abyte1;
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte[] abyte = new byte[1024];
        do {
            int i;
            if ((i = is.read(abyte)) < 0) {
                is.close();
                byte[] abyte1 = bytearrayoutputstream.toByteArray();
                return abyte1;
            }
            bytearrayoutputstream.write(abyte, 0, i);
        } while (true);
    }

    private static void dbg(String str) {
        System.out.println(str);
    }
}

