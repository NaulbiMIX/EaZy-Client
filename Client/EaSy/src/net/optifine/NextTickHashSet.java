/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  java.lang.IllegalStateException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.Set
 *  java.util.TreeSet
 */
package net.optifine;

import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.NextTickListEntry;

public class NextTickHashSet
extends TreeSet {
    private LongHashMap longHashMap = new LongHashMap();
    private int minX = Integer.MIN_VALUE;
    private int minZ = Integer.MIN_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxZ = Integer.MIN_VALUE;
    private static final int UNDEFINED = Integer.MIN_VALUE;

    public NextTickHashSet(Set oldSet) {
        for (Object object : oldSet) {
            this.add(object);
        }
    }

    public boolean contains(Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
        Set set = this.getSubSet(nextticklistentry, false);
        return set == null ? false : set.contains((Object)nextticklistentry);
    }

    public boolean add(Object obj) {
        boolean flag1;
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
        if (nextticklistentry == null) {
            return false;
        }
        Set set = this.getSubSet(nextticklistentry, true);
        boolean flag = set.add((Object)nextticklistentry);
        if (flag != (flag1 = super.add(obj))) {
            throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
        }
        return flag1;
    }

    public boolean remove(Object obj) {
        boolean flag1;
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
        Set set = this.getSubSet(nextticklistentry, false);
        if (set == null) {
            return false;
        }
        boolean flag = set.remove((Object)nextticklistentry);
        if (flag != (flag1 = super.remove((Object)nextticklistentry))) {
            throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
        }
        return flag1;
    }

    private Set getSubSet(NextTickListEntry entry, boolean autoCreate) {
        if (entry == null) {
            return null;
        }
        BlockPos blockpos = entry.position;
        int i = blockpos.getX() >> 4;
        int j = blockpos.getZ() >> 4;
        return this.getSubSet(i, j, autoCreate);
    }

    private Set getSubSet(int cx, int cz, boolean autoCreate) {
        long i = ChunkCoordIntPair.chunkXZ2Int(cx, cz);
        HashSet hashset = (HashSet)this.longHashMap.getValueByKey(i);
        if (hashset == null && autoCreate) {
            hashset = new HashSet();
            this.longHashMap.add(i, hashset);
        }
        return hashset;
    }

    public Iterator iterator() {
        if (this.minX == Integer.MIN_VALUE) {
            return super.iterator();
        }
        if (this.size() <= 0) {
            return Iterators.emptyIterator();
        }
        int i = this.minX >> 4;
        int j = this.minZ >> 4;
        int k = this.maxX >> 4;
        int l = this.maxZ >> 4;
        ArrayList list = new ArrayList();
        for (int i1 = i; i1 <= k; ++i1) {
            for (int j1 = j; j1 <= l; ++j1) {
                Set set = this.getSubSet(i1, j1, false);
                if (set == null) continue;
                list.add((Object)set.iterator());
            }
        }
        if (list.size() <= 0) {
            return Iterators.emptyIterator();
        }
        return list.size() == 1 ? (Iterator)list.get(0) : Iterators.concat((Iterator)list.iterator());
    }

    public void setIteratorLimits(int minX, int minZ, int maxX, int maxZ) {
        this.minX = Math.min((int)minX, (int)maxX);
        this.minZ = Math.min((int)minZ, (int)maxZ);
        this.maxX = Math.max((int)minX, (int)maxX);
        this.maxZ = Math.max((int)minZ, (int)maxZ);
    }

    public void clearIteratorLimits() {
        this.minX = Integer.MIN_VALUE;
        this.minZ = Integer.MIN_VALUE;
        this.maxX = Integer.MIN_VALUE;
        this.maxZ = Integer.MIN_VALUE;
    }
}

