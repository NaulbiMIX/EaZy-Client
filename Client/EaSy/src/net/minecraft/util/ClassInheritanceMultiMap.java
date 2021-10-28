/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.AbstractSet
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 *  java.util.concurrent.ConcurrentHashMap
 */
package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.optifine.util.IteratorCache;

public class ClassInheritanceMultiMap<T>
extends AbstractSet<T> {
    private static final Set<Class<?>> field_181158_a = Collections.newSetFromMap((Map)new ConcurrentHashMap());
    private final Map<Class<?>, List<T>> map = Maps.newHashMap();
    private final Set<Class<?>> knownKeys = Sets.newIdentityHashSet();
    private final Class<T> baseClass;
    private final List<T> values = Lists.newArrayList();
    public boolean empty;

    public ClassInheritanceMultiMap(Class<T> baseClassIn) {
        this.baseClass = baseClassIn;
        this.knownKeys.add(baseClassIn);
        this.map.put(baseClassIn, this.values);
        for (Class oclass : field_181158_a) {
            this.createLookup(oclass);
        }
        this.empty = this.values.size() == 0;
    }

    protected void createLookup(Class<?> clazz) {
        field_181158_a.add(clazz);
        int i = this.values.size();
        for (int j = 0; j < i; ++j) {
            Object t = this.values.get(j);
            if (!clazz.isAssignableFrom(t.getClass())) continue;
            this.addForClass(t, clazz);
        }
        this.knownKeys.add(clazz);
    }

    protected Class<?> initializeClassLookup(Class<?> clazz) {
        if (this.baseClass.isAssignableFrom(clazz)) {
            if (!this.knownKeys.contains(clazz)) {
                this.createLookup(clazz);
            }
            return clazz;
        }
        throw new IllegalArgumentException("Don't know how to search for " + clazz);
    }

    public boolean add(T p_add_1_) {
        for (Class oclass : this.knownKeys) {
            if (!oclass.isAssignableFrom(p_add_1_.getClass())) continue;
            this.addForClass(p_add_1_, oclass);
        }
        this.empty = this.values.size() == 0;
        return true;
    }

    private void addForClass(T value, Class<?> parentClass) {
        List list = (List)this.map.get(parentClass);
        if (list == null) {
            this.map.put(parentClass, (Object)Lists.newArrayList((Object[])new Object[]{value}));
        } else {
            list.add(value);
        }
        this.empty = this.values.size() == 0;
    }

    public boolean remove(Object p_remove_1_) {
        Object t = p_remove_1_;
        boolean flag = false;
        for (Class oclass : this.knownKeys) {
            List list;
            if (!oclass.isAssignableFrom(t.getClass()) || (list = (List)this.map.get((Object)oclass)) == null || !list.remove(t)) continue;
            flag = true;
        }
        this.empty = this.values.size() == 0;
        return flag;
    }

    public boolean contains(Object p_contains_1_) {
        return Iterators.contains((Iterator)this.getByClass(p_contains_1_.getClass()).iterator(), (Object)p_contains_1_);
    }

    public <S> Iterable<S> getByClass(final Class<S> clazz) {
        return new Iterable<S>(){

            public Iterator<S> iterator() {
                List list = (List)ClassInheritanceMultiMap.this.map.get(ClassInheritanceMultiMap.this.initializeClassLookup(clazz));
                if (list == null) {
                    return Iterators.emptyIterator();
                }
                Iterator iterator = list.iterator();
                return Iterators.filter((Iterator)iterator, (Class)clazz);
            }
        };
    }

    public Iterator<T> iterator() {
        return this.values.isEmpty() ? Iterators.emptyIterator() : IteratorCache.getReadOnly(this.values);
    }

    public int size() {
        return this.values.size();
    }

    public boolean isEmpty() {
        return this.empty;
    }

}

