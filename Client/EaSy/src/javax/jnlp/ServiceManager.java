/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package javax.jnlp;

import java.util.HashMap;
import java.util.Map;
import javax.jnlp.ServiceManagerStub;
import javax.jnlp.UnavailableServiceException;

public final class ServiceManager {
    private static ServiceManagerStub stub = null;
    private static Map lookupTable = new HashMap();

    private ServiceManager() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object lookup(String name) throws UnavailableServiceException {
        if (stub == null) {
            throw new UnavailableServiceException("service stub not set.");
        }
        Map map = lookupTable;
        synchronized (map) {
            Object result = lookupTable.get((Object)name);
            if (result == null && (result = stub.lookup(name)) != null) {
                lookupTable.put((Object)name, result);
            }
            if (result == null) {
                throw new UnavailableServiceException("service not available (stub returned null).");
            }
            return result;
        }
    }

    public static String[] getServiceNames() {
        if (stub == null) {
            return new String[0];
        }
        return stub.getServiceNames();
    }

    public static void setServiceManagerStub(ServiceManagerStub stub) {
        if (ServiceManager.stub == null) {
            ServiceManager.stub = stub;
        }
    }
}

