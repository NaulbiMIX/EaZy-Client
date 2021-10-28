/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Math
 */
package javax.vecmath;

import java.io.Serializable;
import javax.vecmath.Point4f;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple4d;
import javax.vecmath.Tuple4f;

public class Point4d
extends Tuple4d
implements Serializable {
    static final long serialVersionUID = 1733471895962736949L;

    public Point4d(double x, double y, double z, double w) {
        super(x, y, z, w);
    }

    public Point4d(double[] p) {
        super(p);
    }

    public Point4d(Point4d p1) {
        super(p1);
    }

    public Point4d(Point4f p1) {
        super(p1);
    }

    public Point4d(Tuple4f t1) {
        super(t1);
    }

    public Point4d(Tuple4d t1) {
        super(t1);
    }

    public Point4d(Tuple3d t1) {
        super(t1.x, t1.y, t1.z, 1.0);
    }

    public Point4d() {
    }

    public final void set(Tuple3d t1) {
        this.x = t1.x;
        this.y = t1.y;
        this.z = t1.z;
        this.w = 1.0;
    }

    public final double distanceSquared(Point4d p1) {
        double dx = this.x - p1.x;
        double dy = this.y - p1.y;
        double dz = this.z - p1.z;
        double dw = this.w - p1.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public final double distance(Point4d p1) {
        double dx = this.x - p1.x;
        double dy = this.y - p1.y;
        double dz = this.z - p1.z;
        double dw = this.w - p1.w;
        return Math.sqrt((double)(dx * dx + dy * dy + dz * dz + dw * dw));
    }

    public final double distanceL1(Point4d p1) {
        return Math.abs((double)(this.x - p1.x)) + Math.abs((double)(this.y - p1.y)) + Math.abs((double)(this.z - p1.z)) + Math.abs((double)(this.w - p1.w));
    }

    public final double distanceLinf(Point4d p1) {
        double t1 = Math.max((double)Math.abs((double)(this.x - p1.x)), (double)Math.abs((double)(this.y - p1.y)));
        double t2 = Math.max((double)Math.abs((double)(this.z - p1.z)), (double)Math.abs((double)(this.w - p1.w)));
        return Math.max((double)t1, (double)t2);
    }

    public final void project(Point4d p1) {
        double oneOw = 1.0 / p1.w;
        this.x = p1.x * oneOw;
        this.y = p1.y * oneOw;
        this.z = p1.z * oneOw;
        this.w = 1.0;
    }
}

