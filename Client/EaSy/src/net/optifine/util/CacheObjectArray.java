/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.reflect.Array
 *  java.util.ArrayDeque
 */
package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import net.minecraft.block.state.IBlockState;
import net.minecraft.src.Config;

public class CacheObjectArray {
    private static ArrayDeque<int[]> arrays = new ArrayDeque();
    private static int maxCacheSize = 10;

    private static synchronized int[] allocateArray(int size) {
        int[] aint = (int[])arrays.pollLast();
        if (aint == null || aint.length < size) {
            aint = new int[size];
        }
        return aint;
    }

    public static synchronized void freeArray(int[] ints) {
        if (arrays.size() < maxCacheSize) {
            arrays.add((Object)ints);
        }
    }

    public static void main(String[] args) throws Exception {
        int i = 4096;
        int j = 500000;
        CacheObjectArray.testNew(i, j);
        CacheObjectArray.testClone(i, j);
        CacheObjectArray.testNewObj(i, j);
        CacheObjectArray.testCloneObj(i, j);
        CacheObjectArray.testNewObjDyn(IBlockState.class, i, j);
        long k = CacheObjectArray.testNew(i, j);
        long l = CacheObjectArray.testClone(i, j);
        long i1 = CacheObjectArray.testNewObj(i, j);
        long j1 = CacheObjectArray.testCloneObj(i, j);
        long k1 = CacheObjectArray.testNewObjDyn(IBlockState.class, i, j);
        Config.dbg("New: " + k);
        Config.dbg("Clone: " + l);
        Config.dbg("NewObj: " + i1);
        Config.dbg("CloneObj: " + j1);
        Config.dbg("NewObjDyn: " + k1);
    }

    private static long testClone(int size, int count) {
        long i = System.currentTimeMillis();
        int[] aint = new int[size];
        for (int j = 0; j < count; ++j) {
            int[] arrn = (int[])aint.clone();
        }
        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testNew(int size, int count) {
        long i = System.currentTimeMillis();
        for (int j = 0; j < count; ++j) {
            int[] arrn = (int[])Array.newInstance((Class)Integer.TYPE, (int)size);
        }
        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testCloneObj(int size, int count) {
        long i = System.currentTimeMillis();
        IBlockState[] aiblockstate = new IBlockState[size];
        for (int j = 0; j < count; ++j) {
            IBlockState[] arriBlockState = (IBlockState[])aiblockstate.clone();
        }
        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testNewObj(int size, int count) {
        long i = System.currentTimeMillis();
        for (int j = 0; j < count; ++j) {
            IBlockState[] arriBlockState = new IBlockState[size];
        }
        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testNewObjDyn(Class cls, int size, int count) {
        long i = System.currentTimeMillis();
        for (int j = 0; j < count; ++j) {
            Object[] arrobject = (Object[])Array.newInstance((Class)cls, (int)size);
        }
        long k = System.currentTimeMillis();
        return k - i;
    }
}

