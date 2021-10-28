/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.CharSequence
 *  java.lang.IllegalStateException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Random
 *  java.util.Set
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundListSerializer;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler
implements IResourceManagerReloadListener,
ITickable {
    private static final Logger logger = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(SoundList.class, (Object)new SoundListSerializer()).create();
    private static final ParameterizedType TYPE = new ParameterizedType(){

        public Type[] getActualTypeArguments() {
            return new Type[]{String.class, SoundList.class};
        }

        public Type getRawType() {
            return Map.class;
        }

        public Type getOwnerType() {
            return null;
        }
    };
    public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
    private final SoundRegistry sndRegistry = new SoundRegistry();
    private final SoundManager sndManager;
    private final IResourceManager mcResourceManager;

    public SoundHandler(IResourceManager manager, GameSettings gameSettingsIn) {
        this.mcResourceManager = manager;
        this.sndManager = new SoundManager(this, gameSettingsIn);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        this.sndManager.reloadSoundSystem();
        this.sndRegistry.clearMap();
        for (String s : resourceManager.getResourceDomains()) {
            try {
                for (IResource iresource : resourceManager.getAllResources(new ResourceLocation(s, "sounds.json"))) {
                    try {
                        Map<String, SoundList> map = this.getSoundMap(iresource.getInputStream());
                        for (Map.Entry entry : map.entrySet()) {
                            this.loadSoundResource(new ResourceLocation(s, (String)entry.getKey()), (SoundList)entry.getValue());
                        }
                    }
                    catch (RuntimeException runtimeexception) {
                        logger.warn("Invalid sounds.json", (Throwable)runtimeexception);
                    }
                }
            }
            catch (IOException iOException) {
            }
        }
    }

    protected Map<String, SoundList> getSoundMap(InputStream stream) {
        Map map;
        try {
            map = (Map)GSON.fromJson((Reader)new InputStreamReader(stream), (Type)TYPE);
        }
        finally {
            IOUtils.closeQuietly((InputStream)stream);
        }
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadSoundResource(ResourceLocation location, SoundList sounds) {
        v0 = flag = this.sndRegistry.containsKey(location) == false;
        if (!flag && !sounds.canReplaceExisting()) {
            soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
        } else {
            if (!flag) {
                SoundHandler.logger.debug("Replaced sound event location {}", new Object[]{location});
            }
            soundeventaccessorcomposite = new SoundEventAccessorComposite(location, 1.0, 1.0, sounds.getSoundCategory());
            this.sndRegistry.registerSound(soundeventaccessorcomposite);
        }
        var5_5 = sounds.getSoundList().iterator();
        block10 : while (var5_5.hasNext() != false) {
            soundlist$soundentry = (SoundList.SoundEntry)var5_5.next();
            s = soundlist$soundentry.getSoundEntryName();
            resourcelocation = new ResourceLocation(s);
            s1 = s.contains((CharSequence)":") != false ? resourcelocation.getResourceDomain() : location.getResourceDomain();
            switch (3.$SwitchMap$net$minecraft$client$audio$SoundList$SoundEntry$Type[soundlist$soundentry.getSoundEntryType().ordinal()]) {
                case 1: {
                    resourcelocation1 = new ResourceLocation(s1, "sounds/" + resourcelocation.getResourcePath() + ".ogg");
                    inputstream = null;
                    try {
                        inputstream = this.mcResourceManager.getResource(resourcelocation1).getInputStream();
                    }
                    catch (FileNotFoundException var18) {
                        SoundHandler.logger.warn("File {} does not exist, cannot add it to event {}", new Object[]{resourcelocation1, location});
                        {
                            catch (Throwable var14_16) {
                                IOUtils.closeQuietly(inputstream);
                                throw var14_16;
                            }
                        }
                        IOUtils.closeQuietly((InputStream)inputstream);
                        continue block10;
                        catch (IOException ioexception) {
                            SoundHandler.logger.warn("Could not load sound file " + resourcelocation1 + ", cannot add it to event " + location, (Throwable)ioexception);
                            IOUtils.closeQuietly((InputStream)inputstream);
                            continue block10;
                        }
                    }
                    IOUtils.closeQuietly((InputStream)inputstream);
                    isoundeventaccessor /* !! */  = new SoundEventAccessor(new SoundPoolEntry(resourcelocation1, soundlist$soundentry.getSoundEntryPitch(), soundlist$soundentry.getSoundEntryVolume(), soundlist$soundentry.isStreaming()), soundlist$soundentry.getSoundEntryWeight());
                    ** break;
                }
                case 2: {
                    isoundeventaccessor /* !! */  = new ISoundEventAccessor<SoundPoolEntry>(){
                        final ResourceLocation field_148726_a;
                        {
                            this.field_148726_a = new ResourceLocation(s1, soundlist$soundentry.getSoundEntryName());
                        }

                        @Override
                        public int getWeight() {
                            SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
                            return soundeventaccessorcomposite1 == null ? 0 : soundeventaccessorcomposite1.getWeight();
                        }

                        @Override
                        public SoundPoolEntry cloneEntry() {
                            SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
                            return soundeventaccessorcomposite1 == null ? SoundHandler.missing_sound : soundeventaccessorcomposite1.cloneEntry();
                        }
                    };
                    ** break;
                }
            }
            throw new IllegalStateException("IN YOU FACE");
lbl41: // 2 sources:
            soundeventaccessorcomposite.addSoundToEventPool(isoundeventaccessor /* !! */ );
        }
    }

    public SoundEventAccessorComposite getSound(ResourceLocation location) {
        return (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
    }

    public void playSound(ISound sound) {
        this.sndManager.playSound(sound);
    }

    public void playDelayedSound(ISound sound, int delay) {
        this.sndManager.playDelayedSound(sound, delay);
    }

    public void setListener(EntityPlayer player, float p_147691_2_) {
        this.sndManager.setListener(player, p_147691_2_);
    }

    public void pauseSounds() {
        this.sndManager.pauseAllSounds();
    }

    public void stopSounds() {
        this.sndManager.stopAllSounds();
    }

    public void unloadSounds() {
        this.sndManager.unloadSoundSystem();
    }

    @Override
    public void update() {
        this.sndManager.updateAllSounds();
    }

    public void resumeSounds() {
        this.sndManager.resumeAllSounds();
    }

    public void setSoundLevel(SoundCategory category, float volume) {
        if (category == SoundCategory.MASTER && volume <= 0.0f) {
            this.stopSounds();
        }
        this.sndManager.setSoundCategoryVolume(category, volume);
    }

    public void stopSound(ISound p_147683_1_) {
        this.sndManager.stopSound(p_147683_1_);
    }

    public /* varargs */ SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory ... categories) {
        ArrayList list = Lists.newArrayList();
        for (ResourceLocation resourcelocation : this.sndRegistry.getKeys()) {
            SoundEventAccessorComposite soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(resourcelocation);
            if (!ArrayUtils.contains((Object[])categories, (Object)((Object)soundeventaccessorcomposite.getSoundCategory()))) continue;
            list.add((Object)soundeventaccessorcomposite);
        }
        return list.isEmpty() ? null : (SoundEventAccessorComposite)list.get(new Random().nextInt(list.size()));
    }

    public boolean isSoundPlaying(ISound sound) {
        return this.sndManager.isSoundPlaying(sound);
    }

}

