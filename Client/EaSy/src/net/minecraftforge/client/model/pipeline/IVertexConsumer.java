/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraftforge.client.model.pipeline;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;

public interface IVertexConsumer {
    public VertexFormat getVertexFormat();

    public void setQuadTint(int var1);

    public void setQuadOrientation(EnumFacing var1);

    public void setQuadColored();

    public /* varargs */ void put(int var1, float ... var2);
}

