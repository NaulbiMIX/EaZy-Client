/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.scoreboard;

import java.util.List;
import java.util.Map;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.EnumChatFormatting;

public class GoalColor
implements IScoreObjectiveCriteria {
    private final String field_178794_j;
    private static final String __OBFID = "CL_00001961";

    public GoalColor(String p_i45549_1_, EnumChatFormatting p_i45549_2_) {
        this.field_178794_j = String.valueOf(p_i45549_1_) + p_i45549_2_.getFriendlyName();
        IScoreObjectiveCriteria.INSTANCES.put(this.field_178794_j, this);
    }

    @Override
    public String getName() {
        return this.field_178794_j;
    }

    @Override
    public int func_96635_a(List p_96635_1_) {
        return 0;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public IScoreObjectiveCriteria.EnumRenderType func_178790_c() {
        return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
    }
}

