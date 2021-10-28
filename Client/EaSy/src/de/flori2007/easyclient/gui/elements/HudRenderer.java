/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.List
 */
package de.flori2007.easyclient.gui.elements;

import de.flori2007.easyclient.EaSyClient;
import de.flori2007.easyclient.features.hacks.HackList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class HudRenderer {
    public final ResourceLocation LOGO = new ResourceLocation("minecraft", "easyclient/eazy_wurst2.jpg");

    public void renderHud(int x, int y) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.LOGO);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 128, 50, 128.0f, 50.0f);
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        fr.drawString("\u00a7cS\u00edds: \u00a7e" + Minecraft.debugFPS, x, y + 50, -1);
        fr.drawString("\u00a7cX: \u00a7e" + String.format((String)"%.2f", (Object[])new Object[]{Minecraft.getMinecraft().thePlayer.posX + 10.0}), x, y + 60, -1);
        fr.drawString("\u00a7cZ: \u00a7e" + String.format((String)"%.2f", (Object[])new Object[]{Minecraft.getMinecraft().thePlayer.posY + 10.0}), x, y + 70, -1);
        fr.drawString("\u00a7cY: \u00a7e" + String.format((String)"%.2f", (Object[])new Object[]{Minecraft.getMinecraft().thePlayer.posZ + 10.0}), x, y + 80, -1);
        fr.drawString("\u00a7cTPS: \u00a7a19.99", x, y + 90, -1);
        if (EaSyClient.INSTANCE.lag > 0L) {
            fr.drawString("\u00a7cLag: \u00a7c" + (System.currentTimeMillis() - EaSyClient.INSTANCE.lag), x, y + 100, -1);
        }
        int width = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
        int iterY = y;
        for (String hack : EaSyClient.INSTANCE.getHackList().getHacks()) {
            fr.drawString(hack, width - fr.getStringWidth(hack), iterY, -1);
            iterY += 10;
        }
    }
}

