/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Collection
 */
package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.IMetadataSection;

public class LanguageMetadataSection
implements IMetadataSection {
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> p_i1311_1_) {
        this.languages = p_i1311_1_;
    }

    public Collection<Language> getLanguages() {
        return this.languages;
    }
}

