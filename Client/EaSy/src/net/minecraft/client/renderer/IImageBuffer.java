/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.image.BufferedImage
 *  java.lang.Object
 */
package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;

public interface IImageBuffer {
    public BufferedImage parseUserSkin(BufferedImage var1);

    public void skinAvailable();
}

