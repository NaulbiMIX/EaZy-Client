/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.util.List
 */
package net.optifine.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class GuiScreenOF
extends GuiScreen {
    protected void actionPerformedRightClick(GuiButton button) throws IOException {
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        GuiButton guibutton;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 1 && (guibutton = GuiScreenOF.getSelectedButton(mouseX, mouseY, (List<GuiButton>)this.buttonList)) != null && guibutton.enabled) {
            guibutton.playPressSound(this.mc.getSoundHandler());
            this.actionPerformedRightClick(guibutton);
        }
    }

    public static GuiButton getSelectedButton(int x, int y, List<GuiButton> listButtons) {
        for (int i = 0; i < listButtons.size(); ++i) {
            GuiButton guibutton = (GuiButton)listButtons.get(i);
            if (!guibutton.visible) continue;
            int j = GuiVideoSettings.getButtonWidth(guibutton);
            int k = GuiVideoSettings.getButtonHeight(guibutton);
            if (x < guibutton.xPosition || y < guibutton.yPosition || x >= guibutton.xPosition + j || y >= guibutton.yPosition + k) continue;
            return guibutton;
        }
        return null;
    }
}

