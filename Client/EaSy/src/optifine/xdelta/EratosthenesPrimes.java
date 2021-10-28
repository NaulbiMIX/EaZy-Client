/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 */
package optifine.xdelta;

import optifine.xdelta.BitArray;

public class EratosthenesPrimes {
    static BitArray sieve;
    static int lastInit;

    public static synchronized void reset() {
        sieve = null;
        lastInit = -1;
    }

    public static synchronized void init(int maxNumber) {
        if (maxNumber > lastInit) {
            int i = (int)Math.ceil((double)Math.sqrt((double)maxNumber));
            lastInit = maxNumber;
            maxNumber >>= 1;
            i >>= 1;
            sieve = new BitArray(++maxNumber + 1);
            sieve.set(0, true);
            for (int j = 1; j <= ++i; ++j) {
                if (sieve.get(j)) continue;
                int k = (j << 1) + 1;
                for (int l = j * ((j << 1) + 2); l <= maxNumber; l += k) {
                    sieve.set(l, true);
                }
            }
        }
    }

    public static synchronized int[] getPrimes(int maxNumber) {
        int i = EratosthenesPrimes.primesCount(maxNumber);
        if (i <= 0) {
            return new int[0];
        }
        if (maxNumber == 2) {
            return new int[]{2};
        }
        EratosthenesPrimes.init(maxNumber);
        int[] aint = new int[i];
        int j = maxNumber - 1 >> 1;
        int k = 0;
        aint[k++] = 2;
        for (int l = 1; l <= j; ++l) {
            if (sieve.get(l)) continue;
            aint[k++] = (l << 1) + 1;
        }
        return aint;
    }

    public static synchronized int primesCount(int number) {
        if (number < 2) {
            return 0;
        }
        EratosthenesPrimes.init(number);
        int i = number - 1 >> 1;
        int j = 1;
        for (int k = 1; k <= i; ++k) {
            if (sieve.get(k)) continue;
            ++j;
        }
        return j;
    }

    public static synchronized int belowOrEqual(int number) {
        int i;
        if (number < 2) {
            return -1;
        }
        if (number == 2) {
            return 2;
        }
        EratosthenesPrimes.init(number);
        for (int j = i = number - 1 >> 1; j > 0; --j) {
            if (sieve.get(j)) continue;
            return (j << 1) + 1;
        }
        return -1;
    }

    public static int below(int number) {
        return EratosthenesPrimes.belowOrEqual(number - 1);
    }

    static {
        lastInit = -1;
    }
}

