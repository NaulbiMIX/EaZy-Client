/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.BitSet
 */
package net.minecraft.client.renderer;

import java.util.BitSet;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.optifine.SmartAnimations;

public class Tessellator {
    private WorldRenderer worldRenderer;
    private WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
    private static final Tessellator instance = new Tessellator(2097152);

    public static Tessellator getInstance() {
        return instance;
    }

    public Tessellator(int bufferSize) {
        this.worldRenderer = new WorldRenderer(bufferSize);
    }

    public void draw() {
        if (this.worldRenderer.animatedSprites != null) {
            SmartAnimations.spritesRendered(this.worldRenderer.animatedSprites);
        }
        this.worldRenderer.finishDrawing();
        this.vboUploader.draw(this.worldRenderer);
    }

    public WorldRenderer getWorldRenderer() {
        return this.worldRenderer;
    }
}

