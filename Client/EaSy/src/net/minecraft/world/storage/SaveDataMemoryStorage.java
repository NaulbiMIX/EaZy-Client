/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package net.minecraft.world.storage;

import java.util.Map;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;

public class SaveDataMemoryStorage
extends MapStorage {
    public SaveDataMemoryStorage() {
        super(null);
    }

    @Override
    public WorldSavedData loadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
        return (WorldSavedData)this.loadedDataMap.get((Object)dataIdentifier);
    }

    @Override
    public void setData(String dataIdentifier, WorldSavedData data) {
        this.loadedDataMap.put((Object)dataIdentifier, (Object)data);
    }

    @Override
    public void saveAllData() {
    }

    @Override
    public int getUniqueDataId(String key) {
        return 0;
    }
}

