/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.HashBiMap
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  io.netty.util.internal.ThreadLocalRandom
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Class
 *  java.lang.Error
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.net.MalformedURLException
 *  java.net.URL
 *  java.net.URLConnection
 *  java.net.URLStreamHandler
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Random
 *  java.util.Set
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.Marker
 *  org.apache.logging.log4j.MarkerManager
 *  paulscode.sound.Library
 *  paulscode.sound.SoundSystem
 *  paulscode.sound.SoundSystemConfig
 *  paulscode.sound.SoundSystemException
 *  paulscode.sound.SoundSystemLogger
 *  paulscode.sound.Source
 *  paulscode.sound.codecs.CodecJOrbis
 *  paulscode.sound.libraries.LibraryLWJGLOpenAL
 */
package net.minecraft.client.audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.Source;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
    private static final Marker LOG_MARKER = MarkerManager.getMarker((String)"SOUNDS");
    private static final Logger logger = LogManager.getLogger();
    private final SoundHandler sndHandler;
    private final GameSettings options;
    private SoundSystemStarterThread sndSystem;
    private boolean loaded;
    private int playTime = 0;
    private final Map<String, ISound> playingSounds = HashBiMap.create();
    private final Map<ISound, String> invPlayingSounds = ((BiMap)this.playingSounds).inverse();
    private Map<ISound, SoundPoolEntry> playingSoundPoolEntries = Maps.newHashMap();
    private final Multimap<SoundCategory, String> categorySounds = HashMultimap.create();
    private final List<ITickableSound> tickableSounds = Lists.newArrayList();
    private final Map<ISound, Integer> delayedSounds = Maps.newHashMap();
    private final Map<String, Integer> playingSoundsStopTime = Maps.newHashMap();

    public SoundManager(SoundHandler p_i45119_1_, GameSettings p_i45119_2_) {
        this.sndHandler = p_i45119_1_;
        this.options = p_i45119_2_;
        try {
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec((String)"ogg", CodecJOrbis.class);
        }
        catch (SoundSystemException soundsystemexception) {
            logger.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", (Throwable)soundsystemexception);
        }
    }

    public void reloadSoundSystem() {
        this.unloadSoundSystem();
        this.loadSoundSystem();
    }

    private synchronized void loadSoundSystem() {
        if (!this.loaded) {
            try {
                new Thread(new Runnable(){

                    public void run() {
                        SoundSystemConfig.setLogger((SoundSystemLogger)new SoundSystemLogger(){

                            public void message(String p_message_1_, int p_message_2_) {
                                if (!p_message_1_.isEmpty()) {
                                    logger.info(p_message_1_);
                                }
                            }

                            public void importantMessage(String p_importantMessage_1_, int p_importantMessage_2_) {
                                if (!p_importantMessage_1_.isEmpty()) {
                                    logger.warn(p_importantMessage_1_);
                                }
                            }

                            public void errorMessage(String p_errorMessage_1_, String p_errorMessage_2_, int p_errorMessage_3_) {
                                if (!p_errorMessage_2_.isEmpty()) {
                                    logger.error("Error in class '" + p_errorMessage_1_ + "'");
                                    logger.error(p_errorMessage_2_);
                                }
                            }
                        });
                        SoundManager soundManager = SoundManager.this;
                        soundManager.getClass();
                        SoundManager.this.sndSystem = soundManager.new SoundSystemStarterThread();
                        SoundManager.this.loaded = true;
                        SoundManager.this.sndSystem.setMasterVolume(SoundManager.this.options.getSoundLevel(SoundCategory.MASTER));
                        logger.info(LOG_MARKER, "Sound engine started");
                    }

                }, "Sound Library Loader").start();
            }
            catch (RuntimeException runtimeexception) {
                logger.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", (Throwable)runtimeexception);
                this.options.setSoundLevel(SoundCategory.MASTER, 0.0f);
                this.options.saveOptions();
            }
        }
    }

    private float getSoundCategoryVolume(SoundCategory category) {
        return category != null && category != SoundCategory.MASTER ? this.options.getSoundLevel(category) : 1.0f;
    }

    public void setSoundCategoryVolume(SoundCategory category, float volume) {
        if (this.loaded) {
            if (category == SoundCategory.MASTER) {
                this.sndSystem.setMasterVolume(volume);
            } else {
                for (String s : this.categorySounds.get((Object)category)) {
                    ISound isound = (ISound)this.playingSounds.get((Object)s);
                    float f = this.getNormalizedVolume(isound, (SoundPoolEntry)this.playingSoundPoolEntries.get((Object)isound), category);
                    if (f <= 0.0f) {
                        this.stopSound(isound);
                        continue;
                    }
                    this.sndSystem.setVolume(s, f);
                }
            }
        }
    }

    public void unloadSoundSystem() {
        if (this.loaded) {
            this.stopAllSounds();
            this.sndSystem.cleanup();
            this.loaded = false;
        }
    }

    public void stopAllSounds() {
        if (this.loaded) {
            for (String s : this.playingSounds.keySet()) {
                this.sndSystem.stop(s);
            }
            this.playingSounds.clear();
            this.delayedSounds.clear();
            this.tickableSounds.clear();
            this.categorySounds.clear();
            this.playingSoundPoolEntries.clear();
            this.playingSoundsStopTime.clear();
        }
    }

    public void updateAllSounds() {
        ++this.playTime;
        for (ITickableSound itickablesound : this.tickableSounds) {
            itickablesound.update();
            if (itickablesound.isDonePlaying()) {
                this.stopSound(itickablesound);
                continue;
            }
            String s = (String)this.invPlayingSounds.get((Object)itickablesound);
            this.sndSystem.setVolume(s, this.getNormalizedVolume(itickablesound, (SoundPoolEntry)this.playingSoundPoolEntries.get((Object)itickablesound), this.sndHandler.getSound(itickablesound.getSoundLocation()).getSoundCategory()));
            this.sndSystem.setPitch(s, this.getNormalizedPitch(itickablesound, (SoundPoolEntry)this.playingSoundPoolEntries.get((Object)itickablesound)));
            this.sndSystem.setPosition(s, itickablesound.getXPosF(), itickablesound.getYPosF(), itickablesound.getZPosF());
        }
        Iterator iterator = this.playingSounds.entrySet().iterator();
        while (iterator.hasNext()) {
            int i;
            Map.Entry entry = (Map.Entry)iterator.next();
            String s1 = (String)entry.getKey();
            ISound isound = (ISound)entry.getValue();
            if (this.sndSystem.playing(s1) || (i = ((Integer)this.playingSoundsStopTime.get((Object)s1)).intValue()) > this.playTime) continue;
            int j = isound.getRepeatDelay();
            if (isound.canRepeat() && j > 0) {
                this.delayedSounds.put((Object)isound, (Object)(this.playTime + j));
            }
            iterator.remove();
            logger.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", new Object[]{s1});
            this.sndSystem.removeSource(s1);
            this.playingSoundsStopTime.remove((Object)s1);
            this.playingSoundPoolEntries.remove((Object)isound);
            try {
                this.categorySounds.remove((Object)this.sndHandler.getSound(isound.getSoundLocation()).getSoundCategory(), (Object)s1);
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
            if (!(isound instanceof ITickableSound)) continue;
            this.tickableSounds.remove((Object)isound);
        }
        Iterator iterator1 = this.delayedSounds.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry entry1 = (Map.Entry)iterator1.next();
            if (this.playTime < (Integer)entry1.getValue()) continue;
            ISound isound1 = (ISound)entry1.getKey();
            if (isound1 instanceof ITickableSound) {
                ((ITickableSound)isound1).update();
            }
            this.playSound(isound1);
            iterator1.remove();
        }
    }

    public boolean isSoundPlaying(ISound sound) {
        if (!this.loaded) {
            return false;
        }
        String s = (String)this.invPlayingSounds.get((Object)sound);
        if (s == null) {
            return false;
        }
        return this.sndSystem.playing(s) || this.playingSoundsStopTime.containsKey((Object)s) && (Integer)this.playingSoundsStopTime.get((Object)s) <= this.playTime;
    }

    public void stopSound(ISound sound) {
        String s;
        if (this.loaded && (s = (String)this.invPlayingSounds.get((Object)sound)) != null) {
            this.sndSystem.stop(s);
        }
    }

    public void playSound(ISound p_sound) {
        if (this.loaded) {
            if (this.sndSystem.getMasterVolume() <= 0.0f) {
                logger.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", new Object[]{p_sound.getSoundLocation()});
            } else {
                SoundEventAccessorComposite soundeventaccessorcomposite = this.sndHandler.getSound(p_sound.getSoundLocation());
                if (soundeventaccessorcomposite == null) {
                    logger.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", new Object[]{p_sound.getSoundLocation()});
                } else {
                    SoundPoolEntry soundpoolentry = soundeventaccessorcomposite.cloneEntry();
                    if (soundpoolentry == SoundHandler.missing_sound) {
                        logger.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", new Object[]{soundeventaccessorcomposite.getSoundEventLocation()});
                    } else {
                        float f = p_sound.getVolume();
                        float f1 = 16.0f;
                        if (f > 1.0f) {
                            f1 *= f;
                        }
                        SoundCategory soundcategory = soundeventaccessorcomposite.getSoundCategory();
                        float f2 = this.getNormalizedVolume(p_sound, soundpoolentry, soundcategory);
                        double d0 = this.getNormalizedPitch(p_sound, soundpoolentry);
                        ResourceLocation resourcelocation = soundpoolentry.getSoundPoolEntryLocation();
                        if (f2 == 0.0f) {
                            logger.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", new Object[]{resourcelocation});
                        } else {
                            boolean flag = p_sound.canRepeat() && p_sound.getRepeatDelay() == 0;
                            String s = MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()).toString();
                            if (soundpoolentry.isStreamingSound()) {
                                this.sndSystem.newStreamingSource(false, s, SoundManager.getURLForSoundResource(resourcelocation), resourcelocation.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f1);
                            } else {
                                this.sndSystem.newSource(false, s, SoundManager.getURLForSoundResource(resourcelocation), resourcelocation.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f1);
                            }
                            logger.debug(LOG_MARKER, "Playing sound {} for event {} as channel {}", new Object[]{soundpoolentry.getSoundPoolEntryLocation(), soundeventaccessorcomposite.getSoundEventLocation(), s});
                            this.sndSystem.setPitch(s, (float)d0);
                            this.sndSystem.setVolume(s, f2);
                            this.sndSystem.play(s);
                            this.playingSoundsStopTime.put((Object)s, (Object)(this.playTime + 20));
                            this.playingSounds.put((Object)s, (Object)p_sound);
                            this.playingSoundPoolEntries.put((Object)p_sound, (Object)soundpoolentry);
                            if (soundcategory != SoundCategory.MASTER) {
                                this.categorySounds.put((Object)soundcategory, (Object)s);
                            }
                            if (p_sound instanceof ITickableSound) {
                                this.tickableSounds.add((Object)((ITickableSound)p_sound));
                            }
                        }
                    }
                }
            }
        }
    }

    private float getNormalizedPitch(ISound sound, SoundPoolEntry entry) {
        return (float)MathHelper.clamp_double((double)sound.getPitch() * entry.getPitch(), 0.5, 2.0);
    }

    private float getNormalizedVolume(ISound sound, SoundPoolEntry entry, SoundCategory category) {
        return (float)MathHelper.clamp_double((double)sound.getVolume() * entry.getVolume(), 0.0, 1.0) * this.getSoundCategoryVolume(category);
    }

    public void pauseAllSounds() {
        for (String s : this.playingSounds.keySet()) {
            logger.debug(LOG_MARKER, "Pausing channel {}", new Object[]{s});
            this.sndSystem.pause(s);
        }
    }

    public void resumeAllSounds() {
        for (String s : this.playingSounds.keySet()) {
            logger.debug(LOG_MARKER, "Resuming channel {}", new Object[]{s});
            this.sndSystem.play(s);
        }
    }

    public void playDelayedSound(ISound sound, int delay) {
        this.delayedSounds.put((Object)sound, (Object)(this.playTime + delay));
    }

    private static URL getURLForSoundResource(final ResourceLocation p_148612_0_) {
        String s = String.format((String)"%s:%s:%s", (Object[])new Object[]{"mcsounddomain", p_148612_0_.getResourceDomain(), p_148612_0_.getResourcePath()});
        URLStreamHandler urlstreamhandler = new URLStreamHandler(){

            protected URLConnection openConnection(URL p_openConnection_1_) {
                return new URLConnection(p_openConnection_1_){

                    public void connect() throws IOException {
                    }

                    public InputStream getInputStream() throws IOException {
                        return Minecraft.getMinecraft().getResourceManager().getResource(p_148612_0_).getInputStream();
                    }
                };
            }

        };
        try {
            return new URL((URL)null, s, urlstreamhandler);
        }
        catch (MalformedURLException var4) {
            throw new Error("TODO: Sanely handle url exception! :D");
        }
    }

    public void setListener(EntityPlayer player, float p_148615_2_) {
        if (this.loaded && player != null) {
            float f = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * p_148615_2_;
            float f1 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * p_148615_2_;
            double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)p_148615_2_;
            double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)p_148615_2_ + (double)player.getEyeHeight();
            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)p_148615_2_;
            float f2 = MathHelper.cos((f1 + 90.0f) * 0.017453292f);
            float f3 = MathHelper.sin((f1 + 90.0f) * 0.017453292f);
            float f4 = MathHelper.cos(-f * 0.017453292f);
            float f5 = MathHelper.sin(-f * 0.017453292f);
            float f6 = MathHelper.cos((-f + 90.0f) * 0.017453292f);
            float f7 = MathHelper.sin((-f + 90.0f) * 0.017453292f);
            float f8 = f2 * f4;
            float f9 = f3 * f4;
            float f10 = f2 * f6;
            float f11 = f3 * f6;
            this.sndSystem.setListenerPosition((float)d0, (float)d1, (float)d2);
            this.sndSystem.setListenerOrientation(f8, f5, f9, f10, f7, f11);
        }
    }

    class SoundSystemStarterThread
    extends SoundSystem {
        private SoundSystemStarterThread() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean playing(String p_playing_1_) {
            Object object = SoundSystemConfig.THREAD_SYNC;
            synchronized (object) {
                if (this.soundLibrary == null) {
                    return false;
                }
                Source source = (Source)this.soundLibrary.getSources().get((Object)p_playing_1_);
                if (source == null) {
                    return false;
                }
                return source.playing() || source.paused() || source.preLoad;
            }
        }
    }

}

