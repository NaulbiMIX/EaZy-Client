/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList
extends GuiListExtended {
    private final GuiControls field_148191_k;
    private final Minecraft mc;
    private final GuiListExtended.IGuiListEntry[] listEntries;
    private int maxListLabelWidth = 0;

    public GuiKeyBindingList(GuiControls controls, Minecraft mcIn) {
        super(mcIn, controls.width, controls.height, 63, controls.height - 32, 20);
        this.field_148191_k = controls;
        this.mc = mcIn;
        KeyBinding[] akeybinding = (KeyBinding[])ArrayUtils.clone((Object[])mcIn.gameSettings.keyBindings);
        this.listEntries = new GuiListExtended.IGuiListEntry[akeybinding.length + KeyBinding.getKeybinds().size()];
        Arrays.sort((Object[])akeybinding);
        int i = 0;
        String s = null;
        for (KeyBinding keybinding : akeybinding) {
            int j;
            String s1 = keybinding.getKeyCategory();
            if (!s1.equals(s)) {
                s = s1;
                this.listEntries[i++] = new CategoryEntry(s1);
            }
            if ((j = mcIn.fontRendererObj.getStringWidth(I18n.format(keybinding.getKeyDescription(), new Object[0]))) > this.maxListLabelWidth) {
                this.maxListLabelWidth = j;
            }
            this.listEntries[i++] = new KeyEntry(keybinding);
        }
    }

    @Override
    protected int getSize() {
        return this.listEntries.length;
    }

    @Override
    public GuiListExtended.IGuiListEntry getListEntry(int index) {
        return this.listEntries[index];
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }

    @Override
    public int getListWidth() {
        return super.getListWidth() + 32;
    }

    static /* synthetic */ GuiControls access$200(GuiKeyBindingList x0) {
        return x0.field_148191_k;
    }

    public class KeyEntry
    implements GuiListExtended.IGuiListEntry {
        private final KeyBinding keybinding;
        private final String keyDesc;
        private final GuiButton btnChangeKeyBinding;
        private final GuiButton btnReset;

        private KeyEntry(KeyBinding p_i45029_2_) {
            this.keybinding = p_i45029_2_;
            this.keyDesc = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
            this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
            this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            boolean flag = GuiKeyBindingList.access$200((GuiKeyBindingList)GuiKeyBindingList.this).buttonId == this.keybinding;
            GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).fontRendererObj.drawString(this.keyDesc, x + 90 - GuiKeyBindingList.this.maxListLabelWidth, y + slotHeight / 2 - GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).fontRendererObj.FONT_HEIGHT / 2, 16777215);
            this.btnReset.xPosition = x + 190;
            this.btnReset.yPosition = y;
            this.btnReset.enabled = this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault();
            this.btnReset.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
            this.btnChangeKeyBinding.xPosition = x + 105;
            this.btnChangeKeyBinding.yPosition = y;
            this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
            boolean flag1 = false;
            if (this.keybinding.getKeyCode() != 0) {
                for (KeyBinding keybinding : GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).gameSettings.keyBindings) {
                    if (keybinding == this.keybinding || keybinding.getKeyCode() != this.keybinding.getKeyCode()) continue;
                    flag1 = true;
                    break;
                }
            }
            if (flag) {
                this.btnChangeKeyBinding.displayString = (Object)((Object)EnumChatFormatting.WHITE) + "> " + (Object)((Object)EnumChatFormatting.YELLOW) + this.btnChangeKeyBinding.displayString + (Object)((Object)EnumChatFormatting.WHITE) + " <";
            } else if (flag1) {
                this.btnChangeKeyBinding.displayString = (Object)((Object)EnumChatFormatting.RED) + this.btnChangeKeyBinding.displayString;
            }
            this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
        }

        @Override
        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
                GuiKeyBindingList.access$200((GuiKeyBindingList)GuiKeyBindingList.this).buttonId = this.keybinding;
                return true;
            }
            if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
                GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
                KeyBinding.resetKeyBindingArrayAndHash();
                return true;
            }
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
            this.btnChangeKeyBinding.mouseReleased(x, y);
            this.btnReset.mouseReleased(x, y);
        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
        }
    }

    public class CategoryEntry
    implements GuiListExtended.IGuiListEntry {
        private final String labelText;
        private final int labelWidth;

        public CategoryEntry(String p_i45028_2_) {
            this.labelText = I18n.format(p_i45028_2_, new Object[0]);
            this.labelWidth = GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).fontRendererObj.getStringWidth(this.labelText);
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).fontRendererObj.drawString(this.labelText, GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).currentScreen.width / 2 - this.labelWidth / 2, y + slotHeight - GuiKeyBindingList.access$100((GuiKeyBindingList)GuiKeyBindingList.this).fontRendererObj.FONT_HEIGHT - 1, 16777215);
        }

        @Override
        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
        }
    }

}

