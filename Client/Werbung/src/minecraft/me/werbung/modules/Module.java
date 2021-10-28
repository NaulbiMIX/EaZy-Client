/*
 * Decompiled with CFR 0.145.
 */
package me.werbung.modules;

import me.werbung.modules.Category;
import net.minecraft.client.Minecraft;

public class Module {
    private String name;
    private String displayname;
    private Category category;
    private boolean toggled;
    private int keyBind;
    public static boolean colormode = false;
    public boolean visible;
    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String displayname, int keyBind, Category category) {
        this.name = name;
        this.displayname = displayname;
        this.category = category;
        this.keyBind = keyBind;
        this.visible = true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isCategory(Category category) {
        return this.category == category;
    }

    public int getKeyBind() {
        return this.keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isEnabled() {
        return this.toggled;
    }

    public void toggle() {
        if (this.toggled) {
            this.toggled = false;
            this.onDisable();
        } else {
            this.toggled = true;
            this.onEnable();
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}

