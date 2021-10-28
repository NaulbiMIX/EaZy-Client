/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  org.lwjgl.opengl.GL11
 */
package de.flori2007.easyclient.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiBuyPremium
extends GuiScreen {
    private final List<String> TEXTS = new ArrayList();

    public GuiBuyPremium() {
        this.TEXTS.add((Object)"Want to buy the premium version of the cool new EaSy client?");
        this.TEXTS.add((Object)"Chcesz kupi\u0107 wersj\u0119 premium nowego, fajnego klienta EaSy?");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add((Object)new GuiButton(1337, this.width / 2 - 100, this.height / 2 + 40, 98, 20, "Back"));
        this.buttonList.add((Object)new GuiButton(1337, this.width / 2 + 2, this.height / 2 + 40, 98, 20, "\u00a7cBuy!"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 1337) {
            Minecraft.getMinecraft().shutdownMinecraftApplet();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            Minecraft.getMinecraft().shutdownMinecraftApplet();
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int yIter = 0;
        for (String text : this.TEXTS) {
            GL11.glPushMatrix();
            GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
            Gui.drawCenteredString(this.fontRendererObj, this.getFunnyString() + text, this.width / 4, yIter, -1);
            GL11.glPopMatrix();
            yIter += 10;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public String getFunnyString() {
        String s = "";
        switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
            default: {
                s = "\u00a7a";
                break;
            }
            case 1: 
            case 3: {
                s = "\u00a7e";
                break;
            }
            case 2: {
                s = "\u00a7c";
            }
        }
        return s;
    }
}

