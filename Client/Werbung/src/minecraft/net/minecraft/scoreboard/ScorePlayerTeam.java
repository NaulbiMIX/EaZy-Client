/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;

public class ScorePlayerTeam
extends Team {
    private final Scoreboard theScoreboard;
    private final String field_96675_b;
    private final Set membershipSet = Sets.newHashSet();
    private String teamNameSPT;
    private String namePrefixSPT = "";
    private String colorSuffix = "";
    private boolean allowFriendlyFire = true;
    private boolean canSeeFriendlyInvisibles = true;
    private Team.EnumVisible field_178778_i = Team.EnumVisible.ALWAYS;
    private Team.EnumVisible field_178776_j = Team.EnumVisible.ALWAYS;
    private EnumChatFormatting field_178777_k = EnumChatFormatting.RESET;
    private static final String __OBFID = "CL_00000616";

    public ScorePlayerTeam(Scoreboard p_i2308_1_, String p_i2308_2_) {
        this.theScoreboard = p_i2308_1_;
        this.field_96675_b = p_i2308_2_;
        this.teamNameSPT = p_i2308_2_;
    }

    @Override
    public String getRegisteredName() {
        return this.field_96675_b;
    }

    public String func_96669_c() {
        return this.teamNameSPT;
    }

    public void setTeamName(String p_96664_1_) {
        if (p_96664_1_ == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.teamNameSPT = p_96664_1_;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    @Override
    public Collection getMembershipCollection() {
        return this.membershipSet;
    }

    public String getColorPrefix() {
        return this.namePrefixSPT;
    }

    public void setNamePrefix(String p_96666_1_) {
        if (p_96666_1_ == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.namePrefixSPT = p_96666_1_;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public String getColorSuffix() {
        return this.colorSuffix;
    }

    public void setNameSuffix(String p_96662_1_) {
        this.colorSuffix = p_96662_1_;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    @Override
    public String formatString(String input) {
        return String.valueOf(this.getColorPrefix()) + input + this.getColorSuffix();
    }

    public static String formatPlayerName(Team p_96667_0_, String p_96667_1_) {
        return p_96667_0_ == null ? p_96667_1_ : p_96667_0_.formatString(p_96667_1_);
    }

    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean p_96660_1_) {
        this.allowFriendlyFire = p_96660_1_;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    @Override
    public boolean func_98297_h() {
        return this.canSeeFriendlyInvisibles;
    }

    public void setSeeFriendlyInvisiblesEnabled(boolean p_98300_1_) {
        this.canSeeFriendlyInvisibles = p_98300_1_;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    @Override
    public Team.EnumVisible func_178770_i() {
        return this.field_178778_i;
    }

    @Override
    public Team.EnumVisible func_178771_j() {
        return this.field_178776_j;
    }

    public void func_178772_a(Team.EnumVisible p_178772_1_) {
        this.field_178778_i = p_178772_1_;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public void func_178773_b(Team.EnumVisible p_178773_1_) {
        this.field_178776_j = p_178773_1_;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public int func_98299_i() {
        int var1 = 0;
        if (this.getAllowFriendlyFire()) {
            var1 |= true;
        }
        if (this.func_98297_h()) {
            var1 |= 2;
        }
        return var1;
    }

    public void func_98298_a(int p_98298_1_) {
        this.setAllowFriendlyFire((p_98298_1_ & 1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((p_98298_1_ & 2) > 0);
    }

    public void func_178774_a(EnumChatFormatting p_178774_1_) {
        this.field_178777_k = p_178774_1_;
    }

    public EnumChatFormatting func_178775_l() {
        return this.field_178777_k;
    }
}

