/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package javax.jnlp;

import javax.jnlp.UnavailableServiceException;

public interface ServiceManagerStub {
    public Object lookup(String var1) throws UnavailableServiceException;

    public String[] getServiceNames();
}

