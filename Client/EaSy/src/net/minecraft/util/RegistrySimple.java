/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Set
 *  org.apache.commons.lang3.Validate
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.IRegistry;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple<K, V>
implements IRegistry<K, V> {
    private static final Logger logger = LogManager.getLogger();
    protected final Map<K, V> registryObjects = this.createUnderlyingMap();

    protected Map<K, V> createUnderlyingMap() {
        return Maps.newHashMap();
    }

    @Override
    public V getObject(K name) {
        return (V)this.registryObjects.get(name);
    }

    @Override
    public void putObject(K key, V value) {
        Validate.notNull(key);
        Validate.notNull(value);
        if (this.registryObjects.containsKey(key)) {
            logger.debug("Adding duplicate key '" + key + "' to registry");
        }
        this.registryObjects.put(key, value);
    }

    public Set<K> getKeys() {
        return Collections.unmodifiableSet((Set)this.registryObjects.keySet());
    }

    public boolean containsKey(K key) {
        return this.registryObjects.containsKey(key);
    }

    public Iterator<V> iterator() {
        return this.registryObjects.values().iterator();
    }
}

