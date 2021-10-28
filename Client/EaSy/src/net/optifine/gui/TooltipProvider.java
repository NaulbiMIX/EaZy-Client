/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.Rectangle
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.gui;

import java.awt.Rectangle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public interface TooltipProvider {
    public Rectangle getTooltipBounds(GuiScreen var1, int var2, int var3);

    public String[] getTooltipLines(GuiButton var1, int var2);

    public boolean isRenderBorder();
}

