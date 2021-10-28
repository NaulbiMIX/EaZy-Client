/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.print.PageFormat
 *  java.awt.print.Pageable
 *  java.awt.print.Printable
 *  java.lang.Object
 */
package javax.jnlp;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

public interface PrintService {
    public PageFormat getDefaultPage();

    public PageFormat showPageFormatDialog(PageFormat var1);

    public boolean print(Pageable var1);

    public boolean print(Printable var1);
}

