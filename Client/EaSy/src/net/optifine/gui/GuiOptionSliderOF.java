/*
 * Decompiled with CFR 0.0.
 */
package net.optifine.gui;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;
import net.optifine.gui.IOptionControl;

public class GuiOptionSliderOF
extends GuiOptionSlider
implements IOptionControl {
    private GameSettings.Options option = null;

    public GuiOptionSliderOF(int id, int x, int y, GameSettings.Options option) {
        super(id, x, y, option);
        this.option = option;
    }

    @Override
    public GameSettings.Options getOption() {
        return this.option;
    }
}

