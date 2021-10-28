/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import net.minecraft.client.renderer.IImageBuffer;

public class ImageBufferDownload
implements IImageBuffer {
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;
    private static final String __OBFID = "CL_00000956";

    @Override
    public BufferedImage parseUserSkin(BufferedImage p_78432_1_) {
        if (p_78432_1_ == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 64;
        BufferedImage var2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        Graphics var3 = var2.getGraphics();
        var3.drawImage(p_78432_1_, 0, 0, null);
        if (p_78432_1_.getHeight() == 32) {
            var3.drawImage(var2, 24, 48, 20, 52, 4, 16, 8, 20, null);
            var3.drawImage(var2, 28, 48, 24, 52, 8, 16, 12, 20, null);
            var3.drawImage(var2, 20, 52, 16, 64, 8, 20, 12, 32, null);
            var3.drawImage(var2, 24, 52, 20, 64, 4, 20, 8, 32, null);
            var3.drawImage(var2, 28, 52, 24, 64, 0, 20, 4, 32, null);
            var3.drawImage(var2, 32, 52, 28, 64, 12, 20, 16, 32, null);
            var3.drawImage(var2, 40, 48, 36, 52, 44, 16, 48, 20, null);
            var3.drawImage(var2, 44, 48, 40, 52, 48, 16, 52, 20, null);
            var3.drawImage(var2, 36, 52, 32, 64, 48, 20, 52, 32, null);
            var3.drawImage(var2, 40, 52, 36, 64, 44, 20, 48, 32, null);
            var3.drawImage(var2, 44, 52, 40, 64, 40, 20, 44, 32, null);
            var3.drawImage(var2, 48, 52, 44, 64, 52, 20, 56, 32, null);
        }
        var3.dispose();
        this.imageData = ((DataBufferInt)var2.getRaster().getDataBuffer()).getData();
        this.setAreaOpaque(0, 0, 32, 16);
        this.setAreaTransparent(32, 0, 64, 32);
        this.setAreaOpaque(0, 16, 64, 32);
        this.setAreaTransparent(0, 32, 16, 48);
        this.setAreaTransparent(16, 32, 40, 48);
        this.setAreaTransparent(40, 32, 56, 48);
        this.setAreaTransparent(0, 48, 16, 64);
        this.setAreaOpaque(16, 48, 48, 64);
        this.setAreaTransparent(48, 48, 64, 64);
        return var2;
    }

    @Override
    public void func_152634_a() {
    }

    private void setAreaTransparent(int p_78434_1_, int p_78434_2_, int p_78434_3_, int p_78434_4_) {
        if (!this.hasTransparency(p_78434_1_, p_78434_2_, p_78434_3_, p_78434_4_)) {
            for (int var5 = p_78434_1_; var5 < p_78434_3_; ++var5) {
                for (int var6 = p_78434_2_; var6 < p_78434_4_; ++var6) {
                    int[] arrn = this.imageData;
                    int n = var5 + var6 * this.imageWidth;
                    arrn[n] = arrn[n] & 16777215;
                }
            }
        }
    }

    private void setAreaOpaque(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_) {
        for (int var5 = p_78433_1_; var5 < p_78433_3_; ++var5) {
            for (int var6 = p_78433_2_; var6 < p_78433_4_; ++var6) {
                int[] arrn = this.imageData;
                int n = var5 + var6 * this.imageWidth;
                arrn[n] = arrn[n] | -16777216;
            }
        }
    }

    private boolean hasTransparency(int p_78435_1_, int p_78435_2_, int p_78435_3_, int p_78435_4_) {
        for (int var5 = p_78435_1_; var5 < p_78435_3_; ++var5) {
            for (int var6 = p_78435_2_; var6 < p_78435_4_; ++var6) {
                int var7 = this.imageData[var5 + var6 * this.imageWidth];
                if ((var7 >> 24 & 255) >= 128) continue;
                return true;
            }
        }
        return false;
    }
}

