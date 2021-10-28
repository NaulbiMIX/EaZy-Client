/*
 * Decompiled with CFR 0.145.
 */
package me.werbung.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.werbung.modules.Module;
import me.werbung.modules.WerbungBizzi;
import me.werbung.modules.WerbungDurchrasten;
import me.werbung.modules.WerbungKyudo;
import me.werbung.modules.WerbungMinesucht;
import me.werbung.modules.WerbungRewi;
import me.werbung.modules.WerbungTimo;

public class ModuleManager {
    public List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        this.addModule(new WerbungRewi());
        this.addModule(new WerbungDurchrasten());
        this.addModule(new WerbungKyudo());
        this.addModule(new WerbungMinesucht());
        this.addModule(new WerbungTimo());
        this.addModule(new WerbungBizzi());
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public Module getModuleByName(String modulename) {
        for (Module m : this.modules) {
            if (!m.getName().trim().equalsIgnoreCase(modulename) && !m.toString().trim().equalsIgnoreCase(modulename.trim())) continue;
            return m;
        }
        return null;
    }

    public Module getModule(Class<? extends Module> clazz) {
        Iterator<Module> localIterator = this.modules.iterator();
        if (localIterator.hasNext()) {
            Module m = localIterator.next();
            return m;
        }
        return null;
    }
}

