/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ResourceLocation;

public class GuiStreamIndicator {
    private static final ResourceLocation locationStreamIndicator = new ResourceLocation("textures/gui/stream_indicator.png");
    private final Minecraft mc;
    private float streamAlpha = 1.0f;
    private int streamAlphaDelta = 1;

    public GuiStreamIndicator(Minecraft mcIn) {
        this.mc = mcIn;
    }

    public void render(int p_152437_1_, int p_152437_2_) {
    }

    private void render(int p_152436_1_, int p_152436_2_, int p_152436_3_, int p_152436_4_) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.65f + 0.35000002f * this.streamAlpha);
        this.mc.getTextureManager().bindTexture(locationStreamIndicator);
        float f = 150.0f;
        float f1 = 0.0f;
        float f2 = (float)p_152436_3_ * 0.015625f;
        float f3 = 1.0f;
        float f4 = (float)(p_152436_3_ + 16) * 0.015625f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 16, f).tex(f1, f4).endVertex();
        worldrenderer.pos(p_152436_1_ - p_152436_4_, p_152436_2_ + 16, f).tex(f3, f4).endVertex();
        worldrenderer.pos(p_152436_1_ - p_152436_4_, p_152436_2_ + 0, f).tex(f3, f2).endVertex();
        worldrenderer.pos(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 0, f).tex(f1, f2).endVertex();
        tessellator.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private int func_152440_b() {
        return this.mc.getTwitchStream().isPaused() ? 16 : 0;
    }

    private int func_152438_c() {
        return this.mc.getTwitchStream().func_152929_G() ? 48 : 32;
    }

    public void updateStreamAlpha() {
        if (this.mc.getTwitchStream().isBroadcasting()) {
            this.streamAlpha += 0.025f * (float)this.streamAlphaDelta;
            if (this.streamAlpha < 0.0f) {
                this.streamAlphaDelta *= -1;
                this.streamAlpha = 0.0f;
            } else if (this.streamAlpha > 1.0f) {
                this.streamAlphaDelta *= -1;
                this.streamAlpha = 1.0f;
            }
        } else {
            this.streamAlpha = 1.0f;
            this.streamAlphaDelta = 1;
        }
    }
}

