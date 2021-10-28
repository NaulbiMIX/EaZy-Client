/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.datatransfer.Transferable
 *  java.lang.Object
 */
package javax.jnlp;

import java.awt.datatransfer.Transferable;

public interface ClipboardService {
    public Transferable getContents();

    public void setContents(Transferable var1);
}

