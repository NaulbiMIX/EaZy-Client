/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.ClassCastException
 *  java.lang.CloneNotSupportedException
 *  java.lang.Cloneable
 *  java.lang.InternalError
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 */
package javax.vecmath;

import java.io.Serializable;

public abstract class Tuple4b
implements Serializable,
Cloneable {
    static final long serialVersionUID = -8226727741811898211L;
    public byte x;
    public byte y;
    public byte z;
    public byte w;

    public Tuple4b(byte b1, byte b2, byte b3, byte b4) {
        this.x = b1;
        this.y = b2;
        this.z = b3;
        this.w = b4;
    }

    public Tuple4b(byte[] t) {
        this.x = t[0];
        this.y = t[1];
        this.z = t[2];
        this.w = t[3];
    }

    public Tuple4b(Tuple4b t1) {
        this.x = t1.x;
        this.y = t1.y;
        this.z = t1.z;
        this.w = t1.w;
    }

    public Tuple4b() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }

    public String toString() {
        return "(" + (this.x & 255) + ", " + (this.y & 255) + ", " + (this.z & 255) + ", " + (this.w & 255) + ")";
    }

    public final void get(byte[] b) {
        b[0] = this.x;
        b[1] = this.y;
        b[2] = this.z;
        b[3] = this.w;
    }

    public final void get(Tuple4b t1) {
        t1.x = this.x;
        t1.y = this.y;
        t1.z = this.z;
        t1.w = this.w;
    }

    public final void set(Tuple4b t1) {
        this.x = t1.x;
        this.y = t1.y;
        this.z = t1.z;
        this.w = t1.w;
    }

    public final void set(byte[] b) {
        this.x = b[0];
        this.y = b[1];
        this.z = b[2];
        this.w = b[3];
    }

    public boolean equals(Tuple4b t1) {
        try {
            return this.x == t1.x && this.y == t1.y && this.z == t1.z && this.w == t1.w;
        }
        catch (NullPointerException e2) {
            return false;
        }
    }

    public boolean equals(Object t1) {
        try {
            Tuple4b t2 = (Tuple4b)t1;
            return this.x == t2.x && this.y == t2.y && this.z == t2.z && this.w == t2.w;
        }
        catch (NullPointerException e2) {
            return false;
        }
        catch (ClassCastException e1) {
            return false;
        }
    }

    public int hashCode() {
        return (this.x & 255) << 0 | (this.y & 255) << 8 | (this.z & 255) << 16 | (this.w & 255) << 24;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public final byte getX() {
        return this.x;
    }

    public final void setX(byte x) {
        this.x = x;
    }

    public final byte getY() {
        return this.y;
    }

    public final void setY(byte y) {
        this.y = y;
    }

    public final byte getZ() {
        return this.z;
    }

    public final void setZ(byte z) {
        this.z = z;
    }

    public final byte getW() {
        return this.w;
    }

    public final void setW(byte w) {
        this.w = w;
    }
}

