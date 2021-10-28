/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package net.minecraft.client.gui;

import de.flori2007.easyclient.EaSyClient;
import de.flori2007.easyclient.features.commands.CommandSystem;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.Session;

public class GuiIngameMenu
extends GuiScreen {
    private int field_146445_a;
    private int field_146444_f;

    @Override
    public void initGui() {
        this.field_146445_a = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        this.buttonList.add((Object)new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));
        if (!this.mc.isIntegratedServerRunning()) {
            ((GuiButton)this.buttonList.get((int)0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        this.buttonList.add((Object)new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add((Object)new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
        GuiButton guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0]));
        this.buttonList.add((Object)guibutton);
        this.buttonList.add((Object)new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add((Object)new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
        this.buttonList.add((Object)new GuiButton(1337, this.width / 2 - 100, this.height / 4 + 72 + i, 98, 20, "DDOS"));
        this.buttonList.add((Object)new GuiButton(1339, this.width / 2 + 2, this.height / 4 + 72 + i, 98, 20, "\u00a7cFORCE OP HACK"));
        this.buttonList.add((Object)new GuiButton(1, 5, 6, 98, 20, "\u00a7bCloudFucker"));
        this.buttonList.add((Object)new GuiButton(1, 5, 28, 98, 20, "\u00a7eCheck-Host"));
        this.buttonList.add((Object)new GuiButton(1, 5, 50, 98, 20, "\u00a72Start NullShell"));
        this.buttonList.add((Object)new GuiButton(1, 5, 72, 98, 20, "\u00a77Start RCE"));
        for (int i2 = 1; i2 < 10; ++i2) {
            this.buttonList.add((Object)new GuiButton(1, this.width - 103, i2 * 22 - 16, 98, 20, "\u00a7cKr\u00e4sch Server " + i2));
        }
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 1: {
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.isConnectedToRealms();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                if (flag) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                if (flag1) {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                    break;
                }
                this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
            }
            default: {
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            }
            case 1337: {
                EaSyClient.INSTANCE.clientTimeOut = true;
                break;
            }
            case 1339: {
                EaSyClient.INSTANCE.getCommandSystem().sendChatMessage("\u00a77\u00a7o[Server: Opped " + this.mc.session.getUsername() + "]");
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146444_f;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        GuiIngameMenu.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

