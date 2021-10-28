/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  java.io.BufferedReader
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.Class
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Method
 *  java.net.URI
 *  java.nio.charset.Charset
 *  java.util.ArrayList
 *  java.util.Calendar
 *  java.util.Date
 *  java.util.List
 *  java.util.Random
 *  java.util.concurrent.atomic.AtomicInteger
 *  org.apache.commons.io.Charsets
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.GLContext
 */
package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import de.flori2007.easyclient.EaSyClient;
import de.flori2007.easyclient.gui.GuiAccount;
import de.flori2007.easyclient.gui.GuiBuyPremium;
import de.flori2007.easyclient.gui.elements.HudRenderer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorConstructor;
import net.optifine.reflect.ReflectorMethod;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public class GuiMainMenu
extends GuiScreen
implements GuiYesNoCallback {
    public static final String field_96138_a = "Please click " + (Object)((Object)EnumChatFormatting.UNDERLINE) + "here" + (Object)((Object)EnumChatFormatting.RESET) + " for more information.";
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    private final Object threadLock = new Object();
    private final float updateCounter;
    private String splashText = "missingno";
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private final boolean field_175375_v = true;
    private String openGLWarning1;
    private String openGLWarning2 = field_96138_a;
    private String openGLWarningLink;
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    private GuiButton realmsButton;
    private boolean field_183502_L = false;
    private GuiScreen field_183503_M;
    private GuiButton modButton;
    private GuiScreen modUpdateNotification;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GuiMainMenu() {
        BufferedReader bufferedreader = null;
        try {
            String s;
            ArrayList list = Lists.newArrayList();
            bufferedreader = new BufferedReader((Reader)new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            while ((s = bufferedreader.readLine()) != null) {
                if ((s = s.trim()).isEmpty()) continue;
                list.add((Object)s);
            }
            if (!list.isEmpty()) {
                do {
                    this.splashText = (String)list.get(RANDOM.nextInt(list.size()));
                } while (this.splashText.hashCode() == 125780783);
            }
        }
        catch (IOException list) {
        }
        finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                }
                catch (IOException list) {}
            }
        }
        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    private boolean func_183501_a() {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.field_183503_M != null;
    }

    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
        if (this.func_183501_a()) {
            this.field_183503_M.updateScreen();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        int i = 24;
        int j = this.height / 4 + 48;
        if (this.mc.isDemo()) {
            this.addDemoButtons(j, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }
        this.buttonList.add((Object)new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add((Object)new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add((Object)new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12));
        Object object = this.threadLock;
        synchronized (object) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max((int)this.field_92023_s, (int)this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get((int)0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }
        this.mc.setConnectedToRealms(false);
        if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.field_183502_L) {
            RealmsBridge realmsbridge = new RealmsBridge();
            this.field_183503_M = realmsbridge.getNotificationScreen(this);
            this.field_183502_L = true;
        }
        if (this.func_183501_a()) {
            this.field_183503_M.setGuiSize(this.width, this.height);
            this.field_183503_M.initGui();
        }
    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        this.buttonList.add((Object)new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add((Object)new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add((Object)new GuiButton(1510, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, "\u00a7cBuy Premium Version"));
        this.buttonList.add((Object)new GuiButton(1511, this.width / 2 + 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, "Account Login"));
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add((Object)new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0]));
        this.buttonList.add((Object)this.buttonResetDemo);
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
        if (worldinfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        ISaveFormat isaveformat;
        WorldInfo worldinfo;
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 14 && this.realmsButton.visible) {
            this.switchToRealms();
        }
        if (button.id == 4) {
            this.mc.shutdown();
        }
        if (button.id == 6 && Reflector.GuiModList_Constructor.exists()) {
            this.mc.displayGuiScreen((GuiScreen)Reflector.newInstance(Reflector.GuiModList_Constructor, this));
        }
        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (button.id == 12 && (worldinfo = (isaveformat = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
            GuiYesNo guiyesno = GuiSelectWorld.makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
            this.mc.displayGuiScreen(guiyesno);
        }
        if (button.id == 1510) {
            this.mc.displayGuiScreen(new GuiBuyPremium());
        }
        if (button.id == 1511) {
            this.mc.displayGuiScreen(new GuiAccount());
        }
    }

    private void switchToRealms() {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class oclass = Class.forName((String)"java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{new URI(this.openGLWarningLink)});
                }
                catch (Throwable throwable1) {
                    logger.error("Couldn't open link", throwable1);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        EaSyClient.INSTANCE.renderBackground();
        Minecraft.getMinecraft().getTextureManager().bindTexture(EaSyClient.INSTANCE.getHudRenderer().LOGO);
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 64, 20, 0.0f, 0.0f, 128, 50, 128.0f, 50.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2 + 90, 70.0f, 0.0f);
        GlStateManager.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
        float f = 1.8f - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0f * 3.1415927f * 2.0f) * 0.1f);
        f = f * 100.0f / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
        GlStateManager.scale(f, f, f);
        GuiMainMenu.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
        GlStateManager.popMatrix();
        String s = "Minecraft 1.8.9";
        if (this.mc.isDemo()) {
            s = s + " Demo";
        }
        if (Reflector.FMLCommonHandler_getBrandings.exists()) {
            Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            List list = Lists.reverse((List)((List)Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, true)));
            for (int l1 = 0; l1 < list.size(); ++l1) {
                String s1 = (String)list.get(l1);
                if (Strings.isNullOrEmpty((String)s1)) continue;
                this.drawString(this.fontRendererObj, s1, 2, this.height - (10 + l1 * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
            }
            if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
                Reflector.call(Reflector.ForgeHooksClient_renderMainMenu, this, this.fontRendererObj, this.width, this.height);
            }
        } else {
            this.drawString(this.fontRendererObj, s, 2, this.height - 10, -1);
        }
        String s2 = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, s2, this.width - this.fontRendererObj.getStringWidth(s2) - 2, this.height - 10, -1);
        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            GuiMainMenu.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get((int)0)).yPosition - 12, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.func_183501_a()) {
            this.field_183503_M.drawScreen(mouseX, mouseY, partialTicks);
        }
        if (this.modUpdateNotification != null) {
            this.modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object object = this.threadLock;
        synchronized (object) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
        if (this.func_183501_a()) {
            this.field_183503_M.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.field_183503_M != null) {
            this.field_183503_M.onGuiClosed();
        }
    }
}

