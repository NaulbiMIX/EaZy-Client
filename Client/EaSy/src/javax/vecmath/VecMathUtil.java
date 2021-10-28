/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Object
 */
package javax.vecmath;

class VecMathUtil {
    private VecMathUtil() {
    }

    static final long hashLongBits(long hash, long l) {
        return (hash *= 31L) + l;
    }

    static final long hashFloatBits(long hash, float f) {
        hash *= 31L;
        if (f == 0.0f) {
            return hash;
        }
        return hash + (long)Float.floatToIntBits((float)f);
    }

    static final long hashDoubleBits(long hash, double d) {
        hash *= 31L;
        if (d == 0.0) {
            return hash;
        }
        return hash + Double.doubleToLongBits((double)d);
    }

    static final int hashFinish(long hash) {
        return (int)(hash ^ hash >> 32);
    }
}

