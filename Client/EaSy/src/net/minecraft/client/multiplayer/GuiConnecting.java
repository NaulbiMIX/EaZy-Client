/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  java.awt.Color
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.net.InetAddress
 *  java.net.UnknownHostException
 *  java.util.List
 *  java.util.concurrent.atomic.AtomicInteger
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.mojang.authlib.GameProfile;
import de.flori2007.easyclient.EaSyClient;
import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting
extends GuiScreen {
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;

    public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_) {
        this.mc = mcIn;
        this.previousGuiScreen = p_i1181_1_;
        ServerAddress serveraddress = ServerAddress.fromString(p_i1181_3_.serverIP);
        mcIn.loadWorld(null);
        mcIn.setServerData(p_i1181_3_);
        this.connect(serveraddress.getIP(), serveraddress.getPort());
    }

    public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String hostName, int port) {
        this.mc = mcIn;
        this.previousGuiScreen = p_i1182_1_;
        mcIn.loadWorld(null);
        this.connect(hostName, port);
    }

    private void connect(final String ip, final int port) {
        logger.info("Connecting to " + ip + ", " + port);
        new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet()){

            public void run() {
                InetAddress inetaddress = null;
                try {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    inetaddress = InetAddress.getByName((String)ip);
                    EaSyClient.INSTANCE.connectingProgress.add((Object)"Starting Spigot.jar");
                    GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.access$200((GuiConnecting)GuiConnecting.this).gameSettings.isUsingNativeTransport());
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
                    EaSyClient.INSTANCE.connectingProgress.add((Object)"Downloading BetterCraft");
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
                    EaSyClient.INSTANCE.connectingProgress.add((Object)"Injecting STools");
                    EaSyClient.INSTANCE.connectingProgress.add((Object)"Bypassing HeroAC");
                }
                catch (UnknownHostException unknownhostexception) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    logger.error("Couldn't connect to server", (Throwable)unknownhostexception);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", "Unknown host")));
                }
                catch (Exception exception) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    logger.error("Couldn't connect to server", (Throwable)exception);
                    String s = exception.toString();
                    if (inetaddress != null) {
                        String s1 = inetaddress.toString() + ":" + port;
                        s = s.replaceAll(s1, "");
                    }
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", s)));
                }
            }
        }.start();
    }

    @Override
    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            } else {
                this.networkManager.checkDisconnected();
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add((Object)new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.cancel = true;
            if (this.networkManager != null) {
                this.networkManager.closeChannel(new ChatComponentText("Aborted"));
            }
            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String s2;
        this.drawDefaultBackground();
        int var4 = this.height / 4 + 120 + 12;
        Gui.drawGradientRect(this.width / 2 - 100, var4 - 130, this.width / 2 + 100, var4 - 20, -16777216, -872415232);
        switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
            default: {
                s2 = "\u00a77_";
                break;
            }
            case 1: 
            case 3: {
                s2 = "";
                break;
            }
            case 2: {
                s2 = "\u00a77_";
            }
        }
        int yPos = var4 - 125;
        for (String connectingProgress : EaSyClient.INSTANCE.connectingProgress) {
            this.drawString(this.fontRendererObj, connectingProgress, this.width / 2 - 95 - 1, yPos, Color.GREEN.darker().getRGB());
            yPos += 10;
        }
        this.drawString(this.fontRendererObj, s2, this.width / 2 - 95 - 1, yPos, Color.GREEN.darker().getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    static /* synthetic */ Minecraft access$200(GuiConnecting x0) {
        return x0.mc;
    }

}

