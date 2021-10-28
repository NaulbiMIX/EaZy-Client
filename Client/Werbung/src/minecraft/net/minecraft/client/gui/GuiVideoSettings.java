/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiVideoSettings
extends GuiScreen {
    private GuiScreen parentGuiScreen;
    protected String screenTitle = "Video Settings";
    private GameSettings guiGameSettings;
    private GuiListExtended optionsRowList;
    private static final GameSettings.Options[] videoOptions = new GameSettings.Options[]{GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.ANAGLYPH, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.GAMMA, GameSettings.Options.RENDER_CLOUDS, GameSettings.Options.PARTICLES, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.ENABLE_VSYNC, GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.USE_VBO};
    private static final String __OBFID = "CL_00000718";

    public GuiVideoSettings(GuiScreen p_i1062_1_, GameSettings p_i1062_2_) {
        this.parentGuiScreen = p_i1062_1_;
        this.guiGameSettings = p_i1062_2_;
    }

    @Override
    public void initGui() {
        this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 27, I18n.format("gui.done", new Object[0])));
        if (!OpenGlHelper.field_176083_O) {
            GameSettings.Options[] var1 = new GameSettings.Options[videoOptions.length - 1];
            int var2 = 0;
            for (GameSettings.Options var6 : videoOptions) {
                if (var6 == GameSettings.Options.USE_VBO) break;
                var1[var2] = var6;
                ++var2;
            }
            this.optionsRowList = new GuiOptionsRowList(this.mc, this.width, this.height, 32, this.height - 32, 25, var1);
        } else {
            this.optionsRowList = new GuiOptionsRowList(this.mc, this.width, this.height, 32, this.height - 32, 25, videoOptions);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.optionsRowList.func_178039_p();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled && button.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentGuiScreen);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int var4 = this.guiGameSettings.guiScale;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.optionsRowList.func_148179_a(mouseX, mouseY, mouseButton);
        if (this.guiGameSettings.guiScale != var4) {
            ScaledResolution var5 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int var6 = var5.getScaledWidth();
            int var7 = var5.getScaledHeight();
            this.setWorldAndResolution(this.mc, var6, var7);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        int var4 = this.guiGameSettings.guiScale;
        super.mouseReleased(mouseX, mouseY, state);
        this.optionsRowList.func_148181_b(mouseX, mouseY, state);
        if (this.guiGameSettings.guiScale != var4) {
            ScaledResolution var5 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int var6 = var5.getScaledWidth();
            int var7 = var5.getScaledHeight();
            this.setWorldAndResolution(this.mc, var6, var7);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.optionsRowList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 5, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

