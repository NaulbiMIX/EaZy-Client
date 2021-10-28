/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Float
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.util.Arrays
 */
package net.minecraft.client.renderer.block.model;

import java.util.Arrays;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BreakingFour
extends BakedQuad {
    private final TextureAtlasSprite texture;

    public BreakingFour(BakedQuad quad, TextureAtlasSprite textureIn) {
        super(Arrays.copyOf((int[])quad.getVertexData(), (int)quad.getVertexData().length), quad.tintIndex, FaceBakery.getFacingFromVertexData(quad.getVertexData()));
        this.texture = textureIn;
        this.remapQuad();
        this.fixVertexData();
    }

    private void remapQuad() {
        for (int i = 0; i < 4; ++i) {
            this.remapVert(i);
        }
    }

    private void remapVert(int vertex) {
        int i = this.vertexData.length / 4;
        int j = i * vertex;
        float f = Float.intBitsToFloat((int)this.vertexData[j]);
        float f1 = Float.intBitsToFloat((int)this.vertexData[j + 1]);
        float f2 = Float.intBitsToFloat((int)this.vertexData[j + 2]);
        float f3 = 0.0f;
        float f4 = 0.0f;
        switch (this.face) {
            case DOWN: {
                f3 = f * 16.0f;
                f4 = (1.0f - f2) * 16.0f;
                break;
            }
            case UP: {
                f3 = f * 16.0f;
                f4 = f2 * 16.0f;
                break;
            }
            case NORTH: {
                f3 = (1.0f - f) * 16.0f;
                f4 = (1.0f - f1) * 16.0f;
                break;
            }
            case SOUTH: {
                f3 = f * 16.0f;
                f4 = (1.0f - f1) * 16.0f;
                break;
            }
            case WEST: {
                f3 = f2 * 16.0f;
                f4 = (1.0f - f1) * 16.0f;
                break;
            }
            case EAST: {
                f3 = (1.0f - f2) * 16.0f;
                f4 = (1.0f - f1) * 16.0f;
            }
        }
        this.vertexData[j + 4] = Float.floatToRawIntBits((float)this.texture.getInterpolatedU(f3));
        this.vertexData[j + 4 + 1] = Float.floatToRawIntBits((float)this.texture.getInterpolatedV(f4));
    }

}

