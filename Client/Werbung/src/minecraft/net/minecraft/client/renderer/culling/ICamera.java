/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public interface ICamera {
    public boolean isBoundingBoxInFrustum(AxisAlignedBB var1);

    public void setPosition(double var1, double var3, double var5);
}

