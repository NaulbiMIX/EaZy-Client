/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Set
 */
package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.IChatComponent;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenCapeOF;

public class GuiCustomizeSkin
extends GuiScreen {
    private final GuiScreen parentScreen;
    private String title;

    public GuiCustomizeSkin(GuiScreen parentScreenIn) {
        this.parentScreen = parentScreenIn;
    }

    @Override
    public void initGui() {
        int i = 0;
        this.title = I18n.format("options.skinCustomisation.title", new Object[0]);
        for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
            this.buttonList.add((Object)new ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts));
            ++i;
        }
        if (i % 2 == 1) {
            ++i;
        }
        this.buttonList.add((Object)new GuiButtonOF(210, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("of.options.skinCustomisation.ofCape", new Object[0])));
        this.buttonList.add((Object)new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * ((i += 2) >> 1), I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 210) {
                this.mc.displayGuiScreen(new GuiScreenCapeOF(this));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (button instanceof ButtonPart) {
                EnumPlayerModelParts enumplayermodelparts = ((ButtonPart)button).playerModelParts;
                this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
                button.displayString = this.func_175358_a(enumplayermodelparts);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        GuiCustomizeSkin.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private String func_175358_a(EnumPlayerModelParts playerModelParts) {
        String s = this.mc.gameSettings.getModelParts().contains((Object)playerModelParts) ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]);
        return playerModelParts.func_179326_d().getFormattedText() + ": " + s;
    }

    class ButtonPart
    extends GuiButton {
        private final EnumPlayerModelParts playerModelParts;

        private ButtonPart(int p_i45514_2_, int p_i45514_3_, int p_i45514_4_, int p_i45514_5_, int p_i45514_6_, EnumPlayerModelParts playerModelParts) {
            super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(playerModelParts));
            this.playerModelParts = playerModelParts;
        }
    }

}

