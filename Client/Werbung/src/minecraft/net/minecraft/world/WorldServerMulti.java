/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

public class WorldServerMulti
extends WorldServer {
    private WorldServer delegate;
    private static final String __OBFID = "CL_00001430";

    public WorldServerMulti(MinecraftServer server, ISaveHandler saveHandlerIn, int dimensionId, WorldServer delegate, Profiler profilerIn) {
        super(server, saveHandlerIn, new DerivedWorldInfo(delegate.getWorldInfo()), dimensionId, profilerIn);
        this.delegate = delegate;
        delegate.getWorldBorder().addListener(new IBorderListener(){
            private static final String __OBFID = "CL_00002273";

            @Override
            public void onSizeChanged(WorldBorder border, double newSize) {
                WorldServerMulti.this.getWorldBorder().setTransition(newSize);
            }

            @Override
            public void func_177692_a(WorldBorder border, double p_177692_2_, double p_177692_4_, long p_177692_6_) {
                WorldServerMulti.this.getWorldBorder().setTransition(p_177692_2_, p_177692_4_, p_177692_6_);
            }

            @Override
            public void onCenterChanged(WorldBorder border, double x, double z) {
                WorldServerMulti.this.getWorldBorder().setCenter(x, z);
            }

            @Override
            public void onWarningTimeChanged(WorldBorder border, int p_177691_2_) {
                WorldServerMulti.this.getWorldBorder().setWarningTime(p_177691_2_);
            }

            @Override
            public void onWarningDistanceChanged(WorldBorder border, int p_177690_2_) {
                WorldServerMulti.this.getWorldBorder().setWarningDistance(p_177690_2_);
            }

            @Override
            public void func_177696_b(WorldBorder border, double p_177696_2_) {
                WorldServerMulti.this.getWorldBorder().func_177744_c(p_177696_2_);
            }

            @Override
            public void func_177695_c(WorldBorder border, double p_177695_2_) {
                WorldServerMulti.this.getWorldBorder().setDamageBuffer(p_177695_2_);
            }
        });
    }

    @Override
    protected void saveLevel() throws MinecraftException {
    }

    @Override
    public World init() {
        this.mapStorage = this.delegate.func_175693_T();
        this.worldScoreboard = this.delegate.getScoreboard();
        String var1 = VillageCollection.func_176062_a(this.provider);
        VillageCollection var2 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, var1);
        if (var2 == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(var1, this.villageCollectionObj);
        } else {
            this.villageCollectionObj = var2;
            this.villageCollectionObj.func_82566_a(this);
        }
        return this;
    }

}
