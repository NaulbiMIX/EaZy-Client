/*
 * Decompiled with CFR 0.145.
 */
package com.sun.jna.platform.mac;

import com.sun.jna.platform.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MacFileUtils
extends FileUtils {
    public boolean hasTrash() {
        return true;
    }

    public void moveToTrash(File[] files) throws IOException {
        File home = new File(System.getProperty("user.home"));
        File trash = new File(home, ".Trash");
        if (!trash.exists()) {
            throw new IOException("The Trash was not found in its expected location (" + trash + ")");
        }
        ArrayList<File> failed = new ArrayList<File>();
        for (int i = 0; i < files.length; ++i) {
            File src = files[i];
            File target = new File(trash, src.getName());
            if (src.renameTo(target)) continue;
            failed.add(src);
        }
        if (failed.size() > 0) {
            throw new IOException("The following files could not be trashed: " + failed);
        }
    }
}

