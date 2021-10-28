/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.SortedSet
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

public class GuiLanguage
extends GuiScreen {
    protected GuiScreen parentScreen;
    private List list;
    private final GameSettings game_settings_3;
    private final LanguageManager languageManager;
    private GuiOptionButton forceUnicodeFontBtn;
    private GuiOptionButton confirmSettingsBtn;

    public GuiLanguage(GuiScreen screen, GameSettings gameSettingsObj, LanguageManager manager) {
        this.parentScreen = screen;
        this.game_settings_3 = gameSettingsObj;
        this.languageManager = manager;
    }

    @Override
    public void initGui() {
        this.forceUnicodeFontBtn = new GuiOptionButton(100, this.width / 2 - 155, this.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT));
        this.buttonList.add((Object)this.forceUnicodeFontBtn);
        this.confirmSettingsBtn = new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done", new Object[0]));
        this.buttonList.add((Object)this.confirmSettingsBtn);
        this.list = new List(this.mc);
        this.list.registerScrollButtons(7, 8);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            switch (button.id) {
                case 5: {
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.parentScreen);
                    break;
                }
                case 100: {
                    if (!(button instanceof GuiOptionButton)) break;
                    this.game_settings_3.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                    button.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                    ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                    int i = scaledresolution.getScaledWidth();
                    int j = scaledresolution.getScaledHeight();
                    this.setWorldAndResolution(this.mc, i, j);
                    break;
                }
                default: {
                    this.list.actionPerformed(button);
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        GuiLanguage.drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), this.width / 2, 16, 16777215);
        GuiLanguage.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", this.width / 2, this.height - 56, 8421504);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    static /* synthetic */ GuiOptionButton access$200(GuiLanguage x0) {
        return x0.confirmSettingsBtn;
    }

    static /* synthetic */ GuiOptionButton access$300(GuiLanguage x0) {
        return x0.forceUnicodeFontBtn;
    }

    class List
    extends GuiSlot {
        private final java.util.List<String> langCodeList;
        private final Map<String, Language> languageMap;

        public List(Minecraft mcIn) {
            super(mcIn, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);
            this.langCodeList = Lists.newArrayList();
            this.languageMap = Maps.newHashMap();
            for (Language language : GuiLanguage.this.languageManager.getLanguages()) {
                this.languageMap.put((Object)language.getLanguageCode(), (Object)language);
                this.langCodeList.add((Object)language.getLanguageCode());
            }
        }

        @Override
        protected int getSize() {
            return this.langCodeList.size();
        }

        @Override
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            Language language = (Language)this.languageMap.get(this.langCodeList.get(slotIndex));
            GuiLanguage.this.languageManager.setCurrentLanguage(language);
            GuiLanguage.access$100((GuiLanguage)GuiLanguage.this).language = language.getLanguageCode();
            this.mc.refreshResources();
            GuiLanguage.this.fontRendererObj.setUnicodeFlag(GuiLanguage.this.languageManager.isCurrentLocaleUnicode() || GuiLanguage.access$100((GuiLanguage)GuiLanguage.this).forceUnicodeFont);
            GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
            GuiLanguage.access$200((GuiLanguage)GuiLanguage.this).displayString = I18n.format("gui.done", new Object[0]);
            GuiLanguage.access$300((GuiLanguage)GuiLanguage.this).displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
            GuiLanguage.this.game_settings_3.saveOptions();
        }

        @Override
        protected boolean isSelected(int slotIndex) {
            return ((String)this.langCodeList.get(slotIndex)).equals((Object)GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
        }

        @Override
        protected int getContentHeight() {
            return this.getSize() * 18;
        }

        @Override
        protected void drawBackground() {
            GuiLanguage.this.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
            GuiLanguage.this.fontRendererObj.setBidiFlag(true);
            GuiLanguage.drawCenteredString(GuiLanguage.this.fontRendererObj, ((Language)this.languageMap.get(this.langCodeList.get(entryID))).toString(), this.width / 2, p_180791_3_ + 1, 16777215);
            GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
        }
    }

}

