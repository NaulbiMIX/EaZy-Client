/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.Rectangle
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 *  java.util.List
 */
package net.optifine.gui;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipProvider;

public class TooltipManager {
    private GuiScreen guiScreen;
    private TooltipProvider tooltipProvider;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public TooltipManager(GuiScreen guiScreen, TooltipProvider tooltipProvider) {
        this.guiScreen = guiScreen;
        this.tooltipProvider = tooltipProvider;
    }

    public void drawTooltips(int x, int y, List buttonList) {
        if (Math.abs((int)(x - this.lastMouseX)) <= 5 && Math.abs((int)(y - this.lastMouseY)) <= 5) {
            GuiButton guibutton;
            int i = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)i && (guibutton = GuiScreenOF.getSelectedButton(x, y, (List<GuiButton>)buttonList)) != null) {
                Rectangle rectangle = this.tooltipProvider.getTooltipBounds(this.guiScreen, x, y);
                Object[] astring = this.tooltipProvider.getTooltipLines(guibutton, rectangle.width);
                if (astring != null) {
                    if (astring.length > 8) {
                        astring = (String[])Arrays.copyOf((Object[])astring, (int)8);
                        astring[astring.length - 1] = (String)astring[astring.length - 1] + " ...";
                    }
                    if (this.tooltipProvider.isRenderBorder()) {
                        int j = -528449408;
                        this.drawRectBorder(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, j);
                    }
                    Gui.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, -536870912);
                    for (int l = 0; l < astring.length; ++l) {
                        Object s = astring[l];
                        int k = 14540253;
                        if (s.endsWith("!")) {
                            k = 16719904;
                        }
                        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
                        fontrenderer.drawStringWithShadow((String)s, rectangle.x + 5, rectangle.y + 5 + l * 11, k);
                    }
                }
            }
        } else {
            this.lastMouseX = x;
            this.lastMouseY = y;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private void drawRectBorder(int x1, int y1, int x2, int y2, int col) {
        Gui.drawRect(x1, y1 - 1, x2, y1, col);
        Gui.drawRect(x1, y2, x2, y2 + 1, col);
        Gui.drawRect(x1 - 1, y1, x1, y2, col);
        Gui.drawRect(x2, y1, x2 + 1, y2, col);
    }
}

