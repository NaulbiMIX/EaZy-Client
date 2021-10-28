/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Enumeration
 *  java.util.LinkedHashSet
 *  java.util.Properties
 *  java.util.Set
 */
package net.optifine.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesOrdered
extends Properties {
    private Set<Object> keysOrdered = new LinkedHashSet();

    public synchronized Object put(Object key, Object value) {
        this.keysOrdered.add(key);
        return super.put(key, value);
    }

    public Set<Object> keySet() {
        Set set = super.keySet();
        this.keysOrdered.retainAll((Collection)set);
        return Collections.unmodifiableSet(this.keysOrdered);
    }

    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(this.keySet());
    }
}

