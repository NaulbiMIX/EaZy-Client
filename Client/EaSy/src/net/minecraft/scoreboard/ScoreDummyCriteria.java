/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Map
 */
package net.minecraft.scoreboard;

import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;

public class ScoreDummyCriteria
implements IScoreObjectiveCriteria {
    private final String dummyName;

    public ScoreDummyCriteria(String name) {
        this.dummyName = name;
        IScoreObjectiveCriteria.INSTANCES.put((Object)name, (Object)this);
    }

    @Override
    public String getName() {
        return this.dummyName;
    }

    @Override
    public int setScore(List<EntityPlayer> p_96635_1_) {
        return 0;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
        return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
    }
}

