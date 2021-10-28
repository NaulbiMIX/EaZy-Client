/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package net.minecraft.stats;

import java.util.List;
import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.IChatComponent;

public class StatBasic
extends StatBase {
    public StatBasic(String statIdIn, IChatComponent statNameIn, IStatType typeIn) {
        super(statIdIn, statNameIn, typeIn);
    }

    public StatBasic(String statIdIn, IChatComponent statNameIn) {
        super(statIdIn, statNameIn);
    }

    @Override
    public StatBase registerStat() {
        super.registerStat();
        StatList.generalStats.add((Object)this);
        return this;
    }
}

