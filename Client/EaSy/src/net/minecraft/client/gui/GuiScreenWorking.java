/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.IProgressUpdate;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class GuiScreenWorking
extends GuiScreen
implements IProgressUpdate {
    private String field_146591_a = "";
    private String field_146589_f = "";
    private int progress;
    private boolean doneWorking;
    private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

    @Override
    public void displaySavingString(String message) {
        this.resetProgressAndMessage(message);
    }

    @Override
    public void resetProgressAndMessage(String message) {
        this.field_146591_a = message;
        this.displayLoadingString("Working...");
    }

    @Override
    public void displayLoadingString(String message) {
        this.field_146589_f = message;
        this.setLoadingProgress(0);
    }

    @Override
    public void setLoadingProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public void setDoneWorking() {
        this.doneWorking = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.doneWorking) {
            if (!this.mc.isConnectedToRealms()) {
                this.mc.displayGuiScreen(null);
            }
        } else {
            if (this.customLoadingScreen != null && this.mc.theWorld == null) {
                this.customLoadingScreen.drawBackground(this.width, this.height);
            } else {
                this.drawDefaultBackground();
            }
            if (this.progress > 0) {
                GuiScreenWorking.drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
                GuiScreenWorking.drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.progress + "%", this.width / 2, 90, 16777215);
            }
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
}

