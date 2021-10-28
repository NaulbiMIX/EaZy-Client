/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 */
package de.flori2007.easyclient;

import de.flori2007.easyclient.features.commands.CommandSystem;
import de.flori2007.easyclient.features.hacks.HackList;
import de.flori2007.easyclient.gui.elements.HudRenderer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class EaSyClient {
    public static final EaSyClient INSTANCE = new EaSyClient();
    public final ResourceLocation BACKGROUND = new ResourceLocation("minecraft", "easyclient/sid_uwu1.png");
    public boolean clientTimeOut = false;
    public long lag = 0L;
    public boolean demoMode = false;
    private HudRenderer hudRenderer;
    private HackList hackList;
    private CommandSystem commandSystem;
    public List<String> connectingProgress = new ArrayList();

    public void start() {
        System.out.println("Starting NullShelled");
        this.hudRenderer = new HudRenderer();
        this.hackList = new HackList();
        this.getHackList().init();
        this.commandSystem = new CommandSystem();
    }

    public void reset() {
        this.clientTimeOut = false;
        this.lag = 0L;
        this.demoMode = false;
    }

    public void renderBackground() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.BACKGROUND);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
    }

    public HudRenderer getHudRenderer() {
        return this.hudRenderer;
    }

    public HackList getHackList() {
        return this.hackList;
    }

    public CommandSystem getCommandSystem() {
        return this.commandSystem;
    }

    public String getDisplayTitle() {
        return "EaSy b1338 Private Version by Nzxter, FixMem(e) & Gecodet aka QUIT";
    }
}

