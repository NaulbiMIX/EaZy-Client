/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 */
package net.optifine.http;

import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;

public interface HttpListener {
    public void finished(HttpRequest var1, HttpResponse var2);

    public void failed(HttpRequest var1, Exception var2);
}

