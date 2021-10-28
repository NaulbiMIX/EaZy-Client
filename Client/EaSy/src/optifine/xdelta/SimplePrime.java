/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 */
package optifine.xdelta;

public class SimplePrime {
    private SimplePrime() {
    }

    public static long belowOrEqual(long number) {
        if (number < 2L) {
            return 0L;
        }
        if (number == 2L) {
            return 2L;
        }
        if ((number & 1L) == 0L) {
            --number;
        }
        while (!SimplePrime.testPrime(number)) {
            if ((number -= 2L) > 2L) continue;
            return 2L;
        }
        return number;
    }

    public static long aboveOrEqual(long number) {
        if (number <= 2L) {
            return 2L;
        }
        if ((number & 1L) == 0L) {
            ++number;
        }
        while (!SimplePrime.testPrime(number)) {
            if ((number += 2L) >= 0L) continue;
            return 0L;
        }
        return number;
    }

    public static boolean testPrime(long number) {
        if (number == 2L) {
            return true;
        }
        if (number < 2L) {
            return false;
        }
        if ((number & 1L) == 0L) {
            return false;
        }
        long i = (long)Math.floor((double)Math.sqrt((double)number));
        for (long j = 3L; j <= i; j += 2L) {
            if (number % j != 0L) continue;
            return false;
        }
        return true;
    }
}

