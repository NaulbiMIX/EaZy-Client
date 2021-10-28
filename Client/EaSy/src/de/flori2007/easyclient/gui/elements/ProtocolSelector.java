/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package de.flori2007.easyclient.gui.elements;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class ProtocolSelector {
    public static final List<String> PROTOCOLS = new ArrayList();
    public int selected = 0;
    private final int x;
    private final int y;

    public ProtocolSelector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render() {
        Gui.drawRect(this.x, this.y, this.x + 195, this.y + 15, Integer.MIN_VALUE);
        Gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, (String)PROTOCOLS.get(this.selected), (this.x + 200) / 2, this.y + 4, -1);
        Gui.drawRect(this.x, this.y, this.x + 15, this.y + 15, Integer.MIN_VALUE);
        Minecraft.getMinecraft().fontRendererObj.drawString("\u00ab", this.x + 4, this.y + 4, -1);
        Gui.drawRect(this.x + 180, this.y, this.x + 195, this.y + 15, Integer.MIN_VALUE);
        Minecraft.getMinecraft().fontRendererObj.drawString("\u00bb", this.x + 185, this.y + 4, -1);
    }

    public void mouseClicked(int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseX <= this.x + 15 && mouseY >= this.y && mouseY <= this.y + 15 && this.selected > 0) {
            --this.selected;
        }
        if (mouseX >= this.x + 180 && mouseX <= this.x + 195 && mouseY >= this.y && mouseY <= this.y + 15 && this.selected < PROTOCOLS.size() - 1) {
            ++this.selected;
        }
    }

    static {
        PROTOCOLS.add((Object)"1.8.x");
        for (int i = 1; i < 11; ++i) {
            PROTOCOLS.add((Object)("1.8." + i));
        }
        PROTOCOLS.add((Object)"Fortnite b1337.000000000");
    }
}

