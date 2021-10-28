/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.Futures
 *  com.google.common.util.concurrent.ListenableFuture
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkSystem;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedPlayerList;
import net.minecraft.server.integrated.IntegratedServerCommandManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Session;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer
extends MinecraftServer {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;
    private static final String __OBFID = "CL_00001129";

    public IntegratedServer(Minecraft mcIn) {
        super(mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
        this.mc = mcIn;
        this.theWorldSettings = null;
    }

    public IntegratedServer(Minecraft mcIn, String p_i1317_2_, String p_i1317_3_, WorldSettings p_i1317_4_) {
        super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
        this.setServerOwner(mcIn.getSession().getUsername());
        this.setFolderName(p_i1317_2_);
        this.setWorldName(p_i1317_3_);
        this.setDemo(mcIn.isDemo());
        this.canCreateBonusChest(p_i1317_4_.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigManager(new IntegratedPlayerList(this));
        this.mc = mcIn;
        this.theWorldSettings = this.isDemo() ? DemoWorldServer.demoWorldSettings : p_i1317_4_;
    }

    @Override
    protected ServerCommandManager createNewCommandManager() {
        return new IntegratedServerCommandManager();
    }

    @Override
    protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long seed, WorldType type, String p_71247_6_) {
        this.convertMapIfNeeded(p_71247_1_);
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
        this.setResourcePackFromWorld(this.getFolderName(), var7);
        WorldInfo var8 = var7.loadWorldInfo();
        if (var8 == null) {
            var8 = new WorldInfo(this.theWorldSettings, p_71247_2_);
        } else {
            var8.setWorldName(p_71247_2_);
        }
        for (int var9 = 0; var9 < this.worldServers.length; ++var9) {
            int var10 = 0;
            if (var9 == 1) {
                var10 = -1;
            }
            if (var9 == 2) {
                var10 = 1;
            }
            if (var9 == 0) {
                this.worldServers[var9] = this.isDemo() ? (WorldServer)new DemoWorldServer(this, var7, var8, var10, this.theProfiler).init() : (WorldServer)new WorldServer(this, var7, var8, var10, this.theProfiler).init();
                this.worldServers[var9].initialize(this.theWorldSettings);
            } else {
                this.worldServers[var9] = (WorldServer)new WorldServerMulti(this, var7, var10, this.worldServers[0], this.theProfiler).init();
            }
            this.worldServers[var9].addWorldAccess(new WorldManager(this, this.worldServers[var9]));
        }
        this.getConfigurationManager().setPlayerManager(this.worldServers);
        if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
            this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
        }
        this.initialWorldChunkLoad();
    }

    @Override
    protected boolean startServer() throws IOException {
        logger.info("Starting integrated minecraft server version 1.8");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        logger.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
        this.setMOTD(String.valueOf(this.getServerOwner()) + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void tick() {
        boolean var1 = this.isGamePaused;
        boolean bl = this.isGamePaused = Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused();
        if (!var1 && this.isGamePaused) {
            logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (this.isGamePaused) {
            Queue var2 = this.futureTaskQueue;
            Queue queue = this.futureTaskQueue;
            // MONITORENTER : queue
            do {
                do {
                    if (this.futureTaskQueue.isEmpty()) {
                        // MONITOREXIT : queue
                        return;
                    }
                    try {
                        ((FutureTask)this.futureTaskQueue.poll()).run();
                    }
                    catch (Throwable var8) {
                        logger.fatal((Object)var8);
                    }
                } while (true);
                break;
            } while (true);
            catch (Throwable throwable) {
                // MONITOREXIT : queue
                throw throwable;
            }
        }
        super.tick();
        if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
            logger.info("Changing view distance to {}, from {}", new Object[]{this.mc.gameSettings.renderDistanceChunks, this.getConfigurationManager().getViewDistance()});
            this.getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
        }
        if (this.mc.theWorld == null) return;
        WorldInfo var10 = this.worldServers[0].getWorldInfo();
        WorldInfo var3 = this.mc.theWorld.getWorldInfo();
        if (!var10.isDifficultyLocked() && var3.getDifficulty() != var10.getDifficulty()) {
            logger.info("Changing difficulty to {}, from {}", new Object[]{var3.getDifficulty(), var10.getDifficulty()});
            this.setDifficultyForAllWorlds(var3.getDifficulty());
            return;
        }
        if (!var3.isDifficultyLocked()) return;
        if (var10.isDifficultyLocked()) return;
        logger.info("Locking difficulty to {}", new Object[]{var3.getDifficulty()});
        for (WorldServer var7 : this.worldServers) {
            if (var7 == null) continue;
            var7.getWorldInfo().setDifficultyLocked(true);
        }
    }

    @Override
    public boolean canStructuresSpawn() {
        return false;
    }

    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }

    @Override
    public EnumDifficulty getDifficulty() {
        return this.mc.theWorld.getWorldInfo().getDifficulty();
    }

    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }

    @Override
    public File getDataDirectory() {
        return this.mc.mcDataDir;
    }

    @Override
    public boolean isDedicatedServer() {
        return false;
    }

    @Override
    protected void finalTick(CrashReport report) {
        this.mc.crashed(report);
    }

    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport report) {
        report = super.addServerInfoToCrashReport(report);
        report.getCategory().addCrashSectionCallable("Type", new Callable(){
            private static final String __OBFID = "CL_00001130";

            public String call() {
                return "Integrated Server (map_client.txt)";
            }
        });
        report.getCategory().addCrashSectionCallable("Is Modded", new Callable(){
            private static final String __OBFID = "CL_00001131";

            public String call() {
                String var1 = ClientBrandRetriever.getClientModName();
                if (!var1.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + var1 + "'";
                }
                var1 = IntegratedServer.this.getServerModName();
                return !var1.equals("vanilla") ? "Definitely; Server brand changed to '" + var1 + "'" : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
            }
        });
        return report;
    }

    @Override
    public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
        super.setDifficultyForAllWorlds(difficulty);
        if (this.mc.theWorld != null) {
            this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
        }
    }

    @Override
    public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
        super.addServerStatsToSnooper(playerSnooper);
        playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }

    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }

    @Override
    public String shareToLAN(WorldSettings.GameType type, boolean allowCheats) {
        try {
            int var3 = -1;
            try {
                var3 = HttpUtil.getSuitableLanPort();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (var3 <= 0) {
                var3 = 25564;
            }
            this.getNetworkSystem().addLanEndpoint(null, var3);
            logger.info("Started on " + var3);
            this.isPublic = true;
            this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), String.valueOf(var3));
            this.lanServerPing.start();
            this.getConfigurationManager().func_152604_a(type);
            this.getConfigurationManager().setCommandsAllowedForAll(allowCheats);
            return String.valueOf(var3);
        }
        catch (IOException var6) {
            return null;
        }
    }

    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    @Override
    public void initiateShutdown() {
        Futures.getUnchecked((Future)this.addScheduledTask(new Runnable(){
            private static final String __OBFID = "CL_00002380";

            @Override
            public void run() {
                ArrayList var1 = Lists.newArrayList((Iterable)IntegratedServer.this.getConfigurationManager().playerEntityList);
                for (EntityPlayerMP var3 : var1) {
                    IntegratedServer.this.getConfigurationManager().playerLoggedOut(var3);
                }
            }
        }));
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    public void func_175592_a() {
        this.func_175585_v();
    }

    public boolean getPublic() {
        return this.isPublic;
    }

    @Override
    public void setGameType(WorldSettings.GameType gameMode) {
        this.getConfigurationManager().func_152604_a(gameMode);
    }

    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }

    @Override
    public int getOpPermissionLevel() {
        return 4;
    }

}

