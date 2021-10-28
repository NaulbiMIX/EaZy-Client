/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  org.lwjgl.opengl.GL11
 */
package de.flori2007.easyclient.gui;

import de.flori2007.easyclient.gui.GuiBuyPremium;
import de.flori2007.easyclient.utils.AuthHelper;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL11;

public class GuiAccount
extends GuiScreen {
    public GuiTextField nameField;
    public GuiTextField passwordField;

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add((Object)new GuiButton(1337, this.width / 2 - 100, this.height / 2 + 40, 98, 20, "Back"));
        this.buttonList.add((Object)new GuiButton(1338, this.width / 2 + 2, this.height / 2 + 40, 98, 20, "Login"));
        this.buttonList.add((Object)new GuiButton(1339, this.width / 2 - 100, this.height / 2 + 18, 200, 20, "\u00a7c\u00a7lHACK ACCOUNT"));
        this.nameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 40, 200, 20);
        this.passwordField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 20, 200, 20);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.nameField.updateCursorCounter();
        this.passwordField.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.nameField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            Minecraft.getMinecraft().shutdownMinecraftApplet();
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 1337) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiBuyPremium());
        }
        if (button.id == 1338 || button.id == 1339) {
            Minecraft.getMinecraft().session = new Session(AuthHelper.getCrackedName(this.nameField.getText(), this.passwordField.getText()), "-", "-", "legacy");
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        GL11.glPushMatrix();
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        Gui.drawCenteredString(this.fontRendererObj, "\u00a7C\u00a7lBEST ACCOUNT MANAGER", this.width / 4, 0, -1);
        GL11.glPopMatrix();
        this.nameField.drawTextBox();
        this.passwordField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

