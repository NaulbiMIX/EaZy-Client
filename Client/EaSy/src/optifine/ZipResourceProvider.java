/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipFile
 */
package optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import optifine.IResourceProvider;
import optifine.Utils;

public class ZipResourceProvider
implements IResourceProvider {
    private ZipFile zipFile = null;

    public ZipResourceProvider(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    @Override
    public InputStream getResourceStream(String path) throws IOException {
        ZipEntry zipentry = this.zipFile.getEntry(path = Utils.removePrefix(path, "/"));
        if (zipentry == null) {
            return null;
        }
        InputStream inputstream = this.zipFile.getInputStream(zipentry);
        return inputstream;
    }
}

