/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Character
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.util.ArrayList
 *  java.util.List
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;

final class Util {
    Util() {
    }

    static <T> List<T> asArrayList(int length) {
        ArrayList list = new ArrayList(length);
        for (int i = 0; i < length; ++i) {
            list.add(null);
        }
        return list;
    }

    static <T> List<T> asArrayList(T[] array) {
        if (array == null) {
            return new ArrayList();
        }
        ArrayList list = new ArrayList(array.length);
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    static List<Byte> asArrayList(byte[] byteArray) {
        if (byteArray == null) {
            return new ArrayList();
        }
        ArrayList byteList = new ArrayList(byteArray.length);
        for (byte b : byteArray) {
            byteList.add((Object)b);
        }
        return byteList;
    }

    static List<Boolean> asArrayList(boolean[] booleanArray) {
        if (booleanArray == null) {
            return new ArrayList();
        }
        ArrayList booleanList = new ArrayList(booleanArray.length);
        for (boolean b : booleanArray) {
            booleanList.add((Object)b);
        }
        return booleanList;
    }

    static List<Short> asArrayList(short[] shortArray) {
        if (shortArray == null) {
            return new ArrayList();
        }
        ArrayList shortList = new ArrayList(shortArray.length);
        for (short s : shortArray) {
            shortList.add((Object)s);
        }
        return shortList;
    }

    static List<Character> asArrayList(char[] charArray) {
        if (charArray == null) {
            return new ArrayList();
        }
        ArrayList charList = new ArrayList(charArray.length);
        for (char c : charArray) {
            charList.add((Object)Character.valueOf((char)c));
        }
        return charList;
    }

    static List<Integer> asArrayList(int[] intArray) {
        if (intArray == null) {
            return new ArrayList();
        }
        ArrayList intList = new ArrayList(intArray.length);
        for (int i : intArray) {
            intList.add((Object)i);
        }
        return intList;
    }

    static List<Float> asArrayList(float[] floatArray) {
        if (floatArray == null) {
            return new ArrayList();
        }
        ArrayList floatList = new ArrayList(floatArray.length);
        for (float f : floatArray) {
            floatList.add((Object)Float.valueOf((float)f));
        }
        return floatList;
    }

    static List<Long> asArrayList(long[] longArray) {
        if (longArray == null) {
            return new ArrayList();
        }
        ArrayList longList = new ArrayList(longArray.length);
        for (long l : longArray) {
            longList.add((Object)l);
        }
        return longList;
    }

    static List<Double> asArrayList(double[] doubleArray) {
        if (doubleArray == null) {
            return new ArrayList();
        }
        ArrayList doubleList = new ArrayList(doubleArray.length);
        for (double d : doubleArray) {
            doubleList.add((Object)d);
        }
        return doubleList;
    }

    static <T> List<T> asArrayList(int length, T[] array) {
        ArrayList list = new ArrayList(length);
        for (int i = 0; i < length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
}

