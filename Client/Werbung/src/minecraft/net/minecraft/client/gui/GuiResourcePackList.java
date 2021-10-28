/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList
extends GuiListExtended {
    protected final Minecraft mc;
    protected final List field_148204_l;
    private static final String __OBFID = "CL_00000825";

    public GuiResourcePackList(Minecraft mcIn, int p_i45055_2_, int p_i45055_3_, List p_i45055_4_) {
        super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
        this.mc = mcIn;
        this.field_148204_l = p_i45055_4_;
        this.field_148163_i = false;
        this.setHasListHeader(true, (int)((float)mcIn.fontRendererObj.FONT_HEIGHT * 1.5f));
    }

    @Override
    protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
        String var4 = (Object)((Object)EnumChatFormatting.UNDERLINE) + (Object)((Object)EnumChatFormatting.BOLD) + this.getListHeader();
        this.mc.fontRendererObj.drawString(var4, p_148129_1_ + this.width / 2 - this.mc.fontRendererObj.getStringWidth(var4) / 2, Math.min(this.top + 3, p_148129_2_), 16777215);
    }

    protected abstract String getListHeader();

    public List getList() {
        return this.field_148204_l;
    }

    @Override
    protected int getSize() {
        return this.getList().size();
    }

    @Override
    public ResourcePackListEntry getListEntry(int p_148180_1_) {
        return (ResourcePackListEntry)this.getList().get(p_148180_1_);
    }

    @Override
    public int getListWidth() {
        return this.width;
    }

    @Override
    protected int getScrollBarX() {
        return this.right - 6;
    }
}

