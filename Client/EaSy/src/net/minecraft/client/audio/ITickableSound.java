/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.ITickable;

public interface ITickableSound
extends ISound,
ITickable {
    public boolean isDonePlaying();
}

