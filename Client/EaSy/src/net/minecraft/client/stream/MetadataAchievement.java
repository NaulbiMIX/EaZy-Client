/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package net.minecraft.client.stream;

import net.minecraft.client.stream.Metadata;
import net.minecraft.stats.Achievement;
import net.minecraft.util.IChatComponent;

public class MetadataAchievement
extends Metadata {
    public MetadataAchievement(Achievement p_i1032_1_) {
        super("achievement");
        this.func_152808_a("achievement_id", p_i1032_1_.statId);
        this.func_152808_a("achievement_name", p_i1032_1_.getStatName().getUnformattedText());
        this.func_152808_a("achievement_description", p_i1032_1_.getDescription());
        this.func_152807_a("Achievement '" + p_i1032_1_.getStatName().getUnformattedText() + "' obtained!");
    }
}

